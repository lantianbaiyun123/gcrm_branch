package com.baidu.gcrm.bpm.service;

import java.util.List;

import com.baidu.gcrm.bpm.model.ActivityNameI18n;
import com.baidu.gcrm.bpm.vo.CompleteActivityResponse;
import com.baidu.gcrm.bpm.vo.ProcessInfo;
import com.baidu.gcrm.bpm.vo.ProcessQueryBean;
import com.baidu.gcrm.bpm.vo.ProcessQueryConditionBean;
import com.baidu.gcrm.bpm.vo.RemindRequest;
import com.baidu.gcrm.bpm.web.helper.Activity;
import com.baidu.gcrm.bpm.web.helper.ActivityInfo;
import com.baidu.gcrm.bpm.web.helper.ApprovalRecordBean;
import com.baidu.gcrm.bpm.web.helper.CompleteActivityReq;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.process.vo.TaskCount;
import com.baidu.gwfp.ws.exception.GWFPException;

public interface IBpmProcessService {
	List<Activity> getCurrentActivitiesOfUser(String username);
	
	List<Activity> getCurrentActivitiesOfUser(String username, LocaleConstants locale);
	
	List<ActivityNameI18n> findActivityNameI18n(String processDefId, List<String> activityIds, LocaleConstants locale);
	
	List<ApprovalRecordBean> getApprovalRecords(String processId);
	
	CompleteActivityResponse completeActivity(CompleteActivityReq request);
	
	ActivityInfo getActivityInfoByActivityId(String activityId);
	
	void remindByForeignKey(RemindRequest conditions);
	
	/**
	 * 撤销流程，即终止流程
	 * @param processId
	 * @param executeUser
	 * @param reason
	 */
	void withdrawAndTerminateProcess(String processId, String executeUser, String reason);
	
	List<ProcessQueryBean> queryProcess(ProcessQueryConditionBean queryBean);
	
	void completeActivityByCondition(ProcessQueryConditionBean queryBean, CompleteActivityReq req);
	
	void completeActivityByConditionNoException(ProcessQueryConditionBean queryBean, CompleteActivityReq req);
	
	void terminateProcess(String processId, String executeUser, String reason) throws GWFPException;
	
	ProcessQueryBean getProcessByProcessId(String processId) throws CRMBaseException;

	/**
	 * 获取当前用户的待办数和最新的审批类待办
	 * @param username
	 * @return
	 */
	TaskCount getTaskCountOfCurrentUser(String username);
	
	void terminateProcessByAdContentId(List<Long> adContentIds);
	
	/** 通过流程外键（即合同编号）获取合同审批流程状态和任务信息
	 * @param foreignKey 流程外键，即合同编号
	 * @return
	 */
	ProcessInfo getCmsProcessInfoByForeignKey(String foreignKey);
} 
