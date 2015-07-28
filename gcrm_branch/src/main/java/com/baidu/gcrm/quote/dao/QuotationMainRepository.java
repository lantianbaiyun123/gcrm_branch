package com.baidu.gcrm.quote.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.quote.model.BusinessType;
import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.QuotationApproveStatus;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.model.QuotationStatus;

@Repository
public interface QuotationMainRepository extends
		JpaRepository<QuotationMain, Long> {
	
    public QuotationMain findByQuoteCode(String quoteCode);
    /**
    * 功能描述：   查询审批通过的，未失效或作废的
    * 创建人：yudajun    
    * 创建时间：2014-4-14 上午11:57:37   
    * 修改人：yudajun
    * 修改时间：2014-4-14 上午11:57:37   
    * 修改备注：   
    * 参数： @param siteId
    * 参数： @param businessType
    * 参数： @param status
    * 参数： @param quotationApproveStatus
    * 参数： @param priceType
    * 参数： @return
    * @version
     */
	public List<QuotationMain> findBySiteIdAndBusinessTypeAndStatusLessThanAndApproveStatusAndPriceTypeAndAdvertisingPlatformId(
			Long siteId, BusinessType businessType, QuotationStatus status,
			QuotationApproveStatus quotationApproveStatus,PriceType priceType,Long platformId);
}
