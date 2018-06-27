package com.freegym.web.chinapay.action;

import java.io.IOException;
import java.io.PrintWriter;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.BeanUtils;

import com.chinaums.pay.api.PayException;
import com.chinaums.pay.api.entities.NoticeEntity;
import com.chinaums.pay.api.impl.DefaultSecurityService;
import com.chinaums.pay.api.impl.UMSPayServiceImpl;
import com.freegym.web.OrderBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class ChinaNoticeManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -5092143094483340030L;

	@Override
	public String execute() {
		try {
			String signKeyMod = getMessage("chinapay.sign.private.mod");
			String signKeyExp = getMessage("chinapay.sign.private.exp");
			String verifyKeyMod = getMessage("chinapay.verify.private.mod");
			String verifyKeyExp = getMessage("chinapay.verify.private.exp");

			DefaultSecurityService ss = new DefaultSecurityService(); // 设置签名的商户私钥，验签的银商公钥
			ss.setSignKeyModHex(signKeyMod);// 签名私钥Mod
			ss.setSignKeyExpHex(signKeyExp);// 签名私钥Exp
			ss.setVerifyKeyExpHex(verifyKeyExp);
			ss.setVerifyKeyModHex(verifyKeyMod);
			UMSPayServiceImpl payService = new UMSPayServiceImpl();
			payService.setSecurityService(ss);
			// 1.银商会传这些参数过来
			NoticeEntity ne = payService.parseNoticeEntity(request);
			NoticeEntity re = new NoticeEntity();
			log.error("银商状态：" + ne.getTransState());
			if (ne.getTransState().equals("1")) {
				log.error("通知请求报文：" + getJsonString(ne));
				log.error("通知请求报文1：" + ne.convertToJsonString());
				// 2.处理银商传过来的参数，处理业务逻辑等。
				try {
					service.updateOrderStatus(ne.getMerOrderId(), ne.getTransId());
					re.setMerOrderState("00");// 00商户销账成功
				} catch (Exception e) {

				}
			}
			BeanUtils.copyProperties(ne, re);
			log.error("通知响应报文：" + getJsonString(re));
			log.error("通知响应报文1：" + re.convertToJsonString());
			// re.setMerOrderId(ne.getMerOrderId());
			// re.setTransType("NoticePay");
			// re.setMerId(ne.getMerId());
			String respStr = payService.getNoticeRespString(re);
			response.setCharacterEncoding("utf-8");
			PrintWriter writer = response.getWriter();
			System.out.println("params.toString()：" + respStr);
			writer.write(respStr);
			writer.flush();
			writer.close();
		} catch (PayException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
