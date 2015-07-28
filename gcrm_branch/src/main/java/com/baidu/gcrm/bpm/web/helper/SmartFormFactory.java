package com.baidu.gcrm.bpm.web.helper;

public class SmartFormFactory {
	public static String generateFormPage(SmartFormRequest request){
		if(request.getView()){
			return "view";
		}
		return request.getActDefId();
	}
}