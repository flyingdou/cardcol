package com.aliyn.api.geteway.util;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import net.sf.json.JSONObject;

public class SingleSendSms implements Constantsms {
	
	 //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIEBpPW8aDSjKX";
    static final String accessKeySecret = "x9wTapvynUDLiyHg3zdWuVjGs3JFxj";
    static final String signName = "健身E卡通";
    static final String signName_c = "卡库健身";

    
    public void sendMsg(String phoneNum, String param, String code) {

        try {
			//可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");

			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);

			//组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号
			request.setPhoneNumbers(phoneNum);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(code);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
      
			request.setTemplateParam(param);

			//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
			//request.setSmsUpExtendCode("90997");

			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");

			//hint 此处可能会抛出异常，注意catch
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			JSONObject ret = new JSONObject();
			
			if ("OK".equals(sendSmsResponse.getCode())){
				ret.accumulate("success", true).accumulate("mobilephone", request.getPhoneNumbers());
			} else {
				ret.accumulate("success", false).accumulate("message", sendSmsResponse.getMessage());
			}
			System.out.println(ret.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
    
    
    public String sendMsg(String phoneNum, String param, String code, String dou) {
		JSONObject ret = new JSONObject();
        try {
			//可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");

			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);

			//组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号
			request.setPhoneNumbers(phoneNum);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(code);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
      
			request.setTemplateParam(param);

			//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
			//request.setSmsUpExtendCode("90997");

			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");

			//hint 此处可能会抛出异常，注意catch
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

			
			if ("OK".equals(sendSmsResponse.getCode())){
				ret.accumulate("success", true).accumulate("mobilephone", request.getPhoneNumbers()).accumulate("message", sendSmsResponse.getMessage());
			} else {
				ret.accumulate("success", false).accumulate("message", sendSmsResponse.getMessage());
			}
			System.out.println(ret.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return e + "";
		}
        return ret.toString();

    }
    
    
    
    
    public void sendMsgC(String phoneNum, String param, String code) {

        try {
			//可自助调整超时时间
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");

			//初始化acsClient,暂不支持region化
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);

			//组装请求对象-具体描述见控制台-文档部分内容
			SendSmsRequest request = new SendSmsRequest();
			//必填:待发送手机号
			request.setPhoneNumbers(phoneNum);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(signName_c);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(code);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
      
			request.setTemplateParam(param);

			//选填-上行短信扩展码(无特殊需求用户请忽略此字段)
			//request.setSmsUpExtendCode("90997");

			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			request.setOutId("yourOutId");

			//hint 此处可能会抛出异常，注意catch
			SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
			JSONObject ret = new JSONObject();
			
			if ("OK".equals(sendSmsResponse.getCode())){
				ret.accumulate("success", true).accumulate("mobilephone", request.getPhoneNumbers());
			} else {
				ret.accumulate("success", false).accumulate("message", sendSmsResponse.getMessage());
			}
			System.out.println(ret.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

    }
	
	
	
	
	

}
