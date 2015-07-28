package com.baidu.gcrm.ad.web.validator;

import java.math.BigDecimal;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.quote.model.PriceType;

public class AdvertiseQuotationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AdSolutionContentView.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(target==null){
			return;
		}
		AdSolutionContentView view = (AdSolutionContentView)target;
		AdvertiseQuotation quotation = view.getAdvertiseQuotation();
		if(quotation==null){
//			errors.reject("advertise.content.quotation.null");
			return;
		}
//		if(quotation.getPriceType()==null){
//			errors.rejectValue("advertiseQuotation.priceType","advertise.content.quotation.pricetype.null");
//		}
		
		Long billModel = quotation.getBillingModelId();
		if(PriceType.ratio.equals(quotation.getPriceType()) || (billModel != null && billModel.longValue() == 2)){
			//价格种类为分成，或计费模式为分成
			validatePattern(quotation,errors);
		}else if(PriceType.unit.equals(quotation.getPriceType())){
	        if(billModel == null || billModel.longValue() == -1){
//	            errors.rejectValue("advertiseQuotation.billingModelId","advertise.content.quotation.billModel.null");
	            return;
	        }
			
			//价格种类为广告单价，校验客户报价
			if (billModel != 2) {
			    Double customerQuote = quotation.getCustomerQuote();
				if (customerQuote == null) {
//	                errors.rejectValue("advertiseQuotation.customerQuote","advertise.content.quotation.quote.null");
				} else if (!pointLessCount(customerQuote, 2)) {
	                errors.rejectValue("advertiseQuotation.customerQuote","advertise.content.quotation.quote.number");
				} else {
	                //校验折扣
					if (quotation.getPublishPrice() != null && quotation.getPublishPrice() > 0) {
						if (!pointLessCount(quotation.getPublishPrice(), 2)) {
	                        //errors.rejectValue("");
	                    }
						double disc = customerQuote / quotation.getPublishPrice();
	                    BigDecimal bg = new BigDecimal(disc);
						disc = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
						if (disc - quotation.getDiscount() != 0) {
	                        errors.rejectValue("advertiseQuotation.discount","advertise.content.quotation.discount");
	                    }
	                }
	            }
			}
			
			if (billModel == 4) {
				//CPM校验投放量
				if (quotation.getDailyAmount() == null) {
//					errors.rejectValue("advertiseQuotation.dailyAmount","advertise.content.quotation.dailyAmount.null");
				}
//			}else if(billModel==1||billModel==5){
//				//CPC||CPT校验预算
//				if(quotation.getBudget()==null){
//					errors.rejectValue("advertiseQuotation.budget","advertise.content.quotation.budget.null");
//				}else if(!pointLessCount(quotation.getBudget(),2)){
//					errors.rejectValue("advertiseQuotation.budget","advertise.content.quotation.budget.number");
//				}
			}
		}
		
	}
	
	/**
	 * 校验值的小数位是否超过指定值
	 * @param number 校验数值
	 * @param count 小数点位数
	 * @return
	 */
	private boolean pointLessCount(Double number, Integer count) {
		if (number == null) {
			return false;
		}
		BigDecimal bg = new BigDecimal(number);
		double n = bg.setScale(count, BigDecimal.ROUND_HALF_UP).doubleValue();
		return number >= 0 && n - number == 0;
	}
	
	/**
	 * 校验分成信息
	 * @param quotation 报价情况
	 * @param errors 错误信息
	 */
	private void validatePattern(AdvertiseQuotation quotation, Errors errors) {
		Double ratioMine = quotation.getRatioMine();
		Double ratioCustomer = quotation.getRatioCustomer();
		Double ratioThird = quotation.getRatioThird();
		BigDecimal sum = new BigDecimal("0");
		if (ratioMine != null) {
			sum = sum.add(new BigDecimal(ratioMine.toString()));
			// 判断是否整数
			if (!pointLessCount(ratioMine, 2) || ratioMine > 100) {
				errors.rejectValue("advertiseQuotation.ratioMine", "advertise.content.quotation.pattern.mine");
			}
		}
		if (ratioCustomer != null) {
			sum = sum.add(new BigDecimal(ratioCustomer.toString()));
			// 判断是否整数
			if (!pointLessCount(ratioCustomer, 2) || ratioCustomer > 100) {
				errors.rejectValue("advertiseQuotation.ratioCustomer", "advertise.content.quotation.pattern.customer");
			}
		}
		if (ratioThird != null) {
			sum = sum.add(new BigDecimal(ratioThird.toString()));
			// 判断是否整数
			if (!pointLessCount(ratioThird, 2) || ratioThird > 100) {
				errors.rejectValue("advertiseQuotation.ratioThird", "advertise.content.quotation.pattern.third");
			}
		}
		// 判断总和是否100
		if (sum.intValue() != 0 && sum.doubleValue() != 100.00D) {
			errors.rejectValue("advertiseQuotation.ratioThird", "advertise.content.quotation.pattern.sum");
		}
	}

	
	
}
