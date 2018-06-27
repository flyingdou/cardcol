package ecartoon.wx.util;

import java.util.Random;

import com.freegym.web.utils.EasyUtils;

public class MathRandom4dou {

	
	/**
	 * 产生随机小数
	 * min 最小值 0.01
	 * max 最大值 1
	 * 随机数个数
	 */
	
//	public String[] getCutMoney(double min, double max, int cnt){
//		// 用于验证总和
//		double sum =  0; 
//		// 每次产生的数据
//		double one = 0; 
//		
//		// 初始处理
//		min = min * 100;
//		max = max * 100;
//		
//		String[] rates = new String [cnt];
//		
//		if (cnt == 1) {
//			rates[0] = max/100 + "";
//			return rates;
//		}
//		
//		for (int i = 0; i < cnt; i ++) {
//			if ( i < cnt - 1 ) {
//				// min~max 指定小数位的随机数
//				Random rand = new Random();
//				one = rand.nextInt(99) + 1;
//				one = one * (1/Double.valueOf(cnt/2));
//				sum += one;
//				while (sum >= max) {
//					// 超出范围的值，清除掉
//					sum -= one;
//					one = rand.nextInt(99) + 1;
//					one = one * (1/Double.valueOf(cnt/2));
//					sum += one;
//				}
//				
//			} else {
//				one = max - sum;
//			}
//			rates[i] = EasyUtils.decimalFormat(one/100, "#.0000") + "";
//		}
//		return rates;
//	}
	
	
	/**
	 * 产生随机小数
	 * min 最小值 0.01
	 * max 最大值 1
	 * 随机数个数
	 */
	public String[] getCutMoney(double min, double max, int cnt){
		// 每次产生的数据
		double one = 0; 
		
		// 初始处理
		min = min * 100;
		max = max * 100;
		
		String[] rates = new String [cnt];
		
		if (cnt == 1) {
			rates[0] = max/100 + "";
			return rates;
		}
		
		for (int i = 0; i < cnt; i ++) {
				// min~max 指定小数位的随机数
				Random rand = new Random();
				if (max <= 1) {
					rates[i] = "0";
					continue;
				}
				one = rand.nextInt((int)(max - 1)) + 1;
				one = one * (i + 1) / cnt;
			    if (i == cnt - 1 ) {
			    	one = max;
			    }
			    rates[i] = EasyUtils.decimalFormat(one/100, "#.0000") + "";
			    max -= one;
		}
		return rates;
	}
	
	
	/**
	 * 产生随机小数
	 * min 最小值 0.01
	 * max 最大值 1
	 * 随机数个数
	 */
	
	public String[] getCutMoneyByCnt(double min, double max, int cnt){
		// 每次产生的数据
		double one = 0; 
		
		String[] rates = new String [cnt];
		
		if (cnt == 1) {
			rates[0] = max + "";
			return rates;
		}
		
		for (int i = 0; i < cnt; i ++) {
			Random rand = new Random();
			one = rand.nextInt((int)(max - 1)) + 1;
			one = one * (i + 1) / cnt;
			if (i == cnt - 1) {
				one = (int) max;
			}
			max -= one;
			rates[i] = one + "";
		}
		return rates;
	}
	
	
	
	@SuppressWarnings("unused")
	public Double getCutMoneyx(double min, double max){
		int    cnt =  1; // 数量
		int    scl =  2; // 小数最大位数
		int    pow = (int) Math.pow(10, scl); // 用于提取指定小数位
		double sum =  0; // 用于验证总和
		double one = 0;
		for (int i = 0; i < cnt; i ++) {
			if ( i <= cnt - 1 ) {
				// min~max 指定小数位的随机数
				one = Math.floor((Math.random() * (max - min) + min) * pow) / pow;
			} else {
				one = max;
			}
			max -= one;
			sum += one;
			
		}
		
		return one;
	}
	
	    
}
