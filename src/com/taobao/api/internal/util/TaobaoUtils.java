package com.taobao.api.internal.util;

import java.io.IOException;
import java.net.InetAddress;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.taobao.api.ApiException;
import com.taobao.api.Constants;
import com.taobao.api.TaobaoResponse;
import com.taobao.api.internal.parser.json.ObjectJsonParser;
import com.taobao.api.internal.util.json.JSONReader;
import com.taobao.api.internal.util.json.JSONValidatingReader;
import com.taobao.api.internal.util.json.JSONWriter;

/**
 * 系统工具类。
 * 
 * @author carver.gu
 * @since 1.0, Sep 12, 2009
 */
public abstract class TaobaoUtils {

	private static String intranetIp;

	private TaobaoUtils() {}

	/**
	 * 给TOP请求签名。
	 * 
	 * @param requestHolder 所有字符型的TOP请求参数
	 * @param secret 签名密钥
	 * @param signMethod signMethod 签名方法，目前支持：空（老md5)、md5, hmac_md5三种
	 * @return 签名
	 */
	public static String signTopRequest(RequestParametersHolder requestHolder, String secret, String signMethod) throws IOException {
		return signTopRequest(requestHolder.getAllParams(), null, secret, signMethod);
	}

	/**
	 * 给TOP请求签名。
	 * 
	 * @param params 所有字符型的TOP请求参数
	 * @param body 请求主体内容
	 * @param secret 签名密钥
	 * @param signMethod 签名方法，目前支持：空（老md5)、md5, hmac_md5三种
	 * @return 签名
	 */
	public static String signTopRequest(Map<String, String> params, String body, String secret, String signMethod) throws IOException {
		// 第一步：检查参数是否已经排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);

		// 第二步：把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder();
		if (Constants.SIGN_METHOD_MD5.equals(signMethod)) {
			query.append(secret);
		}
		for (String key : keys) {
			String value = params.get(key);
			if (StringUtils.areNotEmpty(key, value)) {
				query.append(key).append(value);
			}
		}

		// 第三步：把请求主体拼接在参数后面
		if (body != null) {
			query.append(body);
		}

		// 第四步：使用MD5/HMAC加密
		byte[] bytes;
		if (Constants.SIGN_METHOD_HMAC.equals(signMethod)) {
			bytes = encryptHMAC(query.toString(), secret);
		} else {
			query.append(secret);
			bytes = encryptMD5(query.toString());
		}

		// 第五步：把二进制转化为大写的十六进制
		return byte2hex(bytes);
	}
	
	/**
     * 给TOP带body体的请求签名，适用于TOP批量API和奇门API的请求签名。
     * 
     * @param requestHolder 所有字符型的TOP请求参数
     * @param body 请求body体
     * @param secret 签名密钥
     * @param isHmac 是否为HMAC方式加密
     * @return 签名
     */
    public static String signTopRequestWithBody(RequestParametersHolder requestHolder, String body, String secret, String signMethod) throws IOException {
        Map<String, String> params = requestHolder.getAllParams();
        return signTopRequest(params, body, secret, signMethod);
    }

	private static byte[] encryptHMAC(String data, String secret) throws IOException {
		byte[] bytes = null;
		try {
			SecretKey secretKey = new SecretKeySpec(secret.getBytes(Constants.CHARSET_UTF8), "HmacMD5");
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			bytes = mac.doFinal(data.getBytes(Constants.CHARSET_UTF8));
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse.toString());
		}
		return bytes;
	}

	/**
	 * 对字符串采用UTF-8编码后，用MD5进行摘要。
	 */
	public static byte[] encryptMD5(String data) throws IOException {
		return encryptMD5(data.getBytes(Constants.CHARSET_UTF8));
	}

	/**
	 * 对字节流进行MD5摘要。
	 */
	public static byte[] encryptMD5(byte[] data) throws IOException {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			bytes = md.digest(data);
		} catch (GeneralSecurityException gse) {
			throw new IOException(gse.toString());
		}
		return bytes;
	}

	/**
	 * 把字节流转换为十六进制表示方式。
	 */
	public static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

	/**
	 * 清除字典中值为空的项。
	 * 
	 * @param <V> 泛型
	 * @param map 待清除的字典
	 * @return 清除后的字典
	 */
	public static <V> Map<String, V> cleanupMap(Map<String, V> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}

		Map<String, V> result = new HashMap<String, V>(map.size());
		Set<Entry<String, V>> entries = map.entrySet();

		for (Entry<String, V> entry : entries) {
			if (entry.getValue() != null) {
				result.put(entry.getKey(), entry.getValue());
			}
		}

		return result;
	}

	/**
	 * 把JSON字符串转化为对象结构。
	 * 
	 * @param json JSON字符串
	 * @return 对象结构，一般为Map
	 */
	public static Object jsonToObject(String json) {
		JSONReader jr = new JSONValidatingReader();
		return jr.read(json);
	}

	/**
	 * 把对象结构转换为JSON字符串。
	 * 
	 * @param object 对象结构
	 * @return JSON字符串
	 */
	public static String objectToJson(Object object) {
		JSONWriter writer = new JSONWriter(false, true);
		return writer.write(object);
	}

	/**
	 * 把对象结构转换为XML字符串。
	 * 
	 * @param object 对象结构
	 * @return XML字符串
	 */
	public static String objectToXml(Object object) {
		XmlWriter writer = new XmlWriter();
		return writer.write(object);
	}

	/**
	 * 把JSON字符串解释为对象结构。
	 * 
	 * @param <T> API响应类型
	 * @param json JSON字符串
	 * @param clazz API响应类
	 * @return API响应对象
	 */
	public static <T extends TaobaoResponse> T parseResponse(String json, Class<T> clazz) throws ApiException {
		ObjectJsonParser<T> parser = new ObjectJsonParser<T>(clazz);
		T rsp = parser.parse(json);
		rsp.setBody(json);
		return rsp;
	}

	/**
	 * 获取本机的局域网IP。
	 */
	public static String getIntranetIp() {
		if (intranetIp == null) {
			try {
				intranetIp = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception e) {
				intranetIp = "127.0.0.1";
			}
		}
		return intranetIp;
	}

}
