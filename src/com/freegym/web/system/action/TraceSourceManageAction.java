package com.freegym.web.system.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.alibaba.fastjson.JSONArray;
import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.freegym.web.SystemBasicAction;
import com.freegym.web.utils.EasyUtils;

import net.sf.json.JSONObject;

/**
 * @author hw
 * @version 创建时间：2018年3月9日 下午1:50:47
 * @ClassName 类名称
 * @Description 类描述
 */

@Namespace("")
@Results({ @Result(name = "success", location = "/manager/system/trace_source.jsp") })
public class TraceSourceManageAction extends SystemBasicAction implements Constantsms {

	private static final long serialVersionUID = 2L;

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
		// 生成sql语句
		List<Object> args = new ArrayList<Object>();
		StringBuilder querySql = new StringBuilder();
		querySql.append("select t.openid openId, t.scan_date scanDate, t.source, IFNULL(m.id,0) member,");
		querySql.append(" IFNULL(o.id,0) orderId, IFNULL(o.no,0) orderNo, IFNULL(o.status,0) orderStatus");
		querySql.append(" from tb_trace_source t left join tb_member m");
		querySql.append(" on t.openid = m.wechatID left join(select * from (");
		querySql.append(" select id,no,member,`status` from tb_product_order UNION ALL");
		querySql.append(" select id,no,member,`status` from tb_courserelease_order  UNION ALL");
		querySql.append(" select id,no,member,`status` from tb_planrelease_order  UNION ALL");
		querySql.append(" select id,no,member,`status` from tb_active_order  UNION ALL");
		querySql.append(" select id,no,member,`status` from tb_goods_order  UNION ALL");
		querySql.append(" select id,no,member,`status` from tb_product_order_v45");
		querySql.append(" ) po group by po.member order by po.status desc) o on m.id = o.member");
		String[] strArr = (String[]) query.get("origin");
		String[] strArr1 = (String[]) query.get("startDate");
		String[] strArr2 = (String[]) query.get("endDate");
		if (StringUtils.isNotEmpty(strArr[0])) {
			querySql.append(" where t.source = ?");
			args.add(strArr[0]);
		} else {
			querySql.append(" where t.source in(?,?)");
			args.add("adwy1");
			args.add("adwy2");
		}
		if (StringUtils.isNotEmpty(strArr1[0])) {
			querySql.append(" and t.scan_date between ? and ?");
			args.add(strArr1[0].split("T")[0]);
			args.add(EasyUtils.addDate(EasyUtils.formatStringToDate(strArr2[0].split("T")[0]), 1));
		}
		querySql.append(" order by t.scan_date");
		// 查询数据
		pageInfo.setPageSize(20);
		Object[] obj = new Object[args.size()];
		int i = 0;
		for (Object object : args) {
			obj[i] = object;
			i++;
		}
		pageInfo = service.findPageBySql(querySql.toString(), pageInfo, obj);
		EasyUtils.dateANDdecimalFormat(pageInfo.getItems(), "yyyy-MM-dd");
	}

	public void findStatus() {
		JSONArray jarr = new JSONArray();
		jarr.add(new JSONObject().accumulate("id", "adwy1").accumulate("name", "岳杨"));
		jarr.add(new JSONObject().accumulate("id", "adwy2").accumulate("name", "陈瑶"));
		response(jarr.toJSONString());
	}
}
