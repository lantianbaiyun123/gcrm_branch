package com.baidu.gcrm.quote.service;

import java.util.List;

import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.quote.model.Quotation;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.model.QutationModifyRecord;

public interface IQuotationModifyRecordService {

	/**
	* 功能描述：    保存Main的修改记录 返回要不要保存子表修改记录
	* 创建人：yudajun    
	* 创建时间：2014-4-22 下午6:59:04   
	* 修改人：yudajun
	* 修改时间：2014-4-22 下午6:59:04   
	* 修改备注：   
	* 参数： @param oldObject
	* 参数： @param newObject
	* @version
	 */
	public boolean saveQuotationMainModifyRecord(QuotationMain oldObject,QuotationMain newObject);
	/**
	* 功能描述：   保存子表修改记录
	* 创建人：yudajun    
	* 创建时间：2014-4-22 下午6:58:52   
	* 修改人：yudajun
	* 修改时间：2014-4-22 下午6:58:52   
	* 修改备注：   
	* 参数： @param oldObject
	* 参数： @param newObject
	* 参数： @param mainId
	* @version
	 */
	public void saveQuotationModifyRecord(List<Quotation> oldObject,List<Quotation> newObject,Long mainId);
	/**
	* 功能描述：   查询修改日志
	* 创建人：yudajun    
	* 创建时间：2014-4-24 上午11:53:27   
	* 修改人：yudajun
	* 修改时间：2014-4-24 上午11:53:27   
	* 修改备注：   
	* 参数： @param quotationMainId
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public List<QutationModifyRecord> findQuotationModifyRecord(Long quotationMainId,LocaleConstants locale);
}
