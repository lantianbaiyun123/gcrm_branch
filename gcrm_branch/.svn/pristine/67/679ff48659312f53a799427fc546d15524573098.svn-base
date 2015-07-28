package com.baidu.gcrm.publish.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;

public class PublishDateHelper {
	
	public static List<PublishDate> combineOldAndNewPublish(List<PublishDate> oldDates, List<DatePeriod> newPeriods, Date approvalDate, String publishNumber) {
		List<PublishDate> newDates = new ArrayList<PublishDate>();
		
		dealWithOldPublishDates(oldDates, approvalDate, newDates, publishNumber);
		dealWithNewPublishDates(newPeriods, approvalDate, publishNumber, newDates);
		
		return newDates;
	}

	private static void dealWithOldPublishDates(List<PublishDate> oldDates, Date approvalDate, List<PublishDate> newDates, String publishNumber) {
		for (PublishDate publishDate : oldDates) {
			Date acturalEnd = publishDate.getActuralEnd();
			
			Date planStart = publishDate.getPlanStart();
			if (planStart.after(approvalDate)) {
				continue;
			}
			
			Date planEnd = publishDate.getPlanEnd();
			if (PublishPeriodStatus.ongoing.equals(publishDate.getStatus())) {
				publishDate.setStatus(PublishPeriodStatus.end);
				if (planEnd.after(approvalDate)) {
					publishDate.setActuralEnd(approvalDate);
					publishDate.setPlanEnd(approvalDate);
				} else {
					publishDate.setActuralEnd(planEnd);
				}
				newDates.add(getPublishDate(publishDate, publishNumber));
				continue;
			}
			
			if (!planEnd.before(approvalDate) && PublishPeriodStatus.not_start.equals(publishDate.getStatus()) && planStart.before(approvalDate)) {
				publishDate.setPlanEnd(approvalDate);
				newDates.add(getPublishDate(publishDate, publishNumber));
				continue;
			}
			
			String formatDate = DateUtils.getDate2String(DateUtils.YYYY_MM_DD, acturalEnd);
			if (formatDate.equals(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, approvalDate)) && planEnd.after(approvalDate)) {
				publishDate.setPlanEnd(approvalDate);
			} else if (acturalEnd != null && acturalEnd.before(approvalDate) && planEnd.after(approvalDate)) {
				publishDate.setPlanEnd(DateUtils.getNDayFromDate(approvalDate, -1));
			}
			newDates.add(getPublishDate(publishDate, publishNumber));
		}
	}

	private static PublishDate getPublishDate(PublishDate publishDate, String publishNumber) {
		PublishDate newPublishDate = new PublishDate();
		newPublishDate.setPlanStart(publishDate.getPlanStart());
		newPublishDate.setPlanEnd(publishDate.getPlanEnd());
		newPublishDate.setActuralStart(publishDate.getActuralStart());
		newPublishDate.setActuralEnd(publishDate.getActuralEnd());
		newPublishDate.setStartOperator(publishDate.getStartOperator());
		newPublishDate.setEndOperator(publishDate.getEndOperator());
		newPublishDate.setStatus(publishDate.getStatus());
		if (StringUtils.isNotBlank(publishNumber)) {
			newPublishDate.setPublishNumber(publishNumber);
		} else {
			newPublishDate.setPublishNumber(publishDate.getPublishNumber());
		}
		return newPublishDate;
	}

	private static void dealWithNewPublishDates(List<DatePeriod> newPeriods, Date approvalDate, String publishNumber,
			List<PublishDate> newDates) {
		List<Date> datesAfter = DatePeriodHelper.getDatesEqualOrAfter(newPeriods, approvalDate);
		Collections.sort(datesAfter);
		List<DatePeriod> updatedPeriods = DatePeriodHelper.combineDates(datesAfter);
		for (DatePeriod period : updatedPeriods) {
			PublishDate publishDate = new PublishDate();
			publishDate.setPublishNumber(publishNumber);
			publishDate.setPlanStart(period.getFrom());
			publishDate.setPlanEnd(period.getTo());
			publishDate.setStatus(PublishPeriodStatus.not_start);
			newDates.add(publishDate);
		}
	}
	
	public static List<PublishDate> updatePublishDateAfterAddingDates(List<PublishDate> oldDates, List<Date> dates, String publishNumber) {
		List<PublishDate> newDates = new ArrayList<PublishDate>();
		List<DatePeriod> periods = new ArrayList<DatePeriod>();
		Date latestActrualStart = null;
		Long latestStartOperator = null;
		for (PublishDate pdate : oldDates) {
			if (pdate.getActuralStart() != null && pdate.getActuralEnd() != null) {
				newDates.add(getPublishDate(pdate, StringUtils.EMPTY));
				continue;
			} 
			if (pdate.getActuralStart() != null) {
				latestActrualStart = pdate.getActuralStart();
				latestStartOperator = pdate.getStartOperator();
			} 
			periods.add(new DatePeriod(pdate.getPlanStart(), pdate.getPlanEnd()));
			
		}
		dates.addAll(DatePeriodHelper.getAllDates(periods));
		Collections.sort(dates);
		
		periods = DatePeriodHelper.combineDates(dates);
		for (DatePeriod period : periods) {
			PublishDate publishDate = new PublishDate();
			publishDate.setPlanStart(period.getFrom());
			publishDate.setPlanEnd(period.getTo());
			publishDate.setPublishNumber(publishNumber);
			if (latestActrualStart != null && !period.getFrom().after(latestActrualStart)) {
				publishDate.setActuralStart(latestActrualStart);
				publishDate.setStartOperator(latestStartOperator);
				publishDate.setStatus(PublishPeriodStatus.ongoing);
			} else {
				publishDate.setStatus(PublishPeriodStatus.not_start);
			}
			
			newDates.add(publishDate);
		}
		
		return newDates;
	}
	
	public static List<PublishDate> updatePublishDateAfterRemovingDate(List<PublishDate> oldDates, Date removedDate, String publishNumber) {
		Date currentDate = DateUtils.getCurrentDateOfZero();
		removedDate = new Date(removedDate.getTime());
		if (removedDate.before(currentDate)) {
			return oldDates;
		}
		List<PublishDate> newDates = new ArrayList<PublishDate>();
		List<DatePeriod> periods = new ArrayList<DatePeriod>();
		Date latestActrualStart = null;
		Long latestStartOperator = null;
		for (PublishDate pdate : oldDates) {
			if (pdate.getActuralStart() != null && pdate.getActuralEnd() != null) {
				newDates.add(getPublishDate(pdate, StringUtils.EMPTY));
				continue;
			} 
			if (pdate.getActuralStart() != null) {
				if (removedDate.equals(currentDate) && !removedDate.after(pdate.getPlanEnd())) {
					// pdate.setActuralEnd(new Date());
					// pdate.setEndOperator(RequestThreadLocal.getLoginUserId());
					// pdate.setStatus(PublishPeriodStatus.end);
					periods.add(new DatePeriod(currentDate, pdate.getPlanEnd()));
					PublishDate newPublishDate = new PublishDate();
					newPublishDate.setPlanStart(pdate.getPlanStart());
					newPublishDate.setPlanEnd(currentDate);
					newPublishDate.setActuralStart(pdate.getActuralStart());
					newPublishDate.setActuralEnd(new Date());
					newPublishDate.setStartOperator(pdate.getStartOperator());
					newPublishDate.setEndOperator(Constants.SYSTEM_OPERATOR);
					newPublishDate.setStatus(PublishPeriodStatus.end);
					newPublishDate.setPublishNumber(pdate.getPublishNumber());
					
					newDates.add(newPublishDate);
					continue;
				}
				latestActrualStart = pdate.getActuralStart();
				latestStartOperator = pdate.getStartOperator();
			} 
			periods.add(new DatePeriod(pdate.getPlanStart(), pdate.getPlanEnd()));
			
		}
		Set<Date> dates = DatePeriodHelper.getAllDates(periods);
		if (!dates.contains(removedDate)) {
			return oldDates;
		}
		List<Date> sortedDates = new ArrayList<Date>(dates);
		sortedDates.remove(removedDate);
		Collections.sort(sortedDates);
		
		periods = DatePeriodHelper.combineDates(sortedDates);
		for (DatePeriod period : periods) {
			PublishDate publishDate = new PublishDate();
			publishDate.setPlanStart(period.getFrom());
			publishDate.setPlanEnd(period.getTo());
			publishDate.setPublishNumber(publishNumber);
			if (latestActrualStart != null && !period.getFrom().after(latestActrualStart)) {
				publishDate.setActuralStart(latestActrualStart);
				publishDate.setStartOperator(latestStartOperator);
				publishDate.setStatus(PublishPeriodStatus.ongoing);
			} else {
				publishDate.setStatus(PublishPeriodStatus.not_start);
			}
			
			newDates.add(publishDate);
		}
		
		return newDates;
	}
}
