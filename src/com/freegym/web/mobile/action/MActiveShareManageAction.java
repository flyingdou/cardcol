package com.freegym.web.mobile.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.active.Active;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Body;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Goods;
import com.freegym.web.order.Product;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;
import com.opensymphony.xwork2.ActionContext;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "ordercoach", location = "/coachorder.jsp"),
		@Result(name = "trainrecordshare", location = "/trainrecordshare.jsp"),
		@Result(name = "fitnesscardshare", location = "/fitnesscardshare.jsp"),
		@Result(name = "plandetailshare", location = "/plandetailshare.jsp"),
		@Result(name = "goodsdetailshare", location = "/goodsdetailsshare.jsp")

})
public class MActiveShareManageAction extends BasicJsonAction {

	private static final long serialVersionUID = -4900715126840183203L;

	@SuppressWarnings("unused")
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@SuppressWarnings("unchecked")
	Map<String, Object> request1 = (Map<String, Object>) ActionContext.getContext().get("request");

	public void list() {
		/*
		 * 挑战分享
		 */
		Active a = (Active) service.load(Active.class, id);
		if (a == null) {
			request1.put("a", a);
		} else {
			request1.put("a", a);
			if (a.getCategory() == 'G') {
				request1.put("addornot", a.getContent());
				request1.put("sport", a.getUnit());
			} else {
				request1.put("addornot",
						a.getCategory().equals('A') ? "体重增加"
								: a.getCategory().equals('B') ? "体重减少"
										: a.getCategory().equals('E') ? "运动" : a.getCategory().equals('D') ? "运动" : "");
				request1.put("sport",
						a.getCategory().equals('A') ? "公斤"
								: a.getCategory().equals('B') ? "公斤"
										: a.getCategory().equals('E') ? "小时" : a.getCategory().equals('D') ? "次" : "");
			}
			Long count = service.queryForLong(
					"select count(*) from tb_active_order ci where ci.active = ? and ci.status != '0' ", id);
			request1.put("size", count);
			request1.put("judgeName", a.getJudgeMode() == 'A' ? "参加者指定裁判" : a.getCreator().getName());
			try {
				request.getRequestDispatcher("activeshare.jsp").forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/*
	 * 私教预约
	 */

	public String coachorder() {
		Course c = (Course) service.load(Course.class, id);
		request1.put("c", c);
		return "ordercoach";

	}

	/*
	 * 计划详情
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String plandetails() {
		PlanRelease p = (PlanRelease) service.load(PlanRelease.class, id);
		if (null == p) {
			request1.put("p", p);
		} else {
			StringBuffer appraiseSql = new StringBuffer("select * from (").append(BaseAppraise.getAppraiseInfo())
					.append(") t ");
			appraiseSql.append("where t.orderType = '3' AND t.productId = ").append(id);
			List list = service.queryForList(appraiseSql.toString());
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				Map p1 = (Map) it.next();
				p1.put("avgGrade", p1.get("avgGrade") == null ? 0 : (int) (double) (p1.get("avgGrade")));
			}
			String sql = "select * from tb_planrelease_order po where po.planRelease=? and po.origin='B' and po.`status`='1' and po.isappraise='1'";
			List<Map<String, Object>> li = service.queryForList(sql, new Object[] { id });
			request.setAttribute("countperson", li.size());
			request.setAttribute("planAppraise", list);
			request1.put("p", p);
			String s1 = p.getPlanType();
			String[] S1 = s1.split(",");
			StringBuffer sb1 = new StringBuffer();
			for (int i = 0; i < S1.length; i++) {
				if (S1[i].equals("A")) {
					S1[i] = "瘦身减重";
				} else if (S1[i].equals("B")) {
					S1[i] = "健美增肌";
				} else if (S1[i].equals("C")) {
					S1[i] = "运动康复";
				} else if (S1[i].equals("D")) {
					S1[i] = "提高运动表现";
				}
				sb1.append(S1[i]).append(",");
			}
			String SB1 = sb1.toString().substring(0, sb1.length() - 1);
			request1.put("ptype", SB1);
			String s2 = p.getApplyObject();
			String[] S2 = s2.split(",");
			StringBuffer sb2 = new StringBuffer();
			for (int i = 0; i < S2.length; i++) {
				if (S2[i].equals("A")) {
					S2[i] = "初级";
				} else if (S2[i].equals("B")) {
					S2[i] = "中级";
				} else if (S2[i].equals("C")) {
					S2[i] = "高级";
				}
				sb2.append(S2[i]).append(",");
			}
			String SB2 = sb2.toString().substring(0, sb2.length() - 1);
			request1.put("papply", SB2);
			String s3 = p.getScene();
			String[] S3 = s3.split(",");
			StringBuffer sb3 = new StringBuffer();
			for (int i = 0; i < S3.length; i++) {
				if (S3[i].equals("A")) {
					S3[i] = "办公室";
				} else if (S3[i].equals("B")) {
					S3[i] = "健身房";
				} else if (S3[i].equals("C")) {
					S3[i] = "家庭";
				} else if (S3[i].equals("D")) {
					S3[i] = "户外";
				}
				sb3.append(S3[i]).append(",");
			}
			String SB3 = sb3.toString().substring(0, sb3.length() - 1);
			request1.put("pscene", SB3);
		}
		return "plandetailshare";
	}

	/*
	 * 健身卡分享
	 */
	public String traincard() {

		Product pr = (Product) service.load(Product.class, id);
		request1.put("pr", pr);
		return "fitnesscardshare";
	}

	/*
	 * 训练记录分享
	 */
	@SuppressWarnings("unchecked")
	public String trainrecord() {
		TrainRecord tr = (TrainRecord) service.load(TrainRecord.class, id);
		if (null == tr) {
			request1.put("tr", tr);
		} else {
			request1.put("tr", tr);
			if (tr.getBmi() == null) {
				tr.setBmi(0.0);
			}
			request1.put("data",
					tr.getBmi() > 0 && tr.getBmi() < 18.5 ? "您的体重过轻，请增强营养，加强锻炼。"
							: tr.getBmi() < 24 && tr.getBmi() > 18.5 ? "您的体重正常，健康风险最低，身体健康。"
									: tr.getBmi() > 24 && tr.getBmi() < 28 ? "您的体重超重，患病风险正在加大。"
											: tr.getBmi() > 28 ? "您属于肥胖体型，属健康高危人群。" : "");
			if (tr.getWaistHip() == null) {
				tr.setWaistHip(0.0);
			}
			request1.put("data1", tr.getWaistHip() < 0.94 && tr.getWaistHip() >= 0.82 ? "您的腰臀比正常，身体健康。"
					: tr.getWaistHip() > 0.94 ? "您的腰臀比偏高，患病的概率比较大。" : "");
			if (tr.getHeartRate() == null) {
				tr.setHeartRate(0.0);
			}
			List<Body> b = (List<Body>) service.findObjectBySql(
					"select conclusion from Body as b where b.member = (select partake from TrainRecord as tr where tr.id=?)",
					id);
			request1.put("titai", b);
		}
		return "trainrecordshare";
	}

	/*
	 * 智能计划分享
	 */
	public String goodsdetail() {
		Goods g = (Goods) service.load(Goods.class, id);
		if (null == g) {
			request1.put("g", g);
		} else {
			StringBuffer appraiseSql = new StringBuffer("select distinct cnt,avgGrade from (")
					.append(BaseAppraise.getAppraiseInfo()).append(") t ");
			appraiseSql.append("where t.orderType = ").append("6").append(" AND t.productId = ").append(id);
			List<Map<String, Object>> list = service.queryForList(appraiseSql.toString());
			for (final Map<String, Object> map : list) {
				request1.put("countperson", null == map.get("cnt") || map.size() == 0 ? 0 : map.get("cnt"));
				request1.put("avgGrade", null == map.get("avgGrade") || map.size() == 0 ? 0 : map.get("avgGrade"));
			}
			request.setAttribute("planAppraise", list);
			request1.put("p", g);
			String s1 = g.getPlanType();
			String[] S1 = s1.split(",");
			StringBuffer sb1 = new StringBuffer();
			for (int i = 0; i < S1.length; i++) {
				if (S1[i].equals("A")) {
					S1[i] = "瘦身减重";
				} else if (S1[i].equals("B")) {
					S1[i] = "健美增肌";
				} else if (S1[i].equals("C")) {
					S1[i] = "运动康复";
				} else if (S1[i].equals("D")) {
					S1[i] = "提高运动表现";
				}
				sb1.append(S1[i]).append(",");
			}
			String SB1 = sb1.toString().substring(0, sb1.length() - 1);
			request1.put("gtype", SB1);
			String s2 = g.getApplyObject();
			String[] S2 = s2.split(",");
			StringBuffer sb2 = new StringBuffer();
			for (int i = 0; i < S2.length; i++) {
				if (S2[i].equals("A")) {
					S2[i] = "初级";
				} else if (S2[i].equals("B")) {
					S2[i] = "中级";
				} else if (S2[i].equals("C")) {
					S2[i] = "高级";
				}
				sb2.append(S2[i]).append(",");
			}
			String SB2 = sb2.toString().substring(0, sb2.length() - 1);
			request1.put("gapply", SB2);
			String s3 = g.getScene();
			String[] S3 = s3.split(",");
			StringBuffer sb3 = new StringBuffer();
			for (int i = 0; i < S3.length; i++) {
				if (S3[i].equals("A")) {
					S3[i] = "健身房";
				} else if (S3[i].equals("B")) {
					S3[i] = "办公室";
				} else if (S3[i].equals("C")) {
					S3[i] = "家庭";
				} else if (S3[i].equals("D")) {
					S3[i] = "户外";
				}
				sb3.append(S3[i]).append(",");
			}
			String SB3 = sb3.toString().substring(0, sb3.length() - 1);
			request1.put("gscene", SB3);
			request1.put("author", g.getType() == "1" ? "卡库" : "王严讲健身");
		}
		return "goodsdetailshare";
	}
}
