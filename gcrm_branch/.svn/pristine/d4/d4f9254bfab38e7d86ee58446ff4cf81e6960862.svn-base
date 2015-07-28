package com.baidu.gcrm.publish.service;

import java.util.List;
import java.util.Date;
import com.baidu.gcrm.publish.model.Publish;
import com.baidu.gcrm.publish.model.PublishRecord;
import com.baidu.gcrm.publish.model.PublishRecord.OperateType;

public interface IPublishRecordService {
	PublishRecord createAndSavePublishRecord(Publish publish, Long operatorId, OperateType type, Date planDate);
	
	PublishRecord createAndSavePublishRecord(Publish publish, Long operatorId, OperateType type);

	PublishRecord findById(Long id);
	
	List<PublishRecord> findByPublishNumber(String number);
}
