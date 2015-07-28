package com.baidu.gcrm.customer.web.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerApproveState;
import com.baidu.gcrm.customer.web.helper.CustomerBean;

public class CustomerSaveOrUpdateValidator extends CustomerBaseValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(CustomerBean.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CustomerBean customerBean = (CustomerBean)target;
		Customer customer = customerBean.getCustomer();
		
		Integer status = customer.getApprovalStatus();
		if (customer.getId() == null) {
			ValidationUtils.invokeValidator(new CustomerValidator(), 
					customer, errors);
			return;
		}
		ValidationUtils.invokeValidator(new CustomerTypeChangeValidator(), customer, errors);
		CustomerApproveState approvalStatus = CustomerApproveState.valueOf(status);
		
		if(approvalStatus.isApproving()){
			ValidationUtils.invokeValidator(new CustomerApprovingValidator(), 
					customerBean.getCustomer(), errors);
		} else {
			ValidationUtils.invokeValidator(new CustomerValidator(), 
					customer, errors);
			//驗證名稱和運營許可證 add by cch
	        validatorNameAndLicense(customer, errors);
	        
	        validatorAgentTypeAndAgentCountry(customer, errors);

		}
	}
   
}
