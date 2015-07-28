package com.baidu.gcrm.occupation1.dao;

import java.util.Date;
import java.util.List;

public interface PositionDateRepositoryCustom {

    Date getFarthestDateByPosition(Long positionId);
    
    int moveEnabledFromPositionOccupationToPositionDate(Date date);
    
    List<Long> findByDateFromTo(Date from, Date to);
}
