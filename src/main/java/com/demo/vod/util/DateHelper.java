package com.demo.vod.util;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class DateHelper {
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FOMRAT_SSS = "yyyy-MM-dd HH:mm:ss SSS";

	public static String getDateDirStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String tmpStr = sdf.format(Calendar.getInstance().getTime());
		String[] tmpArr = tmpStr.split("/");
		StringBuffer dateDirStr = new StringBuffer();
		for (String s : tmpArr) {
			dateDirStr.append(s).append(File.separator);
		}
		return dateDirStr.toString().substring(0, dateDirStr.length() - 1);
	}

	public static Date getDateByStr(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return sdf.parse(time);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getFmtDateByStr(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		try {
			return sdf.parse(time);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getDateTimeByStr(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return sdf.parse(time);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getDateTimeByyyyyMMddHHmmss(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return sdf.parse(time);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getDateTimeByyyyyMMdd(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.parse(time);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateByDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatDate(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateTimeByDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateStrTimeByDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateTimeByNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return sdf.format(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getDateByNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return sdf.parse(sdf.format(new Date(System.currentTimeMillis())));
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateTimeByNowyyyyMMddHHmmss() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return sdf.format(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			return null;
		}
	}

	public static String dateToString(Date date) {
		SimpleDateFormat df;
		String returnValue = "";
		if (date != null) {
			df = new SimpleDateFormat(DATE_FORMAT);
			returnValue = df.format(date);
		}

		return (returnValue);
	}

	public static String getDateTimeByyyyyMMddHHmmss(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateTimeByyyyyMMdd(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	public static String getDateNumByNow() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.format(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getNow() {
		return new Date(System.currentTimeMillis());
	}

	public static Date getDateNow() {
		return getDateTimeByyyyyMMdd(getDateNumByNow());
	}

	public static Date getDateYesterday() {
		return subtract(getDateNow(), 1);
	}

	public static String getDateNumByYesterday() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			return sdf.format(new Date(getDateNow().getTime() - 1 * 24 * 60 * 60 * 1000));
		} catch (Exception e) {
			return null;
		}
	}

	public static Date get0LDate() {
		return new Date(0L);
	}

//	/**
//	 * 根据传入的日期字符串以及格式，产生一个Calendar对象
//	 * 
//	 * @param date
//	 *            日期字符串
//	 * @param pattern
//	 *            日期格式
//	 * @return Calendar
//	 * @throws java.text.ParseException
//	 * @throws ParseException
//	 *             当格式与日期字符串不匹配时抛出该异常
//	 * @throws java.text.ParseException
//	 */
//	public static Calendar convertToCalendar(String date, String pattern) throws ParseException {
//
//		Date d = DateUtils.parseDate(date, new String[] { pattern });
//
//		GregorianCalendar calendar = new GregorianCalendar();
//		calendar.setTime(d);
//		return calendar;
//	}

	// /**
	// * 计算传入的日期是星期几
	// *
	// * @param date
	// * String
	// * @return int
	// * @throws ParseException
	// */
	// public static int getDayOfWeek(String date) throws ParseException {
	// return DateHelper.convertToCalendar(date, "yyyy/MM/dd HH:mm:ss").get(
	// new GregorianCalendar().DAY_OF_WEEK);
	// }

	// /**
	// * 判断传入的时间是否是周末(周六or周日)
	// *
	// * @param date
	// * String 时间格式一定是yyyy-MM-dd HH:mm:ss
	// * @return boolean
	// */
	// public static boolean isWeekend(String date) throws ParseException {
	// Calendar calendar = new GregorianCalendar();
	// if ((DateHelper.getDayOfWeek(date) == calendar.SATURDAY)
	// || (DateHelper.getDayOfWeek(date) == calendar.SUNDAY)) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	public static Integer getDateInt() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DATE);

	}

	public static Date getOracleStr(String str) {
		String day = str.substring(0, 2);
		String mon = str.substring(3, 4);
		Integer monInt = (Integer.parseInt(mon) + 6) % 12 + 1;
		String year = "20" + str.substring(7, 9);
		String time = str.substring(10, 18).replace(".", ":");
		String timeStr = year + "-" + monInt + "-" + day + " " + time;
		return DateHelper.getFmtDateByStr(timeStr);
	}

	public static Date add(Date date, int amount) {
		try {
			SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, amount);

			return dft.parse(dft.format(calendar.getTime()));
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date addMonth(Date date, int amount) {
		try {
			SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, amount);

			return dft.parse(dft.format(calendar.getTime()));
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getDateBefore(Integer day) {
		try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(new Date());
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - day);
			return calendar.getTime();
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getLastMonthOfFistDay() {
		try {
			SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(getDateNow());
			calendar.add(Calendar.MONTH, -1);
			calendar.set(Calendar.DAY_OF_MONTH, 1);

			return dft.parse(dft.format(calendar.getTime()));
		} catch (ParseException e) {
			return null;
		}

	}

	public static Date subtract(Date date, int amount) {
		try {
			SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - amount);

			return dft.parse(dft.format(calendar.getTime()));
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("/", "");
	}

	public static String getFormatTime(String time) {
		try {
			StringBuffer fTime = new StringBuffer("");
			if (time.length() < 14) {
				time += "000000000000000";
			}
			fTime.append(time.substring(0, 4)).append("/");
			fTime.append(time.substring(4, 6)).append("/");
			fTime.append(time.substring(6, 8)).append(" ");
			fTime.append(time.substring(8, 10)).append(":");
			fTime.append(time.substring(10, 12)).append(":");
			fTime.append(time.substring(12, 14));
			return fTime.toString();
		} catch (Exception e) {
			return null;
		}
	}

	public static Date getFormatTimeToDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			return sdf.parse(DateHelper.getFormatTime(time));
		} catch (Exception e) {
			return null;
		}
	}

	// public static Date getTodayOrTomorrowTime(Date date ,boolean todayflag){
	// Calendar c=Calendar.getInstance();
	// c.setTime(date);
	// if(!todayflag){
	// c.set(Calendar.DATE, date.getDate()+1);
	// }
	// c.set(Calendar.HOUR_OF_DAY, 0);
	// c.set(Calendar.SECOND, 0);
	// c.set(Calendar.MINUTE, 0);
	// return c.getTime();
	// }

}
