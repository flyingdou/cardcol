package com.freegym.web.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Message;
import com.sanmen.web.core.common.ParamBean;
import com.sanmen.web.core.content.ContentRecommend;
import com.sanmen.web.core.utils.StringUtils;

import net.sf.json.JSONObject;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/member.jsp") })
public class Member1ManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Member member, query;

	private String mobiles, contents;

	private String subject;

	private ContentRecommend sa;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getQuery() {
		return query;
	}

	public void setQuery(Member query) {
		this.query = query;
	}

	public String getMobiles() {
		return mobiles;
	}

	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ContentRecommend getSa() {
		return sa;
	}

	public void setSa(ContentRecommend sa) {
		this.sa = sa;
	}

	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = Member.getCriteriaQuery(query);
		loadPermission(dc);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo);
	}

	@Override
	protected void loadAreaPerm(final DetachedCriteria dc, final String str) {
		dc.add(Restrictions.sqlRestriction("this_.city in (select name from tb_area where id in (" + str + "))"));
	}

	@Override
	protected Long executeSave() {
		if (member != null) {
			member = (Member) service.saveOrUpdate(member);
			return member.getId();
		}
		return null;
	}

	@Override
	public void recommend() {
		try {
			sa.setIcon(saveFile("picture", sa.getIcon()));
			sa.setRecommType(PRODUCT_TYPE_MEMBER);
			Date nowDate = new Date();
			sa.setRecommDate(nowDate);
			sa.setStickTime(nowDate);
			service.saveOrUpdate(sa);
			response(true, "message: 'ok'");
		} catch (Exception e) {
			response(e);
		}
	}

	/**
	 * 后台校验俱乐部和教练的证书
	 */
	public void onValid() {
		try {
			final List<?> list = service.load(Member.class, ids);
			for (Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				m.setHasValid(MEMBER_VALIDATE_SUCCESS);
			}
			service.saveOrUpdate(list);
			response("{success:true}");
		} catch (Exception e) {
			log.error("error", e);
			response("{success:false,message:'" + e.getMessage() + "'}");
		}
	}

	/**
	 * 撤消后台校验俱乐部和教练的证书
	 */
	public void onUndoValid() {
		try {
			final List<?> list = service.load(Member.class, ids);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				m.setHasValid(MEMBER_VALIDATE_UNVALID);
			}
			service.saveOrUpdate(list);
			response("{success:true}");
		} catch (Exception e) {
			log.error("error", e);
			response("{success:false,message:'" + e.getMessage() + "'}");
		}
	}

	/**
	 * 开通会员级别
	 */
	public void onOpen() {
		try {
			final List<?> list = service.load(Member.class, ids);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				m.setGrade("1");
			}
			service.saveOrUpdate(list);
			response("{success:true}");
		} catch (Exception e) {
			log.error("error", e);
			response("{success:false,message:'" + e.getMessage() + "'}");
		}
	}

	/**
	 * 撤消会员开通的级别
	 */
	public void onUndoOpen() {
		try {
			final List<?> list = service.load(Member.class, ids);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				m.setGrade("0");
			}
			service.saveOrUpdate(list);
			response("{success:true}");
		} catch (Exception e) {
			log.error("error", e);
			response("{success:false,message:'" + e.getMessage() + "'}");
		}
	}

	/**
	 * 短信群发
	 */
	public void sendSms() {
		final String msg = sendSms(mobiles, contents);
		response(msg);
	}

	/**
	 * 系统内消息群发
	 */
	public void sendMsg() {
		final List<Message> messages = new ArrayList<Message>();
		final String[] ids = mobiles.split(",");
		for (int i = 0; i < ids.length; i++) {
			final Message msgs = new Message();
			msgs.setContent(contents);
			msgs.setIsRead("0");
			msgs.setMemberFrom(new Member(1l));
			msgs.setMemberTo(new Member(new Long(ids[i])));
			msgs.setSendTime(new Date());
			msgs.setStatus("0");
			msgs.setType("2");
			messages.add(msgs);
		}
		service.saveOrUpdate(messages);
	}

	/**
	 * 邮件群发
	 */
	public void sendEmail() {
		if (mobiles != null && !"".equals(mobiles)) {
			final List<String> list = StringUtils.stringToList(mobiles, ",");
			service.sendMail(list, subject, contents);
			response("OK");
		} else {
			response("未选择需要发送的邮件账户！");
		}
	}

	/**
	 * 清除会员的邮件或手机号码
	 */
	public void onClean() {
		final String type = request.getParameter("type");
		try {
			final List<?> list = service.load(Member.class, ids);
			if (type.equals("email")) {
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final Member m = (Member) it.next();
					m.setEmail(null);
					m.setEmailValid(MEMBER_VALIDATE_UNVALID);
				}
			} else {
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final Member m = (Member) it.next();
					m.setMobilephone(null);
					m.setMobileValid(MEMBER_VALIDATE_UNVALID);
				}
			}
			service.saveOrUpdate(list);
			response("OK");
		} catch (Exception e) {
			log.error("error", e);
			response(e.getMessage());
		}
	}

	/**
	 * 清除会员的邮件或手机号码
	 */
	public void onVerifyMobile() {
		try {
			final List<?> list = service.load(Member.class, ids);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				m.setMobileValid(MEMBER_VALIDATE_SUCCESS);
			}
			service.saveOrUpdate(list);
			response("OK");
		} catch (Exception e) {
			log.error("error", e);
			response(e.getMessage());
		}
	}

	/**
	 * 设置会员的手机号码
	 */
	public void setMobile() {
		try {
			final List<?> list = service.load(Member.class, ids);
			for (final Iterator<?> it = list.iterator(); it.hasNext();) {
				final Member m = (Member) it.next();
				m.setMobilephone(mobiles);
			}
			service.saveOrUpdate(list);
			response("OK");
		} catch (Exception e) {
			log.error("error", e);
			response(e.getMessage());
		}
	}

	@Override
	public void findRole() {
		final StringBuffer jsons = new StringBuffer();
		try {
			final List<ParamBean> params = new ArrayList<ParamBean>();
			final ParamBean pb = new ParamBean(1, "俱乐部");
			pb.setCode("E");

			final ParamBean pb1 = new ParamBean(2, "教练");
			pb1.setCode("S");

			final ParamBean pb2 = new ParamBean(3, "会员");
			pb2.setCode("M");

			final ParamBean pb3 = new ParamBean(4, "慈善机构");
			pb3.setCode("I");

			params.add(pb);
			params.add(pb1);
			params.add(pb2);
			params.add(pb3);
			jsons.append("{success: true, items: " + getJsonString(params) + "}");
		} catch (Exception e) {
			log.error("error", e);
			jsons.append("{success: false, message: '" + e.getMessage() + "'}");
		} finally {
			response(jsons.toString());
		}
	}

	public void queryAllClub() {
		List<?> list = service.findObjectBySql("from Member m where m.role = ?", "E");
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("items", getJsonString(list));
		response(obj);
	}

	@Override
	protected Class<?> getEntityClass() {
		return Member.class;
	}

	@Override
	protected String getExclude() {
		return "applyClubs,order45s,balanceFroms,balanceTos,judges,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets,ticklings";
	}
}
