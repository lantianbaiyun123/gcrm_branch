package com.baidu.gcrm.publish.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.publish.dao.IPublishRecordRepository;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.PublishRecord;
import com.baidu.gcrm.publish.model.PublishRecord.OperateType;
import com.baidu.gcrm.publish.service.IPublishRecordService;

@Service
public class PublishRecordServiceImpl implements IPublishRecordService {
	@Autowired
	IPublishRecordRepository publishRecordDao;
	
	@Override
	public PublishRecord createAndSavePublishRecord(Publish publish, Long operatorId, OperateType type, Date planDate) {
		PublishRecord record = new PublishRecord();
		record.setPublishNumber(publish.getNumber());
		record.setMaterialNumber(publish.getMaterialNumber());
		record.setOperator(operatorId);
		record.setDate(new Date());
		record.setType(type);
		record.setPlanDate(planDate);
		return publishRecordDao.save(record);
	}
	
	@Override
	public PublishRecord createAndSavePublishRecord(Publish publish, Long operatorId, OperateType type) {
		PublishRecord record = new PublishRecord();
		record.setPublishNumber(publish.getNumber());
		record.setMaterialNumber(publish.getMaterialNumber());
		record.setOperator(operatorId);
		record.setDate(new Date());
		record.setType(type);
		return publishRecordDao.save(record);
	}
	
	@Override
	public PublishRecord findById(Long id) {
		return publishRecordDao.findOne(id);
	}
    /**
     * 根据上线单号查询操作历史
     * 
     */
    @Override
    public List<PublishRecord> findByPublishNumber(String number) {
        List<Object> result = publishRecordDao.findPublishRecordByPublishNumber(number);     
        List<PublishRecord> publishRecords = new ArrayList<PublishRecord>();
        Object[] tempObject = null;
        for (Object object : result) {
            tempObject = (Object[]) object;
            PublishRecord record = (PublishRecord) tempObject[0];
            String operatorName = (String) tempObject[1];
            record.setOperatorName(operatorName);
            publishRecords.add(record);
        }
        return publishRecords;
    }
	
	
}
