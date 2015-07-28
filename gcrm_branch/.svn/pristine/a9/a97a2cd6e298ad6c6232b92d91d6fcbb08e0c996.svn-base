package com.baidu.gcrm.common;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.baidu.gcrm.account.rights.service.IUserRightsService;
import com.baidu.gcrm.account.service.IAccountService;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.ad.service.IContractService;
import com.baidu.gcrm.common.mail.IMailService;
import com.baidu.gcrm.common.random.IRandomStringService;
import com.baidu.gcrm.contact.service.IContactService;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.occupation.service.IPositionOccupationService;
import com.baidu.gcrm.opportunity.service.IOpportunityService;
import com.baidu.gcrm.qualification.service.IQualificationService;
import com.baidu.gcrm.resource.adplatform.service.IAdPlatformService;
import com.baidu.gcrm.resource.position.model.Position.RotationType;
import com.baidu.gcrm.resource.position.service.IPositionService;
import com.baidu.gcrm.resource.site.service.ISiteService;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelistcache.service.impl.AgentRegionalServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.BillingModelServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.CountryServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.CurrencyTypeServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.IndustryServcieImpl;
import com.baidu.rigel.crm.rights.service.AuthExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.MenuServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewAcctExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewPositionExtServiceWrapper;
import com.baidu.rigel.crm.rights.service.NewRoleExtServiceWrapper;

public class ServiceBeanFactory {
	private static MessageSource messageSource;
	private static I18nKVService i18nService;
	private static NewAcctExtServiceWrapper acctExtService;
	private static NewPositionExtServiceWrapper newPositionService;
	private static NewRoleExtServiceWrapper roleService;
	private static AuthExtServiceWrapper authService;
	private static MenuServiceWrapper menuService;
	
	private static ICustomerService customerService;
	private static IContactService contactService;
	private static IOpportunityService opportunityService;
	private static IQualificationService qualificationService;
	private static IPositionOccupationService fixedOccupationService;
	private static IPositionOccupationService rotationOccupationService;
	private static IAdPlatformService adPlatformService;
	private static IPositionService positionService;
	private static BillingModelServiceImpl billingModelServiceImpl;
	private static ISiteService siteService;
	private static IContractService contractService;
	private static IAccountService accountService;
	private static AgentRegionalServiceImpl agentRegionalService;
	private static CountryServiceImpl countryCacheServiceImpl;
	private static IndustryServcieImpl industryServiceImpl;
	private static CurrencyTypeServiceImpl currencyTypeServiceImpl;
	
	private static IAdvertiseSolutionService adSolutionService;
	private static IAdSolutionContentService adSolutionContentService;
	private static VelocityEngine velocityEngine = null;
	private static IMailService mailService = null;
	
	private static SessionLocaleResolver sessionLocaleResolver = null;
	private static IUserRightsService userRightsService;
	
	private static IRandomStringService randomStringService;
	
	private static IUserService userService;
	
	public static IPositionOccupationService getOccupationService(RotationType type) {
		if (type != null && type.isRotation()) {
			return getRotationOccupationService();
		} else {
			return getFixedOccupationService();
		}
	}

	public static MessageSource getMessageSource() {
		return messageSource;
	}

	@Autowired
	@Qualifier("messageSource")
	public void setMessageSource(MessageSource messageSource) {
		ServiceBeanFactory.messageSource = messageSource;
	}

	public static I18nKVService getI18nService() {
		return i18nService;
	}

	@Autowired
	public void setI18nService(I18nKVService i18nService) {
		ServiceBeanFactory.i18nService = i18nService;
	}

	public static NewAcctExtServiceWrapper getAcctExtService() {
		return acctExtService;
	}

	@Autowired
	@Qualifier("acctExtService")
	public void setAcctExtService(NewAcctExtServiceWrapper acctExtService) {
		ServiceBeanFactory.acctExtService = acctExtService;
	}

	@Autowired
	public void setNewPositionService(
			NewPositionExtServiceWrapper newPositionService) {
		ServiceBeanFactory.newPositionService = newPositionService;
	}

	public static NewPositionExtServiceWrapper getNewPositionService() {
		return newPositionService;
	}
	
	public static AuthExtServiceWrapper getAuthService() {
		return authService;
	}

	@Autowired
	public void setAuthService(AuthExtServiceWrapper authService) {
		ServiceBeanFactory.authService = authService;
	}

	public static MenuServiceWrapper getMenuService() {
		return menuService;
	}

	@Autowired
	public void setMenuService(MenuServiceWrapper menuService) {
		ServiceBeanFactory.menuService = menuService;
	}

	public static NewRoleExtServiceWrapper getRoleService() {
		return roleService;
	}

	@Autowired
	public  void setRoleService(NewRoleExtServiceWrapper roleService) {
		ServiceBeanFactory.roleService = roleService;
	}

	public static ICustomerService getCustomerService() {
		return customerService;
	}

	@Autowired
	public void setCustomerService(ICustomerService customerService) {
	    ServiceBeanFactory.customerService = customerService;
	}

	public static IContactService getContactService() {
		return contactService;
	}

	@Autowired
	public void setContactService(IContactService contactService) {
		ServiceBeanFactory.contactService = contactService;
	}

	public static IOpportunityService getOpportunityService() {
		return opportunityService;
	}

	@Autowired
	public void setOpportunityService(IOpportunityService opportunityService) {
		ServiceBeanFactory.opportunityService = opportunityService;
	}

	public static IQualificationService getQualificationService() {
		return qualificationService;
	}

	@Autowired
	public void setQualificationService(IQualificationService qualificationService) {
		ServiceBeanFactory.qualificationService = qualificationService;
	}

	public static IPositionOccupationService getFixedOccupationService() {
		return fixedOccupationService;
	}

	@Autowired
	@Qualifier("fixedPositionOccupationServiceImpl")
	public void setFixedOccupationService(IPositionOccupationService fixedOccupationService) {
		ServiceBeanFactory.fixedOccupationService = fixedOccupationService;
	}
	
	public static IPositionOccupationService getRotationOccupationService() {
		return rotationOccupationService;
	}

	@Autowired
	@Qualifier("rotationPositionOccupationServiceImpl")
	public void setRotationOccupationService(IPositionOccupationService rotationOccupationService) {
		ServiceBeanFactory.rotationOccupationService = rotationOccupationService;
	}
	
	public static IPositionService getPositionService() {
		return positionService;
	}
	
	@Autowired
	public void setPositionService(IPositionService positionService) {
		ServiceBeanFactory.positionService = positionService;
	}

	public static BillingModelServiceImpl getBillingModelServiceImpl() {
		return billingModelServiceImpl;
	}

	@Autowired
	public void setBillingModelServiceImpl(
			BillingModelServiceImpl billingModelServiceImpl) {
		ServiceBeanFactory.billingModelServiceImpl = billingModelServiceImpl;
	}

	public static ISiteService getSiteService() {
		return siteService;
	}

	@Autowired
	public void setSiteService(ISiteService siteService) {
		ServiceBeanFactory.siteService = siteService;
	}

	public static IAccountService getAccountService() {
		return accountService;
	}

	@Autowired
	public void setAccountService(IAccountService accountService) {
		ServiceBeanFactory.accountService = accountService;
	}

	public static AgentRegionalServiceImpl getAgentRegionalService() {
		return agentRegionalService;
	}

	@Autowired
	public void setAgentRegionalService(
			AgentRegionalServiceImpl agentRegionalService) {
		ServiceBeanFactory.agentRegionalService = agentRegionalService;
	}

	public static CountryServiceImpl getCountryCacheServiceImpl() {
		return countryCacheServiceImpl;
	}

	@Autowired
	public void setCountryCacheServiceImpl(
			CountryServiceImpl countryCacheServiceImpl) {
		ServiceBeanFactory.countryCacheServiceImpl = countryCacheServiceImpl;
	}

	public static IndustryServcieImpl getIndustryServiceImpl() {
		return industryServiceImpl;
	}

	@Autowired
	public void setIndustryServiceImpl(
			IndustryServcieImpl industryServiceImpl) {
		ServiceBeanFactory.industryServiceImpl = industryServiceImpl;
	}

	public static CurrencyTypeServiceImpl getCurrencyTypeServiceImpl() {
		return currencyTypeServiceImpl;
	}

	@Autowired
	public void setCurrencyTypeServiceImpl(
			CurrencyTypeServiceImpl currencyTypeServiceImpl) {
		ServiceBeanFactory.currencyTypeServiceImpl = currencyTypeServiceImpl;
	}
	
    public static IContractService getContractService() {
        return contractService;
    }
    
    @Autowired
    public void setContractService(IContractService contractService) {
        ServiceBeanFactory.contractService = contractService;
    }

    public static IAdvertiseSolutionService getAdSolutionService() {
        return adSolutionService;
    }
    
    @Autowired
    public void setAdSolutionService(
            IAdvertiseSolutionService adSolutionService) {
        ServiceBeanFactory.adSolutionService = adSolutionService;
    }

    public static IAdSolutionContentService getAdSolutionContentService() {
        return adSolutionContentService;
    }
    
    @Autowired
    public void setAdSolutionContentService(
            IAdSolutionContentService adSolutionContentService) {
        ServiceBeanFactory.adSolutionContentService = adSolutionContentService;
    }
    
    @Autowired
	public void setVelocityEngine(VelocityEngine velocityEngine) {
		ServiceBeanFactory.velocityEngine = velocityEngine;
	}

	public static VelocityEngine getVelocityEngine() {
		return ServiceBeanFactory.velocityEngine;
	}
	
	@Autowired
	public void setMailService(IMailService mailService) {
		ServiceBeanFactory.mailService = mailService;
	}

	public static IMailService getMailService() {
		return mailService;
	}

    public static SessionLocaleResolver getSessionLocaleResolver() {
        return sessionLocaleResolver;
    }
    
    @Autowired
    public void setSessionLocaleResolver(
            SessionLocaleResolver sessionLocaleResolver) {
        ServiceBeanFactory.sessionLocaleResolver = sessionLocaleResolver;
    }

    public static IAdPlatformService getAdPlatformService() {
        return adPlatformService;
    }
    
    @Autowired
    public void setAdPlatformService(IAdPlatformService adPlatformService) {
        ServiceBeanFactory.adPlatformService = adPlatformService;
    }

    public static IRandomStringService getRandomStringService() {
        return randomStringService;
    }
    
    @Autowired
    public void setRandomStringService(
            IRandomStringService randomStringService) {
        ServiceBeanFactory.randomStringService = randomStringService;
    }

	public static IUserRightsService getUserRightsService() {
		return userRightsService;
	}

	@Autowired
	public void setUserRightsService(IUserRightsService userRightsService) {
		ServiceBeanFactory.userRightsService = userRightsService;
	}

	public static IUserService getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(IUserService userService) {
		ServiceBeanFactory.userService = userService;
	}
	
    
}
