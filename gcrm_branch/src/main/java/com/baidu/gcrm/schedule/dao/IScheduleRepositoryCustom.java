package com.baidu.gcrm.schedule.dao;

import java.util.List;
import java.util.Map;

import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;
import com.baidu.gcrm.schedule.web.helper.ScheduleCondition;
import com.baidu.gcrm.schedule.web.vo.ScheduleListBean;

public interface IScheduleRepositoryCustom {
	List<String> findNumbersByPositionAndStatus(Long positionId, ScheduleStatus status);
	
	Map<String, Object> findSchedule(Long id);
	
	PageWrapper<ScheduleListBean> findScheduleListByCondition(ScheduleCondition scheduleCondition);
	
}
