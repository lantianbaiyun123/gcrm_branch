package com.baidu.gcrm.report.web.vo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.report.model.Hao123ReportBean;

public class Hao123ReportPage extends Page<Hao123ReportBean> {

    private static final long serialVersionUID = 4066595930573984135L;
    private Date beginDate;
    private Date endDate;
    private String beginStr;
    private String endStr;
    private String transformerId = "tr1";
    private String templateId = "csv_template";
    private Long siteId;
    private String allSites;

    public Date getBeginDate() {
        if (beginDate == null && StringUtils.isNotBlank(getBeginStr())) {
            beginDate = DateUtils.getString2Date(DateUtils.YYYY_MM_DD, getBeginStr());
        }
        return beginDate;
    }

    public Date getEndDate() {
        if (endDate == null) {
            if (StringUtils.isNotBlank(getEndStr())) {
                endDate = DateUtils.getString2Date(DateUtils.YYYY_MM_DD, getEndStr());
            } else {
                endDate = new Date();
            }
        }
        return endDate;
    }

    public String getBeginStr() {
        return beginStr;
    }

    public String getEndStr() {
        return endStr;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setBeginStr(String beginStr) {
        this.beginStr = beginStr;
    }

    public void setEndStr(String endStr) {
        this.endStr = endStr;
    }

    @Override
    public Class<Hao123ReportBean> getResultClass() {
        return Hao123ReportBean.class;
    }

    public String getTransformerId() {
        return transformerId;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTransformerId(String transformerId) {
        this.transformerId = transformerId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getAllSites() {
        return allSites;
    }

    public void setAllSites(String allSites) {
        this.allSites = allSites;
    }

}
