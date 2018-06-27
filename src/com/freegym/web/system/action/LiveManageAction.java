package com.freegym.web.system.action;

import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.freegym.web.SystemBasicAction;
import com.freegym.web.basic.Live;
import com.freegym.web.utils.EasyUtils;

import net.sf.json.JSONObject;

/**
 * @author hw
 * @version 创建时间：2018年3月9日 下午4:33:42
 * @ClassName 类名称
 * @Description 类描述
 */

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/order/liveManager.jsp") })
public class LiveManageAction extends SystemBasicAction implements Constantsms {
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
		String querySql = "select l.*,m.name from tb_live l inner join tb_member m on l.member = m.id where 1=?"
				+ " order by l.live_history_time desc";
		pageInfo = service.findPageBySql(querySql, pageInfo, 1);
		EasyUtils.dateFormat(pageInfo.getItems(), "yyyy-MM-dd");
	}

	/**
	 * 关闭或者开启直播间
	 */
	public void openOrClose() {
		try {
			String id = request.getParameter("id");
			Live live = (Live) service.load(Live.class, Long.valueOf(id));
			if (live.getLiveState() == 0 || live.getLiveState() == 1) {
				live.setLiveState(2);
			} else {
				live.setLiveState(0);
			}
			service.saveOrUpdate(live);
			response(new JSONObject().accumulate("success", true));
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}
}
