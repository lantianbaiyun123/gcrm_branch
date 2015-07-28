package com.baidu.gcrm.ad.approval.record.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.approval.record.dao.IApprovalInsertRecordRepository;
import com.baidu.gcrm.ad.approval.record.dao.IApprovalRecordRepository;
import com.baidu.gcrm.ad.approval.record.model.ApprovalInsertRecord;
import com.baidu.gcrm.ad.approval.record.model.ApprovalRecord;
import com.baidu.gcrm.ad.approval.record.service.IApprovalRecordService;
import com.baidu.gcrm.ad.approval.record.web.vo.ApprovalInsertRecordJsonVO;
import com.baidu.gcrm.ad.approval.record.web.vo.ApprovalRecordVO;
import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepository;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.model.AdSolutionContent.ApprovalStatus;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.dao.AdvertiseSolutionRepository;
import com.baidu.gcrm.ad.material.service.IMaterialManageService;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.AdvertiseSolutionApproveState;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.bpm.model.ActivityNameI18n;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gcrm.bpm.vo.ProcessQueryBean;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.CompleteAdPlanActivityReq;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.occupation1.service.IPositionDateRelationService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gwfp.ws.exception.GWFPException;

@Service
public class ApprovalRecordServiceImpl implements IApprovalRecordService {

	@Autowired
	IAdSolutionContentRepository contentRepository;

    @Autowired
    IAdSolutionContentService contentService;

	@Autowired
	IApprovalRecordRepository approvalRecordRepository;

	@Autowired
	IApprovalInsertRecordRepository approvalInsertRecordRepository;

	@Autowired
	private AdvertiseSolutionRepository adSolutionDao;

	@Autowired
	IBpmProcessService processService;

	@Autowired
	IUserService userService;

	@Autowired
	private IPositionDateRelationService relationService;

	@Autowired
	@Qualifier("adPlanProcessServiceImpl")
	IBpmProcessStartService adPlanProcessService;

	@Autowired
	private IAdvertiseSolutionService adService;
	
	@Autowired
	IMaterialManageService materialManageService;

    @Autowired
    IGCrmTaskInfoService gcrmTaskInfoService;
	
	@Override
	public void saveApprovalRecordVO(ApprovalRecordVO vo) {
		ApprovalRecord approvalRecord = vo.getRecord();
		approvalRecord.setActivityId(vo.getActDefId());
		approvalRecordRepository.save(approvalRecord);

		List<ApprovalInsertRecord> insertRecords = vo.getInsertRecords();
		if (CollectionUtils.isEmpty(insertRecords)) {
			return;
		}
		Long approvalRecordId = approvalRecord.getId();
		for (ApprovalInsertRecord approvalInsertRecord : insertRecords) {
			approvalInsertRecord.setApprovalRecordId(approvalRecordId);
			approvalInsertRecordRepository.save(approvalInsertRecord);
		}
	}

	@Override
	public List<ApprovalRecord> findByAdSolutionId(Long adSolutionId, String processDefId, LocaleConstants currentLocale) {
		List<ApprovalRecord> approvalRecordList = approvalRecordRepository.findByAdSolutionId(adSolutionId);
		if (currentLocale != null && !CollectionUtils.isEmpty(approvalRecordList)) {
			processTaskName(processDefId, approvalRecordList, currentLocale);
		}
		processOperatorName(approvalRecordList);
		return approvalRecordList;
	}

	@Override
	public List<ApprovalRecord> findByContentId(Long adContentId, String processDefId, LocaleConstants currentLocale) {
		List<ApprovalRecord> approvalRecordList = approvalRecordRepository.findContentApprovalReord(adContentId);
		if (currentLocale != null && !CollectionUtils.isEmpty(approvalRecordList)) {
			processTaskName(processDefId, approvalRecordList, currentLocale);
		}
		processOperatorName(approvalRecordList);

		return approvalRecordList;
	}

	private void processOperatorName(List<ApprovalRecord> approvalRecordList) {
		if (CollectionUtils.isEmpty(approvalRecordList)) {
			return;
		}
		Set<Long> ucids = new HashSet<Long>();
		for (ApprovalRecord temApprovalRecord : approvalRecordList) {
			Long operatorId = temApprovalRecord.getCreateOperator();
			if (operatorId != null) {
				ucids.add(temApprovalRecord.getCreateOperator());
			}
		}
		if (CollectionUtils.isEmpty(ucids)) {
			return;
		}
		Map<Long, User> userMap = userService.findByUcidIn(ucids);
		if (userMap != null && userMap.size() > 0) {
			for (ApprovalRecord tempApprovalRecord : approvalRecordList) {
				Long operatorId = tempApprovalRecord.getCreateOperator();
				User temUser = userMap.get(operatorId);
				if (temUser != null) {
					tempApprovalRecord.setCreateOperatorName(temUser.getRealname());
				}
			}
		}

	}

	private void processTaskName(String processDefId, List<ApprovalRecord> approvalRecordList, LocaleConstants locale) {
		List<String> activityIds = new ArrayList<String>();
		for (ApprovalRecord temApprovalRecord : approvalRecordList) {
			String temActivityId = temApprovalRecord.getActivityId();
			if (!PatternUtil.isBlank(temActivityId)) {
				activityIds.add(temActivityId);
			}
		}
		if (CollectionUtils.isEmpty(activityIds)) {
			return;
		}

		// query task name
		List<ActivityNameI18n> activityNameI18nList = processService.findActivityNameI18n(processDefId, activityIds,
				locale);
		Map<String, String> activityNameMap = new HashMap<String, String>();
		for (ActivityNameI18n temActivityNameI18n : activityNameI18nList) {
			String activityId = temActivityNameI18n.getActivityId();
			if (activityNameMap.get(activityId) == null) {
				activityNameMap.put(activityId, temActivityNameI18n.getActivityName());
			}
		}

		for (ApprovalRecord temApprovalRecord : approvalRecordList) {
			String temActivityId = temApprovalRecord.getActivityId();
			temApprovalRecord.setTaskName(activityNameMap.get(temActivityId));
		}
	}

	@Override
	public Collection<ApprovalInsertRecordJsonVO> findByApprovalRecordId(Long approvalRecordId) {
		List<ApprovalInsertRecord> insertRecordList = approvalInsertRecordRepository
				.findByApprovalRecordId(approvalRecordId);
		if (CollectionUtils.isEmpty(insertRecordList)) {
			return null;
		}
		Map<String, AdSolutionContent> adContentMap = getAdSolutionContent(insertRecordList);
		Map<String, ApprovalInsertRecordJsonVO> jsonMap = new HashMap<String, ApprovalInsertRecordJsonVO>();
		for (ApprovalInsertRecord approvalInsertRecord : insertRecordList) {
			String adSolutionContentIdStr = approvalInsertRecord.getAdsolutionContentId().toString();
			String insertedAdSolutionContentIdStr = approvalInsertRecord.getInsertedAdsolutionContentId().toString();
			AdSolutionContent insertedAdSolutionContent = adContentMap.get(insertedAdSolutionContentIdStr);

			ApprovalInsertRecordJsonVO currVO = jsonMap.get(adSolutionContentIdStr);
			if (currVO == null) {
				AdSolutionContent adSolutionContent = adContentMap.get(adSolutionContentIdStr);
				currVO = new ApprovalInsertRecordJsonVO();
				currVO.setAdSolutionContentId(adSolutionContent.getId());
				currVO.setAdSolutionContentName(adSolutionContent.getDescription());
				currVO.setAdSolutionContentNumber(adSolutionContent.getNumber());
				jsonMap.put(adSolutionContentIdStr, currVO);
			}

			List<ApprovalInsertRecord> existsInsertedRecordList = currVO.getInsertedAdSolutionContent();
			if (CollectionUtils.isEmpty(existsInsertedRecordList)) {
				existsInsertedRecordList = new ArrayList<ApprovalInsertRecord>();
				currVO.setInsertedAdSolutionContent(existsInsertedRecordList);
			}

			approvalInsertRecord.setInsertedAdvertiser(insertedAdSolutionContent.getAdvertiser());
			approvalInsertRecord.setAdSolutionContentNumber(insertedAdSolutionContent.getNumber());
			existsInsertedRecordList.add(approvalInsertRecord);

		}

		return jsonMap.values();
	}

	private Map<String, AdSolutionContent> getAdSolutionContent(List<ApprovalInsertRecord> insertRecordList) {
		Map<String, AdSolutionContent> contentMap = new HashMap<String, AdSolutionContent>();
		Set<Long> adSolutionContentIds = new HashSet<Long>();
		for (ApprovalInsertRecord temApprovalInsertRecord : insertRecordList) {
			adSolutionContentIds.add(temApprovalInsertRecord.getInsertedAdsolutionContentId());
			adSolutionContentIds.add(temApprovalInsertRecord.getAdsolutionContentId());
		}
		Iterable<AdSolutionContent> adSolutionContents = contentRepository.findAll(adSolutionContentIds);
		for (AdSolutionContent temAdSolutionContent : adSolutionContents) {
			String contentIdStr = temAdSolutionContent.getId().toString();
			if (contentMap.get(contentIdStr) == null) {
				contentMap.put(contentIdStr, temAdSolutionContent);
			}
		}

		return contentMap;
	}

	@Override
	public Integer checkBusinessAllow(Long adsolutionContentId, Long insertedAdsolutionContentId, String taskId) {
		List<ApprovalInsertRecord> insertRecordList = approvalInsertRecordRepository.findApprovalInsertRecord(
				adsolutionContentId, insertedAdsolutionContentId, taskId);

		if (insertRecordList == null || insertRecordList.size() == 0) {
			return -1;
		}

		for (ApprovalInsertRecord insertRecord : insertRecordList) {
			if (insertRecord != null && insertRecord.getAllowInsert() <= 0) {
				return 0;
			}
		}

		return 1;
	}
	@Override
	public List<ApprovalInsertRecord> findContentInsertRecord(Long adsolutionContentId, String activityId) {
		return approvalInsertRecordRepository.findContentInsertRecord(adsolutionContentId, activityId);
	}
	@Override
	public ApprovalRecord findByTaskId(String taskId) {
		return approvalRecordRepository.findByTaskId(taskId);
	}

	private void updateHistoryRecord(ApprovalRecord record) {
		if (record.getAdContentId() != null) {
			approvalInsertRecordRepository.updateAdContentHistoryRecord(record.getAdContentId());
		} else {
			List<Long> contentIds = new ArrayList<Long>();
			List<AdSolutionContent> contentList = contentRepository.findByAdSolutionId(record.getAdSolutionId());
			for (AdSolutionContent content : contentList) {
				contentIds.add(content.getId());
			}
			approvalInsertRecordRepository.updateToHistoryRecordInAdContentIds(contentIds);
		}
	}

	@Override
	public void saveAndCompleteApproval(ApprovalRecordVO approvalRecordVO) {
		boolean refused = false;
		ApprovalRecord record = approvalRecordVO.getRecord();
		Integer approvalStatus = record.getApprovalStatus();
		if (isRefused(approvalStatus)) {
			updateHistoryRecord(record);
			changeToRefuseStatusAndRemoveRelationship(record);
			refused = true;
		}
		saveApprovalRecordVO(approvalRecordVO);
		String excuteUsername = RequestThreadLocal.getLoginUsername();
		if (refused) {
			String processId = approvalRecordVO.getProcessId();
			try {
				processService.terminateProcess(processId, excuteUsername, record.getApprovalSuggestion());
				return;
			} catch (GWFPException e) {
				LoggerHelper.err(getClass(), "打回流程失败，流程id：{}，任务id：{}，执行人：{}", processId,
						approvalRecordVO.getActivityId(), excuteUsername);
				throw new CRMRuntimeException("activity.complete.error");
			}
		}
		CompleteAdPlanActivityReq req = generateCompleteActivityReq(approvalRecordVO);
		CompleteActivityResponse response = adPlanProcessService.completeActivity(req);
		List<Activity> acts = response.getActivities();
		String info = gcrmTaskInfoService.getTaskInfo(acts, "advertise.solution.approval.task");
		AdSolutionContent content=null;
		if (record.getAdContentId() != null) {
            content = contentRepository.findOne(record.getAdContentId());
       }
		if (StringUtils.isNotBlank(info) || !response.isProcessFinish()) {
			Long ucid = RequestThreadLocal.getLoginUserId();
			if (content!=null) {
				//如果流程没有完结 切taskinfo为空 说明是并行节点
				if(StringUtils.isBlank(info)){
				    //处理并行节点
				    String souceTaskInfo = content.getTaskInfo();
				    String actDef = approvalRecordVO.getActDefId();
				    info=   gcrmTaskInfoService.removeDoneNodeInfo(souceTaskInfo, actDef);
				}
				
				content.setTaskInfo(info);
				content.setUpdateOperator(ucid);
				content.setUpdateTime(new Date());
				if (response.isProcessFinish()) {
					content.setApprovalDate(new Date());
				}
				contentRepository.save(content);
				AdvertiseSolution advertiseSolution = adSolutionDao.findOne(record.getAdSolutionId());
				advertiseSolution.setUpdateOperator(ucid);
				advertiseSolution.setUpdateTime(new Date());
				advertiseSolution.setTaskInfo("");
				adSolutionDao.save(advertiseSolution);
			} else {
				AdvertiseSolution advertiseSolution = adSolutionDao.findOne(record.getAdSolutionId());
				
				if(StringUtils.isBlank(info)){
                    //处理并行节点
                    String souceTaskInfo = advertiseSolution.getTaskInfo();
                    String actDef = approvalRecordVO.getActDefId();
                    info=   gcrmTaskInfoService.removeDoneNodeInfo(souceTaskInfo, actDef);
                }
				
				advertiseSolution.setTaskInfo(info);
				advertiseSolution.setUpdateOperator(ucid);
				advertiseSolution.setUpdateTime(new Date());
				contentRepository.updateApprovalDateByAdSolutionId(new Date(), advertiseSolution.getId());
				adSolutionDao.save(advertiseSolution);
			}
		}
		if (response.isProcessFinish()) {
            try {
                if (content != null) {
                    content.setTaskInfo(null);
                    contentRepository.save(content);
                }
				String processId = response.getProcessId();
				LoggerHelper.info(getClass(), "{}完成广告方案{}的审批，流程id：{}，即将发起竞价排期流程。", excuteUsername,
						record.getAdSolutionId(), processId);
				ProcessQueryBean process = processService.getProcessByProcessId(processId);
				completeProcess(process.getCreateUser(), process.getForeignKey());
			} catch (CRMBaseException e) {
				LoggerHelper.err(getClass(), "完成广告方案审批流程，发起竞价排期时出错，原因：{}，流程id：{}", e.getMessage(),
						response.getProcessId());
			}
		}
	}

    private void completeProcess(String startUser, String adSolutionId) {
        if (StringUtils.isEmpty(adSolutionId)) {
            throw new IllegalArgumentException("广告方案id不能为空");
        }
        try {
            AdvertiseSolution solution = null;
            int index = adSolutionId.indexOf(Constants.FOREIGN_KEY_SPLIT);
            if (index != -1) {
                String adContentId = adSolutionId.substring(index + 1);
                adSolutionId = adSolutionId.substring(0, index);
                solution = adSolutionDao.findOne(Long.valueOf(adSolutionId));
                adService.completeSingleContentApproveAndCreateSchedule(Long.valueOf(adContentId), startUser);
            } else {
                solution = adSolutionDao.findOne(Long.valueOf(adSolutionId));
                adService.completeAdApproveAndCreateSchedule(solution, startUser);
            }

            // 广告内容审核完成时，更新物料单状态
            materialManageService.updateMaterApplyStateAfterSolutionApproved((Long.valueOf(adSolutionId)));
        } catch (Exception e) {
            String s = String.format("Complete ad plan process error, ad id: %s, start user: %s", adSolutionId,
                    startUser);
            LoggerHelper.err(getClass(), s, e);
            throw new CRMRuntimeException(e);
        }
    }

	private boolean isRefused(Integer approvalStatus) {
		return approvalStatus == 0;
	}

	private void changeToRefuseStatusAndRemoveRelationship(ApprovalRecord record) {
		// ===拒绝描述
	
		User user = userService.findByUcid(record.getCreateOperator());
		String userName;
		if (user != null) {
		    userName=user.getRealname();
		} else {
		    userName=record.getCreateOperator().toString();
		}

		List<String> otherInfos = new ArrayList<String>();
		otherInfos.add(record.getApprovalSuggestion());
		String sb =gcrmTaskInfoService.getTaskInfo(userName, "advertise.solution.approval.refuse", otherInfos);
		Long adSolutionId = record.getAdSolutionId();
		AdvertiseSolution advertiseSolution = adSolutionDao.findOne(adSolutionId);
		Long ucid = RequestThreadLocal.getLoginUserId();
		// update ad solution to refuse status
		if (record.getAdContentId() == null) {
			advertiseSolution.setApprovalStatus(AdvertiseSolutionApproveState.refused);
			advertiseSolution.setTaskInfo(sb);
			advertiseSolution.setUpdateOperator(ucid);
			advertiseSolution.setUpdateTime(new Date());
			adSolutionDao.save(advertiseSolution);
			List<Long> contentIds = new ArrayList<Long>();
			// update ad solution contents to refuse status
			List<AdSolutionContent> adContents = contentRepository.findByAdSolutionId(adSolutionId);
			Date currentDate = DateUtils.getCurrentDateOfZero();
			for (AdSolutionContent content : adContents) {
				content.setApprovalStatus(ApprovalStatus.refused);
				content.setUpdateOperator(ucid);
				content.setUpdateTime(new Date());
				contentIds.add(content.getId());
				rollbackRelationshipAndStock(currentDate, content);
			}
			contentRepository.save(adContents);
			// remove relationships
			relationService.removeRelationshipsAndReleaseStock(contentIds);
		} else {
			// 内容变更后拒绝
			AdSolutionContent content = contentRepository.findOne(record.getAdContentId());
			content.setTaskInfo(sb);
			content.setApprovalStatus(ApprovalStatus.refused);
			content.setUpdateOperator(ucid);
			content.setUpdateTime(new Date());
			contentRepository.save(content);
            relationService.removeRelationsAndReleaseStocksFromDate(record.getAdContentId(),
                    DateUtils.getCurrentDateOfZero());
			advertiseSolution.setTaskInfo("");
			List<AdSolutionContent> contents = contentRepository.findByAdSolutionId(adSolutionId);
			ApprovalStatus status = content.getApprovalStatus();
			for (AdSolutionContent adContent : contents) {
				if (adContent.getId() != content.getId()) {
					if (adContent.getApprovalStatus().ordinal() < status.ordinal()) {
						status = adContent.getApprovalStatus();
					}
				}
			}
			if (status.equals(content.getApprovalStatus())) {
				advertiseSolution.setApprovalStatus(AdvertiseSolutionApproveState.valueOf(status.ordinal()));
				advertiseSolution.setUpdateOperator(ucid);
				advertiseSolution.setUpdateTime(new Date());
				adSolutionDao.save(advertiseSolution);
			}
		}
	}

	private CompleteAdPlanActivityReq generateCompleteActivityReq(ApprovalRecordVO approvalRecordVO) {
		CompleteAdPlanActivityReq req = new CompleteAdPlanActivityReq();
		req.setActivityId(approvalRecordVO.getActivityId());
		req.setProcessId(approvalRecordVO.getProcessId());
		req.setPerformer(RequestThreadLocal.getLoginUser().getUuapName());
		ApprovalRecord record = approvalRecordVO.getRecord();
		req.setApproved(record.getApprovalStatus());
		req.setReason(record.getApprovalSuggestion());
		return req;
	}

	@Override
	public void saveApprovalRecord(ApprovalRecord record) {
		approvalRecordRepository.save(record);
	}

	@Override
	public List<ApprovalInsertRecord> findCountByNotAllowInsert(Long adsolutionContentId,
			Long insertedAdsolutionContentId, List<String> activityIds) {
		return approvalInsertRecordRepository.findCountByAllowInsert(adsolutionContentId, insertedAdsolutionContentId,
				activityIds, Integer.valueOf(0), Integer.valueOf(0));
	}

	@Override
	public List<ApprovalInsertRecord> findByContentId(Long contentId, List<String> activityIds) {
		return approvalInsertRecordRepository.findInsertByContentId(contentId, activityIds);
	}

	@Override
	public void withdrawAD(Long adSolutionId) {
		Long ucid = RequestThreadLocal.getLoginUserId();
		String username = StringUtils.EMPTY;
		String realName = StringUtils.EMPTY;
		User user = userService.findByUcid(ucid);
		if (user != null) {
			realName = user.getRealname();
			username = user.getUuapName();
		}

		updateAdSolution(adSolutionId, ucid, realName);

		List<Long> contentIds = new ArrayList<Long>();
		List<AdSolutionContent> contentList = contentRepository.findByAdSolutionId(adSolutionId);
		Date currentDate = DateUtils.getCurrentDateOfZero();
        for (AdSolutionContent content : contentList) {
			updateContent(ucid, content, null);
			contentIds.add(content.getId());
			rollbackRelationshipAndStock(currentDate, content);
		}

		contentRepository.save(contentList);
		approvalInsertRecordRepository.updateToHistoryRecordInAdContentIds(contentIds);

		List<ApprovalRecord> records = approvalRecordRepository.findByAdSolutionId(adSolutionId);
		LoggerHelper.info(getClass(), "撤销整个广告方案，id：{}", adSolutionId);
		withdraw(records, username);
	}

    private void rollbackRelationshipAndStock(Date currentDate, AdSolutionContent content) {
        if (contentService.isNewContent(content) || contentService.isModifiedContent(content)) {
            relationService.removeRelationsAndReleaseStocksFromDate(content.getId(), currentDate);
        } else { // is unchanged content, back to before changing
            relationService.replaceAdContentIdInRelationship(content.getId(), content.getOldContentId());
        }
    }

	@Override
	public void withdrawSingleContent(Long contentId) {
		AdSolutionContent content = contentRepository.findOne(contentId);
		if (content == null) {
			throw new CRMRuntimeException("content.not.exists");
		}

		Long ucid = RequestThreadLocal.getLoginUserId();
		String username = StringUtils.EMPTY;
		String realName = StringUtils.EMPTY;
		User user = userService.findByUcid(ucid);
		if (user != null) {
			realName = user.getRealname();
			username = user.getUuapName();
		}

		updateContent(ucid, content, realName);
		contentRepository.save(content);
		Long adSolutionId = content.getAdSolutionId();
		updateAdSolution(adSolutionId, ucid, null);

		approvalInsertRecordRepository.updateAdContentHistoryRecord(contentId);
		relationService.removeRelationsAndReleaseStocksFromDate(contentId, DateUtils.getCurrentDateOfZero());

		List<ApprovalRecord> records = approvalRecordRepository.findByAdSolutionIdAndAdContentId(adSolutionId,
				contentId);
		LoggerHelper.info(getClass(), "撤销单个广告内容，广告方案id：{}，广告内容id：{}", adSolutionId, contentId);
		withdraw(records, username);
	}

	private void updateContent(Long ucid, AdSolutionContent content, String realName) {
		if (realName != null) {
			content.setTaskInfo(gcrmTaskInfoService.getTaskInfo(realName, "advertise.solution.approval.cancel"));
		}
		content.setApprovalStatus(ApprovalStatus.saving);
		content.setUpdateOperator(ucid);
		content.setUpdateTime(new Date());
	}

	private void updateAdSolution(Long adSolutionId, Long ucid, String realName) {
		AdvertiseSolution solution = adSolutionDao.findOne(adSolutionId);
		solution.setApprovalStatus(AdvertiseSolutionApproveState.saving);
		solution.setUpdateOperator(ucid);
		solution.setUpdateTime(new Date());
		if (realName != null) {
		    solution.setTaskInfo(gcrmTaskInfoService.getTaskInfo(realName, "advertise.solution.approval.cancel"));
		} else {
			solution.setTaskInfo("");
		}
		adSolutionDao.save(solution);
	}

	private void withdraw(List<ApprovalRecord> records, String username) {
		if (CollectionUtils.isEmpty(records)) {
			throw new CRMRuntimeException("ad.not.approved");
		}
		Set<String> processIds = new HashSet<String>();
		for (ApprovalRecord record : records) {
			if (StringUtils.isEmpty(record.getProcessId())) {
				continue;
			}
			processIds.add(record.getProcessId());
		}
		for (String processId : processIds) {
			processService.withdrawAndTerminateProcess(processId, username, StringUtils.EMPTY);
		}
	}
	
	@Override
	public List<ApprovalRecord> findByAdSolutionIdAndAdContentId(Long adSolutionId, Long adContentId) {
	    return approvalRecordRepository.findByAdSolutionIdAndAdContentId(adSolutionId, adContentId);
	}
}
