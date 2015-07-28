package com.baidu.gcrm.quote.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationApproveStatus;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.model.QuotationStatus;
import com.baidu.gcrm.quote.web.utils.QuotationMainCondition;

public interface IQuotationMainRepositoryCustom {

	/**
	* 功能描述：   标杆价列表
	* 创建人：yudajun    
	* 创建时间：2014-4-10 下午3:26:14   
	* 修改人：yudajun
	* 修改时间：2014-4-10 下午3:26:14   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @return
	* @version
	 */
	public Page<QuotationMain> findQuotationMainPage(QuotationMainCondition condition);
	
	/**
	* 功能描述：   查询列表
	* 创建人：yudajun    
	* 创建时间：2014-4-16 下午1:09:41   
	* 修改人：yudajun
	* 修改时间：2014-4-16 下午1:09:41   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @return
	* @version
	 */
	public List<Quotation> findByQuotationMainCondition(QuotationMainCondition condition);
	
	/**
	* 功能描述：   根据平台id、站点或区域id查询需要置为有效的标杆价
	* 创建人：yudajun    
	* 创建时间：2014-4-22 下午2:29:09   
	* 修改人：yudajun
	* 修改时间：2014-4-22 下午2:29:09   
	* 修改备注：   
	* 参数： @param advertisingPlatformId
	* 参数： @param siteId
	* 参数： @return
	* @version
	 */
	public List<QuotationMain> findForValid(Long advertisingPlatformId,Long siteId,QuotationApproveStatus approveStaus,QuotationStatus status);
	
	/**
	* 功能描述：   查询一段时间内创建的标杆价
	* 创建人：yudajun    
	* 创建时间：2014-5-19 下午5:07:09   
	* 修改人：yudajun
	* 修改时间：2014-5-19 下午5:07:09   
	* 修改备注：   
	* 参数： @param startDate
	* 参数： @param endDate
	* 参数： @return
	* @version
	 */
	public List<QuotationMain> findQuotationMainListCreateDateBetween(Date startDate,Date endDate);
	
	public Map<Long, String> getIdNumberMap();
}
