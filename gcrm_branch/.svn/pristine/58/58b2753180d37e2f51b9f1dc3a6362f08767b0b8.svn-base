package com.baidu.gcrm.occupation.helper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.common.PatternUtil;

public class DatePeriodHelper {

    public static List<DatePeriod> combineAndGetDatePeriods(List<DatePeriod> datePeriods) {
        Set<Date> dates = getAllDates(datePeriods);
        if (CollectionUtils.isEmpty(dates)) {
            return new ArrayList<DatePeriod>();
        }

        List<Date> sortedDates = new ArrayList<Date>(dates);
        Collections.sort(sortedDates);

        return combineDates(sortedDates);
    }

    public static Set<Date> getAllDates(List<DatePeriod> datePeriods) {
        Set<Date> dates = new HashSet<Date>();
        if (null == datePeriods) {
            return dates;
        }
        for (DatePeriod datePeriod : datePeriods) {
            List<Date> datesBetweenPeriod = getDatesInPeriod(datePeriod);
            dates.addAll(datesBetweenPeriod);
        }
        return dates;
    }

    /**
     * 根据字符串返回时间段
     * 
     * @param periodStr，格式为2014-01-01,2014-01-10;2014-02-01;2014-03-01,2014-03-10;
     * @return
     */
    public static Set<Date> getAllDates(String periodStr) {
        return getAllDates(getDatePeriods(periodStr));
    }

    public static List<DatePeriod> combineDates(List<Date> sortedDates) {
        List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
        if (CollectionUtils.isEmpty(sortedDates)) {
            return datePeriods;
        }
        Set<Date> noDuplicateDates = new HashSet<Date>(sortedDates);
        sortedDates.clear();
        sortedDates.addAll(noDuplicateDates);
        Collections.sort(sortedDates);
        Date from = sortedDates.get(0);
        Date previous = from;
        for (int i = 1; i < sortedDates.size(); i++) {
            Date date = sortedDates.get(i);
            if (!isAdjacentNextDate(previous, date)) {
                DatePeriod period = new DatePeriod(from, previous);
                datePeriods.add(period);
                from = date;
            }
            previous = date;
        }
        // the last period
        datePeriods.add(new DatePeriod(from, previous));
        return datePeriods;
    }

    public static boolean isAdjacentNextDate(Date previous, Date date) {
        return DateUtils.getNDayFromDate(date, -1).equals(previous);
    }

    public static int getTotalDays(List<DatePeriod> datePeriods) {
        int totalDays = 0;
        for (DatePeriod datePeriod : datePeriods) {
            List<Date> datesBetweenPeriod = getDatesInPeriod(datePeriod);
            totalDays += datesBetweenPeriod.size();
        }
        return totalDays;
    }

    public static List<Date> getDatesInPeriod(DatePeriod period) {
        List<Date> dates = new ArrayList<Date>();
        SimpleDateFormat format = new SimpleDateFormat(DateUtils.YYYY_MM_DD);
        Date from = null == period.getFrom() ? null : DateUtils.getString2Date(format.format(period.getFrom()));
        Date to = null == period.getTo() ? null : DateUtils.getString2Date(format.format(period.getTo()));
        if (from == null && to == null) { // 起始和截止日期都为空
            return dates;
        } else if (from == null || to == null) { // 起始和截止日期有一个空
            dates.add(null == from ? to : from);
        }
        // 起始和截止日期全都不为空
        while (null != from && null != to && !from.after(to)) {
            dates.add(from);
            from = DateUtils.getNDayFromDate(from, 1);
        }
        return dates;
    }

    @SuppressWarnings("unchecked")
    public static List<DatePeriod> removeDatesFromPeriods(List<DatePeriod> periods, List<Date> removedDates) {
        if (CollectionUtils.isEmpty(removedDates)) {
            return periods;
        }
        Set<Date> dates = getAllDates(periods);
        if (CollectionUtils.isEmpty(dates)) {
            return new ArrayList<DatePeriod>();
        }
        List<Date> sortedDates = (List<Date>) ListUtils.removeAll(dates, removedDates);
        Collections.sort(sortedDates);

        return combineDates(sortedDates);
    }

    /**
     * 根据时间段返回字符串，格式为2014-01-01,2014-01-10;2014-02-01;2014-03-01,2014-03-10;
     * 
     * @param periods
     * @return
     */
    public static String getDatePeriodString(List<DatePeriod> periods) {
        StringBuilder builder = new StringBuilder();
        for (DatePeriod period : periods) {
            Date from = period.getFrom();
            Date to = period.getTo();
            if (null != from) {
                builder.append(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, from));
            }
            if (null != to) {
                if (null != from && !to.equals(from)) {
                    builder.append(',');
                }
                if (!to.equals(from)) {
                    builder.append(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, to));
                }
            }
            if (null != from || null != to) {
                builder.append(';');
            }
        }
        return builder.toString();
    }
    
    public static String getDatePeriodString(List<DatePeriod> periods,
            String dateRangeFlag, String rangeSplitFlag) {
        StringBuilder builder = new StringBuilder();
        for (DatePeriod period : periods) {
            Date from = period.getFrom();
            Date to = period.getTo();
            if (null != from) {
                builder.append(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, from));
            }
            if (null != to) {
                if (null != from && !to.equals(from)) {
                    builder.append(dateRangeFlag);
                }
                if (!to.equals(from)) {
                    builder.append(DateUtils.getDate2String(DateUtils.YYYY_MM_DD, to));
                }
            }
            if (null != from || null != to) {
                builder.append(rangeSplitFlag);
            }
        }
        return builder.toString();
    }

    public static String getDatePeriodStr(List<Date> dates) {
        Collections.sort(dates);
        return getDatePeriodString(combineDates(dates));
    }
    
    public static String getDatePeriodStr(List<Date> dates,
            String dateRangeFlag, String rangeSplitFlag) {
        Collections.sort(dates);
        return getDatePeriodString(combineDates(dates), dateRangeFlag, rangeSplitFlag);
    }

    /**
     * 根据字符串返回时间段
     * 
     * @param periodStr，格式为2014-01-01,2014-01-10;2014-02-01;2014-03-01,2014-03-10;
     * @return
     */
    public static List<DatePeriod> getDatePeriods(String periodStr) {
        List<DatePeriod> periods = new ArrayList<DatePeriod>();
        if (StringUtils.isEmpty(periodStr)) {
            return periods;
        }
        String[] periodSplits = periodStr.split(";");
        for (String split : periodSplits) {
            if (StringUtils.isEmpty(split)) {
                continue;
            }
            String[] dates = split.split(",");
            if (dates.length == 1) {
                Date from = DateUtils.getString2Date(dates[0]);
                periods.add(new DatePeriod(from, from));
            } else {
                Date from = DateUtils.getString2Date(dates[0]);
                Date to = DateUtils.getString2Date(dates[1]);
                periods.add(new DatePeriod(from, to));
            }
        }
        return periods;
    }

    public static List<Date> getScheduleDateInserts(String scheduleInsertStr) {
        List<DatePeriod> periods = getDatePeriods(scheduleInsertStr);
        return new ArrayList<Date>(getAllDates(periods));
    }

    public static List<Date> getDateInserts(String insertStr) {
        if (PatternUtil.isBlank(insertStr)) {
            return null;
        }
        List<Date> insertDates = new ArrayList<Date>();
        String[] insertSplits = insertStr.split(";");
        for (String split : insertSplits) {
            Date date = DateUtils.getString2Date(split);
            insertDates.add(date);
        }

        return insertDates;
    }

    @SuppressWarnings("unchecked")
    public static List<Date> getDatesEqualOrAfter(List<DatePeriod> periods, Date date) {
        List<Date> dates = new ArrayList<Date>();
        for (DatePeriod period : periods) {
            dates.addAll(getDatesInPeriod(period));
        }
        List<Date> overdueDates = new ArrayList<Date>();
        for (Date d : dates) {
            if (!d.before(date)) {
                break;
            }
            overdueDates.add(d);
        }

        return ListUtils.removeAll(dates, overdueDates);
    }

    public static List<DatePeriod> getDatePeriodStringEqualOrBefore(List<DatePeriod> periods, Date date) {
        List<DatePeriod> result = new ArrayList<DatePeriod>();
        for (DatePeriod period : periods) {
            if (date.before(period.getFrom())) {
                continue;
            } else if (date.equals(period.getFrom())) {
                period.setTo(period.getFrom());
                result.add(period);
            } else if (date.before(period.getTo())) {
                period.setTo(date);
                result.add(period);
            } else {
                result.add(period);
            }

        }
        return result;

    }
    
    @SuppressWarnings("unchecked")
    public static Set<Date> getOverlappingDates(String periodStr1, String periodStr2) {
        Set<Date> oldDates = DatePeriodHelper.getAllDates(periodStr1);
        Set<Date> newDates = DatePeriodHelper.getAllDates(periodStr2);
        return (Set<Date>) CollectionUtils.intersection(oldDates, newDates);
    }
    
    public static DatePeriod getMaxRange(List<DatePeriod> periods) {
        DatePeriod maxRange = new DatePeriod();
        for (DatePeriod datePeriod : periods) {
            Date from = datePeriod.getFrom();
            Date minFrom = maxRange.getFrom();
            Date to = datePeriod.getTo();
            Date maxTo = maxRange.getTo();
            if (minFrom == null || from.before(minFrom)) {
                maxRange.setFrom(from);
            }
            if (maxTo == null || to.after(maxTo)) {
                maxRange.setTo(to);
            }
        }
        return maxRange;
    }
}
