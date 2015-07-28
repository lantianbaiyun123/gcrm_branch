package com.baidu.gcrm.publish.model;


public enum PublishMailType {
	/**催办*/
	reminders,
	/**上线*/
	online,
	/**下线*/
	offline,
	/**终止投放*/
	terminate,
	/**上线汇总提醒*/
	onlineCollection,
	/**物料变更提醒*/
	materCollection,
	/**物料上线*/
	materOngoing;
	
	
	
	
	public static PublishMailType valueOf(Integer value){
		if(value == null){
			return null;
		}
		PublishMailType[] values = PublishMailType.values(); 
		for(PublishMailType type : values){
			if(type.ordinal() == value){
				return type;
			}
		}
		return null;
	}
}
