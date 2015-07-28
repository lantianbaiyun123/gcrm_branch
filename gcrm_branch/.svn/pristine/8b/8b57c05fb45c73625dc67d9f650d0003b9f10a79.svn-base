package com.baidu.gcrm.quote.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.baidu.gcrm.quote.dao.QuotationCondition;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.PriceType;

public interface QuotationService {

	/**
	 * 根据id查找报价信息
	 * @param id
	 * @return
	 */
	public Quotation findById(Long id);

	/**
	 * 分页获取所有的报价信息
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Quotation> findOnOpage(int page, int size);

	/**
	 * 查询报价信息
	 * @param advertisingPlatformId：产品（投放平台）id
	 * @param siteId：投放站点Id
	 * @param billingModelId：计费方式Id
	 * @return
	 */
	public List<Quotation> findByAdvertisingPlatformIdAndSiteIdAndBillingModelId(
	        Long advertisingPlatformId, Long siteId, Long billingModelId);

	/**
	 * 保存报价信息
	 * @param quote
	 */
	public void addQuote(Quotation quote);

	public void updateQuote(Quotation quote);

	/**
	 * 删除报价信息
	 * @param id
	 */
	public void delete(Long id);

	public Page<Quotation> findByCondition(int page, int size, QuotationCondition conditon);
	
	public List<Quotation> findByAdvertisingPlatformIdAndSiteIdAndPriceType(
			Long advertisingPlatformId, Long siteId, PriceType priceType);
	
	List<Quotation> findByValidStatus();
	
	Long countQuotation();

}