package com.freegym.web.mobile.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.json.XML;

import com.freegym.web.OrderBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class MWPayNotifyManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	@SuppressWarnings("unchecked")
	public String execute() throws UnsupportedEncodingException, Exception {
		// 解析结果存储在HashMap
		Map<String, String> map = new HashMap<String, String>();
		InputStream inputStream = request.getInputStream();
		// 读取输入流
		SAXReader reader = new SAXReader();
		Document document = reader.read(inputStream);
		// 得到xml根元素
		Element root = document.getRootElement();
		// 得到根元素的所有子节点
		List<Element> elementList = root.elements();

		// 遍历所有子节点
		for (Element e : elementList)
			map.put(e.getName(), e.getText());

		// 释放资源
		inputStream.close();
		inputStream = null;
		String resultcode = map.get("result_code");
		String orderno = map.get("out_trade_no");
		String tradeNo = map.get("out_trade_no");
		if (resultcode.equals("SUCCESS")) {
			service.updateOrderStatus(orderno, tradeNo);
		}
		return null;
	}

	public void H5Notify() {
		try {
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
			String resultcode = json.getString("result_code");
			String orderno = json.getString("out_trade_no");
			String tradeNo = json.getString("out_trade_no");
			if (resultcode.equals("SUCCESS")) {
				service.updateOrderStatus(orderno, tradeNo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
