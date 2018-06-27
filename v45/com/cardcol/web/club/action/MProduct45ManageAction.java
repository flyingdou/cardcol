package com.cardcol.web.club.action;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cardcol.web.utils.DistanceUtil;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.utils.JSONUtils;
import com.freegym.web.utils.EasyUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MProduct45ManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2423015967194000928L;

	/**
	 * 健身卡列表
	 */
	public void findOneCardList() {
		String city = request.getParameter("city");
		pageInfo = service.queryOneCardList(city, pageInfo);
		JSONArray product45ByType = JSONArray.fromObject(pageInfo.getItems());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("product45ByType", product45ByType).accumulate("pageInfo",
				getJsonForPageInfo());
		response(obj);
	}

	/**
	 * 健身卡列表按价格排序
	 */
	/*
	 * public void findByPriceType() { String city =
	 * request.getParameter("city"); String priceType =
	 * request.getParameter("priceType"); pageInfo =
	 * service.queryByPriceType(city, priceType, pageInfo); JSONArray
	 * product45ByPriceType = JSONArray.fromObject(pageInfo.getItems()); final
	 * JSONObject obj = new JSONObject(); obj.accumulate("success",
	 * true).accumulate("product45ByPriceType",
	 * product45ByPriceType).accumulate("pageInfo", getJsonForPageInfo());
	 * response(obj); }
	 */
	/**
	 * 门店分布列表以及门店明细
	 */
	public void findProduct45Member() {
		if (pageInfo.getCurrentPage() != 2) {
			String city = request.getParameter("city");
			String id = request.getParameter("id");// 一卡通id
			String lon = request.getParameter("longitude");
			String lat = request.getParameter("latitude");
			Double longitude = null;
			Double latitude = null;
			if (!lon.equals("") && !lat.equals("")) {
				longitude = Double.valueOf(lon);
				latitude = Double.valueOf(lat);
			}
			pageInfo = service.queryStore(city, pageInfo, longitude, latitude, id);
			JSONArray product45Member = JSONArray.fromObject(pageInfo.getItems());
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true)
			   .accumulate("product45Member", product45Member)
			   .accumulate("pageInfo", getJsonForPageInfo())
			   ;
			response(obj);
		}
	}

	/**
	 * 查找当前坐标点的城市所有俱乐部
	 */
	public void findClubs() {
		String city = request.getParameter("city");
		final DetachedCriteria dc = DetachedCriteria.forClass(Member.class);
		dc.add(Restrictions.eq("role", "E"));
		dc.add(Restrictions.eq("grade", "1"));
		if (city != null && !"".equals(city))
			dc.add(Restrictions.eq("city", city));
		pageInfo.setOrder("stickTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo);
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			final Member m = (Member) it.next();
			final JSONObject obj = getMemberJson(m);
			obj.accumulate("tell", getString(m.getTell())).accumulate("address", getString(m.getAddress()))
					// 计算俱乐部评分 评价人数
					.accumulate("appraise", getGradeJson(m));
			jarr.add(obj);
		}
		final JSONObject ret = new JSONObject();
		ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
		response(ret);
	}

	/**
	 * 门店明细中的服务项目
	 */
	public void findProduct45MemberCourse() {
		String id = request.getParameter("id");
		List<Map<String, Object>> list = service.queryProduct45MemberCourse(id);// 门店课程
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("course", list);
		response(obj);
	}

	/**
	 * 门店明细内评论列表
	 */
	@SuppressWarnings("unchecked")
	public void findMemberEvaluate() {
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map<String, Object> map = service.queryClubEvaluateNum(id);
		pageInfo = service.queryEvaluateByDou(pageInfo, id, type);// 评论列表
		JSONArray memberEvaluate = JSONArray.fromObject(pageInfo.getItems());
		JSONArray jarr = new JSONArray();
		if (memberEvaluate.size() > 0) {
			for (int i = 0; i < memberEvaluate.size(); i++) {
				JSONObject job = memberEvaluate.getJSONObject(i);
				Map<String, Object> map1 = job;
				Member m = (Member) service.load(Member.class, job.getLong("mid"));
				getJsonForMember(m);
				map1.put("eval_content", DistanceUtil.decodeFromNonLossyAscii((String) map1.get("eval_content")));
				map1.put("member", getJsonForMember(m));
				map1.put("taobaoId", m.getId().toString());
				jarr.add(map1);
			}
		}
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("evaluateNum", map).accumulate("memberEvaluate", jarr)
				.accumulate("pageInfo", getJsonForPageInfo());
		System.out.println(obj);
		response(obj);
	}

	/**
	 * 淘课列表
	 */
	@SuppressWarnings("deprecation")
	public void findCourse() {
		String date = request.getParameter("date");
		String lon = request.getParameter("longitude");
		String lat = request.getParameter("latitude");
		Double longitude = null;
		Double latitude = null;
		if (!lon.equals("") && !lat.equals("")) {
			longitude = Double.valueOf(lon);
			latitude = Double.valueOf(lat);
		}
		String currentDate = EasyUtils.dateFormat(new Date(), "yyyy-MM-dd");
		String paramDate = EasyUtils.dateFormat(new Date(date.replace("-", "/")), "yyyy-MM-dd");
		if (currentDate.equals(paramDate)) {
			pageInfo = service.queryCourseByTime(pageInfo, date, longitude, latitude);
		} else {
			pageInfo = service.queryCourse(pageInfo, date, longitude, latitude);
		}

		JSONArray course = JSONArray.fromObject(pageInfo.getItems());
		JSONUtils.bubbleSort(course, true, "distance");
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("course", course).accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
	}

	/**
	 * 课程明细
	 */
	public void findCourseDetail() {
		String id = request.getParameter("id");
		Map<String, Object> map = service.queryCourseDetail(id);
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("courseDetail", map);
		response(obj);
	}

	/**
	 * 挑战明细
	 */
	public void findActiveDetail() {
		String activeId = request.getParameter("aid");// 挑战id
		Map<String, Object> map = service.queryActiveDetail(activeId, getMobileUser().getId().toString());
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("activeDetail", map);
		response(obj);
	}
}