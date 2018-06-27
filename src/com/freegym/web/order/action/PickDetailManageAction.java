package com.freegym.web.order.action;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.OrderBasicAction;
import com.freegym.web.order.PickAccount;
import com.freegym.web.order.PickDetail;
import com.sanmen.web.core.common.LogicException;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/order/pick_detail.jsp"),
		@Result(name = "pick_detail_list", location = "/order/pick_detail_list.jsp") })
public class PickDetailManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private PickAccount pickAccount;

	private PickDetail pickDetail;

	private PickDetail query;

	private Double pickMoneyCount;// 可用提现总金额

	public PickDetail getQuery() {
		return query;
	}

	public void setQuery(PickDetail query) {
		this.query = query;
	}

	public Double getPickMoneyCount() {
		return pickMoneyCount;
	}

	public void setPickMoneyCount(Double pickMoneyCount) {
		this.pickMoneyCount = pickMoneyCount;
	}

	private List<PickAccount> pickAccountList = new ArrayList<PickAccount>();

	public PickDetail getPickDetail() {
		return pickDetail;
	}

	public void setPickDetail(PickDetail pickDetail) {
		this.pickDetail = pickDetail;
	}

	public List<PickAccount> getPickAccountList() {
		return pickAccountList;
	}

	public void setPickAccountList(List<PickAccount> pickAccountList) {
		this.pickAccountList = pickAccountList;
	}

	public PickAccount getPickAccount() {
		return pickAccount;
	}

	public void setPickAccount(PickAccount pickAccount) {
		this.pickAccount = pickAccount;
	}

	@SuppressWarnings("unchecked")
	public String query() {
		pickAccountList = (List<PickAccount>) service.findObjectBySql("from PickAccount pa where pa.member.id = ? ", toMember().getId());
		pickMoneyCount = Double.parseDouble(new DecimalFormat("##.##").format(service.findPickMoneyCountByMember(toMember())));
		session.setAttribute("spath", "5");
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String savePickAccount() {
		if (pickAccountList != null && pickAccountList.size() > 0) {
			List<PickAccount> removeList = new ArrayList<PickAccount>();
			for (PickAccount pa : pickAccountList) {
				if (pa.getAccount() != null && "".equals(pa.getAccount()) && pa.getName() != null && "".equals(pa.getName())
						&& pa.getBankName() != null && "".equals(pa.getBankName())) {
					// pickAccountList.remove(pa);
					removeList.add(pa);
				}
			}
			if (removeList.size() > 0) {
				pickAccountList.removeAll(removeList);
			}
			pickAccountList = (List<PickAccount>) service.saveOrUpdate(pickAccountList);
		}
		return SUCCESS;
	}

	public String delete() {
		String msg = "";
		if (pickAccount != null && pickAccount.getId() != null) {
			try {
				service.delete(PickAccount.class, pickAccount.getId());
				msg = "ok";
			} catch (LogicException e) {
				msg = e.getMessage();
				log.error("error", e);
			}
			response(msg);
		}
		return null;
	}

	public String save() {
		String msg = "";
		if (pickDetail != null) {
			try {
				if (toMember().getMobilephone() != null && !"".equals(toMember().getMobilephone())) {
					if (isRightful(toMember().getMobilephone(), pickDetail.getIdentificationCode())) {
						pickDetail.setNo(service.getKeyNo("", "TB_PICK_DETAIL", 14));
						pickDetail.setType("1");
						pickDetail.setFlowType("1");
						pickDetail.setMember(toMember());
						pickDetail.setPickDate(new Date());
						pickDetail.setStatus("1");
						service.saveOrUpdate(pickDetail);
						msg = "ok";
					} else {
						msg = "no";
					}
				} else {
					msg = "NoMobile";
				}
			} catch (LogicException e) {
				msg = e.getMessage();
				log.error("error", e);
			}
			response(msg);
		}
		return null;
	}

//	public String queryList() {
//		try {
//		if (query == null) {
//			query = new PickDetail();
//		}
//		query.setMember(toMember());
//		pageInfo = service.findPageByCriteria(PickDetail.getCriteriaQuery(query), this.pageInfo,4);
//		session.setAttribute("spath", 5);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "pick_detail_list";
//	}
	
	
	public String queryList() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
		if (query == null) {
			query = new PickDetail();
		}
		query.setMember(toMember());
		String sqlx = " select pd.* from tb_pick_detail pd, tb_member m  where pd.member = m.id and 1=1 ";
		if (query.getMember() != null){
			sqlx += " and pd.member = " + query.getMember().getId();  
		}
		if (query.getStartDate() != null) {
			if (query.getEndDate() != null){
				sqlx += " and  pd.pickDate BETWEEN  '" + sdf.format(query.getStartDate()) + "' AND DATE_ADD('" + sdf.format(query.getEndDate()) + "',INTERVAL 1 DAY) ";
			} else {
				sqlx = " and " + sdf.format(query.getStartDate()) + " <= pd.pickDate ";
			}
		} else {
			if (query.getEndDate() != null ) {
				sqlx += " and pd.pickDate <= " + sdf.format(query.getEndDate());
			}
		}
		sqlx += " order by pickDate desc ";
		PickDetail pickDetail  = new PickDetail();
		List<Map<String, Object>> pickListMap = DataBaseConnection.getList(sqlx, null);
		List<PickDetail> listPick = new ArrayList<PickDetail>();
		//查询到了提现记录
		for (Map<String, Object> map : pickListMap) {
			pickDetail = new PickDetail();
			//将提现记录load出来
			pickDetail = (PickDetail) service.load(PickDetail.class, Long.valueOf(String.valueOf(map.get("id"))));
			//将load出来的pickDetail装进list中
			listPick.add(pickDetail);
		}
		//将listPick装进pageInfo
		pageInfo.setItems(listPick);
		session.setAttribute("spath", 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "pick_detail_list";
	}

	public void getMobileValidCode() {
		String msg = "OK";
		if (toMember().getMobilephone() != null && !"".equals(toMember().getMobilephone())) {
			sendSmsValidate(toMember().getMobilephone(), "1");
		} else {
			msg = "NoMobile";
		}
		response(msg);
	}

	@Override
	protected String getExclude() {
		return "member,pickAccount";
	}
}
