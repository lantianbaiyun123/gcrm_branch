package com.baidu.gcrm.report.core;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.druid.sql.SQLUtils;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.report.config.ColumnConfig;
import com.baidu.gcrm.report.config.InputBinding;
import com.baidu.gcrm.report.config.QueryConfig;
import com.baidu.gcrm.report.converter.IColumnConvert;

/**
 * 
 * @author yangjianguo
 * 
 */
public class ReportManager {

    public static <T> Report<T> getReportById(String reportId, ReportContext<T> context) {

        return new Report<T>(context, reportId);

    }

    public static String getReportSql(Report<?> report) {
        String dsId = report.getReportConfig().getDefaultDataSourceConfig().getId();
        return getReportSql(report, dsId);

    }

    public static String getReportSql(Report<?> report, String dataSourceId) {
        QueryConfig qc = report.getReportConfig().getDataSourceConfig(dataSourceId).getQueryConfig();
        String sql = null;
        if (qc.isDynamic()) {
            StringWriter writer = new StringWriter();
            TemplateUtil.evaluate(report.getReportContext().getDataMap(), writer, "Report sql parse:", qc.getQuery());
            writer.flush();
            sql = writer.toString();
        } else {
            sql = qc.getQuery();
        }
        return SQLUtils.formatMySql(sql);
    }

    public static List<?> getInputBindingData(Report<?> report) {
        String dataSourceId = report.getReportConfig().getDefaultDataSourceConfig().getId();
        return getInputBindingData(report, dataSourceId);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List<?> getInputBindingData(Report<?> report, String dataSourceId) {
        List datas = new ArrayList();
        List<InputBinding> inputs = report.getReportConfig().getDataSourceConfig(dataSourceId).getInputBindings();
        if (inputs != null) {
            for (InputBinding input : inputs) {
                Object value = null;
                try {
                    value =
                            PropertyUtils.getProperty(report.getReportContext().get(input.getObject()),
                                    input.getProperty());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (StringUtils.isNotBlank(input.getIfTrue())) {
                    StringWriter sw = new StringWriter();
                    String inputstr = "#if(" + input.getIfTrue() + ") true #else false #end";
                    TemplateUtil.evaluate(report.getReportContext().getDataMap(), sw, "Input binding parse:", inputstr);
                    sw.flush();
                    if ("true".equals(sw.toString().trim())) {
                        datas.add(value);
                    }
                } else {
                    datas.add(value);

                }
            }
        }
        return datas;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Page<?> getPageDataAfterColumnConverted(Page<?> page, Report<?> report, String dataSourceId) {
        List<?> data = page.getContent();
        List<ColumnConfig> ccs = report.getReportConfig().getDataSourceConfig(dataSourceId).getColumnConfigs();
        if (ccs != null) {
            for (ColumnConfig cc : ccs) {
                if (StringUtils.isNotBlank(cc.getConverter())) {
                    IColumnConvert icc = (IColumnConvert) report.getReportContext().get(cc.getConverter());
                    if (data != null) {
                        for (Object obj : data) {
                            icc.convertColumnValue(obj, cc.getField1(), cc.getField2(), cc.getExpression(), report
                                    .getReportContext().getLocale());
                        }
                    }
                }
            }
        }
        return page;
    }

    public static Page<?> getPageDataAfterColumnConverted(Page<?> page, Report<?> report) {
        String dsId = report.getReportConfig().getDefaultDataSourceConfig().getId();
        return getPageDataAfterColumnConverted(page, report, dsId);
    }

}
