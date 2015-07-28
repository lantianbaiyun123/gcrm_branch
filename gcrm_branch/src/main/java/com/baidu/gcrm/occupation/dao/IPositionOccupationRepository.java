package com.baidu.gcrm.occupation.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.occupation.model.PositionOccupation.OccupationStatus;

public interface IPositionOccupationRepository extends JpaRepository<PositionOccupation, Long> {

	@Query("from PositionOccupation po where po.positionId = ?1 and (po.date between ?2 and ?3) order by date")
	List<PositionOccupation> findOccupationByDateBetween(Long positionId, Date from, Date to);
	
	@Query("from PositionOccupation po where po.positionId = ?1 and po.date in(?2)")
	List<PositionOccupation> findOccupationByDateIn(Long positionId, Collection<Date> dates);
	
	@Query("from PositionOccupation where positionId = ?1 and (date between ?2 and ?3) and curScheduleNumber is not null")
	List<PositionOccupation> findOccupiedByPositionAndDateBetween(Long positionId, Date from, Date to);
	
	List<PositionOccupation> findByPositionId(Long positionId);
	
	PositionOccupation findByPositionIdAndDate(Long positionId, Date date);
	
	@Modifying
	@Query("update PositionOccupation po set po.status = ?1 where po.date >= ?2 and po.positionId = ?3")
	int updateStatus(OccupationStatus status, Date date, Long positionId);
	
	List<PositionOccupation> findByIdIn(Collection<Long> ids);
	
	@Query("from PositionOccupation where id in (?1) and (historySchedules <> '' or historySchedules is not null)")
	List<PositionOccupation> findByIdInAndHistorySchedulesNotEmpty(Collection<Long> ids);
	
	@Query("select id from PositionOccupation where positionId = ?1 and date >= ?2")
	List<Long> findIdsByPositionIdAndDateFrom(Long positionId, Date date);
	
	@Query("from PositionOccupation po where positionId = ?1 and date in(?2) and soldAmount>=totalAmount and ("
			+ " LOCATE(?3,curScheduleNumber)=0 or ( LOCATE(?3,curScheduleNumber)>0 and "
			+ "( po.historySchedules <>'' and po.historySchedules is not null)))")
	List<PositionOccupation> findUpdateContentInertDates(Long positionId, Collection<Date> dates,String oldScheduleNumber);
	
	@Query("select date from PositionOccupation where positionId = ?1 and date >= ?2 and soldAmount>=totalAmount")
	List<Date> findInsertDateByPositionIdFrom(Long positionId, Date from);
	
	@Modifying
	@Query("update PositionOccupation set curScheduleNumber = '', historySchedules = '', soldAmount = 0 where positionId in (?1)")
	int clearScheduleNumberByPositionId(List<Long> positionIds);
	
	@Query("select date from PositionOccupation where id in (?1) order by date")
	List<Date> findDateByIdIn(Collection<Long> ids);
	
    List<PositionOccupation> findByStatusAndSoldAmountGreaterThanAndDateGreaterThan(OccupationStatus status,
            int minSoldAmount, Date fromDate);
}
