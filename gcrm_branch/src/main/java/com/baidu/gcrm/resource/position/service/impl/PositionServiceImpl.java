package com.baidu.gcrm.resource.position.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.model.AdContentCancelRecord.CancelReason;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.IFileUploadService;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.random.IRandomCheckCallback;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.common.random.RandomType;
import com.baidu.gcrm.common.velocity.VelocityUtil;
import com.baidu.gcrm.data.bean.PositionBean;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.mail.PositionDisableContent;
import com.baidu.gcrm.occupation.service.IPositionOccupationCommonService;
import com.baidu.gcrm.occupation1.service.IPositionDateService;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation.AdPlatformSiteRelationStatus;
import com.baidu.gcrm.resource.position.dao.PositionRepository;
import com.baidu.gcrm.resource.position.model.IPositionModel;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionMaterialType;
import com.baidu.gcrm.resource.position.model.Position.PositionPropertyStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.resource.position.model.Position.RotationType;
import com.baidu.gcrm.resource.position.model.PositionQueryBean;
import com.baidu.gcrm.resource.position.model.PositionQueryBean.PositionButtonNumber;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.position.web.vo.PositionCondition;
import com.baidu.gcrm.resource.position.web.vo.PositionI18nVO;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;
import com.baidu.gcrm.stock.service.IStockService;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;

@Service
public class PositionServiceImpl implements IPositionService {

    Logger logger = LoggerFactory.getLogger(PositionServiceImpl.class);

    @Autowired
    PositionRepository positionRepository;

    @Autowired
    I18nKVService i18nService;

    @Autowired
    AdvertisingPlatformServiceImpl adPlatformServiceImpl;

    @Autowired
    private IFileUploadService fileUploadService;

    @Autowired
    IPositionOccupationCommonService positionOccupationCommonService;

    @Autowired
    IAdSolutionContentService adContentService;

    @Autowired
    IBpmProcessService processService;

    @Resource(name = "taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;

    @Autowired
    IRandomStringService randomService;
    
    @Autowired
    IStockService stockService;
    
    @Autowired
    IPositionDateService positionDateService;

    @Override
    public List<PositionVO> findChannelBySiteIdAndStatus(Long adPlatformId, Long siteId, LocaleConstants locale,
            PositionStatus status) {
        List<Position> positionList = null;
        if (null == adPlatformId || null == siteId) {
            return new ArrayList<PositionVO>();
        }
        if (status == null) {
            positionList =
                    positionRepository.findByAdPlatformIdAndSiteIdAndType(adPlatformId, siteId, PositionType.channel);
        } else {
            positionList =
                    positionRepository.findByAdPlatformIdAndSiteIdAndTypeAndStatus(adPlatformId, siteId,
                            PositionType.channel, status);
        }

        return convertPositionToVO(positionList, locale);
    }

    @Override
    public List<PositionVO> findByParentIdAndStatus(Long parentId, LocaleConstants locale, PositionStatus status) {
        List<Position> positionList = null;
        if (null == parentId) {
            return new ArrayList<PositionVO>();
        }
        if (status == null) {
            positionList = positionRepository.findByParentId(parentId);
        } else {
            positionList = positionRepository.findByParentIdAndStatus(parentId, status);
        }
        return convertPositionToVO(positionList, locale);
    }

    private List<PositionVO> convertPositionToVO(Iterable<Position> positionList, LocaleConstants locale) {
        if (positionList == null) {
            return null;
        }
        List<PositionVO> positionVOList = new LinkedList<PositionVO>();
        for (Position temPosition : positionList) {
            if (locale != null) {
                i18nService.fillI18nInfo(temPosition, locale);
            }
            positionVOList.add(convertToPositionVO(temPosition));
        }

        return positionVOList;
    }

    private PositionVO convertToPositionVO(Position currPosition) {
        PositionVO temPositionVO = new PositionVO();
        Long id = currPosition.getId();
        temPositionVO.setId(id);
        temPositionVO.setI18nName(currPosition.getI18nName());
        if (currPosition.getMaterialType() != null) {
            temPositionVO.setMaterialType(currPosition.getMaterialType().ordinal());
        }
        temPositionVO.setAreaRequired(currPosition.getAreaRequired());
        temPositionVO.setSize(currPosition.getSize());
        temPositionVO.setTextlinkLength(currPosition.getTextlinkLength());
        temPositionVO.setSalesAmount(currPosition.getSalesAmount());
        temPositionVO.setPv(currPosition.getPv());
        temPositionVO.setClick(currPosition.getClick());
        temPositionVO.setCptBenchmarkPrice(currPosition.getCptBenchmarkPrice());
        temPositionVO.setI18nData(getPositionI18nVO(Position.class, id));

        if (currPosition.getRotationType() != null) {
            temPositionVO.setRotationType(currPosition.getRotationType().ordinal());
        }
        if (currPosition.getRotationOrder() != null) {
            temPositionVO.setRotationOrder(currPosition.getRotationOrder());
        }
        String guideFileName = currPosition.getGuideFileName();
        if (PatternUtil.isBlank(guideFileName)) {
            temPositionVO.setHasGuideFile(false);
        } else {
            temPositionVO.setHasGuideFile(true);
        }
        temPositionVO.setIndexStr(currPosition.getIndexStr());
        return temPositionVO;

    }

    @Override
    public List<Position> findByParentId(Long parentId) {
        return positionRepository.findByParentId(parentId);
    }

    @Override
    public Position findById(Long id) {
        if (null == id) {
            return null;
        }
        return positionRepository.findOne(id);
    }

    @Override
    public List<Position> findByIds(Collection<Long> ids) {
        return positionRepository.findByIdIn(ids);
    }

    @Override
    public PositionVO findPositionVOById(Long id) {
        if (null == id) {
            return new PositionVO();
        }
        Position temPosition = positionRepository.findOne(id);
        return convertToPositionVO(temPosition);
    }

    @Override
    public long findExistsI18nName(Long id, String i18nValue, LocaleConstants locale) {
        return positionRepository.findExistsName(id, i18nValue, locale);
    }

    @Override
    public List<PositionVO> findI18nByPositionIds(List<Long> ids, LocaleConstants locale) {
        Iterable<Position> positionList = positionRepository.findAll(ids);

        return convertPositionToVO(positionList, locale);
    }

    @Override
    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    @Override
    public Position savePositionAndI18nInfo(Position position, boolean isImport) throws CRMBaseException {
        List<PositionVO> channelList = position.getPosotionData();
        if (CollectionUtils.isEmpty(channelList)) {
            return null;
        }
        // procese adPlatform and site
        BaseI18nInfoDecoratorVO i18nInfoDecoratorVO = new BaseI18nInfoDecoratorVO();
        i18nInfoDecoratorVO.setAdPlatformI18nVO(getPositionI18nVO(AdPlatform.class, position.getAdPlatformId()));
        i18nInfoDecoratorVO.setSiteI18nVO(getPositionI18nVO(Site.class, position.getSiteId()));

        PositionInfoDecorator channelDecorator = new ChannelDecorator(i18nInfoDecoratorVO);
        PositionInfoDecorator areaDecorator = new AreaDecorator(i18nInfoDecoratorVO);
        PositionInfoDecorator positionDecorator = new PositionDecorator(i18nInfoDecoratorVO);
        for (PositionVO temChannelInfoVO : channelList) {
            i18nInfoDecoratorVO.setChannelI18nVO(temChannelInfoVO.getI18nData());
            Long channelId =
                    processPositionVO(temChannelInfoVO, position, channelDecorator, null, PositionType.channel,
                            isImport);
            position.setChannelId(channelId);
            temChannelInfoVO.setId(channelId);

            // save area
            List<PositionVO> areaData = temChannelInfoVO.getChildren();
            if (CollectionUtils.isEmpty(areaData)) {
                continue;
            }
            for (PositionVO temAreaInfoVO : areaData) {
                i18nInfoDecoratorVO.setAreaI18nVO(temAreaInfoVO.getI18nData());
                Long arealId =
                        processPositionVO(temAreaInfoVO, position, areaDecorator, channelId, PositionType.area,
                                isImport);
                position.setAreaId(arealId);
                temAreaInfoVO.setId(arealId);

                List<PositionVO> positionData = temAreaInfoVO.getChildren();
                if (CollectionUtils.isEmpty(positionData)) {
                    continue;
                }
                for (PositionVO temPositionInfoVO : positionData) {
                    Long positionId =
                            processPositionVO(temPositionInfoVO, position, positionDecorator, arealId,
                                    PositionType.position, isImport);
                    temPositionInfoVO.setId(positionId);
                }
            }
        }
        return position;
    }

    private PositionI18nVO getPositionI18nVO(Class<?> clazz, Serializable id) {
        List<I18nKV> i18nInfoList = i18nService.findById(clazz, id);
        String enName = null;
        String cnName = null;
        String enExtraName = null;
        String cnExtraName = null;
        for (I18nKV temI18nKV : i18nInfoList) {
            LocaleConstants locale = temI18nKV.getLocale();
            String value = temI18nKV.getValue();
            String extraValue = temI18nKV.getExtraValue();
            if (LocaleConstants.en_US == locale) {
                enName = value;
                enExtraName = extraValue;
            } else if (LocaleConstants.zh_CN == locale) {
                cnName = value;
                cnExtraName = extraValue;
            }
        }
        PositionI18nVO currDbTempPositionI18nVO = new PositionI18nVO(enName, cnName);
        currDbTempPositionI18nVO.setCnExtraName(cnExtraName);
        currDbTempPositionI18nVO.setEnExtraName(enExtraName);
        return currDbTempPositionI18nVO;
    }

    private Long processPositionVO(PositionVO currPositionInfoVO, Position currExtraPosition,
            PositionInfoDecorator decorator, Long parentId, PositionType type, boolean isImport)
            throws CRMBaseException {

        Long currPositionId = currPositionInfoVO.getId();
        if (currPositionId != null) {// update
            if (isImport) {
                return currPositionId;
            }
            Position dbPosition = positionRepository.findOne(currPositionId);
            // clean history info
            cleanHistotyProperty(dbPosition, type);
            dbPosition.setUpdateTime(currExtraPosition.getUpdateTime());
            dbPosition.setUpdateOperator(currExtraPosition.getUpdateOperator());
            dbPosition.setType(type);
            dbPosition.setIndexStr(decorator.buildIndexStr(currExtraPosition).toString());
            dbPosition.setParentId(parentId);
            positionRepository.save(dbPosition);
        } else {// new
            Long adPlatformId = currExtraPosition.getAdPlatformId();
            Long siteId = currExtraPosition.getSiteId();
            Position temPosition = new Position();
            temPosition.setAdPlatformId(adPlatformId);
            temPosition.setSiteId(siteId);
            temPosition.setType(type);
            temPosition.setParentId(parentId);
            temPosition.setCreateOperator(currExtraPosition.getCreateOperator());
            temPosition.setCreateTime(currExtraPosition.getCreateTime());
            temPosition.setUpdateOperator(currExtraPosition.getUpdateOperator());
            temPosition.setUpdateTime(currExtraPosition.getUpdateTime());
            temPosition.setIndexStr(decorator.buildIndexStr(currExtraPosition).toString());
            // generate number
            String positionNumber = randomService.random(6, RandomType.random_position, new IRandomCheckCallback() {
                @Override
                public boolean exists(String randomStr) {
                    Position exists = positionRepository.findByPositionNumber(randomStr);
                    if (exists == null) {
                        return false;
                    }
                    return true;
                }

            });
            temPosition.setPositionNumber(positionNumber);
            // set other property
            temPosition.setStatus(PositionStatus.disable);
            Integer rotationType = currPositionInfoVO.getRotationType();

            if (rotationType != null) {
                temPosition.setRotationType(RotationType.valueOf(rotationType));
            }
            if (currPositionInfoVO.getRotationOrder() != null) {
                temPosition.setRotationOrder(currPositionInfoVO.getRotationOrder());
            }
            if (rotationType != null && rotationType.intValue() == RotationType.no.ordinal()) {
                temPosition.setSalesAmount(1);
            } else {
                temPosition.setSalesAmount(currPositionInfoVO.getSalesAmount());
            }

            if (currPositionInfoVO.getMaterialType() != null) {
                temPosition.setMaterialType(PositionMaterialType.valueOf(currPositionInfoVO.getMaterialType()));
            }

            temPosition.setAreaRequired(currPositionInfoVO.getAreaRequired());
            temPosition.setSize(currPositionInfoVO.getSize());
            temPosition.setTextlinkLength(currPositionInfoVO.getTextlinkLength());
            temPosition.setPv(currPositionInfoVO.getPv());
            temPosition.setClick(currPositionInfoVO.getClick());
            temPosition.setCptBenchmarkPrice(currPositionInfoVO.getCptBenchmarkPrice());

            PositionMaterialType materialType = temPosition.getMaterialType();
            if (Constants.PLATFORM_HAO123_ID.equals(adPlatformId) && materialType != null) {
                if (PositionMaterialType.image == materialType
                        || PositionMaterialType.image_and_textlink == materialType) {
                    temPosition.setPositionCode(generatePositionCode(temPosition.getAreaRequired(), positionNumber));
                }
            }

            positionRepository.save(temPosition);
            currPositionId = temPosition.getId();
        }
        // process i18n info
        savePositionI18nInfo(currPositionId, currPositionInfoVO, decorator);
        currPositionInfoVO.setId(currPositionId);
        return currPositionId;
    }

    private void savePositionI18nInfo(Long id, PositionVO positionInfoVO, PositionInfoDecorator decorator) {
        PositionI18nVO positionI18nVO = positionInfoVO.getI18nData();
        ;
        if (positionI18nVO == null) {
            return;
        }

        List<LocaleVO> localeVOList = positionI18nVO.peocessLocaleVO(decorator);
        i18nService.deleteById(Position.class, id);
        i18nService.save(Position.class, id, localeVOList);
    }

    private void cleanHistotyProperty(Position dbPosition, PositionType currentType) {

        PositionType historyType = dbPosition.getType();
        if (historyType == currentType) {
            return;
        }
        if (PositionType.area == historyType) {
            // clean guide info
            String existsGuideFileName = dbPosition.getGuideFileName();
            if (!PatternUtil.isBlank(existsGuideFileName)) {
                try {
                    fileUploadService.deleteFile(existsGuideFileName);
                } catch (Exception e) {
                    logger.error("deleteFile failed!", existsGuideFileName);
                }
            }

            dbPosition.setGuideFileDownloadName(null);
            dbPosition.setGuideFileName(null);
            dbPosition.setGuideUrl(null);
        } else if (PositionType.position == historyType) {
            // clean position property
            dbPosition.setRotationType(null);
            dbPosition.setRotationOrder(null);
            dbPosition.setMaterialType(null);
            dbPosition.setAreaRequired(null);
            dbPosition.setSize(null);
            dbPosition.setTextlinkLength(null);
            dbPosition.setCptBenchmarkPrice(null);
            dbPosition.setSalesAmount(null);
            dbPosition.setPv(null);
            dbPosition.setClick(null);
        }
    }

    @Override
    public PositionCondition<PositionQueryBean> query(PositionCondition<PositionQueryBean> page,
            List<AdPlatformSiteRelation> relations, LocaleConstants locale) {

        PositionCondition<PositionQueryBean> pageList = positionRepository.query(page, relations, locale);

        List<PositionQueryBean> resultList = pageList.getContent();
        if (CollectionUtils.isEmpty(resultList)) {
            return pageList;
        }

        for (PositionQueryBean temPositionQueryBean : resultList) {
            processNamesAndButtonStatus(temPositionQueryBean, null);
        }
        pageList.setTotalCount(pageList.getTotal());
        return pageList;
    }

    @Override
    public PositionCondition<PositionQueryBean> queryListTree(PositionCondition<PositionQueryBean> page,
            LocaleConstants locale) {
        PositionCondition<PositionQueryBean> pageList = positionRepository.query(page, null, locale);
        List<PositionQueryBean> resultList = pageList.getContent();
        if (CollectionUtils.isEmpty(resultList)) {
            return pageList;
        }
        List<PositionQueryBean> allProcessedResultList = new LinkedList<PositionQueryBean>();
        Map<String, String> channelDetectMap = new HashMap<String, String>();
        List<PositionQueryBean> channelResultList = new LinkedList<PositionQueryBean>();
        Map<Long, List<PositionQueryBean>> subListMap = new HashMap<Long, List<PositionQueryBean>>();
        // process result
        for (PositionQueryBean temPositionQueryBean : resultList) {
            Long parentId = temPositionQueryBean.getParentId();
            Long id = temPositionQueryBean.getId();
            PositionType currType = temPositionQueryBean.getType();
            if (PositionType.channel == currType && channelDetectMap.get(id.toString()) == null) {
                channelDetectMap.put(id.toString(), "");
                channelResultList.add(temPositionQueryBean);
            } else {
                List<PositionQueryBean> subList = subListMap.get(parentId);
                if (subList == null) {
                    subList = new LinkedList<PositionQueryBean>();
                    subListMap.put(parentId, subList);
                }
                subList.add(temPositionQueryBean);
            }
        }

        for (PositionQueryBean topPositionQueryBean : channelResultList) {
            allProcessedResultList.add(topPositionQueryBean);
            Long channelId = topPositionQueryBean.getId();
            List<PositionQueryBean> subAreaList = subListMap.get(channelId);
            if (CollectionUtils.isEmpty(subAreaList)) {
                continue;
            }
            for (PositionQueryBean temAreaPositionQueryBean : subAreaList) {
                allProcessedResultList.add(temAreaPositionQueryBean);
                Long areaId = temAreaPositionQueryBean.getId();
                List<PositionQueryBean> subPositionList = subListMap.get(areaId);
                if (CollectionUtils.isEmpty(subPositionList)) {
                    continue;
                }
                for (PositionQueryBean temPositionPositionQueryBean : subPositionList) {
                    allProcessedResultList.add(temPositionPositionQueryBean);
                }
            }
        }

        for (PositionQueryBean temPositionQueryBean : resultList) {
            processNamesAndButtonStatus(temPositionQueryBean, subListMap);
        }

        pageList.setContent(allProcessedResultList);
        return pageList;
    }

    private void processNamesAndButtonStatus(PositionQueryBean currPositionQueryBean,
            Map<Long, List<PositionQueryBean>> subListMap) {
        Long id = currPositionQueryBean.getId();
        List<PositionButtonNumber> queryButtons = new LinkedList<PositionButtonNumber>();
        List<PositionButtonNumber> modifyButtons = new LinkedList<PositionButtonNumber>();
        String[] i18nExtraNameArray = null;
        String i18nExtraName = currPositionQueryBean.getI18nExtraValue();
        if (!PatternUtil.isBlank(i18nExtraName)) {
            i18nExtraNameArray = i18nExtraName.split(InfoDecorator.SPLIT_FLAG);
            if (i18nExtraNameArray != null && i18nExtraNameArray.length > 1) {
                currPositionQueryBean.setAdPlatformName(i18nExtraNameArray[0]);
                currPositionQueryBean.setSiteName(i18nExtraNameArray[1]);
            }
        }
        String i18nName = currPositionQueryBean.getI18nValue();
        PositionType type = currPositionQueryBean.getType();
        PositionStatus positionStatus = currPositionQueryBean.getStatus();

        if (PositionType.position == type) {
            queryButtons.add(PositionButtonNumber.viewProperty);

            if (hasProperty(currPositionQueryBean)) {
                modifyButtons.add(PositionButtonNumber.modifyProperty);
            } else {
                modifyButtons.add(PositionButtonNumber.addProperty);
            }

            if (PositionStatus.enable == positionStatus) {
                queryButtons.add(PositionButtonNumber.viewOccupation);
                modifyButtons.add(PositionButtonNumber.modifyOccupation);
            }

            if (currPositionQueryBean.getPositionCode() != null) {
                queryButtons.add(PositionButtonNumber.viewCode);
            }

            if (i18nExtraNameArray != null && i18nExtraNameArray.length > 3) {
                currPositionQueryBean.setChannelName(i18nExtraNameArray[2]);
                currPositionQueryBean.setAreaName(i18nExtraNameArray[3]);
            }

            currPositionQueryBean.setName(i18nName);

        } else if (PositionType.area == type) {
            boolean hasRotationType = false;
            List<Position> subPositions = positionRepository.findByParentIdAndNotNullRotationType(id);
            if (!CollectionUtils.isEmpty(subPositions)) {
                hasRotationType = true;
            }

            if (subListMap != null && subListMap.get(id) != null && !hasRotationType) {
                modifyButtons.add(PositionButtonNumber.batchModifyProperty);
            }
            String guideFileName = currPositionQueryBean.getGuideFileName();
            if (PatternUtil.isBlank(guideFileName)) {
                modifyButtons.add(PositionButtonNumber.addImage);
            } else {
                queryButtons.add(PositionButtonNumber.viewImage);
                modifyButtons.add(PositionButtonNumber.modifyImage);
            }

            if (i18nExtraNameArray != null && i18nExtraNameArray.length > 2) {
                currPositionQueryBean.setChannelName(i18nExtraNameArray[2]);
            }
            currPositionQueryBean.setAreaName(i18nName);
        } else if (PositionType.channel == type) {
            currPositionQueryBean.setChannelName(i18nName);
        }

        PositionMaterialType materialType = currPositionQueryBean.getMaterialType();
        if (materialType != null) {
            currPositionQueryBean.setMaterialTypeValue(Integer.valueOf(materialType.ordinal()));
        }

        modifyButtons.add(PositionButtonNumber.modifyName);

        currPositionQueryBean.setQueryButtons(queryButtons);
        currPositionQueryBean.setModifyButtons(modifyButtons);
    }

    // What the F**k determine
    @Override
    public boolean hasProperty(IPositionModel positionModel) {
        if (positionModel.getRotationType() != null) {
            return true;
        } else if (positionModel.getMaterialType() != null) {
            return true;
        }else if (positionModel.getRotationOrder() != null) {
            return true;
        } else if (positionModel.getAreaRequired() != null) {
            return true;
        } else if (!PatternUtil.isBlank(positionModel.getSize())) {
            return true;
        } else if (positionModel.getTextlinkLength() != null) {
            return true;
        } else if (positionModel.getSalesAmount() != null) {
            return true;
        } else if (positionModel.getPv() != null) {
            return true;
        } else if (positionModel.getClick() != null) {
            return true;
        } else if (positionModel.getCptBenchmarkPrice() != null) {
            return true;
        }

        return false;
    }

    @Override
    public Position queryPreSaveTree(Long adPlatformId, Long siteId) {
        List<Position> allPosition = positionRepository.findByAdPlatformIdAndSiteIdOrderByIdDesc(adPlatformId, siteId);
        Map<String, String> channelDetectMap = new HashMap<String, String>();
        List<PositionVO> allChannelPosition = new LinkedList<PositionVO>();
        // parentId
        Map<Long, List<PositionVO>> subPositionInfoMap = new HashMap<Long, List<PositionVO>>();
        for (Position temPosition : allPosition) {
            Long parentId = temPosition.getParentId();
            Long id = temPosition.getId();
            PositionVO currPositionInfoVO = new PositionVO();
            currPositionInfoVO.setId(id);
            currPositionInfoVO.setI18nData(getPositionI18nVO(Position.class, id));

            PositionType type = temPosition.getType();
            if (PositionType.channel == type && channelDetectMap.get(id.toString()) == null) {
                channelDetectMap.put(id.toString(), "");
                allChannelPosition.add(currPositionInfoVO);
            } else if (parentId != null) {
                List<PositionVO> existsSubPosition = subPositionInfoMap.get(parentId);
                if (existsSubPosition == null) {
                    existsSubPosition = new LinkedList<PositionVO>();
                    subPositionInfoMap.put(parentId, existsSubPosition);
                }
                existsSubPosition.add(currPositionInfoVO);
            }
        }

        // process result
        for (PositionVO temPositionInfoVO : allChannelPosition) {
            Long channelId = temPositionInfoVO.getId();

            List<PositionVO> subPositionInfoVO = subPositionInfoMap.get(channelId);
            if (subPositionInfoVO != null) {
                temPositionInfoVO.setChildren(subPositionInfoVO);
                for (PositionVO temSubPositionInfoVO : subPositionInfoVO) {
                    Long areaId = temSubPositionInfoVO.getId();
                    List<PositionVO> currSubPositionInfoVO = subPositionInfoMap.get(areaId);
                    if (currSubPositionInfoVO != null) {
                        temSubPositionInfoVO.setChildren(currSubPositionInfoVO);
                    }
                }
            }
        }
        Position currPosition = new Position();
        currPosition.setAdPlatformId(adPlatformId);
        currPosition.setSiteId(siteId);
        currPosition.setPosotionData(allChannelPosition);
        return currPosition;
    }

    @Override
    public int updatePositionProperty(Position currPosition, Position dbPosition) {
        PositionType type = dbPosition.getType();
        currPosition.setType(type);

        if (PositionType.area == type) {

            if (Constants.PLATFORM_HAO123_ID.equals(dbPosition.getAdPlatformId())) {
                PositionMaterialType materialType = currPosition.getMaterialType();
                if (PositionMaterialType.image == materialType
                        || PositionMaterialType.image_and_textlink == materialType) {

                    List<Position> subPositions = positionRepository.findByParentId(currPosition.getId());
                    for (Position position : subPositions) {
                        position.setPositionCode(generatePositionCode(currPosition.getAreaRequired(),
                                position.getPositionNumber()));
                    }
                    positionRepository.save(subPositions);
                }
            }

            if (currPosition.getSalesAmount() != null) {
                return positionRepository.updateSubPositionPropertyWithSalesAmount(currPosition);
            } else {
                return positionRepository.updateSubPositionProperty(currPosition);
            }

        } else if (PositionType.position == type) {

            Integer currSalesAmount = currPosition.getSalesAmount();
            Integer dbSalesAmount = dbPosition.getSalesAmount();
            int dbSalesAmountInt = dbSalesAmount == null ? 0 : dbSalesAmount.intValue();
            if (currSalesAmount != null && currSalesAmount.intValue() != dbSalesAmountInt) {// change sales amount
                stockService.updateTotalStocks(currPosition);
            }

            if (Constants.PLATFORM_HAO123_ID.equals(dbPosition.getAdPlatformId())) {
                PositionMaterialType materialType = currPosition.getMaterialType();
                if (PositionMaterialType.image == materialType
                        || PositionMaterialType.image_and_textlink == materialType) {
                    currPosition.setPositionCode(generatePositionCode(currPosition.getAreaRequired(),
                            dbPosition.getPositionNumber()));
                }
            }

            return positionRepository.updatePositionProperty(currPosition);
        }

        return 0;

    }

    @Override
    public String generatePositionCode(String areaRequired, String positionNumber) {
        String positionCode = null;
        if (areaRequired != null) {
            Pattern pattern = Pattern.compile("[1-9]\\d*[*×x][1-9]\\d*");
            Matcher matcher = pattern.matcher(areaRequired);
            if (matcher.find()) {
                String str = matcher.group();
                String[] numbers = null;
                if (str.indexOf("*") != -1) {
                    numbers = str.split("\\*");
                }

                if (str.indexOf("×") != -1) {
                    numbers = str.split("×");
                }

                if (str.indexOf("x") != -1) {
                    numbers = str.split("x");
                }
                Map<String, Object> valueMap = new HashMap<String, Object>();
                valueMap.put("positionWidth", numbers[0]);
                valueMap.put("positionHeight", numbers[1]);
                valueMap.put("positionId", positionNumber);

                positionCode =
                        VelocityUtil.merge(GcrmConfig.getConfigValueByKey("position.code.templateName.en"), valueMap);
            }
        }

        return positionCode;
    }

    @Override
    public List<Position> findByPositionNumber(String positionNumber, PositionType type) {
        StringBuilder positionConditionStr = new StringBuilder().append("%").append(positionNumber).append("%");
        PageRequest request = new PageRequest(0, 10);
        return positionRepository.findByPositionNumberLikeAndType(positionConditionStr.toString(), type, request);
    }

    @Override
    public Position savePosition(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public void updateStatusByAdPlatformId(Long adPlatformId, Date updateTime, Long updateOpreator,
            PositionStatus status) {
        positionRepository.updateStatusByAdPlatformId(adPlatformId, updateTime, updateOpreator, status);

    }

    @Override
    public Position updatePositionStatus(Position updatePosition, Position dbPosition,
            List<Position> occupationPositionList) {
        Long id = updatePosition.getId();
        Long adPlatformId = dbPosition.getAdPlatformId();
        Long siteId = dbPosition.getSiteId();
        PositionType type = dbPosition.getType();
        updatePosition.setType(type);

        if (!CollectionUtils.isEmpty(occupationPositionList)) {
            if (PositionStatus.enable == updatePosition.getStatus()) {
                for (Position temPosition : occupationPositionList) {
                    positionDateService.enablePositionDate(temPosition);
                }
            } else if (PositionStatus.disable == updatePosition.getStatus()) {
                releaseOccupationByPositions(occupationPositionList);
            }
        }

        dbPosition.setStatus(updatePosition.getStatus());
        dbPosition.setUpdateOperator(updatePosition.getUpdateOperator());
        dbPosition.setUpdateTime(updatePosition.getUpdateTime());
        positionRepository.save(dbPosition);
        // update sub
        if (PositionType.position != type) {
            String indexStr = new StringBuilder().append(dbPosition.getIndexStr()).append(id).append("-%").toString();
            positionRepository.updateStatusByIndexStr(adPlatformId, siteId, indexStr, updatePosition.getUpdateTime(),
                    updatePosition.getUpdateOperator(), updatePosition.getStatus());
        }

        // update parent
        processParentPosition(updatePosition, dbPosition);
        return dbPosition;
    }

    private void processParentPosition(Position updatePosition, Position dbPosition) {
        PositionType currType = updatePosition.getType();
        if (PositionType.channel == currType) {
            return;
        }
        String indexStr = dbPosition.getIndexStr();
        String[] indexArray = indexStr.split("-");
        Long channelId = Long.valueOf(indexArray[2]);
        if (PositionType.position == currType) {
            Long areaId = Long.valueOf(indexArray[3]);
            updateParentStatus(areaId, updatePosition);
        }
        updateParentStatus(channelId, updatePosition);

    }

    private void updateParentStatus(Long parentId, Position updatePosition) {
        PositionStatus currStatus = updatePosition.getStatus();
        if (PositionStatus.disable == currStatus) {
            List<Position> existsSubPositions =
                    positionRepository.findByParentIdAndStatus(parentId, PositionStatus.enable, new PageRequest(0, 1));
            if (CollectionUtils.isEmpty(existsSubPositions)) {
                positionRepository.updateStatusById(parentId, updatePosition.getUpdateTime(),
                        updatePosition.getUpdateOperator(), currStatus);
            }
        } else if (PositionStatus.enable == currStatus) {
            Position parentPosition = positionRepository.findOne(parentId);
            if (PositionStatus.disable == parentPosition.getStatus()) {
                positionRepository.updateStatusById(parentId, updatePosition.getUpdateTime(),
                        updatePosition.getUpdateOperator(), currStatus);
            }
        }
    }

    @Override
    public Integer queryMaxSalesAmount(Long positionId) {
        List<ScheduleStatus> scheduleStatus = new LinkedList<ScheduleStatus>();
        scheduleStatus.add(ScheduleStatus.reserved);
        scheduleStatus.add(ScheduleStatus.confirmed);
        scheduleStatus.add(ScheduleStatus.locked);
        return positionOccupationCommonService.findMaxUsedCountByPositionId(positionId, scheduleStatus);
    }

    @Override
    public void updatePositionName(PositionVO positionVO) {
        Long id = positionVO.getId();
        Position dbPosition = positionRepository.findOne(id);
        PositionI18nVO dbI18n = getPositionI18nVO(Position.class, id);
        String dbCnExtraName = dbI18n.getCnExtraName();
        String dbEnExtraName = dbI18n.getEnExtraName();

        PositionI18nVO currI18nVO = positionVO.getI18nData();
        String currCnName = currI18nVO.getCnName();
        String currEnName = currI18nVO.getEnName();
        boolean changedCnName = false;
        boolean changedEnName = false;
        if (!dbI18n.getCnName().equals(currCnName)) {
            changedCnName = true;
        }
        if (!dbI18n.getEnName().equals(currEnName)) {
            changedEnName = true;
        }

        if (changedCnName) {
            updatePositionName(dbPosition, LocaleConstants.zh_CN, currCnName, dbCnExtraName);
        }
        if (changedEnName) {
            updatePositionName(dbPosition, LocaleConstants.en_US, currEnName, dbEnExtraName);
        }
    }

    private void updatePositionName(Position dbPosition, LocaleConstants locale, String value, String dbExtraName) {
        Long id = dbPosition.getId();
        PositionType type = dbPosition.getType();
        String key = null;
        try {
            key = MessageHelper.generateI18nKey(Position.class, id);
        } catch (CRMBaseException e) {
            logger.error("Position object is not a Persistence object!", e);
        }
        dbExtraName =
                new StringBuilder()
                        .append(dbExtraName.substring(0, dbExtraName.lastIndexOf(InfoDecorator.SPLIT_FLAG) + 1))
                        .append(value).toString();
        i18nService.deleteByKeyAndLocale(key, locale);
        LocaleVO newLocaleVO = new LocaleVO(locale, value);
        newLocaleVO.setExtraValue(dbExtraName);
        i18nService.save(Position.class, id, newLocaleVO);
        String indexStr = new StringBuilder().append(dbPosition.getIndexStr()).append(id).append("-%").toString();
        if (PositionType.channel == type) {
            i18nService.updateSubPositionExtraName(indexStr, PositionType.area, locale);
            i18nService.updateSubPositionExtraName(indexStr, PositionType.position, locale);
        } else if (PositionType.area == type) {
            i18nService.updateSubPositionExtraName(indexStr, PositionType.position, locale);
        }

    }

    @Override
    public List<Position> findByIndexStrAndType(Long id, String indexStr, PositionType type) {
        return positionRepository.findByIndexStrLikeAndType(getQueryIndexStr(id, indexStr), type);
    }

    @Override
    public List<Position> findByIndexStrLikeAndTypeAndPropertyStatus(Long id, String indexStr, PositionType type,
            PositionPropertyStatus propertyStatus) {
        return positionRepository.findByIndexStrLikeAndTypeAndPropertyStatus(getQueryIndexStr(id, indexStr), type,
                propertyStatus);
    }

    @Override
    public List<Position> findByIndexStrLikeAndTypeAndStatus(Long id, String indexStr, PositionType type,
            PositionStatus status) {
        return positionRepository.findByIndexStrLikeAndTypeAndStatus(getQueryIndexStr(id, indexStr), type, status);
    }

    private String getQueryIndexStr(Long id, String indexStr) {
        return new StringBuilder().append(indexStr).append(id).append("-%").toString();
    }

    @Override
    public void releaseOccupationByPositions(List<Position> positionList) {
        if (CollectionUtils.isEmpty(positionList)) {
            return;
        }

        List<Long> positionIds = getPositionIds(positionList);

        List<Long> adContentIds = new ArrayList<Long>();
        List<Long> approvedContentIds = new ArrayList<Long>();
        List<AdSolutionContent> adContents = adContentService.findByPositionIdIn(positionIds);
        if (CollectionUtils.isEmpty(adContents)) {
            return;
        }
        for (AdSolutionContent content : adContents) {
            adContentIds.add(content.getId());
            if (ApprovalStatus.approved.equals(content.getApprovalStatus())) {
                approvedContentIds.add(content.getId());
            }
        }

        processService.terminateProcessByAdContentId(approvedContentIds);
        List<Long> releasedContentIds =
                positionOccupationCommonService.releasePositionOccupation(positionIds, adContentIds);
        adContentService.cancelContents(adContents, CancelReason.disable_position);
        sendPositionDisableMail(releasedContentIds);
    }

    private List<Long> getPositionIds(List<Position> positionList) {
        List<Long> positionIds = new LinkedList<Long>();
        for (Position temExistsPosition : positionList) {
            positionIds.add(temExistsPosition.getId());
        }
        return positionIds;
    }

    private void sendPositionDisableMail(List<Long> adContentIds) {
        if (CollectionUtils.isEmpty(adContentIds)) {
            return;
        }
        final List<PositionDisableContent> allContents =
                positionRepository.findDisableContent(adContentIds, LocaleConstants.en_US);
        if (CollectionUtils.isEmpty(allContents)) {
            return;
        }
        // send mail
        taskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String contentUrlPrefix = GcrmConfig.getConfigValueByKey("app.adcontent.url");
                    for (PositionDisableContent temMailContent : allContents) {
                        Long id = temMailContent.getId();
                        temMailContent.setAdContentURL(new StringBuilder().append(contentUrlPrefix).append(id)
                                .toString());
                        MailHelper.sendPositionDisableMail(temMailContent);
                    }
                } catch (Exception e) {
                    logger.error("taskExecutor sendPositionDisableMail failed!", e);
                }
            }
        });
    }

    @Override
    public List<Position> findByAdPlatformAndStatusAndType(Long adPlatform,
            AdPlatformSiteRelationStatus relationStatus, PositionStatus status, PositionType type) {
        return positionRepository.findByAdPlatformAndStatusAndType(adPlatform, relationStatus, status, type);
    }

    @Override
    public List<Position> findOperateOccupationPosition(Position updatePosition, Position dbPosition) {

        PositionStatus occupationPositionStatus = null;
        if (PositionStatus.enable == updatePosition.getStatus()) {
            occupationPositionStatus = PositionStatus.disable;
        } else {
            occupationPositionStatus = PositionStatus.enable;
        }

        return positionRepository.findByIndexStrLikeAndStatusAndType(dbPosition, occupationPositionStatus,
                PositionType.position);

    }

    @Override
    public Long findPositionOperation(List<Long> positionIdList, Date operateDate) {
        return positionRepository.findPositionOperation(positionIdList, operateDate);
    }

    @Override
    public List<Position> findListBetweenDate(Date startDate, Date endDate, String operateType,
            PositionType positionType) {
        return positionRepository.findListBetweenDate(startDate, endDate, operateType, positionType);
    }

    @Override
    public List<Position> findByType(PositionType type) {
        return positionRepository.findByType(type);
    }

    @Override
    public List<PositionBean> getAllPositions() {
        return positionRepository.getAllPositions();
    }
    
    @Override
    public List<Position> findByStatusAndType(PositionStatus status, PositionType type) {
        return positionRepository.findByStatusAndType(status, type);
    }

    @Override
    public void initPositionCode() {
        List<Position> positionList = positionRepository.findAll();
        for (Position position : positionList) {
            if (Constants.PLATFORM_HAO123_ID.equals(position.getAdPlatformId())) {
                PositionMaterialType materialType = position.getMaterialType();
                if (materialType != null && (PositionMaterialType.image == materialType 
                        || PositionMaterialType.image_and_textlink == materialType)) {
                    position.setPositionCode(generatePositionCode(position.getAreaRequired(), 
                            position.getPositionNumber()));
                }
            }
        }
        positionRepository.save(positionList);
    }
    
    @Override
    public Map<Long, Position> findDisabledPositions() {
        Map<Long, Position> disabledPositions = new HashMap<Long, Position>();
        List<Position> positions = positionRepository.findByStatusAndType(PositionStatus.disable, PositionType.position);
        if (CollectionUtils.isEmpty(positions)) {
            return disabledPositions;
        }
        for (Position position : positions) {
            disabledPositions.put(position.getId(), position);
        }
        return disabledPositions;
    }
}
