package com.baidu.gcrm.report.converter.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.report.converter.IColumnConvert;

@Component("i18NColumnConvert")
public class I18NColumnConvert implements IColumnConvert<String> {

	@Autowired
	private I18nKVService I18nKVServiceImpl;

	public void convertColumnValue(Object item, String field1, String field2,
			String expression, LocaleConstants locale) {
		try {
			Object initial = PropertyUtils.getProperty(item, field1);
			String value = I18nKVServiceImpl.getAndLoadI18Info(Class.forName(expression),  (Long)initial, locale).getValue();
			PropertyUtils.setSimpleProperty(item, field2, value);
		} catch (Exception e) {
			LoggerHelper.err(getClass(), "report column 字段{}-->{}转换发生错误", field1,
					field2);
		}
	}

}
