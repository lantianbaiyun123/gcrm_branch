package com.baidu.gcrm.customer.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerBean;

public class CustomerTemporarySaveValidator extends CustomerBaseValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CustomerBean.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CustomerBean customerBean = (CustomerBean)target;
		Customer customer = customerBean.getCustomer();
		ValidationUtils.rejectIfEmpty(errors, "customer.companyName", "customer.companyName.required");
		Long customerId = customer.getId();
		if (customerId != null) {
			ValidationUtils.invokeValidator(new CustomerTypeChangeValidator(), customer, errors);
		}
	
		//驗證名稱和運營許可證 add by cch
       // validatorNameAndLicense(customer, errors);
	}

	
}
