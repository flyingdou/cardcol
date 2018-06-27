package com.freegym.web.home.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.order.Goods;
import com.freegym.web.plan.PlanRelease;
import com.sanmen.web.core.utils.DateUtil;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/plan.jsp"),
		@Result(name = "loadpicture", location = "/home/loadpicture.jsp"),
		@Result(name = "view", location = "/home/planView.jsp"),
		@Result(name = "plan_list", location = "/order/plan_list.jsp"),
		@Result(name = "plan_list1", location = "/homeWindow/plan_list.jsp"),
		@Result(name = "index", location = "/order/index.jsp") })
public class PlanManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	/**
	 * 查询类型
	 */
	private Character queryType;
	private String area;// 地区

	private String sex; // 性别,'M':'男','F':'女

	private String keyword; // 关键字搜索
	private String lttarget;// 左上侧列表传来的健身目的
	private PlanRelease planRelease;// 计划详情页
	private int period;// 计划周期
	private Integer planAvgGrade;// 平均分
	private String isTop;// 1置顶：2取消置顶
	private boolean isPlan;
	private String planType;// 3为计划，6为智能计划
	private Goods goods;

	private Member member;
	private String pid; // 计划发布表tb_plan_release的id字段
	private static String id = "";// 与pid对应

	private String flag;

	private String fileName;
	private File picFile;

	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public File getPicFile() {
		return picFile;
	}

	public void setPicFile(File picFile) {
		this.picFile = picFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public Integer getPlanAvgGrade() {
		return planAvgGrade;
	}

	public void setPlanAvgGrade(Integer planAvgGrade) {
		this.planAvgGrade = planAvgGrade;
	}

	public static void setId(String id) {
		PlanManageAction.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public Character getQueryType() {
		return queryType;
	}

	public void setQueryType(Character queryType) {
		this.queryType = queryType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLttarget() {
		return lttarget;
	}

	public PlanRelease getPlanRelease() {
		return planRelease;
	}

	public void setPlanRelease(PlanRelease planRelease) {
		this.planRelease = planRelease;
	}

	public void setLttarget(String lttarget) {
		this.lttarget = lttarget;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String loadPicture() {
		request.setAttribute("id", id);
		return "loadpicture";
	}

	public String savePicture() {
		String h = saveFile("loadPic", picFile, fileName, "");
		request.setAttribute("picAdd", h);
		return execute();
	}

	public String getIsTop() {
		return isTop;
	}

	public void setIsTop(String isTop) {
		this.isTop = isTop;
	}

	public boolean isPlan() {
		return isPlan;
	}

	public void setPlan(boolean isPlan) {
		this.isPlan = isPlan;
	}

	public String getPlanType() {
		return planType;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String execute() {
		if (pid == null) {
			return null;
		}
		id = pid;
		session.setAttribute("position", 4);
		List<PlanRelease> planReleaseList = (List<PlanRelease>) service
				.findObjectBySql("from PlanRelease p where p.id=?", Long.valueOf(pid));
		if (planReleaseList.size() > 0)
			planRelease = planReleaseList.get(0);
		if (planRelease == null) {
			return null;
		}
		List<?> params = service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) and p.code = ?",
				"plan_type_c", planRelease.getPlanType());
		request.setAttribute("planParam", params.size() > 0 ? params.get(0) : null);
		period = DateUtil.dateDiff(planRelease.getEndDate(), planRelease.getStartDate()) + 1;
		planRelease.setScene(planRelease.getScene().replaceAll("A", "办公室").replaceAll("B", "健身房").replaceAll("C", "家庭")
				.replaceAll("D", "户外").replaceAll("E", "其它"));
		Object j = service.queryForList(
				"select * from tb_plan_release tpr join (select id,planRelease,sum(count) saleNum from tb_planrelease_order tpo GROUP BY tpo.planRelease order by saleNum desc) s ON tpr.id=s.planRelease");
		request.setAttribute("HotPlan", j);
		// 查看计划的评价
		StringBuffer appraiseSql = new StringBuffer("select * from (").append(BaseAppraise.getAppraiseInfo())
				.append(") t ");
		appraiseSql.append("where t.orderType = '3' AND t.productId = ").append(planRelease.getId());
		List list = service.queryForList(appraiseSql.toString());
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("grade", p.get("grade") == null ? 0 : (int) (double) (p.get("grade")));
			p.put("avgGrade", p.get("avgGrade") == null ? 0 : (int) (double) (p.get("avgGrade")));
		}
		request.setAttribute("planAppraise", list);
		// 相关计划
		request.setAttribute("relativePlans",
				service.findObjectBySql("from PlanRelease p  where p.planType = ? and p.id!=" + planRelease.getId()
						+ " and isClose = '2' and audit='1' ", planRelease.getPlanType()));
		// 更多作品
		request.setAttribute("otherPlans",
				service.findObjectBySql(
						"from PlanRelease p where p.member.id =? and p.id!=? and isClose = '2' and audit='1' ",
						planRelease.getMember().getId(), planRelease.getId()));
		// 作者综合评分以及服务人次
		StringBuffer memberSql = new StringBuffer("select * from ("
				+ Member.getMemberSql(null, null, null, null, null, null, planRelease.getMember().getCity())
				+ ") t where t.id=?");
		List memberAppraises = service.queryForList(memberSql.toString(), planRelease.getMember().getId());
		for (final Iterator<?> it = memberAppraises.iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			p.put("member_grade", p.get("member_grade") == null ? 0 : (int) (double) (p.get("member_grade")));
		}
		request.setAttribute("memberAppraise", memberAppraises);
		return SUCCESS;
	}

	public String view() {
		planRelease = (PlanRelease) service.load(PlanRelease.class, Long.valueOf(pid));
		return "view";
	}

	public String query() {
		final Member m = this.toMember();
		pageInfo.setPageSize(10);
		final List<Object> params = new ArrayList<Object>();
		String planSql = "select * from (" + PlanRelease.getPlanSql()
				+ ") t where t.member = ? order by t.topTime desc";
		params.add(m.getId());
		pageInfo = service.findPageBySql(planSql, pageInfo, params.toArray());
		isPlan = true;
		type = 6;
		return "index";
	}

	/**
	 * 非本人查看
	 * 
	 * @return
	 */
	public String queryOther() {
		final Member m = (Member) session.getAttribute("toMember");
		pageInfo.setPageSize(10);
		final List<Object> params = new ArrayList<Object>();
		String planSql = "select * from (" + PlanRelease.getPlanSql()
				+ ") t where t.member = ? and t.audit = '1' and t.isClose = '2' order by t.topTime desc";
		params.add(m.getId());
		pageInfo = service.findPageBySql(planSql, pageInfo, params.toArray());
		isPlan = true;
		return "plan_list1";
	}

	public String changeTopTime() {
		if ("3".equals(planType)) {
			if (planRelease != null && planRelease.getId() != null) {
				planRelease = (PlanRelease) service.load(PlanRelease.class, planRelease.getId());
				if (isTop != null && isTop.equals("1")) {
					planRelease.setTopTime(new Date());
				} else {
					planRelease.setTopTime(null);
				}
				service.saveOrUpdate(planRelease);
			}
		} else {
			if (goods != null && goods.getId() != null) {
				goods = (Goods) service.load(Goods.class, goods.getId());
				if (isTop != null && isTop.equals("1")) {
					goods.setTopTime(new Date());
				} else {
					goods.setTopTime(null);
				}
				service.saveOrUpdate(goods);
			}
		}

		return query();
	}

	public String changeClose() {
		if ("3".equals(planType)) {
			if (planRelease != null && planRelease.getId() != null) {
				String isClose = planRelease.getIsClose();
				planRelease = (PlanRelease) service.load(PlanRelease.class, planRelease.getId());
				planRelease.setIsClose(isClose);
				service.saveOrUpdate(planRelease);
			}
		} else {
			if (goods != null && goods.getId() != null) {
				String isClose = goods.getIsClose();
				goods = (Goods) service.load(Goods.class, goods.getId());
				goods.setIsClose(isClose);
				service.saveOrUpdate(goods);
			}
		}
		return query();
	}

}
