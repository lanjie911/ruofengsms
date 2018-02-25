package com.godai.trade.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class TimeUtil {
	
	private static final String TIMESTAMP_FORMAT_SEPARATOR = "yyyyMMddHHmmssSSS";
	private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";
	private static final String TIMESTAMP_FORMAT_NO_SEPARATOR = "yyyyMMddHHmmss";
	private static final String CLOCKTIME24 = "235959";
	
	
	/**
	 * 计算当前时间 距离今天Time时刻的毫秒数
	 * @param Time 格式 "HHmmssSSS" 例如 "235959999"
	 * @return 毫秒数
	 * @throws ParseException
	 */
	public static Long getInitialDelay(String Time) throws ParseException {
		Date now =new Date(); 
		SimpleDateFormat sdf=new SimpleDateFormat(TIMESTAMP_FORMAT_SEPARATOR);
		String nowstr= sdf.format(now);
		String todayEndDate = nowstr.substring(0,8)+Time;
		Date endTime = null;
		endTime=sdf.parse(todayEndDate);
		return endTime.getTime()-now.getTime();
	}
	
	/**
	 * 根据上送的String转换成timestamp pattern 为null 默认yyyyMMddHHmmss
	 * @param timeStr
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Timestamp getTime(String timeStr,String pattern) throws ParseException{
		if(null==timeStr||"".equals(timeStr)){ 
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(StringUtils.isBlank(pattern)?TIMESTAMP_FORMAT:pattern);
		Date date=sdf.parse(timeStr);
		return new Timestamp(date.getTime());
	}
	
	/**
	 * 返回该时间的yyyyMMddHHmmss形式
	 * @param time
	 * @return
	 */
	public static String getTimestring(Timestamp time){
		if(null==time){
			return "";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(TIMESTAMP_FORMAT);
		return sdf.format(new Date(time.getTime()));
	}
	
	
	/**
	 * 获取系统当前时间
	 * @return yyyyMMddHHmmss
	 */
	public static Timestamp getNowTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 时间滚动 按天滚动，返回时间为滚动到期当天的235959
	 * @param date
	 * @param i
	 * @throws ParseException 
	 */
	public static Timestamp DateRollEndAt24(Date date, int i,boolean rollflag) throws ParseException {
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH,i);
		SimpleDateFormat timeForamt = new SimpleDateFormat(TIMESTAMP_FORMAT_NO_SEPARATOR);
		String dateStr =timeForamt.format(new Date(calendar.getTimeInMillis())); // 滚动后日期
		dateStr=dateStr.substring(0,8)+CLOCKTIME24; // 当天的235959
		return new Timestamp(timeForamt.parse(dateStr).getTime());
	}

	/**
	 * 得到当前日期和时间 "yyyyMMddHHmmss";
	 * @return
	 */
	public static String getCurrentFullTime2() {
		SimpleDateFormat timeForamt = new SimpleDateFormat("yyyyMMddHHmmss");
		return timeForamt.format(new java.util.Date());
	}
	
	public static String getCurrentFullTime3() {
		SimpleDateFormat timeForamt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return timeForamt.format(new java.util.Date());
	}
}
