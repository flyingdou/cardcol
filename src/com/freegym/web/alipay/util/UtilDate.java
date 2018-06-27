package com.freegym.web.alipay.util;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/* *
 *����UtilDate
 *���ܣ��Զ��嶩����
 *��ϸ�������࣬����������ȡϵͳ���ڡ�������ŵ�
 *�汾��3.2
 *���ڣ�2011-03-17
 *˵����
 *���´���ֻ��Ϊ�˷����̻����Զ��ṩ��������룬�̻����Ը���Լ���վ����Ҫ�����ռ����ĵ���д,����һ��Ҫʹ�øô��롣
 *�ô������ѧϰ���о�֧�����ӿ�ʹ�ã�ֻ���ṩһ���ο���
 */
public class UtilDate {

	/** ������ʱ����(���»���) yyyyMMddHHmmss */
	public static final String dtLong = "yyyyMMddHHmmss";

	/** ����ʱ�� yyyy-MM-dd HH:mm:ss */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";

	/** ������(���»���) yyyyMMdd */
	public static final String dtShort = "yyyyMMdd";

	/**
	 * ����ϵͳ��ǰʱ��(��ȷ������),��Ϊһ��Ψһ�Ķ������
	 * 
	 * @return ��yyyyMMddHHmmssΪ��ʽ�ĵ�ǰϵͳʱ��
	 */
	public static String getOrderNum() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtLong);
		return df.format(date);
	}

	/**
	 * ��ȡϵͳ��ǰ����(��ȷ������)����ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateFormatter() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(simple);
		return df.format(date);
	}

	/**
	 * ��ȡϵͳ����������(��ȷ����)����ʽ��yyyyMMdd
	 * 
	 * @return
	 */
	public static String getDate() {
		Date date = new Date();
		DateFormat df = new SimpleDateFormat(dtShort);
		return df.format(date);
	}

	/**
	 * ����������λ��
	 * 
	 * @return
	 */
	public static String getThree() {
		Random rad = new Random();
		return rad.nextInt(1000) + "";
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(Date date1, Date date2) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date1);
		long time1 = cal.getTimeInMillis();
		cal.setTime(date2);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 指定日期加上指定天数
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date addDate(Date d, long day) {
		long time = d.getTime();
		day = day * 24 * 60 * 60 * 1000;
		time += day;
		return new Date(time);
	}
}
