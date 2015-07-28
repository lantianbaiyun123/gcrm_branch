package com.baidu.gcrm.data.bean;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.baidu.gcrm.common.DateUtils;

/**
 * 一周七天每天的展现量
 * 
 * @author anhuan
 * 
 */
public class WeekImpressions {

    /**
     * 数组0-6分别表示Sunday-Saturday的展现量
     */
    private Long[] impressions = new Long[]{-1L, -1L, -1L, -1L, -1L, -1L, -1L};

    public Long[] getImpressions() {
        return impressions;
    }

    public void setImpressions(Long[] impressions) {
        this.impressions = impressions;
    }
    
    public void setImpressions(long impression) {
        Arrays.fill(impressions, impression);
    }

    public long getImpressionOfDay(int dayInWeek) {
        if (dayInWeek > Calendar.DAY_OF_WEEK || dayInWeek < 1) {
            return 0;
        }
        return impressions[dayInWeek - 1].longValue();
    }

    public long getImpressionOfDay(Date date) {
        int dayInWeek = DateUtils.getDayInWeek(date);
        return getImpressionOfDay(dayInWeek);
    }

    public void setImpressionOfDay(int dayInWeek, long impression) {
        impressions[dayInWeek - 1] = impression;
    }

    public void setImpressionOfDay(Date date, long impression) {
        int dayInWeek = DateUtils.getDayInWeek(date);
        setImpressionOfDay(dayInWeek, impression);
    }

}
