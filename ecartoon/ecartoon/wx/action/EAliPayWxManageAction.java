package ecartoon.wx.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cardcol.web.alipay.AlipayConfig;
import com.cardcol.web.order.ProductOrder45;
import com.freegym.web.mobile.BasicJsonAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class EAliPayWxManageAction extends BasicJsonAction {

	private static final long serialVersionUID = 1L;

	public void aliPaySignH5() {
		try {
			ProductOrder45 productOrder45 = (ProductOrder45) service.load(ProductOrder45.class, Long.valueOf(request.getParameter("orderId")));
			// 商户订单号，商户网站订单系统中唯一订单号，必填
			String out_trade_no = productOrder45.getNo();
			// 订单名称，必填
			String subject = productOrder45.getProduct().getName();
			// 付款金额，必填
			String total_amount = String.valueOf(productOrder45.getOrderMoney());
			// 商品描述，可空
			String body = productOrder45.getProduct().getName();
			// 销售产品码 必填
			String product_code = "QUICK_WAP_WAY";
			/**********************/
			// SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
			// 调用RSA签名方式
			AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID,
					AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET,
					AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.SIGNTYPE);
			AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

			// 封装请求支付信息
			AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
			model.setOutTradeNo(out_trade_no);
			model.setSubject(subject);
			model.setTotalAmount(total_amount);
			model.setBody(body);
			model.setProductCode(product_code);
			alipay_request.setBizModel(model);
			// 设置异步通知地址
			alipay_request.setNotifyUrl(AlipayConfig.notify_url);
			// 设置同步地址
			alipay_request.setReturnUrl("http://www.ecartoon.com.cn/ecartoon-weixin/adwy4.html");

			// form表单生产
			String form = "";
			// 调用SDK生成表单
			form = client.pageExecute(alipay_request).getBody();
			response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
			response.getWriter().write(form);// 直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
