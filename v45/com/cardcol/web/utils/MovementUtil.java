package com.cardcol.web.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.push.model.v20160801.PushRequest;
import com.aliyuncs.push.model.v20160801.PushResponse;

public class MovementUtil {
	
	
	 public static void movement(String id,String body) throws ServerException, ClientException {
		 PushRequest pushRequest=new PushRequest();
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIEBpPW8aDSjKX", "x9wTapvynUDLiyHg3zdWuVjGs3JFxj");
			Long appkey=(long) 23708327;
			DefaultAcsClient client1 = new DefaultAcsClient(profile);
			pushRequest.setProtocol(ProtocolType.HTTPS);
	        pushRequest.setMethod(MethodType.POST);
	        pushRequest.setActionName("Push");
	        pushRequest.setRegionId("cn-hangzhou");
	        pushRequest.setVersion("2016-08-01");
	        pushRequest.setAppKey(appkey);
	        pushRequest.setTarget("ACCOUNT"); //推送目标: DEVICE:按设备推送 ALIAS : 按别名推送 ACCOUNT:按帐号推送  TAG:按标签推送; ALL: 广播推送
	        pushRequest.setTargetValue(id); //根据Target来设定，如Target=DEVICE, 则对应的值为 设备id1,设备id2. 多个值使用逗号分隔.(帐号与设备有一次最多100个的限制)
	        pushRequest.setPushType("NOTICE"); // 消息类型 MESSAGE NOTICE
	        pushRequest.setDeviceType("ALL"); // 设备类型 ANDROID iOS ALL.
	        pushRequest.setTitle("健身E卡通"); // 消息的标题
	        pushRequest.setBody(body); // 消息的内容
	        pushRequest.setiOSApnsEnv("PRODUCT");//DEV:开发模式; PRODUCT:生产模式
	        pushRequest.setAndroidOpenType("APPLICATION"); //点击通知后动作 "APPLICATION" : 打开应用 "ACTIVITY" : 打开AndroidActivity "URL" : 打开URL "NONE" : 无跳转
	        pushRequest.setStoreOffline(true); // 离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
	        PushResponse pushResponse = client1.getAcsResponse(pushRequest);
	        System.out.printf("RequestId: %s, MessageID: %s\n",
	                    pushResponse.getRequestId(), pushResponse.getMessageId());
	 }

}
