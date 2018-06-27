package com.freegym.web.config.action;

import java.io.File;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.CourseInfo;
import com.sanmen.web.core.system.Parameter;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "successS", location = "/coach/course.jsp"),
		@Result(name = "successE", location = "/club/course.jsp"),
		@Result(name = "nextS", location = "project.asp", type = "redirect"),
		@Result(name = "prevS", location = "style.asp", type = "redirect"),
		@Result(name = "nextE", location = "rate.asp", type = "redirect"),
		@Result(name = "prevE", location = "factory.asp", type = "redirect"),
		@Result(name = "save", location = "mycourse.asp", type = "redirect") })
public class MyCourseManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private CourseInfo course;

	private List<CourseInfo> courses;

	private String imageFileName;

	private File image;

	public CourseInfo getCourse() {
		return course;
	}

	public void setCourse(CourseInfo course) {
		this.course = course;
	}

	public List<CourseInfo> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseInfo> courses) {
		this.courses = courses;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		Member m = toMember();
		session.setAttribute("spath", 8);
		List<Parameter> types = service.findParametersByCodes("course_type_c");
		request.setAttribute("courseTypes", types);
		courses = (List<CourseInfo>) service
				.findObjectBySql("from CourseInfo ci where ci.member.id = ? order by ci.sort", m.getId());
		return "success" + m.getRole();
	}

	public String save() {
		try {
			course.setMember(toMember());
			if (image != null) {
				String fileName = saveFile("picture", image, imageFileName, course.getImage());
				course.setImage(fileName);
			}
			course = (CourseInfo) service.saveOrUpdate(course);
		} catch (Exception e) {
			log.error("error", e);
		}
		return "save";
	}

	public void delete() {
		try {
			service.delete(CourseInfo.class, ids);
			response("OK");
		} catch (Exception e) {
			log.error("error", e);
			response(e.getMessage());
		}
	}

	public String next() {
		this.saveList();
		Member m = toMember();
		return "next" + m.getRole();
	}

	public String prev() {
		this.saveList();
		Member m = toMember();
		return "prev" + m.getRole();
	}

	private void saveList() {
		try {
			for (CourseInfo ci : courses)
				ci.setMember(toMember());
			service.saveOrUpdate(courses);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	/**
	 * 教练或俱乐部查找自己的所有课程
	 */
	public void findCourse() {
		final String role = toMember().getRole();
		Long memberId = null;
		if ("E".equals(role) || "S".equals(role)) {
			memberId = toMember().getId();
		} else {
			memberId = toMember().getCoach() == null ? null : toMember().getCoach().getId();
		}
		try {
			final List<?> list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", memberId);
			final String str = getJsonString(list);
			response(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 会员访问教练或俱乐部时，查找其教练或俱乐部的所有课程
	 */
	public void findCourseBy() {
		final Member toMember = (Member) session.getAttribute("toMember");
		final List<?> list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?", toMember.getId());
		try {
			final String str = getJsonString(list);
			response(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 会员自己订计划查找其教练的课程
	 */
	public void findCourseByCoach() {
		final Member toMember = (Member) session.getAttribute("toMember");
		List<?> list = null;
		if (toMember != null)
			list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?",
					toMember.getCoach() == null ? 1 : toMember.getCoach().getId());
		else
			list = service.findObjectBySql("from CourseInfo ci where ci.member.id = ?",
					toMember().getCoach() == null ? 1 : toMember().getCoach().getId());
		try {
			final String str = getJsonString(list);
			response(str);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	public String findCourseByCourseInfo() {
		String msg = "";
		if (course != null && course.getId() != null) {
			try {
				course = (CourseInfo) service.load(CourseInfo.class, course.getId());
				msg = getJsonString(course);
			} catch (Exception e) {
				msg = e.getMessage();
				log.error("error", e);
			}
			response(msg);
		}
		return null;
	}

	@Override
	protected String getExclude() {
		return "member,courses";
	}

}
