package com.baidu.gcrm.quote.web.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.web.vo.QuotationMainView;
import com.baidu.gcrm.quote.web.vo.QuotationView;

public class QuotationMainViewValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(QuotationMainView.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		QuotationMainView mainView = (QuotationMainView) target;
		//检验主表的值
		QuotationMain quotationMain = mainView.getQuotationMain();
		if(quotationMain.getBusinessType()==null){
			errors.reject("quote.businessType.null", "quote.businessType.is.null");
			return;
		}
		
		if(quotationMain.getAdvertisingPlatformId()==null){
			errors.reject("quote.platformId.null", "quote.platformId.is.null");
			return;
		}
		
		if(quotationMain.getPriceType()==null){
			errors.reject("quote.priceType.null", "quote.priceType.is.null");
			return;
		}
		
		if(quotationMain.getStartTime()==null){
			errors.reject("quote.startTime.null", "quote.startTime.is.null");
			return;
		}
		
		if(quotationMain.getEndTime()==null){
			errors.reject("quote.endTime.null", "quote.endTime.is.null");
			return;
		}
		
		if(DateUtils.compareDate(quotationMain.getStartTime(), quotationMain.getEndTime(), DateUtils.YYYY_MM_DD) ==1){
			errors.reject("quote.startTime.after.endTime", "quote.startTime.is.after.endTime");
			return;
		}
		
		if(StringUtils.isBlank(quotationMain.getDescreption())){
			errors.reject("quote.descreption.null", "quote.descreption.is.null");
			return;
		}
		
		List<QuotationView> quotationViewList = mainView.getQuotationViewList();
		
		if(quotationViewList==null || quotationViewList.isEmpty()){//标杆价子类不能为空
			errors.reject("quote.quotationViewList.null", "quote.quotationViewList.is.null");
			return;
		}
		
		for(QuotationView quotationView:quotationViewList){
			if(quotationView.getSiteId()==null){
				errors.reject("quote.siteIdOrAgentRegional.null", "quote.siteIdOrAgentRegional.is.all.null");
				return;
			}
			
			List<Quotation> quotationList = quotationView.getQuotationList();
			if(quotationList==null || quotationList.isEmpty()){
				errors.reject("quote.quotationList.null", "quote.quotationList.is.null");
				return;
			}
			for(Quotation quotation:quotationList){
				if(PriceType.unit.equals(quotation.getPriceType())){//广告单价
					if(quotation.getBillingModelId() == null){//计费方式不能为空
						errors.reject("quote.billingModelId.null", "quote.billingModelId.is.null");
						return;
					}
					
					if (quotation.getPublishPrice() != null && quotation.getPublishPrice() < 0) {
						errors.rejectValue("quote.publishPrice",
								"quote.publishPrice.error");
					}
				}
				
				if(PriceType.ratio.equals(quotation.getPriceType())){//分成
					if (quotation.getRatioMine() != null
							&& (quotation.getRatioMine() < 0 || quotation.getRatioMine() > 1)) {
						if (quotation.getRatioThird() == null) {
							errors.rejectValue("quote.ratio.sum", "quote.ratio.sum");
						}
						errors.rejectValue("quote.ratioMine", "quote.ratioMine.error");
						return;
					}
					if (quotation.getRatioThird() != null
							&& (quotation.getRatioThird() < 0 || quotation.getRatioThird() > 1)) {
						if (quotation.getRatioMine() == null) {
							errors.rejectValue("quote.ratio.sum", "quote.ratio.sum");
						}
						errors.rejectValue("quote.ratioThird", "quote.ratioThird.error");
						return;
					}
					if (quotation.getRatioMine() != null && quotation.getRatioThird() != null && quotation.getRatioCustomer() != null) {
						if (Math.abs(quotation.getRatioMine() + quotation.getRatioThird() + quotation.getRatioCustomer() -1) > Float.MIN_VALUE) {
							errors.rejectValue("quote.ratio.sum", "quote.ratio.sum");
							return;
						}
					}
				}
				
				if(PriceType.rebate.equals(quotation.getPriceType())){//返点
					if(quotation.getRatioRebate() == null || quotation.getRatioRebate().doubleValue() < 0){
						errors.rejectValue("quote.ratioRebate", "quote.ratioRebate.error");
					}
				}
			}
		}
		
	}

}
