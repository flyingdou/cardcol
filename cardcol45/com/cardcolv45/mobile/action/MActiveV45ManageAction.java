package com.cardcolv45.mobile.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.active.Active;
import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.course.Message;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.PageInfo;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.OpenimCustmsgPushRequest;
import com.taobao.api.request.OpenimCustmsgPushRequest.CustMsg;
import com.taobao.api.response.OpenimCustmsgPushResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MActiveV45ManageAction extends BasicJsonAction implements Constantsms {
	private static final long serialVersionUID = -4900715126840183203L;
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	// 筛选条件
	// 城市
	public String city;
	/**
	 * 活动查询类型，0 。默认查可参加的，1。为我发起的，2。为已参加的
	 */
	public String type;
	// 专长
	public String expertise;
	// 项目Id
	public Integer project;
	// 经度，纬度
	public Double longitude, latitude;
	// 图片
	public File image1;
	public String image1FileName;
	// 栏目代码值
	public String[] code;

	/**
	 * 关键字, 裁判
	 */
	private String keyword, judge;
	private String recomm_id;

	/**
	 * 手机推荐栏
	 */
	public void lists() {
		// 测试数据
		// code = new String[] { "D", "E", "F" };

		final List<Map<String, Object>> recomms = service.findRecommendBySectorCode(code);
		final JSONArray jarr = JSONArray.fromObject(recomms);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("items", jarr);
		response(obj);
	}

	/**
	 * 查询出距离最近的教练(分页/每页20条)
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void findCoach() {
		try {
			String jsons = request.getParameter("jsons") == null ? request.getParameter("Jsons")
					: request.getParameter("jsons");
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject json = arr.getJSONObject(0);
			String keyword = "";
			if (json.get("currentPage") != null) {
				pageInfo.setCurrentPage(json.getInt("currentPage"));
			}

			if (json.get("pageSize") != null) {
				pageInfo.setPageSize(json.getInt("pageSize"));
			}

			if (json.containsKey("keyword")) {
				keyword = json.getString("keyword");
			}
			longitude = json.getDouble("longitude");
			latitude = json.getDouble("latitude");
			if (json.containsKey("city")) {
				city = json.getString("city");
			}
			if (json.containsKey("project")) {
				project = json.getInt("project");
			}
			if (json.containsKey("type")) {
				type = json.getString("type");
			}
			if (json.containsKey("expertise")) {
				expertise = json.getString("expertise");
			}

			if (!"".equals(longitude) && !"".equals(latitude) && longitude != null && latitude != null) {
				// 计算距离
				String basicSql = "select m.id as id,m.name as name,m.image as image,m.longitude as longitude,"
						+ "m.latitude as latitude,m.count_socure count_socure,m.count_emp as count_emp,"
						+ "m.description as description,"
						+ "(round(6367000 * 2 * asin(sqrt(pow(sin(((latitude * pi()) / 180 - (? * pi()) / 180) / 2), 2) + "
						+ "cos((? * pi()) / 180) * cos((latitude * pi()) / 180) * pow(sin(((longitude * pi()) / 180 -"
						+ "(? * pi()) / 180) / 2), 2))))) as distance from tb_member as m";

				// 查询条件
				StringBuilder condition = new StringBuilder("");

				if (keyword != null && !"".equals(keyword)) {
					condition.append(" where m.grade = 1 and m.name like '%").append(keyword).append("%'");
				} else {
					if (project != null && !"".equals(project)) {
						condition.append(" inner join  tb_course_info as c on m.id = c.member where")
								.append(" m.grade = 1").append(" and c.id=").append(project);
					} else {
						condition.append(" where m.grade = 1");
					}
					if (city != null && !"".equals(city)) {
						condition.append(" and m.city like '%").append(city).append("%'");
					}
					if (type != null && !"".equals(type)) {
						condition.append(" and m.style='").append(type).append("'");
					}
					if (expertise != null && !"".equals(expertise)) {
						condition.append(" and m.speciality like '%").append(expertise).append("%'");
					}
				}
				condition.append(" and m.role='S'");

				String sql1 = "select t.* from (" + basicSql + condition + ") t where t.distance is not null";
				String sql2 = "select t.* from (" + basicSql + condition
						+ ") t where t.distance is not null order by t.distance asc limit ?,?";

				// 获取数据总数量
				String dataCount = "select count(tmp.id) as count from (" + sql1 + ") as tmp";
				Map<String, Object> value = (Map<String, Object>) DataBaseConnection
						.getList(dataCount, new Object[] { latitude, latitude, longitude }).get(0);
				int count = Integer.valueOf(String.valueOf(value.get("count")));

				// 查询的数据
				List<Map<String, Object>> list = DataBaseConnection.getList(sql2, new Object[] { latitude, latitude,
						longitude, (pageInfo.getCurrentPage() - 1) * pageInfo.getPageSize(), pageInfo.getPageSize() });

				// 转换为json
				JSONArray cArray = new JSONArray();
				JSONObject obj = null;
				for (Map<String, Object> map : list) {
					obj = new JSONObject();
					String querySql = "select IFNULL(s.average,0) socure,(select count(memberSign) from tb_sign_in where memberAudit = "
							+ map.get("id") + ") count" + " from ( select (t.totality_score / t.count) average from"
							+ " (select sum(e.totality_score) totality_score,count(e.id) count from tb_member_evaluate e inner join tb_sign_in s"
							+ " on e.signIn = s.id where s.memberAudit = " + map.get("id") + ") t) s";
					Map<String, Object> map1 = service.queryForMap(querySql);
					double socure = Double.parseDouble(String.valueOf(map1.get("socure")));
					map.put("count_socure", Integer.parseInt(String.valueOf(Math.round(socure))));
					map.put("count_emp", map1.get("count"));
					for (Iterator it = map.keySet().iterator(); it.hasNext();) {
						Object key = it.next();
						obj.accumulate(String.valueOf(key), map.get(key));
					}
					cArray.add(obj);
				}

				// 添加分页信息
				pageInfo.setTotalCount(count);
				int totalPage = count % pageInfo.getPageSize() == 0 ? count / pageInfo.getPageSize()
						: (count / pageInfo.getPageSize()) + 1;
				pageInfo.setTotalPage(totalPage);

				// 返回数据格式 : {success:true,items:[用户数据],pageInfo{分页信息}}
				obj = new JSONObject();
				obj.accumulate("success", true).accumulate("items", cArray).accumulate("pageInfo",
						getJsonForPageInfo());
				response(obj);
			} else {
				response(new JSONObject().accumulate("success", false).accumulate("msg", "请指定经纬度"));
			}
		} catch (Exception e) {
			response(e);
		}
	}

	// 发起挑战
	public void activeSavexxx() {
		// 测试数据
		// jsons = new JSONObject().accumulate("name",
		// "木兰山10日游").accumulate("days", 10).accumulate("image1", null)
		// .accumulate("target", "A").accumulate("award",
		// "10块钱").accumulate("amerceMoney", 10)
		// .accumulate("memo", "无").accumulate("institution", 9388).toString();

		JSONObject object = new JSONObject();
		try {
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "utf-8"));
			object = arr.getJSONObject(0);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Active active = new Active();
		active.setName(object.getString("name"));
		active.setDays(object.getInt("days"));
		String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
		active.setImage(fileName1);
		active.setTarget(object.getString("target").charAt(0));
		active.setAward(object.getString("award"));
		active.setAmerceMoney(object.getDouble("amerceMoney"));
		active.setMemo(object.getString("memo"));
		active.setCreateTime(new Date());
		active.setStatus('B');// 默认为开启状态
		active.setCreator((Member) getMobileUser());
		Member member = (Member) service.load(Member.class, object.getLong("institutionId"));
		if (member != null) {
			active.setInstitution(member);
		}
		active = (Active) service.saveOrUpdate(active);
		if (active != null) {
			response(new JSONObject().accumulate("success", true).accumulate("message", "OK").accumulate("key",
					active.getId()));
		} else {
			response(new JSONObject().accumulate("success", false).accumulate("message", "发起挑战失败"));
		}
	}

	/**
	 * 发起挑战
	 */
	@SuppressWarnings("unused")
	public void activeSave() {
		// jsons =
		// "[{\"name\":\"挑战名称\",\"target\":\"A\",\"days\":2,\"memo\":\"注意事项\",\"award\":\"奖励\",\"amerceMoney\":0.01,\"institutionId\":9355,\"amerceMoney\":0.01,\"value\":0.01}]";
		JSONArray arr;
		try {
			arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			final JSONObject obj = arr.getJSONObject(0);
			Active a = new Active();
			Member m = new Member();
			a.setStatus('B');
			a.setCreator(new Member(getMobileUser().getId()));
			a.setName(obj.getString("name"));
			a.setDays(obj.getInt("days"));
			a.setTarget(obj.getString("target").charAt(0));
			a.setMemo(obj.getString("memo"));
			a.setMode("A".charAt(0));
			a.setJoinMode("A".charAt(0));
			a.setAward(obj.getString("award"));
			a.setAmerceMoney(obj.getDouble("amerceMoney"));
			a.setInstitution(new Member(obj.getLong("institutionId")));
			a.setCategory(a.getTarget());
			a.setValue(obj.getDouble("value"));
			a.setCreateTime(new Date());
			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			if (fileName1 != null) {
				a.setImage(fileName1);
			}
			a = (Active) service.saveOrUpdate(a);
			JSONObject objs = new JSONObject();
			response(objs.accumulate("success", true).accumulate("key", a.getId()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 挑战成绩提交
	 */
	public void submitWeight() {
		Double weight = new Double(request.getParameter("weight"));// 用户填写的体重
		service.saveActiveResult(weight, id);
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("code", ao.getResult());
		response(obj);
	}

	/**
	 * 保存活动记录，jsons=[{weight: 80, waist: 30, hip: 50, fat: 30}]
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void saveRecord() {
		// jsons: [{merberId:用户id，doneDate: 完成时间，action: 有氧运动，actionQuan:
		// 运动量，fat: 体脂率,
		// waist: 腰围, hip： 臀围，memo: 备注，times: 次数,height:身高,
		// heartRate:运动后心率，运动时间：SportTime，运动计量单位：SportUnit，体重：weight,BMI：体质指数，WHR：腰臀比}]
		// 测试数据
		// update
		// jsons: [{merberId:用户id，doneDate: 完成时间，waist: 腰围, hip： 臀围，体重：weight}]
		// [{"merberId":"9476","doneDate":"2017-09-13","hip":"36","waist":"36","weight":"25"}]
		// JSONObject jsonObject =new JSONObject();
		// jsonObject.accumulate("memberId", 9476)
		// .accumulate("doneDate", "2017-09-13")
		// .accumulate("waist", 36)
		// .accumulate("hip", 36)
		// .accumulate("weight", 25);
		// JSONArray jsonArray = new JSONArray();
		// jsonArray.add(jsonObject);
		// String jsons = jsonArray.toString();

		// String jsons = request.getParameter("jsons");

		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			final JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("保存会员设置JSON串：" + arr.toString());
			final JSONObject obj = arr.getJSONObject(0);
			Long memberid = Long.parseLong(obj.get("memberId").toString());
			String recordtime = obj.getString("doneDate").replaceAll("-", "");
			List<?> blist = service.queryForList(
					"select * from tb_active_order ao where ao.member=? and ? >= DATE_FORMAT(ao.orderStartTime,'%Y%m%d') and DATE_FORMAT(ao.orderEndTime,'%Y%m%d')>? and ao.status=1",
					new Object[] { memberid, recordtime, recordtime });
			if (blist.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (Iterator<?> it = blist.iterator(); it.hasNext();) {
					Map<String, Object> map = (Map<String, Object>) it.next();
					sb = sb.append(map.get("id").toString());
					sb.append(",");
				}
				String s = sb.toString().substring(0, sb.length() - 1);
				obj.accumulate("activeId", s);
			}
			final List<TrainRecord> rs = new ArrayList<TrainRecord>();
			final String doneDate = obj.getString("doneDate");
			long judgeId;
			if (obj.containsKey("memberId") && obj.getString("memberId") != null) {
				judgeId = obj.getLong("memberId");
			} else {
				judgeId = id != null ? id : getMobileUser().getId();
			}
			final Long key = judgeId;
			final Setting set = service.loadSetting(key);
			if (obj.containsKey("activeId")) {
				final String[] strIds = obj.getString("activeId").split(",");
				for (int i = 0; i < strIds.length; i++) {
					final TrainRecord record = new TrainRecord();
					record.setHeight(Double.valueOf(set.getHeight()));
					record.setActiveOrder(new ActiveOrder(new Long(strIds[i].trim())));
					record.setDoneDate(sdf.parse(doneDate));
					if (obj.containsKey("action"))
						record.setAction(obj.getString("action"));
					if (obj.containsKey("actionQuan") && !StringUtils.isEmpty(obj.getString("actionQuan")))
						record.setActionQuan(obj.getInt("actionQuan"));
					if (obj.containsKey("fat") && !StringUtils.isEmpty(obj.getString("fat")))
						record.setFat(obj.getDouble("fat"));
					if (obj.containsKey("hip") && !StringUtils.isEmpty(obj.getString("hip")))
						record.setHip(obj.getDouble("hip"));
					record.setPartake(new Member(key));
					if (obj.containsKey("waist") && !StringUtils.isEmpty(obj.getString("waist")))
						record.setWaist(obj.getDouble("waist"));
					if (obj.containsKey("times") && !StringUtils.isEmpty(obj.getString("times")))
						record.setTimes(obj.getDouble("times"));
					if (obj.containsKey("weight") && !StringUtils.isEmpty(obj.getString("weight")))
						record.setWeight(obj.getDouble("weight"));
					if (obj.containsKey("memo"))
						record.setMemo(obj.getString("memo"));
					// 增加身体数据字段 身高 运动后心率
					/*
					 * if (obj.containsKey("height"))
					 * record.setHeight(obj.getDouble("height"));
					 */
					if (obj.containsKey("heartRate") && !StringUtils.isEmpty(obj.getString("heartRate")))
						record.setHeartRate(obj.getDouble("heartRate"));
					if (obj.containsKey("SportTime") && !StringUtils.isEmpty(obj.getString("SportTime")))
						record.setTimes(obj.getDouble("SportTime"));
					if (obj.containsKey("SportUnit"))
						record.setUnit(obj.getString("SportUnit"));
					if (obj.containsKey("SportScore") && !StringUtils.isEmpty(obj.getString("SportScore")))
						record.setScore(obj.getInt("SportScore"));
					rs.add(record);
				}
			} else {
				final TrainRecord record = new TrainRecord();
				record.setHeight(Double.valueOf(set.getHeight()));
				record.setDoneDate(sdf.parse(doneDate));
				if (obj.containsKey("action"))
					record.setAction(obj.getString("action"));
				if (obj.containsKey("actionQuan") && !StringUtils.isEmpty(obj.getString("actionQuan")))
					record.setActionQuan(obj.getInt("actionQuan"));
				if (obj.containsKey("fat") && !StringUtils.isEmpty(obj.getString("fat")))
					record.setFat(obj.getDouble("fat"));
				if (obj.containsKey("hip") && !StringUtils.isEmpty(obj.getString("hip")))
					record.setHip(obj.getDouble("hip"));
				record.setPartake(new Member(key));
				if (obj.containsKey("waist") && !StringUtils.isEmpty(obj.getString("waist")))
					record.setWaist(obj.getDouble("waist"));
				if (obj.containsKey("times") && !StringUtils.isEmpty(obj.getString("times")))
					record.setTimes(obj.getDouble("times"));
				if (obj.containsKey("weight") && !StringUtils.isEmpty(obj.getString("weight")))
					record.setWeight(obj.getDouble("weight"));
				if (obj.containsKey("memo"))
					record.setMemo(obj.getString("memo"));
				// 增加身体数据字段 身高 运动后心率
				/*
				 * if (obj.containsKey("height"))
				 * record.setHeight(obj.getDouble("height"));
				 */
				if (obj.containsKey("heartRate") && !StringUtils.isEmpty(obj.getString("heartRate")))
					record.setHeartRate(obj.getDouble("heartRate"));
				if (obj.containsKey("SportTime") && !StringUtils.isEmpty(obj.getString("SportTime")))
					record.setTimes(obj.getDouble("SportTime"));
				if (obj.containsKey("SportUnit") && !StringUtils.isEmpty(obj.getString("SportUnit")))
					record.setUnit(obj.getString("SportUnit"));
				if (obj.containsKey("SportScore") && !StringUtils.isEmpty(obj.getString("SportScore")))
					record.setScore(obj.getInt("SportScore"));
				rs.add(record);
			}
			service.saveOrUpdateRecord(rs, new Member(key), doneDate);

			// obj11.setFromUser(id != null ? id+"" :
			// getMobileUser().getId().toString());
			if (obj.containsKey("activeId")) {
				final String[] strIds = obj.getString("activeId").split(",");
				for (String id : strIds) {
					StringBuffer sb = new StringBuffer("from ActiveOrder A where 1=1 and ");
					ArrayList list_fir = new ArrayList();
					sb.append(" A.id = ? ");
					list_fir.add(Long.parseLong(id));
					List<?> list = service.findObjectBySql(sb.toString(), list_fir.toArray());
					ArrayList userList = new ArrayList();
					for (Object ob : list) {
						ActiveOrder a = (ActiveOrder) ob;
						Long jid = Long.parseLong(a.getJudge().toString());
						Message msg = new Message();
						msg.setContent(getMobileUser().getName() + doneDate + "的训练日志");
						msg.setIsRead("0");
						msg.setMemberFrom(new Member(getMobileUser().getId()));
						msg.setMemberTo(new Member(jid));
						msg.setSendTime(new Date());
						msg.setStatus("1");
						msg.setType("4");
						service.saveOrUpdate(msg);
						if (jid != null) {
							userList.add(jid);
							break;
						}
					}
					if (!userList.isEmpty()) {
						String url = "http://gw.api.taobao.com/router/rest";
						TaobaoClient client = new DefaultTaobaoClient(url, "23330566",
								"0c6b77f937d49f3a14ca98a6316d110e");
						OpenimCustmsgPushRequest req = new OpenimCustmsgPushRequest();
						CustMsg obj11 = new CustMsg();
						obj11.setFromUser("some_one");
						obj11.setToAppkey("0");
						obj11.setToUsers(userList);
						obj11.setSummary("用户" + doneDate + "的运动成绩");
						obj11.setData("challenge");
						obj11.setAps("{\"alert\":\"裁判挑战成绩\"}");
						obj11.setApnsParam("challenge");
						obj11.setInvisible(1L);
						obj11.setFromNick(getMobileUser().getName());
						req.setCustmsg(obj11);
						OpenimCustmsgPushResponse rsp = client.execute(req);
						System.out.println(rsp.getBody());
					}
				}
			}
			final JSONObject obj1 = new JSONObject();
			obj1.accumulate("success", true).accumulate("message", "OK").accumulate("id", rs.get(0).getId());
			response(obj1);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 展示运动记录
	 */
	public void showRecord() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			Date date = new Date();

			String sysDate = String.format("%tY-%tm-%td", date, date, date);

			Date doneDate = sdf.parse(request.getParameter("doneDate"));

			if (doneDate == null) {
				doneDate = sdf.parse(sysDate);
			}
			long memberId = getMobileUser().getId();
			if (request.getParameter("memberId") != null) {
				memberId = Long.parseLong(request.getParameter("memberId").toString());
			}
			final List<?> list = service.findObjectBySql(
					"from TrainRecord r where r.partake.id = ? and r.doneDate = ? ",
					new Object[] { memberId, doneDate });

			final JSONArray jarr = new JSONArray();

			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final TrainRecord tr = (TrainRecord) it.next();
				final JSONObject obj = new JSONObject();

				// id:主键 ，doneDate:完成时间,weight:体重,waist:腰围,hip:臀围
				obj.accumulate("id", tr.getId()).accumulate("doneDate", sdf.format(tr.getDoneDate()))
						.accumulate("weight", getDouble(tr.getWeight())).accumulate("waist", getDouble(tr.getWaist()))
						.accumulate("hip", getDouble(tr.getHip()));
				// .accumulate("times", getDouble(tr.getTimes()))
				// .accumulate("fat", getDouble(tr.getFat()))
				// .accumulate("actionQuan", getInteger(tr.getActionQuan()))
				// .accumulate("strength", getDouble(tr.getStrength()))
				// .accumulate("heartRate", getDouble(tr.getHeartRate()))
				// .accumulate("height", getDouble(tr.getHeight()))
				// .accumulate("activeOrder", tr.getActiveOrder() == null ? "" :
				// tr.getActiveOrder().toJson())
				// .accumulate("BMI", tr.getBmi()).accumulate("WHR",
				// tr.getWaistHip())
				// .accumulate("SportTime",
				// getDouble(tr.getTimes())).accumulate("SportUnit",
				// tr.getUnit())
				// .accumulate("action", tr.getAction());
				jarr.add(obj);
			}

			Setting setting = new Setting();
			setting = service.loadSetting(memberId);

			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("items", jarr).accumulate("bmiLow", setting.getBmiLow())
					.accumulate("bmiHigh", setting.getBmiHigh());
			response(ret);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查找慈善机构列表
	 */
	public void findInstitution() {
		try {
			final List<?> list_login = service.findObjectBySql("from Member m where m.id = ? ",
					getMobileUser().getId());// 当前登录用户
			final List<?> list = service.findObjectBySql("from Member m where m.role = ? and m.grade=1", "I");// 慈善机构
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member in = (Member) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", in.getId()).accumulate("name", in.getName()).accumulate("city", in.getCity())
						.accumulate("sex", in.getSex()).accumulate("nick", in.getNick())
						.accumulate("image", in.getImage()).accumulate("role", in.getRole())
						.accumulate("lng", in.getLongitude()).accumulate("lat", in.getLatitude())
						.accumulate("mobilephone", in.getMobilephone()).accumulate("mobileValid", in.getMobileValid())
						.accumulate("description", in.getDescription());
				jarr.add(obj);
			}
			for (final Iterator<?> it = list_login.iterator(); it.hasNext();) {
				final Member in = (Member) it.next();
				final JSONObject obj = new JSONObject();
				obj.accumulate("id", in.getId()).accumulate("name", in.getName()).accumulate("city", in.getCity())
						.accumulate("sex", in.getSex()).accumulate("nick", in.getNick())
						.accumulate("image", in.getImage()).accumulate("role", in.getRole())
						.accumulate("lng", in.getLongitude()).accumulate("lat", in.getLatitude())
						.accumulate("mobilephone", in.getMobilephone()).accumulate("mobileValid", in.getMobileValid())
						.accumulate("description", in.getDescription());
				jarr.add(obj);
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("items", jarr);
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 查询挑战列表
	 */
	@SuppressWarnings("deprecation")
	public void findActiveAndDetail() {
		try {
			PageInfo pp = pageInfo;
			// 挑战类型
			Integer type = 0;

			if (this.type != null) {
				type = Integer.parseInt(this.type);
			}

			// 挑战目标:A体重减少,B体重增加,D运动次数
			Character target = null;
			if (StringUtils.isNotEmpty(request.getParameter("target"))) {
				target = request.getParameter("target").charAt(0);
			}
			// 搜索关键字
			String keyword = "";
			keyword = request.getParameter("keyword") == null ? null
					: "%" + URLDecoder.decode(request.getParameter("keyword"), "UTF-8") + "%";
			System.err.println(keyword);

			// 挑战模式:A 个人挑战
			String circle = request.getParameter("circle");
			// 挑战周期 :A:小于10天 ,B:11-30天,C:31-90天 D:大于90天
			Character mode = null;
			if (StringUtils.isNotEmpty(request.getParameter("mode"))) {
				mode = request.getParameter("mode").charAt(0);
			}

			BaseMember mu = getLoginMember();
			JSONArray jarr = new JSONArray();
			if (type == 0 || type == 1) {
				final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
				dc.createAlias("creator", "c", Criteria.LEFT_JOIN);
				if (keyword != null && !"".equals(keyword)) {
					dc.add(this.or(Restrictions.like("name", keyword), Restrictions.like("c.nick", keyword),
							Restrictions.like("c.email", keyword), Restrictions.like("c.mobilephone", keyword)));
				}
				if (type == 0) {
					dc.add(Restrictions.eq("status", 'B'));
					if (null != target && !"".equals(target)) {
						if (target == 'A') {
							dc.add(Restrictions.or(Restrictions.eq("category", 'A'), Restrictions.eq("category", 'B')));
						} else if (target == 'C') {
							dc.add(Restrictions.eq("category", 'E'));
						} else if (target == 'D') {
							dc.add(Restrictions.eq("category", 'D'));
						} else {
							dc.add(Restrictions.in("category", new Object[] { 'C', 'F', 'G', 'H' }));
						}
					}
				} else {
					dc.add(Restrictions.eq("c.id", mu.getId()));
					if (null != target && !"".equals(target))
						dc.add(Restrictions.eq("target", target));
				}
				if (null != mode && !"".equals(mode)) {
					dc.add(Restrictions.eq("mode", mode));
				}
				if (circle != null && !"".equals(circle)) {
					if ("A".equals(circle)) {
						dc.add(Restrictions.le("days", 10));
					} else if ("B".equals(circle)) {
						dc.add(Restrictions.ge("days", 11));
						dc.add(Restrictions.le("days", 30));
					} else if ("C".equals(circle)) {
						dc.add(Restrictions.ge("days", 31));
						dc.add(Restrictions.le("days", 90));
					} else if ("D".equals(circle)) {
						dc.add(Restrictions.ge("days", 91));
					}
				}
				pageInfo.setOrder("createTime");
				pageInfo.setOrderFlag("desc");
				pageInfo = service.findPageByCriteria(dc, pageInfo);
				if (pageInfo != null && pageInfo.getItems() != null) {
					for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
						final Object[] objs = (Object[]) it.next();
						final Active m = (Active) objs[1];
						final JSONObject obj = new JSONObject();
						obj.accumulate("id", m.getId()).accumulate("member", getMemberJson(m.getCreator()))
								.accumulate("name", getString(m.getName())).accumulate("mode", getString(m.getMode()))
								.accumulate("days", getInteger(m.getDays()))
								.accumulate("teamNum", getInteger(m.getTeamNum()))
								.accumulate("target", getString(m.getTarget()))
								.accumulate("award", getString(m.getAward()))
								.accumulate("institution", getJsonForMember(m.getInstitution()))
								.accumulate("amerceMoney", getDouble(m.getAmerceMoney()))
								.accumulate("category", getString(m.getCategory()))
								.accumulate("status", getString(m.getStatus()))
								.accumulate("action", getString(m.getAction()))
								.accumulate("value", getDouble(m.getValue()))
								.accumulate("applyCount", applyCount(m.getId()))
								.accumulate("image", getString(m.getImage())).accumulate("memo", getString(m.getMemo()))
								.accumulate("result", null).accumulate("startTime", null).accumulate("endTime", null)
								.accumulate("weight", null).accumulate("activeCount", null)
								.accumulate("lastWeight", null).accumulate("activeId", null);
						jarr.add(obj);
					}
				}
			} else {
				final DetachedCriteria dc = DetachedCriteria.forClass(ActiveOrder.class);
				dc.add(Restrictions.eq("member.id", mu.getId()));
				dc.add(Restrictions.and(Restrictions.eq("status", '1'), Restrictions.isNotNull("status")));
				pageInfo.setOrder("orderDate");
				pageInfo.setOrderFlag("desc");
				pageInfo = service.findPageByCriteria(dc, pageInfo);
				for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
					final ActiveOrder ao = (ActiveOrder) it.next();
					final JSONObject obj = new JSONObject();
					int a = ao.getActive().getDays();// 挑战天数
					Date startDate = ao.getOrderStartTime();// 开始时间
					if (startDate == null) {
						startDate = new Date(System.currentTimeMillis());
					}
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(startDate);
					calendar.add(Calendar.DATE, a);
					Date endDate = calendar.getTime();// 结束时间
					// 活动状态：0为进行中，1为成功，2为失败，3为已结束
					Character result = "0".equals(ao.getResult()) ? '0'
							: (ao.getResult() != null ? ao.getResult() : '3');
					obj.accumulate("id", ao.getId()).accumulate("member", getMemberJson(ao.getActive().getCreator()))
							.accumulate("name", getString(ao.getActive().getName()))
							.accumulate("days", getInteger(ao.getActive().getDays()))
							.accumulate("teamNum", getInteger(ao.getActive().getTeamNum()))
							.accumulate("target", getString(ao.getActive().getTarget()))
							.accumulate("award", getString(ao.getActive().getAward()))
							.accumulate("institution", getMemberJson(ao.getActive().getInstitution()))
							.accumulate("amerceMoney", getDouble(ao.getActive().getAmerceMoney()))
							.accumulate("category", getString(ao.getActive().getCategory()))
							.accumulate("status", getString(ao.getStatus()))
							.accumulate("action", getString(ao.getActive().getName()))
							.accumulate("value", getDouble(ao.getActive().getValue()))
							.accumulate("applyCount", applyCount(ao.getActive().getId()))
							.accumulate("image", getString(ao.getActive().getImage()))
							.accumulate("startTime", sdf.format(startDate)).accumulate("endTime", sdf.format(endDate))
							.accumulate("weight", ao.getWeight()).accumulate("lastWeight", ao.getLastWeight())
							.accumulate("activeCount", this.signCount(ao.getId(), endDate)).accumulate("result", result)
							.accumulate("activeId", ao.getActive().getId());
					jarr.add(obj);
				}
			}

			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo",
					pageInfo == null ? new JSONObject().accumulate("currentPage", pp.getCurrentPage())
							.accumulate("pageSize", pp.getPageSize()).accumulate("totalCount", pp.getTotalCount())
							.accumulate("totalPage", pp.getTotalPage()) : getJsonForPageInfo())
					.accumulate("items", jarr);
			response(ret);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 计算挑战活参与总数
	 */
	public Long applyCount(Long id) {
		Long count = null;
		if (id != null) {
			count = service.queryForLong(
					"select count(*) from tb_active_order ci where ci.active = ? and ci.status != '0' ", id);
		}
		return count;
	}

	/**
	 * 挑战健身次数
	 * 
	 * @param id
	 * @return
	 */
	public Long signCount(Long id, Date date) {
		SimpleDateFormat ymd = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Long count = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);// 今天+1天
		Date endDate = calendar.getTime();
		ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		if (id != null) {
			count = service.queryForLong(
					"select count(*) from tb_sign_in where  signDate>= ? and signDate < ? and memberSign=?",
					ymd.format(ao.getOrderStartTime() == null ? new Date(System.currentTimeMillis())
							: ao.getOrderStartTime()),
					ymd.format(endDate), getMobileUser().getId());
		}
		return count;
	}

	@SuppressWarnings("unused")
	public void getActive() {
		Active a = (Active) service.load(Active.class, id);
		JSONObject obj1 = new JSONObject();
		JSONArray jarr = new JSONArray();
		obj1.accumulate("id", a.getId()).accumulate("member", getMemberJson(a.getCreator()))
				.accumulate("name", getString(a.getName())).accumulate("mode", getString(a.getMode()))
				.accumulate("days", getInteger(a.getDays())).accumulate("teamNum", getInteger(a.getTeamNum()))
				.accumulate("target", getString(a.getTarget())).accumulate("award", getString(a.getAward()))
				.accumulate("institution", getJsonForMember(a.getInstitution()))
				.accumulate("amerceMoney", getDouble(a.getAmerceMoney()))
				.accumulate("joinMode", getString(a.getJoinMode())).accumulate("category", getString(a.getCategory()))
				.accumulate("status", getString(a.getStatus())).accumulate("action", getString(a.getAction()))
				.accumulate("value", getDouble(a.getValue())).accumulate("image", getString(a.getImage()))
				.accumulate("memo", getString(a.getMemo())).accumulate("content", getString(a.getContent()))
				.accumulate("customTareget", getString(a.getCustomTareget())).accumulate("unit", getString(a.getUnit()))
				.accumulate("evaluationMethod", getString(a.getEvaluationMethod()))
				.accumulate("applyCount", applyCount(id));
		response(obj1);
	}

	/**
	 * getter and setter
	 */
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExpertise() {
		return expertise;
	}

	public void setExpertise(String expertise) {
		this.expertise = expertise;
	}

	public Integer getProject() {
		return project;
	}

	public void setProject(Integer project) {
		this.project = project;
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public String[] getCode() {
		return code;
	}

	public void setCode(String[] code) {
		this.code = code;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getJudge() {
		return judge;
	}

	public void setJudge(String judge) {
		this.judge = judge;
	}

	public String getRecomm_id() {
		return recomm_id;
	}

	public void setRecomm_id(String recomm_id) {
		this.recomm_id = recomm_id;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

}
