package com.baidu.gcrm.opportunity.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.opportunity.model.Opportunity;


public class OpportunityValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Opportunity.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
	    Opportunity opportunity=(Opportunity)target;

	    if(opportunity.getBudget()!=null&&opportunity.getBudget()<0){
			errors.rejectValue("budget", "opportunity.budget.number");
		}
		
	}

}
