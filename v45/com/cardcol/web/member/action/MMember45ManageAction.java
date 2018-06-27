package com.cardcol.web.member.action;

import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.cardcol.web.utils.DistanceUtil;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.course.Message;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.system.Area1;
import com.freegym.web.utils.JSONUtils;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.LnglatUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MMember45ManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2878727546042430356L;
	/**
	 * 坐标城市
	 */
	protected String city;
	/**
	 * 经度，纬度
	 */
	private Double lng, lat;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	/**
	 * 加载我的资料
	 */
	public void findMemberDetail() {
		Map<String, Object> map = service.queryMemberDetail(getMobileUser()
				.getId().toString());
		map.put("pickAccount", service.queryPickAccount(getMobileUser()
				.getId().toString()));
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("memberDetail",
				map);
		response(obj);
	}

	/**
	 * 购买专家系统时保存会员设置
	 */
	public void saveMemberSetting() {
		// jsons =
		// "[{\"member\":\"9355\",\"weight\":\"40\",\"height\":\"140\",\"target\":\"1\",\"maxwm\":\"60\",\"waistline\":\"60\",\"sex\":\"M\",\"favoriateCardio\":\"游泳,跑步\"}]";
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons,
					"UTF-8"));
			log.error("保存会员设置JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			Setting setting = new Setting();
			setting = service.loadSetting(obj.getLong("member"));
			if (null == setting.getId()) {
				setting.setId(id);
			}
			setting.setMember(obj.getLong("member"));
			setting.setTarget(obj.getString("target"));
			setting.setHeight(obj.getInt("height"));
			setting.setWeight(obj.getDouble("weight"));
			setting.setWaistline(obj.getDouble("waistline"));
			setting.setMaxwm(obj.getDouble("maxwm"));
			setting.setFavoriateCardio(obj.getString("favoriateCardio"));
			if (obj.containsKey("sex")) {
				Member m = (Member) service.load(Member.class,
						obj.getLong("member"));
				m.setSex(obj.getString("sex"));
				service.saveOrUpdate(m);
			}
			setting = (Setting) service.saveOrUpdate(setting);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("key", setting.getId());
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 昵称修改
	 */
	public void saveName() {
		try {
			final String name = URLDecoder.decode(request.getParameter("name"),
					"utf-8");
			Member m = (Member) service.load(Member.class, getMobileUser()
					.getId());
			m.setName(name);
			service.saveOrUpdate(m);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 保存用户地区
	 */
	public void saveArea() {
		try {
			final String province = URLDecoder.decode(
					request.getParameter("province"), "utf-8");
			final String city = URLDecoder.decode(request.getParameter("city"),
					"utf-8");
			final String county = URLDecoder.decode(
					request.getParameter("county"), "utf-8");
			Member m = (Member) service.load(Member.class, getMobileUser()
					.getId());
			m.setProvince(province);
			m.setCity(city);
			m.setCounty(county);
			service.saveOrUpdate(m);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 手机端用户头像上传
	 */
	public void saveImage() {
		try {
			final Member m = (Member) service.load(Member.class,
					getMobileUser().getId());
			final JSONObject ret = new JSONObject();
			if (file.getFile() != null) {
				m.setImage(saveFile("picture", m.getImage()));
				service.saveOrUpdate(m);
				ret.accumulate("success", true).accumulate("message", "OK");
			} else {
				ret.accumulate("success", false).accumulate("code", -1)
						.accumulate("message", "上传文件请以“file.file”属性名称上传！");
			}
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 健友查询,依据经纬度进行处理
	 */
	@SuppressWarnings("unchecked")
	public void findFriend() {
		try {
			setCity(uuid, lng, lat, city);
			final Member member = (Member) service.load(Member.class,
					getMobileUser().getId());
			member.setLongitude(lng);
			member.setLatitude(lat);
			service.saveOrUpdate(member);
			final BaseMember mu = getMobileUser();
			final Double currLng = mu.getLongitude();
			final Double currLat = mu.getLatitude();
			double[] around = LnglatUtil.getAround(currLat, currLng, 3 * 1000);
			final List<Map<String, Object>> list = service
					.queryForList(
							"select *,tp.traincount/m.workoutTimes *100 finishrate from tb_member m LEFT JOIN (select t.partake , sum(t.times) time, count(*) traincount from tb_plan_record t GROUP BY t.partake) tp on m.id = tp.partake where m.id <> ? and m.role in ('S','M') and m.longitude between ? and ? and m.latitude between ? and ?",
							new Object[] { mu.getId(), around[1], around[3],
									around[0], around[2] });
			final JSONObject ret = new JSONObject();
			final JSONArray jarr = new JSONArray();
			DecimalFormat df = new DecimalFormat("#.00");
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Map<String, Object> map = (Map<String, Object>) it.next();
				Long mid = Long.parseLong(map.get("id").toString());
				Member m = (Member) service.load(Member.class, mid);
				Long evaluatecount = service
						.queryForLong(
								"SELECT count(*) evaluatecount  FROM tb_sign_in where memberSign =?",
								mid);
				Setting set = service.loadSetting(mid);
				final Double distance = LnglatUtil.GetDistance( currLat,
						currLng, m.getLatitude() , m.getLongitude());
				final JSONObject obj = getMemberJson(m);
				obj.accumulate("distance", distance)
						.accumulate(
								"time",
								map.get("time") == null ? 0 : String.valueOf(df
										.format(Double.parseDouble(map.get(
												"time").toString()) / 60)))
						.accumulate(
								"finishrate",
								map.get("finishrate") == null ? 0 : map.get(
										"finishrate").toString())
						.accumulate("heart",
								set.getHeart() == null ? 0 : set.getHeart())
						.accumulate("height",
								set.getHeight() == null ? 0 : set.getHeight())
						.accumulate("evaluatecount", evaluatecount)
						.accumulate("taobaoId", m.getId().toString())
						.put("count",
								map.get("traincount") == null ? 0 : map.get(
										"traincount").toString());
				jarr.add(obj);
			}
			JSONUtils.bubbleSort(jarr, true, "distance");
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 城市切换列表
	 */
	public void changeCity() {
		try {
			final List<?> list = service
					.findObjectBySql("from Area1 ta where open = '1' and type = '1' order by ta.id");
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Area1 tr = (Area1) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", tr.getId()).accumulate("cityname",
						tr.getName());
				jarr.add(obj);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}

	}

	/**
	 * 我的足迹
	 */
	public void mySignIn() {
		pageInfo = service.queryMySignIn(pageInfo, getMobileUser().getId()
				.toString());
		JSONArray mySignIn = JSONArray.fromObject(pageInfo.getItems());
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("mySignIn", mySignIn)
				.accumulate("pageInfo", getJsonForPageInfo());
		response(obj);
	}

	/**
	 * 点评详情
	 */
	public void memberEvaluateDetail() {
		String id = request.getParameter("id");// 评价表id
		Map<String, Object> map = service.queryMemberEvaluateDetail(id);
		map.put("EVAL_CONTENT", DistanceUtil.decodeFromNonLossyAscii((String)map.get("EVAL_CONTENT")))	;
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("evaluateDetail", map);
		response(obj);
	}
	
	
	/**
	 * 申请加入俱乐部
	 */
	@SuppressWarnings("unused")
	public void request() {
		try {
			final BaseMember mu = getMobileUser();
			final Long mid = mu.getId();
			// 增加申请加入私教
			Member memto = (Member) service.load(Member.class, id);
			List<?> fList = service.findObjectBySql("from Friend f where f.member.id = ? and f.friend.id = ? and type = ?", new Object[] { mid, id, "1" });
			if (fList.size() > 0) throw new LogicException("您已经加入了该俱乐部，不得重复加入！");
			final Message msg = new Message();
				msg.setContent("申请加入俱乐部！");
			msg.setIsRead("0");
			msg.setMemberFrom(new Member(mid));
			msg.setMemberTo(new Member(id));
			msg.setSendTime(new Date());
			msg.setStatus("1");
			msg.setType("1");
			service.saveOrUpdate(msg);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
}