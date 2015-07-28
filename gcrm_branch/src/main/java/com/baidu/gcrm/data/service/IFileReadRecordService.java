package com.baidu.gcrm.data.service;

import com.baidu.gcrm.data.model.FileReadRecord;

public interface IFileReadRecordService {
	FileReadRecord findByFilename(String filename);
	
	void save(FileReadRecord record);
	
	boolean checkIfHasRead(String filename);
	
	void saveReadFileRecord(String filename);
}
