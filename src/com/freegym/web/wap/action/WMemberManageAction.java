package com.freegym.web.wap.action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.basic.Member;
import com.freegym.web.config.Project;
import com.freegym.web.mobile.action.MMemberManageAction;
import com.freegym.web.order.Product;
import com.freegym.web.plan.Course;
import com.freegym.web.system.Tickling;
import com.freegym.web.task.MessageThread;
import com.sanmen.web.core.bean.BaseMember;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.content.Banner;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class WMemberManageAction extends MMemberManageAction {

	private static final long serialVersionUID = -1719356678628679456L;

	private String planDate;
	/**
	 * 配置文件中key
	 */
	private String key;

	public String getPlanDate() {
		return planDate;
	}

	public void setPlanDate(String planDate) {
		this.planDate = planDate;
	}

	@Override
	protected BaseMember getLoginMember() {
		//return (BaseMember) getService().load(Member.class, 490l);
		 return toMember();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 解除私教关系
	 */
	@Override
	public void remove() {
		try {
			final Member m = (Member) service.load(Member.class, id);
			String uid = m.getCoach().getUserId();
			Long cid = m.getCoach().getChannelId();
			Integer type = m.getCoach().getTermType();
			m.setCoach(null);
			service.saveOrUpdate(m);
			new MessageThread(uid, cid, type, "会员“" + m.getName() + "”申请加入您的俱乐部。");
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 读取俱乐部3天课程信息
	 */
	@Override
	public void findClubHomeCourse() {
		try {
			Date date = new Date();

			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			String currDate = df.format(date);
			String afterDate = df.format(new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000));

			final DetachedCriteria dc = DetachedCriteria.forClass(Course.class);

			dc.add(Restrictions.eq("member.id", id));

			if (planDate == null || "".equals(planDate)) {
				dc.add(Restrictions.between("planDate", currDate, afterDate));
			} else {
				dc.add(Restrictions.eq("planDate", planDate));
			}
			pageInfo = service.findPageByCriteria(dc, pageInfo);

			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				final Course course = (Course) it.next();
				jarr.add(course.toJson());
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("pageInfo", getJsonForPageInfo()).accumulate("items", jarr);
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 获取手机验证码
	 */
	public void getMobileCode() {
		try {
			final String mobile = request.getParameter("mobile");
			final Long id = Long.parseLong(request.getParameter("id"));
			final Long count = service.queryForLong("select count(*) from tb_member where mobilephone = ? and mobile_valid = '1' and id <> ?", mobile, id);
			if (count > 0) throw new LogicException("当前手机号码已经被其它用户使用，不得重复使用！");
			final String msg = sendSmsValidate(mobile, "mobile.validate.open");
			final Member m = (Member) service.load(Member.class, id);
			m.setMobilephone(mobile);
			m.setMobileValid(MEMBER_VALIDATE_UNVALID);
			service.saveOrUpdate(m);
			if (msg.equalsIgnoreCase("ok")) {
				final Tickling tick = new Tickling();
				tick.setContent("用户" + m.getNick() + "已经申请了手机验证码，如果已经处理可以不用理会本反馈，否则请手动处理。");
				tick.setCreateTime(new Date());
				tick.setLink(m.getMobilephone());
				tick.setMember(m);
				tick.setStatus('0');
				getService().saveOrUpdate(tick);
			}
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 验证手机号码
	 */
	public void validMobile() {
		try {
			final JSONObject ret = new JSONObject();
			final String mobile = request.getParameter("mobile");
			final String code = request.getParameter("code");
			final Long id = Long.parseLong(request.getParameter("id"));
			if (isRightful(mobile, code)) {
				Member m = (Member) service.load(Member.class, id);
				m.setMobilephone(request.getParameter("mobile"));
				m.setMobileValid(MEMBER_VALIDATE_SUCCESS);
				service.saveOrUpdate(m);
				session.setAttribute(LOGIN_MEMBER, m);
				session.setAttribute("member", getMemberJson(m));
				ret.accumulate("success", true).accumulate("message", "OK");
				response(ret);
			} else {
				ret.accumulate("success", false).accumulate("message", "您的手机验证码已经过期，请重新发送手机验证码！");
				response(ret);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
	
	/**
	 * 获取所有project
	 */
	public void listProject(){
		try {
			List<?> list = null;
			list = service.findObjectBySql("from Project");
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Project p = (Project) it.next();
				jarr.add(p.toJson());
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
	 * 获取所有的俱乐部
	 */
	public void listClubs(){
		try {
			List<?> list = null;
			list = service.findObjectBySql("from Member m where m.role = 'E' ");
			final JSONArray jarr = new JSONArray();
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				JSONObject o = new JSONObject();
				jarr.add(o.accumulate("id", m.getId()).accumulate("nick", m.getNick()));
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
	 * 获取Product
	 */
	public void loadProduct(){
		try {
			JSONObject obj = new JSONObject();
			Product p = (Product) service.load(Product.class, id);
			obj.accumulate("success", true).accumulate("item", p.toJson());
			response(obj);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
	public void loadBanner() {
		final List<?> list = service.findBannerBySectorId(id);
		final JSONArray jarr = new JSONArray();
		for (final Iterator<?> it = list.iterator(); it.hasNext();) {
			final Banner b = (Banner) it.next();
			jarr.add(b.toJson());
		}
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("items", jarr);
		response(obj);
	}
	
	public void getPropertiesMessage(){
		final JSONObject ret = new JSONObject();
		String value = getMessage(key);
		ret.accumulate("success", true).accumulate("message", value);
		response(ret);
	}
}
