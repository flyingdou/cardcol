package com.freegym.web.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.freegym.web.common.Constants;

public class SmsUtils {

	@SuppressWarnings("unused")
	public static String sendSms(final String mobiles, final String content) {
		try {
			final String strUrl = Constants.SMS_SERVICE_URL;
			final String strParam = "reg=" + Constants.SMS_SERVICE_USER + "&pwd=" + Constants.SMS_SERVICE_PASS + "&sourceAdd=&phone=" + mobiles + "&content="
					+ paraTo16(content);

			// 发送短信
			String strRes = postSend(strUrl, strParam);
			return "OK";
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

	public static String getSend(String strUrl, String param) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(strUrl + "?" + param);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("GET");
			connection.setUseCaches(false);
			connection.connect();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static String postSend(String strUrl, String param) {
		URL url = null;
		HttpURLConnection connection = null;

		try {
			url = new URL(strUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.connect();

			// POST方法时使用
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			out.writeBytes(param);
			out.flush();
			out.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer buffer = new StringBuffer();
			String line = "";
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			reader.close();
			return buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}

	}

	/**
	 * 转为16进制方法
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String paraTo16(String str) throws UnsupportedEncodingException {
		String hs = "";

		byte[] byStr = str.getBytes("UTF-8");
		for (int i = 0; i < byStr.length; i++) {
			String temp = "";
			temp = (Integer.toHexString(byStr[i] & 0xFF));
			if (temp.length() == 1)
				temp = "%0" + temp;
			else
				temp = "%" + temp;
			hs = hs + temp;
		}
		return hs.toUpperCase();

	}

	public static void main(String args[]) {
		SmsUtils.sendSms("13277957128,18971349557,18971345040", "您的验证码为a,请在a分钟内注册。【健身中国】");
		// final String sUrl =
		// "https://api.weibo.com/2/statuses/update.json?status=测试发微博&access_token=2.003CD_hCb9lY6Eec2c61a0b5SDhAqC";
		// try {
		// HttpClient http = new HttpClient();
		// PostMethod method = new PostMethod(sUrl);
		// int execMethod = http.executeMethod(method);
		// System.out.println(execMethod);
		/*
		 * final URL url = new URL(sUrl); final URLConnection conn =
		 * url.openConnection(); conn.setDoInput(true); conn.setDoOutput(true);
		 * InputStream is = conn.getInputStream(); byte[] buf = new
		 * byte[10*1024]; StringBuffer sb = new StringBuffer(); int size = 0;
		 * while ((size = is.read(buf, size, 10*1024))!= -1) { sb.append(new
		 * String(buf)); } is.close(); System.out.println(sb.toString());
		 */
		// } catch (MalformedURLException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}
}
