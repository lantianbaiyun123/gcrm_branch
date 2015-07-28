package com.baidu.gcrm.customer.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baidu.gcrm.customer.model.Customer;

public class CustomerApprovingValidator implements Validator{
	@Override
	public boolean supports(Class<?> clazz) {
	    return clazz.equals(Customer.class);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "customer.belongSales", "customer.belongSales.required");
		ValidationUtils.rejectIfEmpty(errors, "customer.address", "customer.address.required");
	}
}
