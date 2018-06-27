package com.freegym.web.utils;

import java.util.HashMap;
import java.util.Map;

public class WebConstant implements SessionConstant {
	public static final Map<String, String> typeMap = new HashMap<String, String>();

	static {
		typeMap.put("1", "健身卡");
		typeMap.put("2", "健身挑战");
		typeMap.put("3", "健身计划");
		typeMap.put("4", "场地预订");
		typeMap.put("5", "团体课程");
		typeMap.put("6", "智能计划");
		typeMap.put("7", "E卡通");
	}
	
	public static String getType(String key) {
		return typeMap.get(key);
	}
}
