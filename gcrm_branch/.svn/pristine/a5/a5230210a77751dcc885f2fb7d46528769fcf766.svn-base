package com.baidu.gcrm.common;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public final class DateUtils {

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	
	public static final String YYYY_MM_DD_ = "yyyy/MM/dd";

	public static final String YYYYMMDD = "yyyyMMdd";
	
	public static final String YYMMDD = "yyMMdd";
	
	public static final String YYYY_MM = "yyyy-MM";

	public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";

	public static final String YYYYMMDD_HH = "yyyyMMdd_HH";
	
	public static final String YYYY_MM_DD_00_00_00 = "yyyy-MM-dd 00:00:00";

	public static String today;

	public static String yesterday;

	private DateUtils() {
	}

	public static Date getCurrentDate() {
		return new Date();
	}

	public static Date getYesterdayDate() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -1);
		return c.getTime();
	}
	
	/**
	 * 计算从某个日期开始之后n天的日期
	 * @param from 开始日期
	 * @param n 
	 * @return
	 */
	public static Date getNDayFromDate(Date from, int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(from);
		c.add(Calendar.DAY_OF_MONTH, n);
		return c.getTime();
	}

	public static Date getNMonthDate(int n) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, n);
		return c.getTime();

	}

	public static Date getNWeekDate(int n) {

		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, n);
		return c.getTime();

	}

	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	public static String getCurrentFormatDate(String formatDate) {
		Date date = getCurrentDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);

		return simpleDateFormat.format(date);
	}

	public static String getCurrentFormatDate() {
		Date date = getCurrentDate();
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}

	public static void resetToday() {
		String todayStr = getDate2String("yyyy-MM-dd", getCurrentDate());

		if ((today == null) || !today.equals(todayStr)) {
			today = todayStr;
			yesterday = getDate2String("yyyy-MM-dd", getYesterdayDate());
		}
	}

	public static final String getDate2String(String format, Date date) {
		if (date != null) {
			if (StringUtils.isEmpty(format)) {
				format = YYYY_MM_DD_HH_MM_SS;
			}

			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

			return simpleDateFormat.format(date);
		} else {
			return "";
		}
	}

	public static String getDate2String(Date date) {
		return getDate2String("", date);
	}
	
	public static String getDate2ShortString(Date date) {
		return getDate2String(YYYY_MM_DD, date);
	}

	public static String getLong2ShortString(long l, String formatDate) {
		Date date = getLong2Date(l);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);

		return simpleDateFormat.format(date);
	}

	public static Date getString2Date(String format, String str) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		ParsePosition parseposition = new ParsePosition(0);

		return simpleDateFormat.parse(str, parseposition);
	}

	public static Date getString2Date(String str) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YYYY_MM_DD);
		ParsePosition parseposition = new ParsePosition(0);
		return simpleDateFormat.parse(str, parseposition);
	}

	public static Date getString2Date(String format, Locale locale, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(format, locale);

		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 尝试解析，如果出错就返回null
	 * 
	 * @param format
	 * @param dateStr
	 * @return 正常解析的话，就返回一个date，否则返回null
	 */
	public static Date tryParseString2Date(String format, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getLong2Date(long l) {
		return new Date(l);
	}

	public static int getOffDays(long l) {
		return getOffDays(l, getCurrentTimeMillis());
	}

	public static int getOffDays(long from, long to) {
		return getOffMinutes(from, to) / 1440;
	}

	public static int getOffMinutes(long l) {
		return getOffMinutes(l, getCurrentTimeMillis());
	}

	public static int getOffMinutes(long from, long to) {
		return (int) ((to - from) / 60000L);
	}

	public static String getLastNQuarterBeginDate(int n) {
		return getLastNQuarterBeginDate(n, new SimpleDateFormat("yyyy-MM-dd"));
	}

	public static String getLastNQuarterBeginDate(int n, DateFormat format) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, n * 3);

		int q = getQuarter(c.get(Calendar.MONTH));

		c.set(Calendar.MONTH, 3 * (q - 1));
		c.set(Calendar.DATE, c.getActualMinimum(Calendar.DATE));
		return format.format(c.getTime());
	}

	public static String getLastNQuarterEndDate(int n) {
		return getLastNQuarterEndDate(n, new SimpleDateFormat("yyyy-MM-dd"));
	}

	private static int getQuarter(int month) {
		if (month <= 2 && month >= 0)
			return 1;
		if (month <= 5 && month >= 3)
			return 2;
		if (month <= 8 && month >= 6)
			return 3;
		if (month <= 11 && month >= 9)
			return 4;
		throw new IllegalStateException("月份错误");
	}

	public static String getLastNQuarterEndDate(int n, DateFormat format) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, n * 3);

		int q = getQuarter(c.get(Calendar.MONTH));

		c.set(Calendar.MONTH, 2 + 3 * (q - 1));

		c.set(Calendar.DATE, c.getMaximum(Calendar.DAY_OF_MONTH));

		return format.format(c.getTime());
	}

	public static String getLastNMonthBeginDate(int n, DateFormat format) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, n);
		c.set(Calendar.DATE, c.getMinimum(Calendar.DAY_OF_MONTH));
		return format.format(c.getTime());
	}

	public static String getLastNMonthBeginDate(int n) {
		return getLastNMonthBeginDate(n, new SimpleDateFormat("yyyy-MM-dd"));
	}

	public static String getLastNMonthEndDate(int n) {
		return getLastNMonthEndDate(n, new SimpleDateFormat("yyyy-MM-dd"));
	}

	public static String getLastNMonthEndDate(int n, DateFormat format) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MONTH, n);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));

		return format.format(c.getTime());
	}

	public static String getLastNYearBeginDate(int n) {
		return getLastNYearBeginDate(n, new SimpleDateFormat("yyyy-MM-dd"));
	}

	public static String getLastNYearBeginDate(int n, DateFormat format) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, n);
		c.set(c.get(Calendar.YEAR), 0, 1);
		return format.format(c.getTime());
	}

	public static String getLastNYearEndDate(int n) {
		return getLastNYearEndDate(n, new SimpleDateFormat("yyyy-MM-dd"));
	}

	public static String getLastNYearEndDate(int n, DateFormat format) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.YEAR, n);
		c.set(c.get(Calendar.YEAR), 11, 31);
		return format.format(c.getTime());
	}
	
	/**
	 * 获取当年最后一天日期
	 * @return
	 */
	public static Date getLastDateOfCurrentYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.set(c.get(Calendar.YEAR), 11, 31);
		return c.getTime();
	}
	
	public static int getDaysBetween(Date from, Date to) {
		return getOffDays(from.getTime(), to.getTime());
	}
	
	/**
	 * 计算当年剩余的天数
	 * @return
	 */
	public static int getRemainingDays() {
		return getDaysBetween(new Date(), getLastDateOfCurrentYear());
	}
	
	/**
	 * 获取从指定日期开始到n天后（包括第n天）的日期列表
	 * @param from 开始日期
	 * @param n 天数
	 * @return
	 */
	public static List<Date> getNDatesFromDate(Date from, int n) {
		List<Date> dates = new ArrayList<Date>();
		for (int i=0; i < n; i++) {
			Date date = DateUtils.getNDayFromDate(from, i);
			dates.add(date);
		}
		return dates;
	}
	
	public static String getDateString(List<Date> dates, String format) {
		if (CollectionUtils.isEmpty(dates)) {
			return StringUtils.EMPTY;
		}
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		for (Date date : dates) {
			builder.append(simpleDateFormat.format(date));
			builder.append(';');
		}
		return builder.substring(0, builder.length() - 1);
	}
	
	public static int compareDate(Date from,Date to,String format){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		from  = getString2Date(format, simpleDateFormat.format(from));
		to  = getString2Date(format, simpleDateFormat.format(to));
		
		if(from.after(to)){
			return 1;
		}else if(from.before(to)){
			return -1;
		}else{
			return 0;
		}
	}
	
	public static Date getCurrentDateOfZero() {
		String currentFormatDate = DateUtils.getCurrentFormatDate(DateUtils.YYYY_MM_DD);
		Date currentDate = DateUtils.getString2Date(currentFormatDate);
		return currentDate;
	}
	
	public static Date getTomorrowDate(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, 1);
		return c.getTime();
	}
	
	public static Date getAfterTomorrowDate(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, 2);
		return c.getTime();
	}
	
	public static Date getBeforeYestodayDate(){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, -2);
		return c.getTime();
	}
	/**
	* 功能描述：   获取向前或向后推移week周的day天
	* 创建人：yudajun    
	* 创建时间：2014-5-17 下午2:30:15   
	* 修改人：yudajun
	* 修改时间：2014-5-17 下午2:30:15   
	* 修改备注：   
	* 参数： @param week 0 为当前周 -1 推迟一周  1 为下周 类推
	* 参数： @param day SUNDAY、MONDAY、TUESDAY、WEDNESDAY、THURSDAY、FRIDAY、SATURDAY
	* 参数： @return
	* @version
	 */
	public static Date getTheDayOfWeek(int week,int day){
		Calendar cal = Calendar.getInstance();
		if(day < Calendar.SUNDAY || day > Calendar.SATURDAY){
			return cal.getTime();
		}
		
		cal.add(Calendar.DATE, week*7);
		cal.set(Calendar.DAY_OF_WEEK,day);
		String res = new SimpleDateFormat(YYYY_MM_DD).format(cal.getTime());
		return getString2Date(YYYY_MM_DD, res);
	}
	/**
	* 功能描述：   判断
	* 创建人：yudajun    
	* 创建时间：2014-5-25 下午3:11:57   
	* 修改人：yudajun
	* 修改时间：2014-5-25 下午3:11:57   
	* 修改备注：   
	* 参数： @param n
	* 参数： @return
	* @version
	 */
	public static int getWeekDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.DAY_OF_WEEK);

	}
	
	public static int getDayInWeek(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_WEEK);
	}
}
