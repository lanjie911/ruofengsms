package com.sms.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatetimeUtil {

	public static String date2StringDate(Date date) {
		if (date == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	public static String date2StringDatetime(Date date) {
		if (date == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	public static String date2StringDatetime(Date date, String format) {
		if (date == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	public static String date2StringTime(Date date) {
		if (date == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(date);
	}

	public static String timestamp2StringDate(Timestamp t) {
		if (t == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(t);
	}

	public static String timestamp2StringTime(Timestamp t) {
		if (t == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return df.format(t);
	}

	public static String timestamp2StringDatetime(Timestamp t) {
		if (t == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(t);
	}

	public static String timestamp2StringDatetime(Timestamp t, String formatType) {
		if (t == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat(formatType);
		return df.format(t);
	}

	public static String timestamp2StringDateHourMin(Timestamp t) {
		if (t == null) {
			return "";
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return df.format(t);
	}

	public static Date string2Date(String str) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return new Date(df.parse(str).getTime());
	}

	public static Date string2Date(String str, String format) throws ParseException {
		DateFormat df = new SimpleDateFormat(format);
		return new Date(df.parse(str).getTime());
	}

	public static Date string2Datetime(String str) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return new Date(df.parse(str).getTime());
	}

	public static Date string2Time(String str) throws ParseException {
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return new Date(df.parse(str).getTime());
	}

	public static Timestamp string2Timestamp(String str) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date date = df.parse(str);
		String time = df.format(date);
		return Timestamp.valueOf(time);
	}

	public static String dateTime2StringTimestamp(long currentTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time = df.format(new java.util.Date(currentTime));
		return time;
	}
	public static String dateTime3StringTimestamp(long currentTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = df.format(new java.util.Date(currentTime));
		return time;
	}
	public static Timestamp dateTime2Timestamp(String str) throws ParseException{
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		java.util.Date date = df.parse(str);
		SimpleDateFormat dfTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = dfTimeStamp.format(date);
		return Timestamp.valueOf(time);
	}
	// T+1 非工作日
	public static Date periodStartTime(Integer period) {
		Calendar time = Calendar.getInstance();
		int day = time.get(Calendar.DAY_OF_YEAR);
		time.set(Calendar.DAY_OF_YEAR, day + 1);
		time.set(Calendar.MONTH, time.get(Calendar.MONTH) + period - 1);
		return new Date(time.getTime().getTime());
	}

	public static Date periodEndTime(Integer period) {
		Calendar time = Calendar.getInstance();
		int day = time.get(Calendar.DAY_OF_YEAR);
		time.set(Calendar.DAY_OF_YEAR, day + 1);
		time.set(Calendar.MONTH, time.get(Calendar.MONTH) + period);
		return new Date(time.getTime().getTime());
	}

	public static String getCurrentDateTime(String formatType) {
		java.util.Date date = new java.util.Date();
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(date);
	}

	/**
	 * 返回两个日期之间的天数，每月为30天，每年为360天
	 * @param start 开始日期
	 * @param end 结束日期
	 * @return 间隔的天数
	 */
	public static int getDateInterval(java.sql.Date start, java.sql.Date end) {
		GregorianCalendar startCal = new GregorianCalendar();
		GregorianCalendar endCal = new GregorianCalendar();
		startCal.setTime(start);
		endCal.setTime(end);
		int startYear = startCal.get(Calendar.YEAR);
		int endYear = endCal.get(Calendar.YEAR);
		int startMonth = startCal.get(Calendar.MONTH);
		int endMonth = endCal.get(Calendar.MONTH);
		int months = 0;
		if (startYear == endYear) {
			months = endMonth - startMonth;
		} else {
			months = 12 * (endYear - startYear) + endMonth - startMonth;// 两个日期相差几个月，即月份差
		}
		int startDay = startCal.get(Calendar.DATE) == 31 ? 30 : startCal.get(Calendar.DATE);
		int endDay = endCal.get(Calendar.DATE) == 31 ? 30 : endCal.get(Calendar.DATE);
		int gap = months * 30 + endDay - startDay;
		return gap;
	}

	public static int dateDiff(String start, String end) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("zh", "cn"));
		try {
			long t1 = simpleDateFormat.parse(start).getTime();
			long t2 = simpleDateFormat.parse(end).getTime();
			int diff = (int) ((t2 - t1) / 1000);
			return diff;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 两个时间相差距离多少分
	 * @param str1 时间参数 1 格式：1990-01-01 12:00:00
	 * @param str2 时间参数 2 格式：2009-01-01 12:00:00
	 * @return long 返回值为：分
	 */
	public static long getDistanceTime(String str1, String str2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		java.util.Date one;
		java.util.Date two;
		long min = 0;
		try {
			one = df.parse(str1);
			two = df.parse(str2);
			long time1 = one.getTime();
			long time2 = two.getTime();
			long diff;
			if (time1 < time2) {
				diff = time2 - time1;
			} else {
				diff = time1 - time2;
			}
			min = ((diff / (60 * 1000)));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return min;
	}
	
	/**
	 * 时间比较（格式yyyy-MM-dd HH:mm:ss）
	 * @param startTime 时间字符串
	 * @param endTime 时间字符串
	 * @return  -1:endTimed大  0:相等 1:startTime大
	 */
	public static Integer compareTime(String startTime, String endTime){
		Integer compareResult = 0;
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			java.util.Date now = df.parse(startTime);
			java.util.Date date=df.parse(endTime);
			long l = now.getTime() - date.getTime();
			
			long day = l/(24*60*60*1000);
			if(0 < day) return 1;
			if(0 > day ) return -1;
			
			long hour = (l/(60*60*1000)-day*24);
			if(0 < hour) return 1;
			if(0 > hour) return -1;
			
			long min = ((l/(60*1000))-day*24*60-hour*60);
			if(0 < min) return 1;
			if(0 > min) return -1;
			
			long s = (l/1000-day*24*60*60-hour*60*60-min*60);
			if(0 < s) return 1;
			if(0 > s) return -1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return compareResult;
	}

	public static void main(String[] args) {
		try {
			System.out.println(string2StringDate("20160704"));
			System.out.println(string2StringDateTime("20131227085009"));
			System.out.println(string2Date("2016-07-14"));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
//		}
		
		try {
//			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			java.util.Date now = df.parse("2016-03-12 14:59:00");
//			java.util.Date date=df.parse("2016-03-03 14:59:00");
//			long l=now.getTime()-date.getTime();
//			long day=l/(24*60*60*1000);
//			long hour=(l/(60*60*1000)-day*24);
//			long min=((l/(60*1000))-day*24*60-hour*60);
//			long s=(l/1000-day*24*60*60-hour*60*60-min*60);
//			System.out.println(""+day+"天"+hour+"小时"+min+"分"+s+"秒");
			
			//System.out.println(compareTime("2016-03-03 20:20:00", "2016-03-03 14:58:00"));

		} catch (Exception e) {
		}
	}
	
	public static boolean isValidDate(String str) {
		// String patternStr
		// ="^((((19|20)(([02468][048])|([13579][26]))\\-02\\-29))|((20[0-9][0-9])|(19[0-9][0-9]))\\-((((0[1-9])|(1[0-2]))\\-((0[1-9])|(1\\d)|(2[0-8])))|((((0[13578])|(1[02]))\\-31)|(((01,3-9])|(1[0-2]))\\-(29|30)))))$";

		Pattern p = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\-\\s]?((((0?"
				+ "[13578])|(1[02]))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))"
				+ "|(((0?[469])|(11))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
				+ "(0?2[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12"
				+ "35679])|([13579][01345789]))[\\-\\-\\s]?((((0?[13578])|(1[02]))"
				+ "[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
				+ "[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\-\\s]?((0?[" + "1-9])|(1[0-9])|(2[0-8]))))))");

		// Pattern p = null;
		Matcher m = null;
		// p = Pattern.compile(patternStr);
		m = p.matcher(str);
		boolean b = m.matches();
		if (b)
			return true;
		else
			return false;
	}

	public static String dateDiffForTime(Long endTimeSec, Long startTimeSec) {

		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0L;// 计算差多少天
		long hour = 0L;// 计算差多少小时
		long min = 0L;// 计算差多少分钟
		long sec = 0L;// 计算差多少秒
		StringBuffer result = new StringBuffer();

		// 获得两个时间的毫秒时间差异
		diff = endTimeSec - startTimeSec;
		if (diff >= 0) {
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh;// 计算差多少小时
			min = diff % nd % nh / nm;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒

			result.append(day + "天");
			if (hour < 10) {
				result.append("0" + hour + "小时");
			} else {
				result.append(hour + "小时");
			}
			if (min < 10) {
				result.append("0" + min + "分");
			} else {
				result.append(min + "分");
			}
			if (sec < 10) {
				result.append("0" + sec + "秒");
			} else {
				result.append(sec + "秒");
			}
		} else {
			result.append("0天00小时00分00秒");
		}
		// 输出结果
		return result.toString();
	}

	public static String dateDiffForTimeValue(Long endTimeSec, Long startTimeSec) {

		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0L;// 计算差多少天
		long hour = 0L;// 计算差多少小时
		long min = 0L;// 计算差多少分钟
		long sec = 0L;// 计算差多少秒
		StringBuffer result = new StringBuffer();

		// 获得两个时间的毫秒时间差异
		diff = endTimeSec - startTimeSec;
		if (diff >= 0) {
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh;// 计算差多少小时
			min = diff % nd % nh / nm;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒

			result.append(day + ":");
			result.append(hour + ":");
			result.append(min + ":");
			result.append(sec);

		} else {
			result.append("0:0:0:0");
		}
		// 输出结果
		return result.toString();
	}

	public static String dateDiffForUsedTime(Long endTimeSec, Long startTimeSec) {

		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff;
		long day = 0L;// 计算差多少天
		long hour = 0L;// 计算差多少小时
		long min = 0L;// 计算差多少分钟
		long sec = 0L;// 计算差多少秒
		StringBuffer result = new StringBuffer();

		// 获得两个时间的毫秒时间差异
		diff = endTimeSec - startTimeSec;
		if (diff >= 0) {
			day = diff / nd;// 计算差多少天
			hour = diff % nd / nh;// 计算差多少小时
			min = diff % nd % nh / nm;// 计算差多少分钟
			sec = diff % nd % nh % nm / ns;// 计算差多少秒
			if (day > 0) {
				result.append(day + "天");
			}
			if (hour > 0) {
				result.append(hour + "小时");
			}
			if (min > 0) {
				result.append(min + "分");
			}
			if (sec > 0) {
				result.append(sec + "秒");
			}
		}
		// 输出结果
		return result.toString();
	}

	public static String millisecondsToTime(long milliseconds) {

		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数

		long day = 0L;// 计算差多少天
		long hour = 0L;// 计算差多少小时
		long min = 0L;// 计算差多少分钟
		long sec = 0L;// 计算差多少秒

		day = milliseconds / nd;// 计算差多少天
		hour = milliseconds % nd / nh;// 计算差多少小时
		min = milliseconds % nd % nh / nm;// 计算差多少分钟
		sec = milliseconds % nd % nh % nm / ns;// 计算差多少秒

		return String.format("%02d天%02d时%02d分%02d秒", day, hour, min, sec);
	}

	public static int compareDate(String startDate, String endDate, String formatter) {
		int compareResult = 0;
		DateFormat dateFormat = new SimpleDateFormat(formatter);

		try {
			java.util.Date dateTime1 = dateFormat.parse(startDate);
			java.util.Date dateTime2 = dateFormat.parse(endDate);
			compareResult = dateTime1.compareTo(dateTime2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return compareResult;
	}

	/**
	 * 根据日期获取年份
	 * @param date
	 * @return
	 */
	public static Integer getYearByDate(Date date) {
		Integer year = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		year = cal.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 根据日期获取月份
	 * @param date
	 * @return
	 */
	public static Integer getMonthByDate(Date date) {
		Integer month = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 根据日期 获取天
	 * @param date
	 * @return
	 */
	public static Integer getDayByDate(Date date) {
		Integer day = 0;
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		day = cal.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 获取下个月的日期
	 * @param date
	 * @return
	 */
	public static Date getNextMonthDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		cal.add(Calendar.MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = Date.valueOf(sdf.format(cal.getTime()));
		return newDate;
	}

	/**
	 * 获取下一年1月份的日期
	 * @param date
	 * @return
	 */
	public static Date getNextYearDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		cal.add(Calendar.YEAR, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = Date.valueOf(sdf.format(cal.getTime()));
		return newDate;
	}

	/**
	 * 获取下一个季度的日期
	 * @param date
	 * @return
	 */
	public static Date getNextQuarterDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		cal.add(Calendar.MONTH, 3);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = Date.valueOf(sdf.format(cal.getTime()));
		return newDate;
	}

	/**
	 * 获取指定日期距离当月剩余天数
	 * @param date 指定日期
	 * @return 距离当月剩余天数
	 */
	public static int getSurplusDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		int day = cal.get(Calendar.DAY_OF_MONTH); // 获取当前天数
		int last = cal.getActualMaximum(cal.DAY_OF_MONTH); // 获取本月最大天数
		int surplusDays = last - day;
		return surplusDays;
	}

	/**
	 * 获取指定日期前一天日期
	 * @param date 指定日期
	 * @return 距离当月剩余天数
	 */
	public static Date getBeforeDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		cal.add(Calendar.DAY_OF_MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date newDate = Date.valueOf(sdf.format(cal.getTime()));
		return newDate;
	}

	/**
	 * 获得指定格式的前/后起始与结束时间
	 * @param pattern 格式(如：yyyy-MM-dd HH:mm:ss)
	 * @param day (正-前;负-后)
	 * @param hour 时(开始:0;结束:23)
	 * @param minute 分(开始:0;结束:59)
	 * @param second 秒(开始:0;结束:59)
	 * @return
	 */
	public static Timestamp getNextOrPrevDayToStartOrEndTime(String pattern, int day, int hour, int minute,
			int second) {

		Calendar currentDate = new GregorianCalendar();
		currentDate.add(Calendar.DATE, day);
		currentDate.set(Calendar.HOUR_OF_DAY, hour);
		currentDate.set(Calendar.MINUTE, minute);
		currentDate.set(Calendar.SECOND, second);

		SimpleDateFormat df = new SimpleDateFormat(pattern);

		return Timestamp.valueOf(df.format(currentDate.getTime()));

	}

	/**
	 * 获得指定格式的前/后时间(天取整点)
	 * 
	 * @param p (0-天;1-小时;2-分钟)
	 * @param pattern
	 * @param day
	 * @param hour
	 * @param minute
	 * @param second
	 * @param iscurrent (true: 取整点 ; false: 当前时间倒推)
	 * @return
	 */
	public static Timestamp getNextOrPrevToStartOrEndTime(int p, String pattern, int day, int hour, int minute,
			int second, boolean iscurrent) {

		Calendar currentDate = new GregorianCalendar();
		switch (p) {
		case 0:
			currentDate.add(Calendar.DATE, day);
			currentDate.set(Calendar.HOUR_OF_DAY, hour);
			currentDate.set(Calendar.MINUTE, minute);
			currentDate.set(Calendar.SECOND, second);
			break;
		case 1:
			currentDate.add(Calendar.HOUR, hour);
			if (iscurrent) {
				currentDate.set(Calendar.MINUTE, minute);
				currentDate.set(Calendar.SECOND, second);
			}
			break;
		case 2:
			currentDate.add(Calendar.MINUTE, minute);
			if (iscurrent) {
				currentDate.set(Calendar.SECOND, second);
			}
			break;
		default:

		}

		SimpleDateFormat df = new SimpleDateFormat(pattern);

		return Timestamp.valueOf(df.format(currentDate.getTime()));

	}

	/**
	 * 获取指定日期所在月份共有天数
	 * @param date 指定日期
	 * @return 当月共有天数
	 */
	public static int getMonthTotalDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		int last = cal.getActualMaximum(cal.DAY_OF_MONTH); // 获取本月最大天数
		return last;
	}

	/**
	 * 获取指定日期天数
	 * @param date 指定日期
	 * @return 天数
	 */
	public static int getMonthDays(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		int day = cal.get(Calendar.DAY_OF_MONTH); // 获取本月最大天数
		return day;
	}

	/**
	 * 获取当前月份
	 * @param date 指定日期
	 * @return 当前月份
	 */
	public static int getCurMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		int month = cal.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 * 获取当前年份
	 * @param date 指定日期
	 * @return 当前年份
	 */
	public static int getCurYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date(date.getTime()));
		int year = cal.get(Calendar.YEAR);
		return year;
	}
	
	/**
	 * 将日期数值转换成指定格式日期
	 * @param str 例：20160612
	 * @return 2016-06-12
	 * @throws ParseException
	 */
	public static String string2StringDate(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        java.util.Date date = simpleDateFormat.parse(str);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
	}
	
	/**
	 * 将时间数值转换成指定格式时间字符串
	 * @param str 例：20131227085009
	 * @return 2013-12-27 08:50:09
	 * @throws ParseException
	 */
	public static String string2StringDateTime(String str) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        java.util.Date date = simpleDateFormat.parse(str);
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
	}

	public static int getMonth(Date start, Date end) {
		if (start.after(end)) {
			Date t = start;
			start = end;
			end = t;
		}
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(start);
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(end);
		Calendar temp = Calendar.getInstance();
		temp.setTime(end);
		temp.add(Calendar.DATE, 1);

		int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
		int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);

		if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month + 1;
		} else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
			return year * 12 + month;
		} else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
			return year * 12 + month;
		} else {
			return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
		}
	}

	/**
	 * 获得两个日期之间相隔的天数
	 * @param strdate1 开始日期
	 * @param strdate2 结束日期
	 * @return
	 * @throws ParseException
	 */
	public static long getDayCha(String strdate1, String strdate2) throws ParseException {
		java.util.Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(strdate1);
		java.util.Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(strdate2);
		long cha = date2.getTime() - date1.getTime();
		cha = cha / 1000 / 3600 / 24;
		return cha;
	}

	/**
	 * 取得给定日期的前几天或后几天的指定格式的日期
	 * @param pattern 格式
	 * @param strDate 日期
	 * @param day 前几天或后几天(负：前 正：后)
	 * @return
	 * @throws ParseException
	 */
	public static String getNextOrPrevDay(String pattern, String strDate, int day) throws ParseException {
		Calendar cal = Calendar.getInstance();
		java.util.Date date = new SimpleDateFormat(pattern).parse(strDate);
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		String dayDate = new SimpleDateFormat(pattern).format(cal.getTime());
		return dayDate;
	}

	/**
	 * 日期转整型
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static int dateFormatInt(String date) throws ParseException {
		SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date _date = sformat.parse(date);
		int intdate = (int) (_date.getTime() / 1000);
		return intdate;
	}

}
