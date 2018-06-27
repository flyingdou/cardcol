package com.freegym.web.weixin.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;

import net.sf.json.JSONArray;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/WX/login.jsp"),
		@Result(name = "cardlist", location = "/eg/cardSelling.jsp"),
		@Result(name = "locations", location = "/eg/locations.jsp"),
		@Result(name = "carddetail", location = "/eg/detail.jsp"),
		@Result(name = "orderinfo", location = "/eg/orderinfo.jsp") })
public class OnlineCardManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 6882279186126403406L;
	private long mid;
	private long cid;
	private String stores; 

	public String getStores() {
		return stores;
	}

	public void setStores(String stores) {
		this.stores = stores;
	}

	public long getMid() {
		return mid;
	}

	public void setMid(long mid) {
		this.mid = mid;
	}

	public long getCid() {
		return cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	@Override
	public String execute() {
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return SUCCESS;
		} else {
			return findOneCardList();
		}
	}

	/**
	 * 根据俱乐部 id 查找所有关联俱乐部的并且 typeId = 6 的所有关联健身卡的信息
	 * 
	 * @param Long mid : 俱乐部编号
	 * 
	 * @return  name 卡的编号，cost 卡的名称，cost 卡的价格，image1 卡的图片，freeProject 免费项目，memberName 卡的发行俱乐部的名字 
	 */
	public String findOneCardList() {
		String sql = "SELECT p.id, P.name,	p.cost, P.image1, P.freeProject, m.name as memberName, m.id as mid FROM tb_product as p , tb_member m WHERE proType = '6' AND P.member IN (SELECT id FROM tb_member WHERE role = 'E' AND id IN (SELECT friend FROM tb_member_friend WHERE member = ?))AND P.member = m.id";
		pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { 783 });// 783为测试俱乐部

		return "cardlist";
	}

	/**
	 * 查询卡的详情 和 适用门店数量
	 * 
	 * @param String cid: 健身卡编号
	 * 
	 * @return name 卡的名字, freeProject 免费项目（摘要）,image1 图片, valid_period 有效期, cost 健身卡价格,costProject 付费项目,useRange 所示门店
	 */
	public String CardDetail() {
		String sql = "SELECT p.id, p.name, p.freeProject, p.image1, p.num1 AS valid_period, p.cost, p.costProject, p.useRange FROM tb_product as p WHERE id = ?";
		// pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { cid });
		// TODO 以后要删掉测试数据
		pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { 887 }); // 这个是测试数据
		
		return "carddetail";
	}

	/**
	 * 支付页面信息
	 * 
	 * @param String cid: 健身卡编号
	 * @param String date: 开卡日期
	 * 
	 * @return name 产品名, cost 产品价格
	 */
	public String OrderInfo() {
		// Test Data
		// TODO 以后去掉
		Member memberTest = new Member();
		memberTest.setMobilephone("110");
		session.setAttribute("member", memberTest);
		
		// 获取登录用户的账号 查看是否登录
		Member member = (Member) session.getAttribute("member");
		
		if (member == null) {
			return SUCCESS;
		} else {
			// 如果用户登录了 则保存订单信息 查询查询产品的详细信息
			
			String sql = "SELECT p.`name` as orderName, p.cost as orderCost FROM tb_product p WHERE p.id = ?";
			pageInfo = service.findPageBySql(sql, pageInfo, new Object[] { 384 });
			
			return "orderinfo";
		} 
	}
	
	/**
	 * 查询门店的地址信息 和 经纬度信息
	 * 
	 * @param Long mid : 门店 id （member id）
	 * 
	 * @return address 门店的地址, latitude 纬度, longitude 经度
	 */
	@SuppressWarnings("unused")
	public String stroesLocation() {
		if (isDigit(stores)) {
			Object[] locations = stores.split(",");
			StringBuffer stringBuffer = new StringBuffer("SELECT address, latitude, longitude FROM tb_member  WHERE id IN ( " );
			
			for ( Object location : locations) {
				stringBuffer.append("?,");
			}
			
			String sql = stringBuffer.substring(0, stringBuffer.toString().length() - 1).toString() + ")";
			pageInfo = service.findPageBySql(sql, pageInfo, locations);
			JSONArray jsonArray = JSONArray.fromObject(pageInfo.getItems());
			pageInfo.setItems(jsonArray);;
			
			return "locations";
		} else {
			return "carddetail";	
		}
	}
	
	/**
	 * 判断前端传过来的地址是否包含字母
	 * 
	 * @return 如果包含字母 返回：false 不包含返回：true
	 */
	public static Boolean isDigit (String str) {
		return str.replace(",", "").replaceAll(" ", "").matches("[0-9]{1,}");  
	}
}
