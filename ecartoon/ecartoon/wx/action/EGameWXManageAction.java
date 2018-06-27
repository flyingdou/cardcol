package ecartoon.wx.action;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.basic.Product45;
import com.cardcol.web.order.ProductOrder45;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.priceCutDown;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.utils.EasyUtils;

import ecartoon.wx.util.MathRandom4dou;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "qrlh", location = "/ecartoon-weixin/qrlh.jsp"),
		@Result(name = "detail", location = "/ecartoon-weixin/detail.jsp") })
public class EGameWXManageAction extends BasicJsonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 请人流汗
	 */
	public String pleaseSweat() {
		try {
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId.jsp").forward(request, response);
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
			if (oneList.size() > 0) {
				for (Map<String, Object> map : oneList) {
					objx = new JSONObject();
					objx.accumulate("id", map.get("id")).accumulate("prodName", map.get("prod_name"))
							.accumulate("prodImage", map.get("prod_image"))
							.accumulate("prodDetailImage", map.get("prod_detail_image"))
							.accumulate("prodPrice", map.get("prod_price"))
							.accumulate("prodPeriodMonth", map.get("prodPeriodMonth"))
							.accumulate("summary", map.get("prod_summary")).accumulate("clubNum", map.get("clubNum"))
							.accumulate("saleNum", map.get("saleNum"));
					onecardlist.add(objx);
				}
			}
			final JSONObject obj = new JSONObject();
			obj.accumulate("success", true).accumulate("prodList", onecardlist);
			request.setAttribute("prodInfo", obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "qrlh";
	}

	/**
	 * 请人流汗商品详情
	 */
	public void pleaseSweatDeatil() {
		try {
			// 首先判断用户是否已经登录
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId.jsp").forward(request, response);
			}

			int id = Integer.valueOf(request.getParameter("id"));
			String sql = "select tp.*, count(tpc.id) as clubCount from tb_product_v45 tp,tb_product_club_v45 tpc where tp.id = tpc.product and tp.id = ? ";
			Object[] obj = { id };
			List<Map<String, Object>> oneList = DataBaseConnection.getList(sql, obj);
			JSONObject ret = new JSONObject();
			if (oneList.size() > 0) {
				Map<String, Object> map = oneList.get(0);
				ret.accumulate("success", true).accumulate("message", "OK").accumulate("id", map.get("id"))
						.accumulate("prod_name", map.get("PROD_NAME")).accumulate("prod_price", map.get("PROD_PRICE"))
						.accumulate("prod_image", map.get("PROD_IMAGE"))
						.accumulate("prod_content", map.get("PROD_CONTENT"))
						.accumulate("prod_period", map.get("PROD_PERIOD"))
						.accumulate("prod_summary", map.get("PROD_SUMMARY"))
						.accumulate("clubCount", map.get("clubCount"));

				// 返回分享者的信息
				Member shareMember = (Member) service.load(Member.class,
						Long.valueOf(request.getParameter("shareMember")));
				session.setAttribute("ecartoonDetail", ret);
				session.setAttribute("shareMember", shareMember.getId());
				session.setAttribute("shareMemberName", shareMember.getName());
				response.sendRedirect("ecartoon-weixin/detail.jsp");
			} else {
				ret.accumulate("success", false).accumulate("message", "查询一卡通数据异常");
				response(ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成请人流汗订单
	 */
	@SuppressWarnings("deprecation")
	public void pleaseSweatOrder() {
		try {
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			Member member = (Member) session.getAttribute("member");
			JSONObject result = new JSONObject();
			// 生成订单
			ProductOrder45 order = new ProductOrder45();
			Date startTime = new Date(param.getString("startTime").replace("-", "/"));
			Product45 prod = (Product45) service.load(Product45.class, Long.valueOf(param.getString("product")));
			order.setProduct(prod);
			order.setOrderDate(startTime);
			order.setOrderBalanceTime(startTime);
			order.setOrderStartTime(startTime);
			// 计算结束时间 往 obj 中添加结束时间
			int periodUnit = (prod.getPeriodUnit() == "A" ? 30
					: prod.getPeriodUnit() == "B" ? 3 * 30 : prod.getPeriodUnit() == "C" ? 12 * 30 : 1);
			long dateTime = (long) (prod.getPeriod() * periodUnit) - 1;
			if (dateTime != 0) {
				Date newDate = addDate(startTime, dateTime);
				order.setOrderEndTime(newDate);
			} else {
				order.setOrderEndTime(startTime);
			}

			// 设置订单号,商品金额,实付金额,来源,状态,所属用户
			String no = "22" + new SimpleDateFormat("yyyyMMdd").format(new Date())
					+ String.valueOf(Math.round(Math.random() * 10000));
			order.setNo(no);
			order.setContractMoney(param.getDouble("unitPrice"));
			order.setOrderMoney((order.getContractMoney() - 5) <= 0 ? 0 : (order.getContractMoney() - 5));
			order.setOrigin('E');
			order.setStatus('0');
			order.setMember(member);
			order = (ProductOrder45) service.saveOrUpdate(order);

			// 设置支付回调时需要使用的参数
			EasyUtils.orderType = "TB_PRODUCT_ORDER_V45";
			EasyUtils.orderNo = order.getNo();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fromMember", member.getId());
			map.put("toMember", param.get("shareMember"));
			map.put("backMoney", EasyUtils.decimalFormat(Math.random()));
			// 需要返现时,在支付回调中使用
			EasyUtils.shareOrder = map;

			// 返回订单信息
			result.accumulate("success", true).accumulate("orderId", order.getId())
					.accumulate("contractMoney", order.getContractMoney())
					.accumulate("orderMoney", order.getOrderMoney()).accumulate("prod_name", prod.getName())
					.accumulate("mobile", member.getMobilephone())
					.accumulate("orderType", param.getString("productType"));

			// 到提交订单页面
			session.setAttribute("shareMember", null);
			session.setAttribute("shareMemberName", null);
			session.setAttribute("payMain", result);
			response.sendRedirect("ecartoon-weixin/submit_order.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成砍价订单
	 */
	public void priceCutDownOrder() {
		try {
			JSONObject param = JSONObject.fromObject(URLDecoder.decode(request.getParameter("json"), "UTF-8"));
			Member member = (Member) session.getAttribute("member");
			JSONObject result = new JSONObject();
			// 生成订单
			ProductOrder45 order = new ProductOrder45();
			Date startTime = new Date();
			Product45 prod = (Product45) service.load(Product45.class, Long.valueOf(param.getString("product")));
			order.setProduct(prod);
			order.setOrderDate(startTime);
			order.setOrderBalanceTime(startTime);
			order.setOrderStartTime(startTime);
			// 计算结束时间 往 obj 中添加结束时间
			int periodUnit = (prod.getPeriodUnit() == "A" ? 30
					: prod.getPeriodUnit() == "B" ? 3 * 30 : prod.getPeriodUnit() == "C" ? 12 * 30 : 1);
			long dateTime = (long) (prod.getPeriod() * periodUnit) - 1;
			if (dateTime != 0) {
				Date newDate = addDate(startTime, dateTime);
				order.setOrderEndTime(newDate);
			} else {
				order.setOrderEndTime(startTime);
			}

			// 设置订单号,商品金额,实付金额,来源,状态,所属用户
			String no = "22" + new SimpleDateFormat("yyyyMMdd").format(new Date())
					+ String.valueOf(Math.round(Math.random() * 10000));
			order.setNo(no);
			order.setContractMoney(prod.getPrice());
			order.setOrderMoney(param.getDouble("price"));
			order.setOrigin('E');
			order.setStatus('0');
			order.setMember(member);
			order = (ProductOrder45) service.saveOrUpdate(order);

			// 设置支付回调时需要使用的参数
			EasyUtils.orderType = "TB_PRODUCT_ORDER_V45";
			EasyUtils.orderNo = order.getNo();

			// 返回订单信息
			result.accumulate("success", true).accumulate("orderId", order.getId())
					.accumulate("contractMoney", order.getContractMoney())
					.accumulate("orderMoney", order.getOrderMoney()).accumulate("prod_name", prod.getName())
					.accumulate("mobile", member.getMobilephone())
					.accumulate("orderType", param.getString("productType"));

			// 到提交订单页面
			session.setAttribute("payMain", result);
			response.sendRedirect("ecartoon-weixin/submit_order.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 时间相加的方法
	public static Date addDate(Date date, long day) {
		long time = date.getTime(); // 得到指定日期的毫秒数
		day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
		time += day; // 相加得到新的毫秒数
		return new Date(time); // 将毫秒数转换成日期
	}

	// 是否有返现提示
	public void isHaveBackMoney() {
		Member member = (Member) session.getAttribute("member");
		// 查询是否还有未读消息
		String querySql = "select * from tb_back_money where status = 0 and to_member = " + member.getId();
		Map<String, Object> resMap = DataBaseConnection.getOne(querySql, null);
		if (resMap.size() > 0) {
			Member fromMember = (Member) service.load(Member.class,
					Long.valueOf(String.valueOf(resMap.get("from_member"))));
			// 修改消息阅读阅读状态
			DataBaseConnection.updateData("update tb_back_money set status = 1 where id = " + resMap.get("id"), null);
			String message = "[" + fromMember.getName()
					+ "]购买了您在'请人流汗'游戏中分享的健身卡,系统发送给您5元现金红包,可以在我的账户->我的钱包的收入中查看还可以在健身E卡通APP中申请提现";
			response(new JSONObject().accumulate("success", true).accumulate("msg", message));
		} else {
			response(new JSONObject().accumulate("success", false));
		}
	}

	/**
	 * 砍价业务逻辑
	 */
	public void priceCutDown() {
		JSONObject ret = new JSONObject();
		try {
			// 首先判断用户是否已经登录
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				request.setAttribute("status", 0);
				request.getRequestDispatcher("ecartoon-weixin/requestOpenId.jsp").forward(request, response);
			}
			// 获取请求参数
			// parentID
			String id = request.getParameter("id");
			// productId
			String product = StringUtils.isEmpty(request.getParameter("product")) ? "1"
					: request.getParameter("product");
			// 商品单价
			String money = StringUtils.isEmpty(request.getParameter("money")) ? "200" : request.getParameter("money");
			// 商品底价
			String lowPrice = StringUtils.isEmpty(request.getParameter("lowPrice")) ? "100"
					: request.getParameter("lowPrice");

			MathRandom4dou random4dou = new MathRandom4dou();

			priceCutDown priceCut = new priceCutDown();
			priceCutDown parent = new priceCutDown();

			// 砍价金额计算
			Double cutMoney = random4dou.getCutMoneyx(0.01, Double.valueOf(money) - Double.valueOf(lowPrice));
			BigDecimal b = new BigDecimal(cutMoney);
			cutMoney = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			Double cutCount = 0.0;

			// 帮人砍价
			if (StringUtils.isNotEmpty(id)) {
				parent = (priceCutDown) service.load(priceCutDown.class, Long.parseLong(id));

				Member parentMember = (Member) service.load(Member.class, parent.getMember());

				// 查询砍价排名数据
				String sortSql = "select m.name,m.image,c.cutMoney from tb_member m,tb_price_cutdown c where m.id = c.member and c.parent = ? order by cutMoney desc";
				Object[] sortObj = { id };
				List<Map<String, Object>> sortList = DataBaseConnection.getList(sortSql, sortObj);
				JSONArray sortDou = new JSONArray();
				JSONObject objDou = new JSONObject();
				if (sortList.size() > 0) {
					for (Map<String, Object> map : sortList) {
						objDou = new JSONObject();
						objDou.accumulate("nick", map.get("name")).accumulate("imgSrc", "picture/" + map.get("image"))
								.accumulate("money", map.get("cutMoney"));
						sortDou.add(objDou);
					}

				}

				// 当前已砍价格
				String sqlCut = "select sum(cutMoney) cutMoney from tb_price_cutdown where parent = ?";
				Object[] objCut = { StringUtils.isNotEmpty(id) ? parent.getId() : 0 };
				Map<String, Object> mapCut = DataBaseConnection.getOne(sqlCut, objCut);
				Double parentCut = StringUtils.isNotEmpty(id) ? parent.getCutMoney() : 0;
				Double cutdd = Double
						.valueOf(String.valueOf(mapCut.get("cutMoney") == null ? 0 : mapCut.get("cutMoney")));

				// 判断当前用户是否有已经参加了有效的砍价活动
				String hasSql = "select * from tb_price_cutdown where member = ? and status=0";
				Object[] hasObj = { member.getId() };
				List<Map<String, Object>> hasList = DataBaseConnection.getList(hasSql, hasObj);

				if (hasList.size() > 0) {
					Map<String, Object> map = hasList.get(0);
					ret.accumulate("success", false).accumulate("message", "您已经参加过该砍价活动，请购买产品或，发起新的砍价活动")
							.accumulate("cutMoney", map.get("cutMoney")).accumulate("cutCount", parentCut + cutdd)
							.accumulate("currentPrice",
									Double.valueOf(String.valueOf(map.get("money"))) - (parentCut + cutdd))
							.accumulate("lowPrice", lowPrice).accumulate("money", money).accumulate("cutActive", false)
							.accumulate("sortList", sortDou).accumulate("already", true);
				} else {

					// 砍价未结束
					if ("0".equals(parent.getStatus())) {
						if ((cutdd + parentCut + parent.getLowPrice() + cutMoney) > parent.getMoney()) {
							// 目前已经砍到底价了
							cutMoney = parent.getMoney() - (cutdd + parentCut + parent.getLowPrice());
							parent.setStatus("1");
						}
						cutCount = cutdd + parentCut + cutMoney;
						priceCut.setParent(parent);
						priceCut.setMoney(Double.valueOf(money == null ? "0" : money));
						priceCut.setMember(member.getId());
						priceCut.setProduct(Long.parseLong(product));
						priceCut.setTime(new Date());
						priceCut.setCutMoney(cutMoney);
						priceCut = (priceCutDown) service.saveOrUpdate(priceCut);
						ret.accumulate("success", true)
								.accumulate("message",
										"您已经帮[" + parentMember.getName() + "]砍了" + priceCut.getCutMoney() + "元，谢谢您的参与！")
								.accumulate("id", priceCut.getId())
								// 本次砍价
								.accumulate("cutMoney", cutMoney)
								// 累计砍价
								.accumulate("cutCount", cutCount)
								// 当前价格
								.accumulate("currentPrice", priceCut.getMoney() - cutCount)
								// 商品单价
								.accumulate("money", money)
								// 商品底价
								.accumulate("lowPrice", lowPrice)
								// 当前用户是否是该砍价活动的发起者
								.accumulate("cutActive", false)
								// 砍价排名
								.accumulate("sortList", sortDou)
								.accumulate("already", false);
					} else {
						     ret.accumulate("success", false)
						        .accumulate("message", "该商品已购买，或砍价已到最低价不能再砍价啦！")
								.accumulate("id", id).accumulate("cutMoney", 0)
								.accumulate("cutCount", Double.valueOf(money) - Double.valueOf(lowPrice))
								.accumulate("currentPrice", lowPrice).accumulate("money", money)
								.accumulate("lowPrice", lowPrice).accumulate("cutActive", false)
								.accumulate("sortList", sortDou).accumulate("already", true);
					}
				}

			} else {

				// 判断当前用户是否有已经参加了有效的砍价活动
				String hasSql = "select * from tb_price_cutdown where member = ? and status=0";
				Object[] hasObj = { member.getId() };
				List<Map<String, Object>> hasList = DataBaseConnection.getList(hasSql, hasObj);
				if (hasList.size() > 0) {
					// 当前用户已经参加了该活动，提示不能参加该活动了
					Map<String, Object> map = hasList.get(0);
					// 砍价排名
					String sortSql = " select temp.* from ("
							+ " select m.name,m.image,c.cutMoney from tb_member m,tb_price_cutdown c where m.id = c.member and c.parent= ? "
							+ " union all "
							+ " select m.name,m.image,c.cutMoney from tb_member m,tb_price_cutdown c where m.id = c.member and c.member = ? and ISNULL(parent)"
							+ " ) temp " + " order by temp.cutMoney desc";
					Object[] sortObj = { map.get("parent") == null ? 0 : map.get("parent"), member.getId() };
					List<Map<String, Object>> sortList = DataBaseConnection.getList(sortSql, sortObj);
					JSONArray sortDou = new JSONArray();
					JSONObject sortddd = new JSONObject();
					for (Map<String, Object> sortMap : sortList) {
						sortddd = new JSONObject();
						sortddd.accumulate("nick", sortMap.get("name"))
								.accumulate("imgSrc", "picture/" + sortMap.get("image"))
								.accumulate("money", sortMap.get("cutMoney"));
						sortDou.add(sortddd);
					}
					ret.accumulate("success", false).accumulate("message", "您已经参加过该砍价活动，请购买产品或，发起新的砍价活动")
							// 累计砍价
							.accumulate("cutCount", map.get("cutMoney"))
							// 当前价格
							.accumulate("currentPrice",
									Double.valueOf(String.valueOf(map.get("money")))
											- Double.valueOf(String.valueOf(map.get("cutMoney"))))
							// 商品单价
							.accumulate("money", money)
							// 商品底价
							.accumulate("lowPrice", lowPrice)
							// 当前用户是否是该砍价活动的发起者
							.accumulate("cutActive", true).accumulate("sortList", sortDou).accumulate("already", true);

				} else {

					// 用户第一次生成砍价数据
					while ((Double.valueOf(lowPrice) + cutMoney) > Double.valueOf(money)) {
						// 超出底价范围
						cutMoney = random4dou.getCutMoneyx(0.01, Double.valueOf(money) - Double.valueOf(lowPrice));
						continue;
					}

					priceCut.setMoney(Double.valueOf(money == null ? "0" : money));
					priceCut.setMember(member.getId());
					priceCut.setProduct(Long.parseLong(product));
					priceCut.setStatus("0");
					priceCut.setTime(new Date());
					priceCut.setCutMoney(cutMoney);
					priceCut.setLowPrice(Double.valueOf(lowPrice));
					priceCut = (priceCutDown) service.saveOrUpdate(priceCut);

					cutCount = priceCut.getCutMoney();
					ret.accumulate("success", true).accumulate("message", "OK").accumulate("id", priceCut.getId())
							// 当前砍价人
							.accumulate("member", priceCut.getMember())
							// 本次砍价
							.accumulate("cutMoney", priceCut.getCutMoney())
							// 累计砍价
							.accumulate("cutCount", cutCount)
							// 当前价格
							.accumulate("currentPrice", priceCut.getMoney() - cutCount)
							// 商品单价
							.accumulate("money", money)
							// 商品底价
							.accumulate("lowPrice", lowPrice)
							// 当前用户是否是该砍价活动的发起者
							.accumulate("cutActive", true).accumulate("already", false);

				}
			}
			// 将结果存在session中
			session.setAttribute("cutInfo", ret);
			response.sendRedirect("ecartoon-weixin/tGame/priceCutDown.jsp");
		} catch (Exception e) {
			ret.accumulate("success", false).accumulate("message", "砍价发生异常");
			e.printStackTrace();
		}

	}

}
