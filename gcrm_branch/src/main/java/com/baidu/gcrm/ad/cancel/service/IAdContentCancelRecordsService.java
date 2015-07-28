package com.baidu.gcrm.ad.cancel.service;

import java.util.List;

import com.baidu.gcrm.ad.content.model.AdContentCancelRecord;
import com.baidu.gcrm.ad.content.model.AdContentCancelRecord.CancelReason;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;


public interface IAdContentCancelRecordsService {

	AdContentCancelRecord saveCancel(AdSolutionContent contnt,CancelReason reason);
	
	List<AdContentCancelRecord> findCancelByadSolutionContentId(Long adSolutionContentId);
	
	List<AdContentCancelRecord> findAdCancelRecord(List<Long> contentIds);
	
	List<AdContentCancelRecord> findByAdSolutionId(Long adSolutionId);
	
	List<AdContentCancelRecord> findByAdContentId(Long adContentId);
	
	void updateCancelRecord(AdContentCancelRecord record);
	
	List<AdContentCancelRecord> saveCancelRecords(List<AdSolutionContent> contents, CancelReason reason);
}
