package com.baidu.gcrm.contact.web;

import java.util.List;

import com.baidu.gcrm.contact.model.ContactPerson;


public class ContactPersonForm{
	
	List<ContactPerson> contacts;

	public List<ContactPerson> getContacts() {
		return contacts;
	}

	public void setContacts(List<ContactPerson> contacts) {
		this.contacts = contacts;
	}
}
