package com.cardcolv45.mobile.action;

import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.OrderBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.common.Constants;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Order;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.PlanOrderDetail;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.service.IOrderService;
import com.freegym.web.service.impl.OrderServiceImpl;
import com.sanmen.web.core.BaseJsonAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class MGenPlanDataV45ManageAction extends OrderBasicAction {
	
	/**
	 * 生成计划数据
	 */
	public void createPlanData() {
		try {
			
			Long planOrderId = Long.valueOf(request.getParameter("planOrderId"));
			PlanOrder po = (PlanOrder) service.load(PlanOrder.class, planOrderId);
			PlanRelease pr = po.getPlanRelease();
			Order order = po;
			service.updateOrderStatus(po.getNo(), po.getNo(),"1");
			po.setStatus(ORDER_STATUS_PAID);
			po.setPayTime(new Date());
			po = (PlanOrder) service.saveOrUpdate(po);
			// 交易明细
			PlanOrderDetail od = new PlanOrderDetail();
			od.setOrder(po);
			od.setTransUser(po.getMember());
			od.setTransObject(new Member(1l));
			od.setInOutType(INOUT_TYPE_OUT);
			od.setDetailDate(new Date());
			od.setDetailMoney(po.getOrderMoney());
			od.setDetailType('1');
			service.saveOrUpdate(od);
			final Member productMember = (Member) service.load(Member.class, po.getPlanRelease().getMember().getId());
			productMember.setOrderCount((productMember.getOrderCount() == null ? 0 : productMember.getOrderCount()) + 1);
			service.saveOrUpdate(productMember);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
