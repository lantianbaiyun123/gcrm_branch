package com.baidu.gcrm.quote.model;

public enum QuotationStatus {
	/** 无效 */
	INVALID,
	/** 有效 */
	VALID,
	/** 超期失效 */
	OVERDUE_INVALID,
	/** 作废 */
	CANCEL;
	
	public static QuotationStatus valueOf(Integer value){
		if(value == null){
			return null;
		}
		QuotationStatus[] values = QuotationStatus.values(); 
		for(QuotationStatus status : values){
			if(status.ordinal() == value){
				return status;
			}
		}
		return null;
	}
}
