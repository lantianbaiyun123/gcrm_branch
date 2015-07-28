package com.baidu.gcrm.quote.service.impl;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.quote.dao.QuotationCondition;
import com.baidu.gcrm.quote.dao.QuotationRepository;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationStatus;
import com.baidu.gcrm.quote.service.QuotationService;

@Service
public class QuotationServiceImpl implements QuotationService  {
	private static Logger log = LoggerFactory.getLogger(QuotationServiceImpl.class);
	
	@Autowired
	private QuotationRepository quoteRepository;
	
	/* (non-Javadoc)
	 * @see com.baidu.gcrm.quote.service.impl.QuoteService#findById(java.lang.Long)
	 */
	@Override
    public Quotation findById(Long id){
		return quoteRepository.findOne(id);
	}
	
	/* (non-Javadoc)
	 * @see com.baidu.gcrm.quote.service.impl.QuoteService#findOnOpage(int, int)
	 */
	@Override
    public Page<Quotation> findOnOpage(int page,int size){
		Pageable pageable = new PageRequest(page, size);
		Page<Quotation> quotes = quoteRepository.findAll(pageable);

		return quotes;
	}
	
	/* (non-Javadoc)
	 * @see com.baidu.gcrm.quote.service.impl.QuoteService#findByAdvertisingPlatformIdAndSiteIdAndBillingModelId(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	@Override
    public List<Quotation> findByAdvertisingPlatformIdAndSiteIdAndBillingModelId(
			Long advertisingPlatformId, Long siteId, Long billingModelId
			){
		return quoteRepository.findByAdvertisingPlatformIdAndSiteIdAndBillingModelIdAndStatus(
				advertisingPlatformId, siteId, billingModelId, DateUtils.getCurrentDateOfZero(),QuotationStatus.VALID);
	}
	
	/* (non-Javadoc)
	 * @see com.baidu.gcrm.quote.service.impl.QuoteService#addQuote(com.baidu.gcrm.quote.model.Quote)
	 */
	@Override
    public void addQuote(Quotation quote){
		if(quote==null){
			log.info("=====存入数据为空");
			return;
		}
		
		quoteRepository.save(quote);
	}
	
	/* (non-Javadoc)
	 * @see com.baidu.gcrm.quote.service.impl.QuoteService#updateQuote(com.baidu.gcrm.quote.model.Quote)
	 */
	@Override
    public void updateQuote(Quotation quote){
		if(quote==null){
			log.info("=====传入数据为空");
			return;
		}
		
		quoteRepository.save(quote);
	}
	
	/* (non-Javadoc)
	 * @see com.baidu.gcrm.quote.service.impl.QuoteService#delete(java.lang.Long)
	 */
	@Override
    public void delete(Long id){
		quoteRepository.delete(id);
	}
	
	@Override
	public Page<Quotation> findByCondition(int page, int size, final QuotationCondition conditon){
		Pageable pageable = new PageRequest(page, size);
		Page<Quotation> quotes = quoteRepository.findAll(
				new Specification<Quotation>(){
					@Override
                    public Predicate toPredicate(Root<Quotation> root,
                            CriteriaQuery<?> query, CriteriaBuilder cb) {
						Predicate predicate = cb.conjunction();
			            List<Expression<Boolean>> expressions = predicate.getExpressions();
			            if(conditon.getAdvertisingPlatformId()!=null&& conditon.getAdvertisingPlatformId()>0){
			            	expressions.add(cb.equal(root.<String>get("advertisingPlatformId"), conditon.getAdvertisingPlatformId()));   
			            }
			            if(conditon.getBillingModelId()!=null && conditon.getBillingModelId()>0){
			            	expressions.add(cb.equal(root.<String>get("billingModelId"), conditon.getBillingModelId()));   
			            }
			            if(conditon.getSiteId()!=null && conditon.getSiteId()>0){
			            	expressions.add(cb.equal(root.<String>get("siteId"), conditon.getSiteId()));   
			            }
	                    return predicate;
                    }
					
				},pageable
				);
		
		return quotes;
	}
	
	public List<Quotation> findByAdvertisingPlatformIdAndSiteIdAndPriceType(
			Long advertisingPlatformId, Long siteId, PriceType priceType){
		return quoteRepository.findByAdvertisingPlatformIdAndSiteIdAndPriceTypeAndStatus(
				advertisingPlatformId, siteId, priceType,DateUtils.getCurrentDateOfZero(),QuotationStatus.VALID);
	}

    @Override
    public List<Quotation> findByValidStatus() {
        return quoteRepository.findByStatus(QuotationStatus.VALID);
    }

    @Override
    public Long countQuotation() {
        return quoteRepository.countByStatus(QuotationStatus.VALID);
    }
}
