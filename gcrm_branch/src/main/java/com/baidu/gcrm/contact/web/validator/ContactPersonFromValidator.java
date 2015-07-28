package com.baidu.gcrm.contact.web.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.customer.web.helper.CustomerBean;

public class ContactPersonFromValidator implements Validator{
	private final static int CONTACT_LIMIT = 10;

	@Override
	public boolean supports(Class<?> clazz) {
		return CustomerBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CustomerBean contactPersonForm = (CustomerBean)target;
		List<ContactPerson> contacts = contactPersonForm.getContacts();
		if(contacts == null || (contacts != null 
		        && contacts.size() > CONTACT_LIMIT) ){
			errors.reject("contact.size.invalid", "contact.size.invalid");
			return;
		}
		
		int i = 0;
		for(ContactPerson temContactPerson : contacts){
			ContactPersonValidator.validateSingleContactPerson(errors, new StringBuilder("contacts[").append(i).append("].").toString(), temContactPerson, true);
			i++;
		}
	}
}
