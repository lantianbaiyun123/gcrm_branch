package com.baidu.gcrm.report.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.json.JsonBean;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.report.converter.IColumnConvert;
import com.baidu.gcrm.report.core.ReportContext;
import com.baidu.gcrm.report.model.Hao123ReportBean;
import com.baidu.gcrm.report.web.vo.Hao123ReportPage;
import com.baidu.gcrm.resource.adplatform.model.AdPlatformSiteRelation;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformService;

/**
 * 
 * @author yangjianguo
 * 
 */
@Controller
@RequestMapping("/hao123/{reportId}")
public class ReportHao123Action extends ReportBaseAction<Hao123ReportBean> {
    @Resource(name = "i18NColumnConvert")
    private IColumnConvert<String> i18nColumnConvert;

    @Resource(name = "cacheColumnConvert")
    private IColumnConvert<String> cacheColumnConvert;

    @Resource(name = "enumColumnConvert")
    private IColumnConvert<String> enumColumnConvert;

    @Autowired
    IAdPlatformService adPlatformService;

    @RequestMapping("/show")
    @ResponseBody
    public JsonBean<Page<Hao123ReportBean>> showReport(@RequestBody Hao123ReportPage reportPage,
            @PathVariable("reportId") String reportId) {
        if (reportPage.getSiteId() == null) {
            reportPage.setAllSites(getAccessedSites(Constants.PLATFORM_HAO123_ID));
        }
        return show(reportId, reportPage);
    }

    @RequestMapping("/download")
    public void downloadReport(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("reportId") String reportId) {
        String beginStr = request.getParameter("beginStr");
        String endStr = request.getParameter("endStr");
        String queryAll = request.getParameter("queryAll");
        Hao123ReportPage reportPage = new Hao123ReportPage();
        String siteId = request.getParameter("siteId");
        if (StringUtils.isNotBlank(siteId)) {
            reportPage.setSiteId(Long.valueOf(siteId));
        } else {
            reportPage.setAllSites(getAccessedSites(Constants.PLATFORM_HAO123_ID));
        }
        reportPage.setBeginStr(beginStr);
        reportPage.setEndStr(endStr);
        reportPage.setQueryAll(Boolean.valueOf(queryAll));
        download(request, response, reportId, reportPage);
    }

    private String getAccessedSites(Long platformId) {
        List<AdPlatformSiteRelation> relations =
                adPlatformService.findAccessSites4Platform(platformId, this.getCurrentLocale());
        if (CollectionUtils.isNotEmpty(relations)) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (AdPlatformSiteRelation relation : relations) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(relation.getSiteId());
                i++;
            }
            return sb.toString();
        }
        return "-1";
    }

    @Override
    protected ReportContext<Hao123ReportBean> getReportContext() {
        ReportContext<Hao123ReportBean> context = new ReportContext<Hao123ReportBean>();
        context.put("i18NColumnConvert", i18nColumnConvert);
        context.put("cacheColumnConvert", cacheColumnConvert);
        context.put("enumColumnConvert", enumColumnConvert);
        return context;
    }

}
