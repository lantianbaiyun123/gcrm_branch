package com.baidu.gcrm.occupation.web.vo;

import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.Constants;
import com.baidu.gcrm.common.exception.CRMRuntimeException;
import com.baidu.gcrm.occupation.model.PositionOccupation;

public class PositionOccupationValidator {
	
	public static void validateIfCanInsert(String currentSchedule, String insertedSchedule) {
		if (!currentSchedule.contains(insertedSchedule)) {
			throw new CRMRuntimeException("occupation.error.102");
		}
	}

	public static void validateConfirmAvailable(PositionOccupation occupation) {
		String curScheduleNumber = occupation.getCurScheduleNumber();
		if (StringUtils.isBlank(curScheduleNumber)) {
			return;
		}
		int soldAmount = curScheduleNumber.split(Constants.SCHEDULE_NUMBER_SPLIT).length - 1;
		if (soldAmount == occupation.getTotalAmount()) {
			throw new CRMRuntimeException("occupation.error.101");
		}
	}
}
