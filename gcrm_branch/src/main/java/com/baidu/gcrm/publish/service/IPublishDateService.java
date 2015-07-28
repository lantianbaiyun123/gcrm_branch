package com.baidu.gcrm.publish.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.publish.model.ForcePublishProof;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;

public interface IPublishDateService {
	
	List<PublishDate> findByPublishNumberAndStatus(String publishNumber, PublishPeriodStatus status);

	/**
	 * 上线
	 * @param id 待上线时间段id
	 */
	void publish(Long id, Long operatorId);
	
	/**
	 * 下线
	 * @param id 待下线时间段id
	 */
	void unpublish(Long id, Long operatorId);
	
	/**
	 * 强制上线
	 * @param id 待强制上线时间段id
	 */
	void forcePublish(Long id, Long operatorId);
	
	void forcePublish(Long id, Long operatorId, List<ForcePublishProof> prooves);
	
	PublishDate save(PublishDate publishDate);
	
	List<PublishDate> saveAll(Collection<PublishDate> publishDates);
	
	List<PublishDate> generatePublishDatesAndSave(List<DatePeriod> periods, String publishNumber);
	
	List<PublishDate> generatePublishDatesByPeriods(List<DatePeriod> periods, String publishNumber);
	
	void deleteByPublishNumber(String publishNumber);
	
	List<PublishDate> findByPublishNumber(String publishNumber);
	
	/**
	 * 下线指定上线时段的上线单
	 * @param publishDates
	 * @param endDate
	 * @param endOperatorId
	 */
	void unpublishAllAfterRelease(List<PublishDate> publishDates, Date endDate, Long endOperatorId);
	
	/**
	 * 广告方案变更，排期单确认后，合并已经上线的时间和修改后的上线时间
	 * @param newPeriods 变更后新排期的时间
	 * @param approvalDate	广告内容审核通过的日期，即原上线单释放的时间
	 * @param newPublishNumber	新排期单对应的上线单编号
	 * @param oldPublishNumber	旧上线单对应的上线单编号
	 * @return
	 */
	List<PublishDate> combinePublishDatesAndSave(List<DatePeriod> newPeriods, Date approvalDate, String newPublishNumber, String oldPublishNumber);

	List<PublishDate> findByPublishNumberAndStatusNot(String publishNumber, PublishPeriodStatus status);
	
	void batchUnpublishTimer();
	
	List<PublishDate> findByPublishNumberAndPlanStartLessThan(String publishNumber, Date date);
	
	public void batchPublishTimer();
	
	public void autoPublish(List<Publish> unpublishes);
	
	public List<PublishDate> findByAdContentId(Long contentId);
	
	/**
	 * 系统执行提前下线所有时间段，并且删除以后的还未上线的时间段
	 * @param publish
	 */
	void unpublishAndRemovePublishDates(Publish publish);
}
