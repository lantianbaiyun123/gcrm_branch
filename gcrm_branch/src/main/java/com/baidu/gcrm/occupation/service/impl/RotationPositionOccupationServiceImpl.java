package com.baidu.gcrm.occupation.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.model.PositionOccupation;
import com.baidu.gcrm.occupation.service.AbstractPositionOccupationService;
import com.baidu.gcrm.occupation.service.bean.RotationPositionCountBean;
import com.baidu.gcrm.occupation.web.vo.DateOccupation;
import com.baidu.gcrm.occupation.web.vo.DateStatus;
import com.baidu.gcrm.occupation.web.vo.OccupationVO;
import com.baidu.gcrm.resource.position.model.Position;
import com.baidu.gcrm.schedule.model.Schedule.ScheduleStatus;

@Service("rotationPositionOccupationServiceImpl")
public class RotationPositionOccupationServiceImpl extends AbstractPositionOccupationService {

	@Override
	public List<Date> getReserveDate(Position position, DatePeriod period) {
		return new ArrayList<Date>();
	}
	
	@Override
	public List<Date> getConfirmOrLockDate(Position position, DatePeriod period, ScheduleStatus status) {
		return new ArrayList<Date>();
	}
	
	@Override
	public List<RotationPositionCountBean> getRotationPositionCountByDate(Position position, DatePeriod period) {
		Date from = period.getFrom();
		Date to = period.getTo();
		List<RotationPositionCountBean> positionCounts = new ArrayList<RotationPositionCountBean>();
		
		checkInitialization(position, to);
		
		Long positionId = position.getId();
		Map<Long, Long> reservedCounts = occupationCustomDao.getReservedPositionCountBetween(positionId, from, to);
		List<PositionOccupation> occupations = positionOccupationDao.findOccupationByDateBetween(positionId, from, to);
		
		for (PositionOccupation occupation : occupations) {
			Long id = occupation.getId();
			Date date = occupation.getDate();
			RotationPositionCountBean countBean = new RotationPositionCountBean(positionId, date);
			int occupiedCount = getSoldAmount(occupation.getCurScheduleNumber());
			int reservedCount = getReservedCounts(reservedCounts, id);
			countBean.setOccupiedCount(occupiedCount);
			countBean.setReservedCount(reservedCount);
			positionCounts.add(countBean);
		}
		
		return positionCounts;
	}

	private int getReservedCounts(Map<Long, Long> reservedCounts, Long id) {
		Long count = reservedCounts.get(id);
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}
	
	@Override
	public OccupationVO getDateOccupation(Position position, DatePeriod period) {
		OccupationVO dateOccupation = new OccupationVO();
		dateOccupation.setPositionId(position.getId());
		dateOccupation.setFrom(period.getFrom());
		dateOccupation.setTo(period.getTo());
		dateOccupation.setRotation(true);
		dateOccupation.setTotalCount(position.getSalesAmount());
		Map<Date, Integer> biddingCounts = getBiddingCount(position, period);
		Set<Date> biddingDate = biddingCounts.keySet();
		List<DateOccupation> occupations = new ArrayList<DateOccupation>();
		
		List<RotationPositionCountBean> counts = getRotationPositionCountByDate(position, period);
		for (RotationPositionCountBean count : counts) {
			DateOccupation occupation = new DateOccupation();
			Date date = count.getDate();
			occupation.setDate(date);
			int busyCount = count.getBusyCount();
			occupation.setBusyCount(busyCount);
			if (busyCount == 0) {
				if (biddingDate.contains(date)) {
					occupation.setStatus(DateStatus.bidding.toString());
					occupation.setBiddingCount(biddingCounts.get(date));
				} else {
					occupation.setStatus(DateStatus.idle.toString());
				}
			} else {
				occupation.setStatus(DateStatus.busy.toString());
				if (biddingDate.contains(date)) {
					occupation.setBiddingCount(biddingCounts.get(date));
				}
			}
			
			occupations.add(occupation);
		}
		dateOccupation.setOccupations(occupations);
		return dateOccupation;
	}
	
	@Override
	public int getMaxOccupyCountOfPosition(Position position) {
		DatePeriod period = new DatePeriod();
		period.setFrom(DateUtils.getCurrentDateOfZero());
		Date to = occupationCustomDao.getFarthestDateByPosition(position.getId());
		period.setTo(to);
		List<RotationPositionCountBean> countBeans = getRotationPositionCountByDate(position, period);
		int max = 0;
		for (RotationPositionCountBean countBean : countBeans) {
			int busyCount = countBean.getBusyCount();
			if (busyCount > max) {
				max = busyCount;
			}
		}
		return max;
	}
}
 