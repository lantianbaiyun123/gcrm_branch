package com.baidu.gcrm.customer.web.helper;

public enum CustomerDataState {
	create,
	update;

	
	public static CustomerDataState valueOf(Integer value){
		if(value == null){
			return null;
		}
		CustomerDataState[] values = CustomerDataState.values(); 
		for(CustomerDataState state : values){
			if(state.ordinal() == value){
				return state;
			}
		}
		return null;
	}
}
