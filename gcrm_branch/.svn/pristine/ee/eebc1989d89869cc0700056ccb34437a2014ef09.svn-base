package com.baidu.gcrm.publish;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.baidu.gcrm.common.DateUtils;
import com.baidu.gcrm.occupation.helper.DatePeriod;
import com.baidu.gcrm.publish.helper.PublishDateHelper;
import com.baidu.gcrm.publish.model.PublishDate;
import com.baidu.gcrm.publish.model.PublishDate.PublishPeriodStatus;

@Ignore
public class PublishDateHelperTest {
	@Test
	public void combineOldAndNewPublishTest1() {
		Date approvalDate = DateUtils.getString2Date("2018-01-07");
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate3());
		List<DatePeriod> newPeriods = new ArrayList<DatePeriod>();
		newPeriods.add(getDatePeriod2());
		String publishNumber = "123";
		List<PublishDate> dates = PublishDateHelper.combineOldAndNewPublish(oldDates, newPeriods, approvalDate, publishNumber);
		System.out.println(dates);
	}
	
	@Test
	public void combineOldAndNewPublishTest2() {
		Date approvalDate = DateUtils.getString2Date("2018-01-07");
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		List<DatePeriod> newPeriods = new ArrayList<DatePeriod>();
		newPeriods.add(getDatePeriod1());
		newPeriods.add(getDatePeriod2());
		String publishNumber = "123";
		List<PublishDate> dates = PublishDateHelper.combineOldAndNewPublish(oldDates, newPeriods, approvalDate, publishNumber);
		System.out.println(dates);
	}
	
	@Test
	public void combineOldAndNewPublishTest3() {
		Date approvalDate = DateUtils.getString2Date("2018-01-17");
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		List<DatePeriod> newPeriods = new ArrayList<DatePeriod>();
		newPeriods.add(getDatePeriod3());
		String publishNumber = "123";
		List<PublishDate> dates = PublishDateHelper.combineOldAndNewPublish(oldDates, newPeriods, approvalDate, publishNumber);
		System.out.println(dates);
	}
	
	@Test
	public void combineOldAndNewPublishTest4() {
		Date approvalDate = DateUtils.getString2Date("2018-01-17");
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		List<DatePeriod> newPeriods = new ArrayList<DatePeriod>();
		newPeriods.add(getDatePeriod1());
		newPeriods.add(getDatePeriod4());
		newPeriods.add(getDatePeriod5());
		String publishNumber = "123";
		List<PublishDate> dates = PublishDateHelper.combineOldAndNewPublish(oldDates, newPeriods, approvalDate, publishNumber);
		System.out.println(dates);
	}
	
	@Test
	public void combineOldAndNewPublishTest5() {
		Date approvalDate = DateUtils.getString2Date("2018-01-07");
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate6());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		List<DatePeriod> newPeriods = new ArrayList<DatePeriod>();
		newPeriods.add(getDatePeriod1());
		newPeriods.add(getDatePeriod4());
		newPeriods.add(getDatePeriod5());
		String publishNumber = "123";
		List<PublishDate> dates = PublishDateHelper.combineOldAndNewPublish(oldDates, newPeriods, approvalDate, publishNumber);
		System.out.println(dates);
	}
	
	@Test
	public void combineOldAndNewPublishTest6() {
		Date approvalDate = DateUtils.getString2Date("2018-01-15");
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate6());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		List<DatePeriod> newPeriods = new ArrayList<DatePeriod>();
		newPeriods.add(getDatePeriod1());
		newPeriods.add(getDatePeriod4());
		newPeriods.add(getDatePeriod5());
		String publishNumber = "123";
		List<PublishDate> dates = PublishDateHelper.combineOldAndNewPublish(oldDates, newPeriods, approvalDate, publishNumber);
		System.out.println(dates);
	}
	
	@Test
	public void combineOldAndNewPublishTest7() {
		Date approvalDate = DateUtils.getString2Date("2018-01-16");
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate6());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		List<DatePeriod> newPeriods = new ArrayList<DatePeriod>();
		newPeriods.add(getDatePeriod1());
		newPeriods.add(getDatePeriod4());
		newPeriods.add(getDatePeriod5());
		String publishNumber = "123";
		List<PublishDate> dates = PublishDateHelper.combineOldAndNewPublish(oldDates, newPeriods, approvalDate, publishNumber);
		System.out.println(dates);
	}
	
	@Test
	public void updatePublishDateAfterAddingDatesTest() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		List<Date> dates = new ArrayList<Date>();
		dates.add(DateUtils.getString2Date("2018-01-19"));
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterAddingDates(oldDates, dates, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterAddingDatesTest1() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		List<Date> dates = new ArrayList<Date>();
		dates.add(DateUtils.getString2Date("2018-01-10"));
		dates.add(DateUtils.getString2Date("2018-01-11"));
		dates.add(DateUtils.getString2Date("2018-01-14"));
		dates.add(DateUtils.getString2Date("2018-01-20"));
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterAddingDates(oldDates, dates, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterAddingDatesTest2() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate4());
		List<Date> dates = new ArrayList<Date>();
		dates.add(DateUtils.getString2Date("2018-01-20"));
		dates.add(DateUtils.getString2Date("2018-01-21"));
		dates.add(DateUtils.getString2Date("2018-01-25"));
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterAddingDates(oldDates, dates, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterAddingDatesTest3() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		oldDates.add(getPublishDate5());
		List<Date> dates = new ArrayList<Date>();
		dates.add(DateUtils.getString2Date("2018-01-12"));
		dates.add(DateUtils.getString2Date("2018-01-13"));
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterAddingDates(oldDates, dates, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterAddingDatesTest4() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		oldDates.add(getPublishDate5());
		List<Date> dates = new ArrayList<Date>();
		dates.add(DateUtils.getString2Date("2018-01-12"));
		dates.add(DateUtils.getString2Date("2018-01-13"));
		dates.add(DateUtils.getString2Date("2018-01-14"));
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterAddingDates(oldDates, dates, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterAddingDatesTest5() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate3());
		List<Date> dates = new ArrayList<Date>();
		dates.add(DateUtils.getString2Date("2018-01-10"));
		dates.add(DateUtils.getString2Date("2018-01-12"));
		dates.add(DateUtils.getString2Date("2018-01-13"));
		dates.add(DateUtils.getString2Date("2018-01-14"));
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterAddingDates(oldDates, dates, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterRemovingDateTest1() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		Date date = DateUtils.getString2Date("2018-01-06");
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterRemovingDate(oldDates, date, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterRemovingDateTest2() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		Date date = DateUtils.getString2Date("2018-01-16");
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterRemovingDate(oldDates, date, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterRemovingDateTest3() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate4());
		// need to set current date to 2018-01-16
		Date date = DateUtils.getString2Date("2018-01-16");
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterRemovingDate(oldDates, date, publishNumber);
		System.out.println(newDates);
	}
	
	@Test
	public void updatePublishDateAfterRemovingDateTest4() {
		List<PublishDate> oldDates = new ArrayList<PublishDate>();
		oldDates.add(getPublishDate1());
		oldDates.add(getPublishDate2());
		oldDates.add(getPublishDate3());
		oldDates.add(getPublishDate5());
		// need to set current date to 2018-01-11
		Date date = DateUtils.getString2Date("2018-01-11");
		String publishNumber = "123";
		List<PublishDate> newDates = PublishDateHelper.updatePublishDateAfterRemovingDate(oldDates, date, publishNumber);
		System.out.println(newDates);
	}
	
	private PublishDate getPublishDate1() {
		PublishDate date = new PublishDate();
		date.setId(1l);
		date.setPublishNumber("123");
		date.setPlanStart(DateUtils.getString2Date("2018-01-01"));
		date.setPlanEnd(DateUtils.getString2Date("2018-01-03"));
		date.setActuralStart(DateUtils.getString2Date("2018-01-01"));
		date.setActuralEnd(DateUtils.getString2Date("2018-01-02"));
		date.setStatus(PublishPeriodStatus.end);
		return date;
	}
	
	private PublishDate getPublishDate2() {
		PublishDate date = new PublishDate();
		date.setId(2l);
		date.setPublishNumber("123");
		date.setPlanStart(DateUtils.getString2Date("2018-01-05"));
		date.setPlanEnd(DateUtils.getString2Date("2018-01-09"));
		date.setActuralStart(DateUtils.getString2Date(DateUtils.YYYY_MM_DD_HH_MM_SS, "2018-01-05 12:33:33"));
		date.setActuralEnd(DateUtils.getString2Date(DateUtils.YYYY_MM_DD_HH_MM_SS, "2018-01-07 11:33:33"));
		date.setStatus(PublishPeriodStatus.end);
		return date;
	}
	
	private PublishDate getPublishDate3() {
		PublishDate date = new PublishDate();
		date.setId(3l);
		date.setPublishNumber("123");
		date.setPlanStart(DateUtils.getString2Date("2018-01-15"));
		date.setPlanEnd(DateUtils.getString2Date("2018-01-19"));
		date.setStatus(PublishPeriodStatus.not_start);
		return date;
	}
	
	private PublishDate getPublishDate4() {
		PublishDate date = new PublishDate();
		date.setId(4l);
		date.setPublishNumber("123");
		date.setPlanStart(DateUtils.getString2Date("2018-01-15"));
		date.setPlanEnd(DateUtils.getString2Date("2018-01-19"));
		date.setActuralStart(DateUtils.getString2Date(DateUtils.YYYY_MM_DD_HH_MM_SS, "2018-01-16 12:33:33"));
		date.setStatus(PublishPeriodStatus.ongoing);
		return date;
	}
	
	private PublishDate getPublishDate5() {
		PublishDate date = new PublishDate();
		date.setId(5l);
		date.setPublishNumber("123");
		date.setPlanStart(DateUtils.getString2Date("2018-01-11"));
		date.setPlanEnd(DateUtils.getString2Date("2018-01-11"));
		date.setActuralStart(DateUtils.getString2Date(DateUtils.YYYY_MM_DD_HH_MM_SS, "2018-01-11 12:33:33"));
		date.setStatus(PublishPeriodStatus.ongoing);
		return date;
	}
	
	private PublishDate getPublishDate6() {
		PublishDate date = new PublishDate();
		date.setId(6l);
		date.setPublishNumber("123");
		date.setPlanStart(DateUtils.getString2Date("2018-01-01"));
		date.setPlanEnd(DateUtils.getString2Date("2018-01-03"));
		date.setStatus(PublishPeriodStatus.not_start);
		return date;
	}
	
	private DatePeriod getDatePeriod1() {
		DatePeriod date = new DatePeriod();
		date.setFrom(DateUtils.getString2Date("2018-01-01"));
		date.setTo(DateUtils.getString2Date("2018-01-03"));
		return date;
	}
	
	private DatePeriod getDatePeriod2() {
		DatePeriod date = new DatePeriod();
		date.setFrom(DateUtils.getString2Date("2018-01-07"));
		date.setTo(DateUtils.getString2Date("2018-01-13"));
		return date;
	}
	
	private DatePeriod getDatePeriod3() {
		DatePeriod date = new DatePeriod();
		date.setFrom(DateUtils.getString2Date("2018-01-01"));
		date.setTo(DateUtils.getString2Date("2018-01-07"));
		return date;
	}
	
	private DatePeriod getDatePeriod4() {
		DatePeriod date = new DatePeriod();
		date.setFrom(DateUtils.getString2Date("2018-01-05"));
		date.setTo(DateUtils.getString2Date("2018-01-09"));
		return date;
	}
	
	private DatePeriod getDatePeriod5() {
		DatePeriod date = new DatePeriod();
		date.setFrom(DateUtils.getString2Date("2018-01-15"));
		date.setTo(DateUtils.getString2Date("2018-01-17"));
		return date;
	}
}
