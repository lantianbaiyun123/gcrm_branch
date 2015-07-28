package com.baidu.gcrm.resource.adplatform.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.baidu.gcrm.account.rights.model.RightsRole;
import com.baidu.gcrm.ad.approval.dao.IParticipantRepositoryCustom;
import com.baidu.gcrm.ad.approval.dao.ParticipantRepository;
import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.content.web.helper.BaseI18nModelComparator;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.occupation.service.IPositionOccupationService;
import com.baidu.gcrm.resource.adplatform.dao.IAdPlatformRepository;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformStatus;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformService;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformSiteRelationService;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.valuelist.service.IValuelistWithCacheService;
import com.baidu.gcrm.valuelist.utils.ITableMetaDataManger;

@Service
public class AdPlatformServiceImpl implements IAdPlatformService {
    
    @Autowired
    IAdPlatformRepository adPlatformRepository;
    
    @Autowired
    IAdPlatformSiteRelationService adPlatSiteRelationService;
    
    @Autowired
    ISiteService siteService;
    
    @Autowired
    IPositionService positionService;
    
    @Autowired
    I18nKVService i18nService;
    
    @Autowired
    IAdSolutionContentService adContentService;
    
    @Resource(name="fixedPositionOccupationServiceImpl")
    IPositionOccupationService positionOccupationService;
    
    @Autowired
    IValuelistWithCacheService valuelistCacheService;
    
    @Autowired
    private ITableMetaDataManger talbeManager;
    
    @Autowired
    private IParticipantRepositoryCustom participantRepositoryCustom;
    
    @Autowired
	ParticipantRepository participantRepository;
    
    @Autowired
    I18nKVService i18nKVService;
    
    
    private static final int CHINA_SITE_ID = 0;

    @Override
    public void saveAdPlatformAndRelation(AdPlatform adPlatform) {
        Long currAdPlatformId = adPlatform.getId();
        boolean isAdd = false;
        if (currAdPlatformId == null) {
            isAdd = true;
            adPlatform.setStatus(AdPlatformStatus.enable);
            adPlatformRepository.saveAndFlush(adPlatform);
            currAdPlatformId = adPlatform.getId();
            
            // 添加新投放平台时，创建人有相关权限 add by zhanglei35
            User currUser = RequestThreadLocal.getLoginUser();
            if (null != currUser) {
            	participantRepository.deleteByUnameAndDescAndKey(currUser.getUsername(), DescriptionType.platform, currAdPlatformId.toString());
            	List<RightsRole> roleList = RequestThreadLocal.getLoginUserRole();
            	if (null != roleList) {
					for (RightsRole role : roleList) {
						Participant participant = new Participant();
						participant.setParticipantId(role.getRoleTag());
						participant.setDescription(DescriptionType.platform);
						participant.setUsername(currUser.getUsername());
						participant.setKey(currAdPlatformId.toString());
						participantRepository.save(participant);
					}
				}
			}
        } else {
            AdPlatform dbAdPlatform = adPlatformRepository.findOne(currAdPlatformId);
            dbAdPlatform.setBusinessType(adPlatform.getBusinessType());
            dbAdPlatform.setUpdateOperator(adPlatform.getUpdateOperator());
            dbAdPlatform.setUpdateTime(adPlatform.getUpdateTime());
            adPlatformRepository.saveAndFlush(dbAdPlatform);
        }
        i18nService.deleteById(AdPlatform.class, currAdPlatformId);
        i18nService.save(AdPlatform.class, currAdPlatformId, adPlatform.getI18nData());
        
        //save relation
        adPlatSiteRelationService.saveAdPlatformSiteRelation(adPlatform, isAdd);
        
        //refresh cache
        valuelistCacheService.refreshCache(talbeManager.getTableMetaData("g_advertising_platform"));
        valuelistCacheService.refreshCache(talbeManager.getTableMetaData("g_i18n"));
    }

    @Override
    public AdPlatform findById(Long id, LocaleConstants locale) {
        AdPlatform currAdPlatform = null;
        boolean needProcessStatus = false;
        if (id == null) {
            currAdPlatform = new AdPlatform();
        } else {
            needProcessStatus = true;
            currAdPlatform =  adPlatformRepository.findOne(id);
            List<I18nKV> i18nInfoList = i18nService.findById(AdPlatform.class, id);
            String enName = null;
            String cnName = null;
            for (I18nKV temI18nKV : i18nInfoList) {
                LocaleConstants temLocale = temI18nKV.getLocale();
                String value = temI18nKV.getValue();
                if (LocaleConstants.en_US == temLocale) {
                    enName = value;
                } else if (LocaleConstants.zh_CN == temLocale) {
                    cnName = value;
                }
            }
            
            currAdPlatform.setCnName(cnName);
            currAdPlatform.setEnName(enName);
        }
        
        List<Site> siteList = siteService.findAll(locale);
        //remove china site
        removeChinaSite(siteList);
        //process site
        if (needProcessStatus) {
            processSiteStatus(id, siteList);
        }
        Collections.sort(siteList, new BaseI18nModelComparator());
        currAdPlatform.setSites(siteList);
        return currAdPlatform;
    }
    
    private void removeChinaSite(List<Site> siteList) {
        Iterator<Site> iterator = siteList.iterator();
        while (iterator.hasNext()) {
            Site temSite = iterator.next();
            Long siteId = temSite.getId();
            if (siteId.longValue() == CHINA_SITE_ID) {
                iterator.remove();
            }
        }
    }
    
    private void processSiteStatus(Long adPlatformId, List<Site> siteList) {
        if (CollectionUtils.isEmpty(siteList)) {
            return;
        }
        //process checked
        List<AdPlatformSiteRelation> relations = adPlatSiteRelationService.findByAdPlatformIdAndStatus(adPlatformId,
                AdPlatformSiteRelationStatus.enable);
        Map<Long,String> siteRelationMap = new HashMap<Long,String> ();
        if (!CollectionUtils.isEmpty(relations)) {
            for (AdPlatformSiteRelation temAdPlatformSiteRelation : relations) {
                siteRelationMap.put(temAdPlatformSiteRelation.getSiteId(), "");
            }
        }
        //process allowCancel
        Map<Long,String> usedSiteMap = new HashMap<Long,String> ();
        List<Long> usedSiteIds = siteService.findUsedSiteIdByAdPlatform(adPlatformId);
        if (!CollectionUtils.isEmpty(usedSiteIds)) {
            for (Long temUsedSiteId : usedSiteIds) {
                usedSiteMap.put(temUsedSiteId, "");
            }
        }
        
        for (Site temSite : siteList) {
            Long siteId = temSite.getId();
            if (siteRelationMap.get(siteId) != null) {
                temSite.setChecked(true);
            }
            if (usedSiteMap.get(siteId) != null) {
                temSite.setAllowCanceled(false);
            } else {
                temSite.setAllowCanceled(true);
            }
        }
        
    }

    @Override
    public List<AdPlatform> findAll(LocaleConstants locale, boolean isQueryList) {
        List<AdPlatform> allAdPlatform = adPlatformRepository.findAllOrderByCreateTime();
        if (locale != null) {
            i18nService.fillI18nInfo(allAdPlatform, locale);
            if (isQueryList && !CollectionUtils.isEmpty(allAdPlatform)) {
                processSiteListInfo(allAdPlatform, locale);
            }
        }
        
        return allAdPlatform;
    }
    
    private void processSiteListInfo(List<AdPlatform> allAdPlatform, LocaleConstants locale) {
        
        List<Long> adPlatformIds = new ArrayList<Long> ();
        for (AdPlatform temAdPlatform : allAdPlatform) {
            adPlatformIds.add(temAdPlatform.getId());
        }
        List<AdPlatformSiteRelation> relations = adPlatSiteRelationService.findByAdPlatformIdInAndStatus(adPlatformIds, AdPlatformSiteRelationStatus.enable);
        if (CollectionUtils.isEmpty(relations)) {
            return;
        }
        //key platformId
        Map<Long, List<AdPlatformSiteRelation>> relationMap = new HashMap<Long, List<AdPlatformSiteRelation>> ();
        Set<Long> siteIds = new HashSet<Long> ();
        for (AdPlatformSiteRelation temAdPlatformSiteRelation : relations) {
            siteIds.add(temAdPlatformSiteRelation.getSiteId());
            Long temAdPlatformId = temAdPlatformSiteRelation.getAdPlatformId();
            List<AdPlatformSiteRelation> temAdPlatformList = relationMap.get(temAdPlatformId);
            if (temAdPlatformList == null) {
                temAdPlatformList = new ArrayList<AdPlatformSiteRelation> ();
                relationMap.put(temAdPlatformId, temAdPlatformList);
            }
            
            temAdPlatformList.add(temAdPlatformSiteRelation);
        }
        Map<Long, Site> siteMap = new HashMap<Long, Site> ();
        List<Site> siteList = siteService.findByIdIn(siteIds, locale);
        for (Site temSite : siteList) {
            siteMap.put(temSite.getId(), temSite);
        }
        
        processSiteNames(allAdPlatform, relationMap, siteMap);
    }
    
    private void processSiteNames(List<AdPlatform> allAdPlatform,
            Map<Long, List<AdPlatformSiteRelation>> relationMap,
            Map<Long, Site> siteMap) {
        if (siteMap.size() < 1) {
            return;
        }
        
        for (AdPlatform temAdPlatform : allAdPlatform) {
            Long adPlatformId = temAdPlatform.getId();
            List<AdPlatformSiteRelation> siteRelations = relationMap.get(adPlatformId);
            if (!CollectionUtils.isEmpty(siteRelations)) {
                temAdPlatform.setSiteCount(siteRelations.size());
                StringBuilder siteNameStr = new StringBuilder();
                for (AdPlatformSiteRelation currAdPlatformSiteRelation : siteRelations) {
                    Long currSiteId = currAdPlatformSiteRelation.getSiteId();
                    Site currSite = siteMap.get(currSiteId);
                    if (currSite == null) {
                        continue;
                    }
                    if (siteNameStr.length() > 0) {
                        siteNameStr.append(",");
                    }
                    siteNameStr.append(currSite.getI18nName());
                }
                
                temAdPlatform.setSiteNames(siteNameStr.toString());
            } else {
                temAdPlatform.setSiteCount(-1);
            }
        }
    }

    @Override
    public Long findUsedAmountById(Long id) {
        return adContentService.findAdContentAmountByAdPlatformId(id);
    }

    @Override
    public List<I18nKV> findExistsName(Long id, List<LocaleVO> values) {
        List<I18nKV> dbI18nNames = i18nService.findExistsValue(AdPlatform.class, id, values);
        if (CollectionUtils.isEmpty(dbI18nNames)) {
            return null;
        }
        return dbI18nNames;
    }

    @Override
    public List<AdPlatform> findByStatus(LocaleConstants locale,
            AdPlatformStatus status) {
        List<AdPlatform> allAdPlatform = adPlatformRepository.findByStatus(status);
        i18nService.fillI18nInfo(allAdPlatform, locale);
        return allAdPlatform;
    }

    @Override
    public void closeAdPlatform(AdPlatform adPlatform) {
        processAdPlatformStatusChange(adPlatform, AdPlatformSiteRelationStatus.disable,
                PositionStatus.disable);
        
        
    }
    
    @Override
    public void openAdPlatform(AdPlatform adPlatform) {
        processAdPlatformStatusChange(adPlatform, AdPlatformSiteRelationStatus.enable,
                PositionStatus.enable);
        
    }
    
    private void processAdPlatformStatusChange(AdPlatform adPlatform,AdPlatformSiteRelationStatus relationStatus,
            PositionStatus positionStatus) {
        adPlatformRepository.saveAndFlush(adPlatform);
        
        /*
        Long adPlatformId = adPlatform.getId();
        if (PositionStatus.disable == positionStatus) {
            List<Position> positionList = positionService.findByAdPlatformAndStatusAndType(adPlatformId,
                    AdPlatformSiteRelationStatus.enable, PositionStatus.enable,
                    PositionType.position);
            if (!CollectionUtils.isEmpty(positionList)) {
                positionService.releaseOccupationByPositions(positionList);
            }
        } else if (PositionStatus.enable == positionStatus) {
            List<Position> positionList = positionService.findByAdPlatformAndStatusAndType(adPlatformId,
                    AdPlatformSiteRelationStatus.enable, PositionStatus.disable,
                    PositionType.position);
            if (!CollectionUtils.isEmpty(positionList)) {
                for (Position temPosition : positionList) {
                    positionOccupationService.enablePositionOccupation(temPosition);
                }
            }
        }
        
        positionService.updateStatusByAdPlatformId(adPlatformId, adPlatform.getUpdateTime(),
                adPlatform.getUpdateOperator(), positionStatus);
                
        */
        
        //refresh cache
        valuelistCacheService.refreshCache(talbeManager.getTableMetaData("g_advertising_platform"));
        valuelistCacheService.refreshCache(talbeManager.getTableMetaData("g_i18n"));
        
    }

    @Override
    public AdPlatform findById(Long id) {
        return adPlatformRepository.findOne(id);
    }

    @Override
    public Map<String, String> findUsedAdPlatform(List<Long> adPlatformIds) {
        Map<String, String> usedMap = new HashMap<String,String> ();
        if (CollectionUtils.isEmpty(adPlatformIds)) {
            return usedMap;
        }
        List<Long> adPlatformUsedList = adPlatformRepository.findByUsed(adPlatformIds,
                PositionType.position, PositionStatus.enable);
        if (!CollectionUtils.isEmpty(adPlatformUsedList)) {
            for (Long adPlatformId : adPlatformUsedList) {
                usedMap.put(adPlatformId.toString(), "");
            }
        }
        return usedMap;
    }
    
    public List<AdPlatformSiteRelation> findAccessSites4Platform(Long platformId,LocaleConstants locale) {
        List<AdPlatformSiteRelation> platSiteRelats =
                adPlatSiteRelationService.findByAdPlatformIdAndStatus(platformId, AdPlatformSiteRelationStatus.enable);
        List<Participant> pants = participantRepository.findByUsernameAndDescription(RequestThreadLocal.getLoginUser().getUsername(), DescriptionType.site);
        Set<Long> set = new HashSet<Long>();
        if (pants != null) {
            for (Participant pant : pants) {
                set.add(Long.parseLong(pant.getKey()));
            }
        }
        List<AdPlatformSiteRelation> tempSiteRelats = new ArrayList<AdPlatformSiteRelation>();
        if (platSiteRelats != null) {
            for (AdPlatformSiteRelation relation : platSiteRelats) {
                if (set.contains(relation.getSiteId())) {
                    relation.setSiteName(i18nKVService.getAndLoadI18Info(Site.class, relation.getSiteId(),
                            locale).getValue());
                    tempSiteRelats.add(relation);
                }
            }
        }
        return tempSiteRelats;
    }
    
}
