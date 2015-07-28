package com.baidu.gcrm.bpm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.baidu.gcrm.bpm.model.ActivityNameI18n;
import com.baidu.gcrm.common.LocaleConstants;

public interface IActivityNameI18nRepository extends JpaRepository<ActivityNameI18n, Long> {
    
	List<ActivityNameI18n> findByLocale(LocaleConstants locale);
	
	List<ActivityNameI18n> findByProcessDefIdAndLocaleAndActivityIdIn(String processDefId,
	        LocaleConstants locale, List<String> activityIds);
	
	ActivityNameI18n findByProcessDefIdAndLocaleAndActivityId(String processDefId,
            LocaleConstants locale, String activityId);
}
