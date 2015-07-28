package com.baidu.gcrm.opportunity.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.customer.web.helper.BusinessType;
import com.baidu.gcrm.opportunity.dao.IOpportunityPlatformRepository;
import com.baidu.gcrm.opportunity.dao.IOpportunityRepository;
import com.baidu.gcrm.opportunity.model.Opportunity;
import com.baidu.gcrm.opportunity.model.OpportunityPlatform;
import com.baidu.gcrm.opportunity.service.IOpportunityService;
import com.baidu.gcrm.opportunity.web.vo.OpportunityView;
import com.baidu.gcrm.valuelistcache.model.AdvertisingPlatform;
import com.baidu.gcrm.valuelistcache.model.CurrencyType;
import com.baidu.gcrm.valuelistcache.service.impl.AdvertisingPlatformServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.BillingModelServiceImpl;
import com.baidu.gcrm.valuelistcache.service.impl.CurrencyTypeServiceImpl;

@Service
public class OpportunityServiceImpl implements IOpportunityService {
	@Autowired
	private IOpportunityRepository opportunityDao;
	@Autowired
	private IOpportunityPlatformRepository opportunityPlatformDao;
	@Autowired
	AdvertisingPlatformServiceImpl advertisingPlatformServiceImpl;
	@Autowired
	CurrencyTypeServiceImpl currencyTypeServiceImpl;
	@Autowired
	private BillingModelServiceImpl billingModelServiceImpl;	
	

	
	/**
	 * 保存代理商資質
	 */
	@Override
	public void saveOpportunity(Opportunity opportunity) {
	    if(opportunity ==null){
	        return;
	    }
	    
	    String platformIds = opportunity.getPlatformIds();
	    opportunity =opportunityDao.save(opportunity);
		if(StringUtils.isNotBlank(platformIds)){
		    savePlatformList(opportunity.getId(),platformIds);
		}
	}

    private void savePlatformList(Long opportunityId, String platformIds) {
        if (platformIds == null) {
            return;
        }
        List<OpportunityPlatform> platformList = new ArrayList<OpportunityPlatform>();
        String[] platformIdsArray = platformIds.split(",");
        for (String platformId : platformIdsArray) {
            OpportunityPlatform platform = new OpportunityPlatform();
            platform.setPlatformId(new Long(platformId));
            platform.setOpportunityId(opportunityId);
            platformList.add(platform);
        }
        opportunityPlatformDao.save(platformList);

    }
	@Override
	public void savePlatformList(List<OpportunityPlatform> opportunityPlatformList) {
		opportunityPlatformDao.save(opportunityPlatformList);
	}

	@Override
	public void saveOrUpdateOpportunity(Opportunity opportunity) {
		if(opportunity.getId()==null||!opportunityDao.exists(opportunity.getId())){
			opportunityDao.save(opportunity);
			return;
		}

		opportunityDao.saveAndFlush(opportunity);
	}

	@Override
	public void updatePlatformList(Long opportunityId,
			List<OpportunityPlatform> opportunityPlatformList) {
		List<OpportunityPlatform> list=opportunityPlatformDao.findByOpportunityId(opportunityId);
		opportunityPlatformDao.deleteInBatch(list);
		opportunityPlatformDao.save(opportunityPlatformList);
	}

	@Override
	public Opportunity findOpportunityByCustomerNumber(Long customerNumber) {
		List<Opportunity> opportunityList=opportunityDao.findByCustomerNumber(customerNumber);
		if(opportunityList==null||opportunityList.size()==0){
			return new Opportunity();
		}
		return opportunityList.get(0);
	}
	@Override
    public Opportunity findOpportunityByCustomerId(Long customerId) {
        return findOpportunityByCustomerNumber(customerId);
    }
	@Override
	public String findOpportunityPlatformsByOpportunityId(
	        Long opportunityId) {
		List<OpportunityPlatform> list=opportunityPlatformDao.findByOpportunityId(opportunityId);
		StringBuffer platformIds=new StringBuffer();
		for(OpportunityPlatform p:list){
			platformIds.append(p.getPlatformId());
		}
		return platformIds.toString();
	}

	
	

	@Override
	public void updatePlatformList(Long opportunityId, String platformIds) {
	    List<OpportunityPlatform> list=opportunityPlatformDao.findByOpportunityId(opportunityId);
	    if(list!=null && list.size()>0)
	        opportunityPlatformDao.deleteInBatch(list);
		if(StringUtils.isNotBlank(platformIds)){
			List<OpportunityPlatform> platformList=new ArrayList<OpportunityPlatform>();
			String[] platformIdsArray=platformIds.split(",");
			for(String platformId:platformIdsArray){
				OpportunityPlatform platform=new OpportunityPlatform();
				platform.setPlatformId(new Long(platformId));
				platform.setOpportunityId(opportunityId);
				platformList.add(platform);
			}
			
			opportunityPlatformDao.save(platformList);
		}
		
	}

	@Override
	public OpportunityView getOpportunityViewByCustomerNumber(Long customerNumber,LocaleConstants locale) {
		Opportunity opportunity=null;
		List<Opportunity> opportunityList=opportunityDao.findByCustomerNumber(customerNumber);
		if(opportunityList==null||opportunityList.size()==0){
			return null;
		}else{
			opportunity=opportunityList.get(0);
		}
		
		OpportunityView view =new OpportunityView();
		view.setId(opportunity.getId());
		view.setCustomerId(opportunity.getCustomerNumber());
		view.setBudget(opportunity.getBudget());
		String currentTypeS=opportunity.getCurrencyType();
		if(currentTypeS!=null){
			CurrencyType ctype = currencyTypeServiceImpl.getByIdAndLocale(Long.valueOf(currentTypeS), locale);
			if(ctype!=null){
				view.setCurrencyType(ctype);
			}		
		}
		view.setSpendingTime(opportunity.getSpendingTime());
		String billModelS=opportunity.getBillingModel();
		view.setBillingModel(billModelS);
		//TODO 是否改造
//		if(billModelS!=null){
//			StringBuffer sb=new StringBuffer();
//			String[] billModelA=billModelS.split(",");
//			for(String id:billModelA){
//				BillingModel  bm=billingModelServiceImpl.getByIdAndLocale(id, locale);
//				if(bm==null){continue;}
//				if(sb.length()>0){sb.append(",");}
//				sb.append(bm.getI18nName());
//			}
//			view.setBillingModel(sb.toString());
//		}
		
		view.setBusinessType(opportunity.getBusinessType());
		view.setPaymentPeriod(opportunity.getPaymentPeriod());
		view.setPayment(opportunity.getPayment());
		List<OpportunityPlatform> platformList=opportunityPlatformDao.findByOpportunityId(opportunity.getId());
		List<AdvertisingPlatform> advertisingPlatformList = new ArrayList<AdvertisingPlatform>();
		
		StringBuffer platformSale  = new StringBuffer();
		StringBuffer platformCash = new StringBuffer();
		for(OpportunityPlatform platform: platformList){
			AdvertisingPlatform adplatform=advertisingPlatformServiceImpl.getByIdAndLocale(platform.getPlatformId(), locale);
			if(adplatform==null){continue;}
			advertisingPlatformList.add(adplatform);
			if(adplatform.getBusinessType()==BusinessType.CASH.ordinal()){
			    platformCash.append(adplatform.getI18nName());
			    platformCash.append("、");
			}
			if(adplatform.getBusinessType()==BusinessType.SALE.ordinal()){
                platformSale.append(adplatform.getI18nName());
                platformSale.append("、");
            }
		}
	    if(platformCash.length()!=0)
	         view.setPlatformCash(platformCash.substring(0, platformCash.length()-1));
		if(platformSale.length()!=0)
		    view.setPlatformSale(platformSale.substring(0, platformSale.length()-1));

		view.setAdvertisingPlatforms(advertisingPlatformList);
		view.setDescription(opportunity.getDescription());
		
		return view;
	}

	@Override
    public OpportunityView getOpportunityViewByCustomerId(Long customerId,LocaleConstants locale) {
	    return getOpportunityViewByCustomerNumber(customerId, locale);
	}
    @Override
    public void deleteOpportunityByCustomerNumber(Long customerNumber) {
        opportunityDao.deleteByCustomerNumber(customerNumber);
        
        deletePlatformListByCustomerNumber(customerNumber);
    }
    @Override
    public void deleteOpportunityByCustomerId(Long customerId) {
        opportunityDao.deleteByCustomerNumber(customerId);
        
        deletePlatformListByCustomerNumber(customerId);
    }
    @Override
    public void deletePlatformListByCustomerNumber(Long customerNumber) {
        opportunityPlatformDao.deleteOpportunityPlatformByCustomerNumber(customerNumber);
        
    }
    @Override
    public void deletePlatformListByCustomerId(Long customerId) {
        opportunityPlatformDao.deleteOpportunityPlatformByCustomerNumber(customerId);
        
    }
    @Override
    public void deleteOpportunity(Long id) {
        opportunityDao.delete(id);
        
    }
}
