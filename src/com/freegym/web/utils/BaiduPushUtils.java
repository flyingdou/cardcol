package com.freegym.web.utils;

import org.apache.log4j.Logger;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

public class BaiduPushUtils {

	final static Logger log = Logger.getLogger(BaiduPushUtils.class);
	
	private static String apiKey = "ipyKOxDbNvR0LWnDG9FpZnXP";

	private static String secretKey = "G9Ic4s5exPIBcISdXWKLFYyi1x35kx5G";

	public static void push(int termType, Long channelId, String userId, String notityTitle, String content) {
		try {
			final ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
			final BaiduChannelClient channelClient = new BaiduChannelClient(pair);
			channelClient.setChannelLogHandler(new YunLogHandler() {
				@Override
				public void onHandle(YunLogEvent event) {
					System.out.println(event.getMessage());
				}
			});
			final PushUnicastMessageRequest request = new PushUnicastMessageRequest();
			request.setDeviceType(termType);
			request.setChannelId(channelId);
			request.setUserId(userId);
			request.setDeployStatus(2);

			request.setMessageType(0);
			if (termType == 3) {
				request.setMessage("{\"title\":\"" + notityTitle + "\",\"description\":\"" + content + "\"}");
			} else if (termType == 4) {
				request.setMessage("{\"aps\":{\"alert\":\"" + content + "\"}}");
			}
			final PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
			System.out.println("push amount : " + response.getSuccessAmount());
		} catch (ChannelClientException e) {
			log.error("error", e);
		} catch (ChannelServerException e) {
			log.error("error", e);
		}
	}
}
