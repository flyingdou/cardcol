package com.freegym.web.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	public static Date[] getStartAndEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Date[] dateArr = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		dateArr[0] = cal.getTime();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dateArr[1] = cal.getTime();
		return dateArr;
	}
	public static Date[] getStartAndEndDate(Date startDate,Date endDate) {
		Date[] dateArr = new Date[2];
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		dateArr[0] = cal.getTime();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		dateArr[1] = cal.getTime();
		return dateArr;
	}
	public static Date getStartDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static Date getEndDate(Date date) {
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	public static int[] getDaysOfWeekNumbers(String dateString) {
		String[] spls = dateString.split("\\,");
		int[] days = new int[spls.length];
		for (int i = 0; i < days.length; i++) {
			days[i] = Integer.valueOf(spls[i].trim());
		}
		return days;
	}
	
	public static String getDateTimeZone(Date date,String str){
		if(null == date){
			return null;
		}
        SimpleDateFormat sdf = new SimpleDateFormat(str);
        TimeZone tz = TimeZone.getTimeZone("GMT+8");
        sdf.setTimeZone(tz);
        String dateStr = sdf.format(date);
        return dateStr;
	}
}
