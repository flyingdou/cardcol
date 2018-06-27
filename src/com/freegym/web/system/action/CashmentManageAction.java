package com.freegym.web.system.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.aliyn.api.geteway.util.SingleSendSms;
import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.SystemBasicAction;
import com.freegym.web.order.PickDetail;
import com.freegym.web.utils.EasyUtils;
import com.sanmen.web.core.common.ParamBean;

import net.sf.json.JSONObject;

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/order/cashment.jsp") })
public class CashmentManageAction extends SystemBasicAction implements Constantsms{

	private static final long serialVersionUID = -2297129011587931307L;
	
	@SuppressWarnings("unused")
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private Map<String, Object> query;
	
	public Map<String, Object> getQuery() {
		return query;
	}

	public void setQuery(Map<String, Object> query) {
		this.query = query;
	}

	@Override
	protected void executeQuery() {
        String sqlx=" select pddd.no, m.nick, m.name, pddd.pickMoney, balance.balance, "
        		  + " pddd.pickDate, pddd.pickAccount, pddd.status, pddd.remark, balance.memberId "
        		  + " from tb_member m,tb_pick_detail pddd LEFT JOIN "
        		  + " ( "
        		  + " select (CASE WHEN (bb.inMoney-pp.outMoney) < 0 THEN 0 ELSE (bb.inMoney-pp.outMoney) END) as  balance, bb.*,m.id as memberId from tb_member m, "
        		  + " (select sum(pickMoney) as outMoney,member from tb_pick_detail pd GROUP BY member ) as pp , "
        		  + " (SELECT sum(balance_money) as inMoney, balance_to from tb_order_balance_v45 ob  GROUP BY balance_to) as bb "
        		  + " where m.id = pp.member  "
        		  + " and m.id = bb.balance_to  "
        		  + " ) balance ON balance.balance_to = pddd.member "
        		  + " WHERE pddd.member = m.id order by pddd.pickDate desc ";
        List<Map<String, Object>> pickList =  DataBaseConnection.getList(sqlx, null);
        EasyUtils.dateANDdecimalFormat(pickList, "yyyy-MM-dd HH:mm:ss");
        pageInfo.setItems(pickList);
	}
	
	
	@Override
	protected void loadAreaPerm(final DetachedCriteria dc, final String str) {
		dc.add(Restrictions.eq("m.role", "E"));
		dc.add(Restrictions.sqlRestriction("this_.member in (select id from tb_member where city in (select name from tb_area where id in (" + str + ")))"));
	}

	/**
	 * 账户余额查询
	 */
	public void showMoney() {

	}

	/**
	 * 如果已经提现，则需要在这里进行处理为已提现状态
	 */
	public void handler() {
		try {
			final PickDetail pd = (PickDetail) service.load(PickDetail.class, id);
			pd.setStatus(String.valueOf(getAudit()));
			service.saveOrUpdate(pd);
			if ("2".equals(pd.getStatus())){
				SingleSendSms sms = new SingleSendSms();
				sms.sendMsg(pd.getMember().getMobilephone(), "{\"money\":\'"+pd.getPickMoney()+"\'}",TEMPLATE_CASHS_CODE );
			}
			response("{success: true}");
		} catch (Exception e) {
			e.printStackTrace();
			response("{success: false, message: '" + e.getMessage() + "'}");
		}
	}

	public void queryTrade() {
		final List<Map<String, Object>> list = service.queryTradeDetail(id);
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("items", getJsonString(list));
		response(obj);
	}

	/**
	 * 查找状态
	 */
	public void findStatus() {
		final List<ParamBean> beans = new ArrayList<ParamBean>();
		beans.add(new ParamBean(1, "处理中"));
		beans.add(new ParamBean(2, "已完成"));
		beans.add(new ParamBean(3, "失败"));
		response(getJsonString(beans));
	}

	@Override
	protected String getExclude() {
		return "applyClubs,order45s,balanceFroms,balanceTos,judges,courseGrades,coach,coachMembers,friends,clubs,coachs,members,visitors,setting,times,products,certificates,integrals,courses,alwaysAddrs,applys,coursess,records,courseCoachs,courseMembers,prods,appraises,appraises1,allFriends,orderDetails,orderDetails1,messages,messages1,orders,addrs,signIns,signIns1,shops,accounts,prodOrders,aos,actives,partakes,teams,tickets,ticklings,pickDetails";
	}
}
