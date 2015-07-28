package com.baidu.gcrm.schedule1.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.model.Schedules.ScheduleStatus;

public interface ScheduleRepository extends JpaRepository<Schedules, Long> {

    List<Schedules> findByAdContentIdAndStatus(Long adContentId, ScheduleStatus status);
    
    List<Schedules> findByAdContentIdAndStatusNot(Long adContentId, ScheduleStatus status);
    
    List<Schedules> findByAdContentId(Long adContentId);
    
    Schedules findByNumber(String number);
    
    List<Schedules> findByPositionIdAndStatusIn(Long positionId, Collection<ScheduleStatus> status);
    
    @Query("select s from Schedules s,Position p where s.positionId =p.id "
            + " and p.indexStr like ?1 and p.type=?2 and s.status in (?3)")
    List<Schedules> findByIndexStrAndTypeAndStatus(String indexStr, PositionType type,
            Collection<ScheduleStatus> status);
    
    @Modifying
    @Query("update Schedules set completed = 1 where number = ?1")
    void updateScheduleCompletedByNumber(String number);
}
