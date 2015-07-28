package com.baidu.gcrm.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.data.model.FileReadRecord;

public interface IFileReadRecordRepository extends JpaRepository<FileReadRecord, Long> {
	FileReadRecord findByFilename(String filename);
}
