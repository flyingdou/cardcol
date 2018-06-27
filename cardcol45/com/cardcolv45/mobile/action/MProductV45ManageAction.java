package com.cardcolv45.mobile.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cardcol.web.utils.DistanceUtil;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.utils.JSONUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MProductV45ManageAction extends BasicJsonAction {

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
		pageInfo = service.queryProduct45Member(city, pageInfo, longitude, latitude, id);
		JSONArray product45Member = JSONArray.fromObject(pageInfo.getItems());
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("product45Member", product45Member).accumulate("pageInfo",
				getJsonForPageInfo());
		response(obj);
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

	// /**
	// * 教练评论明细列表
	// */
	// @SuppressWarnings("unchecked")
	// public void findMemberEvaluate() {
	// String id = request.getParameter("id");
	// String type = request.getParameter("type");
	// Map<String, Object> map = service.queryClubEvaluateNum(id);
	// pageInfo = service.queryProduct45MemberEvaluate(pageInfo, id, type);//
	// 评论列表
	// JSONArray memberEvaluate = JSONArray.fromObject(pageInfo.getItems());
	// JSONArray jarr = new JSONArray();
	//
	// // 查询用户身高，体重，腰围，臀围的sql
	// String sql = "select height,weight,waist,hip from tb_plan_record where
	// partake = ? order by done_date desc limit 0,1";
	// if (memberEvaluate.size() > 0) {
	// for (int i = 0; i < memberEvaluate.size(); i++) {
	// JSONObject job = memberEvaluate.getJSONObject(i);
	// Map<String, Object> map1 = job;
	// Member m = (Member) service.load(Member.class, job.getLong("mid"));
	// // getJsonForMember(m);
	// // map1.put("eval_content",
	// //
	// DistanceUtil.decodeFromNonLossyAscii((String)map1.get("eval_content")));
	// // map1.put("member", getJsonForMember(m));
	// // map1.put("taobaoId",m.getId().toString());
	//
	// // 需要添加用户身高，体重，腰围，臀围参数
	// Object[] objx = { job.getLong("mid") };
	// List<Map<String, Object>> trainList = DataBaseConnection.getList(sql,
	// objx);
	//
	// // 用户回复列表(dou 2017-08-23)
	// String replySql = "select * from TB_MEMBER_EVALUATE_REPLY where evaluate
	// = ?";
	// Object[] objre = { map1.get("id") };
	//
	// List<Map<String, Object>> listre = DataBaseConnection.getList(replySql,
	// objre);
	// List<Map<String, Object>> replyList = new ArrayList<Map<String,
	// Object>>();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// if (listre.size() > 0) {
	// for (Map<String, Object> map2 : listre) {
	// String replySingle = "select r.* , "
	// + "(select m.name from tb_member m,TB_MEMBER_EVALUATE_REPLY r where m.id
	// = r.member and r.id = ? ) as mname , "
	// + "(select m.name from tb_member m,TB_MEMBER_EVALUATE_REPLY r where m.id
	// = r.replyTo and r.id = ? ) as rname "
	// + "from TB_MEMBER_EVALUATE_REPLY r where evaluate = ? and id = ? ";
	// Object[] objresingle = { map2.get("id"), map2.get("id"), map1.get("id"),
	// map2.get("id") };
	// Map<String, Object> mapx = (Map<String, Object>) DataBaseConnection
	// .getList(replySingle, objresingle).get(0);
	// mapx.put("time", sdf.format(mapx.get("time")));// 将查询出来的时间循环格式化
	// replyList.add(mapx);
	// }
	// }
	//
	// // 用户点赞数
	// String praiseCount = "select count(*) count from
	// TB_MEMBER_EVALUATE_PRAISE where evaluate = ?";
	// Object[] objpc = { map1.get("id") };
	// List<Map<String, Object>> listpc =
	// DataBaseConnection.getList(praiseCount, objpc);
	// Map<String, Object> mappc = null;
	// if (listpc.size() > 0) {
	// mappc = listpc.get(0);
	// }
	//
	//
	//
	// //查询该评论是否已被当前用户点赞
	// boolean isPraise = false;//默认未被当前用户点赞
	// String isPraiseStr = "select * from tb_member_evaluate_praise where
	// evaluate = ? and member = ?";
	// Object[] isObj = {map1.get("id"),getMobileUser().getId()};
	// List<Map<String, Object>> isList =
	// DataBaseConnection.getList(isPraiseStr, isObj);
	// if(isList.size() > 0){//已被当前用户点赞
	// isPraise = true;
	// }
	//
	//
	//
	//
	// // 该用户有训练记录
	// if (trainList.size() > 0) {
	// Map<String, Object> trainRecord = trainList.get(0);
	// // 将要输出的memberEvaluate中的参数添加到jsonArray中
	// jarr.add(new JSONObject().accumulate("id", job.getInt("id"))
	// .accumulate("name", job.getString("name")).accumulate("image",
	// job.getString("image"))
	// .accumulate("totality_score", job.getInt("totality_score"))
	// .accumulate("evenScore", job.getInt("even_score"))
	// .accumulate("deviceScore", job.getInt("device_score"))
	// .accumulate("serviceScore", job.getInt("service_score"))
	// .accumulate("eval_content", DistanceUtil.decodeFromNonLossyAscii((String)
	// job.get("eval_content")))
	// .accumulate("evalTime", job.getString("evalTime"))
	// .accumulate("image1", job.getString("image1")).accumulate("image2",
	// job.getString("image2"))
	// .accumulate("image3", job.getString("image3")).accumulate("signNum",
	// job.getInt("signNum"))
	// .accumulate("height", trainRecord.get("height"))
	// .accumulate("weight", trainRecord.get("weight"))
	// .accumulate("waist", trainRecord.get("waist")).accumulate("hip",
	// trainRecord.get("hip"))
	// .accumulate("replyList", replyList).accumulate("replyCount",
	// listre.size())
	// .accumulate("praiseCount", mappc.get("count"))
	// .accumulate("isPraise", isPraise)
	//
	// );
	// } else {
	// // 该用户没有训练记录
	// jarr.add(new JSONObject().accumulate("id", job.getInt("id"))
	// .accumulate("name", job.getString("name")).accumulate("image",
	// job.getString("image"))
	// .accumulate("totality_score", job.getInt("totality_score"))
	// .accumulate("evenScore", job.getInt("even_score"))
	// .accumulate("deviceScore", job.getInt("device_score"))
	// .accumulate("serviceScore", job.getInt("service_score"))
	// .accumulate("eval_content", DistanceUtil.decodeFromNonLossyAscii((String)
	// job.get("eval_content")))
	// .accumulate("evalTime", job.getString("evalTime"))
	// .accumulate("image1", job.getString("image1")).accumulate("image2",
	// job.getString("image2"))
	// .accumulate("image3", job.getString("image3")).accumulate("signNum",
	// job.getInt("signNum"))
	// .accumulate("height", 0).accumulate("weight", 0).accumulate("waist",
	// 0).accumulate("hip", 0)
	// .accumulate("replyList", replyList).accumulate("replyCount",
	// listre.size())
	// .accumulate("praiseCount", mappc.get("count"))
	// .accumulate("isPraise", isPraise)
	// );
	// }
	//
	// }
	// }
	// JSONObject obj = new JSONObject();
	// obj.accumulate("success", true).accumulate("evaluateNum",
	// map).accumulate("memberEvaluate", jarr)
	// .accumulate("pageInfo", getJsonForPageInfo());
	// System.out.println(obj);
	// response(obj);
	// }
	//
	//

	/**
	 * 教练评论明细列表
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public void findMemberEvaluate() {
		Member member = (Member) getMobileUser();
		String id = member.getId().toString();
		String queryType = request.getParameter("queryType");
		String searchFor = request.getParameter("searchFor");
		String coachId = request.getParameter("coachId");
		
		// 教练的评论
		if ("coach".equals(searchFor)) {
			pageInfo = service.queryProduct45MemberEvaluate(pageInfo, coachId, "0", "1");
		} else {
		       
				if ("M".equals(member.getRole())) {
					pageInfo = service.queryProduct45MemberEvaluate(pageInfo, id, "0", queryType, "");
				} else {
					pageInfo = service.queryProduct45MemberEvaluate(pageInfo, id, "0", queryType);
				}
		} 
		
		JSONArray memberEvaluate = JSONArray.fromObject(pageInfo.getItems());
		JSONArray jarr = new JSONArray();

		// 查询用户身高，体重，腰围，臀围的sql
		String sql = "select height,weight,waist,hip from tb_plan_record where partake = ? order by done_date desc limit 0,1";
		if (memberEvaluate.size() > 0) {

			for (int i = 0; i < memberEvaluate.size(); i++) {
				JSONObject job = memberEvaluate.getJSONObject(i);
				Map<String, Object> map1 = job;
				Member m = (Member) service.load(Member.class, job.getLong("mid"));

				// 需要添加用户身高，体重，腰围，臀围参数
				Object[] objx = { job.getLong("mid") };
				List<Map<String, Object>> trainList = DataBaseConnection.getList(sql, objx);

				// 用户回复列表(dou 2017-08-23)
				String replySql = "select * from TB_MEMBER_EVALUATE_REPLY where evaluate = ?";
				Object[] objre = { map1.get("id") };

				List<Map<String, Object>> listre = DataBaseConnection.getList(replySql, objre);
				List<Map<String, Object>> replyList = new ArrayList<Map<String, Object>>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (listre.size() > 0) {
					for (Map<String, Object> map2 : listre) {
						String replySingle = "select r.* , "
								+ "(select m.name from tb_member m,TB_MEMBER_EVALUATE_REPLY r where m.id = r.member and r.id = ? ) as mname ,  "
								+ "(select m.name from tb_member m,TB_MEMBER_EVALUATE_REPLY r where m.id = r.replyTo and r.id = ? ) as rname  "
								+ "from TB_MEMBER_EVALUATE_REPLY r where evaluate = ? and id = ? ";
						Object[] objresingle = { map2.get("id"), map2.get("id"), map1.get("id"), map2.get("id") };
						Map<String, Object> mapx = (Map<String, Object>) DataBaseConnection
								.getList(replySingle, objresingle).get(0);
						mapx.put("time", sdf.format(mapx.get("time")));// 将查询出来的时间循环格式化
						replyList.add(mapx);
					}
				}

				// 用户点赞数
				String praiseCount = "select count(*) count from TB_MEMBER_EVALUATE_PRAISE where evaluate = ?";
				Object[] objpc = { map1.get("id") };
				List<Map<String, Object>> listpc = DataBaseConnection.getList(praiseCount, objpc);
				Map<String, Object> mappc = null;
				if (listpc.size() > 0) {
					mappc = listpc.get(0);
				}

				// 查询该评论是否已被当前用户点赞
				boolean isPraise = false;// 默认未被当前用户点赞
				String isPraiseStr = "select * from tb_member_evaluate_praise where evaluate = ? and member = ?";
				Object[] isObj = { map1.get("id"), getMobileUser().getId() };
				List<Map<String, Object>> isList = DataBaseConnection.getList(isPraiseStr, isObj);
				if (isList.size() > 0) {// 已被当前用户点赞
					isPraise = true;
				}

				// 该用户有训练记录
				if (trainList.size() > 0) {
					Map<String, Object> trainRecord = trainList.get(0);
					// 将要输出的memberEvaluate中的参数添加到jsonArray中
					jarr.add(new JSONObject()
							.accumulate("id", job.getInt("id"))
							.accumulate("mName", job.getString("mName"))
							.accumulate("mid", job.getInt("mid"))
							.accumulate("mImage", job.getString("mImage"))
							.accumulate("cName", job.getString("cName"))
							.accumulate("cImage", job.getString("cImage"))
							.accumulate("totality_score", job.getInt("totality_score"))
							.accumulate("evenScore", job.getInt("even_score"))
							.accumulate("deviceScore", job.getInt("device_score"))
							.accumulate("serviceScore", job.getInt("service_score"))
							.accumulate("eval_content",
									DistanceUtil.decodeFromNonLossyAscii((String) job.get("eval_content")))
							.accumulate("evalTime", job.getString("evalTime"))
							.accumulate("image1", job.getString("image1")).accumulate("image2", job.getString("image2"))
							.accumulate("image3", job.getString("image3")).accumulate("signNum", job.getInt("signNum"))
							.accumulate("height", trainRecord.get("height"))
							.accumulate("weight", trainRecord.get("weight"))
							.accumulate("waist", trainRecord.get("waist")).accumulate("hip", trainRecord.get("hip"))
							.accumulate("replyList", replyList).accumulate("replyCount", listre.size())
							.accumulate("praiseCount", mappc.get("count")).accumulate("isPraise", isPraise));
				} else {
					// 该用户没有训练记录
					jarr.add(new JSONObject().accumulate("id", job.getInt("id"))
							.accumulate("mName", job.getString("mName"))
							.accumulate("mid", job.getInt("mid"))
							.accumulate("mImage", job.getString("mImage"))
							.accumulate("cName", job.getString("cName"))
							.accumulate("cImage", job.getString("cImage"))
							.accumulate("totality_score", job.getInt("totality_score"))
							.accumulate("evenScore", job.getInt("even_score"))
							.accumulate("deviceScore", job.getInt("device_score"))
							.accumulate("serviceScore", job.getInt("service_score"))
							.accumulate("eval_content",
									DistanceUtil.decodeFromNonLossyAscii((String) job.get("eval_content")))
							.accumulate("evalTime", job.getString("evalTime"))
							.accumulate("image1", job.getString("image1")).accumulate("image2", job.getString("image2"))
							.accumulate("image3", job.getString("image3")).accumulate("signNum", job.getInt("signNum"))
							.accumulate("height", 0).accumulate("weight", 0).accumulate("waist", 0).accumulate("hip", 0)
							.accumulate("replyList", replyList).accumulate("replyCount", listre.size())
							.accumulate("praiseCount", mappc.get("count")).accumulate("isPraise", isPraise));
				}

			}
		}
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("memberEvaluate", jarr).accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
	}

	/**
	 * 淘课列表
	 */
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
		pageInfo = service.queryCourse(pageInfo, date, longitude, latitude);
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