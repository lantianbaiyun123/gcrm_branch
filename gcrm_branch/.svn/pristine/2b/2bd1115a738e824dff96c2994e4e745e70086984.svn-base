package com.baidu.gcrm.ad.web.validator;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.baidu.gcrm.ad.content.web.vo.AdSolutionContentView;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;

public class OccupationValidator implements Validator {
private static Logger log = LoggerFactory.getLogger(OccupationValidator.class);
	
	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.equals(AdSolutionContentView.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target == null) {
			log.error("广告内容信息为空");
			return;
		}
		AdSolutionContentView view = (AdSolutionContentView)target;
		List<DatePeriod> periods = view.getPeriods();
		Set<Date> allDates = new HashSet<Date> ();
		if (CollectionUtils.isEmpty(periods)) {
//		    errors.reject("occupation.period.null");
		    return;
		} else {
		    for (DatePeriod datePeriod : periods) {
	            allDates.addAll(DatePeriodHelper.getDatesInPeriod(datePeriod));
	        }
		}
		
        if (errors.hasErrors()) {
            return;
		}
	}
	
}
