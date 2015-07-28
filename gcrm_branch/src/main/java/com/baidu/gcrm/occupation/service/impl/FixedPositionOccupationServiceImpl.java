package com.baidu.gcrm.occupation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.occupation.service.bean.RotationPositionCountBean;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.occupation.service.AbstractPositionOccupationService;
import com.baidu.gcrm.occupation.web.vo.DateOccupation;
import com.baidu.gcrm.occupation.web.vo.DateStatus;
import com.baidu.gcrm.occupation.web.vo.OccupationVO;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;

@Service("fixedPositionOccupationServiceImpl")
public class FixedPositionOccupationServiceImpl extends AbstractPositionOccupationService {

	@Override
	public List<Date> getReserveDate(Position position, DatePeriod period) {
		Date from = period.getFrom();
		Date to = period.getTo();
		checkInitialization(position, to);
		return occupationCustomDao.getReservedDateBetween(position.getId(), from, to);
	}
	
	@Override
	public List<Date> getConfirmOrLockDate(Position position, DatePeriod period, ScheduleStatus status) {
		Date from = period.getFrom();
		Date to = period.getTo();
		List<Date> confirmDates = new ArrayList<Date>();
		if (status.isReleased() || status.isReserved()) {
			return confirmDates;
		}
		checkInitialization(position, to);
		
		Long positionId = position.getId();
		List<String> scheduleNumbers = scheduleCustomDao.findNumbersByPositionAndStatus(positionId, status);
		if (CollectionUtils.isEmpty(scheduleNumbers)) {
			return confirmDates;
		}
		
		List<PositionOccupation> occupations = positionOccupationDao.findOccupiedByPositionAndDateBetween(positionId, from, to);
		for (PositionOccupation occupation : occupations) {
			String scheduleNumberStr = occupation.getCurScheduleNumber();
			if (StringUtils.isEmpty(scheduleNumberStr)) {
				continue;
			}
			String scheduleNumber = StringUtils.remove(scheduleNumberStr, Constants.SCHEDULE_NUMBER_SPLIT);
			if (scheduleNumbers.contains(scheduleNumber)) {
				confirmDates.add(occupation.getDate());
			}
		}
		return confirmDates;
	}
	
	@Override
	public List<RotationPositionCountBean> getRotationPositionCountByDate(Position position, DatePeriod period) {
		return new ArrayList<RotationPositionCountBean>();
	}
	
	@Override
	public OccupationVO getDateOccupation(Position position, DatePeriod period) {
		List<Date> reserveDate = getReserveDate(position, period);
		List<Date> confirmDate = getConfirmOrLockDate(position, period, ScheduleStatus.confirmed);
		List<Date> lockDate = getConfirmOrLockDate(position, period, ScheduleStatus.locked);
		Map<Date, Integer> biddingCounts = getBiddingCount(position, period);
		Set<Date> biddingDate = biddingCounts.keySet();
		
		OccupationVO dateOccupation = new OccupationVO();
		dateOccupation.setPositionId(position.getId());
		dateOccupation.setFrom(period.getFrom());
		dateOccupation.setTo(period.getTo());
		dateOccupation.setRotation(false);
		
		List<DateOccupation> occupations = new ArrayList<DateOccupation>();
		
		for (Date date : DatePeriodHelper.getDatesInPeriod(period)) {
			DateOccupation occupation = new DateOccupation();
			occupation.setDate(date);
			if (reserveDate.contains(date)) {
				occupation.setStatus(DateStatus.reserved.toString());
			} else if (confirmDate.contains(date)) {
				occupation.setStatus(DateStatus.confirmed.toString());
			} else if (lockDate.contains(date)) {
				occupation.setStatus(DateStatus.locked.toString());
			} else if (biddingDate.contains(date)) {
				occupation.setStatus(DateStatus.bidding.toString());
				occupation.setBiddingCount(biddingCounts.get(date));
			} else {
				occupation.setStatus(DateStatus.idle.toString());
			}
			if (biddingDate.contains(date)) {
				occupation.setBiddingCount(biddingCounts.get(date));
			}
			occupations.add(occupation);
		}
		dateOccupation.setOccupations(occupations);
		return dateOccupation;
	}
	
	@Override
	public int getMaxOccupyCountOfPosition(Position position) {
		return 1;
	}
}
