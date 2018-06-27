package com.freegym.web.config.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/member/frequency.jsp") })
public class FrequencyManageAction extends ConfigBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	public static Map<String, String> messages1 = new HashMap<String, String>();
	public static Map<String, String[][]> messages2 = new HashMap<String, String[][]>();

	static {
		messages1.put("A", "2-3");
		messages1.put("B", "2-3");
		messages1.put("C", "3-4");
		messages1.put("D", "4-6");

		messages2.put("A", new String[][] { { "5-6", "5-6", "5-6", "5" }, { "20-40", "20", "20", "40-60" } });
		messages2.put("B", new String[][] { { "5-6", "3", "3", "5" }, { "40", "40", "20", "40-60" } });
		messages2.put("C", new String[][] { { "5-6", "3", "3", "5" }, { "40", "40", "40", "40-60" } });
		messages2.put("D", new String[][] { { "5-6", "3", "4", "5" }, { "40-60", "40-60", "40", "40-60" } });
	}

	public String execute() {
		session.setAttribute("spath", 8);
		if (setting.getCurrGymStatus() != null && !"".equals(setting.getCurrGymStatus())) {
			request.setAttribute("llmsg", messages1.get(setting.getCurrGymStatus()));
			if (setting.getTarget() != null && !"".equals(setting.getTarget())) {
				final int target = Integer.parseInt(setting.getTarget()) - 1;
				String[][] strings = messages2.get(setting.getCurrGymStatus());
				request.setAttribute("yyplmsg", strings[0][target]);
				request.setAttribute("yysjmsg", strings[1][target]);
			}
			
		}
		String strengthDate = setting.getStrengthDate();
		List<String> list1 = new ArrayList<String>();
		if (strengthDate != null) {
			String[] arrs = strengthDate.split(",");
			for (int i = 0; i < arrs.length; i++)
				list1.add(arrs[i].trim());
		}
		String cardioDate = setting.getCardioDate();
		List<String> list2 = new ArrayList<String>();
		if (cardioDate != null) {
			String[] arrs = cardioDate.split(",");
			for (int i = 0; i < arrs.length; i++)
				list2.add(arrs[i].trim());
		}
		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);
		return SUCCESS;
	}
}
