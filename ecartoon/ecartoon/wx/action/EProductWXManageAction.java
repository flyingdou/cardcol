package ecartoon.wx.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.cardcol.web.utils.DistanceUtil;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.utils.JSONUtils;

import common.util.getAddress;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "oneCardDetail", location = "/ecartoon-weixin/detail.jsp"),
		@Result(name = "findProduct45Member", location = "/ecartoon-weixin/mdfb.jsp"),
		@Result(name = "findClubById", location = "/ecartoon-weixin/mdfbxq.jsp"),
		@Result(name = "findOneCardList", location = "/ecartoon-weixin/productList.jsp"),
		@Result(name = "map", location = "/ecartoon-weixin/mdfbmap.jsp"),
		@Result(name = "mdfb2", location = "/ecartoon-weixin/mdfb2.jsp")

})
public class EProductWXManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2423015967194000928L;

	/**
	 * 健身卡列表
	 */
	public String findOneCardList() {
		try {
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId2.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			String sql = " SELECT a.id,a.prod_name,a.prod_price,a.prod_image,a.prod_detail_image,a.prod_content,a.prod_summary,a.prodPeriodMonth,b.clubNum,IFNULL(c.saleNum,0) AS saleNum  "
					+ " FROM (SELECT id,prod_name,prod_price,prod_image,prod_detail_image,prod_content,prod_summary,  "
					+ " (CASE prod_period_unit WHEN 'D' THEN prod_period WHEN 'A' THEN prod_period * 30 WHEN 'B' THEN prod_period * 3 * 30 ELSE prod_period * 12 * 30 END) AS prodPeriodMonth   "
					+ " FROM tb_product_v45 WHERE id in (SELECT pc.product from tb_product_club_v45 pc, tb_member m WHERE pc.club = m.id ) AND prod_status = 'B' ) a   "
					+ " LEFT JOIN (SELECT pc.product,COUNT(1) AS clubNum from tb_product_club_v45 pc,tb_member m WHERE pc.club = m.id GROUP BY pc.product) b   "
					+ " ON a.id = b.product LEFT JOIN (SELECT order_product,COUNT(order_product) AS saleNum from tb_product_order_v45 GROUP BY order_product) c   "
					+ " ON a.id = c.order_product ";
			List<Map<String, Object>> oneList = DataBaseConnection.getList(sql, null);
			JSONArray onecardlist = new JSONArray();
			JSONObject objx = null;
			if (oneList.size() > 0) {
				for (Map<String, Object> map : oneList) {
					objx = new JSONObject();
					objx.accumulate("id", map.get("id")).accumulate("prodName", map.get("prod_name"))
							.accumulate("prodImage", map.get("prod_image"))
							.accumulate("prodDetailImage", map.get("prod_detail_image"))
							.accumulate("prodPrice", map.get("prod_price"))
							.accumulate("prodPeriodMonth", map.get("prodPeriodMonth"))
							.accumulate("clubNum", map.get("clubNum")).accumulate("saleNum", map.get("saleNum"));
					onecardlist.add(objx);
				}
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("prodList", onecardlist);

			// if (StringUtils.isEmpty(member.getMobilephone())) {
			// // 用户未验证手机号，跳转到验证手机号的页面
			// request.getRequestDispatcher("ecartoon-weixin/saveMobile.jsp").forward(request,
			// response);
			// }
			session.setAttribute("shareMember", null);
			session.setAttribute("shareMemberName", null);
			request.setAttribute("prodInfo", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "findOneCardList";
	}

	/**
	 * 健身卡详情
	 */
	public String oneCardDetail() {
		try {
			//确保当前操作有用户登录
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			
			int id = Integer.valueOf(request.getParameter("id"));
			String sql = "select tp.*, count(tpc.id) as clubCount from tb_product_v45 tp,tb_product_club_v45 tpc, tb_member m where tp.id = tpc.product  and m.id = tpc.club and m.longitude != '' and m.latitude != '' and tp.id = ?";
			Object[] obj = { id };
			List<Map<String, Object>> oneList = DataBaseConnection.getList(sql, obj);
			
			//查询当前健身卡销量
			String saleSql = " select count(*) saleCount from tb_product_order_v45 tpo,tb_product_v45 tp   "
                           + " where tpo.order_product = tp.id and tpo.status != '' and tpo.status != '0' and tp.id = ?";
			Object[] saleObj = {id};
			Map<String, Object>  saleMap = DataBaseConnection.getOne(saleSql, saleObj);
			
			JSONObject ret = new JSONObject();
			if (oneList.size() > 0) {
				Map<String, Object> map = oneList.get(0);
					 ret.accumulate("success", true)
						.accumulate("message", "OK")
				        .accumulate("id", map.get("id"))
						.accumulate("prod_name", map.get("PROD_NAME"))
						.accumulate("prod_price", map.get("PROD_PRICE"))
						.accumulate("prod_image", map.get("PROD_IMAGE"))
						.accumulate("prod_content", map.get("PROD_CONTENT"))
						.accumulate("prod_period", map.get("PROD_PERIOD"))
						.accumulate("prod_summary", map.get("PROD_SUMMARY"))
						.accumulate("clubCount", map.get("clubCount"))
						.accumulate("count", saleMap.get("saleCount") == null ? 0 : saleMap.get("saleCount"))
						;
				 request.setAttribute("ecartoonDetail", ret);
				return "oneCardDetail";
			} else {
				ret.accumulate("success", false).accumulate("message", "查询一卡通数据异常");
				response(ret);
				return "";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 门店分布列表以及门店明细
	 * 
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public String findProduct45Member() {

		if (pageInfo.getCurrentPage() != 2){
		
		String source = request.getParameter("source");
		try {
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId2.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}

			String eid = request.getParameter("id");// 一卡通id
			String lon = request.getParameter("longitude");
			String lat = request.getParameter("latitude");
			
			// 测试使用
//			String city = getAddress.getCity("114.033190", "30.540896");
			String city = "北京市";
			String locationCity = getAddress.getCity(lon, lat);
			if (locationCity.indexOf("武汉") != -1){
				city = locationCity;
			}
			Double longitude = null;
			Double latitude = null;
			if (!"".equals(lon) && lon != null && !"".equals(lat) && lat != null) {
				longitude = Double.valueOf(lon);
				latitude = Double.valueOf(lat);

				pageInfo = service.queryStore4EWX(city, pageInfo, longitude, latitude, eid);
				List<Map<String, Object>> clubList0 = pageInfo.getItems();
				JSONObject objx = null;
				JSONArray clubList1 = new JSONArray();

				DecimalFormat df2 = new DecimalFormat("###");
				for (Map<String, Object> map : clubList0) {
					objx = new JSONObject();
					int totalScore = Integer.valueOf(df2.format(map.get("totalScore")));
					int deviceScore = Integer.valueOf(df2.format(map.get("deviceScore")).toString());
					int evenScore = Integer.valueOf(df2.format(map.get("evenScore")).toString());
					int serviceScore = Integer.valueOf(df2.format(map.get("serviceScore")).toString());
					
					    objx.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
							.accumulate("image", map.get("image")).accumulate("longitude", map.get("longitude"))
							.accumulate("latitude", map.get("latitude")).accumulate("distance", map.get("distance"))
							.accumulate("address", map.get("address")).accumulate("mobilephone", map.get("mobilephone"))
							.accumulate("deviceScore", deviceScore)
							.accumulate("evenScore", evenScore)
							.accumulate("serviceScore",serviceScore)
							.accumulate("evaluateNum", map.get("evaluateNum"))
							.accumulate("star", totalScore);
				   clubList1.add(objx);
				}

				final JSONObject obj = new JSONObject();
					 obj.accumulate("success", true)
					    .accumulate("clubList", clubList1)
						.accumulate("eid", eid)
						.accumulate("pageInfo", getJsonForPageInfo())
						.accumulate("longitude", lon)
						.accumulate("latitude", lat);

				if ("1".equals(request.getParameter("ajax"))) {
					response(obj);
					return null;
				}
				request.setAttribute("club", obj);
			}


		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if("mdfb".equals(source)){
			return "map";
		 }
	}
		return "findProduct45Member";

}
	
	//用户登录
	public String mdfbLogin(){
		JSONObject obj = new JSONObject();
		try {
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null || member.getId() == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId2.jsp").forward(request, response);
				return null;
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}
			obj.accumulate("success", true).accumulate("message", "用户登录成功");
		} catch (Exception e) {
			obj.accumulate("success", false).accumulate("message", "用户登录异常").accumulate("异常原因： ", e);
			e.printStackTrace();
		}
		request.getSession().setAttribute("mdfbLogin", obj);
		return "mdfb2";
	}
	
	
	
	/**
	 * 门店分布列表以及门店明细
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void findStoreList() {
		JSONObject obj = new JSONObject();
		if (pageInfo.getCurrentPage() != 2){
		String source = request.getParameter("source");
		try {
			Member member = (Member) request.getSession().getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId2.jsp").forward(request, response);
			} else {
				member = (Member) service.load(Member.class, member.getId());
				request.getSession().setAttribute("member", member);
			}

			String eid = request.getParameter("id");// 一卡通id
			String lon = request.getParameter("longitude");
			String lat = request.getParameter("latitude");
			
			//判断用户上传的经纬度是否为空
			String city = "北京市";
			if (StringUtils.isEmpty(lon) || StringUtils.isEmpty(lat)) {
				city = "北京市";
				lon = "0";
				lat = "0";
			} else {
				String locationCity = getAddress.getCity(lon, lat);
				if (locationCity.indexOf("武汉") != -1){
					city = locationCity;
				}
			}

			Double longitude = null;
			Double latitude = null;
			if (!"".equals(lon) && lon != null && !"".equals(lat) && lat != null) {
				longitude = Double.valueOf(lon);
				latitude = Double.valueOf(lat);

				pageInfo = service.queryStore4EWX(city, pageInfo, longitude, latitude, eid);
				List<Map<String, Object>> clubList0 = pageInfo.getItems();
				JSONObject objx = null;
				JSONArray clubList1 = new JSONArray();

				DecimalFormat df2 = new DecimalFormat("###");
				for (Map<String, Object> map : clubList0) {
					objx = new JSONObject();
					int totalScore = Integer.valueOf(df2.format(map.get("totalScore")));
					int deviceScore = Integer.valueOf(df2.format(map.get("deviceScore")).toString());
					int evenScore = Integer.valueOf(df2.format(map.get("evenScore")).toString());
					int serviceScore = Integer.valueOf(df2.format(map.get("serviceScore")).toString());
					
					    objx.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
							.accumulate("image", map.get("image")).accumulate("longitude", map.get("longitude"))
							.accumulate("latitude", map.get("latitude")).accumulate("distance", map.get("distance"))
							.accumulate("address", map.get("address")).accumulate("mobilephone", map.get("mobilephone"))
							.accumulate("deviceScore", deviceScore)
							.accumulate("evenScore", evenScore)
							.accumulate("serviceScore",serviceScore)
							.accumulate("evaluateNum", map.get("evaluateNum"))
							.accumulate("star", totalScore);
				   clubList1.add(objx);
				}

				 
					 obj.accumulate("success", true)
					    .accumulate("clubList", clubList1)
						.accumulate("eid", eid)
						.accumulate("pageInfo", getJsonForPageInfo())
						.accumulate("longitude", lon)
						.accumulate("latitude", lat);
				session.setAttribute("club", obj);
				
				
			}
			if("mdfb".equals(source)){
				//跳转到map页面
				request.getRequestDispatcher("ecartoon-weixin/mdfbmap.jsp").forward(request, response);
			 }

		} catch (NumberFormatException e ) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
		
	}
		//响应结果到页面
		response(obj);
	
	

}
	
	
	
	
	
	

	/**
	 * 门店明细
	 */
	@SuppressWarnings("unchecked")
	public String findClubById() {
		// 门店id
		//测试数据
//		String id = "11865";
		String id = request.getParameter("id");

		// 门店明细信息
		pageInfo = service.queryClubById(pageInfo, id);
		List<Map<String, Object>> clubList0 = pageInfo.getItems();
		Map<String, Object> map = new HashMap<String, Object>();
		DecimalFormat df2 = new DecimalFormat("###");

		// 查询门店签到数
		String sql = "select count(id) count from tb_sign_in where memberAudit = ?";
		Object[] oobb = { id };
		int signCount = Integer.parseInt(DataBaseConnection.getOne(sql, oobb).get("count").toString());

		JSONObject objx = new JSONObject();
		if (clubList0.size() > 0) {
			map = clubList0.get(0);
			int	totalScore = Integer.valueOf(df2.format(map.get("totalScore")).toString());
			int deviceScore = Integer.valueOf(df2.format(map.get("deviceScore")).toString());
			int evenScore = Integer.valueOf(df2.format(map.get("evenScore")).toString());
			int serviceScore = Integer.valueOf(df2.format(map.get("serviceScore")).toString());
			
			objx.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
					.accumulate("image", map.get("image")).accumulate("longitude", map.get("longitude"))
					.accumulate("latitude", map.get("latitude")).accumulate("address", map.get("address"))
					.accumulate("mobilephone", map.get("mobilephone"))
					.accumulate("deviceScore", deviceScore)
					.accumulate("evenScore", evenScore)
					.accumulate("serviceScore", serviceScore)
					.accumulate("evaluateNum", map.get("evaluateNum")).accumulate("signCount", signCount)
					.accumulate("star", totalScore);
		}

		// 门店明细中的服务项目
		List<Map<String, Object>> list = service.queryProduct45MemberCourse(id);// 门店课程
		int courseCount = list.size();

		// 门店明细内评论列表

		// 评论数
		Map<String, Object> mapEvalCount = service.queryClubEvaluateNum(id);
		String type = "";

		// 好评 type = 1
		type = "1";
		pageInfo = service.queryStoreEvaluate(pageInfo, id, type, "1");// 评论列表
		JSONArray memberEvaluate1 = JSONArray.fromObject(pageInfo.getItems());
		// 好评数
		int evalCount1 = Integer.valueOf( mapEvalCount.get("Aevaluate").toString());
		JSONArray jarr1 = new JSONArray();
		if (memberEvaluate1.size() > 0) {
			for (int i = 0; i < memberEvaluate1.size(); i++) {
				JSONObject job = memberEvaluate1.getJSONObject(i);
				Map<String, Object> map1 = job;
				Member m = (Member) service.load(Member.class, job.getLong("mid"));
				getJsonForMember(m);
				map1.put("eval_content", DistanceUtil.decodeFromNonLossyAscii((String) map1.get("eval_content")));
				map1.put("taobaoId", m.getId().toString());
				jarr1.add(map1);
			}
		}

		// 中评 type= 2
		type = "2";
		pageInfo = service.queryStoreEvaluate(pageInfo, id, type, "1");// 评论列表
		JSONArray memberEvaluate2 = JSONArray.fromObject(pageInfo.getItems());
		// 中评数
		int evalCount2 = Integer.valueOf(mapEvalCount.get("Bevaluate").toString());
		JSONArray jarr2 = new JSONArray();
		if (memberEvaluate2.size() > 0) {
			for (int i = 0; i < memberEvaluate2.size(); i++) {
				JSONObject job = memberEvaluate2.getJSONObject(i);
				Map<String, Object> map2 = job;
				Member m = (Member) service.load(Member.class, job.getLong("mid"));
				getJsonForMember(m);
				map2.put("eval_content", DistanceUtil.decodeFromNonLossyAscii((String) map2.get("eval_content")));
				map2.put("taobaoId", m.getId().toString());
				jarr2.add(map2);
			}
		}

		// 差评 type= 3
		type = "3";
		pageInfo = service.queryStoreEvaluate(pageInfo, id, type, "1");// 评论列表
		JSONArray memberEvaluate3 = JSONArray.fromObject(pageInfo.getItems());
		// 差评数
		int evalCount3 = Integer.valueOf(mapEvalCount.get("Cevaluate").toString());
		JSONArray jarr3 = new JSONArray();
		if (memberEvaluate2.size() > 0) {
			for (int i = 0; i < memberEvaluate3.size(); i++) {
				JSONObject job = memberEvaluate3.getJSONObject(i);
				Map<String, Object> map3 = job;
				Member m = (Member) service.load(Member.class, job.getLong("mid"));
				getJsonForMember(m);
				map3.put("eval_content", DistanceUtil.decodeFromNonLossyAscii((String) map3.get("eval_content")));
				map3.put("taobaoId", m.getId().toString());
				jarr3.add(map3);
			}
		}

		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("club", objx).accumulate("course", list)
				.accumulate("courseCount", courseCount).accumulate("evaluate1", jarr1).accumulate("evaluate2", jarr2)
				.accumulate("evaluate3", jarr3).accumulate("evalCount1", evalCount1)
				.accumulate("evalCount2", evalCount2).accumulate("evalCount3", evalCount3)
				.accumulate("evaluateNum", mapEvalCount).accumulate("pageInfo", getJsonForPageInfo());
		request.setAttribute("club", obj);
		return "findClubById";
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