package com.cardcolv45.mobile.action;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.mobile.BasicJsonAction;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class MLiveNotifyV45ManageAction extends BasicJsonAction {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 修改直播状态
	 */
	public void changeStatus() {
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
			JSONObject result = JSONObject.fromObject(resultStr);
			DataBaseConnection.updateData("update tb_live set live_state = ? where live_number = ?",
					new Object[] { result.get("event_type"), result.get("stream_id") });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
