package com.baidu.gcrm.opportunity.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.PatternUtil;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.customer.web.helper.AjaxResult;
import com.baidu.gcrm.customer.web.helper.CustomerType;
import com.baidu.gcrm.opportunity.model.Opportunity;
import com.baidu.gcrm.opportunity.service.IOpportunityService;
import com.baidu.gcrm.opportunity.web.vo.OpportunityVO;
import com.baidu.gcrm.opportunity.web.vo.OpportunityView;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.model.BillingModel;
import com.baidu.gcrm.valuelistcache.model.CurrencyType;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.BillingModelServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.CurrencyTypeServiceImpl;

@Controller
@RequestMapping("/opportunity")
public class OpportunityAction extends ControllerHelper {
	@Autowired
	IOpportunityService opportunityService;
	@Autowired
	AdvertisingPlatformServiceImpl advertisingPlatformServiceImpl;
	@Autowired
	CurrencyTypeServiceImpl currencyTypeServiceImpl;
	@Autowired
	private BillingModelServiceImpl billingModelServiceImpl;	
	@Autowired
    private ICustomerService customerService;

	@RequestMapping("/reloadOpportunity/{customerNumber}")
	public String reloadOpportunity(@PathVariable(value="customerNumber")Long customerNumber, Model model){
		OpportunityView opportunityView = opportunityService.getOpportunityViewByCustomerNumber(customerNumber, currentLocale);
		if (opportunityView == null) {
		    return "widget/detailBusinessChanceEmpty";
		}
		model.addAttribute("opportunityView", opportunityView);
		return "widget/detailBusinessChance";
	}
	
	@RequestMapping("/preUpdateOpportunity/{customerNumber}")
	public String preUpdateOpportunity(@PathVariable(value="customerNumber")Long customerNumber,Model model){
		OpportunityVO opportunityVO=new OpportunityVO();
		Opportunity opportunity=opportunityService.findOpportunityByCustomerNumber(customerNumber);
		opportunityVO.setOpportunity(opportunity);
		if(opportunity.getId()!=null){
			String PlatformIds=opportunityService.findOpportunityPlatformsByOpportunityId(opportunity.getId());
			opportunityVO.setPlatformIds(PlatformIds);
		}
		model.addAttribute("opportunityVO", opportunityVO);
		addPlatformList(model,currentLocale);
		addCurrencyList(model,currentLocale);
		addbillingModel(model,currentLocale);
		return "widget/editBusinessChance";
	}	

	@RequestMapping("/updateOpportunity")
	@ResponseBody
	public AjaxResult updateOpportunity(HttpServletRequest request,
	        @RequestParam(value="customerNumber")Long customerNumber,
	        OpportunityVO opportunityVO,Model model){
		AjaxResult ajaxResult = new AjaxResult();
		Map<String,String> errors = new HashMap<String, String>();
		try{
			Customer dbCustomer = customerService.findByCustomerNumber(customerNumber);
			if(dbCustomer.getBusinessType()==null || dbCustomer.getCustomerType().ordinal()==CustomerType.offline.getValue()){
				errors.put("businesstype.error", "opportunity.save.faild.businesstype.error");
	        	ajaxResult.setErrors(errors);
	        	ajaxResult.setSuccess(false);
	        	return ajaxResult;
			}
			
		    Opportunity opportunity = opportunityVO.getOpportunity();
		    Long opportunityId = opportunity.getId();
		    boolean isBlankOpportunity = isBlank(opportunityVO);
		    if (isBlankOpportunity && opportunityId != null) {
		        opportunityService.deleteOpportunity(opportunityId);
		        opportunityService.deletePlatformListByCustomerNumber(customerNumber);
		    }else if (!isBlankOpportunity){
	            generatePropertyForCreateUpdate(opportunity);
	            opportunity.setCustomerNumber(customerNumber);
	            opportunityService.saveOrUpdateOpportunity(opportunity);
	            opportunityService.updatePlatformList(opportunity.getId(), opportunityVO.getPlatformIds());
		    }
		    updateCustomerRelated(customerService.findByCustomerNumber(customerNumber));
		    
			model.addAttribute("opportunityVO", opportunityVO);
			addPlatformList(model,currentLocale);
			addCurrencyList(model,currentLocale);
			addbillingModel(model,currentLocale);
			ajaxResult.setSuccess(true);
			
		}catch (Exception e){
			ajaxResult.setSuccess(false);
			ajaxResult.setErrors(errors);
		}
		return ajaxResult;
	}
	
	
	
	
	
	
	
	private void addPlatformList(Model model,LocaleConstants locale) {
		List <AdvertisingPlatform> realizedAdvertisingPlatformList=new ArrayList<AdvertisingPlatform>();
		List <AdvertisingPlatform> salesAdvertisingPlatformList=new ArrayList<AdvertisingPlatform>();
		List<AdvertisingPlatform> advertisingPlatformList= advertisingPlatformServiceImpl.getAllByLocale(locale);
		for(AdvertisingPlatform ap:advertisingPlatformList){
			if(Constants.Advertising_Platform_Realized==ap.getBusinessType()){
				realizedAdvertisingPlatformList.add(ap);
			}
			else if(Constants.Advertising_Platform_Sales==ap.getBusinessType()){
				salesAdvertisingPlatformList.add(ap);
			}
		}
		model.addAttribute("realizedAdvertisingPlatformList",realizedAdvertisingPlatformList);
		model.addAttribute("salesAdvertisingPlatformList",salesAdvertisingPlatformList);
	}
	

	private void addCurrencyList(Model model,LocaleConstants locale) {
		List<CurrencyType> CurrencyTypeList= currencyTypeServiceImpl.getAllByLocale(locale);
		model.addAttribute("currencyTypeList",CurrencyTypeList);
	}
	private void addbillingModel(Model model,LocaleConstants locale) {
		List<BillingModel> billingModelList= billingModelServiceImpl.getAllByLocale(locale);
		model.addAttribute("billingModelList",billingModelList);
	}
	
	private void updateCustomerRelated(Customer customer) {
		generatePropertyForUpdate(customer);
		customerService.updateCustomer(customer);
	}
	
	private boolean isBlank(OpportunityVO vo){
	    boolean isBlankOpportunityVO = true;
	    Opportunity currOpportunity = vo.getOpportunity();
	    if(currOpportunity != null){
	        if(currOpportunity.getBudget() != null){
	            return false;
	        }else if(!PatternUtil.isBlank(currOpportunity.getSpendingTime())){
	            return false;
	        }else if(currOpportunity.getPayment()!=null){
	            return false;
	        }else if(!PatternUtil.isBlank(currOpportunity.getPaymentPeriod())){
	            return false;
	        }else if(!PatternUtil.isBlank(currOpportunity.getBillingModel())){
	            return false;
	        }else if(!PatternUtil.isBlank(currOpportunity.getBusinessType())){
	            return false;
	        }else if(!PatternUtil.isBlank(currOpportunity.getDescription())){
	            return false;
	        }
	    }
	    
	    if(isBlankOpportunityVO && !PatternUtil.isBlank(vo.getPlatformIds())){
	        isBlankOpportunityVO =  false;
	    }
	    
	    return isBlankOpportunityVO;
	}
	
}
