package com.cardcolv45.mobile.action;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.active.Active;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Product;
import com.freegym.web.plan.PlanRelease;

import net.sf.json.JSONObject;

/**
 * 分享接口类
 * 
 * @author hw
 *
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class MShareV45ManageAction extends BasicJsonAction implements Constantsms {

	private static final long serialVersionUID = -3897486386552369705L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	/*
	 * 挑战分享
	 */
	@SuppressWarnings("unused")
	public void activeShare() {
		try {
			Active a = (Active) service.load(Active.class, id);
			Long count = service.queryForLong(
					"select count(*) from tb_active_order ci where ci.active = ? and ci.status != '0' ", id);
			JSONObject json = new JSONObject();
			request.setAttribute("a", a);
			request.setAttribute("addornot",
					a.getCategory().equals('A') ? "体重增加"
							: a.getCategory().equals('B') ? "体重减少"
									: a.getCategory().equals('E') ? "运动" : a.getCategory().equals('D') ? "健身次数" : "");
			request.setAttribute("sport",
					a.getCategory().equals('A') ? "公斤"
							: a.getCategory().equals('B') ? "公斤"
									: a.getCategory().equals('E') ? "小时" : a.getCategory().equals('D') ? "次" : "");
			request.setAttribute("size", count);
			request.getRequestDispatcher("shareV45/activeShare.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 健身计划分享
	 */
	public void planShare() {
		try {
			JSONObject obj = new JSONObject();
			PlanRelease planRelease = (PlanRelease) service.load(PlanRelease.class, id);
			Member member = planRelease.getMember();
			long count = (long) DataBaseConnection
					.getOne("select count(t.id) as count from tb_planrelease_order as t where planrelease="
							+ planRelease.getId(), null)
					.get("count");
			obj.accumulate("success", true).accumulate("item", new JSONObject().accumulate("id", planRelease.getId())
					.accumulate("memberId", member.getId()).accumulate("memberName", member.getName())
					.accumulate("name", planRelease.getPlanName()).accumulate("planType", planRelease.getPlanType())
					.accumulate("scene", planRelease.getScene()).accumulate("applyObject", planRelease.getApplyObject())
					.accumulate("apparatuses", planRelease.getApparatuses())
					.accumulate("price", planRelease.getUnitPrice()).accumulate("summary", planRelease.getBriefing())
					.accumulate("image1", planRelease.getImage1()).accumulate("plancircle", planRelease.getPlanDay())
					.accumulate("goodsCount", count))
					.accumulate("startDate",
							sdf.format(planRelease.getStartDate() == null ? new Date() : planRelease.getStartDate()))
					.accumulate("endDate", sdf.format(planRelease.getEndDate() == null ? new Date()
							: planRelease.getEndDate()))
					.accumulate("plan_participantId", planRelease.getPlan_participant().getId())
					.accumulate("isClose", planRelease.getIsClose());
			request.setAttribute("plan", obj);
			request.getRequestDispatcher("shareV45/planShare.jsp").forward(request, response);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 私教套餐分享
	 */
	public void traincard() {
		try {
			Product a = (Product) service.load(Product.class, id);
			request.setAttribute("a",
					new JSONObject().accumulate("name", a.getName()).accumulate("cost", a.getCost())
							.accumulate("memberName", a.getMember().getName()).accumulate("remark", a.getRemark())
							.accumulate("wellNum", a.getWellNum()));
			request.getRequestDispatcher("shareV45/productShare.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
