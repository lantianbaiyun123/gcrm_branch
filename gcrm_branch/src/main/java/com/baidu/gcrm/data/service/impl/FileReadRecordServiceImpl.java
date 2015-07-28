package com.baidu.gcrm.data.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baidu.gcrm.data.dao.IFileReadRecordRepository;
import com.baidu.gcrm.data.model.FileReadRecord;
import com.baidu.gcrm.data.service.IFileReadRecordService;

@Service
public class FileReadRecordServiceImpl implements IFileReadRecordService {
	@Autowired
	IFileReadRecordRepository fileRecordDao;

	@Override
	public FileReadRecord findByFilename(String filename) {
		return fileRecordDao.findByFilename(filename);
	}

	@Override
	public void save(FileReadRecord record) {
		fileRecordDao.save(record);
	}

	@Override
	@Transactional(propagation=Propagation.NOT_SUPPORTED,readOnly=true)
	public boolean checkIfHasRead(String filename) {
		FileReadRecord record = findByFilename(filename);
		return record != null;
	}
	
	@Override
	public void saveReadFileRecord(String filename) {
		FileReadRecord record = new FileReadRecord();
		record.setFilename(filename);
		record.setReadTime(new Date());
		save(record);
	}
}
