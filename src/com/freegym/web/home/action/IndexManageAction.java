package com.freegym.web.home.action;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.FirstPage;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.order.BasicShop;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.utils.DBConstant;
import com.freegym.web.utils.IpUtils;
import com.sanmen.web.core.utils.BaiduUtils;

import fr.expression4j.core.Expression;
import fr.expression4j.core.Parameters;
import fr.expression4j.core.exception.EvalException;
import fr.expression4j.core.exception.ParsingException;
import fr.expression4j.factory.ExpressionFactory;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ 
	@Result(name = "success", location = "/indexss.jsp"), 
	@Result(name = "video", location = "/plan/workout_detail_video.jsp"),
    @Result(name = "about", location = "/us/about.jsp"),
    @Result(name = "contact", location = "/us/contact.jsp"),
    @Result(name = "cardcol", location = "/indexc.jsp")
})
public class IndexManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;
	private String city;// 当前城市
	private String day1;
	private String day2;
	private String day3;
	String clubList; // 场馆预定--推荐的可预订的场馆
	private List<FirstPage> typeList1;// 场馆预订-运动项目列表
	private List<PlanRelease> planList; // 健身计划列表

	

	@SuppressWarnings("rawtypes")
	public void queryHeadInfo() {
		final String city = (String) session.getAttribute("currentCity");
		if (this.toMember() != null) {
			final List msgs = service.findObjectBySql(
					"SELECT COUNT(msg.id) FROM Message msg where msg.memberTo.id = ? and msg.type = '1' and msg.status = '1' ", new Object[] { this.toMember()
							.getId() });
			final List msgs1 = service.findObjectBySql("select count(m.id) from Message m where m.memberTo.id = ? and m.isRead = ? and m.type != '1'",
					new Object[] { this.toMember().getId(), "0" });
			List<Map<String, Object>> shops = service.queryForList("select * from (" + BasicShop.getShopSql() + ") t where t.member_id = ?", this.toMember()
					.getId());
			int shopsize = shops.size();
			String countStr = "";
			// 若会员登录，则不含审批消息
			if (this.toMember().getRole().equals("M")) {
				countStr = msgs1.get(0).toString();
			} else {
				countStr = new BigDecimal(msgs1.get(0).toString()).add(new BigDecimal(msgs.get(0).toString())).toString();
			}
			response("{\"msgNum\": \"" + countStr + "\",\"shopNum\": \"" + String.valueOf(shopsize) + "\",\"city\":\"" + city + "\"}");
		} else {
			response("{\"msgNum\": \"0\",\"shopNum\": \"0\",\"city\":\"" + city + "\"}");
		}
	}

	public String execute() {
		session.setAttribute("position", 0);
		final String currentCity = (String) session.getAttribute("currentCity");
		// 找出所有幻灯片
		request.setAttribute("slides", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_A));
		// 右边广告
		Object o =service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_B);
		request.setAttribute("notices",o);
		// 健身教练广告位
		request.setAttribute("coachRecommends", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_C));
		// 健身计划广告位
		Object planRecomms = service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_D);
		request.setAttribute("planRecommends", planRecomms);
		// 健身挑战广告位
		Object activeRecomms = service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_E);
		request.setAttribute("activeList", activeRecomms);
		// 健身卡广告位
		request.setAttribute("cardList", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_F));
		// 健身场馆广告位
		request.setAttribute("factoryRecommends", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_G));
		request.setAttribute("typeList1", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) and p.viewType = '1' order by p.code asc",
				"course_type_c"));
		// 找出所有公告
		request.setAttribute("notitys", service.findRecommendBySectorCode(DBConstant.RECOMM_HOME_NOTICE));
		// 找出所有规则
		request.setAttribute("rules", service.findRecommendBySectorCode(DBConstant.RECOMM_HOME_RULE));
		// 找出所有精品阅读
		request.setAttribute("readers", service.findRecommendBySectorCode(DBConstant.RECOMM_HOME_READER));
		// 左上角图片
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		day1 = sdf.format(new Date());
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdfshow = new SimpleDateFormat("MM月dd日");

		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date date = calendar.getTime();
		day2 = sdf.format(date);
		String day2show = sdfshow.format(date);

		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		String day3show = sdfshow.format(date);
		day3 = sdf.format(date);
		// 淘课吧 ok
		request.setAttribute("courseList1", service.findObjectBySql(
				"from Course c  where c.planDate =? and c.startTime > ? and c.member.id in(select id from Member m where m.role='E' and m.city='" + currentCity
						+ "') order by c.costs desc ", day1, new SimpleDateFormat("HH:mm").format(new Date())));
		request.setAttribute(
				"courseList2",
				service.findObjectBySql("from Course c  where c.planDate =? and c.member.id in(select id from Member m where m.role='E' and m.city='"
						+ currentCity + "') order by c.costs desc ", day2));
		request.setAttribute(
				"courseList3",
				service.findObjectBySql("from Course c  where c.planDate =? and c.member.id in(select id from Member m where m.role='E' and m.city='"
						+ currentCity + "') order by c.costs desc ", day3));
		day1 = new SimpleDateFormat("MM月dd日").format(new Date());
		day2 = day2show;
		day3 = day3show;
		// 健身达人榜 ok
		request.setAttribute("personList", service.query(" from Member m where m.role!='E' and m.city = ? ORDER BY m.workoutTimes DESC", 3, currentCity));

		// 健身卡在这儿淘-运动项目列表
		request.setAttribute("typeList", service.findParametersByCodes("course_type_c"));
		// 健身方法我知道

		// 自动生成列表
		request.setAttribute("goodsList", service.findObjectBySql(" from Goods g where g.id in (1,3)"));
		// 健身达人列表
		Object doyenList=service.queryForList(Member.getRanks());
		request.setAttribute("doyenList",doyenList );
		return SUCCESS;
	}

	public void queryClubRecomm() {
		final String city = (String) session.getAttribute("currentCity");
		response(getJsonString(service.findRecommendClubsByProject(DBConstant.RECOMM_SECTOR_SLIDES_G, city, id)));
	}

	public String showVideo() {
		final Action a = (Action) service.load(Action.class, id);
		request.setAttribute("videoSignPath", BaiduUtils.getSignPath(BUCKET, a.getVideo()));
		request.setAttribute("actionName", a.getName());
		return "video";
	}

	public static void main(String[] args) {
		try {
			Expression exp = ExpressionFactory.createExpression("f(x,y,z)=500*0.17/1");
			Parameters parms = ExpressionFactory.createParameters();
			System.out.println(exp.evaluate(parms).getRealValue());
		} catch (ParsingException e) {
			e.printStackTrace();
		} catch (EvalException e) {
			e.printStackTrace();
		}
	}
	
	
	// 关于我们
	public  String  about(){
		return "about";
	}
	
	// 联系我们
	public  String  contact(){
		return "contact";
	}
	
	// cardcol 域名访问时，跳转到cardcol的首页
	public String cardcol () {
		session.setAttribute("position", 0);
		final String currentCity = (String) session.getAttribute("currentCity");
		// 找出所有幻灯片
		request.setAttribute("slides", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_A));
		// 右边广告
		Object o =service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_B);
		request.setAttribute("notices",o);
		// 健身教练广告位
		request.setAttribute("coachRecommends", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_C));
		// 健身计划广告位
		Object planRecomms = service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_D);
		request.setAttribute("planRecommends", planRecomms);
		// 健身挑战广告位
		Object activeRecomms = service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_E);
		request.setAttribute("activeList", activeRecomms);
		// 健身卡广告位
		request.setAttribute("cardList", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_F));
		// 健身场馆广告位
		request.setAttribute("factoryRecommends", service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_G));
		request.setAttribute("typeList1", service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ? ) and p.viewType = '1' order by p.code asc",
				"course_type_c"));
		// 找出所有公告
		request.setAttribute("notitys", service.findRecommendBySectorCode(DBConstant.RECOMM_HOME_NOTICE));
		// 找出所有规则
		request.setAttribute("rules", service.findRecommendBySectorCode(DBConstant.RECOMM_HOME_RULE));
		// 找出所有精品阅读
		request.setAttribute("readers", service.findRecommendBySectorCode(DBConstant.RECOMM_HOME_READER));
//        //查找所有类型为A(健美增肌)的健身计划广告位
//		Object planRecommendsA = service.findRecommendPlanByType(new String[]{"A"});
//		request.setAttribute("planRecommendsA",planRecommendsA);
//		//查找所有类型为B(瘦身减重)的健身计划广告位
//		Object planRecommendsB = service.findRecommendPlanByType(new String[]{"B"});
//		request.setAttribute("planRecommendsB", planRecommendsB);
//		//查找所有类型为C/D(运动康复/提高运动表现)的健身计划广告位
//		Object planRecommendsC =  service.findRecommendPlanByType(new String[]{"C","D"});
//		request.setAttribute("planRecommendsC", planRecommendsC);
		// 左上角图片
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd");
		day1 = sdf.format(new Date());
		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat sdfshow = new SimpleDateFormat("MM月dd日");

		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date date = calendar.getTime();
		day2 = sdf.format(date);
		String day2show = sdfshow.format(date);

		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		String day3show = sdfshow.format(date);
		day3 = sdf.format(date);
		// 淘课吧 ok
		request.setAttribute("courseList1", service.findObjectBySql(
				"from Course c  where c.planDate =? and c.startTime > ? and c.member.id in(select id from Member m where m.role='E' and m.city='" + currentCity
						+ "') order by c.costs desc ", day1, new SimpleDateFormat("HH:mm").format(new Date())));
		request.setAttribute(
				"courseList2",
				service.findObjectBySql("from Course c  where c.planDate =? and c.member.id in(select id from Member m where m.role='E' and m.city='"
						+ currentCity + "') order by c.costs desc ", day2));
		request.setAttribute(
				"courseList3",
				service.findObjectBySql("from Course c  where c.planDate =? and c.member.id in(select id from Member m where m.role='E' and m.city='"
						+ currentCity + "') order by c.costs desc ", day3));
		day1 = new SimpleDateFormat("MM月dd日").format(new Date());
		day2 = day2show;
		day3 = day3show;
		// 健身达人榜 ok
		request.setAttribute("personList", service.query(" from Member m where m.role!='E' and m.city = ? ORDER BY m.workoutTimes DESC", 3, currentCity));

		// 健身卡在这儿淘-运动项目列表
		request.setAttribute("typeList", service.findParametersByCodes("course_type_c"));
		// 健身方法我知道

		// 自动生成列表
		request.setAttribute("goodsList", service.findObjectBySql(" from Goods g where g.id in (1,3)"));
		// 健身达人列表
		Object doyenList=service.queryForList(Member.getRanks());
		request.setAttribute("doyenList",doyenList );
		return "cardcol";
	}
	
	/**
	 * setter && getter
	 */
	public String getCity() {
		return city;
	}

	public String getDay1() {
		return day1;
	}

	public void setDay1(String day1) {
		this.day1 = day1;
	}

	public String getDay2() {
		return day2;
	}

	public void setDay2(String day2) {
		this.day2 = day2;
	}

	public String getDay3() {
		return day3;
	}

	public void setDay3(String day3) {
		this.day3 = day3;
	}

	public List<FirstPage> getTypeList1() {
		return typeList1;
	}

	public void setTypeList1(List<FirstPage> typeList1) {
		this.typeList1 = typeList1;
	}

	public List<PlanRelease> getPlanList() {
		return planList;
	}

	public void setPlanList(List<PlanRelease> planList) {
		this.planList = planList;
	}

	public void setCity(String city) {
		if (session.getAttribute("currentCity") == null) {
			this.city = IpUtils.getCardcolCity(request);
			session.setAttribute("currentCity", city);
		}
	}
}
