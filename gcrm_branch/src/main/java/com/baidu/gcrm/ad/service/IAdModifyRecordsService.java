package com.baidu.gcrm.ad.service;

import java.util.List;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.ad.model.AdvertiseSolution;
import com.baidu.gcrm.log.model.ModifyRecord;

public interface IAdModifyRecordsService {
	
	/**
	 * 提交方案后的广告方案才需要记录修改记录
	 * @param adSolutionId
	 * @return
	 */
	public boolean isSaveModifyRecords(Long adSolutionId);
	
	/**
	 * 保存广告方案的修改记录
	 * 
	 * @param solution 保存的广告方案
	 */
	public void saveAdSolutionModifyRecords(AdvertiseSolution solution);
	
	/**
	 * 保存广告内容的修改记录
	 * 
	 * @param contentView 保存的广告内容
	 */
	public void saveAdContentModifyRecords(AdSolutionContentView contentView);
	
	/**
	 * 保存广告内容的修改记录
	 * 
	 * @param contentView 保存的广告内容
	 */
	public List<ModifyRecord> saveAdContentModifyRecords(AdSolutionContentView newContentView,
			AdSolutionContentView oldContentView);
	
	/**
	 * 保存广告内容的修改记录
	 * 
	 * @param content 保存的广告内容
	 */
	public void removeAdContentModifyRecords(AdSolutionContent content);

}
