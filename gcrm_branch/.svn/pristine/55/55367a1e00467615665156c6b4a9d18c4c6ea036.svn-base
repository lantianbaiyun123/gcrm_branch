package com.baidu.gcrm.account.rights.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.account.rights.model.RightsPosition;

public interface IRightsPositionRepository extends JpaRepository<RightsPosition, Long> {
	public RightsPosition findByPosId(Long posId);
	
	@Query("From RightsPosition Where posId in (?1) and posDelMark = 0 ")
	public List<RightsPosition> findByPosIds(Collection<Long> posIds);
	
	@Query("From RightsPosition Where posTag = ?1 and posDelMark = 0 ")
	public RightsPosition findByPosTag(String posTag);
}
