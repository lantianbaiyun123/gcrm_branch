package com.baidu.gcrm.publish.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepository;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.material.service.IMaterialManageService;
import com.baidu.gcrm.ad.material.vo.MaterialContentVO;
import com.baidu.gcrm.ad.model.AdvertiseMaterial;
import com.baidu.gcrm.ad.model.AdvertiseMaterialMenu;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.auth.service.IUserDataRightService;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.IPageQuery;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.customer.web.helper.CustomerListBean;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.publish.dao.IForcePublishProofRepository;
import com.baidu.gcrm.publish.dao.IPublishOwnerRepository;
import com.baidu.gcrm.publish.dao.IPublishRepository;
import com.baidu.gcrm.publish.dao.IPublishRepositoryCustom;
import com.baidu.gcrm.publish.model.ForcePublishProof;
import com.baidu.gcrm.publish.model.ForcePublishProof.ProofType;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.Publish.PublishType;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;
import com.baidu.gcrm.publish.model.PublishOwner;
import com.baidu.gcrm.publish.service.IPublishListService;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.publish.web.utils.PublishCondition;
import com.baidu.gcrm.publish.web.utils.PublishCondition.OperateType;
import com.baidu.gcrm.publish.web.utils.PublishDoneCondition;
import com.baidu.gcrm.publish.web.vo.ChannelVO;
import com.baidu.gcrm.publish.web.vo.ConditionVO;
import com.baidu.gcrm.publish.web.vo.OperateVO;
import com.baidu.gcrm.publish.web.vo.PlatformVO;
import com.baidu.gcrm.publish.web.vo.PublishDateListVO;
import com.baidu.gcrm.publish.web.vo.PublishDateVO;
import com.baidu.gcrm.publish.web.vo.PublishDoneListVO;
import com.baidu.gcrm.publish.web.vo.PublishListVO;
import com.baidu.gcrm.publish.web.vo.PublishOwnerListVO;
import com.baidu.gcrm.publish.web.vo.PublishProofVO;
import com.baidu.gcrm.publish.web.vo.SiteVO;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformBusinessType;
import com.baidu.gcrm.resource.position.dao.PositionRepository;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.position.web.vo.PositionVO;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.user.web.vo.UserVO;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;

@Service
public class PublishListServiceImpl implements IPublishListService {
	
	@Autowired
	IPublishRepository publishDao;
	@Autowired
	IPublishRepositoryCustom publishRepositoryCustom;
	@Autowired
	IAdSolutionContentRepository adSolutionContentRepository;
	@Autowired
	AdvertisingPlatformServiceImpl adPlatformService;
	@Autowired
	I18nKVService i18nKVService;
    @Autowired
    IPositionService positionService;
    @Autowired
	ISiteService siteService;
    @Autowired
    PositionRepository positionRepository;
    @Autowired
	IUserService userService;
    @Autowired
    IMaterialManageService materialManageService;
    @Autowired
	IUserDataRightService userDataRightService;
    @Autowired
    IPublishOwnerRepository publishOwnerDao;
    @Autowired
    IPublishService publishService;
    @Autowired
    IForcePublishProofRepository forcePublishProofDao;
    
	private static final String UCID_SPLIT = ",";
	private static final int MAX_HISTORY_RECORD_COUNT = 100;
	
	public Page<PublishListVO> findPublishList(PublishCondition condition,LocaleConstants locale){
		condition.setCurrentOperChannelIds(this.getCurrentOperChannelList());
		condition.setCurrentOperPlatIds(getCurrentOperPlatList());
		Page<PublishListVO> page = publishRepositoryCustom.findPublishListVOByCondition(condition);
		List<PublishListVO> ls = page.getContent();
		processPublishList(ls,locale);
		page.setContent(ls);
		return page;
	}

	private void processPublishList(List<PublishListVO> ls,LocaleConstants locale){
		if(CollectionUtils.isNotEmpty(ls)){
			for(PublishListVO vo : ls){
				StringBuilder sb = new StringBuilder();
				
				if(vo.getProductId() != null){
					AdvertisingPlatform plat = adPlatformService.getByIdAndLocale(
			 				vo.getProductId(), locale);
					if(plat != null){
						sb.append(plat.getI18nName()).append(">");
					}
				}
				
				if(vo.getSiteId() != null){
					sb.append(i18nKVService.getI18Info(
			 				Site.class,vo.getSiteId(),locale)).append(">");
				}
				
				if(vo.getChannelId() != null){
					sb.append(i18nKVService.getI18Info(
			 				Position.class,vo.getChannelId(),locale)).append(">");
				}
				
				if(vo.getAreaId() != null){
					sb.append(i18nKVService.getI18Info(
			         		Position.class,vo.getAreaId(),locale)).append(">");
				}
				
				if(vo.getPositionId() != null){
					sb.append(i18nKVService.getI18Info(
			         		Position.class,vo.getPositionId(),locale)).append(">");
				}
				
				if(sb.length() > 0){
					vo.setPositionName(sb.substring(0, sb.length()-1));
				}
			}
		}
	}
	
	@Override
	public PublishDateListVO findPublishDetailByPublishNumber(String publishNumber, Date operateDate) {
		String odate = null;
		if(operateDate != null){
			odate = DateUtils.getDate2String(DateUtils.YYYY_MM_DD, operateDate);
		}
		
		PublishDateListVO res = new PublishDateListVO();
		Publish publish = publishDao.findByNumber(publishNumber);
		List<PublishDate> list = publishRepositoryCustom.findPublishDateByPublishNumber(publishNumber);
		AdSolutionContent content = adSolutionContentRepository.findOne(publish
					.getAdContentId());
		// 物料
		MaterialContentVO contentVO = materialManageService
				.findAdContVoMaterByContId(content.getId());
		String materialNumber = StringUtils.EMPTY;
		String mNumber = publish.getMaterialNumber();
		if (contentVO != null) {
			materialNumber = contentVO.getMaterialNumber();
			res.setMaterialTitle(contentVO.getMaterialTitle());
			res.setMaterialNumber(materialNumber);
			res.setMaterialUrl(contentVO.getMaterialUrl());
			res.setMaterialFileType(contentVO.getMaterialFileType());
			res.setMaterialType(contentVO.getMaterialType());
			res.setMaterialEmbedCodeContent(contentVO.getMaterialEmbedCodeContent());
			res.setMonitorUrl(contentVO.getMonitorUrl());
			res.setMaterialList(contentVO.getMaterialList());
			if (CollectionUtils.isNotEmpty(res.getMaterialList())) {
			    for (AdvertiseMaterial material : res.getMaterialList()) {
	                String directDownloadUrl = 
	                        material.getDownloadUrl().replaceAll("downloadMaterialFile", "downloadMaterialFileDirect");
	                material.setFileDownloadUrl(directDownloadUrl);
	            }   
			}
			res.setMaterialEmpty(false);
			int materialType = contentVO.isMaterialFull() ? 2 : 1;
			res.setMaterialEmptyType(materialType);
			res.setMaterialMenuList(contentVO.getMaterialMenuList());
			if (CollectionUtils.isNotEmpty(res.getMaterialMenuList())) {
			    for (AdvertiseMaterialMenu materialMenu : res.getMaterialMenuList()) {
	                String directDownloadUrl = materialMenu.getDownloadUrl().replaceAll("false", "true");
	                materialMenu.setFileDownloadUrl(directDownloadUrl);
	            }
			}
		}else{
			res.setMaterialEmpty(true);
			res.setMaterialEmptyType(BigDecimal.ZERO.intValue());
		}
		if (!StringUtils.trimToEmpty(mNumber).equals(StringUtils.trimToEmpty(materialNumber))) {
			publishService.triggerMaterialPublish(publish, materialNumber, Constants.SYSTEM_OPERATOR);
			LoggerHelper.err(getClass(), "查看上线单时，发现物料已更新，更新上线单中物料编号，原编号：{}，新编号：{}", mNumber, materialNumber);
		}
		boolean isMaterialFull = contentVO == null ? false : contentVO.isMaterialFull();
		List<PublishDateVO> publishDate = getListPublishDateVO(list, odate, publish, isMaterialFull, content);
		
		// getProof(publishNumber, res);
		res.setPublishDate(publishDate);
		res.setAdContentId(publish.getAdContentId());
		return res;
	}

	private void getProof(String publishNumber, PublishDateListVO res) {
		List<ForcePublishProof> proofList = forcePublishProofDao.findByPublishNumber(publishNumber);
		
		if(CollectionUtils.isNotEmpty(proofList)){
			Map<ProofType,List<PublishProofVO>> proof = new HashMap<ForcePublishProof.ProofType, List<PublishProofVO>>();
			Long recordId = null;
			List<PublishProofVO> result =  new ArrayList<PublishProofVO>();
			ProofType type = null;
			for(ForcePublishProof obj : proofList){
				if(recordId == null){
					recordId = obj.getRecordId();
					type = obj.getType();
				}else if(!recordId.equals(obj.getRecordId())){
					break;
				}
				PublishProofVO vo = new PublishProofVO();
				vo.setId(obj.getId());
				if(ProofType.online_approval.equals(obj.getType())){
					vo.setApprovalUrl(obj.getApprovalUrl());
				}
				if(ProofType.offline_attachment.equals(obj.getType())){
					vo.setDownloadFileUrl(getProofDownLoadUrl(obj.getId()));
					vo.setDownloadFilename(obj.getDownloadFilename());
				}
				result.add(vo);
			}
			if(ProofType.none.equals(type)){
				proof.put(type, new ArrayList<PublishProofVO>());
			}else{
				proof.put(type, result);
			}
			res.setProof(proof);
		}
	}

	private List<PublishDateVO> getListPublishDateVO(List<PublishDate> list, String odate, Publish publish,
			boolean isMaterialFull, AdSolutionContent content) {
		List<PublishDateVO> publishDate = new ArrayList<PublishDateVO>();
		for (PublishDate date : list) {
			PublishDateVO vo = new PublishDateVO();
			convertPublishDateToVO(date, vo);
 
			if(StringUtils.isBlank(odate)){
				String currDate = DateUtils.getDate2String(DateUtils.YYYY_MM_DD, new Date());
				if (PublishType.normal.equals(publish.getType())
						&& date.getStatus().equals(PublishPeriodStatus.not_start)
						&& getDateCompareResult(date.getPlanStart(), currDate) <= 0
						&& getDateCompareResult(date.getPlanEnd(), currDate) >= 0) {
					vo.setShowOnline(isMaterialFull);
				} else if (date.getStatus().equals(PublishPeriodStatus.ongoing)) {
					vo.setShowOffline(true);
				} /*else if (PublishType.force.equals(publish.getType())
						&& date.getStatus().equals(PublishPeriodStatus.not_start)
						&& getDateCompareResult(date.getPlanStart(), currDate) <= 0
						&& getDateCompareResult(date.getPlanEnd(), currDate) >= 0) {
					if (userDataRightService.isUserDataByRoleAndDataType(
							RequestThreadLocal.getLoginUserId(), "pm",
							DescriptionType.platform, content.getProductId()
									.toString()) && isMaterialFull) {
						vo.setShowForceOnline(true);
					}
				}*/
			}else{
				if (DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD).equals(odate)) {// 今天
					if (PublishType.normal.equals(publish.getType())
							&& date.getStatus().equals(PublishPeriodStatus.not_start)
							&& getDateCompareResult(date.getPlanStart(), odate) <= 0
							&& getDateCompareResult(date.getPlanEnd(), odate) >= 0) {
						vo.setCountOnline(true);
						vo.setShowOnline(isMaterialFull);
					} else if (date.getStatus().equals(PublishPeriodStatus.ongoing)
							&& getDateCompareResult(date.getPlanEnd(), odate) <= 0) {
						vo.setCountOffline(true);
						vo.setShowOffline(true);
					} /*else if (PublishType.force.equals(publish.getType())
							&& date.getStatus().equals(PublishPeriodStatus.not_start)
							&& getDateCompareResult(date.getPlanStart(), odate) <= 0
							&& getDateCompareResult(date.getPlanEnd(), odate) >= 0) {
						vo.setCountForceOnline(true);
						if (userDataRightService.isUserDataByRoleAndDataType(
								RequestThreadLocal.getLoginUserId(), ParticipantConstants.pm_leader.name(),
								DescriptionType.platform, content.getProductId()
										.toString()) && isMaterialFull) {
							vo.setShowForceOnline(true);
						}
					}*/
				}

				if (DateUtils.getDate2String(DateUtils.YYYY_MM_DD, DateUtils.getTomorrowDate()).equals(odate)) {// 明天
					if (PublishType.normal.equals(publish.getType())
							&& date.getStatus().equals(PublishPeriodStatus.not_start)
							&& getDateCompareResult(date.getPlanStart(), odate) == 0) {
						vo.setCountOnline(true);
						vo.setShowOnline(false);
					} else if (date.getStatus().equals(PublishPeriodStatus.ongoing)
							&& getDateCompareResult(date.getPlanEnd(), odate) == 0) {
						vo.setCountOffline(true);
						vo.setShowOffline(true);
					} /*else if (PublishType.force.equals(publish.getType())
							&& date.getStatus().equals(PublishPeriodStatus.not_start)
							&& getDateCompareResult(date.getPlanStart(), odate) == 0) {
						vo.setCountForceOnline(true);
						vo.setShowForceOnline(false);
					}*/
				}

				if (DateUtils.getDate2String(DateUtils.YYYY_MM_DD, DateUtils.getAfterTomorrowDate()).equals(odate)) {// 明天之后
					if (PublishType.normal.equals(publish.getType())
							&& date.getStatus().equals(PublishPeriodStatus.not_start)
							&& getDateCompareResult(date.getPlanStart(), odate) >= 0) {
						vo.setCountOnline(true);
						vo.setShowOnline(false);
					} else if (date.getStatus().equals(PublishPeriodStatus.ongoing)
							&& getDateCompareResult(date.getPlanEnd(), odate) >= 0) {
						vo.setCountOffline(true);
						vo.setShowOffline(true);
					} /*else if (PublishType.force.equals(publish.getType())
							&& date.getStatus().equals(PublishPeriodStatus.not_start)
							&& getDateCompareResult(date.getPlanStart(), odate) >= 0) {
						vo.setCountForceOnline(true);
						vo.setShowForceOnline(false);
					}*/
				}
			}
			publishDate.add(vo);
		}

		return publishDate;
	}
	
	private int getDateCompareResult(Date date1, String date2) {
		return DateUtils.compareDate(date1, DateUtils.getString2Date(date2), DateUtils.YYYY_MM_DD);
	}
	
	private String getProofDownLoadUrl(Long id){
		String moduleName = "/gcrm";
		StringBuffer requestURL = RequestThreadLocal.getRequestURL();
		int endPos = requestURL.indexOf(moduleName);
		StringBuffer url = new StringBuffer();
		url.append(requestURL.substring(0, endPos + moduleName.length()));
		url.append("/publish/downProofMaterial/");
		url.append(id);
		return url.toString();
	}
	
	@Override
	public PublishDateListVO findPublishDatesByPublishAndMaterialNumber(String publishNumber, String materialNumber) {
		PublishDateListVO res = new PublishDateListVO();
		Publish publish = publishDao.findByNumber(publishNumber);
		String newMaterialNumber = publish.getMaterialNumber();
		List<PublishDate> list = publishRepositoryCustom.findPublishDateByPublishNumber(publishNumber);
		// 物料
		MaterialContentVO contentVO = StringUtils.isEmpty(materialNumber) ? null : materialManageService.findAdContVoMaterByMaterNumber(materialNumber);
		if (contentVO != null) {
			res.setMaterialNumber(materialNumber);
			res.setMaterialTitle(contentVO.getMaterialTitle());
			res.setMaterialNumber(materialNumber);
			res.setMaterialUrl(contentVO.getMaterialUrl());
			res.setMaterialFileType(contentVO.getMaterialFileType());
			res.setMaterialType(contentVO.getMaterialType());
			res.setMaterialEmbedCodeContent(contentVO.getMaterialEmbedCodeContent());
			res.setMonitorUrl(contentVO.getMonitorUrl());
			res.setMaterialList(contentVO.getMaterialList());
			res.setMaterialEmpty(false);
		}else{
			res.setMaterialEmpty(true);
		}
		List<PublishDateVO> publishDate = convertPublishDateToVO(list);
		
		// getProof(publishNumber, res);
		res.setPublishDate(publishDate);
		res.setAdContentId(publish.getAdContentId());
		if (!StringUtils.trimToEmpty(newMaterialNumber).equals(materialNumber)) {
			res.setMaterialOld(true);
		}
		return res;
	}
	
	private List<PublishDateVO> convertPublishDateToVO(List<PublishDate> list){
		List<PublishDateVO> publishDate = new ArrayList<PublishDateVO>();
		for(PublishDate date : list){
			PublishDateVO vo = new PublishDateVO();
			convertPublishDateToVO(date, vo);
			publishDate.add(vo);
		}
		
		return publishDate;
	}

	private void convertPublishDateToVO(PublishDate date, PublishDateVO vo) {
		vo.setId(date.getId());
		vo.setActuralEnd(date.getActuralEnd());
		vo.setActuralStart(date.getActuralStart());
		vo.setPlanEnd(date.getPlanEnd());
		vo.setPlanStart(date.getPlanStart());
		vo.setPublishNumber(date.getPublishNumber());
		
		if(date.getEndOperator() != null){
			vo.setEndOperator(userService.findByUcid(date.getEndOperator()).getRealname());
		}
		if(date.getStartOperator() != null){
			vo.setStartOperator(userService.findByUcid(date.getStartOperator()).getRealname());
		}
	}
	
	@Override
	public Page<PublishDoneListVO> findPublishDoneList(
			PublishDoneCondition condition, LocaleConstants locale) {
		
	    condition.setCurrentOperChannelIds(this.getCurrentOperChannelList());
		condition.setCurrentOperPlatIds(this.getCurrentOperPlatList());
//		
		Page<PublishDoneListVO> page= publishRepositoryCustom.findPublishDoneList(condition);
		
		for(PublishDoneListVO vo : page.getContent()){
			StringBuilder sb = new StringBuilder();
			
			if(vo.getProductId() != null){
				AdvertisingPlatform plat = adPlatformService.getByIdAndLocale(
		 				vo.getProductId(), locale);
				if(plat != null){
					sb.append(plat.getI18nName()).append("/");
				}
			}
			
			if(vo.getSiteId() != null){
				sb.append(i18nKVService.getI18Info(
		 				Site.class,vo.getSiteId(),locale)).append("/");
			}
			
			if(vo.getChannelId() != null){
				sb.append(i18nKVService.getI18Info(
		 				Position.class,vo.getChannelId(),locale)).append("/");
			}
			
			if(vo.getAreaId() != null){
				sb.append(i18nKVService.getI18Info(
		         		Position.class,vo.getAreaId(),locale)).append("/");
			}
			
			if(vo.getPositionId() != null){
				sb.append(i18nKVService.getI18Info(
		         		Position.class,vo.getPositionId(),locale)).append("/");
			}
			
			if(sb.length() > 0){
				vo.setPositionName(sb.substring(0, sb.length()-1));
			}
		
		}

		return page;
	}

	
	
	@Override
	public Map<String,ConditionVO> findConditionResult(PublishCondition condition, LocaleConstants locale) {
		List<String> dates =  new ArrayList<String>();
		dates.add(DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD));
		dates.add(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, DateUtils.getTomorrowDate()));
		dates.add(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, DateUtils.getAfterTomorrowDate()));
		Map<String,ConditionVO> result = new HashMap<String, ConditionVO>();
		ConditionVO today = new ConditionVO();
		ConditionVO tomorrow = new ConditionVO();
		ConditionVO afterTomorrow = new ConditionVO();
		
		for (String date : dates) {
			condition.setOperateDate(date);
			condition.setPageSize(Integer.MAX_VALUE);
			condition.setPageNumber(1);
			condition.setOperateType(OperateType.all);
			List<PublishListVO> ls = findPublishList(condition, locale).getContent();
			Map<Long, AdSolutionContent> contents = getContentMap(ls);
			Map<String, Publish> publishMap = getPublishMap(ls);
            Date operateDate = DateUtils.getString2Date(DateUtils.YYYY_MM_DD, date);
			if (DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD).equals(
					date)) {// today
				today = processConditions(ls, locale, operateDate, contents, publishMap);
			} else if (DateUtils.getDate2String(DateUtils.YYYY_MM_DD,DateUtils.getTomorrowDate()).equals(date)) {// tomorrow
				tomorrow = processConditions(ls, locale, operateDate, contents, publishMap);
			} else {// afterTomorrow
				afterTomorrow = processConditions(ls, locale, operateDate, contents, publishMap);
			} 
		}
		result.put("today", today);
		result.put("tomorrow", tomorrow);
		result.put("afterTomorrow", afterTomorrow);
		return result;
	}
	
	private Map<String, Publish> getPublishMap(List<PublishListVO> publishList) {
		Map<String, Publish> publishMap = new HashMap<String, Publish>();
		if (CollectionUtils.isEmpty(publishList)) {
			return publishMap;
		}
		Set<String> publishNumbers = new HashSet<String>();
		for (PublishListVO publishListVO : publishList) {
			publishNumbers.add(publishListVO.getNumber());
		}
		List<Publish> publishes = publishDao.findByNumberIn(publishNumbers);
		if (CollectionUtils.isEmpty(publishes)) {
			return publishMap;
		}
		for (Publish publish : publishes) {
			publishMap.put(publish.getNumber(), publish);
		}
		return publishMap;
	}

	private Map<Long, AdSolutionContent> getContentMap(List<PublishListVO> publishList) {
		Map<Long, AdSolutionContent> contents = new HashMap<Long, AdSolutionContent>();
		if (CollectionUtils.isEmpty(publishList)) {
			return contents;
		}
		List<String> adContentNumbers = new ArrayList<String>();
		for (PublishListVO publishListVO : publishList) {
			adContentNumbers.add(publishListVO.getAdContentNumber());
		}
		List<AdSolutionContent> contentList = adSolutionContentRepository.findByNumberIn(adContentNumbers);
		if (CollectionUtils.isEmpty(contentList)) {
			return contents;
		}
		for (AdSolutionContent content : contentList) {
			contents.put(content.getId(), content);
		}
		return contents;
	}

	private ConditionVO processConditions(List<PublishListVO> ls, LocaleConstants locale,
			Date operateDate, Map<Long, AdSolutionContent> contents, Map<String, Publish> publishMap) {
		ConditionVO conditionVO = new ConditionVO();
		List<OperateVO> operate = new ArrayList<OperateVO>();
		Map<String, PlatformVO> platMap = getPlatMap(locale);
		Integer online = 0;
		Integer offline = 0;
		Integer matarial = 0;

		if (CollectionUtils.isNotEmpty(ls)) {
			for (PublishListVO vo : ls) {
				List<PublishDateVO> publishDate = getPublishDates(vo.getNumber(), operateDate, contents, publishMap);
				for (PublishDateVO date : publishDate) {
					if (date.isCountForceOnline() || date.isCountOnline()) {
						online++;
						continue;
					}

					if (date.isCountOffline()) {
						offline++;
						continue;
					}
				}
				if(PublishType.material.equals(vo.getType()) 
						&& DateUtils.compareDate(new Date(), operateDate, DateUtils.YYYY_MM_DD)==0){//今天
					matarial = matarial + 1;
				}
				
				if (platMap.containsKey(vo.getProductId().toString())) {
					PlatformVO plat = platMap.get(vo.getProductId().toString());
					Map<String, SiteVO> siteMap = plat.getSite();
					if (siteMap.containsKey(vo.getSiteId().toString())) {
						SiteVO sit = siteMap.get(vo.getSiteId().toString());

						Map<String, ChannelVO> channelMap = sit.getChannel();
						if (channelMap.containsKey(vo.getChannelId().toString())) {
							ChannelVO channe = channelMap.get(vo.getChannelId().toString());
							channe.setTotal(channe.getTotal() + 1);
							channelMap.put(vo.getChannelId().toString(), channe);
						}

						sit.setTotal(sit.getTotal() + 1);
						sit.setChannel(channelMap);
						siteMap.put(vo.getSiteId().toString(), sit);
					}
					plat.setTotal(plat.getTotal() + 1);
					plat.setSite(siteMap);
					platMap.put(vo.getProductId().toString(), plat);
				}
			}
		}
		operate.add(getOperateVO(0, OperateType.all));
		operate.add(getOperateVO(online, OperateType.online));
		operate.add(getOperateVO(offline, OperateType.offline));
		operate.add(getOperateVO(matarial, OperateType.materialChange));

		conditionVO.setTotal(ls.size());
		conditionVO.setOperate(operate);
		conditionVO.setPlatformList(platMap);
		return conditionVO;
	}
	
	private List<PublishDateVO> getPublishDates(String publishNumber, Date operateDate, Map<Long, AdSolutionContent> contents, Map<String, Publish> publishMap) {
		if (operateDate == null) {
			operateDate = new Date();
		}
		String odate = DateUtils.getDate2String(DateUtils.YYYY_MM_DD, operateDate);
		Publish publish = publishMap.get(publishNumber);
		List<PublishDate> list = publishRepositoryCustom.findPublishDateByPublishNumber(publishNumber);
		AdSolutionContent content = contents.get(publish.getAdContentId());
		// 物料
		List<PublishDateVO> publishDate = getListPublishDateVO(list, odate, publish, materialManageService.isMaterFullByPosMaterType(content.getId()), content);

		return publishDate;
	}
	
	private OperateVO getOperateVO(Integer total,OperateType operateType){
		OperateVO vo =  new OperateVO();
		vo.setTotal(total);
		vo.setName(operateType);
		return vo;
	}
	
	//初始化当前人员的平台
	private Map<String,PlatformVO> getPlatMap(LocaleConstants locale){
		Map<String,PlatformVO> platMap = new HashMap<String, PlatformVO>();
        List<AdvertisingPlatform> platList = adPlatformService.getAllByLocale(locale);
		//初始化plat
		for(AdvertisingPlatform plat : platList){
			if(AdPlatformBusinessType.liquidate.ordinal() == plat.getBusinessType()){
				PlatformVO vo = new PlatformVO();
				vo.setTotal(0);
				vo.setName(plat.getI18nName());
				
				Map<String,SiteVO> siteMap = new HashMap<String, SiteVO>();
				
				List<Site> siteAllList = siteService.findSiteByAdPlatform(plat.getId(), locale);
				for(Site site : siteAllList){
					SiteVO s = new SiteVO();
					s.setTotal(0);
					s.setName(site.getI18nName());
					
					Map<String,ChannelVO> channelMap = new HashMap<String, ChannelVO>();
					List<PositionVO> allPosition = positionService.findChannelBySiteIdAndStatus(plat.getId(), site.getId(), locale,
				            PositionStatus.enable);
					for(PositionVO position : allPosition){
						ChannelVO channel = new ChannelVO();
						channel.setName(position.getI18nName());
						channel.setTotal(0);
						channel.setSelected(true);
						channelMap.put(position.getId().toString(),channel);
					}
					s.setChannel(channelMap);
					siteMap.put(site.getId().toString(), s);
				}
				vo.setSite(siteMap);
				platMap.put(plat.getId().toString(), vo);
			}
		}
		return platMap;
	}

	@Override
	public List<PublishOwnerListVO> findPublishOwnerListByPlatformId(
			Long platformId,LocaleConstants locale) {
		Map<Long,UserVO> userMap = getAllUser();
		
		List<PublishOwnerListVO> result = new ArrayList<PublishOwnerListVO>();
		
		List<Long> channelIdList = getChannelIdList(result,platformId,locale);
		
		Map<Long,PublishOwner> publishOwnerMap = findPublishOwner(channelIdList);
		
		if(CollectionUtils.isNotEmpty(result)){
			for(PublishOwnerListVO vo : result){
				if(publishOwnerMap.containsKey(vo.getChannelId())){
					PublishOwner owner = publishOwnerMap.get(vo.getChannelId());
					vo.setId(owner.getId());
					String oper = owner.getOwner();
					if(StringUtils.isNotBlank(oper)){
						String[] ucids = oper.split(UCID_SPLIT);
						List<UserVO> user = new ArrayList<UserVO>();
						for(int i = 0; i < ucids.length; i++){
							String ucid = ucids[i];
							if(StringUtils.isNotBlank(ucid) && userMap.containsKey(Long.valueOf(ucid))){
								user.add(userMap.get(Long.valueOf(ucid)).clone());
							}
						}
						vo.setUser(user);
					}
				}
			}
		}
		return result;
	}
	
	private List<Long> getChannelIdList(List<PublishOwnerListVO> result,Long platformId,LocaleConstants locale){
		List<Long> channelIdList = new ArrayList<Long>();
		List<Site> siteAllList = siteService.findSiteByAdPlatform(platformId, locale);
		for(Site site : siteAllList){
			List<PositionVO> allPosition = positionService.findChannelBySiteIdAndStatus(platformId, site.getId(), locale,
		            PositionStatus.enable);
			for(PositionVO position : allPosition){
				PublishOwnerListVO vo = new PublishOwnerListVO();
				vo.setChannelName(position.getI18nName());
				vo.setChannelId(position.getId());
				vo.setSiteName(site.getI18nName());
				
				channelIdList.add(position.getId());
				
				result.add(vo);
			}
		}
		return channelIdList;
	}
	
	private Map<Long,UserVO> getAllUser(){
		Map<Long,UserVO> res = new HashMap<Long, UserVO>();
		List<User> all = userService.findAll();
		if(CollectionUtils.isNotEmpty(all)){
			for (User user : all) {
				UserVO vo = new UserVO();
				vo.setUcid(user.getUcid());
				vo.setUsername(user.getUsername());
				res.put(user.getUcid(), vo);
			}
		}
		return res;
	}
	
	private Map<Long,PublishOwner> findPublishOwner(List<Long> channelIdList){
		Map<Long,PublishOwner> res = new HashMap<Long,PublishOwner>();
		if(CollectionUtils.isEmpty(channelIdList)){
			return res;
		}
		List<PublishOwner> result = publishOwnerDao.findByPositionIdIn(channelIdList);
		if(CollectionUtils.isNotEmpty(result)){
			for(PublishOwner owner : result){
				res.put(owner.getPositionId(), owner);
			}
		}
		return res;
	}

	@Override
	public void deletePublishOwner(Long id, Long ucid) {
		if(id == null || ucid == null){
			return;
		}
		PublishOwner publishOwner = publishOwnerDao.findOne(id);
		String owner = publishOwner.getOwner();
		if(StringUtils.isNotBlank(owner)){
			if(owner.startsWith(ucid.toString() + UCID_SPLIT)){
				owner = owner.replaceFirst(ucid.toString() + UCID_SPLIT, "");
			}else if(owner.contains(UCID_SPLIT + ucid.toString() + UCID_SPLIT)){
				owner = owner.replace(ucid.toString() + UCID_SPLIT, "");
			}
			publishOwner.setOwner(owner);
		}
		publishOwnerDao.save(publishOwner);
	}

	@Override
	public Long addPublishOwner(Long id, Long ucid,Long positionId) {
		Long res = null;
		if(id == null){
			PublishOwner publishOwner = new PublishOwner();
			publishOwner.setOwner(ucid.toString() + UCID_SPLIT);
			publishOwner.setPositionId(positionId);
			publishOwnerDao.save(publishOwner);
			res = publishOwner.getId();
		}else{
			PublishOwner publishOwner = publishOwnerDao.findOne(id);
			String owner = publishOwner.getOwner();
			if(!owner.startsWith(ucid.toString() + UCID_SPLIT) && 
					!owner.contains(UCID_SPLIT + ucid.toString() + UCID_SPLIT)){
				owner = owner.concat(ucid.toString()).concat(UCID_SPLIT);
				if(owner.startsWith(UCID_SPLIT)){
					owner = owner.replaceFirst(UCID_SPLIT, "");
				}
				publishOwner.setOwner(owner);
				publishOwnerDao.save(publishOwner);
			}
			res = publishOwner.getId();
		}
		return res;
	}
	
	private List<Long> getCurrentOperChannelList(){
		List<Long> channelIdList = new ArrayList<Long>();
		List<PublishOwner> all = publishOwnerDao.findAll();
		Long ucid = RequestThreadLocal.getLoginUserId();
		for(PublishOwner obj : all){
			String owner = obj.getOwner();
			if(StringUtils.isNotBlank(owner)){
				if(owner.startsWith(ucid.toString() + UCID_SPLIT) ||
						owner.contains(UCID_SPLIT + ucid.toString() + UCID_SPLIT)){
					channelIdList.add(obj.getPositionId());
				}
			}
		}
		return channelIdList;
	}
	
	public boolean checkAddUcidRe(Long id, Long ucid){
		if(id != null){
			PublishOwner publishOwner = publishOwnerDao.findOne(id);
			String owner = publishOwner.getOwner();
			if(owner.startsWith(ucid.toString() + UCID_SPLIT) ||
					owner.contains(UCID_SPLIT + ucid.toString() + UCID_SPLIT)){
				return true;
			}
		}
		return false;
	}
	
	private List<Long> getCurrentOperPlatList(){
		List<Long> platList = new ArrayList<Long>();
		List<AdvertisingPlatform> all = userDataRightService.getPlatformListByUcId(RequestThreadLocal.getLoginUserId());
		for(AdvertisingPlatform obj : all){
			platList.add(obj.getId());
		}
		return platList;
	}
}
