package com.freegym.web.utils;

import java.util.List;

import org.apache.log4j.Logger;

import weibo4j.Timeline;
import weibo4j.Weibo;
import weibo4j.model.Emotion;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

public class WeiboUtils {

	protected static Logger log = Logger.getLogger(WeiboUtils.class);

	/**
	 * 将内容发送至本人新浪微博中
	 * 
	 * @param sinaId当前登录用户的新浪access_token值
	 *            ，取session用户的sinaId值
	 * @param content要发送的内容
	 *            ，由用户在界面进行录入
	 * @return true为徽博发送成功，false为发送失败
	 */
	public static boolean createWeibo(final String sinaId, final String content) {
		Weibo weibo = new Weibo();
		weibo.setToken(sinaId);
		final Timeline tm = new Timeline();
		tm.client.setToken(sinaId);
		try {
			final Status status = tm.UpdateStatus(content);
			System.out.println(status.toString());
			return true;
		} catch (WeiboException e) {
			log.error("发送微博错误", e);
			return false;
		}
	}

	/**
	 * 获取官方表情
	 */
	public static List<Emotion> getEmotions(final String sinaId) {
		final Timeline tm = new Timeline();
		tm.client.setToken(sinaId);
		try {
			List<Emotion> emotions = tm.getEmotions();
			if (emotions.size() > 40) {
				emotions = emotions.subList(0, 40);
			}
			return emotions;
		} catch (WeiboException e) {
			log.error("获取微博官方表情错误", e);
		}
		return null;
	}
}
