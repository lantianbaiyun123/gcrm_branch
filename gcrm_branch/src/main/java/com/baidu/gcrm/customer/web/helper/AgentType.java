package com.baidu.gcrm.customer.web.helper;

public enum AgentType {
	exclusive,
	normal,
	second;
	
	public int getValue() {
        return this.ordinal();
    }
	
	public static AgentType valueOf(Integer value){
		if(value == null){
			return null;
		}
		AgentType[] values = AgentType.values(); 
		for(AgentType agentType : values){
			if(agentType.ordinal() == value){
				return agentType;
			}
		}
		return null;
	}
}
