package com.baidu.gcrm.schedule.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.schedule.model.Schedule;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;

public interface IScheduleRepository extends JpaRepository<Schedule, Long> {
	Schedule findByNumber(String number);
	
	Collection<Schedule> findByNumberIn(Collection<String> numbers);

	List<Schedule> findByAdContentIdAndStatusNot(Long adContentId,ScheduleStatus status);
	
	List<Schedule> findByAdContentIdIn(Collection<Long> adContentIds);
	
	@Query("from Schedule where adContentId=?1 and status=?2")
	List<Schedule> findByAdContentIdAndStatus(Long id,ScheduleStatus status);
	
	@Query("from Schedule where adContentId=?1 and status=?2 order by id desc")
	List<Schedule> findByContentIdAndStatusAndOrder(Long id, ScheduleStatus status);
	
	@Query("from Schedule where adContentId=?1 and status in ?2")
	List<Schedule> findByAdContentIdAndStatusIn(Long id,List<ScheduleStatus> status);
	
    @Query("from Schedule s where s.adContentId = ?1 and s.positionId= ?2")
    List<Schedule> findByAdContentIdAndPositionId(Long adContentId,Long positionId);
    
    @Query("from Schedule s where s.adContentId in (?1) and status = ?2")
    List<Schedule> findByAdContentIdInAndStatus(Collection<Long> adContentIds, ScheduleStatus status);
    
    @Query("select r.insertedAdsolutionContentId,(count(*)- max(r.allowInsert)) as allowInsert from Schedule s, "
    		+ "AdSolutionContent ac,ApprovalInsertRecord r where s.adContentId=ac.id"
    		+ " and ac.id=r.adsolutionContentId and s.number=?1 group by r.insertedAdsolutionContentId")
    List<Object[]> findInsertedAdContentByNumber(String number);
    
    @Query("select s.number,r.insertPeriod,(count(*)- sum(r.allowInsert)) as allowInsert,a.operator,ac.siteId,ac.productId,"
    		+ "ac.submitTime,ac.number,s.periodDescription from "
    		+ "Schedule s,AdSolutionContent ac,ApprovalInsertRecord r,AdvertiseSolution a "
    		+ " where s.adContentId=ac.id and ac.id=r.insertedAdsolutionContentId "
    		+ "and ac.adSolutionId=a.id and r.adsolutionContentId=?1 and (s.status='2' or s.status='1'"
    		+ "or (s.status='3' and ac.contentType='update'))"
    		+ " and (r.isHistory='0' or r.isHistory is null) group by s.number,r.insertPeriod,a.operator")
    List<Object[]> findInsertedScheduleByAdContentId(Long adContentId);
    
    @Query("select '','','', a.operator,ac.siteId,ac.productId,ac.submitTime,ac.number,'' from "
    		+ "AdSolutionContent ac,AdvertiseSolution a  where ac.adSolutionId=a.id and ac.id=?1")
    List<Object[]> findScheduleMailContentByAdContentId(Long adContentId);
    
    @Query("select '','','', a.operator,ac.siteId,ac.productId,"
    		+ "ac.submitTime,ac.number,s.periodDescription from "
    		+ "Schedule s,AdSolutionContent ac,AdvertiseSolution a "
    		+ " where s.adContentId=ac.id and ac.adSolutionId=a.id and s.id=?1")
    List<Object[]> findScheduleMailContentByAdScheduleId(Long scheduleId);

    @Query("from Schedule s where s.adContentId in(?1) and s.positionId = ?2 and s.status != ?3")
    List<Schedule> findByAdContentIdInAndPositionIdAndStatusNot(Collection<Long> adContentIds, Long positionId, ScheduleStatus status);
    
    List<Schedule> findByPositionIdAndStatusNot(Long positionId, ScheduleStatus status);
    @Query("select s2.number from Schedule s1,AdSolutionContent ac, Schedule s2 where s1.adContentId=ac.id and "
    		+ "ac.oldContentId = s2.adContentId and ac.contentType='update' and s1.number=?1")
    List<String> findUpdatedScheduleNumber(String number);
    
//    @Modifying
//    @Query("update AdSolutionContent a set a.periodDescription=?2,a.insertPeriodDescription=?3 where id =?1")
//    void updateContentPeriodBySchedule(Long adContentId,String periodDescription,String insertPeriodDescription);
    
    @Query(" from Schedule where positionId = ?1 and status in (?2)")
    List<Schedule> findByPositionIdAndStatus(Long positionId, Collection<ScheduleStatus> status);
    
    @Query("select s from Schedule s,Position p where s.positionId =p.id and p.indexStr like ?1 and p.type=?2 and s.status in (?3)")
    List<Schedule> findByIndexStrAndTypeAndStatus(String indexStr, PositionType type, Collection<ScheduleStatus> status);
    
    @Query(" from Schedule where positionId in (?1) and status in (?2)")
    List<Schedule> findByPositionIdsAndStatus(List<Long> positionIds, Collection<ScheduleStatus> status);
    
    @Modifying
    @Query("update Schedule set status = ?2 where positionId in (?1)")
    int updateScheduleStatusByPositionIdIn(List<Long> positionIds, ScheduleStatus status);
    
    @Modifying
    @Query("update Schedule set completed = 1 where number = ?1")
    int updateScheduleCompleted(String number);
    
    @Query("from Schedule where status = ?1 and periodDescription != '' and (periodDescription between ?2 and ?3)")
    List<Schedule> findSchedulesBeginningInDays(ScheduleStatus status, String dateFrom, String dateTo);
}
