package com.baidu.gcrm.schedule.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.exception.CRMBaseException;
import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.mail.ScheduleCompleteContent;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule.model.Schedule;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;
import com.baidu.gcrm.schedule.web.helper.ScheduleCondition;
import com.baidu.gcrm.schedule.web.vo.ScheduleListBean;
import com.baidu.gcrm.user.model.User;

public interface IScheduleService {

    Schedule findByNumber(String number);

    Map<String, Object> findScheduleAd(Long id, LocaleConstants locale);

    Schedule findByAdContentIdAndStatus(Long adContentId, ScheduleStatus status);

    void lockSchedule(Schedule schedule);

    /**
     * 按条件分页查询排期单列表
     * 
     * @param scheduleCondition
     * @param locale
     * @return
     */
    PageWrapper<ScheduleListBean> findByCondition(ScheduleCondition scheduleCondition);

    Schedule findCurrentScheduleByAdContentId(Long adContentId);

    List<Schedule> findRelaseSchedulesByAdContentId(Long adContentId);

    ScheduleCompleteContent processMailContent(Object[] insert, Map<String, User> operatorMap);

    List<Schedule> findByPositionAndStatus(Position position, Collection<ScheduleStatus> status);

    boolean isScheduleLocked(String scheduleNumber);

    void updateScheduleCompleted(String scheduleNumber);

    List<Schedule> findByAdContentIdAndStatusNot(Long adContentId, ScheduleStatus status);

    public Schedule generateNewSchedule(AdSolutionContent content, String occupyPeriod) throws CRMBaseException;

    public String getOccupyPeriods(Long positionId, List<Date> dates);

    public Schedule saveSchedule(Schedule schedule);
    
    public Schedule releaseSchedule(Schedule schedule);
}
