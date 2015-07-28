package com.baidu.gcrm.log.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baidu.gcrm.log.model.ModifyRecord;

@Repository
public interface ModifyRecordRepository extends JpaRepository<ModifyRecord, Long>,ModifyRecordRepositoryCustome {
	
	public List<ModifyRecord> findByTableNameAndModifiedDataIdOrderByCreateTimeDesc(String tableName,Long modifiedDataId);
}
