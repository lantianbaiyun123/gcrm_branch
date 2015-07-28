package com.baidu.gcrm.common;

import java.util.HashMap;
import java.util.Map;

public enum LocaleConstants {
	zh_CN("zh_cn"),
	en_US("en_us");
	
	static Map<String,LocaleConstants> allLocaleMap = new HashMap<String,LocaleConstants> ();
	static{
		for(LocaleConstants currLocaleConstants : LocaleConstants.values()){
			String[] localeSplitStr = currLocaleConstants.localeName.split("_");
			for(String temStr : localeSplitStr){
				allLocaleMap.put(temStr, currLocaleConstants);
			}
			allLocaleMap.put(currLocaleConstants.localeName, currLocaleConstants);
		}
	}
	private String localeName = null;
	LocaleConstants(String localeName){
		this.localeName = localeName;
	}
	
	public static LocaleConstants getLocaleConstantsByName(String localeStr){
		return allLocaleMap.get(localeStr.toLowerCase());
	}
	
	public static void main(String[] args) {
		System.out.println(LocaleConstants.en_US.toString().toLowerCase());
	}
	
}