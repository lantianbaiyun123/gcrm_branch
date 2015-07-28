package com.baidu.gcrm.quote.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.baidu.gcrm.bpm.vo.StartProcessResponse;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.page.Page;
import com.baidu.gcrm.mail.QuoteCompleteContent;
import com.baidu.gcrm.quote.model.QuotationMain;
import com.baidu.gcrm.quote.web.utils.QuotationMainCondition;
import com.baidu.gcrm.quote.web.vo.QuotationAddVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainConflict;
import com.baidu.gcrm.quote.web.vo.QuotationMainVO;
import com.baidu.gcrm.quote.web.vo.QuotationMainView;
import com.baidu.gcrm.quote.web.vo.QuotationView;

public interface QuotationMainService {

	public QuotationMain findById(Long id);
	/**
	* 功能描述：   按标杆价编号查询
	* 创建人：yudajun    
	* 创建时间：2014-4-9 上午9:22:17   
	* 修改人：yudajun
	* 修改时间：2014-4-9 上午9:22:17   
	* 修改备注：   
	* 参数： @param quoteCode
	* 参数： @return
	* @version
	 */
	public QuotationMain findByQuoteCode(String quoteCode);
	/**
	* 功能描述：   标杆价创建提交
	* 创建人：yudajun    
	* 创建时间：2014-4-9 上午9:38:47   
	* 修改人：yudajun
	* 修改时间：2014-4-9 上午9:38:47   
	* 修改备注：   
	* 参数： @param quotationMainView
	* 参数： @return
	* @version
	 */
	public List<String> submitQuotation(QuotationMainView quotationMainView) throws Exception;
	
	
	/**
	* 功能描述：   标杆价列表
	* 创建人：yudajun    
	* 创建时间：2014-4-10 下午3:52:45   
	* 修改人：yudajun
	* 修改时间：2014-4-10 下午3:52:45   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public Page<QuotationMainVO> findQuotationMainPage(QuotationMainCondition condition,LocaleConstants locale);
	/**
	* 功能描述：   检查标杆价冲突
	* 创建人：yudajun    
	* 创建时间：2014-4-11 下午3:42:03   
	* 修改人：yudajun
	* 修改时间：2014-4-11 下午3:42:03   
	* 修改备注：   
	* 参数： @param quotationMainView
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public List<QuotationMainConflict> checkConflict(QuotationMainView quotationMainView,LocaleConstants locale);
	/**
	* 功能描述：   新标杆价审批通过后，处理原标杆价，返回发送邮件的格式化数据
	* 创建人：yudajun    
	* 创建时间：2014-4-13 下午1:36:25   
	* 修改人：yudajun
	* 修改时间：2014-4-13 下午1:36:25   
	* 修改备注：   
	* 参数： @param quotationMainId
	* 参数： @param locale
	* 参数： @param operator
	* 参数： @return
	* @version
	 */
	public List<QuoteCompleteContent> processQuotationAfterApproved(Long quotationMainId,
	        LocaleConstants locale, Long operator) throws CRMBaseException;
	/**
	* 功能描述：     标杆价详情
	* 创建人：yudajun    
	* 创建时间：2014-4-13 下午3:01:49   
	* 修改人：yudajun
	* 修改时间：2014-4-13 下午3:01:49   
	* 修改备注：   
	* 参数： @param quotationMainId
	* 参数： @param locale
	* 参数： @param request
	* 参数： @return
	* @version
	 */
	public QuotationMainView findQuotationVOById(Long quotationMainId,LocaleConstants locale,HttpServletRequest request);

	StartProcessResponse submitProcess(QuotationMain mian,int i, String username) throws Exception;

	/**
	* 功能描述：   当前标杆价
	* 创建人：yudajun    
	* 创建时间：2014-4-16 下午12:32:28   
	* 修改人：yudajun
	* 修改时间：2014-4-16 下午12:32:28   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public List<QuotationView> findCurrentQuotation(QuotationMainCondition condition,LocaleConstants locale);
	/**
	* 功能描述：   保存
	* 创建人：yudajun    
	* 创建时间：2014-4-18 上午9:09:54   
	* 修改人：yudajun
	* 修改时间：2014-4-18 上午9:09:54   
	* 修改备注：   
	* 参数： @param quotationMain
	* 参数： @return
	* @version
	 */
	public QuotationMain saveAndFlush(QuotationMain quotationMain);
	/**
	* 功能描述：   撤销申请
	* 创建人：yudajun    
	* 创建时间：2014-4-18 上午10:09:34   
	* 修改人：yudajun
	* 修改时间：2014-4-18 上午10:09:34   
	* 修改备注：   
	* 参数： @param quotationMain
	* 参数： @return
	* @version
	 */
	public QuotationMain cancelQuotationApprove(QuotationMain quotationMain);
	/**
	* 功能描述：   将标杆价设置为生效或失效
	* 创建人：yudajun    
	* 创建时间：2014-4-22 下午3:02:05   
	* 修改人：yudajun
	* 修改时间：2014-4-22 下午3:02:05   
	* 修改备注：   
	* 参数： @param advertisingPlatformId
	* 参数： @param siteId
	* @version
	 */
	public void validQuotation(Long advertisingPlatformId,Long siteId);
	QuotationAddVO findluge(Long mainId, LocaleConstants locale);

	/**
	* 功能描述：   首页查询标杆价
	* 创建人：yudajun    
	* 创建时间：2014-5-16 上午9:06:51   
	* 修改人：yudajun
	* 修改时间：2014-5-16 上午9:06:51   
	* 修改备注：   
	* 参数： @param condition
	* 参数： @param locale
	* 参数： @return
	* @version
	 */
	public List<QuotationMainVO> findQuotationMainVOList(QuotationMainCondition condition,LocaleConstants locale);
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
	/**
	 * 
	 * @param quoteMainId
	 */
	void remindQuote(Long quoteMainId);

}

