package com.baidu.gcrm.amp.web;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.amp.model.Country;
import com.baidu.gcrm.amp.model.Offer;
import com.baidu.gcrm.amp.service.ICountryService;
import com.baidu.gcrm.amp.service.IOfferService;
import com.baidu.gcrm.amp.service.IPositionService;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.i18n.Message;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;


@Controller
@RequestMapping("/offer")
public class OfferAction extends ControllerHelper{
	
	@Autowired
	ICustomerService customerService;
	
	@Autowired
	ICountryService countryService;
	
	@Autowired
	IPositionService positionService;
	
	@Autowired
	IOfferService offerService;
	
	@RequestMapping("/customer/list")
	public String listCustomer(HttpServletRequest request,Model model){
		Pageable page = new PageRequest(0,10);
		Page<Customer> customerPage = customerService.findAll(page);
		model.addAttribute("page", customerPage);
		return "amp/offer/listCustomer";
		
	}
	
	@RequestMapping("/customer/getAll")
	@ResponseBody
	public Page<Customer> getCustomerList(HttpServletRequest request,Model model){
		Pageable page = new PageRequest(0,10);
		Page<Customer> customerPage = customerService.findAll(page);
		return customerPage;
		
	}
	
	@RequestMapping("/customer/save")
	public String saveCustomer(HttpServletRequest request,Customer customer,Model model){
		generatePropertyForUpdate(customer);
		customerService.saveCustomer(customer);
		return "redirect:/offer/customer/list";
		
	}
	
	@RequestMapping("/customer/update")
	public String updateCustomer(HttpServletRequest request,Customer customer,Model model){
		generatePropertyForUpdate(customer);
		customerService.updateCustomer(customer);
		return "redirect:/offer/customer/list";
		
	}
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,
			@RequestParam(defaultValue="1",value="pageNum" ) int pageNum,
			@RequestParam(defaultValue="10",value="pageSize") int pageSize,
			Offer offer,Model model){
		Pageable page = new PageRequest(pageNum,pageSize);
		model.addAttribute("countryList",countryService.findAllCountry(Country.CountryStatus.ENABLE, currentLocale));
		model.addAttribute("topPositionList",positionService.findPositionByPid(Long.valueOf(0), currentLocale));
		model.addAttribute("page", offerService.findAll(offer, page));
		
		return "amp/offer/listOffer";
	}
	
	@RequestMapping("/del")
	@ResponseBody
	public Message delete(HttpServletRequest request,Offer offer,Model model){
		offerService.delOffer(offer);
		return new Message(null,Message.Type.SUCCESS);
	}
	
	@RequestMapping("/save")
	public String save(HttpServletRequest request,Offer offer,Model model){
//		offer.setOptUserId(SpringUtil.getCurrUserId());
		offerService.saveOffer(offer);
		return "redirect:offer/list";
		
	}
	
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        dateFormat.setLenient(false);  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));  
    }  
	
	
}
