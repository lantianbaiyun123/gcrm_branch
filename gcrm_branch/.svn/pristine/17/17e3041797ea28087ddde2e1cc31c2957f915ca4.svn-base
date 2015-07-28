package com.baidu.gcrm.report.converter.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.stereotype.Component;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.report.converter.IColumnConvert;

@Component("enumColumnConvert")
public class EnumColumnConvert implements IColumnConvert<String> {

    @Override
    public void convertColumnValue(Object item, String field1, String field2, String expression, LocaleConstants locale) {
        try {
            Object initial = PropertyUtils.getProperty(item, field1);
            if (initial != null) {
                String[] values = expression.split(";", 0);
                String temp[] = null;
                for (String value : values) {
                    temp = value.split("\\|", 2);
                    if (initial.toString().equals(temp[0])) {
                        PropertyUtils.setSimpleProperty(item, field2, MessageHelper.getMessage(temp[1], locale));
                        return;
                    }
                }

            }
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "report column 字段{}-->{}转换发生错误", field1, field2, e);
        }

    }

}
