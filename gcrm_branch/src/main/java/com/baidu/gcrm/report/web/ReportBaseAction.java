package com.baidu.gcrm.report.web;

import java.io.IOException;
import java.io.Writer;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.json.JsonBeanUtil;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.common.util.HttpHeaderUtil;
import com.baidu.gcrm.report.config.TemplateConfig;
import com.baidu.gcrm.report.config.TransformerConfig;
import com.baidu.gcrm.report.core.Report;
import com.baidu.gcrm.report.core.ReportContext;
import com.baidu.gcrm.report.core.ReportManager;
import com.baidu.gcrm.report.core.TemplateUtil;
import com.baidu.gcrm.report.service.IReportService;

/**
 * 
 * @author yangjianguo
 * 
 */
public abstract class ReportBaseAction<T> extends ControllerHelper {
    @Autowired
    private IReportService<T> reportService;

    public JsonBean<Page<T>> show(String reportId, Page<T> page) {
        ReportContext<T> context = getReportContext();
        context.putPageCritera(page);
        Report<T> report = ReportManager.getReportById(reportId, context);
        Page<T> pages = reportService.findReportPages(report);
        return JsonBeanUtil.convertBeanToJsonBean(pages);
    }

    public JsonBean<Page<T>> show(String reportId, String dataSourceId, Page<T> page) {
        ReportContext<T> context = getReportContext();
        context.putPageCritera(page);
        Report<T> report = ReportManager.getReportById(reportId, context);
        Page<T> pages = reportService.findReportPages(report, dataSourceId);
        return JsonBeanUtil.convertBeanToJsonBean(pages);
    }

    public void download(HttpServletRequest request, HttpServletResponse response, String reportId,
            String transformerId, String templateId, Page<T> page) {
        ReportContext<T> context = getReportContext();
        context.putPageCritera(page);
        Report<T> report = ReportManager.getReportById(reportId, context);
        TransformerConfig transConfig = report.getReportConfig().getTransformerConfig(transformerId);
        reportService.findReportPages(report, transConfig.getDataSourceId());
        TemplateConfig temConfig = transConfig.getTemplateConfig(templateId);
        context.put("transformerConfig", transConfig);
        Writer writer = null;
        String fileName = getExpectedFileName(temConfig.getDownLoadFileName());
        try {
            HttpHeaderUtil.setDownloadHeader(fileName, request, response, true);
            writer = response.getWriter();
            TemplateUtil.mergeToWriter(temConfig.getName(), context, writer);
            writer.flush();
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "文件{}下载发生错误:", fileName, e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                LoggerHelper.err(getClass(), "关闭下载数据流发生错误:文件名{}", fileName, e);
            }
        }

    }

    protected String getExpectedFileName(String configName){
        return String.format(configName, DateUtils.getDate2String(DateUtils.YYYYMMDD, new Date()));
    }

    public void download(HttpServletRequest request, HttpServletResponse response, String reportId, Page<T> page) {
        ReportContext<T> context = getReportContext();
        context.putPageCritera(page);
        Report<T> report = ReportManager.getReportById(reportId, context);
        TransformerConfig transConfig = report.getReportConfig().getDefaultTransformerConfig();
        reportService.findReportPages(report, transConfig.getDataSourceId());
        TemplateConfig temConfig = transConfig.getTemplateConfigs().get(0);
        context.put("transformerConfig", transConfig);
        Writer writer = null;
        String fileName = getExpectedFileName(temConfig.getDownLoadFileName());
        try {
            HttpHeaderUtil.setDownloadHeader(fileName, request, response, true);
            writer = response.getWriter();
            TemplateUtil.mergeToWriter(temConfig.getName(), context, writer);
            writer.flush();
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "文件{}下载发生错误:", fileName, e);
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                LoggerHelper.err(getClass(), "关闭下载数据流发生错误:文件名{}", fileName, e);
            }
        }

    }

    protected ReportContext<T> getReportContext() {
        ReportContext<T> context = new ReportContext<T>();
        return context;
    }
}
