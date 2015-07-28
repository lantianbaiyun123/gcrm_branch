package com.baidu.gcrm.opportunity.service;

import java.util.List;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.opportunity.model.Opportunity;
import com.baidu.gcrm.opportunity.model.OpportunityPlatform;
import com.baidu.gcrm.opportunity.web.vo.OpportunityView;

public interface IOpportunityService {
	void saveOpportunity(Opportunity opportunity);
	void deleteOpportunity(Long id);
	void saveOrUpdateOpportunity(Opportunity opportunity);
	void savePlatformList(List<OpportunityPlatform> opportunityPlatformList);
	void updatePlatformList(Long opportunityId, List<OpportunityPlatform> opportunityPlatformList);
	@Deprecated
	Opportunity findOpportunityByCustomerNumber(Long customerNumber);
    Opportunity findOpportunityByCustomerId(Long customerId);

	@Deprecated
	void deleteOpportunityByCustomerNumber(Long customerNumber);
	void deleteOpportunityByCustomerId(Long customerId);
	@Deprecated
	void deletePlatformListByCustomerNumber(Long customerNumber);
	void deletePlatformListByCustomerId(Long customerId);

	String findOpportunityPlatformsByOpportunityId(Long opportunityId);
//	void savePlatformList(Long opportunityID,String platformIds);
	void updatePlatformList(Long opportunityID,String platformIds);
	
	@Deprecated
	OpportunityView getOpportunityViewByCustomerNumber(Long customerNumber,LocaleConstants locale);
	OpportunityView getOpportunityViewByCustomerId(Long customerId,LocaleConstants locale);

}
