package com.baidu.gcrm.personalpage.web.vo;


public enum ModuleType {
	/**个人待办*/
	unprocess,
	/**个人提交*/
	submit,
	/**运营情况*/
	operation,
	/**模块统计*/
	moduleCount;
	
	public static ModuleType valueOf(Integer value){
		if(value == null){
			return null;
		}
		ModuleType[] values = ModuleType.values(); 
		for(ModuleType type : values){
			if(type.ordinal() == value){
				return type;
			}
		}
		return null;
	}
}
