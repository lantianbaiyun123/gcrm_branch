package com.baidu.gcrm.customer.web.helper;

public enum CustomerState {
	waiting_take_effect,
	have_taken_effect,
	disabled;
	
	public boolean isWating(){
		return this.equals(waiting_take_effect);
	}
	
	public boolean isApproved(){
		return this.equals(have_taken_effect);
	}
	
	public boolean isDisabled(){
		return this.equals(disabled);
	}
	
	public static CustomerState valueOf(Integer value){
		if(value == null){
			return null;
		}
		CustomerState[] values = CustomerState.values(); 
		for(CustomerState state : values){
			if(state.ordinal() == value){
				return state;
			}
		}
		return null;
	}
}
