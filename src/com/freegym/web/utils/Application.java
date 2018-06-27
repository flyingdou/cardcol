package com.freegym.web.utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.freegym.web.basic.Member;

public class Application {

	private static Map<String, Member> containers = new HashMap<String, Member>();
	public static final String numberChar = "0123456789";

	public static Member get(String key) {
		return containers.get(key);
	}

	public static void put(String key, Member user) {
		containers.put(key, user);
	}

	public static void clear() {
		containers.clear();
	}

	public static String getRandom() {
		Long seed = System.currentTimeMillis();// 获得系统时间，作为生成随机数的种子
		StringBuffer sb = new StringBuffer();// 装载生成的随机数
		Random random = new Random(seed);// 调用种子生成随机数
		for (int i = 0; i < 10; i++) {
			sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
		}
		return sb.toString();
	}

	public static boolean compare(String sessionId, String username) {
		Member u = containers.get(sessionId);
		if (u == null) return false;
		if (u.getName().equals(username)) return true;
		return false;
	}

	@SuppressWarnings({ "unused", "hiding" })
	public static void main(String[] args) {
		try {
			URL url = new URL(
					"https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=100332016&client_secret=e0501fa57186c7504b0439ad36b5a1b1&code=86CFADC868AC395DA0930539A513A9E8&redirect_uri=http://www.cardcol.com/qqlogin.asp");
			// URLConnection conn = url.openConnection();
			// conn.setDoInput(true);
			// InputStream is = conn.getInputStream();
			// byte[] buff = new byte[20*1024];
			// int len = is.read(buff);
			// String string = new String(buff, 0, len);
			// String accessToken = string.split("&")[0].split("=")[1];
			// System.out.println(string + " = " + accessToken);
			//
			// url = new URL("https://graph.qq.com/oauth2.0/me?access_token=" +
			// accessToken);
			// conn = url.openConnection();
			// conn.setDoInput(true);
			// is = conn.getInputStream();
			// buff = new byte[2000*1024];
			// len = is.read(buff);
			// String msg = new String(buff, 0, len);
			// System.out.println(msg);
			//
			String callback = "callback( {\"client_id\":\"100332016\",\"openid\":\"D0445C58D6FE5A323D6B4F7CE3A6DC79\"} );";
			System.out.println(callback + " = " + callback.length());
			int index = callback.indexOf("openid");
			String openId = callback.substring(index + 9, callback.length() - 5);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
