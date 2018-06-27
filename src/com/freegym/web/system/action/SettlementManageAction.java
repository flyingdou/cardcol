package com.freegym.web.system.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.freegym.web.SystemBasicAction;
import com.freegym.web.utils.EasyUtils;

/**
 * @author hw
 * @version 创建时间：2018年3月9日 下午1:50:47
 * @ClassName 类名称
 * @Description 类描述
 */

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/order/balanceManager.jsp") })
public class SettlementManageAction extends SystemBasicAction implements Constantsms {
	private static final long serialVersionUID = 1L;

	private Map<String, Object> query;

	public Map<String, Object> getQuery() {
		return query;
	}

	public void setQuery(Map<String, Object> query) {
		this.query = query;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void executeQuery() {
		pageInfo.setPageSize(20);
		String querySql = "select tmp.id,tmp.orderNo,tmp.fromName,tmp.toName,tmp.balanceTime,tmp.balanceType,tmp.prodName,tmp.orderMoney,"
				+ "IFNULL(tmp.expenditure,0) expenditure ,IFNULL(tmp.income,0) income from (select t.*,(select BALANCE_MONEY from "
				+ "tb_order_balance_v45 where BALANCE_TO = ? AND id = t.id ) income,(select ORDER_MONEY expenditure from "
				+ "tb_order_balance_v45 where BALANCE_FROM = ? AND id = t.id ) expenditure from "
				+ "(select b.id,b.BALANCE_TIME balanceTime,b.ORDER_NO orderNo,b.BALANCE_TYPE balanceType,b.PROD_NAME prodName,m.name fromName,mm.name toName,"
				+ "b.BALANCE_MONEY balanceMoney,b.order_money orderMoney from tb_order_balance_v45 b inner join tb_member m on b.BALANCE_FROM = m.id inner join tb_member mm on b.balance_to = mm.id"
				+ " where b.balance_from = ? or b.balance_to = ?) t) tmp order by tmp.balanceTime desc";
		pageInfo = service.findPageBySql(querySql, pageInfo, new Object[] { 1, 1, 1, 1 });
		EasyUtils.dateANDdecimalFormat(pageInfo.getItems(), "yyyy-MM-dd");
	}
}
