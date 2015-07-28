package com.baidu.gcrm.report.core;

import java.util.HashMap;
import java.util.Map;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.auth.RequestThreadLocal;
import com.baidu.gcrm.common.page.Page;

public class ReportContext<T> {

	private Map<String, Object> dataMap;

	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public ReportContext() {
		super();
		dataMap = new HashMap<String, Object>();
		registerUtils();
	}
    
	public void putLocale(LocaleConstants locale) {
		dataMap.put("reportlocale", locale);
	}

	public LocaleConstants getLocale() {
		LocaleConstants locale = (LocaleConstants) dataMap.get("reportlocale");
		if (locale == null) {
			locale = RequestThreadLocal.getLocale();
			dataMap.put("reportlocale", locale);
		}
		return locale;
	}

	public void put(String key, Object value) {
		dataMap.put(key, value);
	}

	public Object get(String key) {
		return dataMap.get(key);
	}

	public void putPageCritera(Page<T> page) {
		dataMap.put("pageCritera", page);
	}

	@SuppressWarnings("unchecked")
	public Page<T> getPageCritera() {
		return (Page<T>) dataMap.get("pageCritera");
	}
	private void registerUtils(){
		dataMap.put("templateUtil", new TemplateUtil());
		dataMap.put("reportlocale", getLocale());
	}
}
