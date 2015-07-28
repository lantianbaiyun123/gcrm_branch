package com.baidu.gcrm.ws.cms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.service.IContactService;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.materials.model.Attachment;
import com.baidu.gcrm.materials.service.MatericalsService;
import com.baidu.gcrm.ws.cms.customer.dto.ContactPersonDTO;
import com.baidu.gcrm.ws.cms.customer.dto.CustomerDTO;
import com.baidu.gcrm.ws.cms.customer.dto.MaterialDTO;
import com.baidu.gcrm.ws.cms.service.ICMSCustomerService;

@Service
public class CMSCustomerService implements ICMSCustomerService{
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private MatericalsService matericalsService;
    
    @Autowired
    IContactService contactService;

    @Override
    public CustomerDTO findByCustomerId(Long customerId) {
        Customer currCustomer = customerService.findById(customerId);
        CustomerDTO currCustomerDTO = generateCustomerDTO(currCustomer);
        List<ContactPerson> contactPersons = contactService.findContactsByCustomerId(customerId);
        if (!CollectionUtils.isEmpty(contactPersons)) {
            List<ContactPersonDTO> contacts = new ArrayList<ContactPersonDTO> ();
            for (ContactPerson temContactPerson : contactPersons) {
                contacts.add(generateContactPersonDTO(customerId, temContactPerson));
            }
            currCustomerDTO.setContacts(contacts);
        }
        List<Attachment> attachments = matericalsService.findByCustomerNumber(customerId);
        if (!CollectionUtils.isEmpty(attachments)) {
            List<MaterialDTO> materials = new ArrayList<MaterialDTO> ();
            for (Attachment temAttachment : attachments) {
                materials.add(generateMaterialDTO(customerId, temAttachment));
            }
            currCustomerDTO.setMaterials(materials);
        }
        return currCustomerDTO;
    }
    
    private CustomerDTO generateCustomerDTO(Customer currCustomer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(currCustomer.getId());
        customerDTO.setCustomerNumber(currCustomer.getCustomerNumber() == null ? "" : String.valueOf(currCustomer.getCustomerNumber()));
        customerDTO.setName(currCustomer.getCompanyName());
        customerDTO.setCountry(currCustomer.getCountry());
        customerDTO.setAddress(currCustomer.getAddress());
        customerDTO.setBelongSales(currCustomer.getBelongSales());
        customerDTO.setStatus(currCustomer.getCompanyStatus());
        return customerDTO;
    }
    
    private ContactPersonDTO generateContactPersonDTO(Long customerId, ContactPerson currContactPerson) {
        ContactPersonDTO temContactPersonDTO = new ContactPersonDTO();
        temContactPersonDTO.setId(currContactPerson.getId());
        temContactPersonDTO.setCustomerId(customerId);
        temContactPersonDTO.setName(currContactPerson.getName());
        temContactPersonDTO.setEmail(currContactPerson.getEmail());
        
        StringBuilder phoneStr = new StringBuilder();
        String currTelephoneStr = currContactPerson.getTelephone();
        if (!StringUtils.isBlank(currTelephoneStr)) {
            phoneStr.append(currTelephoneStr);
        }
        String currMobileStr = currContactPerson.getMobile();
        if (!StringUtils.isBlank(currMobileStr)) {
            if (phoneStr.length() > 0) {
                phoneStr.append(",");
            }
            phoneStr.append(currMobileStr);
        }
        temContactPersonDTO.setTelephone(phoneStr.toString());
        return temContactPersonDTO;
    }
    
    private MaterialDTO generateMaterialDTO(Long customerId, Attachment currAttachment) {
        MaterialDTO materialDTO = new MaterialDTO();
        Long attachmentId = currAttachment.getId();
        materialDTO.setId(attachmentId);
        materialDTO.setCustomerId(customerId);
        materialDTO.setName(currAttachment.getName());
        materialDTO.setType(currAttachment.getType().ordinal());
        //materialDTO.setUrl(currAttachment.getTempUrl());
        return materialDTO;
    }
    
}
