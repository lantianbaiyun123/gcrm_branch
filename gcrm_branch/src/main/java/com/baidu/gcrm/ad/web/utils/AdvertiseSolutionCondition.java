package com.baidu.gcrm.ad.web.utils;

import java.util.List;

import com.baidu.gcrm.ad.model.AdvertiseMaterialApply.MaterialApplyState;
import com.baidu.gcrm.ad.model.AdvertiseSolutionApproveState;
import com.baidu.gcrm.common.page.PageWrapper;
@SuppressWarnings("rawtypes")
public class AdvertiseSolutionCondition extends PageWrapper {
	
	private static final long serialVersionUID = 2459152617406126804L;
	
	public enum QueryType {
		number,//方案编号
		contractnum,//合同编号
		advertisers,//广告主
		customerid,//公司id
		ponumber;//po编号
    }
	/**
	 * 查询类型
	 */
	private QueryType queryType = QueryType.number;
	/**
	 * 查询字段内容
	 */
	private String queryStr;
	
	/**
	 * 方案状态
	 */
	private AdvertiseSolutionApproveState solutionStatus;
	/**
	 * 投放开始时间
	 */
	private String startDate;
	/**
	 * 投放结束时间
	 */
	private String endDate;
	/**
	 * 方案ID列表
	 */
	private List<Long> solutionIdList;
	
	/**
	 * 提交人
	 */
	private Long createOperator;
	
	/**
	 * 提交开始时间（首页查询）
	 */
	private String createStartDate;
	/**
	 * 首页查询 加条件
	 */
	private List<AdvertiseSolutionApproveState> solutionStatusList;
	/**
	 * 首页查询加条件
	 */
	private List<MaterialApplyState> materialApplyStateList;
	/**
	 * 客户详情页查询使用客户id
	 */
	private String cusid;
	/**
	 * 客户详情页查询使用非直客客户名称
	 */
	private String customerName;
	
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCusid() {
		return cusid;
	}
	public void setCusid(String cusid) {
		this.cusid = cusid;
	}
	
	public List<MaterialApplyState> getMaterialApplyStateList() {
		return materialApplyStateList;
	}
	public void setMaterialApplyStateList(
			List<MaterialApplyState> materialApplyStateList) {
		this.materialApplyStateList = materialApplyStateList;
	}
	public List<AdvertiseSolutionApproveState> getSolutionStatusList() {
		return solutionStatusList;
	}
	public void setSolutionStatusList(
			List<AdvertiseSolutionApproveState> solutionStatusList) {
		this.solutionStatusList = solutionStatusList;
	}
	public QueryType getQueryType() {
		return queryType;
	}
	public void setQueryType(QueryType queryType) {
		this.queryType = queryType;
	}
	public String getQueryStr() {
		return queryStr;
	}
	public void setQueryStr(String queryStr) {
		this.queryStr = queryStr;
	}
	public AdvertiseSolutionApproveState getSolutionStatus() {
		return solutionStatus;
	}
	public void setSolutionStatus(AdvertiseSolutionApproveState solutionStatus) {
		this.solutionStatus = solutionStatus;
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
	public List<Long> getSolutionIdList() {
		return solutionIdList;
	}
	public void setSolutionIdList(List<Long> solutionIdList) {
		this.solutionIdList = solutionIdList;
	}
	public Long getCreateOperator() {
		return createOperator;
	}
	public void setCreateOperator(Long createOperator) {
		this.createOperator = createOperator;
	}
	public String getCreateStartDate() {
		return createStartDate;
	}
	public void setCreateStartDate(String createStartDate) {
		this.createStartDate = createStartDate;
	}
}
