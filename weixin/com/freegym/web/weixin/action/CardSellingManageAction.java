package com.freegym.web.weixin.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.chinapay.config.ChinaPayConfig;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
@SuppressWarnings("serial")
@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/eg/cardSelling.jsp"),
	@Result(name = "package", location = "/eg/package.jsp"),
	@Result(name = "submitorder", location = "/eg/submit_order.jsp"),
    @Result(name="coupon",location="/eg/coupon.jsp"),
    @Result(name = "login", location = "/WX/login.jsp")
	 })
public class CardSellingManageAction extends BaseBasicAction{
	  private Long pid;
	  private Long mid;
	  private Date orderdate;
	  private String payNo;
	  private String pname;
	  private String summoney;
	  
       public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getSummoney() {
		return summoney;
	}

	public void setSummoney(String summoney) {
		this.summoney = summoney;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}
    
	@Override
	public String execute() {
		/*Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return "login";
		} else {*/
			return cardSelling();
		/*}*/
	}
	
	public String cardSelling(){
    	   String sql="select p.*,mem.id mid,mem.name mname from tb_product p join tb_member mem on p.member=mem.id where p.member in (select id from tb_member tm where tm.id in(select m.friend from tb_member_friend m  join tb_member f on f.id=m.member where f.role='E' and f.id=? UNION all select id from tb_member where id=?) and tm.role='E')";
    	   pageInfo=service.findPageBySql(sql, pageInfo, new Object[]{454,454});
    	   request.setAttribute("pageInfo",pageInfo);
		return SUCCESS;	      	   
       }
       
       public String getPackage(){
    	final Member m =(Member) service.load(Member.class,479L);
    	Product pr = (Product) service.load(Product.class, pid);
        String sql="select name from tb_member tm where tm.id in (select m.friend from tb_member_friend m  join tb_member f on f.id=m.member where f.role='E' and f.id=? UNION ALL select id from tb_member where id=?) and tm.role='E'";
        List<Map<String,Object>> list=service.queryForList(sql, new Object[]{454,454});
        request.setAttribute("pr", pr);
   		request.setAttribute("author", pr.getMember().getName());
   		request.setAttribute("customer", m);
   		request.setAttribute("friends", list);
		return "package";
       }
       
       public String submitOrder(){
    	  final Member m=(Member) service.load(Member.class,mid);
    	  final Product p=(Product) service.load(Product.class,pid);
    	  if (m.getMobileValid() != null && m.getMobileValid().equals("1") && m.getMobilephone() != null && m.getMobilephone().length() > 7) {
  			request.setAttribute("mobilephone", m.getMobilephone().replaceAll(m.getMobilephone().substring(3, 7), "*****"));
  		}
    	  request.setAttribute("customer", m);
          request.setAttribute("p", p);
          request.setAttribute("orderdate", orderdate);
		  return "submitorder";
       }
       
       public String getCoupons(){
     	  String sql="select *,DATE_FORMAT(DATE_ADD(mt.active_date,INTERVAL tt.period DAY),'%Y-%m-%d') endDate,DATE_FORMAT(mt.active_date,'%Y-%m-%d')startDate from tb_member_ticket mt join tb_ticket tt on mt.ticket=tt.id where mt.member=? and mt.status=1 ";
          List<Map<String,Object>> list=service.queryForList(sql, new Object[]{mid});
          request.setAttribute("coupons", list);
    	   return "coupon";
       }
       
       
       @SuppressWarnings("deprecation")
	public String saveOrder() throws ParseException {
   		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   		final Member m =(Member) service.load(Member.class,mid);
    	Product pr = (Product) service.load(Product.class, pid);
   		ProductOrder pro=new ProductOrder();
   		payNo = service.getKeyNo("", "CARDCOL_ORDER_NO", 13);
   		pro.setNo(payNo);   		
   		payNo = payNo.substring(0, 2) + payNo.substring(4, 6) + ChinaPayConfig.MerId.substring(ChinaPayConfig.MerId.length() - 5) + payNo.substring(6);
   		// 交易号从原有16位改为28位，前8位数字不变，后面填充至28位:如：之前交易号是：2014102200001111，升级后变成：2014102200001111222233334444
   		// 在这里直接在后面填充至28位
   		payNo = payNo + payNo.substring(0, 12);
        pro.setCount(1);
        pro.setPayNo(payNo);
        pro.setOrderDate(orderdate);
        pro.setOrderStartTime(new Date(sdf.format(orderdate)));
        pro.setOrderMoney(Double.parseDouble(summoney));
        pro.setPayServiceCostE(0d);
        pro.setPayServiceCostM(0d);
        pro.setStatus('0');
        pro.setSurplusMoney(Double.parseDouble(summoney));
        pro.setUnitPrice(Double.parseDouble(summoney));
        pro.setProduct(pr);
        pro.setMember(m);
        pro.setOrigin('B');
        service.saveOrUpdate(pro);
        if (m.getMobileValid() != null && m.getMobileValid().equals('1') && m.getMobilephone() != null) {
   			request.setAttribute("mobilephone", m.getMobilephone().replaceAll(m.getMobilephone().substring(3, 7), "*****"));
   		}
   		return "success";
   	}
       
       
       
       
       
       
       
}
