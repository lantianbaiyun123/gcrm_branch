package com.baidu.gcrm.schedule1.dao;

import java.util.List;

import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.schedule1.model.ScheduleVO;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.web.vo.ScheduleConditionVO;
import com.baidu.gcrm.schedule1.web.vo.ScheduleListVO;

public interface ScheduleRepositoryCustom {

    PageWrapper<ScheduleListVO> findScheduleListByCondition(ScheduleConditionVO scheduleConditionVO);
    
    ScheduleVO findSchedule(Long id);
    
    List<Schedules> findLatestScheduleByAdContentId(Long adContentId);
    
    void releaseScheduleByAdContentId(Long adContentId);
}
