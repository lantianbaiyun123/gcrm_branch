package com.baidu.gcrm.data.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.common.util.GcrmFileUtils;
import com.baidu.gcrm.common.util.GcrmHelper;
import com.baidu.gcrm.data.bean.WeekImpressions;
import com.baidu.gcrm.data.dao.IPositionDataSampleRepository;
import com.baidu.gcrm.data.model.PositionDataSample;
import com.baidu.gcrm.data.service.IFileReadRecordService;
import com.baidu.gcrm.data.service.IPositionDataSampleService;

@Service
public class PositionDataSampleServiceImpl implements IPositionDataSampleService {
    @Autowired
    IPositionDataSampleRepository positionSampleDao;

    @Autowired
    IFileReadRecordService fileRecordService;

    private static final int POSITION_COLUMN_COUNT = 6;

    @Override
    public void save(List<PositionDataSample> positionSamples) {
        positionSampleDao.save(positionSamples);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public int readFromFileAndSave(File file) {
        List<PositionDataSample> positionSamples = new ArrayList<PositionDataSample>();
        try {
            List<String[]> datas = GcrmFileUtils.analyzeFile(file, POSITION_COLUMN_COUNT);
            for (String[] data : datas) {
                positionSamples.add(generatePositionDataSample(data));
            }
            save(positionSamples);
            fileRecordService.saveReadFileRecord(StringUtils.trimToEmpty(file.getName()));
        } catch (Exception e) {
            LoggerHelper.err(getClass(), "读取站点统计文件并保存时出错，错误原因：{}，文件：{}", e.getMessage(), file.getName());
            throw new CRMRuntimeException(e);
        }
        return positionSamples.size();
    }

    private PositionDataSample generatePositionDataSample(String[] data) {
        PositionDataSample positionSample = new PositionDataSample();
        positionSample.setDate(data[0]);
        // ecom传回的位置编号是整数，需要前边补0至6位得到原始的值
        positionSample.setPositionNumber(GcrmHelper.getPositionNumber(data[1]));
        positionSample.setImpressions(Long.valueOf(data[2]));
        positionSample.setClicks(Long.valueOf(data[3]));
        positionSample.setUv(Long.valueOf(data[4]));
        positionSample.setClickUv(Long.valueOf(data[5]));
        
        return positionSample;
    }
    
	@Override
	public List<PositionDataSample> findLast7DaysSamplesByPositionNumber(String positionNumber) {
		Date currentDate = DateUtils.getCurrentDate();
		Date aWeekBefore = DateUtils.getNDayFromDate(currentDate, -(Calendar.DAY_OF_WEEK + 1));
		return positionSampleDao.findByPositionNumberAndDateBetween(positionNumber,
				DateUtils.getDate2ShortString(aWeekBefore), DateUtils.getDate2ShortString(currentDate));
	}
	
	@Override
	public List<PositionDataSample> findLast7DaysSamplesByPositionNumberIn(Collection<String> positionNumbers) {
		Date currentDate = DateUtils.getCurrentDate();
		Date aWeekBefore = DateUtils.getNDayFromDate(currentDate, -(Calendar.DAY_OF_WEEK + 1));
		return positionSampleDao.findByPositionNumberInAndDateBetween(positionNumbers,
				DateUtils.getDate2ShortString(aWeekBefore), DateUtils.getDate2ShortString(currentDate));
	}
	
	@Override
	public Map<String, WeekImpressions> findImpressionsInLastWeek(Collection<String> positionNumbers) {
		Map<String, WeekImpressions> impressionsMap = new HashMap<String, WeekImpressions>();
		if (CollectionUtils.isEmpty(positionNumbers)) {
			return impressionsMap;
		}
		List<PositionDataSample> samples = findLast7DaysSamplesByPositionNumberIn(positionNumbers);
		if (CollectionUtils.isEmpty(samples)) {
			return impressionsMap;
		}
		
		for (PositionDataSample sample : samples) {
			WeekImpressions impressions = impressionsMap.get(sample.getPositionNumber());
			if (impressions == null) {
				impressions = new WeekImpressions();
			}
			updateWeekImpressions(impressions, sample);
			impressionsMap.put(sample.getPositionNumber(), impressions);
		}
		return impressionsMap;
	}
	
	@Override
	public WeekImpressions findImpressionsInLastWeek(String positionNumber) {
		WeekImpressions impressions = new WeekImpressions();
		List<PositionDataSample> samples = findLast7DaysSamplesByPositionNumber(positionNumber);
		if (CollectionUtils.isEmpty(samples)) {
			return impressions;
		}
		
		for (PositionDataSample sample : samples) {
			updateWeekImpressions(impressions, sample);
		}
		return impressions;
	}
	
	@Override
	public Map<Long, WeekImpressions> findImpressionsInLastWeek() {
		Map<Long, WeekImpressions> weekImpressions = new HashMap<Long, WeekImpressions>();
		Date currentDate = DateUtils.getCurrentDate();
		Date aWeekBefore = DateUtils.getNDayFromDate(currentDate, -(Calendar.DAY_OF_WEEK + 1));
		List<Object[]> objs = positionSampleDao.findPosDatasOfEnablePos(DateUtils.getDate2ShortString(aWeekBefore),
				DateUtils.getDate2ShortString(currentDate));
		if (CollectionUtils.isEmpty(objs)) {
			return weekImpressions;
		}
		
		for (Object[] obj : objs) {
			Long positionId = (Long) obj[0];
			WeekImpressions impressions = weekImpressions.get(positionId);
			if (impressions == null) {
				impressions = new WeekImpressions();
			}
			PositionDataSample sample = (PositionDataSample) obj[1];
			updateWeekImpressions(impressions, sample);
			
			weekImpressions.put(positionId, impressions);
		}
		return weekImpressions;
	}

	private void updateWeekImpressions(WeekImpressions impressions, PositionDataSample sample) {
		Date date = DateUtils.getString2Date(sample.getDate());
		impressions.setImpressionOfDay(date, sample.getImpressions());
	}
	
	@Override
	public Map<Long, Long> findImpressionsOfYesterday() {
	    Map<Long, Long> yesterdayImpressions = new HashMap<Long, Long>();
        String yesterday = DateUtils.getDate2ShortString(DateUtils.getYesterdayDate());
        List<Object[]> objs = positionSampleDao.findYesterdayPosDatasOfEnablePos(yesterday);
        if (CollectionUtils.isEmpty(objs)) {
            return yesterdayImpressions;
        }
        for (Object[] obj : objs) {
            Long positionId = (Long) obj[0];
            PositionDataSample sample = (PositionDataSample) obj[1];
            yesterdayImpressions.put(positionId, sample.getImpressions());
        }
        return yesterdayImpressions;
	}
	
}
