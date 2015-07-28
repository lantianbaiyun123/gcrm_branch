package com.baidu.gcrm.occupation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.baidu.gcrm.amp.dao.IPositionDao;
import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.common.log.LoggerHelper;
import com.baidu.gcrm.occupation.dao.IADPositionDateRelationRepository;
import com.baidu.gcrm.occupation.dao.IADPositionDateRelationRepositoryCustom;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepository;
import com.baidu.gcrm.occupation.dao.IPositionOccupationRepositoryCustom;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation.helper.PositionOccupationHelper;
import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.occupation.model.PositionOccupation.OccupationStatus;
import com.baidu.gcrm.occupation.validator.PositionOccupationValidator;
import com.baidu.gcrm.publish.service.IPublishService;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.resource.position.model.Position.PositionStatus;
import com.baidu.gcrm.resource.position.model.Position.PositionType;
import com.baidu.gcrm.schedule.dao.IScheduleRepository;
import com.baidu.gcrm.schedule.dao.IScheduleRepositoryCustom;
import com.baidu.gcrm.schedule.model.Schedule;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;

public abstract class AbstractPositionOccupationService extends PositionOccupationHelper implements IPositionOccupationService {
	@Autowired
	protected IPositionOccupationRepository positionOccupationDao;
	@Autowired
	protected IPositionOccupationRepositoryCustom occupationCustomDao;
	@Autowired
	protected IScheduleRepository scheduleDao;
	@Autowired
	protected IScheduleRepositoryCustom scheduleCustomDao;
	@Autowired
	protected IADPositionDateRelationRepository relationDao;
	@Autowired
	protected IADPositionDateRelationRepositoryCustom relationCustomDao;
	@Autowired
	protected IPositionDao positionDao;
	@Autowired
	protected IPublishService publishService;
	
	@Value("#{appproperties['occupation.init.count']}")
	private int initalDaysCount;

	@Override
	public void initPositionOccupationTimer() {
		List<Position> positions = positionDao.findByStatusAndType(PositionStatus.enable, PositionType.position);
		if (CollectionUtils.isEmpty(positions)) {
			return;
		}
		LoggerHelper.info(getClass(), "开始更新位置投放时间定时任务。");
		for (Position position : positions) {
			initPositionOccupation(position);
		}
		LoggerHelper.info(getClass(), "成功更新位置投放时间{}个。", positions.size());
	}
	
	@Override
	public void initPositionOccupation(Position position) {
		if (!PositionType.position.equals(position.getType())) {
			return;
		}
		
		Date farthestDate = occupationCustomDao.getFarthestDateByPosition(position.getId());
		
		String currentFormatDate = DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD);
		Date currentDate = DateUtils.getString2Date(currentFormatDate);
		Date from = (farthestDate == null) ? currentDate : DateUtils.getNDayFromDate(farthestDate, 1);
		int offDays = DateUtils.getOffDays(currentDate.getTime(), from.getTime());
		int days = initalDaysCount - offDays;
		LoggerHelper.info(getClass(), "从{}开始初始化{}天，位置id：{}", from, days, position.getId());
		// initial one year from current date in db
		initOccupationByYear(position, from, days);
		
	}

	private void initOccupationByYear(Position position, Date from, int n) {
		List<PositionOccupation> existedOccupations = positionOccupationDao.findByPositionId(position.getId());
		
		List<PositionOccupation> occupations = new ArrayList<PositionOccupation>();
		List<Date> dates = DateUtils.getNDatesFromDate(from, n);
		for (Date date : dates) {
			PositionOccupation occupation = generatePositionOccupation(position, date);
			if (!existedOccupations.contains(occupation)) {
				occupations.add(occupation);
				existedOccupations.add(occupation);
			}
		}
		positionOccupationDao.save(occupations);
	}
	
	private PositionOccupation generatePositionOccupation(Position position, Date date) {
		PositionOccupation occupation = new PositionOccupation();
		occupation.setStatus(OccupationStatus.ENABLED);
		occupation.setDate(date);
		occupation.setPositionId(position.getId());
		int totalAmount = position.getSalesAmount() == null ? 1 : position.getSalesAmount();
		occupation.setTotalAmount(totalAmount);
		occupation.setSoldAmount(0);
		return occupation;
	}

	
	@Override
	public void disablePositionOccupation(Position position) {
		Long positionId = position.getId();
		Date maxIdleOccupationDate = relationCustomDao.getMaxIdleOccupationDate(positionId);
		Date maxOccupiedOccupationDate = relationCustomDao.getMaxOccupiedOccupationDate(positionId);
		if (maxIdleOccupationDate == null && maxOccupiedOccupationDate == null) {
			return;
		} else if (maxIdleOccupationDate == null || maxIdleOccupationDate.before(maxOccupiedOccupationDate)) {
			// 所有的投放时间都不空闲或者空闲的投放时间在已占用的投放时间之前，此时不能禁用该位置
			throw new CRMRuntimeException("positionOccupatoin.disable.forbidden");
		} else if (maxOccupiedOccupationDate == null) {
			// 所有投放时间都空闲，直接禁用所有空闲时间
			positionOccupationDao.updateStatus(OccupationStatus.DISABLED, new Date(), positionId);
		} else if (maxIdleOccupationDate.after(maxOccupiedOccupationDate)) {
			// 从最大占用日期的后一天开始禁用
			positionOccupationDao.updateStatus(OccupationStatus.DISABLED, DateUtils.getNDayFromDate(maxOccupiedOccupationDate, 1), positionId);
		}
		
	}
	
	@Override
	public void enablePositionOccupation(Position position) {
		// enable
		Long positionId = position.getId();
		positionOccupationDao.updateStatus(OccupationStatus.ENABLED, new Date(), positionId);
		// init
		initPositionOccupation(position);
	}
	
	@Override
	public void confirmOccupation(List<Long> occupationIds, String confirmedSchedule) {
		List<PositionOccupation> occupations = (List<PositionOccupation>)positionOccupationDao.findAll(occupationIds);
		List<String> conditions = new ArrayList<String>();
		for (PositionOccupation occupation : occupations) {
			String curScheduleNumbers = occupation.getCurScheduleNumber();
			conditions.add(curScheduleNumbers);
			PositionOccupationValidator.validateConfirmAvailable(occupation);
			curScheduleNumbers = getCurrentSchedulesAfterConfirm(curScheduleNumbers, confirmedSchedule.toString());
			
			occupation.setCurScheduleNumber(curScheduleNumbers);
			occupation.setSoldAmount(getSoldAmount(curScheduleNumbers));
		}
		occupationCustomDao.batchUpdateAfterConfirm(occupations, conditions);
	}
	

	@Override
	public void insertOccupation(Long occupationId, String insertedSchedule) {
		PositionOccupation occupation = positionOccupationDao.findOne(occupationId);
		String curScheduleNumber = occupation.getCurScheduleNumber();
		PositionOccupationValidator.validateIfCanInsert(curScheduleNumber, insertedSchedule);
		
		Schedule schedule = scheduleDao.findByNumber(insertedSchedule);
		Long adContentId = schedule.getAdContentId();
		relationDao.deleteByAdContentIdAndPositionOccId(adContentId, occupation.getId());
		
		occupation.setCurScheduleNumber(getCurrentSchedulesAfterInsert(curScheduleNumber, insertedSchedule));
		String historySchedules = occupation.getHistorySchedules();
		occupation.setHistorySchedules(getHistorySchedulesAfterInsert(historySchedules, insertedSchedule));
		occupation.setSoldAmount(getSoldAmount(occupation.getCurScheduleNumber()));
		
		updateSchedule(schedule,occupation);
		positionOccupationDao.save(occupation);
		
		// do this only when inserted schedule is locked
		if (schedule.getStatus().isLocked()) {
			publishService.updatePublishAfterScheduleInserted(insertedSchedule, occupation.getDate());
		}
	}
	
	private void updateSchedule(Schedule schedule,PositionOccupation occupation){
		Long occupationId = occupation.getId();
		String occup = schedule.getOccupyPeriod();
		if(occup.equals(occupationId.toString())){
			schedule.setOccupyPeriod(occup.replace(occupationId.toString(),""));
		}else if(occup.startsWith(occupationId.toString())){
			schedule.setOccupyPeriod(occup.replace(
					occupationId+Constants.SCHEDULE_NUMBER_SPLIT,""));
		}else{
			schedule.setOccupyPeriod(occup.replace(
					Constants.SCHEDULE_NUMBER_SPLIT+occupationId,""));
		}
		String period = schedule.getPeriodDescription();
		List<DatePeriod> periods = DatePeriodHelper.getDatePeriods(period);
		periods = DatePeriodHelper.removeDatesFromPeriods(periods,Arrays.asList(occupation.getDate()));
		period = DatePeriodHelper.getDatePeriodString(periods);
		schedule.setPeriodDescription(period);
		String insert = schedule.getInsertPeriod();
		if (insert != null) {
			if(insert.indexOf(occupationId.toString())>-1){
				if(insert.equals(occupationId.toString())){
					schedule.setInsertPeriod(insert.replace(occupationId.toString(),""));
				}else if(insert.startsWith(occupationId.toString())){
					schedule.setInsertPeriod(insert.replace(
							occupationId+Constants.SCHEDULE_NUMBER_SPLIT,""));
				}else{
					schedule.setInsertPeriod(insert.replace(
							Constants.SCHEDULE_NUMBER_SPLIT+occupationId,""));
				}
			}
			String insertPeriod = schedule.getInsertPeriodDescription();
				String insDate = DateUtils.getDate2String(DateUtils.YYYY_MM_DD, occupation.getDate());
			if(insertPeriod.indexOf(insDate)>-1){
				if(insertPeriod.equals(insDate)){
					schedule.setInsertPeriodDescription(insertPeriod.replace(insDate,""));
				}else if(insertPeriod.startsWith(insDate)){
					schedule.setInsertPeriodDescription(insertPeriod.replace(
							insDate+Constants.INSERT_PERIOD_SPLIT ,""));
				}else{
					schedule.setInsertPeriodDescription(insertPeriod.replace(
							Constants.INSERT_PERIOD_SPLIT +insDate,""));
				}
			}
		}
		scheduleDao.save(schedule);
	}
	
	@Override
	public List<Date> getInsertDate(Position position, DatePeriod period) {
		Date from = period.getFrom();
		Date to = period.getTo();
		checkInitialization(position, to);
		List<PositionOccupation> occupations = positionOccupationDao.findOccupationByDateBetween(position.getId(), from, to);
		List<Date> insertDate = new ArrayList<Date>();
		for (PositionOccupation occupation : occupations) {
			if (occupation.getSoldAmount() >= occupation.getTotalAmount()) {
				insertDate.add(occupation.getDate());
			}
		}
		return insertDate;
	}
	
	@Override
	public List<Date> getInsertDate(Position position, List<DatePeriod> periods) {
		List<Date> dates = new ArrayList<Date>();
		if (CollectionUtils.isEmpty(periods)) {
			return dates;
		}
		for (DatePeriod datePeriod : periods) {
			dates.addAll(DatePeriodHelper.getDatesInPeriod(datePeriod));
		}
		Collections.sort(dates);
		checkInitialization(position, dates.get(dates.size() - 1));
		
		return getValidInsertDates(position, dates);
	}
	
	@Override
    public List<Date> getValidInsertDate(Position position, List<Date> dates) {
		if (CollectionUtils.isEmpty(dates)) {
			return dates;
		}
	    Collections.sort(dates);
        checkInitialization(position, dates.get(dates.size() - 1));
        return getValidInsertDates(position, dates);
    }
	
	private List<Date> getValidInsertDates(Position position,Collection<Date> dates) {
	    List<PositionOccupation> occupations = positionOccupationDao.findOccupationByDateIn(position.getId(), dates);
        List<Date> insertDates = new ArrayList<Date>();
        if (CollectionUtils.isEmpty(occupations)) {
            return insertDates;
        }
        for (PositionOccupation occupation : occupations) {
            if (occupation.getSoldAmount() >= occupation.getTotalAmount()) {
                insertDates.add(occupation.getDate());
            }
        }
        return insertDates;
	}
	
	@Override
	public Map<Date, Integer> getBiddingCount(Position position, DatePeriod period) {
		Date from = period.getFrom();
		Date to = period.getTo();
		Map<Date, Integer> biddingCounts = new HashMap<Date, Integer>();
		checkInitialization(position, to);
		
		Long positionId = position.getId();
		Map<Long, Date> dateMap = occupationCustomDao.getDateMapByDateBetween(positionId, from, to);
		Set<Long> positionOccIds = dateMap.keySet();
		Map<Long, Long> allPositionOccCount = relationCustomDao.getCountGroupByPositionOccId(positionOccIds);
		Map<Long, Long> busyPositionOccCount = relationCustomDao.getBusyCountGroupByPositionOccId(positionOccIds);
		
		for (Long occId : allPositionOccCount.keySet()) {
			int allCount = allPositionOccCount.get(occId).intValue();
			int busyCount = busyPositionOccCount.get(occId) == null ? 0 : busyPositionOccCount.get(occId).intValue();
			int biddingCount = allCount - busyCount;
			if (biddingCount != 0) {
				biddingCounts.put(dateMap.get(occId), biddingCount);
			}
		}
		return biddingCounts;
	}

	@Override
	public void checkInitialization(Position position, Date date) {
		Date farthestDate = occupationCustomDao.getFarthestDateByPosition(position.getId());
		if (farthestDate != null && !farthestDate.before(date)) {
			return;
		}
		throw new CRMRuntimeException("occupation.error.100");
	}
	
	@Override
	public List<Date> findUpdateContentInertDates(Long positionId, List<DatePeriod> periods,Long oldContentId){
		List<ScheduleStatus> status = new ArrayList<ScheduleStatus>();
		status.add(ScheduleStatus.confirmed);
		status.add(ScheduleStatus.locked);
		List<Schedule> schedules = scheduleDao.findByAdContentIdAndStatusIn(oldContentId,status);
		if(schedules!=null && schedules.size()>0){
			List<Date> dates = new ArrayList<Date>();
			if (CollectionUtils.isEmpty(periods)) {
				return dates;
			}
			for (DatePeriod datePeriod : periods) {
				dates.addAll(DatePeriodHelper.getDatesInPeriod(datePeriod));
			}
			List<PositionOccupation> occupations = positionOccupationDao.findUpdateContentInertDates(positionId, dates, schedules.get(0).getNumber());
			List<Date> insertDates = new ArrayList<Date>();
	        if (CollectionUtils.isEmpty(occupations)) {
	            return insertDates;
	        }
	        for (PositionOccupation occupation : occupations) {
	            if (occupation.getSoldAmount() >= occupation.getTotalAmount()) {
	                insertDates.add(occupation.getDate());
	            }
	        }
			return insertDates;
		}
		return null;
	}
	
	@Override
	public PositionOccupation findById(Long id){
		return positionOccupationDao.findOne(id);
	}
}
