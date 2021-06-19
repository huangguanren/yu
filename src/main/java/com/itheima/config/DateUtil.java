package com.itheima.config;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DateUtil {

	private static final String DEFAULT_PATTERN = "yyyy-MM-dd";
	// yyyy/MM/dd HH:mm:ss

	private static ConcurrentMap<String, DateTimeFormatter> map = new ConcurrentHashMap<>();

	private static DateTimeFormatter getSdf(String pattern) {
		DateTimeFormatter sdf = null;
		if (pattern == null) {
			sdf = map.get(DEFAULT_PATTERN);
			if (sdf == null) {
				sdf = DateTimeFormatter.ofPattern(DEFAULT_PATTERN);
				map.put(DEFAULT_PATTERN, sdf);
			}
		} else {
			sdf = map.get(pattern);
			if (sdf == null) {
				sdf = DateTimeFormatter.ofPattern(pattern);
				map.put(pattern, sdf);
			}
		}
		return sdf;
	}

	public static LocalDateTime date2LocalDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDateTime();
	}

	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_PATTERN);
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return date2LocalDateTime(date).format(getSdf(pattern));
	}

	public static Date localDateTime2Date(LocalDateTime localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atZone(zoneId);
		return Date.from(zdt.toInstant());
	}

	public static Date localDate2Date(LocalDate localDateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = localDateTime.atStartOfDay(zoneId);
		return Date.from(zdt.toInstant());
	}

	public static Date parseDate(String date) {
		return parseDate(date, null);
	}

	public static Date parseDate(String date, String pattern) {
		boolean withHouer = false;
		if (pattern != null && pattern.toUpperCase().contains("H")) {
			withHouer = true;
		}
		DateTimeFormatter formatter = getSdf(pattern);
		if (withHouer) {
			return localDateTime2Date(LocalDateTime.parse(date, formatter));
		} else {
			return localDate2Date(LocalDate.parse(date, formatter));
		}
	}

	/**
	 * 计算时间差
	 * 
	 * @param startTime 开始时间
	 * @param entTime   结束时间
	 * @param type      (1: 天数 2: 小时 3:分钟 4:秒)
	 * @return
	 */
	public static Long dateDiff(Date startTime, Date entTime, Integer type) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = entTime.getTime() - startTime.getTime();
		if (type == 1) {
			// 计算差多少天
			return diff / nd;
		} else if (type == 2) {
			// 计算差多少小时
			return diff / nh;
		} else if (type == 3) {
			// 计算差多少分钟
			return diff / nm;
		} else if (type == 4) {
			// 计算差多少秒//输出结果
			return diff / ns;
		}
		throw new RuntimeException("参数出现错误");
	}

	/**
	 * 将日期转为 几天前 几月前
	 * 
	 * @param date
	 * @return
	 */
	public static String getShortTime(Date date) {
		String shortstring = null;
		long now = Calendar.getInstance().getTimeInMillis();
		long deltime = (now - date.getTime()) / 1000;
		if (deltime > 365 * 24 * 60 * 60) {
			shortstring = (int) (deltime / (365 * 24 * 60 * 60)) + "年前";
		} else if (deltime > 24 * 60 * 60) {
			shortstring = (int) (deltime / (24 * 60 * 60)) + "天前";
		} else if (deltime > 60 * 60) {
			shortstring = (int) (deltime / (60 * 60)) + "小时前";
		} else if (deltime > 60) {
			shortstring = (int) (deltime / (60)) + "分钟前";
		} else if (deltime > 1) {
			shortstring = deltime + "秒前";
		} else {
			shortstring = "1秒前";
		}
		return shortstring;
	}

	public static Date addDate(Date date, Integer days) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.DATE, days);
		return ca.getTime();
	}

	public static Date addMinutes(Date date, Integer minutes) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MINUTE, minutes);
		return ca.getTime();
	}

	public static int getDay(Date DateTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(DateTime);
		// 今天是这个星期的第几天
		int week = c.get(Calendar.DAY_OF_WEEK);
		return week;
	}

	public static String getWeek(Date date) {
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week_index < 0) {
			week_index = 0;
		}
		return weeks[week_index];
	}

	public static String getYesterday() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = formatDate(cal.getTime());
		return yesterday;
	}

	public static Integer getClYear(Date createDate) {
		LocalDate today = LocalDate.now();
		LocalDateTime localDateTime = date2LocalDateTime(createDate);
		LocalDate birthDate = LocalDate.of(localDateTime.getYear(), localDateTime.getMonth(),
				localDateTime.getDayOfMonth());
		Period p = Period.between(birthDate, today);
		if (p.getYears() <= 0) {
			return 1;
		}
		return p.getYears();
	}

	public static Integer getWeekNum(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

}
