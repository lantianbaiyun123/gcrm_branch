package com.baidu.gcrm.data.service;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.data.bean.WeekImpressions;
import com.baidu.gcrm.data.model.PositionDataSample;

public interface IPositionDataSampleService {
	void save(List<PositionDataSample> positonSamples);
	
	/**
	 * 读取文件数据并保存
	 * @param file
	 * @return 成功保存记录个数
	 * @throws CRMBaseException 
	 */
	int readFromFileAndSave(File file) throws CRMBaseException;
	
	List<PositionDataSample> findLast7DaysSamplesByPositionNumberIn(Collection<String> positionNumbers);
	
	List<PositionDataSample> findLast7DaysSamplesByPositionNumber(String positionNumber);
	
	WeekImpressions findImpressionsInLastWeek(String positionNumber);
	
	Map<String, WeekImpressions> findImpressionsInLastWeek(Collection<String> positionNumbers);
	
	/**
	 * 获取所有有效位置的一周每天展示量
	 * @return key是位置id，value是该位置的一周展示量
	 */
	Map<Long, WeekImpressions> findImpressionsInLastWeek();
	
	/**
	 * 获取所有有效位置的前一天的展示量
	 */
	Map<Long, Long> findImpressionsOfYesterday();
}
