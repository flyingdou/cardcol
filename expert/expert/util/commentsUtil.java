package expert.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.coobird.thumbnailator.Thumbnails;

public class commentsUtil {

	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getRandomString() {
		return "" + Math.round(Math.random() * 1000000);
	}

	/**
	 * 获得指定位数的随机数
	 * 
	 * @param count
	 * @return
	 */
	public static String getRandom(int count) {
		int param = 1;
		for (int i = 0; i < count; i++) {
			param *= 10;
		}
		return String.valueOf(Math.round(Math.random() * param));
	}

	/**
	 * 获取当前日期加上指定位数的随机数
	 * 
	 * @param count
	 * @return
	 */
	public static String getRandomByDate(int count) {
		int param = 1;
		for (int i = 0; i < count; i++) {
			param *= 10;
		}
		return sdf.format(new Date()) + String.valueOf(Math.round(Math.random() * param));
	}

	public static String getNewFileName(String fileName) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String newFileName = sdf.format(new Date()) + commentsUtil.getRandomString();
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		return newFileName + suffix;
	}

	public static String format(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
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
	 * MD5
	 * 
	 * @param buffer
	 * @return
	 */
	public static String MD5(String buffer) {
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
	 * 读取请求中流的参数
	 * 
	 * @param request
	 * @return
	 */
	public static String readStream(HttpServletRequest request) {
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
			String resultStr = new String(outSteam.toByteArray(), "utf-8");
			return resultStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * emoji表情替换
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
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
	 * 图片压缩
	 * @param filePath
	 */
	public static void compress(String filePath){
        try {
        	//图片尺寸不变，压缩图片文件大小outputQuality实现,参数1为最高质量
			 Thumbnails.of(filePath).scale(1f).outputQuality(0.25f).toFile(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
