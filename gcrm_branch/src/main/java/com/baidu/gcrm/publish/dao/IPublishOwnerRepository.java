package com.baidu.gcrm.publish.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.publish.model.PublishOwner;

public interface IPublishOwnerRepository extends JpaRepository<PublishOwner, Long> {

	public List<PublishOwner> findByPositionIdIn(List<Long> channelIdList);
	
	public PublishOwner findByPositionId(Long channelId);
}
