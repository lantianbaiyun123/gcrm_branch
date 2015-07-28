package com.baidu.gcrm.opportunity.web.vo;

import java.util.List;

import com.baidu.gcrm.opportunity.model.PaymentType;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.model.CurrencyType;

public class OpportunityView {

    private Long id;

    private Long customerId;

    // 在线广告投放预算

    private Double budget;

    // 货币

    private CurrencyType currencyType;

    // 预算对应投放周期

    private String spendingTime;

    // 付款方式

    private PaymentType payment;

    // 付款周期

    private String paymentPeriod;

    // 计费模式

    private String billingModel;

    // 合作模式

    private String businessType;

    private List<AdvertisingPlatform> advertisingPlatforms;

    private String platformSale;
    private String platformCash;
    
    private String description;

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Double getBudget() {
        return budget;
    }

    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    public String getSpendingTime() {
        return spendingTime;
    }

    public PaymentType getPayment() {
        return payment;
    }

    public String getPaymentPeriod() {
        return paymentPeriod;
    }

    public String getBillingModel() {
        return billingModel;
    }

    public String getBusinessType() {
        return businessType;
    }

    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public void setSpendingTime(String spendingTime) {
        this.spendingTime = spendingTime;
    }

    public void setPayment(PaymentType payment) {
        this.payment = payment;
    }

    public void setPaymentPeriod(String paymentPeriod) {
        this.paymentPeriod = paymentPeriod;
    }

    public void setBillingModel(String billingModel) {
        this.billingModel = billingModel;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AdvertisingPlatform> getAdvertisingPlatforms() {
        return advertisingPlatforms;
    }

    public void setAdvertisingPlatforms(List<AdvertisingPlatform> advertisingPlatforms) {
        this.advertisingPlatforms = advertisingPlatforms;
    }

    public String getPlatformSale() {
        return platformSale;
    }

    public void setPlatformSale(String platformSale) {
        this.platformSale = platformSale;
    }

    public String getPlatformCash() {
        return platformCash;
    }

    public void setPlatformCash(String platformCash) {
        this.platformCash = platformCash;
    }

}
