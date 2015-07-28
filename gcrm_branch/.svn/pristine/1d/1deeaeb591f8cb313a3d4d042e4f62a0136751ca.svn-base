package com.baidu.gcrm.stock.vo;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;

public class AdContentBriefDTO implements Comparable<AdContentBriefDTO> {
    
    public enum ADContentBriefStatus {
        reserved, confirmed
    }
    
    private String advertiser;
    
    private String operator;
    
    private String billingModel;
    
    private ADContentBriefStatus status;
    
    private List<String> dates;
    
    private Long adSolutionId;
    
    private Long adContentId;
    
    private String number;

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBillingModel() {
        return billingModel;
    }

    public void setBillingModel(String billingModel) {
        this.billingModel = billingModel;
    }

    public ADContentBriefStatus getStatus() {
        return status;
    }

    public void setStatus(ADContentBriefStatus status) {
        this.status = status;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }
    
    public Long getAdSolutionId() {
        return adSolutionId;
    }

    public void setAdSolutionId(Long adSolutionId) {
        this.adSolutionId = adSolutionId;
    }

    public Long getAdContentId() {
        return adContentId;
    }

    public void setAdContentId(Long adContentId) {
        this.adContentId = adContentId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public int compareTo(AdContentBriefDTO o) {
        if (StringUtils.isEmpty(this.billingModel)) {
            return 1;
        }
        if (StringUtils.isEmpty(o.getBillingModel())) {
            return -1;
        }
        return BillingModelOrder.valueOf(this.billingModel)
                .compareTo(BillingModelOrder.valueOf(o.getBillingModel()));
    }
    
    /**
     * 计费方式权重，控制前端广告内容的排序
     *
     */
    private enum BillingModelOrder {
        CPT, CPM, CPC, CPA, CPS
    }
}
