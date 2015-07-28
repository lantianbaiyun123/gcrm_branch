package com.baidu.gcrm.ad.cancel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.ad.cancel.dao.IAdContentCancelRecordRepository;
import com.baidu.gcrm.ad.cancel.service.IAdContentCancelRecordsService;
import com.baidu.gcrm.ad.content.model.AdContentCancelRecord;
import com.baidu.gcrm.ad.content.model.AdContentCancelRecord.CancelReason;
import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.common.ControllerHelper;
import com.baidu.gcrm.user.model.User;
import com.baidu.gcrm.user.service.IUserService;
@Service
public class AdContentCancelRecordsServiceImpl extends ControllerHelper implements IAdContentCancelRecordsService
{
    Logger logger = LoggerFactory.getLogger(AdContentCancelRecordsServiceImpl.class);

    @Autowired
    
    IAdContentCancelRecordRepository cancelrecordrepository;
    
    @Autowired
    IUserService userService;
    
    @Override
    public AdContentCancelRecord saveCancel(AdSolutionContent content, CancelReason reason) {
        if (content == null) {
            return null;
        }
        AdContentCancelRecord record = generateCancelRecord(content, reason);
        cancelrecordrepository.save(record);
        return record;
    }

    private AdContentCancelRecord generateCancelRecord(AdSolutionContent content, CancelReason reason) {
        AdContentCancelRecord record = new AdContentCancelRecord();
        record.setAdSolutionContentId(content.getId());
        record.setAdSolutionId(content.getAdSolutionId());
        record.setAdvertiser(content.getAdvertiser());
        record.setAdNumber(content.getNumber());
        record.setCancelReason(reason);
        generatePropertyForCreate(record);
        record.setSubmitTime(content.getSubmitTime());
        record.setCancelTime(record.getCreateTime());
        User user = userService.findByUcid(record.getCreateOperator());
        if (user != null) {
            record.setOperator(user.getRealname());
        }
        return record;
    }
	
	@Override
	public List<AdContentCancelRecord> saveCancelRecords(List<AdSolutionContent> contents, CancelReason reason) {
	    List<AdContentCancelRecord> records = new ArrayList<AdContentCancelRecord>();
	    if (CollectionUtils.isEmpty(contents)) {
	        return records;
	    }
	    for (AdSolutionContent content : contents) {
	        records.add(generateCancelRecord(content, reason));
        }
	    return cancelrecordrepository.save(records);
	}
	
	@Override
	public List<AdContentCancelRecord> findCancelByadSolutionContentId(
			Long adSolutionContentId) {
		List<AdContentCancelRecord> CancelRecordList=cancelrecordrepository.findByAdSolutionContentId(adSolutionContentId);
		for(AdContentCancelRecord CancelRecord :CancelRecordList){
			User user=userService.findByUcid(CancelRecord.getCreateOperator());
			if(user != null){
				CancelRecord.setOperator(user.getRealname());
			}
		}
		
		return CancelRecordList;
	}
	@Override
	public List<AdContentCancelRecord> findAdCancelRecord(List<Long> contentIds){
		List<AdContentCancelRecord> records = cancelrecordrepository.findByAdSolutionContentIdIn(contentIds);
		for(AdContentCancelRecord record: records){
			User user=userService.findByUcid(record.getCreateOperator());
			record.setOperator(user.getRealname());
		}
		return records;
	}
	@Override
	public List<AdContentCancelRecord> findByAdSolutionId(Long adSolutionId){
		List<AdContentCancelRecord> records = cancelrecordrepository.findByAdSolutionId(adSolutionId);
		for(AdContentCancelRecord record: records){
			User user=userService.findByUcid(record.getCreateOperator());
			if(user != null){
				record.setOperator(user.getRealname());
			}
		}
		return records;
	}

	@Override
	public List<AdContentCancelRecord> findByAdContentId(Long adContentId) {
	    return cancelrecordrepository.findByAdSolutionContentId(adContentId);
	}
	
	@Override
	public void updateCancelRecord(AdContentCancelRecord record) {
	    cancelrecordrepository.save(record);
	}
}
