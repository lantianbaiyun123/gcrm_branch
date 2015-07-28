package com.baidu.gcrm.quote.model;

public enum QuotationApproveStatus {
	/** 待提交 */
	SAVING,
	/** 审批中 */
	APPROVING,
	/** 审批通过 */
	APPROVED,
	/** 驳回 */
	CANCEL;
	
	public static QuotationApproveStatus valueOf(Integer value){
		if(value == null){
			return null;
		}
		QuotationApproveStatus[] values = QuotationApproveStatus.values(); 
		for(QuotationApproveStatus status : values){
			if(status.ordinal() == value){
				return status;
			}
		}
		return null;
	}
}
