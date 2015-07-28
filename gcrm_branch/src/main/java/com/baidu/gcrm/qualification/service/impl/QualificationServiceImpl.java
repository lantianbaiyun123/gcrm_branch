package com.baidu.gcrm.qualification.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.qualification.dao.ICustomerResourceDao;
import com.baidu.gcrm.qualification.dao.IQualificationDao;
import com.baidu.gcrm.qualification.model.CustomerResource;
import com.baidu.gcrm.qualification.model.Qualification;
import com.baidu.gcrm.qualification.service.IQualificationService;

@Service
public class QualificationServiceImpl implements IQualificationService {
	
	@Autowired
	private IQualificationDao qualificationDao;
	
	@Autowired
	private ICustomerResourceDao customerResourceDao;
	/**
	 */
	@Override
	public void saveQualification(Qualification qualification){
		
		if(qualification==null)
			return;
		
		Qualification oldQulification = qualificationDao.findByCustomerNumber(qualification.getCustomerNumber());
		List<CustomerResource> oldResource = customerResourceDao.findByAgentQualificationId(qualification.getId());
		
		boolean isQualificationEmpty = true; 
		if(oldQulification!=null){
			isQualificationEmpty = false;
			qualification.setId(oldQulification.getId());
			qualification.setCreateTime(oldQulification.getCreateTime());
		}else{
			isQualificationEmpty = isQualificationEmpty && StringUtils.isEmpty(qualification.getParterTop1())
			&& StringUtils.isEmpty(qualification.getParterTop2())
			&& StringUtils.isEmpty(qualification.getParterTop3())
			&& StringUtils.isEmpty(qualification.getPerformanceHighlights());
			qualification.setCreateTime(new Date());
		}
		
		boolean isResourceEmpty = true;
		List<CustomerResource> resources = qualification.getCustomerResources();
		if(resources!=null){
			if(oldResource!=null&&oldResource.size()>0){
				isResourceEmpty = false;	
			}else{
				for(CustomerResource resource : resources){
					isResourceEmpty = isResourceEmpty && StringUtils.isEmpty(resource.getAdvertisersCompany1())
							&& StringUtils.isEmpty(resource.getAdvertisersCompany2())
							&& StringUtils.isEmpty(resource.getAdvertisersCompany3())
							&& StringUtils.isEmpty(resource.getIndustry());
					if(isResourceEmpty==false){
						break;
					}
				}
			}
		}
		
		if(isQualificationEmpty && isResourceEmpty){
			return;
		}
		// TODO 记录逻辑
	    //modifyRecordService.saveModifyRecord(qualification);      
		
		qualificationDao.save(qualification);
		
		if(resources!=null&&isResourceEmpty==false){
			for(CustomerResource resource : resources){
				resource.setAgentQualificationId(qualification.getId());
				customerResourceDao.save(resource);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.baidu.gcrm.qualification.service.IQualificationService#updateCustomerId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public int updateCustomerNumber(Long newCustomerNumber,Long oldCustomerNumber){
		return qualificationDao.updateCustomerNumber(newCustomerNumber, oldCustomerNumber);
	}

	@Override
	public Qualification findByCustomerNumber(Long customerNumber) {
		
		Qualification qualification = qualificationDao.findByCustomerNumber(customerNumber);
		if(qualification!=null){
		      processQualification(qualification);

			List<CustomerResource> resources = customerResourceDao.findByAgentQualificationId(qualification.getId());
			for(CustomerResource resource:resources){
			    processCustomerResource(resource);
			}
			qualification.setCustomerResources(resources);
		}
		return qualification;
		
	}

	private void processQualification(Qualification qualification){
	    StringBuffer partner = new StringBuffer("");
	    boolean  hadValue= false;
	    if(StringUtils.isNotBlank(qualification.getParterTop1())){
	        hadValue =true;
	        partner.append(qualification.getParterTop1());
	    }
	    if(StringUtils.isNotBlank(qualification.getParterTop2())){
	        if(hadValue)
	            partner.append("、");
	        hadValue =true;
	        partner.append(qualification.getParterTop2());
        }
	    if(StringUtils.isNotBlank(qualification.getParterTop3())){
	        if(hadValue)
                partner.append("、");
            hadValue =true;
            partner.append(qualification.getParterTop3());
        }
	    qualification.setPartner(partner.toString());
	}
	
	private void processCustomerResource(CustomerResource resources){
	    StringBuffer  compony = new StringBuffer("");
	       boolean  hadValue= false;

	    if(StringUtils.isNotBlank(resources.getAdvertisersCompany1())){
	         hadValue =true;
	        compony.append(resources.getAdvertisersCompany1());
	    }
	    
	    if(StringUtils.isNotBlank(resources.getAdvertisersCompany2())){
	        if(hadValue)
	            compony.append("、");
	        hadValue =true;
           compony.append(resources.getAdvertisersCompany2());
       }
	    
	    if(StringUtils.isNotBlank(resources.getAdvertisersCompany3())){
            if(hadValue)
                compony.append("、");
            hadValue =true;
           compony.append(resources.getAdvertisersCompany3());
       }
	    
	    resources.setAdvertisersCompany(compony.toString());
	}
	
	@Override
    public void deleteByCustomerNumber(Long customerId) {
		Qualification qualification = qualificationDao.findByCustomerNumber(customerId);
		if(qualification != null){
			qualificationDao.deleteByCustomerNumber(customerId);
			customerResourceDao.deleteByAgentQualificationId(qualification.getId());
		}
    }
}
