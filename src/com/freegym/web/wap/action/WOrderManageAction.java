package com.freegym.web.wap.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.mobile.action.MOrderManageAction;
import com.freegym.web.order.Order;
import com.sanmen.web.core.bean.BaseMember;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class WOrderManageAction extends MOrderManageAction {

	private static final long serialVersionUID = 1L;

	@Override
	protected BaseMember getLoginMember() {
		// return (BaseMember) getService().load(Member.class, 490l);
		return toMember();
	}

	/**
	 * 订单管理
	 */
	@Override
	public void orderList() {
		try {
			final JSONObject obj = new JSONObject();
			final StringBuffer sql = new StringBuffer();
			String orderSql = Order.getOrderSql();
			sql.append("SELECT * FROM (").append(orderSql).append(") t where ( status = ?)  ");
			final List<Object> params = new ArrayList<Object>();
			params.add("0");
			sql.append("and fromId = ? ");
			params.add(id);
			sql.append("order by payNo ");
			pageInfo = service.findPageBySql(sql.toString(), pageInfo, params.toArray());

			final JSONArray items = new JSONArray();
			for (Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
				Map<?, ?> map = (Map<?, ?>) it.next();
				JSONObject jobj = new JSONObject();

				jobj.accumulate("id", map.get("id")).accumulate("no", map.get("no")).accumulate("name", map.get("name")).accumulate("status", map.get("status"))
						.accumulate("isappraise", map.get("isappraise")).accumulate("productId", map.get("productId"))
						.accumulate("orderType", map.get("orderType")).accumulate("toId", map.get("toId"));

				items.add(jobj);
			}

			obj.accumulate("success", true).accumulate("currentPage", pageInfo.getCurrentPage()).accumulate("totalPage", pageInfo.getTotalPage())
					.accumulate("items", items);
			response(obj);

		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
}
