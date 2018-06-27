package com.freegym.web.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.handsome.ip.IPLocation;
import com.handsome.ip.IPSeeker;

public class IpUtils {
	public static String getCardcolCity(HttpServletRequest request) {
		String city = "";
		String ip = request.getRemoteAddr();
		if (ip.equals("127.0.0.1") || ip.equals("localhost") || ip.equals("0:0:0:0:0:0:0:1")) {
			city = "武汉市";
		} else {
			city = getCity(request);
		}
		return city;
	}

	public static String getCity(HttpServletRequest request) {
		String city = "";
		String tomcatRoot = request.getSession().getServletContext().getRealPath("/");
		IPSeeker ips = new IPSeeker("qqwry.dat", tomcatRoot + "\\ipdata");
		String ipAddr = getIpAddr(request);
		IPLocation ipLocation = ips.getIPLocation(ipAddr);
		String country = ipLocation.getCountry();
		if (country.indexOf("省") != -1) {
			city = country.substring(country.indexOf("省") + 1);
		} else {
			city = country;
		}
		return city;
	}

	private static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
															// = 15
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
}
