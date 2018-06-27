package com.cardcolv45.mobile.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.cardcol.web.basic.EvaluateReply;
import com.cardcol.web.basic.MemberEvaluate;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MReplyV45ManageAction extends BasicJsonAction implements Constantsms {

	/**
	 * 
	 */
	private static final long serialVersionUID = -657760644161895169L;

	/**
	 * 全局变量
	 */
	private MemberEvaluate evaluate;
	private Member member;

	/**
	 * 回复评价
	 */
	public void addReply() {
		try {
			// 测试数据
			// JSONObject jsonObject = new JSONObject();
			// jsonObject.accumulate("content", "Oh ! Are you be sure ? ")
			// .accumulate("evaluateId", 191)
			// .accumulate("parent", "")
			// ;
			// String jsons = jsonObject.toString();

			JSONArray array = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject obj = (JSONObject) array.get(0);
			EvaluateReply reply = new EvaluateReply();

			reply.setContent(obj.getString("content"));
			reply.setTime(new Date());

			evaluate = (MemberEvaluate) service.load(MemberEvaluate.class, obj.getLong("evaluateId"));
			reply.setEvaluate(evaluate);
			member = (Member) getMobileUser();
			reply.setMember(member);
			if (obj.containsKey("parent") && obj.get("parent") != null && !"".equals(obj.get("parent"))) {
				EvaluateReply parent = (EvaluateReply) service.load(EvaluateReply.class, obj.getLong("parent"));
				Member replyTo = parent.getMember();
				// reply.setParent(parent);
				reply.setReplyTo(replyTo);
			}

			// 数据持久化
			reply = (EvaluateReply) service.saveOrUpdate(reply);
			JSONObject ret = new JSONObject();
			if (reply != null) {
				// 回复成功
				ret.accumulate("success", true).accumulate("message", "OK").accumulate("key", reply.getId());
				response(ret);
			} else {
				// 回复失败
				ret.accumulate("success", false).accumulate("message", "回复失败");
				response(ret);
			}
		} catch (LogicException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除回复(用户只能删除自己的回复)
	 */
	public void deleteReply() {
		try {
			long replyId = Long.parseLong(request.getParameter("replyId"));
			JSONObject ret = new JSONObject();
			if (replyId != 0) {
				String sql = "delete from TB_MEMBER_EVALUATE_REPLY where id = " + replyId;
				int i = DataBaseConnection.updateData(sql, null);
				if (i > 0) {
					// 删除成功
					ret.accumulate("success", true).accumulate("message", "OK");
					response(ret);
				} else {
					// 删除异常
					ret.accumulate("success", false).accumulate("message", "该评论已经被删除");
					response(ret);
				}
			} else {
				ret.accumulate("success", false).accumulate("message", "请传入要删除的回复的id");
				response(ret);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response(new JSONObject().accumulate("sucess", false).accumulate("message", e));
		}

	}

	/**
	 * 
	 * getter and setter
	 */
	public MemberEvaluate getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(MemberEvaluate evaluate) {
		this.evaluate = evaluate;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
}
