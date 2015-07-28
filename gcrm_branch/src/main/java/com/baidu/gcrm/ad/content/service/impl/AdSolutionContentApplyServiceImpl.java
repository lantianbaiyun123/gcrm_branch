package com.baidu.gcrm.ad.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.ad.approval.model.Participant;
import com.baidu.gcrm.ad.approval.model.Participant.DescriptionType;
import com.baidu.gcrm.ad.approval.service.IParticipantService;
import com.baidu.gcrm.ad.content.dao.IAdSolutionContentApplyRepository;
import com.baidu.gcrm.ad.content.model.AdContentApplyApprovalRecord;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.content.model.AdSolutionContentApply.ApprovalStatus;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentApplyService;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentApplyVo;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.ad.web.utils.ContractUrlUtilHelper;
import com.baidu.gcrm.attachment.model.AttachmentModel;
import com.baidu.gcrm.attachment.model.AttachmentModel.ModuleNameWithAtta;
import com.baidu.gcrm.attachment.service.IAttachmentModelService;
import com.baidu.gcrm.bpm.dao.IProcessNameI18nRepository;
import com.baidu.gcrm.bpm.model.ProcessNameI18n;
import com.baidu.gcrm.bpm.service.IAssignmentHandler;
import com.baidu.gcrm.bpm.service.IBpmProcessService;
import com.baidu.gcrm.bpm.service.IBpmProcessStartService;
import com.baidu.gcrm.bpm.service.IGCrmTaskInfoService;
import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gcrm.bpm.vo.ParticipantConstants;
import com.baidu.gcrm.bpm.vo.RemindRequest;
import com.baidu.gcrm.bpm.vo.RemindType;
import com.baidu.gcrm.bpm.vo.StartProcessResponse;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.BaseStartProcessBean;
import com.baidu.gcrm.bpm.web.helper.CompleteBaseActivityReq;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.GcrmConfig;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.mail.OnlineApplyMailContent;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.ws.cms.ICMSRequestFacade;
import com.baidu.gwfp.ws.dto.ActivityData;
import com.baidu.gwfp.ws.exception.GWFPException;

/**
 * 
 * @author yangjianguo
 * 广告内容提前上线申请相关业务
 */
@Service
public class AdSolutionContentApplyServiceImpl implements
		IAdSolutionContentApplyService {

	@Autowired
	private IAdSolutionContentApplyRepository adSolutionContentApplyRepository;
    @Autowired
    @Qualifier("baseProcessServiceImpl")
    IBpmProcessStartService baseProcessService;
    @Autowired
    private IBpmProcessService processService;
    @Autowired
    private IAttachmentModelService attachmentModelService;
    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private IProcessNameI18nRepository processNameDao;
    @Autowired
	private IUserService userService;
    @Autowired
    private IAdvertiseSolutionService adSolutionService;
    @Autowired
    private IAdSolutionContentService adSolutionContentService;
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private IUserRightsService userRightsService;
    @Autowired
    private IParticipantService participantService;
    @Autowired
    private IPublishService publishService;
    @Autowired
    IGCrmTaskInfoService gcrmTaskInfoService;
    @Autowired
    private ICMSRequestFacade cmsRequestFacade;
    @Value("#{appproperties['adContentOnlineApply.process.defineId']}")
    private String processDefineId;
    
    private static final String messagePrefix = "onlineApply.main.approval.";
    
	public AdSolutionContentApply saveAdContentApply(AdSolutionContentApplyVo adContentApplyVo) {
		 AdSolutionContentApply adSolutionContentApply=adContentApplyVo.getAdSolutionContentApply();
		 if(adSolutionContentApply.getId()==null){
		 adSolutionContentApply.setCreateTime(new Date());
		 adSolutionContentApply.setCreateOperator(RequestThreadLocal.getLoginUserId());
		 adSolutionContentApplyRepository.saveAdContentApply(adSolutionContentApply);//save
		 }else{
			 AdSolutionContentApply exist=adSolutionContentApplyRepository.findAdContentApply(adSolutionContentApply.getId());
			 BeanUtils.copyProperties(adSolutionContentApply, exist);
			 adSolutionContentApplyRepository.saveAdContentApply(exist);//update
			 attachmentModelService.deleteByRecordAndModule(adSolutionContentApply.getId(), ModuleNameWithAtta.adcontentonlineapply);
		 }
		 
		for(AttachmentModel att:adContentApplyVo.getAttachments()){
			att.setId(null);
			att.setTransactionRecordId(adSolutionContentApply.getId());
			attachmentModelService.save(att);
		}
		submitProcess(adSolutionContentApply,RequestThreadLocal.getLoginUser());
		return adSolutionContentApply;
	}
	
	
    @SuppressWarnings("rawtypes")
	public List<AdContentApplyApprovalRecord> findApprovalRecordsByOnlineApplyId(Long applyId,LocaleConstants currentLocale) {
        List records= adSolutionContentApplyRepository.findAdContentApplyApprovalRecords(processDefineId, currentLocale,applyId);
        List<AdContentApplyApprovalRecord> approvalRecords = new ArrayList<AdContentApplyApprovalRecord>();
        Object[] tempObject = null;
        AdContentApplyApprovalRecord record=null;
        for (Object object : records) {
            tempObject = (Object[]) object;
            record = (AdContentApplyApprovalRecord) tempObject[0];
            record.setCreater((String) tempObject[1]);
            record.setTaskName( (String) tempObject[2]);
            approvalRecords.add(record);
        }
        return approvalRecords;
    }
    
    public AdSolutionContentApplyVo findAdSolutionContentApplDetial(Long applyId, LocaleConstants currentLocale){
    	AdSolutionContentApplyVo adSolutionContentApplyVo=new AdSolutionContentApplyVo();
    	AdSolutionContentApply  adSolutionContentApply=findAdContentApply(applyId);
    	adSolutionContentApply.setTaskInfo(gcrmTaskInfoService.convertTaskInfo(RemindType.contentOnlineApply, adSolutionContentApply.getTaskInfo(), currentLocale));
    	adSolutionContentApplyVo.setAdSolutionContentApply(adSolutionContentApply);
    	adSolutionContentApplyVo.setAttachments(attachmentModelService.findByRecordAndModule(applyId, ModuleNameWithAtta.adcontentonlineapply));
    	adSolutionContentApplyVo.setApprovalRecords(findApprovalRecordsByOnlineApplyId(applyId,currentLocale));
		return adSolutionContentApplyVo;
    	
    }
    
    public List<Contract> findImmeContractsFromCMS(Long customerId){
    	return cmsRequestFacade.getImmeContractsByCustomerId(customerId);
    }
    	
    public void submitProcess(AdSolutionContentApply adSolutionContentApply, User operaterUser) {

        BaseStartProcessBean startBean = new BaseStartProcessBean();
        Long contentApplyId = adSolutionContentApply.getId();
        prepare(startBean, operaterUser, adSolutionContentApply);
        StartProcessResponse respone = null;
        try {
            //构建 工作流个节点绑定的handler
            respone = baseProcessService.startProcess(startBean);
            String taskInfo = gcrmTaskInfoService.getTaskInfo(respone.getActivities(), messagePrefix + "task");
            updateStatus4ContentOnlineApply(adSolutionContentApply, operaterUser, ApprovalStatus.approving, taskInfo);
            AdContentApplyApprovalRecord record = generateApprovalRecord(contentApplyId, operaterUser, respone);
            // 保存审批记录
            saveAdContentApplyApproval(record);
        } catch (Exception e) {
            LoggerHelper.err(getClass(), e.getMessage(), e);
            throw new CRMRuntimeException("process.start.error", e);
        }
    }
    
    public void terminateOnlineApplyProcess(AdSolutionContentApply apply){
    	//当合同生效或某些条件满足时，需终止当前的提前上线审批流,只有审核中的才可以终止
        if (apply.getApprovalStatus() == ApprovalStatus.approving) {
            List<AdContentApplyApprovalRecord> records = findApprovalRecordsByOnlineApplyId(apply.getId(), LocaleConstants.en_US);
            if (records!=null && records.size() > 0) {
                AdContentApplyApprovalRecord record = records.get(records.size()-1);
                try {
					processService.terminateProcess(record.getProcessId(), Constants.SYSTEM_USERNAME, "当前广告内容已无需上线申请,终止即可");
				} catch (GWFPException e) {
					LoggerHelper.err(getClass(), "终止提前上线申请审批失败:"+e.getMessage(), e);
					throw new CRMRuntimeException(e);
				}
            }
            apply.setApprovalStatus(ApprovalStatus.terminated);
            adSolutionContentApplyRepository.saveAdContentApply(apply);
        }
    }
	
     public void processApproveRequest(AdContentApplyApprovalRecord approvalRecord,User operateUser,LocaleConstants currentLocale) {
        // 判断审批通过OR驳回
        ApprovalStatus approvalStatus = null;
        String taskInfo = "";
        boolean processFinished=false;
        if (approvalRecord.getApprovalStatus()==0) {
        	approvalStatus=ApprovalStatus.refused;
            String processId = approvalRecord.getProcessId();
            List<String> reasons=new ArrayList<String>();
            reasons.add(approvalRecord.getApprovalSuggestion());
            taskInfo = gcrmTaskInfoService.getTaskInfo(operateUser.getRealname(), messagePrefix + "refuse",reasons);
            try {
            	processService.terminateProcess(processId, operateUser.getRealname(), approvalRecord.getApprovalSuggestion());
            } catch (GWFPException e) {
                LoggerHelper.err(getClass(), "打回流程失败，流程id：{}，任务id：{}，执行人：{}", processId,
                        approvalRecord.getActivityId(), operateUser.getRealname());
                throw new CRMRuntimeException("activity.complete.error");
            }

        } else {
            CompleteBaseActivityReq req = generateCompleteActivityReq(approvalRecord, operateUser);

            try {
                CompleteActivityResponse response = baseProcessService.completeActivity(req);
                List<Activity> acts = response.getActivities();
                taskInfo = gcrmTaskInfoService.getTaskInfo(acts, messagePrefix+"task");
                if (response.isProcessFinish()) {
                	approvalStatus=ApprovalStatus.approved;
                	processFinished=true;
                	try{
                	OnlineApplyMailContent content=getMailContent4OnlineReqeust(findAdContentApply(approvalRecord.getAdContentApplyId()),Boolean.TRUE);
                	MailHelper.sendOnlineApplyApprovedMail(content);
                	}catch(Exception e){
                		LoggerHelper.err(getClass(), "提前上线申请已获批准,但邮件通知失败:"+e.getMessage(),e);
                	}
                }

            } catch (Exception e) {
                LoggerHelper.err(getClass(), "打回流程失败，流程id：{}，任务id：{}，执行人：{}", e);

                throw new CRMRuntimeException("activity.complete.error");
            }
        }
        
        //更新上线申请单状态
        AdSolutionContentApply contentApply=findAdContentApply(approvalRecord.getAdContentApplyId());
        updateStatus4ContentOnlineApply(contentApply, operateUser, approvalStatus,taskInfo);
        // 记录审批记录
        approvalRecord.setCreateOperator(operateUser.getUcid());
        approvalRecord.setCreateTime(new Date());
        saveAdContentApplyApproval(approvalRecord);
        //尝试生成排期单任务
        if(processFinished){
            publishService.tryLockScheduleAndCreatePublish(adSolutionContentService.findOne(contentApply
                    .getAdSolutionContentId()));
        }
    }
  
   public List<AdSolutionContentApply> findByContractNumber(String contractNumber){
	   List<AdSolutionContentApply> adSolutionContentApplys=adSolutionContentApplyRepository.findByContractNumber(contractNumber);
	   try {
		if(adSolutionContentApplys!=null){
			   for(AdSolutionContentApply apply:adSolutionContentApplys){
				   User createUser=userService.findByUcid(apply.getCreateOperator());
					if(createUser!=null){
						apply.setCreator(createUser.getRealname());
						apply.setCreatorEmail(createUser.getEmail());
					}  
			   }
		   }
	} catch (Exception e) {
		LoggerHelper.err(getClass(), "获取提前上线申请人真实姓名报错", e);
	}
		return adSolutionContentApplys;
   }
   
   /**
    * @param approved 
    * true:获取提前上线批准邮件内容; false:当合同生效时,提醒提前上线申请人绑定合同的邮件内容
    */
    public OnlineApplyMailContent getMailContent4OnlineReqeust(AdSolutionContentApply apply,boolean approved){
    		OnlineApplyMailContent content=new OnlineApplyMailContent();
    		Set<String> toSet=new HashSet<String>();
    		Set<String> ccSet=new HashSet<String>();
    		if(approved){
    		ccSet.add(GcrmConfig.getConfigValueByKey("bufinance.baidu.mail"));
    		ccSet.add(GcrmConfig.getConfigValueByKey("contract.baidu.mail"));
    		ccSet.add(GcrmConfig.getConfigValueByKey("inneraudit.baidu.mail"));
    		//获取直接领导
    		List<User> users=userRightsService.findDirectLeaderByUcId(apply.getCreateOperator());
    		if(users!=null){
    			for(User user:users){
    				ccSet.add(user.getEmail());
    			}
    		}
    		//获取平台pm负责人
            Participant pmleader = participantService.findByKeyAndDescAndParticId(
                    adSolutionContentService.findOne(apply.getAdSolutionContentId()).getProductId().toString(),
                    DescriptionType.platform, ParticipantConstants.pm_leader.toString());
    		//获取平台变现主管
            Participant cashleader = participantService.findByKeyAndDescAndParticId(
                    adSolutionContentService.findOne(apply.getAdSolutionContentId()).getProductId().toString(),
                    DescriptionType.platform, ParticipantConstants.cash_leader.toString());
    		ccSet.add(userService.findByUsername(pmleader.getUsername()).getEmail());
    		ccSet.add(userService.findByUsername(cashleader.getUsername()).getEmail());
    		content.setContentNumber(apply.getAdSolutiionContentNumber());
    		content.setCustomerName(customerService.findByCustomerNumber(adSolutionService.findById(apply.getAdSolutionId()).getCustomerNumber()).getCompanyName());
    		}else{
    		content.setAdSolutionNumber(adSolutionService.findById(apply.getAdSolutionId()).getNumber());	
    		}
    		toSet.add(apply.getCreatorEmail());
    		content.setSendTo(toSet);
    		content.setCc(ccSet);
    		content.setOperator(apply.getCreator());
    		content.setContractNumber(apply.getContractNumber());
    		content.setAdSolutionURL(GcrmConfig.getConfigValueByKey("app.adSolution.url")+apply.getAdSolutionId());
        	return content;
    }
    public AdSolutionContentApply withdrawOnlineApplyById(Long applyId, LocaleConstants locale){
        AdSolutionContentApply apply = null;
        try {
        	apply = adSolutionContentApplyRepository.findAdContentApply(applyId);
            if (apply == null||StringUtils.isEmpty(String.valueOf(apply.getId()))) {
                throw new CRMRuntimeException("onlineApplyIderror");
            }
            // 撤销审批流，同时更新上线申请单状态为撤销
            withdrawOnlineApplyProcess(apply, locale);
            apply.setUpdateTime(new Date());
            apply.setUpdateOperator(RequestThreadLocal.getLoginUser().getUcid());
            apply.setTaskInfo(gcrmTaskInfoService.getTaskInfo(RequestThreadLocal.getLoginUser().getRealname(), messagePrefix + "withdrawn"));
            adSolutionContentApplyRepository.saveAdContentApply(apply);
        } catch (Exception e) {
        	 throw new CRMRuntimeException("process.withdraw.failed",e.getMessage());
        }
        return apply;
    }
    
    public AdSolutionContentApply findAdContentApplyByConId(Long contentId){
    	 return adSolutionContentApplyRepository.findAdContentApplyByConId(contentId);
    }
    
   

	@Override
	public void saveAdContentApplyApproval(AdContentApplyApprovalRecord adContentApplyApprovalRecord) {
		 adSolutionContentApplyRepository.saveAdContentApplyApproval(adContentApplyApprovalRecord);
	}


	@Override
	public AdSolutionContentApply findAdContentApply(Long contentApplyId) {
		AdSolutionContentApply adSolutionContentApply=adSolutionContentApplyRepository.findAdContentApply(contentApplyId);
		User createUser=userService.findByUcid(adSolutionContentApply.getCreateOperator());
		if(createUser!=null){
		adSolutionContentApply.setCreator(createUser.getRealname());
		adSolutionContentApply.setCreatorEmail(createUser.getEmail());
		adSolutionContentApply.setContractUrl(ContractUrlUtilHelper.getContractDetailUrl(adSolutionContentApply.getContractNumber()));
		}
		return adSolutionContentApply;
	}
	

    public void remindersContentByMail(Long id, LocaleConstants currentLocale) {

        RemindRequest request = generateRemindRequest(RemindType.contentOnlineApply, id.toString());
        processService.remindByForeignKey(request);
    }

    private void withdrawOnlineApplyProcess(AdSolutionContentApply apply, LocaleConstants locale) {

        // 只有审核中的才可以撤销
        if (apply.getApprovalStatus() == ApprovalStatus.approving) {
            List<AdContentApplyApprovalRecord> records = findApprovalRecordsByOnlineApplyId(apply.getId(), locale);
            if (records!=null && records.size() > 0) {
                AdContentApplyApprovalRecord record = records.get(records.size()-1);
                processService.withdrawAndTerminateProcess(record.getProcessId(), RequestThreadLocal.getLoginUuapName(), StringUtils.EMPTY);
            }
            apply.setApprovalStatus(ApprovalStatus.withdrawn);
        }
	
	}

	private void updateStatus4ContentOnlineApply(AdSolutionContentApply adSolutionContentApply, User user, ApprovalStatus approvalStatus,String taskInfo) {
	    try {
	        if (approvalStatus != null) {
	        	adSolutionContentApply.setApprovalStatus(approvalStatus);
	        }
	        adSolutionContentApply.setTaskInfo(taskInfo);
	        adSolutionContentApply.setUpdateTime(new Date());
	        adSolutionContentApply.setUpdateOperator(user.getUcid());
	        adSolutionContentApplyRepository.saveAdContentApply(adSolutionContentApply);
	    } catch (Exception e) {
	        LoggerHelper.err(getClass(), e.getMessage(), e);
	        throw new CRMRuntimeException("adContentOnlineApply.updateStatus.error");
	    }
	}
	
	private CompleteBaseActivityReq generateCompleteActivityReq(AdContentApplyApprovalRecord approvalRecord, User user) {
	    CompleteBaseActivityReq req = new CompleteBaseActivityReq();
	    req.setActivityId(approvalRecord.getActivityId());
	    req.setProcessId(approvalRecord.getProcessId());
	    req.setPerformer(user.getUuapName());
	    req.setApproved(approvalRecord.getApprovalStatus());
	    req.setReason(approvalRecord.getApprovalSuggestion());
	    List<ActivityData> activityDataList = new ArrayList<ActivityData>();
	    req.setActivityDataList(activityDataList);
	
	    return req;
	}
	private AdContentApplyApprovalRecord generateApprovalRecord(Long contentApplyId, User operaterUser,
	        StartProcessResponse processResponse) {
		AdContentApplyApprovalRecord record = new AdContentApplyApprovalRecord();
	    record.setAdContentApplyId(contentApplyId);
	    record.setActDefId(processResponse.getActDefId());
	    record.setActivityId(processResponse.getFirstActivityId());
	    record.setProcessId(processResponse.getProcessId());
	    record.setCreateOperator(operaterUser.getUcid());
		record.setCreateTime(new Date());
	    return record;
	}
	
	private void prepare(BaseStartProcessBean startBean, User operaterUser, AdSolutionContentApply adSolutionContentApply) {
	    startBean.setStartUser(operaterUser.getUsername());
	    startBean.setPackageId(GcrmConfig.getConfigValueByKey("adContentOnlineApply.package.id"));
	    startBean.setProcessDefineId(processDefineId);
	    startBean.setProcessStartBeanName("AdContentApplyProcessStartBean");
	    ProcessNameI18n processNameI18n = processNameDao.findByProcessDefIdAndLocale(processDefineId,
	            LocaleConstants.en_US.name());
	    if (processNameI18n != null) {
	        startBean.setProcessName(processNameI18n.getProcessName());
	    }
	    Long contentApplyId = adSolutionContentApply.getId();
	    startBean.setForeignKey(contentApplyId.toString());
	    //为工作流个节点绑定获取操作人的handler
	    startBean.putAssignmentHandler2Map(ParticipantConstants.startUser,applicationContext.getBean("processInstanceStarterHandler", IAssignmentHandler.class));
	    startBean.putAssignmentHandler2Map(ParticipantConstants.dept_manager,applicationContext.getBean("chainResponsibilityAssignmentHandler", IAssignmentHandler.class));
	    startBean.putAssignmentHandler2Map(ParticipantConstants.finance_manager,applicationContext.getBean("defaultAssignmentHandler", IAssignmentHandler.class));
	    startBean.putAssignmentHandler2Map(ParticipantConstants.gcrm_gpm_manager,applicationContext.getBean("defaultAssignmentByPositionHandler", IAssignmentHandler.class));
	}

    private RemindRequest generateRemindRequest(RemindType type, String key) {
        int email = 0;
        RemindRequest request = new RemindRequest();
        request.setReminder(RequestThreadLocal.getLoginUuapName());
        request.setNotifyType(email);
        request.setType(type);
        request.setKey(key);
        return request;
    }
}
