package com.baidu.gcrm.contact.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.repository.BatchRepository;
import com.baidu.gcrm.contact.dao.IContactRepository;
import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.service.IContactService;

@Service
public class ContactServiceImpl implements IContactService{
	
	@Autowired
	IContactRepository contactRepository;
	
	@Autowired
	BatchRepository<ContactPerson> batchRepository;

	@Override
	public void saveContact(ContactPerson contactPerson) {
		contactRepository.save(contactPerson);
	}
	
	@Override
	public void updateContact(ContactPerson contactPerson) {
		ContactPerson existsContactPerson = contactRepository.findOne(contactPerson.getId());
		contactPerson.setCreateOperator(existsContactPerson.getCreateOperator());
		contactPerson.setCreateTime(existsContactPerson.getCreateTime());
		contactPerson.setCustomerNumber(existsContactPerson.getCustomerNumber());
		contactRepository.save(contactPerson);
	}
	
	@Override
    public void updateContact(List<ContactPerson> contactPersons) {
	    if (CollectionUtils.isEmpty(contactPersons)) {
	        return;
	    }
	    for (ContactPerson temContactPerson : contactPersons){
	        updateContact(temContactPerson);
	    }
    }
	
	@Override
	public void delContact(Long id) {
		contactRepository.delete(id);
	}

	@Override
	public int updateCustomerId(Long newCustomerId,Long oldCustomerId) {
		return contactRepository.updateCustomerId(newCustomerId, oldCustomerId);
	}

	@Override
	public void saveContacts(List<ContactPerson> contactPersons) {
		contactRepository.save(contactPersons);
		
	}

	@Override
	public List<ContactPerson> findContactsByCustomerNumber(Long customerNumber) {
		return contactRepository.findByCustomerNumber(customerNumber);
	}
	@Override
    public List<ContactPerson> findContactsByCustomerId(Long customerId){
	       return contactRepository.findByCustomerNumber(customerId);

	}
	@Override
	public ContactPerson findById(Long contactId) {
		return contactRepository.findOne(contactId);
	};
	
	
}
