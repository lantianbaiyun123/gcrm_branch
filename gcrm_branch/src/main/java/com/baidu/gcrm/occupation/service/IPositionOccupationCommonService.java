package com.baidu.gcrm.occupation.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule.model.Schedule;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;

public interface IPositionOccupationCommonService {
	PositionOccupation findByPositionIdAndDate(Long positionId,Date date);
	
	List<PositionOccupation> findOccupationByDateBetween(Long positionId, Date from, Date to);
	
	List<PositionOccupation> findOccupationByIdIn(Collection<Long> ids);
	
	List<Date> findDateById(Collection<Long> ids);
	
	List<Long> findIdsByPositionIdAndDateFrom(Long positionId, Date date);
	
	/**
	 * <p>排期单释放后，则删除当前排期单，同时在历史关联排期单中，查找最近一次的排期单</p>
	 * <li>若排期单还在确认或锁定过程，则此排期单号，在历史关联排期单中删除，记录进入当前关联排期单</li>
	 * <li>若排期单已释放，则继续往前再追溯一单，直至找到为止</li>
	 * <li>若历史关联的排期单均已释放，则清空“当前关联排期单”、“历史关联排期单”字段</li>
	 * <p>所有投放位置的历史排期单删除该排期单</p>
	 * @param scheduleNumber 排期单编号
	 */
	void releaseOccupationDuringProcess(String scheduleNumber, Long adContentId);
	
	void releaseAfter72Hours(String scheduleNumber, Long adContentId);
	
	void updateScheduleFromConfirmedToReserved(String scheduleNumber);
	
	Integer findMaxUsedCountByPositionId(Long positionId, Collection<ScheduleStatus> scheduleStatus);
	
	void updateOccupationTotalAmount(Position position);
	
	void releaseOccupationByDates(String scheduleNumber, List<Date> dates, Long positionId, Long adContentId);
	
	void releaseOccupationAfterPublishTerminate(String scheduleNumber, Long adContentId);
	
	List<Long> releasePositionOccupation(List<Long> positionIds, List<Long> adContentIds);
	
	int releaseConfirmedSchedules(List<Schedule> schedules);
}
