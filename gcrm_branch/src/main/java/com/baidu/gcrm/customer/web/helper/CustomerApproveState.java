package com.baidu.gcrm.customer.web.helper;

public enum CustomerApproveState {
	saving,
	refused,
	approving,
	approved;
	
	public boolean isApproving(){
		return this.equals(approving);
	}
	
	public boolean isApproved() {
		return this.equals(approved);
	}
	
	public static CustomerApproveState valueOf(Integer value){
		if(value == null){
			return null;
		}
		CustomerApproveState[] values = CustomerApproveState.values(); 
		for(CustomerApproveState approvaeState : values){
			if(approvaeState.ordinal() == value){
				return approvaeState;
			}
		}
		return null;
	}
}
