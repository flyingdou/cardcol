package com.freegym.web.mobile;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cardcol.web.service.IClub45Service;
import com.freegym.web.basic.Member;

import net.sf.json.JSONObject;

public class BasicJsonAction extends MobileBasicAction {

	private static final long serialVersionUID = 7157114233222993271L;

	@Autowired
	@Qualifier("club45Service")
	protected IClub45Service service;

	@Override
	protected IClub45Service getService() {
		return service;
	}

	protected String Jsons;

	/**
	 * 计算每个俱乐部 教练 评分 评价人数
	 * 
	 */
	public JSONObject getGradeJson(Member m) {
		final JSONObject json = new JSONObject();
		if (m != null) {
			StringBuffer memberSql = new StringBuffer(Member.getMemberSql(null, null, null, null, null, null, null));
			memberSql.append(" and m.id = ?");
			final List<Map<String, Object>> maps = service.queryForList(memberSql.toString(), m.getId());
			Random random = new Random();
			for (final Map<String, Object> map : maps) {
				Long object = (Long) map.get("countEmp");
				if (null == object || object.equals(0l)) {
					int i = random.nextInt(10) + 1;
					map.put("countEmp", i);
					map.put("member_grade", random.nextInt(100 - 80 + 1) + 80);
				}
				json.accumulate("appraiseCount", map.get("countEmp")).accumulate("avgGrade", map.get("member_grade"));
			}
		}
		return json;
	}

	public String getJsons() {
		return Jsons;
	}

	public void setJsons(String jsons) {
		Jsons = getChinaStr(jsons);
		super.setJsons(jsons);
	}

}
