package com.baidu.gcrm.ws.cms.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.model.AdSolutionContentApply;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentApplyService;
import com.baidu.gcrm.ad.dao.AdvertiseSolutionRepository;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.model.AdvertiseSolutionContractStatus;
import com.baidu.gcrm.ad.model.Contract;
import com.baidu.gcrm.ad.model.Contract.ContractState;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.ad.service.IContractService;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.mail.MailHelper;
import com.baidu.gcrm.mail.OnlineApplyMailContent;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;
import com.baidu.gcrm.publish.service.IPublishDateService;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.quote.model.BusinessType;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.service.QuotationService;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
import com.baidu.gcrm.valuelist.service.IValuelistDBService;
import com.baidu.gcrm.ws.cms.ICMSServiceFacade;
import com.baidu.gcrm.ws.cms.dto.ContractDTO;
import com.baidu.gcrm.ws.cms.dto.ContractExecuteInfoDTO;
import com.baidu.gcrm.ws.cms.dto.I18nDTO;
import com.baidu.gcrm.ws.cms.dto.PositionDTO;
import com.baidu.gcrm.ws.cms.dto.QuotationDTO;
import com.baidu.gcrm.ws.cms.dto.SiteDTO;
import com.baidu.gcrm.ws.cms.dto.UCUserDTO;
import com.baidu.gcrm.ws.cms.service.IResourceService;

@WebService(endpointInterface = "com.baidu.gcrm.ws.cms.ICMSServiceFacade")
@Service("gcrmServiceFacade")
public class CMSServiceFacade implements ICMSServiceFacade {
    
    private static Logger logger = LoggerFactory.getLogger(CMSServiceFacade.class);
    
    private int LIMIT_SIZE = 10000;//增量更新记录限制
    
    @Autowired
    IResourceService resourceService;
    
    @Autowired
    IContractService contractService;
    
    @Autowired
    IValuelistDBService valuelistDBService;
    
    @Autowired
    I18nKVService i18nKVService;
    
    @Autowired
    IUserService userService;
    
    @Autowired
    QuotationService quotationService;
    @Autowired
    IAdvertiseSolutionService  advertiseSolutionServiceImpl;
 
	@Autowired
	private AdvertiseSolutionRepository adSolutionDao;
	
	@Autowired
	IPublishService publishService;
	
	@Autowired
	IPublishDateService publishDateService;
	@Autowired
	IAdSolutionContentApplyService adSolutionContentApplyService;
	
	@Resource(name="taskExecutor")
    ThreadPoolTaskExecutor taskExecutor;
	
	
    @Override
    public List<SiteDTO> queryAllSite() {
        return resourceService.queryAllSite();
    }

    @Override
    public List<PositionDTO> queryAllPosition() {
        return resourceService.queryAllPosition();
    }

    @Override
    public void syncContract(ContractDTO contractDTO) {
        try {
            logger.info("syncContract,advertiseSolutionId:{}, contract number:{}", contractDTO.getAdvertiseSolutionId(), contractDTO.getNumber());
            logger.info("syncContract,contractDTO:{}", ToStringBuilder.reflectionToString(contractDTO));
            logger.info("ContractDTO data, number:{},category:{},customerId:{},summary:{},state:{}",
                    contractDTO.getNumber(),
                    contractDTO.getCategory(),
                    contractDTO.getCustomerId(),
                    contractDTO.getSummary(),
                    contractDTO.getState());
            Contract contract = convertContractDTOToContract(contractDTO);
            contractService.save(contract);
            if (ContractState.VALID.equals(contract.getState())) {
            	advertiseSolutionServiceImpl.updateAdSoulutionAndScheduleStatus(contract);
            	sendEmailReminder4OnlineApply(contract);
            }else{
                advertiseSolutionServiceImpl.eraseContractNumber(contractDTO.getNumber());
            }
        } catch (Exception e) {
            logger.error("syncContract failed", e);
            final String errorMsg = e.getMessage();
            taskExecutor.execute(new Runnable(){
                @Override
                public void run() {
                    MailHelper.sendSystemMail("[ERROR]syncContract failed", errorMsg);
                }
            });
        }
            
    }

	private void sendEmailReminder4OnlineApply(Contract contract) {
		try {
			List<AdSolutionContentApply> applys=adSolutionContentApplyService.findByContractNumber(contract.getNumber());
			if(applys!=null){
				for(AdSolutionContentApply apply:applys){
					OnlineApplyMailContent content=adSolutionContentApplyService.getMailContent4OnlineReqeust(apply, Boolean.FALSE);
					MailHelper.sendOnlineApplyMail4ContractEffective(content);
				}
			}
		} catch (Exception e) {
			LoggerHelper.err(getClass(), "提前上线申请关联合同已经生效,但通知上线申请人绑定合同邮件发送失败:"+e.getMessage(), e);
		}
	}
    
    private Contract convertContractDTOToContract(ContractDTO contractDTO) {
        Contract temContract = new Contract();
        temContract.setNumber(contractDTO.getNumber());
        temContract.setCategory(contractDTO.getCategory());
        temContract.setCustomerId(contractDTO.getCustomerId());
        temContract.setSummary(contractDTO.getSummary());
        try {
            temContract.setState(ContractState.valueOf(Integer.valueOf(contractDTO.getState())));
        } catch (NumberFormatException e) {
        }
        temContract.setBeginDate(contractDTO.getBeginDate());
        temContract.setEndDate(contractDTO.getEndDate());
        temContract.setSyncDate(new Date());
        temContract.setApplier(contractDTO.getApplier());
        temContract.setSales(contractDTO.getSales());
        temContract.setParentNumber(contractDTO.getParentNumber());
        temContract.setAdvertiseSolutionId(contractDTO.getAdvertiseSolutionId());
        return temContract;
    }

    @Override
    public void updateAdSolutionWithdrawState(boolean isAllowWithdraw,
            Long adSolutionId) {
        logger.info("start updateAdSolutionWithdrawState isAllow value:{}, adSolutionId: {}",isAllowWithdraw, adSolutionId);
        AdvertiseSolution adsolution= advertiseSolutionServiceImpl.findById(adSolutionId);
        if(adsolution!=null){
        	if(!adsolution.getContractStatus().equals(AdvertiseSolutionContractStatus.Revoked)){	  
        		if(isAllowWithdraw){
        			adsolution.setContractStatus(AdvertiseSolutionContractStatus.display);
        			adSolutionDao.save(adsolution);
        		}else{
        			adsolution.setContractStatus(AdvertiseSolutionContractStatus.blank);
        			adSolutionDao.save(adsolution);
        		}
        	}else{
        	    logger.info("withdrawAdSolution, current adSolution was withdrawed,cms adSolutionId:{}", adSolutionId);
        	}
        	
        }else{
            logger.info("withdrawAdSolution, ad solution not found,cms adSolutionId:{}", adSolutionId);
        }
    }

    @Override
    public void withdrawAdSolution(Long adSolutionId, String reason,
            Long operator, Date operateDate) {
        logger.info("withdrawAdSolution ,cms adSolutionId:{}", adSolutionId);
        AdvertiseSolution solution=	adSolutionDao.findOne(adSolutionId);
        if(solution!=null){
    		solution.setContractType(null);
    		solution.setContractStatus(AdvertiseSolutionContractStatus.Revoked);
    		adSolutionDao.save(solution);
        }else{
            logger.info("withdrawAdSolution, ad solution not found,cms adSolutionId:{}", adSolutionId);
        }
    }

    
    @Override
    public List<Long> queryAllCountry() {
        return getAllIdList("g_country");
    }
    
    @Override
    public List<Long> queryAllAgentRegional() {
        return getAllIdList("g_agent_regional");
    }

    @Override
    public List<Long> queryAllCurrencyType() {
        return getAllIdList("g_currency_type");
    }

    @Override
    public List<Long> queryAllBillingModel() {
        return getAllIdList("g_billing_model");
    }

    @Override
    public List<Long> queryAllProduct() {
        return getAllIdList("g_advertising_platform");
    }
    
    private List<Long> getAllIdList(String tableName) {
        return valuelistDBService.getAllIdList(tableName);
    }

    @Override
    public List<I18nDTO> queryAllI18n() {
        List<I18nKV> i18nList = i18nKVService.findAll();
        if (CollectionUtils.isEmpty(i18nList)) {
            return null;
        }
        
        List<I18nDTO> allI18nDTOList = new LinkedList<I18nDTO> ();
        for (I18nKV temI18nKV : i18nList) {
            I18nDTO temI18nDTO = new I18nDTO();
            temI18nDTO.setId(temI18nKV.getId());
            temI18nDTO.setKey(temI18nKV.getKey());
            temI18nDTO.setValue(temI18nKV.getValue());
            temI18nDTO.setLocale(temI18nKV.getLocale().name());
            allI18nDTOList.add(temI18nDTO);
        }
        return allI18nDTOList;
    }

    @Override
    public UCUserDTO queryUserById(Long ucId) {
        User user = userService.findByUcid(ucId);
        if (user == null) {
            return null;
        }
        UCUserDTO userDTO = new UCUserDTO();
        userDTO.setUcId(user.getUcid());
        userDTO.setUserName(user.getUsername());
        userDTO.setRealName(user.getRealname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhonenumber());
        userDTO.setStatus(String.valueOf(user.getStatus()));
        return userDTO;
    }

    @Override
    public List<QuotationDTO> queryAllQuotation() {
        Long count = quotationService.countQuotation();
        long countValue = count.longValue();
        if (countValue < 1 || countValue > LIMIT_SIZE) {
            return null;
        }
        List<Quotation> quotationList = quotationService.findByValidStatus();
        List<QuotationDTO> quotationDTOList = new LinkedList<QuotationDTO> ();
        
        for (Quotation temQuotation : quotationList) {
            QuotationDTO temQuotationDTO = new QuotationDTO();
            temQuotationDTO.setId(temQuotation.getId());
            temQuotationDTO.setProductId(temQuotation.getAdvertisingPlatformId());
            temQuotationDTO.setSiteId(temQuotation.getSiteId());
            temQuotationDTO.setBillingModelId(temQuotation.getBillingModelId());
            // convert ratio double to %
            processRatioData(temQuotation, temQuotationDTO);
            
            temQuotationDTO.setPublishPrice(temQuotation.getPublishPrice());
            temQuotationDTO.setStartTime(temQuotation.getStartTime());
            temQuotationDTO.setEndTime(temQuotation.getEndTime());
            PriceType priceType = temQuotation.getPriceType();
            if (priceType != null) {
                temQuotationDTO.setPriceType(priceType.ordinal());
            }
            if (temQuotation.getIndustryId() != null) {
                temQuotationDTO.setIndustryType(temQuotation.getIndustryId());
            }
            BusinessType businessType = temQuotation.getBusinessType();
            if (businessType != null) {
                temQuotationDTO.setBusinessType(businessType.ordinal());
            }
            
            temQuotationDTO.setStatus(temQuotation.getStatus());
            quotationDTOList.add(temQuotationDTO);
        }
        return quotationDTOList;
    }
    
    private void processRatioData(Quotation temQuotation, QuotationDTO temQuotationDTO) {
    	Double ratioMine = temQuotation.getRatioMine();
        if (ratioMine != null) {
        	temQuotationDTO.setRatioMine(new BigDecimal(ratioMine.doubleValue())
        		.multiply(new BigDecimal(100)).doubleValue());
        }
        
        Double ratioCustomer = temQuotation.getRatioCustomer();
        if (ratioCustomer != null) {
        	temQuotationDTO.setRatioCustomer(new BigDecimal(ratioCustomer.doubleValue())
        		.multiply(new BigDecimal(100)).doubleValue());
        }
        
        Double ratioThird = temQuotation.getRatioThird();
        if (ratioThird != null) {
        	temQuotationDTO.setRatioThird(new BigDecimal(ratioThird.doubleValue())
        		.multiply(new BigDecimal(100)).doubleValue());
        }
        
        Double ratioRebate = temQuotation.getRatioRebate();
        if (ratioRebate != null) {
        	temQuotationDTO.setRatioRebate(new BigDecimal(ratioRebate.doubleValue())
        		.multiply(new BigDecimal(100)).doubleValue());
        }
        
    }

    @Override
    public List<ContractExecuteInfoDTO> queryContractExecuteInfo(Long adContentId) {
        
        List<ContractExecuteInfoDTO> infoList = new LinkedList<ContractExecuteInfoDTO> ();
        
        Publish publish = publishService.findByAdContentId(adContentId);
        if (publish == null) {
            return infoList;
        }
        List<PublishDate> publishDateList = publishDateService.findByPublishNumberAndStatusNot(publish.getNumber(),PublishPeriodStatus.not_start);
        if (CollectionUtils.isEmpty(publishDateList)) {
            return infoList;
        }
        
        for (PublishDate currPublishDate : publishDateList) {
            ContractExecuteInfoDTO newContractExecuteInfoDTO = new ContractExecuteInfoDTO();
            newContractExecuteInfoDTO.setStartOperator(currPublishDate.getStartOperator());
            newContractExecuteInfoDTO.setEndOperator(currPublishDate.getEndOperator());
            newContractExecuteInfoDTO.setStartDate(currPublishDate.getActuralStart());
            newContractExecuteInfoDTO.setEndDate(currPublishDate.getActuralEnd());
            infoList.add(newContractExecuteInfoDTO);
        }
        
        return infoList;
    }
    
}
