package com.baidu.gcrm.report.core;

import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.common.i18n.MessageHelper;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.velocity.VelocityUtil;
import com.baidu.gcrm.report.config.ColumnConfig;

public class TemplateUtil extends VelocityUtil {

    public static final String CHARSET = "UTF-8";

    public static boolean evaluate(Map<String, Object> model, Writer writer, String logTag, Reader reader) {
        VelocityEngine velocityEngine = ServiceBeanFactory.getVelocityEngine();
        VelocityContext velocityContext = new VelocityContext(model);
        return velocityEngine.evaluate(velocityContext, writer, logTag, reader);

    }

    public static boolean evaluate(Map<String, Object> model, Writer writer, String logTag, String instring) {
        VelocityEngine velocityEngine = ServiceBeanFactory.getVelocityEngine();
        VelocityContext velocityContext = new VelocityContext(model);
        return velocityEngine.evaluate(velocityContext, writer, logTag, new StringReader(instring));

    }

    public static String merge(String fullPath, ReportContext<?> context) {
        VelocityEngine velocityEngine = ServiceBeanFactory.getVelocityEngine();
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, fullPath, CHARSET, context.getDataMap());
    }

    public static void mergeToWriter(String fullPath, ReportContext<?> context,Writer writer) {
        VelocityEngine velocityEngine = ServiceBeanFactory.getVelocityEngine();
        VelocityEngineUtils.mergeTemplate(velocityEngine, fullPath, CHARSET, context.getDataMap(), writer);
    }
    public static boolean isNotEmpty(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj instanceof String) {
            return obj.toString().trim().length() > 0;
        } else {
            return true;
        }
    }

    public String localize(String key, LocaleConstants locale) {
        return MessageHelper.getMessage(key, locale);
    }

    public String displayCellValue(Object obj, ColumnConfig cc) {

        try {
            Object value = null;
            if (StringUtils.isNotBlank(cc.getField2())) {
                value = PropertyUtils.getProperty(obj, cc.getField2());
            } else {
                value = PropertyUtils.getProperty(obj, cc.getField1());
                if (value instanceof Date) {
                    return DateUtils.getDate2String(cc.getPattern(), (Date) value);
                }
            }
            if (value != null) {

                return value.toString();
            }
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "对象字段取值失败:", e);
        }
        return "";

    }

    public String escapeDelimiter(Object target, String delimiter) {
        if (target == null)
            return "";
        String tmp = target.toString();
        if (tmp.indexOf(delimiter) >= 0) {
            if (tmp.indexOf("\"") >= 0) {
                tmp = tmp.replaceAll("\"", "\"\"");
            }
            return "\"" + tmp + "\"";
        } else
            return tmp;
    }
}
