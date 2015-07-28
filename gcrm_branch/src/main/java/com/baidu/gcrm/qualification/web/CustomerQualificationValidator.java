package com.baidu.gcrm.qualification.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.qualification.model.CustomerResource;
import com.baidu.gcrm.qualification.model.Qualification;

public class CustomerQualificationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(Qualification.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
				
		Qualification qualification = (Qualification)target;
		if(qualification==null){
		    return;
		}
		if(StringUtils.isNotBlank(qualification.getParterTop1())&&qualification.getParterTop1().length()>128){
			errors.rejectValue("qualification.parterTop1", "qualification.parterTop.length.toolong");
		}
		if(StringUtils.isNotBlank(qualification.getParterTop2())&&qualification.getParterTop2().length()>128){
			errors.rejectValue("qualification.parterTop2", "qualification.parterTop.length.toolong");
		}
		if(StringUtils.isNotBlank(qualification.getParterTop3())&&qualification.getParterTop3().length()>128){
			errors.rejectValue("qualification.parterTop3", "qualification.parterTop.length.toolong");
		}
		if(StringUtils.isNotBlank(qualification.getPerformanceHighlights())&&qualification.getPerformanceHighlights().length()>512){
			errors.rejectValue("qualification.performanceHighlights", "qualification.performanceHighlights.length.toolong");
		}
		
		int index = 0;
		for(CustomerResource resource : qualification.getCustomerResources()){
			if(resource ==null){
			    continue;
			}
		    if(StringUtils.isNotBlank(resource.getAdvertisersCompany1())&&resource.getAdvertisersCompany1().length()>128){
				errors.rejectValue("qualification.customerResources["+index+"].advertisersCompany1", "costomerResource.advertisersCompany.length.toolong");
			}
			if(StringUtils.isNotBlank(resource.getAdvertisersCompany2())&&resource.getAdvertisersCompany2().length()>128){
				errors.rejectValue("qualification.customerResources["+index+"].advertisersCompany2", "costomerResource.advertisersCompany.length.toolong");
			}
			if(StringUtils.isNotBlank(resource.getAdvertisersCompany3())&&resource.getAdvertisersCompany3().length()>128){
				errors.rejectValue("qualification.customerResources["+index+"].advertisersCompany3", "costomerResource.advertisersCompany.length.toolong");
			}
			if(StringUtils.isNotBlank(resource.getIndustry())&&resource.getIndustry().length()>128){
				errors.rejectValue("qualification.customerResources["+index+"].industry", "costomerResource.industry.length.toolong");
			}
			index++;
		}
	}
}
