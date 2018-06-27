package com.freegym.web.weixin.action;

import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Order;
import com.freegym.web.order.PickDetail;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONArray;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/login.jsp"),
		@Result(name = "income", location = "/WX/mywallet.jsp") })
public class MyWalletWxManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8728264604077534376L;

	@Override
	public String execute() {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return SUCCESS;
		} else {
			return income();
		}
	}

	/**
	 * 收入
	 */
	public String income() {
		Member member = (Member) session.getAttribute("member");
		pageInfo = service.income(pageInfo, member.getId());
		@SuppressWarnings("unused")
		JSONArray income = JSONArray.fromObject(pageInfo.getItems());
		
		return "income";
	}

	/**
	 * 支出
	 */
	public String expenditure() {
		Member member = (Member) session.getAttribute("member");
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM (").append(Order.getOrderSqls()).append(") t where 1 = 1");
		sql.append(" and (fromId = ?) and STATUS !=0 ");
		sql.append("order by payTime desc");
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, member.getId());
		
		return "income";
	}

	/**
	 * 提现记录查询
	 */
	public String findPickDetail() {
		Member member = (Member) session.getAttribute("member");
		// 查询余额
		Double pickMoneyCount = service.findPickMoneyCountByMember(member);
		request.setAttribute("pickMoneyCount", pickMoneyCount);
		// 查询提现记录
		pageInfo = service.queryPickDetail(pageInfo, member.getId().toString());
		
		return "income";
	}

	/**
	 * 提现
	 */
	public String savePickDetail() {
		Double pickMoney = new Double(request.getParameter("pickMoney"));
		String msg = "";
		PickDetail pickDetail = new PickDetail();
		if (pickDetail != null) {
			try {
				if (getMobileUser().getMobilephone() != null && !"".equals(getMobileUser().getMobilephone())) {
					if (isRightful(getMobileUser().getMobilephone(), pickDetail.getIdentificationCode())) {
						pickDetail.setNo(service.getKeyNo("", "TB_PICK_DETAIL", 14));
						pickDetail.setType("1");
						pickDetail.setFlowType("1");
						pickDetail.setMember((Member) getMobileUser());
						pickDetail.setPickDate(new Date());
						pickDetail.setStatus("1");
						pickDetail.setPickMoney(pickMoney);
						service.saveOrUpdate(pickDetail);
						msg = "ok";
					} else {
						msg = "no";
					}
				} else {
					msg = "NoMobile";
				}
			} catch (LogicException e) {
				msg = e.getMessage();
				log.error("error", e);
			}
			response(msg);
		}
		return null;
	}
}
