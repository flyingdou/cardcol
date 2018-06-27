package com.alipay.server;

import com.alipay.client.Rsa;

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = "2088202267449811";
	// 商户的私钥
	public static String private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCqsMIYGNkSPLrSR0EJwUhCpuopdi1CPMoPfUKHWJritxOpu7SSA4fZlVUYQoga8qTud8adihJuKz3SWKwT3BlfnspWdYsl/M7cyCMdM+u6qqNMQRGIULJpT9iEdBiG7VI9lIYn5cmxfZwT4B4ssG8ba7ef36raRtWbEVHBvB/BRope+pZXxWOv8K7SXOEceZAf40ogt6eZRIrHZGfbCuHFBEecjqjk7tjTOL06xJWd8am/Y5UP/4908ONsnMSolrkcQb1fUcA3cXtdHssMC8/DSxHz8XoNOtUyyJJN1hx7c51dsScFABvys+cyLYKaPuKJYXtOLzjayTG52loGxAq/AgMBAAECggEAO86RQ4Y5UOWMhC9JP5TEr+3yMDLjKslH92NpUykdpACH2DoXy+2GxRtI5KYzMFYCqje3lO2TBZ0LHU/Kg27kSRfHR8IJznalOMC7ZPkTSKWPcCGAdiXoTXQXpllTWrt+zGrlEse2AwXDEUe+6uOrGo2EmnTxVWfqYloQLnSvH7X1kwmenLCDnaldOTuLcCWlBENCZlDxsx96JWq79wQAIUvuuumaIYFfCymq/6vbVN+uLFsCvDgVl32zy4ebif/wu+igHDyRp0m+BOBbscGsS4ZWD/itPki9pOMsKPiw5Kg7j8nAWgtwOnV1GjtGee54nvBRUnZUs/hqLzEOs5yJ+QKBgQD8zqMXlAspzEom2y/oUNqzpJMI9SIHOFjQeqp4dNIdjA1+jQVxiCojJpHKjuRmjgl4x3xLBsPEFK0aZNSaB9ck1oKXvw/h6N6tt49uk+K3/DsQxpSrVna8RctiJdY8hxuXMO+j/CB6k19gT/jyUqDIxp8UYMpfsCShko8l3ezkgwKBgQCs2KAqBblxPSwRS67SEcPiFyGUglChCR1Bxp+4mMH3xO5bX6T8gknIGMLW3+3XLrtrPZFNApdV91VDQi8IIMsNx7uOluVwQHdi08nAADBWuBVN/0cwOqmcEXI7+6oC97WRUUCOnr5Q5LlS/c3yNGjWtb0gP8XqUg/otZmZq0vEFQKBgGEngGH1UPz1N6ILJjQfrozPOHp+yl7/9pHYMl8sdftI8X6q72acL6VmkyzHEVugRl+WOlwS83UNhSDZkyLG1JWhonpywMXO2pTkMEkMqFqQoppl1oiqJ+Ne5zSG2fhU6/OEqPPaVxkEPekjksZ9nrDKNCR+tgdkCA/X5Q9mZD3FAoGAP80Kedh5FhqNbKjyE+qo0ojVRVtA5eBWY1Qs2tfQKWQQ07ufS/HPyEOTj/tCcL54QeKNAs8lC39rgpMohyiLqz+BtOVwNZomN12TyAKJ1UjH3G2RWnUc7FXoQ8nmfIfxBn3mhX6FI5j5629yGKhN1otpD0FbDd5SrwRxeS6one0CgYEAjzWrVL0Ps4XiuyDjMEqk837qxr/SbmAIaBBX9ei9hGFPus8vJMAQ93Vu6TAA35JWNMrjMgezrQbizogPjDdqFP3Sae144AVseZL84787OawGNu/7vLkqCV77azPXD3tdNls1heIIPdFZv2KstRJLa08Hgs6LqazKUXoMT0q0nZo=";

	// 支付宝的公钥，无需修改该值
	public static String ali_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAl96fmuI2NEmDJGPsnmJ1I/NE8O6F4mdVV0hx4+CuOa+IQUpiV8dPIaRpURYV+XiCvRDW3p7P32UVQ5bVklJs7rh5sXk0KA6hp46FnjLksd6UWZwlgd0DX1KFTSb8gaL65jrl6b5iXxeVTJDljzoH2igctJ2kbnTeas8ABZ617neTfYIt1PVxyuAzlc2o0Z/Zh8A1tUQj6vo5qE4aUjAldMHfwaUAzFOaDCOY908KaAVgNU5F6QjCuEomUEJd2GGnNjtCK8M2+VQ+bdseAxv/yxQEsiw7e3englGaXzaeR9muxkGUPkLmfNOfbX4wsxxmtgKJ1FOsQkXk4GEJ2LBVPQIDAQAB";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "RSA";

	public static String key = "bflw3f2ytusdtzgolncnqzmkoxa0i76w";

	public static String seller_email = "cs@ptstudio.com.cn";

	public static String notify_url = "http://www.ecartoon.com.cn/alipaynotify.asp";
	
	public static String notify_url_c = "http://www.ecartoon.com.cn/alipaynotifyC.asp";
	
	public static String return_url = "http://www.ecartoon.com.cn/alipayreturnurl.asp";
	
//    public static String notify_url = "http://www.ecartoon.com.cn:88/alipaynotify.asp";
//	
//	public static String notify_url_c = "http://test.ecartoon.com.cn:88/alipaynotifyC.asp";
//	//
//	public static String return_url = "http://test.ecartoon.com.cn:88/alipayreturnurl.asp";
	// public static String notify_url =
	// "http://156q1j2075.iask.in:88/cardcolv3/alipaynotify.asp";

	// public static String return_url =
	// "http://156q1j2075.iask.in:88/cardcolv3/alipayreturnurl.asp";

	public static void main(String[] args) {
		final String content = "asdfasdfasdf";
		final String sign = Rsa.sign(content, AlipayConfig.private_key);
		final boolean checked = Rsa.doCheck(content, sign, AlipayConfig.ali_public_key);
		System.out.println(checked);
	}
}
