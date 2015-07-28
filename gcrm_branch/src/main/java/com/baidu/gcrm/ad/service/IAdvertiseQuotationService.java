package com.baidu.gcrm.ad.service;

import java.util.List;

import com.baidu.gcrm.ad.model.AdvertiseQuotation;
import com.baidu.gcrm.ad.web.vo.QuotationRecordVO;

public interface IAdvertiseQuotationService {
	
	/**
	 * 保存报价投放量信息
	 * 
	 * @param quotation 报价投放量信息
	 */
	public void save(AdvertiseQuotation quotation);
	
	void deleteByAdSolutionContentId(Long adSolutionContentId);
	
	/**
	 * 获取报价投放量信息
	 * 
	 * @param id
	 * @return
	 */
	public AdvertiseQuotation getAdvertiseQuotationById(Long id);
	
	/**
	 * 根据广告内容获取报价情况
	 * @param id 广告内容ID
	 * @return
	 */
	public AdvertiseQuotation findByAdSolutionContentId(Long id);
	
	List<AdvertiseQuotation> findQuotationByAdContentIds(List<Long> adContentIds);
	
	/**
	 * 根据广告内容ID获取报价的备案、折扣、我方分成信息
	 * @param contentId 广告内容ID
	 * @return
	 */
	public QuotationRecordVO findQuotationRecordByContent(Long contentId);
	
	/**
	 * 根据广告方案ID获取报价的备案、折扣、我方分成信息
	 * @param solutionId 广告方案ID
	 * @return
	 */
	public List<QuotationRecordVO> findQuotationRecordBySolution(Long solutionId);
	
	/**
	 * 
	 * moveToHistory:将广告内容里的报价信息移入历史表. <br/>
	 *
	 * @param adContentIds
	 * @since JDK 1.6
	 */
	void moveToHistory(List<Long> adContentIds);
	
	void deleteByAdSolutionContentIdIn(List<Long> adSolutionContentIds);

}
