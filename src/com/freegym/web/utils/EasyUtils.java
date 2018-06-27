package com.freegym.web.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;

public class EasyUtils {

	/**
	 * 计数
	 */
	public static int count;

	/**
	 * 订单编号
	 */
	public static String orderNo;

	/**
	 * 订单类型
	 */
	public static String orderType;

	/**
	 * 订单分享
	 */
	public static Map<String, Object> shareOrder;

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateFormat(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 日期格式化
	 * 
	 * @param Map
	 * @return
	 */
	public static Map<String, Object> dateFormat(Map<String, Object> map, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			if (map.get(key) instanceof Date) {
				map.put(key, sdf.format(map.get(key)));
			}
		}
		return map;
	}

	/**
	 * 日期格式化
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> dateFormat(List<Map<String, Object>> list, String format) {
		for (Map<String, Object> map : list) {
			dateFormat(map, format);
		}
		return list;
	}

	/**
	 * 把String转成Date
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date formatStringToDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 保留两位小数
	 * 
	 * @param number
	 * @return
	 */
	public static String decimalFormat(double number) {
		DecimalFormat df = new DecimalFormat("#.00");
		if (number < 1) {
			return "0" + df.format(number);
		}
		return df.format(number);
	}
	
	/**
	 * 保留两位小数
	 * 
	 * @param number
	 * @return
	 */
	public static String decimalFormat(double number, String formart) {
		DecimalFormat df = new DecimalFormat(formart);
		if (number < 1) {
			return "0" + df.format(number);
		}
		return df.format(number);
	}

	/**
	 * 保留两位小数
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, Object> decimalFormat(Map<String, Object> map) {
		DecimalFormat df = new DecimalFormat("#.00");
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			if (map.get(key) instanceof Integer) {
				if (Integer.parseInt(String.valueOf(map.get(key))) == 0) {
					map.put(key, "0.00");
				} else {
					map.put(key, df.format(map.get(key)));
				}
			} else if (map.get(key) instanceof Double) {
				if (Double.parseDouble(String.valueOf(map.get(key))) < 1) {
					map.put(key, "0" + df.format(map.get(key)));
				} else {
					map.put(key, df.format(map.get(key)));
				}
			}
		}
		return map;
	}

	/**
	 * 保留两位小数
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> decimalFormat(List<Map<String, Object>> list) {
		for (Map<String, Object> map : list) {
			decimalFormat(map);
		}
		return list;
	}

	/**
	 * 日期和保留两位小数
	 * 
	 * @param list
	 * @return
	 */
	public static List<Map<String, Object>> dateANDdecimalFormat(List<Map<String, Object>> list, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		DecimalFormat df = new DecimalFormat("#.00");
		for (Map<String, Object> map : list) {
			Set<String> keys = map.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				if (map.get(key) instanceof Integer) {
					if (Integer.parseInt(String.valueOf(map.get(key))) == 0) {
						map.put(key, "0.00");
					} else {
						map.put(key, df.format(map.get(key)));
					}
				} else if (map.get(key) instanceof Double) {
					if (Double.parseDouble(String.valueOf(map.get(key))) < 1) {
						map.put(key, "0" + df.format(map.get(key)));
					} else {
						map.put(key, df.format(map.get(key)));
					}
				} else if (map.get(key) instanceof Date) {
					map.put(key, sdf.format(map.get(key)));
				}
			}
		}
		return list;
	}

	/**
	 * 获取随机数字编号(6位)
	 * 
	 * @return
	 */
	public static String getRandomName() {
		return dateFormat(new Date(), "yyyyMMdd") + Math.round(Math.random() * 1000000);
	}

	/**
	 * 获取随机数字编号(指定位数)
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomName(int length) {
		int Multiple = 1;
		for (int i = 0; i < length; i++) {
			Multiple *= 10;
		}
		return dateFormat(new Date(), "yyyyMMdd") + Math.round(Math.random() * Multiple);
	}

	/**
	 * MD5加密
	 * 
	 * @param buffer
	 * @return
	 */
	public final static String MD5(String buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer.getBytes("UTF-8"));
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 时间相加的方法
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDate(Date date, long day) {
		// 得到指定日期的毫秒数
		long time = date.getTime();
		// 要加上的天数转换成毫秒数
		day = day * 24 * 60 * 60 * 1000;
		// 相加得到新的毫秒数
		time += day;
		// 将毫秒数转换成日期
		return new Date(time);
	}

	/**
	 * 获取客户端公网ip
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
			// 多次反向代理后会有多个ip值，第一个ip才是真实ip
			if (ip.indexOf(",") != -1) {
				ip = ip.split(",")[0];
			}
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			System.out.println("Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			System.out.println("WL-Proxy-Client-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			System.out.println("HTTP_CLIENT_IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			System.out.println("HTTP_X_FORWARDED_FOR ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
			System.out.println("X-Real-IP ip: " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			System.out.println("getRemoteAddr ip: " + ip);
		}
		return ip;
	}

	/**
	 * 从二进制流中读取参数
	 * 
	 * @param request
	 * @return
	 */
	public static String getInputStreamParam(HttpServletRequest request) {
		try {
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			return new String(outSteam.toByteArray(), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * emoji表情替换
	 * 
	 */
	public static String cutStringEmoji(String str) throws Exception {
		char[] chr = str.toCharArray();
		String strx = "";
		for (int i = 0; i < chr.length; i++) {
			if (((chr[i] >= 0x4e00) && (chr[i] <= 0x9fbb))
					|| ((chr[i] >= 'A' && chr[i] <= 'Z') || (chr[i] >= 'a' && chr[i] <= 'z'))
					|| (chr[i] > 47 && chr[i] < 58)) {
				strx += String.valueOf(chr[i]);
			} else {
				chr[i] = '*';
				strx += String.valueOf(chr[i]);
			}
		}

		return strx;
	}

	/**
	 * 图片压缩
	 * 
	 * @param filePath
	 *            图片路径
	 */
	public static void compress(String filePath) {
		try {
			// 图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
			Thumbnails.of(filePath).scale(1f).outputQuality(0.25f).toFile(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从解密数据中获取需要的字段
	 * 
	 * @param AESstring
	 *            解密后的数据
	 * @param target
	 *            需要获取的字段
	 * @return
	 */
	public static String getTargetFromAES(String AESstring, String target) {
		String[] douStrx = AESstring.split(",");
		for (String string : douStrx) {
			if (string.indexOf(target) > 0) {
				string = string.replace("'", "");
				string = string.replace("\"", "");
				String[] doux = string.split(":");
				return doux[1];
			}
		}
		return "";
	}

	/**
	 * String 转换成Integer数组
	 * 
	 * @param obj
	 * @return
	 */
	public static Integer[] StringConvert2Integer(String obj) {
		String[] strings = obj.toString().split(",");
		Integer[] objTarget = new Integer[strings.length];
		for (int i = 0; i < objTarget.length; i++) {
			objTarget[i] = Integer.valueOf(strings[i]);
		}
		return objTarget;
	}

	/**
	 * String 转换成Double数组
	 * 
	 * @param obj
	 * @return
	 */
	public static Double[] StringConvert2Double(String obj) {
		String[] strings = obj.toString().split(",");
		Double[] objTarget = new Double[strings.length];
		for (int i = 0; i < objTarget.length; i++) {
			objTarget[i] = Double.valueOf(strings[i]);
		}
		return objTarget;
	}

	public static Integer[] bubbleSort(Integer[] objTarget, String orderType) {
		if ("desc".equals(orderType)) {
			// 倒序
			for (int i = 0; i < objTarget.length - 1; i++) {
				for (int j = 0; j < objTarget.length - i - 1; j++) {
					if (objTarget[j] < objTarget[j + 1]) {
						Integer temp = objTarget[j];
						objTarget[j] = objTarget[j + 1];
						objTarget[j + 1] = temp;
					}
				}
			}

		} else {
			// 正序
			for (int i = 0; i < objTarget.length - 1; i++) {
				for (int j = 0; j < objTarget.length - i - 1; j++) {
					if (objTarget[j] > objTarget[j + 1]) {
						Integer temp = objTarget[j];
						objTarget[j] = objTarget[j + 1];
						objTarget[j + 1] = temp;
					}
				}
			}
		}

		return objTarget;
	}

	/**
	 * 时间相减，返回两个时间之间的秒数
	 * 
	 * @param frist
	 * @param seconed
	 * @return
	 * @throws ParseException
	 */
	public static Integer timeDiff(Date frist, Date seconed) throws ParseException {
		Long sLong = seconed.getTime();
		Long fLong = frist.getTime();
		Long diff = sLong - fLong;

		return Integer.valueOf(String.valueOf(diff / 1000));
	}

	/**
	 * 获取指定长度的平均值，传0时，默认给所有的平均值
	 * 
	 * @param inte
	 * @param lenght
	 * @return
	 */
	public static Integer avgInteger(Integer[] inte, int lenght) {
		// 计入计算的长度
		int xlength = 0;
		// 计入计算的总值
		int count = 0;
		// 数组长度大于指定的长度
		if (lenght < inte.length) {
			if (lenght > 0) {
				xlength = lenght;
				count = 0;
				for (int i = 0; i < lenght; i++) {
					if (inte[i] < 1) {
						// 取出的值小于1的不计入计算
						xlength--;
						continue;
					}
					count += inte[i];

				}

			}

		} else {
			// 数组长度小于指定长度
			count = 0;
			xlength = inte.length;
			for (int i = 0; i < inte.length; i++) {
				if (inte[i] < 1) {
					xlength--;
					continue;
				}
				count += inte[i];
			}
		}
		return count / (xlength == 0 ? inte.length : xlength);
	}

	// 由出生日期获得年龄
	public static int getAge(Date birthDay) throws Exception {
		Calendar cal = Calendar.getInstance();

		if (cal.before(birthDay)) {
			throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthDay);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth)
					age--;
			} else {
				age--;
			}
		}
		return age;
	}

	/**
	 * 计算心率区间值
	 * 
	 * @param age
	 *            年龄
	 * @param heartInt
	 *            静心率
	 * @param listx
	 *            区间list
	 * @param heartRateInt
	 *            离线心率数组
	 * @param ringTimeType
	 *            离线手环记录时间单位
	 * @return
	 */
	public static Map<String, Object> getDt(Integer age, Integer heartInt, List<List<Double>> listx,
			Integer[] heartRateInt, String ringTimeType) {
		Map<String, Object> listMap = new HashMap<String, Object>();
		for (int xx = 0; xx < listx.size(); xx++) {
			List<Double> rates = (List<Double>) listx.get(xx);
			List<Integer> dt = new ArrayList<Integer>();
			for (int i = 0; i < rates.size(); i++) {
				Double dd = (220 - age - heartInt) * rates.get(i);
				Double dou = heartInt.doubleValue() + dd;
				dou = Math.floor(dou);
				dt.add(dou.intValue());
			}

			int count = 0;
			String time = "";
			for (int i = 0; i < heartRateInt.length; i++) {
				// 一个比例
				if (dt.size() == 1) {
					if (heartRateInt[i] < dt.get(0)) {
						count += 1;
					}

				}
				// 两个比例
				if (dt.size() == 2) {
					if (heartRateInt[i] >= dt.get(0) && heartRateInt[i] < dt.get(1)) {
						count += 1;
					}
				}
				// 以分钟为单位
				if ("minute".equals(ringTimeType)) {
					time = count + "'";
				}

				// 以秒为单位
				if ("second".equals(ringTimeType)) {
					if (count < 60) {
						time = "00'" + count + "''";
					} else {
						time = count % 60 == 0 ? (count / 60 + "'") : ((count / 60) + "'" + (count % 60) + "''");
					}
				}
			}
			Map<String, Object> mapx = new HashMap<String, Object>();
			mapx.put("count", count);
			mapx.put("area" + xx, dt);
			mapx.put("time", time);
			listMap.put("area" + xx, mapx);
		}

		return listMap;
	}

	/**
	 * 数组转字符串
	 */
	public static String getStr(String[] strx, String flag) {
		String res = "";
		for (String string : strx) {
			res += string + flag;
		}
		res = res.substring(0, res.lastIndexOf(flag));
		return res;
	}

}
