package com.baidu.gcrm.occupation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.occupation.helper.DatePeriodHelper;

public class DatePeriodTest {

	@Test
	public void getDatesInPeriodTest1() {
		Date from = DateUtils.getString2Date("2000-02-28");
		Date to = DateUtils.getString2Date("2000-03-01");
		DatePeriod period = new DatePeriod(from, to);
		List<Date> dates = DatePeriodHelper.getDatesInPeriod(period);
		Assert.assertEquals(3, dates.size());
		Assert.assertEquals(DateUtils.getString2Date("2000-02-29"), dates.get(1));
	}

	@Test
	public void getDatesInPeriodTest2() {
		Date from = DateUtils.getString2Date("2000-02-28");
		Date to = DateUtils.getString2Date("2000-02-28");
		DatePeriod period = new DatePeriod(from, to);
		List<Date> dates = DatePeriodHelper.getDatesInPeriod(period);
		Assert.assertEquals(1, dates.size());
		Assert.assertEquals(DateUtils.getString2Date("2000-02-28"), dates.get(0));
	}
	
	@Test
	public void getDatesInPeriodTest3() {
		Date from = DateUtils.getString2Date("2000-03-28");
		Date to = DateUtils.getString2Date("2000-02-28");
		DatePeriod period = new DatePeriod(from, to);
		List<Date> dates = DatePeriodHelper.getDatesInPeriod(period);
		Assert.assertEquals(0, dates.size());
	}
	
	@Test
	public void isAdjacentDateTest1() {
		Date previous = DateUtils.getString2Date("2000-02-28");
		Date date = DateUtils.getString2Date("2000-02-29");
		Assert.assertTrue(DatePeriodHelper.isAdjacentNextDate(previous, date));
	}
	
	@Test
	public void isAdjacentDateTest2() {
		Date previous = DateUtils.getString2Date("2001-02-28");
		Date date = DateUtils.getString2Date("2001-02-28");
		Assert.assertFalse(DatePeriodHelper.isAdjacentNextDate(previous, date));
	}
	
	@Test
	public void isAdjacentDateTest3() {
		Date previous = DateUtils.getString2Date("2000-02-29");
		Date date = DateUtils.getString2Date("2000-02-28");
		Assert.assertFalse(DatePeriodHelper.isAdjacentNextDate(previous, date));
	}
	
	@Test
	public void isAdjacentDateTest4() {
		Date previous = DateUtils.getString2Date("2000-02-28");
		Date date = DateUtils.getString2Date("2000-03-01");
		Assert.assertFalse(DatePeriodHelper.isAdjacentNextDate(previous, date));
	}
	
	@Test
	public void getAllDatesTest1() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod1());
		datePeriods.add(getPeriod2());
		datePeriods.add(getPeriod3());
		Set<Date> dates = DatePeriodHelper.getAllDates(datePeriods);
		Assert.assertEquals(9, dates.size());
		Assert.assertFalse(dates.contains(DateUtils.getString2Date("2000-03-04")));
		Assert.assertTrue(dates.contains(DateUtils.getString2Date("2000-02-29")));
	}
	
	@Test
	public void getAllDatesTest2() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod2());
		datePeriods.add(getPeriod3());
		datePeriods.add(getPeriod4());
		Set<Date> dates = DatePeriodHelper.getAllDates(datePeriods);
		Assert.assertEquals(10, dates.size());
		Assert.assertTrue(dates.contains(DateUtils.getString2Date("2000-03-13")));
	}
	
	@Test
	public void getAllDatesTest3() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod4());
		datePeriods.add(getPeriod5());
		Set<Date> dates = DatePeriodHelper.getAllDates(datePeriods);
		Assert.assertEquals(7, dates.size());
		Assert.assertTrue(dates.contains(DateUtils.getString2Date("2000-03-07")));
	}
	
	@Test
	public void combineAndGetDatePeriodsTest1() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod1());
		datePeriods.add(getPeriod2());
		datePeriods.add(getPeriod3());
		List<DatePeriod> periods = DatePeriodHelper.combineAndGetDatePeriods(datePeriods);
		Assert.assertEquals(2, periods.size());
		DatePeriod period1 = periods.get(0);
		Assert.assertEquals(DateUtils.getString2Date("2000-02-28"), period1.getFrom());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-03"), period1.getTo());
		
		DatePeriod period2 = periods.get(1);
		Assert.assertEquals(DateUtils.getString2Date("2000-03-05"), period2.getFrom());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-08"), period2.getTo());
	}
	
	@Test
	public void combineAndGetDatePeriodsTest2() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod2());
		datePeriods.add(getPeriod3());
		datePeriods.add(getPeriod4());
		List<DatePeriod> periods = DatePeriodHelper.combineAndGetDatePeriods(datePeriods);
		Assert.assertEquals(2, periods.size());
		DatePeriod period1 = periods.get(0);
		Assert.assertEquals(DateUtils.getString2Date("2000-03-03"), period1.getFrom());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-03"), period1.getTo());
		
		DatePeriod period2 = periods.get(1);
		Assert.assertEquals(DateUtils.getString2Date("2000-03-05"), period2.getFrom());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-13"), period2.getTo());
	}
	
	@Test
	public void combineAndGetDatePeriodsTest3() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod4());
		datePeriods.add(getPeriod5());
		List<DatePeriod> periods = DatePeriodHelper.combineAndGetDatePeriods(datePeriods);
		Assert.assertEquals(1, periods.size());
		DatePeriod period1 = periods.get(0);
		Assert.assertEquals(DateUtils.getString2Date("2000-03-07"), period1.getFrom());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-13"), period1.getTo());
	}
	
	@Test
	public void combineAndGetDatePeriodsTest4() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod2());
		List<DatePeriod> periods = DatePeriodHelper.combineAndGetDatePeriods(datePeriods);
		Assert.assertEquals(1, periods.size());
		DatePeriod period1 = periods.get(0);
		Assert.assertEquals(DateUtils.getString2Date("2000-03-03"), period1.getFrom());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-03"), period1.getTo());
	}
	
	@Test
	public void combineAndGetDatePeriodsTest5() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		List<DatePeriod> periods = DatePeriodHelper.combineAndGetDatePeriods(datePeriods);
		Assert.assertEquals(0, periods.size());
	}
	
	@Test
	public void combineDatesTest() {
		List<Date> dates = new ArrayList<Date>();
		dates.add(DateUtils.getString2Date("2000-03-03"));
		dates.add(DateUtils.getString2Date("2000-03-03"));
		System.out.println(DatePeriodHelper.getDatePeriodStr(dates));
	}
	
	@Test
	public void removeDatesFromPeriodsTest() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod1());
		datePeriods.add(getPeriod2());
		datePeriods.add(getPeriod3());
		List<Date> removedDates = new ArrayList<Date>();
		removedDates.add(DateUtils.getString2Date("2000-03-03"));
		removedDates.add(DateUtils.getString2Date("2000-03-01"));
		List<DatePeriod> periods = DatePeriodHelper.removeDatesFromPeriods(datePeriods, removedDates);
		Assert.assertEquals(3, periods.size());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-02"), periods.get(1).getFrom());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-02"), periods.get(1).getTo());
	}
	
	@Test
	public void getDatePeriodStringTest() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod1());
		datePeriods.add(getPeriod2());
		datePeriods.add(getPeriod3());
		List<Date> removedDates = new ArrayList<Date>();
		removedDates.add(DateUtils.getString2Date("2000-03-03"));
		removedDates.add(DateUtils.getString2Date("2000-03-01"));
		List<DatePeriod> periods = DatePeriodHelper.removeDatesFromPeriods(datePeriods, removedDates);
		String periodString = DatePeriodHelper.getDatePeriodString(periods);
		Assert.assertEquals("2000-02-28,2000-02-29;2000-03-02;2000-03-05,2000-03-08;", periodString);
	}
	
	@Test
	public void getSigleDateTest(){
		List<DatePeriod> periods = new ArrayList<DatePeriod>();
		periods.add(new DatePeriod(DateUtils.getString2Date("2000-03-03"), null));
		System.out.println("just from: " + DatePeriodHelper.getDatePeriodString(periods));
		periods.clear();
		
		periods.add(new DatePeriod(null, DateUtils.getString2Date("2000-03-03")));
		System.out.println("just to: " + DatePeriodHelper.getDatePeriodString(periods));
		periods.clear();
		
		periods.add(new DatePeriod(DateUtils.getString2Date("2000-03-03"), DateUtils.getString2Date("2000-03-03")));
		System.out.println("to equals from: " + DatePeriodHelper.getDatePeriodString(periods));
		periods.clear();
		
		
		periods.add(new DatePeriod(DateUtils.getString2Date("2000-03-03"), null));
		periods.add(new DatePeriod(DateUtils.getString2Date("2000-03-05"), DateUtils.getString2Date("2000-03-08")));

		System.out.println(" normal: " + DatePeriodHelper.getDatePeriodString(periods));
		periods.clear();
	}
	
	@Test
	public void getDateStringTest() {
		List<Date> dates = new ArrayList<Date>();
		dates.add(DateUtils.getString2Date("2000-03-03"));
		dates.add(DateUtils.getString2Date("2000-03-01"));
		Assert.assertEquals("2000-03-03;2000-03-01", DateUtils.getDateString(dates, DateUtils.YYYY_MM_DD));
	}
	
	@Test
	public void getDatePeriodTest() {
		String periodStr = "2000-03-01,2000-03-01;2000-03-02,2000-03-03;";
		List<DatePeriod> datePeriods = DatePeriodHelper.getDatePeriods(periodStr);
		Assert.assertEquals(2, datePeriods.size());
		Assert.assertEquals(DateUtils.getString2Date("2000-03-01"), datePeriods.get(0).getTo());
	}
	
	@Test
	public void getDatesAfterTest() {
		List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
		datePeriods.add(getPeriod1());
		datePeriods.add(getPeriod2());
		datePeriods.add(getPeriod3());
		Date date = DateUtils.getString2Date("2000-03-05");
		List<Date> datesBefore = DatePeriodHelper.getDatesEqualOrAfter(datePeriods, date);
		Assert.assertEquals(4, datesBefore.size());
	}
	
	@Test
	public void getMaxRangeTest() {
	    List<DatePeriod> datePeriods = new ArrayList<DatePeriod>();
	    datePeriods.add(getPeriod2());
        datePeriods.add(getPeriod1());
        datePeriods.add(getPeriod3());
        DatePeriod maxRange = DatePeriodHelper.getMaxRange(datePeriods);
        Assert.assertEquals(DateUtils.getString2Date("2000-02-28"), maxRange.getFrom());
        Assert.assertEquals(DateUtils.getString2Date("2000-03-08"), maxRange.getTo());
	}
	
	private DatePeriod getPeriod1() {
		Date from = DateUtils.getString2Date("2000-02-28");
		Date to = DateUtils.getString2Date("2000-03-02");
		return new DatePeriod(from, to);
	}
	
	private DatePeriod getPeriod2() {
		Date from = DateUtils.getString2Date("2000-03-03");
		Date to = DateUtils.getString2Date("2000-03-03");
		return new DatePeriod(from, to);
	}
	
	private DatePeriod getPeriod3() {
		Date from = DateUtils.getString2Date("2000-03-05");
		Date to = DateUtils.getString2Date("2000-03-08");
		return new DatePeriod(from, to);
	}
	
	private DatePeriod getPeriod4() {
		Date from = DateUtils.getString2Date("2000-03-07");
		Date to = DateUtils.getString2Date("2000-03-13");
		return new DatePeriod(from, to);
	}
	
	private DatePeriod getPeriod5() {
		Date from = DateUtils.getString2Date("2000-03-09");
		Date to = DateUtils.getString2Date("2000-03-11");
		return new DatePeriod(from, to);
	}
	
}
