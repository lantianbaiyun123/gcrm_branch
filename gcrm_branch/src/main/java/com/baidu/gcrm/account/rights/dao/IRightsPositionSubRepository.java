package com.baidu.gcrm.account.rights.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.account.rights.model.RightsPosition;
import com.baidu.gcrm.account.rights.model.RightsPositionSub;
import com.baidu.gcrm.account.rights.model.RightsPositionSub.PositionSubType;

public interface IRightsPositionSubRepository extends JpaRepository<RightsPositionSub, Long> {
	
	@Query("Select rp From RightsPositionSub rps, RightsPosition rp Where rps.subId = rp.posId And rps.directSub = ?2 And rps.posId = ?1")
	public List<RightsPosition> findSubPosByIdAndType(Long posId, PositionSubType subType);
	
	@Query("Select rp From RightsPositionSub rps, RightsPosition rp Where rps.subId = rp.posId And rps.posId = ?1")
	public List<RightsPosition> findSubPosByPosId(Long posId);
}
