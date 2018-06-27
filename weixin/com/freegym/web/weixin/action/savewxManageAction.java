package com.freegym.web.weixin.action;

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.system.Area1;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class savewxManageAction extends BaseBasicAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1431277224167086030L;
	/**
	 * 坐标城市
	 */
	protected String city = "武汉市";

	public void save() {
		try {
			// 地区
			@SuppressWarnings("unchecked")
			List<Area1> aList = (List<Area1>) service.findObjectBySql("from Area1 a where a.name like ? and a.type = ? ",new Object[] { "%" + city + "%", '1' });
			System.out.println(aList);
			final JSONArray jarr1 = new JSONArray();
			if (aList != null && aList.size() > 0) {
				@SuppressWarnings("unchecked")
				List<Area1> areaList = (List<Area1>) service.findObjectBySql("from Area1 a where a.parent = ?", aList.get(0).getId());
				for (final Iterator<?> it = areaList.iterator(); it.hasNext();) {
					final JSONObject obj = new JSONObject();
					final Area1 area = (Area1) it.next();
					jarr1.add(obj.accumulate("id", area.getId()).accumulate("name", area.getName()));
				System.out.println(jarr1);
				}
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
}

