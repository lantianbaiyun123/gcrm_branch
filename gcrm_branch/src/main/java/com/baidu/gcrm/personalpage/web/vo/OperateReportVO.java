package com.baidu.gcrm.personalpage.web.vo;

import java.util.List;

import com.baidu.gcrm.personalpage.model.OperateReport;

public class OperateReportVO {
	private List<OperateReport> quotationList;
	private List<OperateReport> customerList;
	private List<OperateReport> solutionList;
	private List<OperateReport> positionList;
	
	public List<OperateReport> getQuotationList() {
		return quotationList;
	}
	public void setQuotationList(List<OperateReport> quotationList) {
		this.quotationList = quotationList;
	}
	public List<OperateReport> getCustomerList() {
		return customerList;
	}
	public void setCustomerList(List<OperateReport> customerList) {
		this.customerList = customerList;
	}
	public List<OperateReport> getSolutionList() {
		return solutionList;
	}
	public void setSolutionList(List<OperateReport> solutionList) {
		this.solutionList = solutionList;
	}
	public List<OperateReport> getPositionList() {
		return positionList;
	}
	public void setPositionList(List<OperateReport> positionList) {
		this.positionList = positionList;
	}
}
