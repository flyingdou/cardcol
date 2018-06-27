package com.freegym.web.task;

import org.apache.log4j.Logger;

import com.baidu.yun.channel.auth.ChannelKeyPair;
import com.baidu.yun.channel.client.BaiduChannelClient;
import com.baidu.yun.channel.exception.ChannelClientException;
import com.baidu.yun.channel.exception.ChannelServerException;
import com.baidu.yun.channel.model.PushUnicastMessageRequest;
import com.baidu.yun.channel.model.PushUnicastMessageResponse;
import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;

public class MessageThread implements Runnable {

	final Logger log = Logger.getLogger(MessageThread.class);

	private static String apiKey = "ipyKOxDbNvR0LWnDG9FpZnXP";

	private static String secretKey = "G9Ic4s5exPIBcISdXWKLFYyi1x35kx5G";

	protected static final String NOTITY_TITLE = "卡库通知";

	private Thread thread;

	private String userId;

	private Long channelId;

	private String content;

	private Integer termType;

	public MessageThread(String userId, Long channelId, Integer termType, String content) {
		this.userId = userId;
		this.channelId = channelId;
		this.termType = termType;
		this.content = content;
		thread = new Thread(this);
		thread.start();
	}

	public MessageThread(String userId, String channelId, String content) {
		this.userId = userId;
		if (null != channelId && !"".equals(channelId))
			this.channelId = Long.parseLong(channelId);
		this.content = content;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		if (null == userId || null == channelId)
			return;
		final ChannelKeyPair pair = new ChannelKeyPair(apiKey, secretKey);
		// 2. 创建BaiduChannelClient对象实例
		final BaiduChannelClient channelClient = new BaiduChannelClient(pair);
		// 3. 若要了解交互细节，请注册YunLogHandler类
		channelClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});

		try {
			// 4. 创建请求类对象
			// 手机端的ChannelId， 手机端的UserId， 先用1111111111111代替，用户需替换为自己的
			final PushUnicastMessageRequest request = new PushUnicastMessageRequest();
			request.setDeviceType(termType); // device_type => 1: web 2: pc 3:android
										// 4:ios 5:wp
			request.setChannelId(channelId);
			request.setUserId(userId);
			request.setDeployStatus(2);

			request.setMessageType(1);
			if (this.termType == 3) {
				request.setMessage("{\"title\":\"" + MessageThread.NOTITY_TITLE + "\",\"description\":\"" + content + "\"}");
			} else if (this.termType == 4) {
				request.setMessage("{\"aps\":{\"alert\":\"" + content + "\"}}");
			}
			// 5. 调用pushMessage接口
			final PushUnicastMessageResponse response = channelClient.pushUnicastMessage(request);
			// 6. 认证推送成功
			System.out.println("push amount : " + response.getSuccessAmount());
		} catch (ChannelClientException e) {
			// 处理客户端错误异常
			log.error("error", e);
			// new MessageThread(userId, channelId, content);
		} catch (ChannelServerException e) {
			// 处理服务端错误异常
			log.error("error", e);
			// new MessageThread(userId, channelId, content);
		}
	}
}
