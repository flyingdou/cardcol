package ecartoon.wx.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.json.XML;

import com.cardcol.web.order.ProductOrder45;
import com.freegym.web.basic.TraceSource;
import com.freegym.web.mobile.BasicJsonAction;

import common.util.HttpRequestUtils;
import ecartoon.wx.util.SDK_WX;
import net.sf.json.JSONObject;

/**
 * E卡通微信公众号相关功能
 * 
 * @author hw
 *
 */
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/ecartoon-weixin/success.jsp"),
		@Result(name = "fail", location = "/ecartoon-weixin/fail.jsp") })
public class EWechatWXManageAction extends BasicJsonAction {

	private static final long serialVersionUID = 1L;

	public static final String TOKEN = "yfkj_xfcamp_token";

	/**
	 * 获取微信sdk信息
	 */
	public void sign() {
		SDK_WX wx = new SDK_WX(request);
		String url = request.getParameter("url");
		String result = wx.sign(url);
		response(result);
	}

	/**
	 * 微信公众号支付签名
	 */
	public void paySign() {
		try {
			String openid = null;
			String result = null;
			SDK_WX wx = new SDK_WX(request);
			String productPrice = request.getParameter("productPrice");
			String orderNo = request.getParameter("orderNo");
			openid = String.valueOf(session.getAttribute("openId"));
			if (openid != null && !"".equals(openid)) {
				result = wx.paySign(openid, productPrice, orderNo);
			}
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 微信H5支付签名
	 */
	public void paySignH5() {
		try {
			String result = null;
			SDK_WX wx = new SDK_WX(request, "H5");
			ProductOrder45 productOrder45 = (ProductOrder45) service.load(ProductOrder45.class,
					Long.valueOf(request.getParameter("orderId")));
			result = wx.paySignH5(productOrder45);
			response(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建微信带参数的二维码
	 * 
	 * @throws Exception
	 */
	public void createWechatQrcode() throws Exception {
		SDK_WX sdk = new SDK_WX(request);
		// 第一步获取ticket
		JSONObject param = new JSONObject();
		param.accumulate("expire_seconds", 2592000).accumulate("action_name", "QR_STR_SCENE").accumulate("action_info",
				new JSONObject().accumulate("scene", new JSONObject().accumulate("scene_id", "test123")));
		String requestUrl = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";
		requestUrl = requestUrl.replace("TOKEN", sdk.getACCESS_TOKEN());
		JSONObject jsonObject = HttpRequestUtils.httpPost(requestUrl, param);
		// 第二步通过ticket换取二维码
		requestUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
		requestUrl = requestUrl.replace("TICKET", jsonObject.getString("ticket"));
		response(new JSONObject().accumulate("url", requestUrl));
	}

	/**
	 * 测试号配置
	 * 
	 */
	public void getToken() {
		try {
			String echostr = request.getParameter("echostr");
			if (StringUtils.isNotEmpty(echostr)) {
				// 返回给微信,表示收到了微信的通知
				response.getWriter().write(echostr);
			}
			// 接收微信发送的xml消息包
			InputStream inStream = request.getInputStream();
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			inStream.close();
			String resultStr = new String(outSteam.toByteArray(), "utf-8");
			org.json.JSONObject json = XML.toJSONObject(resultStr).getJSONObject("xml");
			List<?> list = service.findObjectBySql("from TraceSource t where t.openId = ? and t.source = ?",
					json.getString("FromUserName"), json.getString("EventKey"));
			TraceSource traceSource = null;
			if (list != null && list.size() > 0) {
				traceSource = (TraceSource) list.get(0);
				traceSource.setScanDate(new Date());
			} else {
				traceSource = new TraceSource();
				traceSource.setOpenId(json.getString("FromUserName"));
				traceSource.setScanDate(new Date());
				String source = json.getString("EventKey");
				if (source.indexOf("qrscene_") != -1) {
					source = source.split("qrscene_")[1];
				}
				traceSource.setSource(source);
			}
			service.saveOrUpdate(traceSource);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
