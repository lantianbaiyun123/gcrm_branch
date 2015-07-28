package com.baidu.gcrm.publish.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.publish.model.PublishRecord;

public interface IPublishRecordRepository extends JpaRepository<PublishRecord, Long> {
    @Query("SELECT R,u.realname FROM PublishRecord R,User u WHERE R.operator = u.ucid and R.publishNumber=? order by  date")
    public List<Object> findPublishRecordByPublishNumber(String publishNumber);
} 
