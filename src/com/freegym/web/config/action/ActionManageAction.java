package com.freegym.web.config.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.Part;
import com.freegym.web.config.Project;
import com.sanmen.web.core.utils.BaiduUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/coach/action.jsp"), @Result(name = "content", location = "/coach/action_content.jsp"),
		@Result(name = "edit", location = "/coach/action_edit.jsp"), @Result(name = "prev", location = "part.asp", type = "redirect"),
		@Result(name = "next", location = "done2.asp", type = "redirect"), @Result(name = "all", location = "/action.jsp") })
public class ActionManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Long projectId;

	private String mode;

	private Action action;

	private List<Action> actions;

	private File image;

	private String imageFileName;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	// ID: 2516724
	// API KEY: vtpI8z2Q4G1KvSKunvQaRDKK
	// SECRET KEY: uuB5rlXoAWxdcxUsRn3sN8PmWooKdeh1
	public String execute() {
		session.setAttribute("spath", 8);
		Member m = toMember();
		// 加载所有应用的项目
		List<Project> list = service.loadProjectsByApplied(m.getId());
		if (projectId == null && list.size() > 0) {
			Project p = list.get(0);
			projectId = p.getId();
		}
		request.setAttribute("projects", list);
		// 加载该项目的所有部位
		loadAction();
		return SUCCESS;
	}

	public String loadAction() {
		request.setAttribute("actions", service.findObjectBySql(
				"from Action a where a.part.id in (select p.id from Part p where p.project.id = ?) and (a.member = ? or a.member = ?)", projectId, 1l,
				toMember().getId()));
		return "content";
	}

	public String edit() {
		request.setAttribute("muscles", service.findParametersByCodes("system_muscle_c"));
		request.setAttribute("parts", service.findObjectBySql("from Part p where p.project.id = ?", projectId));
		List<String> arrs = new ArrayList<String>();
		if (id != null) {
			action = (Action) service.load(Action.class, id);
			if (action.getMuscle() != null) {
				String[] muscles = action.getMuscle().split(",");
				for (int i = 0; i < muscles.length; i++) {
					arrs.add(muscles[i].trim());
				}
			}
		}
		request.setAttribute("appliedmuscles", arrs);
		return "edit";
	}

	public String save() {
		try {
			if (file != null) {
				String filePath = BaiduUtils.upload(BUCKET, toMember().getNick(), getFileName(file.getFileFileName()), file.getFile());
				action.setVideo(filePath);
			}
			// 上传图片
			String fileName = image != null ? saveFile("picture", image, imageFileName, null) : null;
			if (fileName != null) action.setImage("picture/" + fileName);
			action.setSystem('0');
			action.setMember(toMember().getId());
			service.saveOrUpdate(action);
			this.loadAction();
		} catch (Exception e) {
			log.error("保存动作信息失败！", e);
		}
		return "content";
	}

	@SuppressWarnings("unchecked")
	public void delete() {
		try {
			List<Action> actionList = (List<Action>) service.findObjectByIds(Action.class, "id", ids);
			List<String> pathList = new ArrayList<String>();
			if (actionList != null) {
				for (Action action : actionList) {
					pathList.add(action.getVideo());
				}
			}
			BaiduUtils.deleteObject(BUCKET, pathList);
			service.delete(Action.class, ids);
		} catch (Exception e) {
			log.error("error", e);
		}
	}

	public void loadPartsByProject() {
		List<?> list = service.findObjectBySql("from Part p where p.project = ?", projectId);
		StringBuffer sb = new StringBuffer("[");
		Part p = null;
		int i = 1, size = list.size();
		for (Iterator<?> it = list.iterator(); it.hasNext(); i++) {
			p = (Part) it.next();
			sb.append("{\"id\":").append(p.getId()).append(",\"name\":\"").append(p.getName()).append("\"}");
			sb.append(i < size ? "," : "");
		}
		sb.append("]");
		response(sb.toString());
	}

	@SuppressWarnings("unchecked")
	public String loadAll() {
		actions = (List<Action>) service.findObjectBySql("from Action a where a.system = ?", '1');
		return "all";
	}

	public void updateAll() {
		service.saveOrUpdate(actions);
		response("ok");
	}

	public String next() {
		return "next";
	}

	public String prev() {
		return "prev";
	}

}
