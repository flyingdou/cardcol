package sport.test;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.utils.EasyUtils;

import ecartoon.wx.util.MathRandom4dou;

@SuppressWarnings("serial")
public class test extends BasicJsonAction implements Constantsms {


public static void main(String[] args) {

//	SDK_WX sdk = new SDK_WX(null);
//	   String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
//			+ sdk.getAPPID() + "&secret=" + sdk.getAPPSERCRET();
//	JSONObject json = HttpRequestUtils.httpGet(GET_ACCESS_TOKEN_URL);
//	String access_token = json.getString("access_token");
//	System.err.println(access_token);
	
	MathRandom4dou ma = new MathRandom4dou();
	String [] dx = ma.getCutMoney(0.01, 1.00, 15);
	String res = EasyUtils.getStr(dx, ",");
	
	System.err.println(res);
	
	
}





/**
 * 调用阿里IM服务创建会话群组
 * 
 * @return
 */
//@SuppressWarnings("unused")
//private static String createTribe() {
//	String url = "http://gw.api.taobao.com/router/rest";
//	TaobaoClient client = new DefaultTaobaoClient(url, "24665455", "5fa4610e96e7ff53d1280a9f4052bf48");
//	OpenimTribeCreateRequest openRequest = new OpenimTribeCreateRequest();
//	OpenImUser user = new OpenImUser();
//	user.setUid("dou123");
//	user.setTaobaoAccount(false);
//	user.setAppKey("24665455");
//	openRequest.setUser(user);
//	openRequest.setTribeName(String.valueOf(Math.floor(Math.random() * 100000)));
//	openRequest.setNotice("123456"+Math.random()*10000);
//	openRequest.setTribeType(0L);
//	try {
//		OpenimTribeCreateResponse openResponse = client.execute(openRequest);
//		// {"openim_tribe_create_response":
//		// {"tribe_info":{"check_mode":0,"name":"60883.0","notice":"123456","recv_flag":0,
//		// "tribe_id":2410141833,"tribe_type":0},"request_id":"101zddx2enn3f"}}
//		JSONObject res = JSONObject.fromObject(JSONObject
//				.fromObject(JSONObject.fromObject(openResponse.getBody()).get("openim_tribe_create_response"))
//				.get("tribe_info"));
//		return res.getString("tribe_id");
//	} catch (Exception e) {
//		e.printStackTrace();
//	}
//	return "";
//}


	
	
	
	
}
