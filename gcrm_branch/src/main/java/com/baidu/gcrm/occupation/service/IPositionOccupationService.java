package com.baidu.gcrm.occupation.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.occupation.service.bean.RotationPositionCountBean;
import com.baidu.gcrm.occupation.web.vo.OccupationVO;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;

public interface IPositionOccupationService {
	
	/**
	 * 每天定时初始化所有position的投放时间，保证投放时间最大范围是当前日期开始一年后
	 */
	void initPositionOccupationTimer();
	
	/**
	 * 根据职位初始化该职位的投放时间，时间从已初始化的最新投放时间到当前日期开始的一年后
	 * eg. 当前日期是2014-01-01，某个职位已经初始化到2014-05-31，则执行该方法后投放时间从2014-06-01初始化到2014-12-31
	 * @param position
	 */
	void initPositionOccupation(Position position);
	
	
	/**
	 * 禁用某个位置后，更新投放时间的状态，从最大的已占用的投放日期后禁用没有被预订、确认、锁定的投放时间
	 * 即已占用的投放日期不可禁用
	 * @param position
	 */
	void disablePositionOccupation(Position position);
	
	/**
	 * 启用某个位置后，更新投放时间的状态，启用已有的投放时间，并初始化投放时间到当前日期的一年后
	 * @param position
	 */
	void enablePositionOccupation(Position position);
	
	/**
	 * 广告内容的排期单的状态变为确认后，需要更新排期单编号到对应的投放记录
	 * @param occupationIds 投放id
	 * @param scheduleNumber	排期单编号
	 */
	void confirmOccupation(List<Long> occupationIds, String scheduleNumber);
	
	
	/**
	 * 插单指定投放编号日期，对应的当前排期单进行删除，放入历史关联排期单；
	 * 由于插单客户此时处于预定状态，所以当前排期单没有更新为插单客户的排期单编号
	 * @param scheduleNumber   被插单排期单编号
	 * @param occupationNumber 投放id
	 */
	void insertOccupation(Long occupationId, String scheduleNumber);
	
	/**
	 * 根据投放的开始日期和结束日期计算指定位置（包括轮播、非轮播）的需要插单的日期
	 * @param position 广告位置
	 * @param period 开始和结束日期
	 */
	List<Date> getInsertDate(Position position, DatePeriod period);
	
	/**
	 * 根据多个投放时间段计算指定位置（包括轮播、非轮播）的需要插单的日期
	 * @param position 广告位置
	 * @param periods 投放时间段列表
	 */
	List<Date> getInsertDate(Position position, List<DatePeriod> periods);
	
	/**
	 * 返回所有有效插单时间
	 * @param position
	 * @param dates
	 * @return
	 */
	List<Date> getValidInsertDate(Position position, List<Date> dates);
	
	/**
	 * 根据投放的开始日期和结束日期计算指定位置的预定日期
	 * @param position 广告位置
	 * @param period 开始和结束日期
	 * @return 已被预订的日期 列表
	 */
	List<Date> getReserveDate(Position position, DatePeriod period);
	
	/**
	 * 根据投放的开始日期和结束日期计算指定位置的确认或锁定日期
	 * @param position 广告位置
	 * @param period 开始和结束日期
	 * @param status 排期单状态，确认或锁定
	 * @return 已被确认的日期 列表
	 */
	List<Date> getConfirmOrLockDate(Position position, DatePeriod period, ScheduleStatus status);
	
	/**
	 * 根据投放的开始日期和结束日期计算指定位置的竞价广告方案数
	 * @param position 广告位置
	 * @param period 开始和结束日期
	 * @return 日期及对应的竞价广告方案数，广告方案还未生成排期单
	 */
	Map<Date, Integer> getBiddingCount(Position position, DatePeriod period);

	void checkInitialization(Position position, Date date);
	
	/**
	 * 根据投放的开始日期和结束日期计算指定<b>“轮播”</b>位置的已确认位置数（已确认位置为排期单状态为预定、确认、锁定三个状态）
	 * @param position
	 * @param period 开始和结束日期
	 * @return 日期及对应的已确认位置数
	 */
	List<RotationPositionCountBean> getRotationPositionCountByDate(Position position, DatePeriod period);
	
	
	OccupationVO getDateOccupation(Position position, DatePeriod period);
	
	
	PositionOccupation findById(Long id);
	
	List<Date> findUpdateContentInertDates(Long positionId, List<DatePeriod> periods,Long oldContentId);
	
	int getMaxOccupyCountOfPosition(Position position);
}
