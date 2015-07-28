package com.baidu.gcrm.ad.web.validator;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.web.vo.AdSolutionBaseInfoView;
import com.baidu.gcrm.ad.web.vo.AdvertiseSolutionView;

public class BaseInfoViewValidator implements Validator{
	private static Logger log = LoggerFactory.getLogger(AdSolutionBaseInfoView.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AdSolutionBaseInfoView.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(target == null){
			errors.reject("advertise.solution.baseinfo.save.faild", "advertise.solution.baseinfo.save.faild");
			log.error("=====baseinfo信息为空");
			return;
		}
		
		AdSolutionBaseInfoView baseInfo = (AdSolutionBaseInfoView)target;
		AdvertiseSolutionView adSolutionView = baseInfo.getAdvertiseSolutionView();
		AdvertiseSolution adSolution = null;
		if(adSolutionView != null){
			adSolution = adSolutionView.getAdvertiseSolution();
		}

		if(adSolution == null){
			errors.reject("advertise.solution.baseinfo.save.faild", "advertise.solution.baseinfo.save.faild");
			log.error("======adSolution信息问空");
			return;
		}
		
		
		if(adSolution.getCustomerNumber() == null){
			errors.rejectValue("customerView.customer.customerNumber", "advertise.solution.baseinfo.customer.null");
		}

		if(baseInfo.getHasContract() != null && baseInfo.getHasContract() == true){
			if(StringUtils.isEmpty(adSolution.getContractNumber())){
				errors.rejectValue("contract.number", "advertise.solution.baseinfo.contract.null");
			}
		}
	}
	
}
