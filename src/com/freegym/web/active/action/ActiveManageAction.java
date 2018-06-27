package com.freegym.web.active.action;

import java.io.File;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.active.Active;
import com.freegym.web.active.ActiveJudge;
import com.freegym.web.basic.Member;
import com.freegym.web.order.ActiveOrder;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.FileUtil;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/active/active_start_list.jsp"),
		@Result(name = "look", location = "/active/active_new_edit.jsp"),
		@Result(name = "edit", location = "/active/active_new_edit.jsp"),
		@Result(name = "view", location = "/active/active_order.jsp") })
public class ActiveManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Active active;

	private Character status;

	private Character external = '0';

	private File image;

	private String imageFileName, code;

	private List<ActiveJudge> judges;

	public String execute() {
		session.setAttribute("spath", 5);
		query();
		return SUCCESS;
	}

	private void query() {
		final DetachedCriteria dc = DetachedCriteria.forClass(Active.class);
		dc.createAlias("creator", "m");
		if (external != null && external == '1') {
			final Member m = (Member) session.getAttribute("toMember");
			dc.add(Restrictions.eq("m.id", m.getId()));
		} else {
			dc.add(Restrictions.eq("m.id", toMember().getId()));
		}
		pageInfo.setOrder("createTime");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo, 1);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String edit() {
		if (id != null) {
			active = (Active) service.load(Active.class, id);
		} else {
			active = new Active();
		}
		judges = new ArrayList<ActiveJudge>();
		judges.addAll(active.getJudges());
		List csjg = service.findObjectBySql("from Member m where m.role = ?", "I");
		Member m = (Member) session.getAttribute("loginMember");
		if (m != null) {
			csjg.add(m);
		}

		request.setAttribute("yyxms", service.findActionByMode(1l, '0'));
		request.setAttribute("csjgs", csjg);
		return EDIT;
	}

	public void deleteJudge() {
		try {
			service.delete(ActiveJudge.class, id);
			response();
		} catch (Exception e) {
			response(e);
		}
	}

	public void findMemberInfo() {
		try {
			final JSONObject obj = new JSONObject();
			final Member member = service.findMemberByName(code);
			if (member != null) {
				obj.accumulate("success", true).accumulate("id", member.getId()).accumulate("name", member.getName());
			} else {
				obj.accumulate("success", false).accumulate("message", "未找到对应的会员或教练，请确认重新输入！");
			}
			response(obj);
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 发起人查看活动详情 a
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String look() {
		active = (Active) service.load(Active.class, id);
		judges = new ArrayList<ActiveJudge>();
		judges.addAll(active.getJudges());
		List csjg = service.findObjectBySql("from Member m where m.role = ?", "I");
		Member m = (Member) session.getAttribute("loginMember");
		if (m != null) {
			csjg.add(m);
		}

		request.setAttribute("yyxms", service.findActionByMode(1l, '0'));
		request.setAttribute("csjgs", csjg);
		request.setAttribute("look", 1);
		return "look";
	}

	public String view() {
		final ActiveOrder ao = (ActiveOrder) service.load(ActiveOrder.class, id);
		request.setAttribute("ao", ao);
		return "view";
	}

	public String changeStatus() {
		active = (Active) service.load(Active.class, id);
		active.setStatus(status);
		service.saveOrUpdate(active);
		query();
		return SUCCESS;
	}

	/**
	 * 
	 */
	public String save() {
		try {
			if (image != null && image != null) {
				boolean isImage = FileUtil.isFile(image, "png,jpg,gif,");
				if (!isImage)
					throw new LogicException("非正常图片格式，请确认！");
				FileUtil.addWaterMark(image, imageFileName.substring(imageFileName.lastIndexOf(".") + 1),
						webPath + "/images/watermark.png", -1, -1, 0.5f);
			}
			String fileName = image != null ? saveFile("picture", image, imageFileName, active.getImage()) : null;
			if (fileName != null) {
				active.setImage(fileName);
			} else {
				active.setImage(active.getImage());
			}

			if (active.getCreator() == null || active.getCreator().getId() == null)
				active.setCreator(toMember());
			if (active.getStatus() == null)
				active.setStatus('A');
			if (active.getCreateTime() == null)
				active.setCreateTime(new Date());
			if (judges != null) {
				for (final ActiveJudge judge : judges)
					active.addJudge(judge);
			}
			active = (Active) service.saveOrUpdate(active);
		} catch (Exception e) {
			log.error("error", e);
		}
		return execute();
	}

	/**
	 * 发挑战上传图片
	 */
	public void uploadImage() {
		JSONObject ret = new JSONObject();
		try {
			String fileName = image == null ? null : saveFile("picture", image, imageFileName, null);
			ret.accumulate("success", true).accumulate("message", "OK").accumulate("imageName", fileName);

		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", "上传图片异常");
			e.printStackTrace();
		}
		response(ret);

	}

	/**
	 * 发起挑战
	 */
	public String activeSave() {
		JSONObject objs = new JSONObject();
		try {
			String jsons = request.getParameter("jsons");
			JSONObject obj = JSONObject.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			active.setStatus('B');
			active.setCreator(toMember());
			active.setName(obj.getString("activeName"));
			active.setDays(obj.getInt("activeDays"));
			active.setTarget(obj.getString("targetType").charAt(0));
			active.setMemo(obj.getString("memo"));
			active.setMode("A".charAt(0));
			active.setJoinMode("A".charAt(0));
			active.setAward(obj.getString("parise"));
			active.setAmerceMoney(obj.getDouble("amerceMoney"));
			active.setInstitution(new Member(obj.getLong("institution")));
			active.setCategory(active.getTarget());
			active.setValue(obj.getDouble("targetValue"));
			active.setCreateTime(new Date());
			
			String fileName = image != null ? saveFile("picture", image, imageFileName, active.getImage()) : null;
			active.setImage(fileName);

			active = (Active) service.saveOrUpdate(active);
			objs.accumulate("success", true).accumulate("id", active.getId());
		} catch (Exception e) {
			objs.accumulate("success", false).accumulate("message", e);
			e.printStackTrace();
		}
		
		return execute();
	}

	/**
	 * setter && getter
	 */

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

	public Active getActive() {
		return active;
	}

	public void setActive(Active active) {
		this.active = active;
	}

	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public Character getExternal() {
		return external;
	}

	public void setExternal(Character external) {
		this.external = external;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ActiveJudge> getJudges() {
		return judges;
	}

	public void setJudges(List<ActiveJudge> judges) {
		this.judges = judges;
	}

}
