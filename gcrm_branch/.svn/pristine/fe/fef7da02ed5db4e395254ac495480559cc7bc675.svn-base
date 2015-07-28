package com.baidu.gcrm.data.service;

import java.util.Date;
import java.util.List;

import com.baidu.gcrm.data.model.ADContentDataSample;
import com.baidu.gcrm.publish.model.PublishDate;

public interface IPublishDateStatisticsService {
	void initByPublishNumber(String publishNumber);
	
	/**
	 * 更新每天的采样数据到上线单对应的时间段
	 * @param contentSamples
	 */
	void updateStatisticsFromSamples(List<ADContentDataSample> contentSamples);
	
	/**
	 * 上线单更新后（由于方案变更或内容修改导致生成新的上线单和上线时间段），初始化修改后内容对应的统计数据
	 * @param publishDates
	 * @param oldPublishNumber
	 * @param approvalDate
	 */
	void initStatisticsAfterPublishUpdate(List<PublishDate> publishDates, String oldPublishNumber, Date approvalDate);
	
	/**
	 * 删除指定上线单的未开始上线时间段的数据
	 * @param publishNumber
	 * @param approvalDate
	 */
	void deleteNotStartStatistics(String publishNumber, Date approvalDate);
	
	int deleteByPublishNumber(String publishNumber);
}
