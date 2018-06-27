package com.freegym.web.plan.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.WorkoutBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.Part;
import com.freegym.web.config.Project;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Music;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.sanmen.web.core.utils.BaiduUtils;
import com.sanmen.web.core.utils.DateUtil;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/plan/workout.jsp"),
		@Result(name = "course", location = "/plan/workout_course.jsp"),
		@Result(name = "detail", location = "/plan/workout_detail.jsp"),
		@Result(name = "edit", location = "/plan/workout_detail_edit.jsp"),
		@Result(name = "look", location = "/plan/workout_look.jsp"),
		@Result(name = "music", location = "/plan/workout_detail_music.jsp") })
public class WorkoutManageAction extends WorkoutBasicAction {

	private static final long serialVersionUID = 5439537428568798578L;

	private String planDate;

	private String startDate, endDate;

	private Long member, toMember;

	private Course course;

	private Integer[] weeks;

	private List<Workout> workouts;

	private List<WorkoutDetail> details;

	private Music music;

	private List<Music> musics;

	private String exe;

	private String editPlan;

	private PlanRelease planRelease;

	public String getExe() {
		return exe;
	}

	public void setExe(String exe) {
		this.exe = exe;
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public List<Music> getMusics() {
		return musics;
	}

	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
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

	public Long getMember() {
		return member;
	}

	public void setMember(Long member) {
		this.member = member;
	}

	public Long getToMember() {
		return toMember;
	}

	public void setToMember(Long toMember) {
		this.toMember = toMember;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Integer[] getWeeks() {
		return weeks;
	}

	public void setWeeks(Integer[] weeks) {
		this.weeks = weeks;
	}

	public List<Workout> getWorkouts() {
		return workouts;
	}

	public void setWorkouts(List<Workout> workouts) {
		this.workouts = workouts;
	}

	public List<WorkoutDetail> getDetails() {
		return details;
	}

	public void setDetails(List<WorkoutDetail> details) {
		this.details = details;
	}

	public String getEditPlan() {
		return editPlan;
	}

	public void setEditPlan(String editPlan) {
		this.editPlan = editPlan;
	}

	public PlanRelease getPlanRelease() {
		return planRelease;
	}

	public void setPlanRelease(PlanRelease planRelease) {
		this.planRelease = planRelease;
	}

	public String execute() {
		session.setAttribute("spath", 3);
		Member m = toMember();
		if (course != null) {
			if (planDate == null)
				planDate = course.getPlanDate();
			member = course.getMember().getId();
		} else {
			if (planDate == null)
				planDate = DateUtil.getDateString(new Date());
			member = m.getId();
		}
		loadData();
		Long coachId = null;
		if (m.getRole().equals("S")) {// 鏁欑粌
			coachId = m.getId();
		} else {
			coachId = m.getCoach() == null ? 1 : m.getCoach().getId();
		}
		// 鑾峰彇鎵�鏈夊簲鐢ㄩ」鐩�
		List<Project> projects = new ArrayList<Project>();
		projects = service.loadProjectsByApplied(coachId);
		request.setAttribute("projects", projects);
		// 鑾峰彇褰撳墠椤圭洰鐨勬墍鏈夐儴浣嶅強鍔ㄤ綔
		Long projectId = projects.get(0) != null ? projects.get(0).getId() : null;
		List<Part> parts = service.findPartsByProjects(projectId, coachId);
		List<Action> actions = service.findActionsByProjectAndPart(projectId, null, m);
		request.setAttribute("parts", parts);
		request.setAttribute("actions", actions);
		getMonthPlanStatus();
		return SUCCESS;
	}

	/**
	 * 鑾峰彇褰撳墠鐢ㄦ埛鏈堣鍒掔姸鎬�
	 */
	private void getMonthPlanStatus() {
		try {
			String status = service.findMonthPlanStatus(member, planDate, "B");
			request.setAttribute("monthStatus", status);
		} catch (ParseException e) {
			log.error("error", e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void loadData() {
		Member m = toMember();
		List list = service.findAllCourse(m);
		if (list == null || list.size() == 0) {
			list = service.findAllCourse((Member) service.load(Member.class, 1L));
		}
		request.setAttribute("courseinfos", list);
		request.setAttribute("times", DateUtil.getAllTimeForDay());
		List<?> members = new ArrayList<>();
		Long coachId = null;
		// 鑾峰彇褰撳墠鏃ユ湡鐨勬墍鏈夎鍒掓槑缁嗘暟鎹�
		List<Course> courses = new ArrayList<Course>();
	
//  	??	
//		if (member == null && members.size() > 0) {
//			member = m.getId();// ((Member)members.get(0)).getId();
//		}
		
		Member me = null;
		if (member == null) {
			me = m;
		} else {
			me = (Member) service.load(Member.class, member);
		}
		
		if ("S".equals(m.getRole())) {
			coachId = m.getId();
		} else {
			coachId = m.getCoach() == null ? 1 : m.getCoach().getId();
		}

		if ("S".equals(me.getRole())) {// 鏁欑粌
			members = service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?",
					new Object[] { coachId, coachId });
			request.setAttribute("members", members);
			courses = (List<Course>) service.findObjectBySql(
					"from Course c where c.coach.id = ? and c.planDate = ? order by c.id desc",
					new Object[] { member, planDate });
		} else { // 浼氬憳
			members = service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?",
					new Object[] { coachId, coachId });
			request.setAttribute("members", members);
			courses = (List<Course>) service.findObjectBySql(
					"from Course c where c.member.id = ? and c.planDate = ? order by c.id desc",
					new Object[] { member, planDate });
		}
		request.setAttribute("courses", courses);
		workouts = new ArrayList<Workout>();
		if (course != null) {
			for (final Course c : courses) {
				if (c.getId().equals(course.getId())) {
					workouts.addAll(c.getWorks());
					break;
				}
			}
		} else {
			if (courses.size() > 0) {
				course = (Course) courses.get(0);
				workouts = new ArrayList<Workout>();
				workouts.addAll(course.getWorks());
				if (course.getMusic() != null)
					request.setAttribute("audioSignPath", BaiduUtils.getSignPath(BUCKET, course.getMusic().getAddr()));
			}
		}
	}

	/**
	 * 鍒犻櫎褰撳墠璇剧▼
	 */
	public String delete() {
		try {
			// service.delete(Course.class, id);
			String updateSql = "update tb_course set planDate='1997-01-01' where id = " + id;
			service.executeUpdate(updateSql);
			loadData();
			getMonthPlanStatus();
		} catch (Exception e) {
			log.error("error", e);
		}
		return "course";
	}

	/**
	 * 鍒犻櫎褰撳墠鍔ㄤ綔
	 */
	public void deleteWorkout() {
		try {
			service.delete(Workout.class, id);
			response("OK");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response(e.getMessage());
		}
	}

	/**
	 * 鍒犻櫎褰撳墠缁�
	 */
	public void deleteGroup() {
		try {
			service.delete(WorkoutDetail.class, id);
			response("OK");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response(e.getMessage());
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String deleteMusic() {
		try {
			Music mu = (Music) service.load(Music.class, id);
			String fileName = mu.getAddr();
			service.delete(mu);
			this.deleteFile(fileName);
			Member mem = toMember();
			musics = (List<Music>) service.findObjectBySql("from Music c where c.member.id = ? order by c.id desc",
					mem.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response("error");
		}
		return "music";
	}

	public void deleteCourseMusic() {
		try {
			Course course = (Course) service.load(Course.class, id);
			course.setMusic(null);
			service.saveCourse(course, null);
			response("OK");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
			response(e.getMessage());
		}
	}

	/**
	 * 褰撶偣鍑绘煇涓�椤瑰姩浣滄椂锛屽姞杞藉叾鍔ㄤ綔涓嬬殑鎵�鏈夌殑缁勬鏁版嵁
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadGroup() {
		Workout workout = (Workout) service.load(Workout.class, id);
		details = (List<WorkoutDetail>) service.findObjectBySql("from WorkoutDetail wd where wd.workout.id = ?", id);
		request.setAttribute("workout", workout);
		request.setAttribute("videoSignPath", BaiduUtils.getSignPath(BUCKET, workout.getAction().getVideo()));
		return "edit";
	}

	/**
	 * 褰撶偣鍑绘煇涓�椤硅繍鍔ㄦ椂锛屽姞杞藉叾鍔ㄤ綔涓嬬殑鎵�鏈夌殑缁勬鐨勯厤涔�
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String loadMusic() {
		Member mem = toMember();
		musics = (List<Music>) service.findObjectBySql("from Music c where c.member.id = ? order by c.id desc",
				mem.getId());
		if (id != null) {
			course = (Course) service.load(Course.class, id);
			request.setAttribute("course", course);
			music = course.getMusic();
		}
		return "music";
	}

	/**
	 * 鐐瑰嚮纭畾鍚庝笂浼犻煶涔� exe=2 网路音乐
	 * 
	 * @return
	 * @throws JSONException
	 */
	public String uploadMusic() {
		final JSONObject obj = new JSONObject();
		try {
			Member m = toMember();
			Music mu = new Music();
			if (file != null) {
				String t = saveFile("music", music.getAddr());
				music.setAddr("music/" + t);
				// filePath = BaiduUtils.upload(toMember().getNick(),
				// file.getFile(), file.getFileFileName());
				// String t = saveFile("music", file.getFile(),
				// file.getFileFileName(), file.getFileFileName());
				music.setName(t.substring(0, t.lastIndexOf(".")));
				music.setMember(m);
				mu = (Music) service.saveOrUpdate(music);
			} else {
				if (course.getMusic() != null) {
					mu.setId(course.getMusic().getId());
				}
			}
			if (id != null) {
				course = (Course) service.load(Course.class, id);
				course.setMusic(mu);
				service.saveOrUpdate(course);
			}
			obj.accumulate("success", true).accumulate("key", mu.getId());
		} catch (Exception e) {
			obj.accumulate("success", false).accumulate("message", e.getMessage());
		}
		response(obj);
		return null;
	}

	@SuppressWarnings("unlikely-arg-type")
	public String save() {
		Member m = toMember();
		try {
			if (course != null) {
				if (course.getDoneDate() != null && course.getDoneDate().equals(""))
					course.setDoneDate(null);
				if (course.getCoach() != null && course.getCoach().getId() == null) {
					if (m.getRole().equals("S"))
						course.setCoach(m);
					else
						course.setCoach(m.getCoach() == null ? new Member(1l) : m.getCoach());
				}
				if (course.getColor() == null || "".equals(course.getColor()))
					course.setColor("#ffcc00");
				String source = course.getMember() == null || course.getMember().equals(m.getId()) ? PLAN_SOURCE_SELF
						: PLAN_SOURCE_COACH;
				course.setPlanSource(source);
				if (course.getMember() == null)
					course.setMember(m);
				course = service.saveWorkout(course, workouts);
				service.clean();
				planDate = course.getPlanDate();
				member = course.getMember().getId();
				loadData();
				getMonthPlanStatus();
			}
		} catch (Exception e) {
			log.error("error", e);
		}
		return "course";
	}

	/**
	 * 淇濆瓨璇剧▼鏄庣粏鏁版嵁
	 * 
	 * @return
	 */
	public String saveDetail() {
		try {
			if (details != null) {
				service.saveOrUpdate(details);
			}
			return loadGroup();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
		}
		return "edit";
	}

	/**
	 * 椤甸潰鐐瑰嚮缂栬緫褰撳墠璇剧▼鏃讹紝鏍规嵁璇剧▼ID鍙栧緱鍏惰缁嗘暟鎹�
	 * 
	 * @return
	 */
	public String edit() {
		workouts = new ArrayList<Workout>();
		if (id != null) {
			// course = (Course) service.load(Course.class, id);
			// workouts.addAll(course.getWorks());
			String querySql = "select id from tb_workout where course = " + id;
			List<Map<String, Object>> works = service.queryForList(querySql);
			for (Map<String, Object> work : works) {
				workouts.add((Workout) service.load(Workout.class, Long.valueOf(String.valueOf(work.get("id")))));
			}
			// if (course.getMusic() != null) {
			// request.setAttribute("audioSignPath",
			// BaiduUtils.getSignPath(course.getMusic().getAddr()));
			// }
		}
		return "detail";
	}

	/**
	 * 褰撴暀缁冮�夋嫨鍏跺畠浼氬憳鏃跺姞杞借浼氬憳鐨勮绋嬫暟鎹�
	 * 
	 * @return
	 */
	public String switchMember() {
		loadData();
		getMonthPlanStatus();
		return "course";
	}

	/**
	 * 褰撴敼鍙樻棩鍘嗙殑骞存湀鏃堕噸鏂板姞杞藉叾鎵�鏈夌殑璇剧▼鏁版嵁
	 */
	public void switchYearMonth() {
		try {
			String status = service.findMonthPlanStatus(member, planDate, "B");
			response(status);
		} catch (ParseException e) {
			e.printStackTrace();
			log.error("error", e);
		}
	}

	/**
	 * 褰撴敼鍙樻棩鍘嗙殑鏃堕棿鏃跺姞杞藉綋澶╃殑璇剧▼鏁版嵁
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String switchDate() {
		loadData();
		getMonthPlanStatus();
		Member m = toMember();
		List list = service.findAllCourse(m);
		if (list == null || list.size() == 0) {
			list = service.findAllCourse((Member) service.load(Member.class, 1L));
		}
		request.setAttribute("courseinfos", list);
		return "course";
	}

	/**
	 * 褰撴敼鍙樺仴韬」鐩殑鏃跺�欏姞杞藉綋鍓嶉」鐩殑鎵�鏈夐儴浣嶅強鍔ㄤ綔鏁版嵁
	 */
	public void switchProject() {
		final Long projectId = new Long(request.getParameter("projectId"));
		final List<Part> parts = service.findPartsByProjects(projectId,
				toMember().getRole().equals("S") ? toMember().getId()
						: toMember().getCoach() == null ? 1l : toMember().getCoach().getId());
		final StringBuffer sb = new StringBuffer("[");
		int i = 0, j = 0;
		for (final Part p : parts) {
			sb.append(i == 0 ? "" : ",");
			sb.append("{\"id\": " + p.getId() + ", ").append("\"name\": \"" + p.getName() + "\", \"actions\": [");
			j = 0;
			for (final Action a : service.findActionsByProjectAndPart(projectId, p.getId(), toMember())) {
				sb.append(j == 0 ? "" : ",");
				sb.append(getActionJson(a));
				j++;
			}
			sb.append("]}");
			i++;
		}
		sb.append("]");
		response(sb.toString());
	}

	/**
	 * 褰撴敼鍙樻煇涓」鐩殑閮ㄤ綅鏃跺姞杞藉叾閮ㄤ綅涓嬬殑鎵�鏈夌殑鍔ㄤ綔
	 */
	public void switchPart() {
		Member m = toMember();
		Long projectId = new Long(request.getParameter("projectId"));
		String part = request.getParameter("partId");
		List<Action> actions = null;
		if (part != null && !"".equals(part)) {
			final Long partId = new Long(part);
			actions = service.findActionsByProjectAndPart(projectId, partId, m);
		} else {
			actions = service.findActionsByProjectAndPart(projectId, null, m);
		}
		final StringBuffer sb = new StringBuffer("[");
		int i = 0;
		for (final Action a : actions) {
			sb.append(i > 0 ? "," : "");
			sb.append(getActionJson(a));
			i++;
		}
		sb.append("]");
		response(sb.toString());
	}

	@SuppressWarnings("unused")
	private String getActionJson(Action a) {
		final StringBuffer sb = new StringBuffer();
		final String desc = a.getDescr() == null ? "" : a.getDescr().replace('\n', ' ').replace('\r', ' ');
		sb.append("{\"id\":" + a.getId() + ", ").append("\"name\": \"" + a.getName() + "\",")
				.append("\"partId\": " + a.getPart().getId() + ", ")
				.append("\"partName\": \"" + a.getPart().getName() + "\", ")
				.append("\"pId\": " + a.getPart().getProject().getId() + ", ")
				.append("\"pName\":\"" + a.getPart().getProject().getName() + "\", ")
				.append("\"mode\": \"" + a.getPart().getProject().getMode() + "\"}");
		// .append("\"flash\": \"" + a.getFlash() +
		// "\", ").append("\"image\": \"" + a.getImage() + "\", ")
		// .append("\"video\": \"" + a.getVideo() +
		// "\", ").append("\"descr\": \"" + desc + "\"}");
		return sb.toString();
	}

	public String look() {
		request.setAttribute("action", service.load(Action.class, id));
		request.setAttribute("signPath",
				BaiduUtils.getSignPath(BUCKET, ((Action) request.getAttribute("action")).getVideo()));
		return "look";
	}

	public void clean() {
		try {
			if (toMember == null)
				toMember = toMember().getId();
			List<?> list = service.findObjectBySql("from Course c where c.member.id = ? and c.planDate between ? and ?",
					new Object[] { toMember, startDate, endDate });
			for (Iterator<?> it = list.iterator(); it.hasNext();)
				service.delete(it.next());
			String status = service.findMonthPlanStatus(member, planDate, "B");
			response("{\"success\":true,\"message\":" + status + "}");
		} catch (Exception e) {
			log.error("error", e);
			response("{\"success\":false,\"message\": \"" + e.getMessage() + "\"}");
		}
	}

	public void copy() {
		try {
			if (toMember == null)
				toMember = toMember().getId();
			service.copy(course, toMember, startDate, endDate, weeks);
			// 褰撳墠鐢ㄦ埛鏈堣鍒掔姸鎬�
			String status = service.findMonthPlanStatus(member, planDate, "B");
			response("{\"success\":true,\"message\":" + status + "}");
		} catch (Exception e) {
			log.error("error", e);
			response("{\"success\":false,\"message\": \"" + e.getMessage() + "\"}");
		}
	}
}
