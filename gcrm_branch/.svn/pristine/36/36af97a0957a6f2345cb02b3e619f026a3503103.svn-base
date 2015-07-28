package com.baidu.gcrm.quote.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.quote.model.Quotation;

public class QuotationValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Quotation.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Quotation quote = (Quotation)target;
		
		if(quote == null){
			errors.reject("quote.null", "quote.is.null");
			return;
		}
		
		if(quote.getPublishPrice() != null && quote.getPublishPrice() < 0){
			errors.rejectValue("quote.publishPrice", "quote.publishPrice.error");
		}
		if(quote.getRatioMine() != null && (quote.getRatioMine() < 0 || quote.getRatioMine() > 100)){
			if(quote.getRatioThird() == null){
				errors.rejectValue("quote.ratio.sum","quote.ratio.sum");
			}
			errors.rejectValue("quote.ratioMine", "quote.ratioMine.error");
		}
		if(quote.getRatioThird() != null && (quote.getRatioThird() < 0 || quote.getRatioThird() > 100)){
			if(quote.getRatioMine() == null){
				errors.rejectValue("quote.ratio.sum","quote.ratio.sum");
			}
			errors.rejectValue("quote.ratioThird", "quote.ratioThird.error");
		}	
		if(quote.getRatioMine() != null && quote.getRatioThird() != null){
			if(Math.abs(quote.getRatioMine() + quote.getRatioThird() - 100) > Float.MIN_VALUE){
				errors.rejectValue("quote.ratio.sum","quote.ratio.sum");
			}
		}
		
	}
}
