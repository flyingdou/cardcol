package com.freegym.web.goods.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.order.Goods;
import com.freegym.web.utils.DBConstant;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "cardcol", location = "/goods/goods_cardcol.jsp"), @Result(name = "wangyan", location = "/goods/goods_wangyan.jsp"),
		@Result(name = "shopim", location = "/order/shopim.jsp") })
public class GoodsManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 6135804291162978585L;

	private Goods goods;

	private Setting setting;

	private Member member;

	private String goodsId;

	private Member author;

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() {
		final String currentCity = (String) session.getAttribute("currentCity");
		request.setAttribute("planRecommends1", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_N, currentCity));
		request.setAttribute("planRecommends2", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_O, currentCity));
		if (goodsId == null) {
			return null;
		}
		goods = (Goods) service.load(Goods.class, Long.valueOf(goodsId));
		if (goods == null) {
			return null;
		}
		goods.setScene(goods.getScene().replaceAll("A", "健身房").replaceAll("B", "办公室").replaceAll("C", "家庭").replaceAll("D", "户外").replaceAll("E", "其它"));
		goods.setPlanType(goods.getPlanType().replaceAll("A", "瘦身减重").replaceAll("B", "健美增肌").replaceAll("C", "运动康复").replaceAll("D", "提高运动表现"));
		goods.setApplyObject(goods.getApplyObject().replaceAll("A", "初级").replaceAll("B", "中级").replaceAll("C", "高级"));
		member = this.toMember();
		author = (Member) service.load(Member.class, Long.valueOf(goods.getMember()));
		if (member != null) {
			setting = service.loadSetting(member.getId());
			request.setAttribute("strengthDates", StringUtils.stringToList(setting.getStrengthDate()));
			request.setAttribute("cardioDates", StringUtils.stringToList(setting.getCardioDate()));
		}
		request.setAttribute("actions", service
				.findObjectBySql("from Action a where a.member = ? and a.part in (select id from Part p where p.project in (select id from Project p1 where p1.mode = '0'))", 1l));
		String type = goods.getType();
		StringBuffer appraiseSql = new StringBuffer("select * from (").append(BaseAppraise.getAppraiseInfo()).append(") t ");
		appraiseSql.append("where t.orderType = '6' AND t.productId = ").append(goods.getId());
		System.out.println(appraiseSql);
		List goodsAppraises = service.queryForList(appraiseSql.toString());
		for (final Iterator<?> it = goodsAppraises.iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("grade", p.get("grade") == null ? 0 : (int) (double) (p.get("grade")));
			p.put("avgGrade", p.get("avgGrade") == null ? 0 : (int) (double) (p.get("avgGrade")));
		}
		request.setAttribute("goodsAppraise", goodsAppraises);
		// 作者综合评分以及服务人次
		StringBuffer memberSql = new StringBuffer("select * from (" + Member.getMemberSql(null, null, null, null, null, null, author.getCity())
				+ ") t where t.id=?");
		List memberAppraises = service.queryForList(memberSql.toString(), author.getId());
		for (final Iterator<?> it = memberAppraises.iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("member_grade", p.get("member_grade") == null ? 0 : (int) (double) (p.get("member_grade")));
		}
		request.setAttribute("memberAppraise", memberAppraises);
		// 更多作品
		request.setAttribute("otherPlans", service.findObjectBySql("from PlanRelease p where p.member.id =?", author.getId()));
		if ("1".equals(type)) {
			return "cardcol";
		} else if ("2".equals(type)) {
			return "wangyan";
		} else {
			return "cardcol";
		}
	}
}
