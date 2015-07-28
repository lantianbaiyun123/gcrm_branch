package com.baidu.gcrm.bpm.web.helper;

public class AdPlanProcessStartBean extends StartProcessBean{
	private Long adPlanId;
	
	/** 报价低于8折 */
	private String isLessThanEightyPercent;
	/** 是否插单 */
	private String isInsertOrder;
	/** 报价低于5折 */
	private String isLessThanFiftyPercent;
	/** 未备案 */
	private String isWithoutRecord;
	/** 实际分成比例低于我方分成比例 */
	private String isActualRatioLessThanMine;
	/** 是否需要上级审批 */
	private String needSuperiorApproval;
	
	public Long getAdPlanId() {
		return adPlanId;
	}
	
	public void setAdPlanId(Long adPlanId) {
		this.adPlanId = adPlanId;
	}

	@Override
	public void validate() {
		
	}

	public String getIsLessThanEightyPercent() {
		return isLessThanEightyPercent;
	}

	public void setIsLessThanEightyPercent(String isLessThanEightyPercent) {
		this.isLessThanEightyPercent = isLessThanEightyPercent;
	}

	public String getIsInsertOrder() {
		return isInsertOrder;
	}

	public void setIsInsertOrder(String isInsertOrder) {
		this.isInsertOrder = isInsertOrder;
	}

	public String getIsLessThanFiftyPercent() {
		return isLessThanFiftyPercent;
	}

	public void setIsLessThanFiftyPercent(String isLessThanFiftyPercent) {
		this.isLessThanFiftyPercent = isLessThanFiftyPercent;
	}

	public String getIsWithoutRecord() {
		return isWithoutRecord;
	}

	public void setIsWithoutRecord(String isWithoutRecord) {
		this.isWithoutRecord = isWithoutRecord;
	}

	public String getIsActualRatioLessThanMine() {
		return isActualRatioLessThanMine;
	}

	public void setIsActualRatioLessThanMine(String isActualRatioLessThanMine) {
		this.isActualRatioLessThanMine = isActualRatioLessThanMine;
	}

	public String getNeedSuperiorApproval() {
		return needSuperiorApproval;
	}

	public void setNeedSuperiorApproval(String needSuperiorApproval) {
		this.needSuperiorApproval = needSuperiorApproval;
	}

	@Override
	public String toString() {
		return "AdPlanProcessStartBean [adPlanId=" + adPlanId + ", isLessThanEightyPercent=" + isLessThanEightyPercent
				+ ", isInsertOrder=" + isInsertOrder + ", isLessThanFiftyPercent=" + isLessThanFiftyPercent
				+ ", isWithoutRecord=" + isWithoutRecord + ", isActualRatioLessThanMine=" + isActualRatioLessThanMine
				+ ", needSuperiorApproval=" + needSuperiorApproval + "]";
	}

}