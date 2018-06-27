package com.cardcolv45.mobile.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.mobile.BasicJsonAction;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.system.Parameter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MCourseInfoV45ManageAction extends BasicJsonAction {

	private static final long serialVersionUID = 1L;

	private File imageCourseInfo;

	private String imageCourseInfoFileName;

	/**
	 * 教练服务项目
	 */
	public void saveCourseInfo() {
		try {
			// 测试数据
			// JSONObject jsonObject = new JSONObject();
			// jsonObject.accumulate("userId", 9388).accumulate("typeId", 55)
			// .accumulate("image", "2017-08-11.jpg").accumulate("name",
			// "木兰湖环湖山地车赛")
			// .accumulate("intensity", 1).accumulate("memo", "木兰文化旅游节")
			// .accumulate("sort",0 );
			// JSONArray jsonArray = new JSONArray();
			// jsonArray.add(jsonObject);
			// String jsons=jsonArray.toString();

			// arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			// log.error("保存会员设置JSON串：" + arr.toString());
			// final JSONObject obj = arr.getJSONObject(0);
			JSONObject obj = new JSONObject();
			if (request.getParameter("name") != null && !"".equals(request.getParameter("name"))) {
				obj.accumulate("name", request.getParameter("name"));
			}
			if (request.getParameter("memo") != null && !"".equals(request.getParameter("memo"))) {
				obj.accumulate("memo", request.getParameter("memo"));
			}
			if (request.getParameter("intensity") != null && !"".equals(request.getParameter("intensity"))) {
				obj.accumulate("intensity", request.getParameter("intensity"));
			}
			if (request.getParameter("sort") != null && !"".equals(request.getParameter("sort"))) {
				obj.accumulate("sort", request.getParameter("sort"));
			}
			if (request.getParameter("userId") != null && !"".equals(request.getParameter("userId"))) {
				obj.accumulate("userId", request.getParameter("userId"));
			}
			if (request.getParameter("typeId") != null && !"".equals(request.getParameter("typeId"))) {
				obj.accumulate("typeId", request.getParameter("typeId"));
			}

			CourseInfo courseInfo = new CourseInfo();
			Member member = new Member();
			Parameter ty = new Parameter();
			if (obj.size() > 0) {
				String fileName1 = saveFile("picture", imageCourseInfo, imageCourseInfoFileName, null);
				if (!"".equals(fileName1) && null != fileName1) {
					courseInfo.setImage(fileName1);
				}
				if (obj.containsKey("name") && obj.get("name") != null) {
					courseInfo.setName(obj.getString("name"));
				}
				if (obj.containsKey("memo") && obj.get("memo") != null) {
					courseInfo.setMemo(obj.getString("memo"));
				}
				if (obj.containsKey("intensity") && obj.get("intensity") != null) {
					courseInfo.setIntensity(obj.getInt("intensity"));
				}
				if (obj.containsKey("sort") && obj.get("sort") != null) {
					courseInfo.setSort(obj.getInt("sort"));
				}
				if (obj.containsKey("userId") && obj.get("userId") != null) {
					// 将当前登录用户查出来
					member = (Member) service.load(Member.class, (long) obj.getInt("userId"));
					courseInfo.setMember(member);
				}
				if (obj.containsKey("typeId") && obj.get("typeId") != null) {
					ty = (Parameter) service.load(Parameter.class, (long) obj.getInt("typeId"));
					courseInfo.setType(ty);
				}

				courseInfo = (CourseInfo) service.saveOrUpdate(courseInfo);
				final JSONObject obj1 = new JSONObject();
				// 设置添加成功后，需要返回的数据
				obj1.accumulate("success", true).accumulate("message", "OK")
						.accumulate("image", getString(courseInfo.getImage()))
						.accumulate("name", getString(courseInfo.getName()))
						.accumulate("memo", getString(courseInfo.getMemo()))
						.accumulate("intensity", getInteger(courseInfo.getIntensity()))
						.accumulate("sort", getInteger(courseInfo.getSort())).accumulate("userId", member.getId())
						.accumulate("typeId", ty.getId()).accumulate("key", courseInfo.getId());// 返回当前业务的主键
				response(obj1);
			} else {
				final JSONObject obj2 = new JSONObject();
				// 设置添加失败后，需要返回的数据
				obj2.accumulate("success", false).accumulate("message", "请输入要保存的服务项目数据！");
				response(obj2);
			}
		} catch (LogicException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除服务项目
	 */
	public void deleteCourseInfo() {
		JSONObject obj = new JSONObject();
		try {
			long courseInfoId = Long.parseLong(request.getParameter("courseInfoId").toString());
			// 获取当前登录用户
			String sqlx = "select co.* from tb_course c,tb_courserelease_order co,tb_course_info ci where c.id = co.course and ci.id = c.courseid and  now()<=co.orderEndTime and  ci.id = ?";
			Object[] objx = { courseInfoId };
			List<Map<String, Object>> listx = DataBaseConnection.getList(sqlx, objx);
			if (listx.size() <= 0) {// 该courseInfo下没有有效的课程订单
				Member member = (Member) getMobileUser();
				// 将要删除的服务项目加载出来
				CourseInfo courseInfo = (CourseInfo) service.load(CourseInfo.class, courseInfoId);
				long cmid = courseInfo.getMember().getId();
				// 判断当前用户是否是要被删除的服务项目的发布者(教练)
				if (cmid == member.getId()) {
					// 验证通过，执行删除操作
					courseInfo.setMember(null);
					service.saveOrUpdate(courseInfo);
					obj.accumulate("success", true).accumulate("message", "OK");
				} else {
					obj.accumulate("success", false).accumulate("message", "你不是该服务项目的发布者，无法删除");
				}
			} else {// 该courseInfo下还有 有效的课程订单
				obj.accumulate("success", false).accumulate("message", "该服务项目下还有有效订单，不能删除！");
			}

		} catch (NumberFormatException e) {
			obj.accumulate("success", false).accumulate("message", e);
			e.printStackTrace();
		} catch (LogicException e) {
			obj.accumulate("success", false).accumulate("message", e);
			e.printStackTrace();
		}
		response(obj);

	}

	@SuppressWarnings("rawtypes")
	public void listCourseType() {
		try {
			List<Map<String, Object>> list = DataBaseConnection.getList("select * from TB_PARAMETER where parent = 45",
					null);
			JSONArray jarr = new JSONArray();
			for (Map map : list) {
				jarr.add(new JSONObject().accumulate("id", map.get("id")).accumulate("code", map.get("code"))
						.accumulate("name", map.get("name")));
			}
			response(new JSONObject().accumulate("success", true).accumulate("items", jarr));
		} catch (Exception e) {
			e.printStackTrace();
			response(new JSONObject().accumulate("succes", false).accumulate("message", e));
		}

	}

	/**
	 * getter && setter
	 */
	public File getImageCourseInfo() {
		return imageCourseInfo;
	}

	public void setImageCourseInfo(File imageCourseInfo) {
		this.imageCourseInfo = imageCourseInfo;
	}

	public String getImageCourseInfoFileName() {
		return imageCourseInfoFileName;
	}

	public void setImageCourseInfoFileName(String imageCourseInfoFileName) {
		this.imageCourseInfoFileName = imageCourseInfoFileName;
	}

}
