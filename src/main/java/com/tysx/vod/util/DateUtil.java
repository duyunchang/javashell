package com.tysx.vod.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期的工具类
 * @author duomn
 *
 */

public class DateUtil {
	/** 转换时间的默认格式 */
	private static final String DEFULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 获取字符串格式日期
	 * @param date 指定日期
	 * @param format 指定的格式
	 * @return 时间字符串
	 */
	public static String getDateText(Date date, String format) {
		return new SimpleDateFormat(format).format(date);
	}
	
	/**
	 * 获取字符串格式日期
	 * @param date 指定日期
	 * @return yyyy-MM-dd HH:mm:ss格式的时间串
	 */
	public static String getDateText(Date date) {
		return getDateText(date, DEFULT_FORMAT);
	}
	
	/**
	 * 获取字符串日期格式
	 * @param format 日期的格式
	 * @return	指定格式的当前时间
	 */
	public static String getDateNow(String format) {
		return getDateText(new Date(), format);
	}
	
	/**
	 * 获取字符串日期格式
	 * @return yyyy-MM-dd HH:mm:ss格式的当前时间
	 */
	public static String getDateNow() {
		return getDateText(new Date(), DEFULT_FORMAT);
	}
	
	/**
	 * 按指定的格式，把时间字符串转换成Date类型
	 * @return Date类型
	 */
	public static Date toDate(String dateStr, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(dateStr);
	}
	
	/**
	 * 把yyyy-MM-dd HH:mm:ss格式字符串转换成Date类型 
	 * @param dateStr
	 * @return Date类型
	 */
	public static Date toDate(String dateStr) throws ParseException {
		return toDate(dateStr, DEFULT_FORMAT);
	}
	
	/**
	 * 获取前一天的时间
	 * @return 前一天
	 */
	public static Date getBefore(Date date) {
		return getBefore(date, -1);
	}
	
	/**
	 * 获取指定天数的偏移量
	 * @param date	时间
	 * @param off	偏移的天数，整数为加上的天数，负数为减去的天数
	 * @return
	 */
	public static Date getBefore(Date date, int off) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, off);
		return cal.getTime();
	}
}
