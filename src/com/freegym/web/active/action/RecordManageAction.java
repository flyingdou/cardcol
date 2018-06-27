package com.freegym.web.active.action;

import java.util.Iterator;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.active.TrainRecord;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/active/active_record_list.jsp") })
public class RecordManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private TrainRecord query;

	private Character audit;

	public TrainRecord getQuery() {
		return query;
	}

	public void setQuery(TrainRecord query) {
		this.query = query;
	}

	public Character getAudit() {
		return audit;
	}

	public void setAudit(Character audit) {
		this.audit = audit;
	}

	public String execute() {
		onQuery();
		return SUCCESS;
	}

	private void onQuery() {
		final DetachedCriteria dc = DetachedCriteria.forClass(TrainRecord.class);
		dc.createAlias("activeOrder", "ao");
		dc.add(Restrictions.in("ao.judge", new Object[] { toMember().getName(), toMember().getNick() }));
		pageInfo.setPageSize(15);
		pageInfo.setOrder("doneDate");
		pageInfo.setOrderFlag("desc");
		pageInfo = service.findPageByCriteria(dc, pageInfo, 1);
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			final TrainRecord tr = (TrainRecord) it.next();
			tr.setStrength(service.getActualWeight(tr.getPartake().getId(), tr.getDoneDate()));
		}
	}

	public String audit() {
		service.updateRecordData(id, audit);
		onQuery();
		return SUCCESS;
	}

}
