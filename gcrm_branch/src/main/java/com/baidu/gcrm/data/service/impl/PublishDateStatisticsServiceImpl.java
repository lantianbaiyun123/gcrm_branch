package com.baidu.gcrm.data.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.data.dao.IPublishDateStatisticsRepository;
import com.baidu.gcrm.data.model.ADContentDataSample;
import com.baidu.gcrm.data.model.PublishDateStatistics;
import com.baidu.gcrm.data.service.IPublishDateStatisticsService;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.service.IPublishDateService;

@Service
public class PublishDateStatisticsServiceImpl implements IPublishDateStatisticsService {
	
	@Autowired
	IPublishDateStatisticsRepository publishDateStatisticsDao;
	
	@Autowired
	IPublishDateService publishDateService;

	@Override
	public void initByPublishNumber(String publishNumber) {
		publishDateStatisticsDao.initByPublishNumber(publishNumber);
	}

	@Override
	public void updateStatisticsFromSamples(List<ADContentDataSample> contentSamples) {
		if (CollectionUtils.isEmpty(contentSamples)) {
			return;
		}
		List<PublishDateStatistics> updateStatistics = new ArrayList<PublishDateStatistics>();
		for (ADContentDataSample contentSample : contentSamples) {
			Long adContentId = contentSample.getAdContentId();
			String date = contentSample.getDate();
			List<PublishDateStatistics> statisticsList = publishDateStatisticsDao.findByAdContentIdPublishDate(
					adContentId, DateUtils.getString2Date(date));
			if (CollectionUtils.isEmpty(statisticsList)) {
				LoggerHelper.err(getClass(), "Update statistics error, no satisfied date, id: {}, date: {}, contentId: {}",
						 contentSample.getId(), date, adContentId);
				continue;
			}
			// if there are two results, update the first
			// eg: 1)9.15-9.18 2)9.18-9.20, update statistics of 9.18 to 1)
			PublishDateStatistics statistics = statisticsList.get(0);
			LoggerHelper.info(getClass(), "before update: {}", statistics);
			statistics.setClicks(statistics.getClicks() + contentSample.getClicks());
			statistics.setImpressions(statistics.getImpressions() + contentSample.getImpressions());
			statistics.setUv(statistics.getUv() + contentSample.getUv());
			statistics.setClickUv(statistics.getClickUv() + contentSample.getClickUv());
			LoggerHelper.info(getClass(), "after update: {}", statistics);
			updateStatistics.add(statistics);
		}
		publishDateStatisticsDao.save(updateStatistics);
		LoggerHelper.info(getClass(), "Update statistics successfully, total count:{}", updateStatistics.size());
	}
	
	@Override
	public void initStatisticsAfterPublishUpdate(List<PublishDate> publishDates, String oldPublishNumber, Date approvalDate) {
		List<PublishDate> oldPublishDates = publishDateService.findByPublishNumber(oldPublishNumber);
		for (PublishDate publishDate : oldPublishDates) {
			switch (publishDate.getStatus()) {
				case not_start :
					if (!publishDate.getPlanStart().before(approvalDate)) {
						// FIXME 可能会出现时间段跨越approvalDate，与修改后的内容的时间段重合
						break;
					}
					publishDateStatisticsDao.deleteByPublishDateId(publishDate.getId());
					LoggerHelper.info(getClass(), "delete not start statistics. {}", publishDate);
					break;
				case ongoing :
					LoggerHelper.err(getClass(), "old publish date is still ongoing. {}", publishDate);
					break;
				case end :
					LoggerHelper.info(getClass(), "keep old statistics. {}", publishDate);
					break;
				default :
						break;
			}
		}
		for (PublishDate publishDate : publishDates) {
			switch (publishDate.getStatus()) {
				case not_start :
					if (publishDate.getPlanStart().before(approvalDate)) {
						break;
					}
					publishDateStatisticsDao.initByPublishDateId(publishDate.getId());
					LoggerHelper.info(getClass(), "initialize not start statistics. {}", publishDate);
					break;
				case ongoing :
					LoggerHelper.err(getClass(), "new publish date is ongoing. {}", publishDate);
					break;
				case end :
					LoggerHelper.info(getClass(), "skip completed publish date. {}", publishDate);
					break;
				default :
					break;
			}
		}
	}
	
	@Override
	public void deleteNotStartStatistics(String publishNumber, Date approvalDate) {
		List<PublishDate> oldPublishDates = publishDateService.findByPublishNumber(publishNumber);
		for (PublishDate publishDate : oldPublishDates) {
			switch (publishDate.getStatus()) {
				case not_start :
					if (!publishDate.getPlanStart().before(approvalDate)) {
						break;
					}
					publishDateStatisticsDao.deleteByPublishDateId(publishDate.getId());
					LoggerHelper.info(getClass(), "delete not start statistics. {}", publishDate);
					break;
				case ongoing :
					LoggerHelper.err(getClass(), "old publish date is still ongoing. {}", publishDate);
					break;
				case end :
					LoggerHelper.info(getClass(), "keep old statistics. {}", publishDate);
					break;
				default :
					break;
			}
		}
	}
	
	@Override
	public int deleteByPublishNumber(String publishNumber) {
		return publishDateStatisticsDao.deleteByPublishNumber(publishNumber);
	}
}
