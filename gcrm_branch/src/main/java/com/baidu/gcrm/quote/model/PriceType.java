package com.baidu.gcrm.quote.model;

public enum PriceType {
	/** 广告单价 */
	unit,
	/** 分成 */
	ratio,
	/** 返点*/
	rebate;
	
	public static PriceType valueOf(Integer value){
		if(value == null){
			return null;
		}
		
		PriceType[] values = PriceType.values();
		for(PriceType val : values ){
			if(val.ordinal() == value){
				return val;
			}
		}
		
		return null;
	}
}
