package com.freegym.web.order;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNo {
	public synchronized static String createNo() {
		String dateStr = "";
		java.text.SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		dateStr = sdf.format(new Date());
		return dateStr;
	}

	public static void main(String[] args) {
		System.out.println(CreateNo.createNo());
	}
}
