package com.freegym.web.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JSONUtils {
	private static void exchange(JSONArray jarr, int p, int q) {
		if (p == q) return;
		Object temp = jarr.get(p);
		jarr.set(p, jarr.get(q));
		jarr.set(q, temp);
	}

	public static void quickSort(JSONArray jarr, int lo, int hi, boolean ascend, String key) {
		if (lo >= hi) return;
		JSONObject obj = (JSONObject) jarr.get(lo);
		Double val = obj.getDouble(key);
		int leftIdx = lo, rightIdx = hi - 1;
		int i = lo + 1;
		while (i <= rightIdx) {
			final Double nextVal = ((JSONObject) jarr.get(i)).getDouble(key);
			int compare = nextVal.compareTo(val);
			if (compare == 0) i++;
			else if (compare < 0 == ascend) exchange(jarr, leftIdx++, i++);
			else exchange(jarr, rightIdx--, i);
		}
		quickSort(jarr, lo, leftIdx - 1, ascend, key);
		quickSort(jarr, rightIdx + 1, hi, ascend, key);
	}

	public static void bubbleSort(JSONArray jarr, boolean ascend, String key) {
		for (int i = jarr.size() - 1; i > 0; i--) {
			for (int j = 0; j < i; j++) {
				JSONObject obj = jarr.getJSONObject(j);
				final Double v1 = obj.getDouble(key);
				obj = jarr.getJSONObject(j + 1);
				final Double v2 = obj.getDouble(key);
				if (v1.compareTo(v2) > 0) exchange(jarr, j, j + 1);
			}
		}
	}
}