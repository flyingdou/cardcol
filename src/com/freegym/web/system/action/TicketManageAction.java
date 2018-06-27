package com.freegym.web.system.action;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.system.Ticket;
import com.freegym.web.utils.Application;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/ticket.jsp") })
public class TicketManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -2297129011587931307L;

	private Ticket ticket, query;

	private Map<String, Object> param;

	private File image;

	private String imageFileName;

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Ticket getQuery() {
		return query;
	}

	public void setQuery(Ticket query) {
		this.query = query;
	}

	@Override
	public String execute() {
		return SUCCESS;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
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

	@SuppressWarnings("unchecked")
	@Override
	protected void executeQuery() {
		String querySql = "select t.id,t.name,t.price,t.kind,t.period,t.scope,t.active_code activeCode,t.effective,"
				+ "tl.limit_price,tl.limit_club,tl.image from tb_ticket t "
				+ "left join tb_ticket_limit tl on t.id = tl.ticket where 1=? order by t.id desc";
		pageInfo = service.findPageBySql(querySql, pageInfo, "1");
		List<Map<String, Object>> list = pageInfo.getItems();
		for (Map<String, Object> map : list) {
			if (map.get("limit_club") != null && StringUtils.isNotEmpty(String.valueOf(map.get("limit_club")))) {
				String[] clubIds = String.valueOf(map.get("limit_club")).split(",");
				String limit_club = "";
				for (String clubId : clubIds) {
					Member club = (Member) service.load(Member.class, Long.valueOf(clubId));
					limit_club += club.getName() + ",";
				}
				limit_club = limit_club.substring(0, limit_club.length() - 1);
				map.put("limit_club", limit_club);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	protected Long executeSave() {
		if (ticket != null) {
			// 保存优惠券
			if (ticket.getActiveCode() == null || "".equals(ticket.getActiveCode()))
				ticket.setActiveCode(Application.getRandom());
			ticket = (Ticket) service.saveOrUpdate(ticket);
			// 保存优惠券限制条件
			String limit_price = "";
			String limit_club = "";
			String[] strs = {};
			Map map = service.queryForMap("select id from tb_ticket_limit where ticket = " + ticket.getId());
			if (map.get("id") != null && StringUtils.isNotEmpty(String.valueOf(map.get("id")))) {
				// 修改逻辑
				StringBuilder updateSql = new StringBuilder("update tb_ticket_limit set");
				// 如果填写了价格限制就取出并修改
				strs = (String[]) param.get("limit_price");
				if (StringUtils.isNotEmpty(strs[0])) {
					limit_price = strs[0];
					updateSql.append(" limit_price =" + limit_price + ",");
				}
				// 如何选择了俱乐部限制就取出并保存
				strs = (String[]) param.get("limit_club");
				if (StringUtils.isNotEmpty(strs[0])) {
					for (String string : strs) {
						limit_club += string + ",";
					}
					limit_club = limit_club.substring(0, limit_club.length() - 1);
					updateSql.append(" limit_club ='" + limit_club + "',");
				}
				// 调用图片上传
				imageFileName = saveFile("picture", image, imageFileName, null);
				if (imageFileName != null) {
					updateSql.append(" iamge ='" + imageFileName + "',");
				}
				updateSql.delete(updateSql.length() - 1, updateSql.length());
				updateSql.append(" where id = " + map.get("id"));
				service.executeUpdate(updateSql.toString());
				return ticket.getId();
			} else {
				// 添加逻辑
				StringBuilder insertSql = new StringBuilder("insert into tb_ticket_limit values(null,");
				insertSql.append(ticket.getId() + ",");
				// 如果填写了价格限制就取出并保存
				strs = (String[]) param.get("limit_price");
				if (StringUtils.isNotEmpty(strs[0])) {
					limit_price = strs[0];
					insertSql.append(limit_price + ",");
				} else {
					insertSql.append("null,");
				}
				// 如何选择了俱乐部限制就取出并保存
				strs = (String[]) param.get("limit_club");
				if (StringUtils.isNotEmpty(strs[0])) {
					for (String string : strs) {
						limit_club += string + ",";
					}
					limit_club = limit_club.substring(0, limit_club.length() - 1);
					insertSql.append("'" + limit_club + "',");
				} else {
					insertSql.append("null,");
				}
				// 调用图片上传
				imageFileName = saveFile("picture", image, imageFileName, null);
				if (imageFileName != null) {
					insertSql.append("'" + imageFileName + "',");
				} else {
					insertSql.append("null,");
				}
				insertSql.delete(insertSql.length() - 1, insertSql.length());
				insertSql.append(")");
				service.executeUpdate(insertSql.toString());
				return ticket.getId();
			}
		}
		return null;
	}

	@Override
	protected Class<?> getEntityClass() {
		return Ticket.class;
	}

	@Override
	protected String getExclude() {
		return "ticklings,judges,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets";
	}
}
