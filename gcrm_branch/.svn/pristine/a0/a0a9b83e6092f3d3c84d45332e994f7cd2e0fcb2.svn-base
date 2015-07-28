package com.baidu.gcrm.ws.cms.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.service.IAdSolutionContentService;
import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.ad.service.IAdvertiseQuotationService;
import com.baidu.gcrm.ad.service.IAdvertiseSolutionService;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.code.model.Code;
import com.baidu.gcrm.common.code.service.ICodeService;
import com.baidu.gcrm.customer.model.Customer;
import com.baidu.gcrm.customer.service.ICustomerService;
import com.baidu.gcrm.i18n.model.I18nKV;
import com.baidu.gcrm.i18n.service.I18nKVService;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.service.impl.InfoDecorator;
import com.baidu.gcrm.ws.cms.ad.dto.AdSolutionContentDTO;
import com.baidu.gcrm.ws.cms.ad.dto.AdSolutionDTO;
import com.baidu.gcrm.ws.cms.service.ICMSAdSolutionService;


@Service
public class CMSAdSolutionService implements ICMSAdSolutionService{
    
    private static Logger logger = LoggerFactory.getLogger(CMSAdSolutionService.class);
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IAdvertiseSolutionService adSolutionService;
    
    @Autowired
    IAdSolutionContentService adSolutionContentService;
    
    @Autowired
    IAdvertiseQuotationService advertiseQuotationService;
    
    @Autowired
    I18nKVService i8nKVService;
    
    @Autowired
    ICodeService codeService;
    
    private String QUOTATION_INDUSTRY_TYPE_KEY = "quotationMain.industry";
    
    @Override
    public AdSolutionDTO findById(Long adSolutionId) {
        AdvertiseSolution adSolution = adSolutionService.findById(adSolutionId);
        AdSolutionDTO temAdSolutionDTO = generateAdSolutionDTO(adSolution);
        //process content
        List<AdSolutionContent> contentList = adSolutionContentService.findByAdSolutionId(adSolutionId);
        temAdSolutionDTO.setContents(getContentDTO(contentList));
        return temAdSolutionDTO;
    }
    
    
    private List<AdSolutionContentDTO> getContentDTO(List<AdSolutionContent> contentList) {
        if (CollectionUtils.isEmpty(contentList)) {
            return null;
        }
            
        Map<String, AdvertiseQuotation> quotationMap = getAdvertiseQuotation(contentList);
        Map<String, Code> industryTypeMap = null;
        if (!quotationMap.isEmpty()) {
        	industryTypeMap = getIndustryTypeMap();
        }
        List<AdSolutionContentDTO> contentDTOList = new ArrayList<AdSolutionContentDTO> ();
        for (AdSolutionContent content : contentList) {
            contentDTOList.add(generateContentDTO(content, quotationMap, industryTypeMap));
        }
        
        return contentDTOList;
    }
    
    private Map<String, Code> getIndustryTypeMap() {
        Map<String, Code> codenMap = new HashMap<String,Code>();
        List<Code> codeList = codeService.findByCodeType(QUOTATION_INDUSTRY_TYPE_KEY);
        if (!CollectionUtils.isEmpty(codeList)) {
            for (Code temCode : codeList) {
            	codenMap.put(temCode.getCodeValue(), temCode);
            }
        }
        return codenMap;
    }
    
    private Map<String, AdvertiseQuotation> getAdvertiseQuotation(List<AdSolutionContent> contentList) {
        
        Map<String, AdvertiseQuotation> quotationMap = new HashMap<String,AdvertiseQuotation> ();
        List<Long> adContentIds = new ArrayList<Long> ();
        for (AdSolutionContent content : contentList) {
            adContentIds.add(content.getId());
        }
        List<AdvertiseQuotation> quotationList = advertiseQuotationService.findQuotationByAdContentIds(adContentIds);
        if (!CollectionUtils.isEmpty(quotationList)) {
            for (AdvertiseQuotation temAdvertiseQuotation : quotationList) {
                quotationMap.put(temAdvertiseQuotation.getAdSolutionContentId().toString(), temAdvertiseQuotation);
            }
        }
        return quotationMap;
    }
    
    private AdSolutionDTO generateAdSolutionDTO(AdvertiseSolution adSolution) {
        AdSolutionDTO adSolutionDTO = new AdSolutionDTO();
        adSolutionDTO.setId(adSolution.getId());
        Long customerNumber = adSolution.getCustomerNumber();
        Customer currCustomer = customerService.findByCustomerNumber(customerNumber);
        adSolutionDTO.setCustomerId(currCustomer.getId());
        adSolutionDTO.setCustomerName(currCustomer.getCompanyName());
        adSolutionDTO.setSales(adSolution.getOperator());
        adSolutionDTO.setNumber(adSolution.getNumber());
        adSolutionDTO.setSubmitTime(adSolution.getCreateTime());
        return adSolutionDTO;
    }
    
    private AdSolutionContentDTO generateContentDTO(AdSolutionContent adSolutionContent,
    		Map<String, AdvertiseQuotation> quotationMap,
    		Map<String, Code> industryTypeMap) {
        AdSolutionContentDTO adSolutionContentDTO = new AdSolutionContentDTO();
        adSolutionContentDTO.setId(adSolutionContent.getId());
        //process old content number
//        Long oldContentId = adSolutionContent.getOldContentId();
        String numberStr = adSolutionContent.getNumber();
//        if (oldContentId != null 
//            && ContentType.update == adSolutionContent.getContentType()
//            && ModifyStatus.NOMODIFY == adSolutionContent.getModifyStatus() ) {
//            
//            AdSolutionContent oldAdSolutionContent = adSolutionContentService.findAdSolutionContentById(oldContentId);
//            numberStr = oldAdSolutionContent.getNumber();
//            adSolutionContentDTO.setId(oldContentId);
//        }
        
        adSolutionContentDTO.setNumber(numberStr);
        adSolutionContentDTO.setAdvertiser(adSolutionContent.getAdvertiser());
        adSolutionContentDTO.setAdvertiserId(adSolutionContent.getAdvertiserId());
        adSolutionContentDTO.setAdvertiseSulutionId(adSolutionContent.getAdSolutionId());
        adSolutionContentDTO.setProductId(adSolutionContent.getProductId());
        adSolutionContentDTO.setSiteId(adSolutionContent.getSiteId());
        adSolutionContentDTO.setChannelId(adSolutionContent.getChannelId());
        adSolutionContentDTO.setRegionId(adSolutionContent.getAreaId());
        adSolutionContentDTO.setPositionId(adSolutionContent.getPositionId());
        adSolutionContentDTO.setPutOnMarketTime(adSolutionContent.getPeriodDescription());
        adSolutionContentDTO.setType(adSolutionContent.getContentType().ordinal());
        String adContentIdStr = adSolutionContent.getId().toString();
        
        Long positionId = adSolutionContent.getPositionId();
        I18nKV positionI18n = i8nKVService.findByIdAndLocale(Position.class, positionId, LocaleConstants.zh_CN);
        if (positionI18n != null && positionI18n.getExtraValue() != null) {
            String positionExtraValue = positionI18n.getExtraValue();
            String[] positionNameArray = positionExtraValue.split(InfoDecorator.SPLIT_FLAG);
            if (positionNameArray.length >= InfoDecorator.PISITION_NAME_LEVEL) {
                adSolutionContentDTO.setProduct(positionNameArray[0]);
                adSolutionContentDTO.setSite(positionNameArray[1]);
                adSolutionContentDTO.setChannel(positionNameArray[2]);
                adSolutionContentDTO.setRegion(positionNameArray[3]);
                adSolutionContentDTO.setPosition(positionNameArray[4]);
            }
        }
        
        
        AdvertiseQuotation adQuotation = quotationMap.get(adContentIdStr);
        if (adQuotation != null) {
            Long billingModelId = adQuotation.getBillingModelId();
            
            if (billingModelId != null) {
                adSolutionContentDTO.setBillingModelId(billingModelId);
                I18nKV billingModelI18n = i8nKVService.findByKeyAndLocale(
                        new StringBuilder()
                            .append("g_billing_model.")
                            .append(billingModelId.longValue()).toString()
                        ,LocaleConstants.zh_CN);
                if (billingModelI18n != null) {
                    adSolutionContentDTO.setBillingModel(billingModelI18n.getValue());
                }
            }
            
            PriceType priceType = adQuotation.getPriceType();
            if (priceType != null) {
                I18nKV priceTypeI18n = i8nKVService.findByKeyAndLocale(
                        new StringBuilder()
                            .append("price_type.")
                            .append(priceType.ordinal()).toString()
                        ,LocaleConstants.zh_CN);
                if (priceTypeI18n != null) {
                    adSolutionContentDTO.setPriceTYpe(priceTypeI18n.getValue());
                }
                adSolutionContentDTO.setPriceTypeId(Long.valueOf(priceType.ordinal()));
            }
            
            Integer industryType = adQuotation.getIndustryType();
            if (industryType != null) {
                I18nKV industryTypeI18n = i8nKVService.findByKeyAndLocale(
                        new StringBuilder()
                            .append(QUOTATION_INDUSTRY_TYPE_KEY)
                            .append(".")
                            .append(industryType.intValue()).toString()
                        ,LocaleConstants.zh_CN);
                //process parent industryType
                if (industryTypeI18n != null) {
                	String industryValueStr = industryType.toString();
                	String currIndustryTypeName = industryTypeI18n.getValue();
                	if (industryTypeMap != null) {
                		Code industryCode = industryTypeMap.get(industryValueStr);
                		if (industryCode != null && industryCode.getCodeParent() != null) {
                			String parentCode = industryCode.getCodeParent();
                			I18nKV parentIndustryTypeI18n = i8nKVService.findByKeyAndLocale(
                                    new StringBuilder()
                                        .append(QUOTATION_INDUSTRY_TYPE_KEY)
                                        .append(".")
                                        .append(parentCode).toString()
                                    ,LocaleConstants.zh_CN);
                			if (parentIndustryTypeI18n != null) {
                				currIndustryTypeName = new StringBuilder()
                											.append(parentIndustryTypeI18n.getValue())
                											.append("-")
                											.append(industryTypeI18n.getValue()).toString();
                			}
                		}
                	} 
                    adSolutionContentDTO.setCustomerIndustryType(currIndustryTypeName);
                }
                adSolutionContentDTO.setCustomerIndustryTypeId(Long.valueOf(industryType.intValue()));
            }
            
            
            Double budget = adQuotation.getBudget();
            if (budget != null) {
                adSolutionContentDTO.setBudget(new BigDecimal(budget.toString()));
            }
            Double publishPrice = adQuotation.getPublishPrice();
            if (publishPrice != null) {
                adSolutionContentDTO.setPublishPrice(new BigDecimal(publishPrice.toString()));
            }
            Double customerQuote = adQuotation.getCustomerQuote();
            if (customerQuote != null) {
                adSolutionContentDTO.setCustomerPrice(new BigDecimal(customerQuote.toString()));
            }
            
            Double discount = adQuotation.getDiscount();
            if (discount != null) {
                adSolutionContentDTO.setDiscount(new BigDecimal(discount.toString()));
            }
            
            Double ratioMine = adQuotation.getRatioMine();
            if (ratioMine != null) {
                adSolutionContentDTO.setMineRatio(new BigDecimal(ratioMine.toString()));
            }
            Double ratioCustomer = adQuotation.getRatioCustomer();
            if (ratioCustomer != null) {
                adSolutionContentDTO.setCustomerRatio(new BigDecimal(ratioCustomer.toString()));
            }
            Double ratioThird = adQuotation.getRatioThird();
            if (ratioThird != null) {
                adSolutionContentDTO.setThirdRatio(new BigDecimal(ratioThird.toString()));
            }
            Double productRatioMine = adQuotation.getProductRatioMine();
            if (productRatioMine != null) {
                adSolutionContentDTO.setProductMineRatio(new BigDecimal(productRatioMine.toString()));
            }
            Double productRatioCustomer = adQuotation.getProductRatioCustomer();
            if (productRatioCustomer != null) {
                adSolutionContentDTO.setProductCustomerRatio(new BigDecimal(productRatioCustomer.toString()));
            }
            Double productRatioThird = adQuotation.getProductRatioThird();
            if (productRatioThird != null) {
                adSolutionContentDTO.setProductThirdRatio(new BigDecimal(productRatioThird.toString()));
            }
            
            
            adSolutionContentDTO.setRatioDesc(adQuotation.getRatioConditionDesc());
            Boolean reachEstimate = adQuotation.getReachEstimate();
            if (reachEstimate != null) {
                Integer estimateValue = Integer.valueOf(0);
                if (reachEstimate.booleanValue()) {
                    estimateValue = Integer.valueOf(1);
                }
                adSolutionContentDTO.setIsEstimate(estimateValue);
            }
            
        }
            
        logger.info("adSolutionContentDTO:{}", ToStringBuilder.reflectionToString(adSolutionContentDTO));
        return adSolutionContentDTO;
    }

    @Override
    public AdSolutionContentDTO findByContentId(Long adContentId) {
        AdSolutionContent adContent = adSolutionContentService.findOne(adContentId);
        List<AdSolutionContent> contentList = new ArrayList<AdSolutionContent> (1);
        contentList.add(adContent);
        List<AdSolutionContentDTO> contentDTOList = getContentDTO(contentList);
        if (CollectionUtils.isEmpty(contentDTOList)) {
            return null;
        }
        
        return contentDTOList.get(0);
        
    }
    
}
