package com.baidu.gcrm.schedule1.service;

import java.util.Collection;
import java.util.List;

import com.baidu.gcrm.ad.content.model.AdSolutionContent;
import com.baidu.gcrm.common.LocaleConstants;
import com.baidu.gcrm.common.page.PageWrapper;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule1.model.ScheduleVO;
import com.baidu.gcrm.schedule1.model.Schedules;
import com.baidu.gcrm.schedule1.model.Schedules.ScheduleStatus;
import com.baidu.gcrm.schedule1.web.vo.ScheduleConditionVO;
import com.baidu.gcrm.schedule1.web.vo.ScheduleListVO;

public interface ISchedulesService {
    
    Schedules findByNumber(String number);
    
    void lockSchedule(Schedules schedule);
    
    void releaseSchedule(Schedules schedule);
    
    void releaseScheduleByAdContentId(Long adContentId);
    
    void saveOrUpdateSchedule(Schedules schedule);
    
    void updateScheduleCompleted(String number);
    
    List<Schedules> findByPositionAndStatus(Position position, Collection<ScheduleStatus> status);
    
    List<Schedules> findByAdContentIdAndStatus(Long adContentId, ScheduleStatus status);
    
    Schedules findCurrentScheduleByAdContent(AdSolutionContent content);
    
    /**
     * 按条件分页查询排期单列表
     * 
     * @param scheduleCondition
     * @param locale
     * @return
     */
    PageWrapper<ScheduleListVO> findByCondition(ScheduleConditionVO scheduleConditionVO);
    
    ScheduleVO findScheduleAd(Long id, LocaleConstants locale);
    
    /**
     * 指定广告内容的排期单是否都已释放，即是否存在未释放的排期单
     * @param adContentId 广告内容id
     * @return true，所有排期单都已释放；false，有未释放的排期单；如果广告内容没有排期单，返回true
     */
    boolean isSchedulesAllReleased(Long adContentId);

    List<Schedules> findByAdContentIdAndStatusNot(Long adContentId, ScheduleStatus status);
    
    Schedules findCurrentSchedule(Long adContentId);

    Schedules createAndSaveSchedule(AdSolutionContent content, ScheduleStatus status);

    Schedules createAndSaveSchedule(AdSolutionContent content);
    
    public List<Schedules> findByAdContentId(Long adContentId);
}
