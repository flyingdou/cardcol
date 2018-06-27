package com.cardcolv45.mobile.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Certificate;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.plan.PlanRelease;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 教练 Action
 * 
 * @author hwj
 *
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MCoachV45ManageAction extends BasicJsonAction implements Constantsms {

	private static final long serialVersionUID = 4818947254616641572L;

	/**
	 * 被预约的课程
	 */
	public Long courseId;

	public String startDate, endDate;

	public Integer[] weeks;

	public File image1;

	public String image1FileName, image2FileName, image3FileName;

	public Long userId;

	public Long musicId;

	public Integer currentPage;

	/**
	 * 查询教练的简介
	 */
	public void detail() {

		try {
			String sql1 = "SELECT id, `name`, style, speciality, description FROM tb_member WHERE id = ?";
			String sql2 = "SELECT c.id projectId, c.name projectName, c.image projectImage, c.type projectType,p.name projectTypeName  FROM tb_course_info c,tb_parameter p WHERE c.type = p.id and c.member = ? order by projectId desc";
			String sql3 = "SELECT id certificateId, `name` certificateName, fileName certificateImage FROM tb_member_certificate WHERE member = ? order by certificateId desc";
			// 接收传递过来的教练Id
			int coachId = Integer.parseInt(request.getParameter("id"));
			// 教练的基本信息
			List<Map<String, Object>> baseInfo = service.queryForList(sql1, coachId);
			JSONArray baseInfoJarr = new JSONArray();
			if (baseInfo.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : baseInfo) {
					objx = new JSONObject();
					objx.accumulate("id", map.get("id")).accumulate("name", map.get("name"))
							.accumulate("style", map.get("style")).accumulate("speciality", map.get("speciality"))
							.accumulate("description", map.get("description"));
					baseInfoJarr.add(objx);
				}
			}

			// 该教练的服务项目
			List<Map<String, Object>> project = service.queryForList(sql2, coachId);
			JSONArray projectJarr = new JSONArray();
			if (project.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : project) {
					objx = new JSONObject();
					objx.accumulate("projectId", map.get("projectId")).accumulate("projectName", map.get("projectName"))
							.accumulate("projectType", map.get("projectType"))
							.accumulate("projectTypeName", map.get("projectTypeName"))
							.accumulate("projectImage", map.get("projectImage"));
					projectJarr.add(objx);
				}
			}

			// 该教练的证书
			List<Map<String, Object>> certificate = service.queryForList(sql3, coachId);
			JSONArray certificateJarr = new JSONArray();
			if (certificate.size() > 0) {
				JSONObject objx = null;
				for (Map<String, Object> map : certificate) {
					objx = new JSONObject();
					objx.accumulate("certificateId", map.get("certificateId"))
							.accumulate("certificateName", map.get("certificateName"))
							.accumulate("certificateImage", map.get("certificateImage"));
					certificateJarr.add(objx);
				}
			}

			JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("baseList", baseInfoJarr)
					.accumulate("projectList", projectJarr).accumulate("certificateList", certificateJarr);
			response(ret);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 访问教练首页数据
	 */
	public void findCoachHome() {
		// 获取当前登录用户
		Member currentMember = (Member) getMobileUser();
		// 根据id查询教练数据
		final Member member = (Member) service.load(Member.class, id);
		// final JSONObject obj = getMemberJson(member);
		// 查询“我的私教套餐”
		String privateSql = "";
		if (id.equals(currentMember.getId())) {
			// 当访问者教练本人时
			privateSql = "select p.id,p.image1,p.cost,p.name,p.isClose from tb_product p ,tb_member m   where p.type = 3  and m.id = p.member and m.role = 'S' and p.member = ? ORDER BY createTime desc ";
		} else {
			// 当访问者为会员或其他教练时
			privateSql = "select p.id,p.image1,p.cost,p.name,p.isClose from tb_product p ,tb_member m   where p.type = 3  and m.id = p.member and m.role = 'S' and p.member = ? and p.isClose = 2 and p.audit = 1 ORDER BY createTime desc ";
		}
		List<Map<String, Object>> privateList1 = DataBaseConnection.getList(privateSql,
				new Object[] { member.getId() });
		JSONArray privateList = JSONArray.fromObject(privateList1);

		// 查询“健身计划”
		String PlanSql = "";
		if (id.equals(currentMember.getId())) {
			// 当访问者教练本人时
			PlanSql = "select id,plan_name,image1,unit_price,briefing,isClose from tb_plan_release where member = ?  and audit = '1' ORDER BY publish_time desc ";
		} else {
			// 当访问者为会员或其他教练时
			PlanSql = "select id,plan_name,image1,unit_price,briefing,isClose from tb_plan_release where member = ?  and audit = '1' and isClose = 2  ORDER BY publish_time desc ";
		}
		// service.queryForList(PlanSql,new Object[]{member.getId()});
		List<Map<String, Object>> planList1 = DataBaseConnection.getList(PlanSql, new Object[] { member.getId() });

		JSONArray planList = JSONArray.fromObject(planList1);
		final JSONObject obj = new JSONObject();
		String querySql = "select IFNULL(s.average,0) socure,(select count(memberSign) from tb_sign_in where memberAudit = "
				+ id + ") count" + " from ( select (t.totality_score / t.count) average from"
				+ " (select sum(e.totality_score) totality_score,count(e.id) count from tb_member_evaluate e inner join tb_sign_in s"
				+ " on e.signIn = s.id where s.memberAudit = " + id + ") t) s";
		Map<String, Object> map1 = service.queryForMap(querySql);
		double socure = Double.parseDouble(String.valueOf(map1.get("socure")));
		obj.accumulate("success", true).accumulate("type", member.getRole()).accumulate("message", uuid)
				// 添加教练个人信息
				.accumulate("id", getMemberJson(member).get("id")).accumulate("name", getMemberJson(member).get("name"))
				.accumulate("image", getMemberJson(member).get("image"))
				.accumulate("mobilephone", getMemberJson(member).get("mobilephone"))
				// 计算教练 评分 评价人数
				.accumulate("appraiseCount", map1.get("count"))
				.accumulate("avgGrade", Integer.parseInt(String.valueOf(Math.round(socure))))
				// 添加“我的私教套餐”
				.accumulate("privateList", privateList)
				// 添加“我的健身计划”
				.accumulate("planList", planList);
		response(obj);
	}

	/**
	 * 发布健身计划接口 参数： uuid : String 用户唯一标识 id: Long 计划主键。 image1: File 计划图片一
	 * jsons: JSON数组， [{ planName: 计划名称， planType： 计划类型 A.瘦身减重 B.健美增肌 C.运动康复
	 * D.提高运动表现， scene： 适用场景 A.健身房 B.办公室 C.家庭 D.户外， applyObject： 适用对象 A.初级 B.中级
	 * C.高级， apparatuses： 所需器材， startDate： 计划开始时间， endDate： 计划结束时间， unitPrice：
	 * 销售价格， briefing： 计划简介， details： 计划详情， image1FileName： 计划图片一名称，
	 * member：保存用户id }];
	 */
	public void releasePlan() {
		// 模拟测试的json数据
		// String json = new JSONObject().accumulate("planName",
		// "A").accumulate("planType", "A")
		// .accumulate("scene", "A").accumulate("applyObject", "A")
		// .accumulate("apparatuses", "A").accumulate("startDate", "2015-11-02")
		// .accumulate("endDate", "2016-08-23").accumulate("unitPrice", 115.8)
		// .accumulate("briefing", "A").accumulate("details", "A")
		// .accumulate("image1FileName", "1").accumulate("image2FileName", "2")
		// .accumulate("image3FileName", "3").accumulate("member", 251)
		// .toString();
		// jsons = "[" + json + "]";

		try {
			// 创建格式化日期对象
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 将json参数编码转换为utf-8
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			log.error("发布计划JSON串：" + arr.toString());
			JSONObject obj = arr.getJSONObject(0);
			// 创建pojo对象并填充对象属性
			PlanRelease plan = new PlanRelease(id);
			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
			if (fileName1 != null) {
				plan.setImage1(fileName1);
			}
			plan.setPlanName(obj.getString("planName"));
			plan.setPlanType(obj.getString("planType"));
			plan.setScene(obj.getString("scene"));
			plan.setApplyObject(obj.getString("applyObject"));
			plan.setApparatuses(obj.getString("apparatuses"));
			plan.setStartDate(null != obj.get("startDate") ? sdf.parse(obj.getString("startDate")) : null);
			plan.setEndDate(null != obj.get("endDate") ? sdf.parse(obj.getString("endDate")) : null);
			plan.setUnitPrice(obj.getDouble("unitPrice"));
			plan.setBriefing(obj.getString("briefing"));
			plan.setAudit("1");
			plan.setIsClose("2");

			if (obj.containsKey("details")) {
				plan.setDetails(obj.getString("details"));
			}

			final Long userId = getMobileUser().getId();
			// 根据用户id查询用户对象数据
			final Member m = (Member) service.load(Member.class, userId);
			plan.setMember(m);
			if (obj.getString("member") != null) {
				Long plan_participant_long = Long.parseLong(obj.getString("member"));
				Member plan_participant = (Member) service.load(Member.class, plan_participant_long);
				plan.setPlan_participant(plan_participant);
			}
			plan.setPublishTime(new Date());
			plan.setPlanDay(
					(int) ((plan.getEndDate().getTime() - plan.getStartDate().getTime()) / 1000 / 60 / 60 / 24) + 1);

			// 执行数据库持久化操作
			plan = (PlanRelease) service.saveOrUpdate(plan);
			obj = new JSONObject().accumulate("success", true).accumulate("key", plan.getId());
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 新增教练专业资格
	 */
	public void insertMajor() {
		// 资格名称
		String majorName = request.getParameter("majorName");
		String fileName = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
		Member member = (Member) service.load(Member.class, id);
		if ("S".equals(member.getRole().toUpperCase())) {
			// 如果是教练才能添加专业资格证书
			Certificate certificate = new Certificate();
			certificate.setName(majorName);
			certificate.setFileName(fileName);
			certificate.setMember(id);
			certificate = (Certificate) service.saveOrUpdate(certificate);
			if (certificate != null) {
				response(new JSONObject().accumulate("success", true).accumulate("id", certificate.getId())
						.accumulate("image", certificate.getFileName()).accumulate("majorName", certificate.getName()));
			} else {
				response(new JSONObject().accumulate("success", false));
			}
		}
	}

	/**
	 * 删除教练资格证书
	 */
	public void deleteCertificate() {
		try {
			String cerId = request.getParameter("id");
			Certificate certificate = (Certificate) service.load(Certificate.class, Long.valueOf(cerId));
			if (getMobileUser().getId().equals(certificate.getMember())) {
				service.delete(Certificate.class, Long.valueOf(cerId));
				response(new JSONObject().accumulate("success", true).accumulate("message", "ok"));
			} else {
				response(new JSONObject().accumulate("success", false).accumulate("message", "删除失败,该证书不属于当前用户"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			response(new JSONObject().accumulate("success", false).accumulate("message", e));
		}
	}

	/**
	 * 保存或修改教练基本信息
	 */
	public void saveCoach() {
		// 测试数据
		// jsons =
		// "[{'style':'A','speciality':'%5BA%2C+C%5D','description':'%E4%BD%A0%E5%A5%BD'}]";

		JSONObject ret = new JSONObject();
		try {
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject obj = (JSONObject) arr.get(0);
			// 获取当前登录用户
			Member member = (Member) getMobileUser();
			member.setStyle(obj.getString("style"));
			member.setSpeciality(obj.getString("speciality"));
			member.setDescription(obj.getString("description"));
			service.saveOrUpdate(member);
			ret.accumulate("success", true).accumulate("message", "OK");
		} catch (UnsupportedEncodingException e) {
			ret.accumulate("success", false).accumulate("message", e);
			e.printStackTrace();
		}
		response(ret);

	}

	/**
	 * getter and setter
	 */
	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer[] getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer[] weeks) {
		this.weeks = weeks;
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public String getImage2FileName() {
		return image2FileName;
	}

	public void setImage2FileName(String image2FileName) {
		this.image2FileName = image2FileName;
	}

	public String getImage3FileName() {
		return image3FileName;
	}

	public void setImage3FileName(String image3FileName) {
		this.image3FileName = image3FileName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getMusicId() {
		return musicId;
	}

	public void setMusicId(Long musicId) {
		this.musicId = musicId;
	}

	@Override
	public Integer getCurrentPage() {
		return currentPage;
	}

	@Override
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
}
