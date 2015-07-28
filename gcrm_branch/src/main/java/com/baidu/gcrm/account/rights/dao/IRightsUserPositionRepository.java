package com.baidu.gcrm.account.rights.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.account.rights.model.RightsPosition;
import com.baidu.gcrm.account.rights.model.RightsUserPosition;
import com.baidu.gcrm.user.model.User;

public interface IRightsUserPositionRepository extends JpaRepository<RightsUserPosition, Long> {
	@Modifying
	@Query("Delete From RightsUserPosition Where ucId = ?1")
	public void deleteByUcId(Long ucId);
	
	@Query("Select rp From RightsPosition rp, RightsUserPosition rup Where rup.posId = rp.posId And rup.ucId = ?1 And rp.posDelMark = 0")
	public List<RightsPosition> findPosByUcId(Long ucId);
	
	@Query("Select rp.posTag, u From User u, RightsPosition rp, RightsUserPosition rup "
			+ "Where rup.posId = rp.posId And u.ucid = rup.ucId And rp.posId in (?1)")
	public List<Object[]> findPosUserByPosIds(Collection<Long> posIds);
	
	@Query("Select distinct u From User u, RightsUserPosition rup Where u.ucid = rup.ucId And rup.posId in (?1)")
	public List<User> findUsersByPosIds(Collection<Long> posIds);
	
	@Query("SELECT DISTINCT u FROM RightsPositionSub psub, RightsUserPosition upsub, RightsUserPosition up, User u "
			+ "WHERE u.ucid = up.ucId AND up.posId = psub.posId AND upsub.posId = psub.subId AND upsub.ucId = ?1 ")
	public List<User> findLeadersByUcId(Long ucId);
	
}
