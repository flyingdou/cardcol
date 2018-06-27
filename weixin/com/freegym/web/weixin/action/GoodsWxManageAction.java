package com.freegym.web.weixin.action;

import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.order.Goods;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "wangyan", location = "/WX/systemXq.jsp"),
		   @Result(name = "orderinfo", location = "/eg/orderinfo.jsp"),
		   @Result(name = "movementtype", location = "/WX/expert_system.jsp") })
public class GoodsWxManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3298832203206779142L;

	private Goods goods;

	private Setting setting;

	private Member member;

	private String goodsId;

	private Member author;
	private Date date;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Setting getSetting() {
		return setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public Member getAuthor() {
		return author;
	}

	public void setAuthor(Member author) {
		this.author = author;
	}

	/*
	 * 王严专家系统首页
	 */
	@Override
	public String execute() {
		member = (Member) session.getAttribute("member");
		@SuppressWarnings("unused")
		final String currentCity = (String) session.getAttribute("currentCity");
		Long goodsId = (long) 1;
		goods = (Goods) service.load(Goods.class, goodsId);
		if (goods == null) {
			return null;
		}
		goods.setScene(goods.getScene().replaceAll("A", "健身房").replaceAll("B", "办公室").replaceAll("C", "家庭")
				.replaceAll("D", "户外").replaceAll("E", "其它"));
		goods.setPlanType(goods.getPlanType().replaceAll("A", "瘦身减重").replaceAll("B", "健美增肌").replaceAll("C", "运动康复")
				.replaceAll("D", "提高运动表现"));
		goods.setApplyObject(goods.getApplyObject().replaceAll("A", "初级").replaceAll("B", "中级").replaceAll("C", "高级"));
		request.setAttribute("goods",goods);
		return "wangyan";
	}

	public String movementtype() {
		// TODO 测试数据
		Member memberTest = new Member();
		memberTest.setId(Long.parseLong("11"));
		session.setAttribute("member", memberTest);
		goodsId = "1";
		
		member = (Member) session.getAttribute("member");
		request.setAttribute("startDate", date);
		request.setAttribute("setting", service.loadSetting(member.getId()));
		request.setAttribute("actions",
				service.findObjectBySql(
						"from Action a where a.member = ? and a.part in (select id from Part p where p.project in (select id from Project p1 where p1.mode = '0'))",
						1l));
		goods = (Goods) service.load(Goods.class, Long.valueOf(goodsId));
		return "movementtype";
	}
	
	/**
	 * 购买王严健身计划
	 * 
	 */
	public String wyTraningPlan() {
		// Test Data
		// TODO 以后去掉
		Member memberTest = new Member();
		memberTest.setMobilephone("110");
		session.setAttribute("member", memberTest);
		
		// 获取登录用户的账号 查看是否登录
		Member member = (Member) session.getAttribute("member");
		
		if (member == null) {
			return SUCCESS;
		} else {
			// 如果用户登录了 查询订单的详细信息
			
			String sql = "SELECT name orderName,price orderCost FROM tb_goods WHERE id = ?";
			pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { 1 });
			
			return "orderinfo";
		} 
	}

}
