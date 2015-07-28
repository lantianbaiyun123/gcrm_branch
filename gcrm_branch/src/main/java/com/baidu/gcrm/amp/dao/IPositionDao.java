package com.baidu.gcrm.amp.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;


public interface IPositionDao extends JpaRepository<Position, Long> {
	
	List<Position> findByParentId(Long parentId);
	
//	List<Position> findByStatus(PositionStatus status);
	
	List<Position> findByStatusAndType(PositionStatus status, PositionType type);
}
