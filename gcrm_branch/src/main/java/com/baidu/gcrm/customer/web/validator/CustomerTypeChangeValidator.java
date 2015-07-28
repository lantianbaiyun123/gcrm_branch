package com.baidu.gcrm.customer.web.validator;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.web.helper.CustomerType;

public class CustomerTypeChangeValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Customer.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Customer customer = (Customer)target;
		Long id = customer.getId();
		if (id == null) return;
		Customer dbCustomer = ServiceBeanFactory.getCustomerService().findById(id);
		if ( isBindingAgent(dbCustomer)) {
			if (!dbCustomer.getCustomerType().equals(customer.getCustomerType())) {
				errors.rejectValue("customer.customerType", "customer.type.change.forbidden");
			}
		}
	}
	
	/**
	 * 判断customer是否是其他客户指定的代理商
	 * @param customer
	 * @return
	 */
	private boolean isBindingAgent(Customer customer) {
		if (customer.getCustomerType() == null || customer.getCustomerType().ordinal() != CustomerType.offline.getValue()) {
			return false;
		}
		if (CollectionUtils.isNotEmpty(ServiceBeanFactory.getCustomerService().findByAgentCompany(customer.getId()))) {
			return true;
		}
		return false;
	}
}
