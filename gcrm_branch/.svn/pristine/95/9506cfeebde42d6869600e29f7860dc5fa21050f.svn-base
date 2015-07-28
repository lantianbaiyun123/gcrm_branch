package com.baidu.gcrm.data.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.baidu.gcrm.data.model.PositionDataSample;

public interface IPositionDataSampleRepository extends JpaRepository<PositionDataSample, Long> {

    @Query("from PositionDataSample where positionNumber = ?1 order by date desc limit 7")
    List<PositionDataSample> findLast7DaysSamplesByPositionNumber(String positionNumber);

    List<PositionDataSample> findByPositionNumberAndDateBetween(String positionNumber, String fromDate, String toDate);

    List<PositionDataSample> findByPositionNumberInAndDateBetween(Collection<String> positionNumbers, String fromDate,
            String toDate);

    @Query("select p.id, pds from Position p, PositionDataSample pds where p.positionNumber = pds.positionNumber"
            + " and p.status = 1 and pds.date between ?1 and ?2")
    List<Object[]> findPosDatasOfEnablePos(String fromDate, String toDate);
    
    @Query("select p.id, pds from Position p, PositionDataSample pds where p.positionNumber = pds.positionNumber"
            + " and p.status = 1 and pds.date = ?1")
    List<Object[]> findYesterdayPosDatasOfEnablePos(String yesterday);
}
