package sport.action;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;

import ecartoon.wx.util.loginCommons;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({
	@Result(name = "oneCardDetail", location = "/sport/detail.jsp"),
	@Result(name="findOneCardList",location = "/sport/product_list2.jsp")
})
public class SProductWXManageAction extends BasicJsonAction {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -3921772783805718044L;
	
	/**
	 * 健身卡列表
	 */
	public String findOneCardList() {
		Member member = (Member) request.getSession().getAttribute("member");
		if(member == null){
			loginCommons.thirdType = "W";
			new loginCommons().setMember(request,service);
			member = (Member) request.getSession().getAttribute("member");
		}else{
			member = (Member) service.load(Member.class, member.getId());
			request.getSession().setAttribute("member", member);
		}
		String sql = " SELECT a.id,a.prod_name,a.prod_price,a.prod_image,a.prod_detail_image,a.prod_content,a.prod_summary,a.prodPeriodMonth,b.clubNum,IFNULL(c.saleNum,0) AS saleNum  "
				+ " FROM (SELECT id,prod_name,prod_price,prod_image,prod_detail_image,prod_content,prod_summary,  "
				+ " (CASE prod_period_unit WHEN 'D' THEN prod_period WHEN 'A' THEN prod_period * 30 WHEN 'B' THEN prod_period * 3 * 30 ELSE prod_period * 12 * 30 END) AS prodPeriodMonth   "
				+ " FROM tb_product_v45 WHERE id in (SELECT pc.product from tb_product_club_v45 pc, tb_member m WHERE pc.club = m.id ) AND prod_status = 'B' ) a   "
				+ " LEFT JOIN (SELECT pc.product,COUNT(1) AS clubNum from tb_product_club_v45 pc,tb_member m WHERE pc.club = m.id GROUP BY pc.product) b   "
				+ " ON a.id = b.product LEFT JOIN (SELECT order_product,COUNT(order_product) AS saleNum from tb_product_order_v45 GROUP BY order_product) c   "
				+ " ON a.id = c.order_product ";
		List<Map<String, Object>> oneList = DataBaseConnection.getList(sql, null);
		JSONArray onecardlist = new JSONArray();
		JSONObject objx = null;
		if(oneList.size() > 0){
			for (Map<String, Object> map : oneList) {
				objx = new JSONObject();
				objx.accumulate("id",map.get("id"))
				    .accumulate("prodName", map.get("prod_name"))
				    .accumulate("prodImage", map.get("prod_image"))
				    .accumulate("prodDetailImage", map.get("prod_detail_image"))
				    .accumulate("prodPrice", map.get("prod_price"))
				    .accumulate("prodPeriodMonth", map.get("prodPeriodMonth"))
				    .accumulate("clubNum", map.get("clubNum"))
				    .accumulate("saleNum", map.get("saleNum"))
				    .accumulate("prodSummary", map.get("prod_summary"));
				onecardlist.add(objx);
			}
		}
		final JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("prodList", onecardlist);
		request.setAttribute("prodInfo", obj);
		return "findOneCardList";
	}
	
	/**
	 * 健身卡详情
	 */
	public String oneCardDetail() {
		try {
			int id = Integer.valueOf(request.getParameter("id"));
			String sql = "select tp.*, count(tpc.id) as clubCount from tb_product_v45 tp,tb_product_club_v45 tpc where tp.id = tpc.product and tp.id = ? ";
			Object[] obj = { id };
			List<Map<String, Object>> oneList = DataBaseConnection.getList(sql, obj);
			JSONObject ret = new JSONObject();
			if (oneList.size() > 0) {
				Map<String, Object> map = oneList.get(0);
				     ret.accumulate("success", true)
				        .accumulate("message", "OK")
				        .accumulate("id", map.get("id"))
						.accumulate("prod_name", map.get("PROD_NAME"))
						.accumulate("prod_price", map.get("PROD_PRICE"))
						.accumulate("prod_image", map.get("PROD_IMAGE"))
						.accumulate("prod_content", map.get("PROD_CONTENT"))
						.accumulate("prod_period", map.get("PROD_PERIOD"))
						.accumulate("prod_summary", map.get("PROD_SUMMARY"))
						.accumulate("clubCount", map.get("clubCount"));
				//response(ret);
				request.setAttribute("ecartoonDetail", ret);
				return "oneCardDetail";
			} else {
				ret.accumulate("success", false).accumulate("message", "查询一卡通数据异常");
				response(ret);
				return "";
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return "";

	}

}
