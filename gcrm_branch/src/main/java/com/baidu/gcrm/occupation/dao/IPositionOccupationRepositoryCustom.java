package com.baidu.gcrm.occupation.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.personalpage.web.vo.ChannelOperationVO;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;

public interface IPositionOccupationRepositoryCustom {
	
	Date getFarthestDateByPosition(Long positionId);
	
	List<Date> getReservedDateBetween(Long positionId, Date from, Date to);
	
	Map<Long, Long> getReservedPositionCountBetween(Long positionId, Date from, Date to);
	
	Map<Long, Date> getDateMapByDateBetween(Long positionId, Date from, Date to);
	
	List<PositionOccupation> findByCurScheduleNumberFazzyLike(String scheduleNumber);
	
	List<PositionOccupation> findByCurScheduleNumberFazzyLikeFrom(String scheduleNumber, Date from);

	void batchUpdateAfterConfirm(List<PositionOccupation> occupations, List<String> conditions);
	
	void batchUpdateAfterRelease(List<PositionOccupation> occupations, List<String> conditions);
	/**
	 * 按时间段查询位置投放记录
	 * @param from
	 * @param to
	 * @return
	 */
	List<PositionOccupation> findByDateFromTo(Date from, Date to);
	
	List<PositionOccupation> findByHistoryScheduleNumberFazzyLike(String scheduleNumber);
	
	Map<String, Long> getDateOccIdMapFrom(Long positionId, Date from);
	
	Map<Long, Date> getOccIdDateMapFrom(Long positionId, Date from);
	
	Map<Long, Date> getOccIdDateMapInOccIds(List<Long> occIds);
	
	Integer findMaxUsedCountByPositionId(Long positionId, Collection<ScheduleStatus> scheduleStatus);
	
	int updateOccupationTotalAmount(Position position);
	List<ChannelOperationVO> getPositionFull();

}
