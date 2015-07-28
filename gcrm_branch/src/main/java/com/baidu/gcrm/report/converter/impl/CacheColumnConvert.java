package com.baidu.gcrm.report.converter.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.report.converter.IColumnConvert;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.model.Industry;
import com.baidu.gcrm.valuelistcache.service.AbstractValuelistCacheService;

@Component("cacheColumnConvert")
public class CacheColumnConvert implements IColumnConvert<String> {
	@Resource(name = "billingModelServiceImpl")
	AbstractValuelistCacheService<BillingModel> billingModelService;
	@Resource(name = "industryServiceImpl")
	AbstractValuelistCacheService<Industry> IndustryTypeService;

	@Override
	public void convertColumnValue(Object item, String field1, String field2,
			String expression, LocaleConstants locale) {
		try {
			Object initial = PropertyUtils.getProperty(item, field1);
			if(initial!=null){
			String value = null;
			if (Class.forName(expression) == BillingModel.class) {
				value = billingModelService.getByIdAndLocale(
						(Serializable) initial, locale).getI18nName();
			}
			if (Class.forName(expression) == Industry.class) {
				value = IndustryTypeService.getByIdAndLocale(
						(Serializable) initial, locale).getI18nName();
			}
			PropertyUtils.setSimpleProperty(item, field2, value);
			}
		} catch (Exception e) {
			LoggerHelper.err(getClass(), "report column 字段{}-->{}转换发生错误",
					field1, field2,e);
		}
	}

}
