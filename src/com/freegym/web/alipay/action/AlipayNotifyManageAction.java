package com.freegym.web.alipay.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cardcol.web.alipay.AlipayConfig;
import com.freegym.web.OrderBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "shopping_order", location = "/order/shopping_order.jsp") })
public class AlipayNotifyManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	@Override
	public String execute() {
		// 获取支付宝GET过来反馈信息
		final Map<String, String> params = new HashMap<String, String>();
		final Map<?, ?> requestParams = request.getParameterMap();
		for (final Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			final String name = (String) iter.next();
			final String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		final String order_no = request.getParameter("out_trade_no"); // 获取订单号
		final String trade_no = request.getParameter("trade_no");
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)
		// 计算得出通知验证结果
		boolean rsaCheckV1;
		try {
			rsaCheckV1 = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET,
					AlipayConfig.SIGNTYPE);
			if (rsaCheckV1) {// 验证成功
				service.updateOrderStatus(order_no, trade_no);
				response("success");
			} else {
				response("failed");
				// 该页面可做页面美工编辑
				// response("验证失败");
			}
		} catch (AlipayApiException e1) {
			e1.printStackTrace();
			response("failed");
		} catch (Exception e) {
			e.printStackTrace();
			response("failed");
		}
		return null;
	}

	// 3.成功提交订单
	public String queryOrder() {
		return "shopping_order";
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		final String arg = "asdfasdfasdf[aadsfasdf]";
		int index = arg.indexOf('[');
		String tmp;
		if (index > 0) {
			tmp = arg.substring(index + 1, arg.length() - 1);
		}
	}
}
