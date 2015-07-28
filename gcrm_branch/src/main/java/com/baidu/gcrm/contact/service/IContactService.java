package com.baidu.gcrm.contact.service;

import java.util.List;

import com.baidu.gcrm.contact.model.ContactPerson;

public interface IContactService {
	
	void saveContact(ContactPerson contactPerson);
	
	void delContact(Long id);
	
	void updateContact(ContactPerson contactPerson);
	
	void updateContact(List<ContactPerson> contactPersons);
	
	void saveContacts(List<ContactPerson> contactPersons);
	
	int updateCustomerId(Long newCustomerId,Long oldCustomerId);
	@Deprecated
	List<ContactPerson> findContactsByCustomerNumber(Long customerNumber);
	
	List<ContactPerson> findContactsByCustomerId(Long customerId);
	
	ContactPerson findById(Long contactId);
	
}
