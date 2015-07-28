package com.baidu.gcrm.contact.web;


import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.contact.model.ContactPerson;
import com.baidu.gcrm.contact.service.IContactService;
import com.baidu.gcrm.contact.web.validator.ContactPersonFromValidator;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.web.helper.AjaxResult;
import com.baidu.gcrm.customer.web.helper.CustomerBean;


@Controller
@RequestMapping("/contact")
public class ContactAction extends ControllerHelper{
	
	@Autowired
	IContactService contactService;
	
	@Autowired
    private ICustomerService customerService;
	
	@RequestMapping("/save")
	@ResponseBody
    public AjaxResult save(@Valid @ModelAttribute("saveContactBean")CustomerBean customerBean, Model model, 
            BindingResult result, HttpServletRequest request){
	    AjaxResult ajaxResult = new AjaxResult();
	    
	    if(result.hasErrors()){
            addError(result, model);
            ajaxResult.setRetBean(customerBean);
            ajaxResult.setSuccess(false);
            return ajaxResult;
        }
//	    Customer customer = customerBean.getCustomer();
//	    Long customerNumber = customer.getCustomerNumber();
//	    generatePropertysForCreate(customerBean.getContacts());
//	    customerService.saveContacts(customerNumber, customerBean.getContacts());
//	    updateCustomerRelated(customerService.findByCustomerNumber(customerNumber));
	    ajaxResult.setSuccess(true);
	    
	    return ajaxResult;
    }
	
	@RequestMapping("/saveOrUpdate")
    @ResponseBody
    public AjaxResult saveOrUpdate(@Valid @ModelAttribute("saveOrUpdateContactBean")CustomerBean customerBean, Model model, 
            BindingResult result, HttpServletRequest request){
        AjaxResult ajaxResult = new AjaxResult();
        
        if(result.hasErrors()){
            addError(result, model);
            ajaxResult.setRetBean(customerBean);
            ajaxResult.setSuccess(false);
            return ajaxResult;
        }
        
        
        Customer customer = customerBean.getCustomer();
        Long customerNumber = customer.getId();
        List<ContactPerson> saveContactPersons = new ArrayList<ContactPerson> ();
        List<ContactPerson> updateContactPersons = new ArrayList<ContactPerson> ();
        for(ContactPerson temContactPerson : customerBean.getContacts()){
            Long contactId = temContactPerson.getId();
            temContactPerson.setCustomerNumber(customerNumber);
            if(contactId == null){
                saveContactPersons.add(temContactPerson);
            }else{
                updateContactPersons.add(temContactPerson);
            }
        }
        generatePropertysForCreate(saveContactPersons);
        generatePropertyForCreateUpdate(updateContactPersons);
        customerService.saveContacts(customerNumber, saveContactPersons);
        contactService.updateContact(updateContactPersons);
		updateCustomerRelated(customerService.findByCustomerNumber(customerNumber));
        ajaxResult.setSuccess(true);
        
        return ajaxResult;
    }
	
	@RequestMapping("/view/{customerNumber}")
    public String view(@PathVariable("customerNumber")Long customerNumber, Model model,HttpServletRequest request){
        model.addAttribute("contactList", contactService.findContactsByCustomerNumber(customerNumber));
        model.addAttribute("customerNumber", customerNumber);
        return "/widget/detailConcatInfo";
    }
	
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxResult delete(@RequestParam("id") Long contactId){
	    AjaxResult ajaxResult = new AjaxResult();
		try {
			ContactPerson contactPerson = contactService.findById(contactId);
			contactService.delContact(contactId);
			updateCustomerRelated(customerService.findByCustomerNumber(contactPerson.getCustomerNumber()));
            ajaxResult.setSuccess(true);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
		return ajaxResult;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public AjaxResult update(ContactPerson contactPerson){
	    AjaxResult ajaxResult = new AjaxResult();
	    try {
            generatePropertyForCreateUpdate(contactPerson);
            contactService.updateContact(contactPerson);
            Customer customer = customerService.findByCustomerNumber(contactPerson.getCustomerNumber());
			updateCustomerRelated(customer);
            ajaxResult.setSuccess(true);
        } catch (Exception e) {
            ajaxResult.setSuccess(false);
        }
		return ajaxResult;
	}
	
	@InitBinder("saveContactBean")
    public void initSaveBinder(WebDataBinder binder) {
        binder.setValidator(new ContactPersonFromValidator());
    }
	
	@InitBinder("saveOrUpdateContactBean")
    public void initSaveOrUpdateBinder(WebDataBinder binder) {
	    binder.setValidator(new ContactPersonFromValidator());
	}
	
	private void updateCustomerRelated(Customer customer) {
		generatePropertyForUpdate(customer);
		customerService.updateCustomer(customer);
	}
	
}
