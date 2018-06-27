package ecartoon.wx.test;

import java.io.UnsupportedEncodingException;

public class getEncode {

	public static String getEncoding(String str) {
		String[] codes = { "ISO-8859-1", "GBK", "UTF-8" };
		String lastCode = null;
		for (String code : codes) {
			try {
				if (str.equals(new String(str.getBytes(), "UTF-8"))) {
					lastCode = "UTF-8";
					break;
				}
				if (str.equals(new String(str.getBytes(code), code))) {
					lastCode = code;
					break;
				}
			} catch (Exception exception) {
			}
		}
		return lastCode;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String aa = new String("哈哈".getBytes(), "GBK");
		System.out.println(aa);
		System.out.println(getEncoding(aa));
	}
}