package com.baidu.gcrm.bpm.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.gcrm.ad.content.dao.IAdSolutionContentRepositoryCustom;
import com.baidu.gcrm.ad.dao.AdvertiseSolutionRepositoryCustom;
import com.baidu.gcrm.ad.material.dao.MaterialApplyCustomRepository;
import com.baidu.gcrm.bpm.dao.IActivityNameI18nRepository;
import com.baidu.gcrm.bpm.dao.IProcessActivityTypeRepository;
import com.baidu.gcrm.bpm.dao.IProcessNameI18nRepository;
import com.baidu.gcrm.bpm.model.ActivityNameI18n;
import com.baidu.gcrm.bpm.model.ProcessActivityType;
import com.baidu.gcrm.bpm.model.ProcessActivityType.ActivityType;
import com.baidu.gcrm.bpm.model.ProcessNameI18n;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.vo.ProcessInfo;
import com.baidu.gcrm.bpm.vo.ProcessQueryBean;
import com.baidu.gcrm.bpm.vo.ProcessQueryConditionBean;
import com.baidu.gcrm.bpm.vo.RemindRequest;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.SimpleActivityInfo;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.ActivityInfo;
import com.baidu.gcrm.bpm.web.helper.ApprovalRecordBean;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.bpm.web.helper.ProcessHelper;
import com.baidu.gcrm.bpm.web.helper.ProcessStatus;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.customer.dao.CustomerRepository;
import com.baidu.gcrm.process.vo.TaskCount;
import com.baidu.gcrm.quote.dao.IQuotationMainRepositoryCustom;
import com.baidu.gcrm.user.dao.IUserRepository;
import com.baidu.gcrm.user.model.User;
import com.baidu.gwfp.ws.BPMClientExtendWebService;
import com.baidu.gwfp.ws.WorkClientService;
import com.baidu.gwfp.ws.dto.ActivityContentResponse;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.dto.ActivityView;
import com.baidu.gwfp.ws.dto.ActivityViewQueryResult;
import com.baidu.gwfp.ws.dto.CompleteActivityRequest;
import com.baidu.gwfp.ws.dto.ProcessView;
import com.baidu.gwfp.ws.dto.ProcessViewQueryResult;
import com.baidu.gwfp.ws.dto.RemindActivityRequest;
import com.baidu.gwfp.ws.exception.GWFPException;

@Service
public class BpmProcessServiceImpl implements IBpmProcessService {
	private BPMClientExtendWebService bpmExtWebService;
	@Autowired
	private IActivityNameI18nRepository activityNameI18nDao;
	@Autowired
	private IUserRepository userDao;
	@Autowired
	private IProcessNameI18nRepository processNameI18nDao;
	@Autowired
	private IProcessActivityTypeRepository actTypeDao;
	@Autowired
	private WorkClientService workClientService;
	@Autowired
	private CustomerRepository customerDao;
	@Autowired
	private AdvertiseSolutionRepositoryCustom adSolutionDao;
	@Autowired
	private MaterialApplyCustomRepository materialDao;
	@Autowired
	private IQuotationMainRepositoryCustom quotationDao;
	@Autowired
	private IAdSolutionContentRepositoryCustom contentDao;
	@Autowired
	BPMClientExtendWebService cmsClientBpmExtWebService;
	
	@Value("#{appproperties['bpm.client.id']}")
	private String clientId;
	@Override
	public List<Activity> getCurrentActivitiesOfUser(String username) {
		try {
			List<Activity> activities = new ArrayList<Activity>();
			ActivityViewQueryResult result = bpmExtWebService.findCurrentActivitiesByUserName(username);
			if (result.getTotalCount() == 0) {
				return activities;
			}
			Map<String, ProcessActivityType> actTypesMap = getActTypesMap();
			ActivityView[] activityViews = result.getActivityViews();
			for (ActivityView activityView : activityViews) {
				if (ignore(activityView)) {
					continue;
				}
				if (clientId.equals(activityView.getClientId())) {
					activities.add(generateActivity(activityView, actTypesMap));
				}
			}
			return activities;
		} catch (GWFPException e) {
			throw new CRMRuntimeException("task.find.error");
		}
	}

	private Map<String, ProcessActivityType> getActTypesMap() {
		Map<String, ProcessActivityType> actTypesMap = new HashMap<String, ProcessActivityType>();
		List<ProcessActivityType> activityTypes = actTypeDao.findAll();
		for (ProcessActivityType type : activityTypes) {
			actTypesMap.put(type.getProcessDefId(), type);
		}
		return actTypesMap;
	}

	private Activity generateActivity(ActivityView activityView, Map<String, ProcessActivityType> actTypesMap) {
		Activity activity = new Activity();
		ProcessView processView = activityView.getProcessView();
		String processDefineId = processView.getProcessDefineId();
		activity.setProcessDefId(processDefineId);
		activity.setProcessName(processView.getProcessName());
		activity.setProcessId(processView.getProcessId());
		activity.setStartTime(processView.getProcessCreateTime());
		activity.setStartUser(processView.getCreateUser());
		activity.setActivityName(activityView.getActivityName());
		activity.setActivityId(activityView.getActivityId());
		activity.setActDefId(activityView.getActivityDefineId());
		activity.setActivityArrivedTime(activityView.getStartTime());
		activity.setActDefId(activityView.getActivityDefineId());
		activity.setForeignKey(processView.getForeignKey());
		// activity.setForeignName(processView.getForeignName());
		ProcessActivityType activityType = actTypesMap.get(processDefineId);
		if(activityType != null){
			activity.setType(activityType.getType());
			activity.setSubtype(activityType.getSubtype());
			activity.setForeignName(activityType.getParamKey().toString());
		}
		return activity;
	}
	
	@Override
	public TaskCount getTaskCountOfCurrentUser(String username) {
		TaskCount taskCount = new TaskCount();
		int count = 0;
		try {
			List<Activity> activities = getCurrentActivitiesOfUser(username);
			boolean hasFirstApproval = false;
			boolean hasFirst = false;
			for (Activity activity : activities) {
				if (!hasFirstApproval && ActivityType.approval.equals(activity.getType())) {
					taskCount.setFirstAct(activity);
					hasFirstApproval = true;
					hasFirst = true;
				} else if (!hasFirst) {
					taskCount.setFirstAct(activity);
					hasFirst = true;
				}
				count++;
			}
		} catch(Exception e) {
			LoggerHelper.err(getClass(), "获取待办数量出错，错误原因：{}", e.getMessage());
		}
		taskCount.setCount(count);
		
		return taskCount;
	}
	
	@Override
	public List<Activity> getCurrentActivitiesOfUser(String username, LocaleConstants locale) {
		List<Activity> activities = getCurrentActivitiesOfUser(username);
		Map<String, User> users = getUsers();
		// default en_US
//		if (LocaleConstants.en_US.equals(locale)) {
//			return activities;
//		}
		
		Map<String, String> actNameI18ns = getActNameI18ns(locale);
		Map<String, String> processNameI18ns = getProcessNameI18ns(locale);
		Map<Long, Long> customerNumbers = null;
		Map<Long, String> adSolutionNumbers = null;
		Map<Long, String> materialNumbers = null;
		Map<Long, String> quoteNumbers = null;
		Map<Long, String> contentNumbers = null;
		for (Activity activity : activities) {
			i18nActivityName(locale, actNameI18ns, activity);
//			if (LocaleConstants.zh_CN.equals(locale)) {
				i18nStartUsername(activity, users);
//			}
			i18nProcessName(locale, processNameI18ns, activity);
			
			Long id = getId(activity.getForeignKey());
			switch(activity.getSubtype()) {
				case customer:
					if (customerNumbers == null) {
						customerNumbers = customerDao.getIdNumberMap();
					}
					Long customerNumber = customerNumbers.get(id);
					if (customerNumber != null) {
						activity.setNumber(customerNumber.toString());
					}
					break;
				case approval:
					if (adSolutionNumbers == null) {
						adSolutionNumbers = adSolutionDao.getIdNumberMap();
					}
					activity.setNumber(adSolutionNumbers.get(id));
					break;
				case material :
					if (materialNumbers == null) {
						materialNumbers = materialDao.getIdNumberMap();
					}
					activity.setNumber(materialNumbers.get(id));
					break;
				case quote :
					if (quoteNumbers == null) {
						quoteNumbers = quotationDao.getIdNumberMap();
					}
					activity.setNumber(quoteNumbers.get(id));
					break;
				case schedule :
					if (contentNumbers == null) {
						contentNumbers = contentDao.getIdNumberMap();
					}
					activity.setNumber(contentNumbers.get(id));
					break;
				default :
					break;
			}
		}
		return activities;
	}
	
	private Long getId(String foreignKey) {
		try {
			if (StringUtils.isBlank(foreignKey)) {
				return null;
			}
			int index = foreignKey.indexOf("_");
			if (index > 0) {
				return Long.valueOf(foreignKey.substring(0, index));
			}
			return Long.valueOf(foreignKey);
		} catch (Exception e) {
			return null;
		}
	}

	private Map<String, User> getUsers() {
		Map<String, User> userMap = new HashMap<String, User>();
		List<User> users = userDao.findAll();
		if (CollectionUtils.isEmpty(users)) {
			return userMap;
		}
		for (User user : users) {
			userMap.put(user.getUuapName(), user);
		}
		return userMap;
	}

	private boolean ignore(ActivityView activity) {
		String actDefId = activity.getActivityDefineId();
		String processDefId = activity.getProcessView().getProcessDefineId();
		String biddingProcessDefId = GcrmConfig.getConfigValueByKey("bidding.process.defineId");
		if ((processDefId.equals(biddingProcessDefId) && actDefId.equals("act2")) || (!processDefId.equals(biddingProcessDefId) && actDefId.equals("act1"))) {
			return true;
		}
		return false;
	}

	private Map<String, String> getProcessNameI18ns(LocaleConstants locale) {
		Map<String, String> processNameI18ns = new HashMap<String, String>();
		List<ProcessNameI18n> i18ns = processNameI18nDao.findByLocale(locale.name());
		for (ProcessNameI18n i18n : i18ns) {
			processNameI18ns.put(i18n.getProcessDefId(), i18n.getProcessName());
		}
		return processNameI18ns;
	}

	private Map<String, String> getActNameI18ns(LocaleConstants locale) {
		Map<String, String> actNameI18ns = new HashMap<String, String>();
		List<ActivityNameI18n> i18ns = activityNameI18nDao.findByLocale(locale);
		for (ActivityNameI18n i18n : i18ns) {
			String processDefId = i18n.getProcessDefId();
			String actDefId = i18n.getActivityId();
			actNameI18ns.put(getKey(processDefId, actDefId), i18n.getActivityName());
		}
		return actNameI18ns;
	}

	private void i18nActivityName(LocaleConstants locale, Map<String, String> actNameI18ns, Activity activity) {
		String processDefId = activity.getProcessDefId();
		String actDefId = activity.getActDefId();
		String key = getKey(processDefId, actDefId); 
		String actName = actNameI18ns.get(key);
		if (StringUtils.isNotBlank(actName)) {
			activity.setActivityName(actName);
		}
	}

	private void i18nStartUsername(Activity activity, Map<String, User> users) {
		User user = users.get(activity.getStartUser());
		if (user != null) {
			activity.setStartUser(user.getRealname());
		}
	}

	private void i18nProcessName(LocaleConstants locale, Map<String, String> processNameI18ns, Activity activity) {
		String processDefId = activity.getProcessDefId();;
		String processName = processNameI18ns.get(processDefId);
		if (StringUtils.isNotBlank(processName)) {
			activity.setProcessName(processName);
		} 
	}
	
	private String getKey(String processDefId, String actDefId) {
		return processDefId.concat(actDefId);
	}

	@Override
	public List<ApprovalRecordBean> getApprovalRecords(String processId) {
		List<ApprovalRecordBean> approvalReocrds = new ArrayList<ApprovalRecordBean>();
		try {
			ActivityView[] activityViews = bpmExtWebService.getActivitiesByProcessId(processId);
			for (int i = activityViews.length - 1; i >= 0; i--) {
				ApprovalRecordBean approvalRecord = generateApprovalRecord(activityViews[i]);
				approvalRecord.setPerformer(qutoStr(approvalRecord.getPerformer()));
				approvalReocrds.add(approvalRecord);
			}
			return approvalReocrds;
		} catch (GWFPException e) {
			throw new CRMRuntimeException();
		}
	}
	
	private static String qutoStr(String str) {
		String rtn = "";
		List<String> users = new ArrayList<String>();
		Pattern p = Pattern.compile("\\[(.*?)\\]");
		Matcher m = p.matcher(str);
		boolean match = false;
		while(m.find()) {
			match = true;
			String group = m.group(1);
			if (StringUtils.isNotBlank(group)) {
				users.add(group);
			}
		}
		if (CollectionUtils.isNotEmpty(users)) {
			rtn = StringUtils.join(users, ",");
		} else if (!match) {
			rtn = str;
		}
		return rtn;
	}
	
	private ApprovalRecordBean generateApprovalRecord(ActivityView activityView) {
		ApprovalRecordBean approvalRecord = new ApprovalRecordBean();

		String activityName = activityView.getActivityName();
		approvalRecord.setActivityId(activityView.getActivityId());
		approvalRecord.setActivityName(activityName);
		approvalRecord.setPerformer(activityView.getPerformer());

		String startTime = DateUtils.getDate2String(DateUtils.YYYY_MM_DD_HH_MM_SS, activityView.getStartTime());
		approvalRecord.setStartTime(startTime);

		String endTime = DateUtils.getDate2String(DateUtils.YYYY_MM_DD_HH_MM_SS, activityView.getEndTime());
		approvalRecord.setEndTime(endTime);

		approvalRecord.setRemark(ProcessHelper.decodeApproveReason(activityView.getRemark()));
		String backActivityDefineId = activityView.getBackActivityDefineId();
		if (StringUtils.isNotBlank(backActivityDefineId) && backActivityDefineId.startsWith("-")) {
			approvalRecord.setApproved(false);
		} else {
			approvalRecord.setApproved(true);
		}
		return approvalRecord;
	}

	@Override
	public CompleteActivityResponse completeActivity(CompleteActivityReq request) {
		try {
			CompleteActivityRequest completeReq = new CompleteActivityRequest();
			completeReq.setProcessId(request.getProcessId());
			completeReq.setActivityId(request.getActivityId());
			completeReq.setPerformUser(request.getPerformer());
			completeReq.setActivityData(buildCompleteActivityData(request));
			ActivityContentResponse[] response = bpmExtWebService.completeActivity(completeReq);
			String message = String.format("%s完成任务，流程id：%s，任务id：%s", completeReq.getPerformUser(), completeReq.getProcessId(), completeReq.getActivityId());
			LoggerHelper.info(getClass(), message);
			return ProcessHelper.convertActivityContentResponseToActivities(response);
		} catch (GWFPException e) {
			String message = String.format("%s完成任务失败，流程id：%s，任务id：%s，失败原因：%s", request.getPerformer(),
					request.getProcessId(), request.getActivityId(), e.getMessage());
			LoggerHelper.err(getClass(), message);
			throw new CRMRuntimeException("activity.complete.error." + e.getErrorCode());
		}
	}
	
	private ActivityData[] buildCompleteActivityData(CompleteActivityReq request) {
		int approved = request.getApproved();
		String reason = ProcessHelper.encodeApproveReason(request.getReason());
		if (approved == 1) {
			ActivityData activityData = new ActivityData("BPM_AGREE_REASON", reason);
			return new ActivityData[]{activityData};
		} else {
			return new ActivityData[]{new ActivityData("BPM_REJECT_REASON", reason),
					new ActivityData("BPM_REJECT_TARGET", "act1")};
		}
	}
	
	@Override
	public ActivityInfo getActivityInfoByActivityId(String activityId) {
		try {
			ActivityInfo activityInfo = new ActivityInfo();
			ActivityView activity = bpmExtWebService.getActivity(activityId);
			if (activity == null) {
				throw new CRMRuntimeException("activity.not.exists");
			}
			String participantId = activity.getParticipantId();
			activityInfo.setParticipantId(ParticipantConstants.valueOf(participantId));
			activityInfo.setRunning(activity.getOperationStatus() == 0);
			activityInfo.setPerformer(activity.getPerformer());
			activityInfo.setForeignKey(
					activity.getProcessView().getForeignKey());
			return activityInfo;
		} catch (GWFPException e) {
			throw new CRMRuntimeException("activity.find.error");
		}
	}

	@Autowired
	public void setBpmExtWebService(BPMClientExtendWebService bpmExtWebService) {
		this.bpmExtWebService = bpmExtWebService;
	}

    @Override
    public List<ActivityNameI18n> findActivityNameI18n(String processDefId,
            List<String> activityIds, LocaleConstants locale) {
        return activityNameI18nDao.findByProcessDefIdAndLocaleAndActivityIdIn(processDefId, locale, activityIds);
    }

    @Override
    public void remindByForeignKey(RemindRequest remindRequest) {
    	ProcessQueryConditionBean queryBean = genrateQueryConditionBean(remindRequest);
    	List<ProcessQueryBean> processes = queryProcess(queryBean);
    	if (CollectionUtils.isEmpty(processes)) {
    		throw new CRMRuntimeException("process.toremind.not.exists");
    	}
    	
    	String processId = processes.get(0).getProcessId();
    	try {
			ActivityView[] activityViews = bpmExtWebService.getActivitiesByProcessId(processId);
			if (ArrayUtils.isEmpty(activityViews)) {
				throw new CRMRuntimeException("process.toremind.not.exists");
			}
			List<RemindActivityRequest> requests = new ArrayList<RemindActivityRequest>();
			for (ActivityView activityView : activityViews) {
				Integer operationStatus = activityView.getOperationStatus();
				if (operationStatus == null || operationStatus.intValue() != 0) {
					continue;
				}
				RemindActivityRequest remindActivityRequest = generateRemindActivityRequest(remindRequest, processId, activityView);
				requests.add(remindActivityRequest);
			}
			workClientService.batchRemindActivity(requests);
		} catch (GWFPException e) {
			LoggerHelper.err(getClass(), "催办失败，processId：{}，失败原因：{}", processId, e.getMessage());
			throw new CRMRuntimeException("process.remind.error");
		}
    }

	private ProcessQueryConditionBean genrateQueryConditionBean(RemindRequest request) {
		ProcessQueryConditionBean queryBean = new ProcessQueryConditionBean();
    	queryBean.setPackageId(GcrmConfig.getConfigValueByKey("ad.package.id"));
    	queryBean.setForeignKey(request.getKey());
    	int runningState = 0;
    	List<Integer> processState = new ArrayList<Integer>();
    	processState.add(runningState);
    	queryBean.setProcessState(processState);
    	RemindType type = request.getType();
    	queryBean.setProcessDefineId(GcrmConfig.getConfigValueByKey(type.getProcessDefineId()));
   
    	
		return queryBean;
	}
	
	private RemindActivityRequest generateRemindActivityRequest(RemindRequest remindRequest, String processId,
			ActivityView activityView) {
		RemindActivityRequest request = new RemindActivityRequest();
		request.setActivityId(activityView.getActivityId());
		request.setProcessId(processId);
		request.setNotifyType(remindRequest.getNotifyType());
		request.setReminder(remindRequest.getReminder());
		return request;
	}
    
    @Override
    public List<ProcessQueryBean> queryProcess(ProcessQueryConditionBean queryBean) {
    	String[] properties = queryBean.getProperties();
		ProcessViewQueryResult processViewQueryResult = bpmExtWebService.findProcessByProperties(properties, null);
    	return generateProcessQueryList(processViewQueryResult);
    }
    
    private List<ProcessQueryBean> generateProcessQueryList(ProcessViewQueryResult processViewQueryResult) {
    	List<ProcessQueryBean> processes = new ArrayList<ProcessQueryBean>();
		ProcessView[] processViews = processViewQueryResult.getProcessViews();
		if (ArrayUtils.isEmpty(processViews)) {
			return processes;
		}
		for (ProcessView processView : processViews) {
			ProcessQueryBean bean = new ProcessQueryBean();
			bean.setProcessId(processView.getProcessId());
			bean.setForeignKey(processView.getForeignKey());
			bean.setStartTime(DateUtils.getDate2String(processView.getProcessCreateTime()));
			bean.setCreateUser(processView.getCreateUser());
			bean.setStatus(ProcessStatus.findStatus(processView.getProcessState()));
			processes.add(bean);
		}
		return processes;
	}

	@Override
    public void withdrawAndTerminateProcess(String processId, String executeUser, String reason) {
    	try {
			bpmExtWebService.terminalProcess(processId, executeUser, reason);
			LoggerHelper.info(getClass(), "撤销成功，流程id：{}，撤销执行人：{}", processId, executeUser, reason);
		} catch (GWFPException e) {
			LoggerHelper.err(getClass(), "撤销终止流程失败，流程id：{}，撤销执行人：{}，失败原因：{}。", processId, executeUser, e.getMessage());
		}
    }
	
	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void completeActivityByCondition(ProcessQueryConditionBean queryBean, CompleteActivityReq req) {
		List<ProcessQueryBean> processes = queryProcess(queryBean);
		if (CollectionUtils.isEmpty(processes)) {
			throw new CRMRuntimeException("process.not.found");
		}
		ProcessQueryBean process = processes.get(0);
		String processId = process.getProcessId();
		try {
			ActivityView[] activityViews = bpmExtWebService.getActivitiesByProcessId(processId);
			if (ArrayUtils.isEmpty(activityViews)) {
				throw new CRMRuntimeException("activity.complete.not.exists");
			}
			List<String> activityIds = new ArrayList<String>();
			for (ActivityView activityView : activityViews) {
				Integer operationStatus = activityView.getOperationStatus();
				if (operationStatus == null || operationStatus.intValue() != 0) {
					continue;
				}
				activityIds.add(activityView.getActivityId());
			}
			
			for (String activityId : activityIds) {
				CompleteActivityReq request = new CompleteActivityReq();
				BeanUtils.copyProperties(request, req);
				request.setProcessId(processId);
				request.setActivityId(activityId);
				completeActivity(request);
			}
		} catch (GWFPException e) {
			LoggerHelper.err(getClass(), "完成任务失败，processId：{}，失败原因：{}", processId, e.getMessage());
			throw new CRMRuntimeException("process.complete.error");
		} catch (IllegalAccessException e) {
			LoggerHelper.err(getClass(), "完成任务失败，processId：{}，失败原因：{}", processId, e.getMessage());
			throw new CRMRuntimeException("process.complete.error");
		} catch (InvocationTargetException e) {
			LoggerHelper.err(getClass(), "完成任务失败，processId：{}，失败原因：{}", processId, e.getMessage());
			throw new CRMRuntimeException("process.complete.error");
		}
	}
	
	@Override
	public void completeActivityByConditionNoException(
			ProcessQueryConditionBean queryBean, CompleteActivityReq req) {
		try {
			completeActivityByCondition(queryBean, req);
		} catch (Exception e) {
			LoggerHelper.err(getClass(), "完成任务失败，失败原因：{}", e.getMessage());
		}
	}
	
	@Override
	public void terminateProcess(String processId, String executeUser, String reason) throws GWFPException {
		try {
			bpmExtWebService.terminalProcess(processId, executeUser, reason);
		} catch (GWFPException e) {
			LoggerHelper.err(getClass(), "终止流程失败，流程id：{}，执行人：{}", processId, executeUser);
			throw e;
		}
		
	}
	
	@Override
	public ProcessQueryBean getProcessByProcessId(String processId) throws CRMBaseException {
		try {
			ProcessQueryBean bean = new ProcessQueryBean();
			ProcessView processView = bpmExtWebService.getProcessByProcessId(processId);
			if (processView != null) {
				bean.setProcessId(processView.getProcessId());
				bean.setForeignKey(processView.getForeignKey());
				bean.setStartTime(DateUtils.getDate2String(processView.getProcessCreateTime()));
				bean.setCreateUser(processView.getCreateUser());
				bean.setForeignName(processView.getForeignName());
			}
			return bean;
		} catch (GWFPException e) {
			LoggerHelper.err(getClass(), "根据流程id：{}查找流程失败，失败原因：{}", processId, e.getMessage());
			throw new CRMBaseException(e);
		}
	}
	@Override
	public void terminateProcessByAdContentId(List<Long> adContentIds) {
		if (CollectionUtils.isEmpty(adContentIds)) {
			return;
		}
		for (Long adContentId : adContentIds) {
			try {
				ProcessQueryConditionBean queryBean = new ProcessQueryConditionBean();
				queryBean.setForeignKey(adContentId.toString());
				queryBean.setPackageId(GcrmConfig.getConfigValueByKey("ad.package.id"));
				queryBean.setProcessDefineId(GcrmConfig.getConfigValueByKey("bidding.process.defineId"));
				List<Integer> processState = new ArrayList<Integer>();
				processState.add(0);
				queryBean.setProcessState(processState);
				List<ProcessQueryBean> processes = queryProcess(queryBean);
				if (CollectionUtils.isEmpty(processes)) {
					return;
				}
				for (ProcessQueryBean process : processes) {
					String username = RequestThreadLocal.getLoginUsername();
					if (StringUtils.isEmpty(username)) {
						username = Constants.SYSTEM_USERNAME;
					}
					String processId = process.getProcessId();
					try {
						terminateProcess(processId, username, "释放排期单后，终止流程。");
						LoggerHelper.info(getClass(), "终止流程，流程id：{}，广告内容id：{}",  processId, adContentId);
					} catch(Exception e) {
						LoggerHelper.err(getClass(), "终止流程失败，流程id：{}，广告内容id：{}，失败原因：{}", processId, adContentId,
								e.getMessage());
					}
				}
			} catch(Exception e) {
				LoggerHelper.err(getClass(), "终止流程失败，广告内容id：{}，失败原因：{}", adContentId, e.getMessage());
			}
		}
	}
	
	@Override
	public ProcessInfo getCmsProcessInfoByForeignKey(String foreignKey) {
		ProcessQueryConditionBean queryBean = new ProcessQueryConditionBean();
		queryBean.setPackageId(GcrmConfig.getConfigValueByKey("cms.package.id"));
		queryBean.setProcessDefineId(GcrmConfig.getConfigValueByKey("cms.process.defineId"));
		queryBean.setProcessName(foreignKey);
		String[] properties = queryBean.getProperties();
		ProcessViewQueryResult processViewQueryResult = cmsClientBpmExtWebService.findProcessByProperties(properties, null);
    	List<ProcessQueryBean> processes = generateProcessQueryList(processViewQueryResult);
		if (CollectionUtils.isEmpty(processes)) {
			return null;
		}
		if (processes.size() > 1) {
			LoggerHelper.err(getClass(), "合同编号：{}对应的流程不止一个！", foreignKey);
		}
		ProcessQueryBean queryRes = processes.get(0);
		ProcessInfo processInfo = new ProcessInfo();
		try {
			PropertyUtils.copyProperties(processInfo, queryRes);
			if (!ProcessStatus.RUN.equals(queryRes.getStatus())) {
				return processInfo;
			}
			String processId = processInfo.getProcessId();
			ActivityContentResponse[] activites = cmsClientBpmExtWebService.getProcessCurrentActivites(processId);
			if (ArrayUtils.isEmpty(activites)) {
				LoggerHelper.err(getClass(), "cms流程没有结束，也没有当前运行的任务，合同编号：{}", foreignKey);
				return processInfo;
			}
			List<SimpleActivityInfo> acts = new ArrayList<SimpleActivityInfo>();
			for (ActivityContentResponse activity : activites) {
				SimpleActivityInfo act = new SimpleActivityInfo(activity.getActivityName(), activity.getPerformName());
				acts.add(act);
			}
			processInfo.setActs(acts);
			return processInfo;
		} catch (Exception e) {
			LoggerHelper.info(getClass(), "获取cms流程时出错，合同编号：" + foreignKey, e);
		} 
		return processInfo;
	}
}
