package com.baidu.gcrm.resource.adplatform.web.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.common.ServiceBeanFactory;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.vo.LocaleVO;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform;
import com.baidu.gcrm.resource.adplatform.model.AdPlatform.AdPlatformBusinessType;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformService;
import com.baidu.gcrm.resource.site.model.Site;

public class AdPlatformValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AdPlatform.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target==null) {
			return;
		}
		
		//check name 
		AdPlatform currAdPlatform = (AdPlatform)target;
		List<Site> sites = currAdPlatform.getSites();
		if (CollectionUtils.isEmpty(sites)) {
		    errors.reject("resource.site.null");
		}
		
		Long id = currAdPlatform.getId();
		List<LocaleVO> adPlatformNames = currAdPlatform.getI18nData();
		if (CollectionUtils.isEmpty(adPlatformNames) 
		        || (adPlatformNames.size() < 2 && id == null)) {
		    errors.reject("resource.adplatform.name.null");
		}
		
		AdPlatformBusinessType businessType = currAdPlatform.getBusinessType();
		if (businessType == null) {
		    errors.reject("resource.adplatform.businessType.null");
		}
		
		IAdPlatformService adPlatformService = ServiceBeanFactory.getAdPlatformService();
		List<I18nKV> i18nData = adPlatformService.findExistsName(currAdPlatform.getId(), adPlatformNames);
		
		if (!CollectionUtils.isEmpty(i18nData)) {
		    errors.reject("resource.adplatform.name.duplicate");
		}
	}
	

}
