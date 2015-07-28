package com.baidu.gcrm.occupation.helper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.occupation.model.PositionOccupation;

public class PositionOccupationHelper {
	public Set<String> getHistorySchedules(List<PositionOccupation> occupations) {
		Set<String> scheduleNumbers = new HashSet<String>();
		for (PositionOccupation occupation : occupations) {
			String historySchedules = occupation.getHistorySchedules();
			if (StringUtils.isBlank(historySchedules)) {
				continue;
			}
			String[] scheduleArray = historySchedules.split(Constants.SCHEDULE_NUMBER_SPLIT);
			for (String schedule : scheduleArray) {
				scheduleNumbers.add(schedule);
			}
		}
		
		return scheduleNumbers;
	}
	
	public String getLastScheduleFromHistory(String historyScheduleStr, Set<String> scheduleNumbers) {
		String split = Constants.SCHEDULE_NUMBER_SPLIT;
		String scheduleNumber = StringUtils.EMPTY;
		if (StringUtils.isBlank(historyScheduleStr)) {
			return scheduleNumber;
		}
		// remove start and end ","
		historyScheduleStr = StringUtils.removeEnd(StringUtils.removeStart(historyScheduleStr, split), split);
		String[] historySchedules = historyScheduleStr.split(split);
		for (String historySchedule : historySchedules) {
			if (scheduleNumbers.contains(historySchedule)) {
				scheduleNumber = historySchedule;
				break;
			}
		}
		return scheduleNumber;
	}
	
	public String getHistorySchedulesAfterRelease(String historySchedules, String lastScheduleNumber) {
		if (StringUtils.isBlank(historySchedules)) {
			return StringUtils.EMPTY;
		}
		int index = historySchedules.indexOf(lastScheduleNumber);
		historySchedules = historySchedules.substring(index + lastScheduleNumber.length());
		if (Constants.SCHEDULE_NUMBER_SPLIT.equals(historySchedules)) {
			return StringUtils.EMPTY;
		}
		return historySchedules;
	}
	
	public String getHistorySchedulesAfterInsert(String historySchedules, String insertedSchedule) {
		String split = Constants.SCHEDULE_NUMBER_SPLIT;
		if (StringUtils.isBlank(historySchedules)) {
			historySchedules = split.concat(insertedSchedule).concat(split);
		} else {
			historySchedules = split.concat(insertedSchedule).concat(historySchedules);
		}
		
		return historySchedules;
	}
	
	public String getCurrentSchedulesAfterConfirm(String currentSchedules, String confirmedSchedule) {
		String split = Constants.SCHEDULE_NUMBER_SPLIT;
		if (StringUtils.isBlank(currentSchedules)) {
			return split.concat(confirmedSchedule).concat(split);
		} else {
			return split.concat(confirmedSchedule).concat(currentSchedules);
		}
	}
	
	public String getCurrentSchedulesAfterRelease(String currentSchedule, String releasedSchedule, String lastSchedule, int totalAmount) {
		String split = Constants.SCHEDULE_NUMBER_SPLIT;
		if (StringUtils.isNotBlank(currentSchedule) && currentSchedule.contains(releasedSchedule)) {
			if (StringUtils.isEmpty(lastSchedule)) {
				currentSchedule = currentSchedule.replace(split + releasedSchedule + split, split);
				if (split.equals(currentSchedule)) {
					return StringUtils.EMPTY;
				}
				return currentSchedule;
			}
			return currentSchedule.replace(releasedSchedule, lastSchedule);
		} else if (StringUtils.isBlank(currentSchedule)) {
			if (StringUtils.isEmpty(lastSchedule)) {
				return StringUtils.EMPTY;
			}
			return split.concat(lastSchedule).concat(split);
		} else { // !currentSchedule.contains(releasedSchedule)
			if (StringUtils.isBlank(lastSchedule) || getSoldAmount(currentSchedule) == totalAmount) {
				return currentSchedule;
			} else {
				return getCurrentSchedulesAfterConfirm(currentSchedule, lastSchedule);
			}
		}
	}
	
	public String getCurrentSchedulesAfterInsert(String currentSchedule, String insertedSchedule) {
		int soldAmount = getSoldAmount(currentSchedule);
		if (soldAmount == 1) {
			return StringUtils.EMPTY;
		}
		String split = Constants.SCHEDULE_NUMBER_SPLIT;
		return currentSchedule.replace(split.concat(insertedSchedule).concat(split), split);
	}
	
	public int getSoldAmount(String currentSchedules) {
		if (StringUtils.isBlank(currentSchedules)) {
			return 0;
		}
		return currentSchedules.split(Constants.SCHEDULE_NUMBER_SPLIT).length - 1;
	}
}
