package com.baidu.gcrm.quote.web.utils;

import java.util.Date;
import java.util.List;

import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.quote.model.BusinessType;
import com.baidu.gcrm.quote.model.QuotationApproveStatus;
import com.baidu.gcrm.quote.model.QuotationStatus;
import com.baidu.gcrm.resource.site.model.Site;
import com.baidu.gcrm.valuelistcache.model.AgentRegional;
@SuppressWarnings("rawtypes")
public class QuotationMainCondition extends PageWrapper {
	
	/**
	 * 平台
	 */
	private String advertisingPlatformId;
	
	/**
	 * 站点区域
	 */
	private String siteId;
	/**
	 * 业务类型
	 */
	private BusinessType businessType;
	
	/**
	 * 审批状态
	 */
	private QuotationApproveStatus approveStatus;
	/**
	 * 标杆价状态
	 */
	private QuotationStatus status;
	/**
	 * 投放开始时间
	 */
	private String startDate;
	/**
	 * 投放结束时间
	 */
	private String endDate;
	/**
	 * 提交人
	 */
	private String createOperator;
	/**
	 * 投放站点列表
	 */
	private List<Site> siteList;
	/**
	 * 区域列表
	 */
	List<AgentRegional> agentList;
	/**
	 * 站点区域id列表
	 */
	List<Long> siteOrAgentIdList;
	/**
	 * 提交人id列表
	 */
	List<Long> createOperatorList;
	/***
	 * mainId
	 * @return
	 */
	private Long MainId;
	/**
	 * 提交开始时间（首页查询）
	 */
	private String createStartDate;
	
	List<Long> siteIdList;
	
	List<Long> agentIdList;
	
	List<Long> platIdList;
	
	public List<Long> getPlatIdList() {
		return platIdList;
	}
	public void setPlatIdList(List<Long> platIdList) {
		this.platIdList = platIdList;
	}
	public List<Long> getSiteIdList() {
		return siteIdList;
	}
	public void setSiteIdList(List<Long> siteIdList) {
		this.siteIdList = siteIdList;
	}
	public List<Long> getAgentIdList() {
		return agentIdList;
	}
	public void setAgentIdList(List<Long> agentIdList) {
		this.agentIdList = agentIdList;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public QuotationApproveStatus getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(QuotationApproveStatus approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public BusinessType getBusinessType() {
		return businessType;
	}
	public void setBusinessType(BusinessType businessType) {
		this.businessType = businessType;
	}
	public QuotationStatus getStatus() {
		return status;
	}
	public void setStatus(QuotationStatus status) {
		this.status = status;
	}
	public String getAdvertisingPlatformId() {
		return advertisingPlatformId;
	}
	public void setAdvertisingPlatformId(String advertisingPlatformId) {
		this.advertisingPlatformId = advertisingPlatformId;
	}
	public String getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(String createOperator) {
		this.createOperator = createOperator;
	}
	public List<Site> getSiteList() {
		return siteList;
	}
	public void setSiteList(List<Site> siteList) {
		this.siteList = siteList;
	}
	public List<AgentRegional> getAgentList() {
		return agentList;
	}
	public void setAgentList(List<AgentRegional> agentList) {
		this.agentList = agentList;
	}
	public List<Long> getSiteOrAgentIdList() {
		return siteOrAgentIdList;
	}
	public void setSiteOrAgentIdList(List<Long> siteOrAgentIdList) {
		this.siteOrAgentIdList = siteOrAgentIdList;
	}
	public List<Long> getCreateOperatorList() {
		return createOperatorList;
	}
	public void setCreateOperatorList(List<Long> createOperatorList) {
		this.createOperatorList = createOperatorList;
	}
	/**
	 * @return the mainId
	 */
	public Long getMainId() {
		return MainId;
	}
	/**
	 * @param mainId the mainId to set
	 */
	public void setMainId(Long mainId) {
		MainId = mainId;
	}
	public String getCreateStartDate() {
		return createStartDate;
	}
	public void setCreateStartDate(String createStartDate) {
		this.createStartDate = createStartDate;
	}
}
