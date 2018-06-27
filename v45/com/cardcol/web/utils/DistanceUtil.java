package com.cardcol.web.utils;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DistanceUtil {
	// 根据经纬度算距离
	public static Double getDistanceByLatAndLng(Double Mlat, Double Mlng, Double lat, Double lng) {
		if (Mlat == null&&Mlng==null) {
			return new Double(-1);
		}
		// 两点间距离 m
		double d = Math.acos(Math.sin(Mlat) * Math.sin(lat) + Math.cos(Mlat) * Math.cos(lat) * Math.cos(lng - Mlng))
				* new Double(6378137);
		return d;
	}
	private static final Pattern UNICODE_HEX_PATTERN = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
	private static final Pattern UNICODE_OCT_PATTERN = Pattern.compile("\\\\([0-7]{3})");

	
	public static String encodeToNonLossyAscii(String original) {
	    Charset asciiCharset = Charset.forName("US-ASCII");
	    if (asciiCharset.newEncoder().canEncode(original)) {
	        return original;
	    }
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < original.length(); i++) {
	        char c = original.charAt(i);
	        if (c < 128) {
	            stringBuffer.append(c);
	        } else if (c < 256) {
	            String octal = Integer.toOctalString(c);
	            stringBuffer.append("\\");
	            stringBuffer.append(octal);
	        } else {
	            String hex = Integer.toHexString(c);
	            stringBuffer.append("\\u");
	            stringBuffer.append(hex);
	        }
	    }
	    return stringBuffer.toString();
	}
	  
	public static String decodeFromNonLossyAscii(String original) {
		if(original ==null) return"";
	    Matcher matcher = UNICODE_HEX_PATTERN.matcher(original);
	    StringBuffer charBuffer = new StringBuffer(original.length());
	    while (matcher.find()) {
	        String match = matcher.group(1);
	        char unicodeChar = (char) Integer.parseInt(match, 16);
	        matcher.appendReplacement(charBuffer, Character.toString(unicodeChar));
	    }
	    matcher.appendTail(charBuffer);
	    String parsedUnicode = charBuffer.toString();

	    matcher = UNICODE_OCT_PATTERN.matcher(parsedUnicode);
	    charBuffer = new StringBuffer(parsedUnicode.length());
	    while (matcher.find()) {
	        String match = matcher.group(1);
	        char unicodeChar = (char) Integer.parseInt(match, 8);
	        matcher.appendReplacement(charBuffer, Character.toString(unicodeChar));
	    }
	    matcher.appendTail(charBuffer);
	    return charBuffer.toString();
	}
	
}
