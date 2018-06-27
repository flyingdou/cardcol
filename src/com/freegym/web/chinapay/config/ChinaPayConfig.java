package com.freegym.web.chinapay.config;


public class ChinaPayConfig {
	//public static String payUrl = "http://payment-test.chinapay.com/pay/TransGet"; // 测试地址，测试的时候用这个地址，应用到网站时用下面那个地址
//	public static String payUrl = "https://payment.chinapay.com/pay/TransGet"; //支付地址
	public static String payUrl = "https://payment.chinapay.com/CTITS/payment/TransGet";
	public static String MerId = "808080201300321"; // ChinaPay统一分配给商户的商户号，15位长度，必填
	public static String CuryId = "156"; // 订单交易币种，3位长度，固定为人民币156，必填
	public static String TransTypePay = "0001"; // 交易类型，4位长度，必填，0001表示消费交易，0002表示退货交易
	public static String TransTypeBack = "0002"; // 交易类型，4位长度，必填，0001表示消费交易，0002表示退货交易
	public static String Version = "20040916"; // 支付接入版本号，808080开头的商户用此版本，必填,另一版本为"20070129"
	public static String BgRetUrl = "http://www.cardcol.com/chinapaybgreturn.asp"; // 后台交易接收URL，为后台接受应答地址，用于商户记录交易信息和处理，对于使用者是不可见的，长度不要超过80个字节，必填
	public static String PageRetUrl = "http://www.cardcol.com/chinapaypgreturn.asp"; // 页面交易接收URL，为页面接受应答地址，用于引导使用者返回支付后的商户网站页面，长度不要超过80个字节，必填
	public static String GateId = "8607"; // 支付网关号，可选，参看银联网关类型，如填写GateId（支付网关号），则消费者将直接进入支付页面，否则进入网关选择页面
	public static String PgId = "999999999999999";
	public static String MerPrkPath = "\\chinapayKey\\MerPrk.key";
	public static String PgPubkPath = "\\chinapayKey\\PgPubk.key";
}
