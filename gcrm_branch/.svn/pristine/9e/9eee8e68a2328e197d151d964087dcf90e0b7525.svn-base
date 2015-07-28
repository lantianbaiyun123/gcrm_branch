package com.baidu.gcrm.occupation.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IADPositionDateRelationRepositoryCustom {
	List<Long> findAdcontentIdsByPositionOccIdIn(List<Long> positionOccIds);
	
	/**
	 * 获取没有被预订、确认、锁定的投放时间
	 * @param positionOccids
	 * @return
	 */
	List<Long> findIdlePositionOccIds(Collection<Long> positionOccids);
	
	Map<Long, Long> getCountGroupByPositionOccId(Collection<Long> positionOccids);

	/**
	 * 获取指定投放编号对应的预定、确认、锁定的排期单数量，对于轮播一个投放编号可能对应多个符合状态的排期单
	 * @param positionOccIds
	 * @return
	 */
	Map<Long, Long> getBusyCountGroupByPositionOccId(Collection<Long> positionOccIds);

	Date getMaxIdleOccupationDate(Long positionId);

	Date getMaxOccupiedOccupationDate(Long positionId);
}
