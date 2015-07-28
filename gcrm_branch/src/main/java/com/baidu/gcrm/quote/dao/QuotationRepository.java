package com.baidu.gcrm.quote.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.quote.model.PriceType;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationStatus;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long>,JpaSpecificationExecutor<Quotation>{
	
	/**
	 * 查询报价信息
	 * @param advertisingPlatformId：产品（投放平台）id
	 * @param siteId：投放站点Id
	 * @param billingModelId：计费方式Id
	 * @return
	 */
	@Query("select q from Quotation q,QuotationMain a where q.quotationMainId = a.id and "
			+ "q.advertisingPlatformId=?1 and "
			+ "q.siteId=?2 and "
			+ "q.billingModelId=?3 and "
			+ "q.startTime<=?4 and "
			+ "q.endTime>=?4 and "
			+ "a.status=?5  ")
	List<Quotation> findByAdvertisingPlatformIdAndSiteIdAndBillingModelIdAndStatus(
			Long advertisingPlatformId, Long siteId, Long billingModelId, Date currentDate,QuotationStatus status);
	
	@Query("select q from Quotation q,QuotationMain a where q.quotationMainId = a.id and "
			+ "q.advertisingPlatformId=?1 and "
			+ "q.siteId=?2 and "
			+ "q.priceType=?3 and "
			+ "q.startTime<=?4 and "
			+ "q.endTime>=?4 and "
			+ "a.status=?5  ")
	List<Quotation> findByAdvertisingPlatformIdAndSiteIdAndPriceTypeAndStatus(
			Long advertisingPlatformId, Long siteId, PriceType priceType, Date CurrentDate,QuotationStatus status);
	
	@Query("select q from Quotation q,QuotationMain qm where q.quotationMainId=qm.id and qm.status=?1")
	List<Quotation> findByStatus(QuotationStatus status);
	
	@Query("select count(q.id) from Quotation q,QuotationMain qm where q.quotationMainId=qm.id and qm.status=?1 ")
	Long countByStatus(QuotationStatus status);
	/**
	* 功能描述：   根据mainid查询标杆价子表
	* 创建人：yudajun    
	* 创建时间：2014-4-13 下午1:03:16   
	* 修改人：yudajun
	* 修改时间：2014-4-13 下午1:03:16   
	* 修改备注：   
	* 参数： @param quotationMainId
	* 参数： @return
	* @version
	 */
	public List<Quotation> findByQuotationMainId(Long quotationMainId);

}
