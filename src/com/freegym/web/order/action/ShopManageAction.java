package com.freegym.web.order.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.OrderBasicAction;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Project;
import com.freegym.web.dto.ShopListDto;
import com.freegym.web.order.BasicShop;
import com.freegym.web.order.Goods;
import com.freegym.web.order.Product;
import com.freegym.web.order.Shop;
import com.freegym.web.order.ShopGoods;
import com.freegym.web.order.ShopPlan;
import com.freegym.web.plan.PlanRelease;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "shop_edit", location = "/homeWindow/product.jsp"), @Result(name = "shop_edit1", location = "/shop/shop_edit1.jsp"),
		@Result(name = "shop_edit2", location = "/shop/shop_edit2.jsp"), @Result(name = "shop_edit3", location = "/shop/shop_edit3.jsp"),
		@Result(name = "shop_edit4", location = "/shop/shop_edit4.jsp"), @Result(name = "shop_edit5", location = "/shop/shop_edit5.jsp"),
		@Result(name = "shop_edit6", location = "/shop/shop_edit6.jsp"), @Result(name = "query_shop", location = "shop!queryShopping.asp", type = "redirect"),
		@Result(name = "shopping_list", location = "/shop/shopping_list.jsp"), @Result(name = "shopping_audit", location = "/shop/shopping_audit.jsp"),
		@Result(name = "selected", location = "/home/register3.jsp") })
public class ShopManageAction extends OrderBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private Shop shop;

	private Shop query;

	private Product product;

	private List<Shop> shopList = new ArrayList<Shop>();

	public String infoStr;// 订单号，数量、订单金额、积分

	private Member member;

	private String goType;// goType == 1,订单里面进入查看

	private List<ShopPlan> shopPlanList;

	private ShopPlan shopPlan;

	private PlanRelease planRelease;

	private Goods goods;

	private ShopGoods shopGoods;

	private List<ShopGoods> shopGoodsList;

	private List<ShopListDto> shopDtos;

	public PlanRelease getPlanRelease() {
		return planRelease;
	}

	public void setPlanRelease(PlanRelease planRelease) {
		this.planRelease = planRelease;
	}

	public ShopPlan getShopPlan() {
		return shopPlan;
	}

	public void setShopPlan(ShopPlan shopPlan) {
		this.shopPlan = shopPlan;
	}

	public List<ShopPlan> getShopPlanList() {
		return shopPlanList;
	}

	public void setShopPlanList(List<ShopPlan> shopPlanList) {
		this.shopPlanList = shopPlanList;
	}

	public Shop getShop() {
		return shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}

	public Shop getQuery() {
		return query;
	}

	public void setQuery(Shop query) {
		this.query = query;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Shop> getShopList() {
		return shopList;
	}

	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}

	public String getInfoStr() {
		return infoStr;
	}

	public void setInfoStr(String infoStr) {
		this.infoStr = infoStr;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getGoType() {
		return goType;
	}

	public void setGoType(String goType) {
		this.goType = goType;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public ShopGoods getShopGoods() {
		return shopGoods;
	}

	public void setShopGoods(ShopGoods shopGoods) {
		this.shopGoods = shopGoods;
	}

	public List<ShopGoods> getShopGoodsList() {
		return shopGoodsList;
	}

	public void setShopGoodsList(List<ShopGoods> shopGoodsList) {
		this.shopGoodsList = shopGoodsList;
	}

	public List<ShopListDto> getShopDtos() {
		return shopDtos;
	}

	public void setShopDtos(List<ShopListDto> shopDtos) {
		this.shopDtos = shopDtos;
	}

	public String save() {
		if (shop != null) {
			product = (Product) service.load(Product.class, product.getId());
			shop.setMember(toMember());
			shop.setProduct(product);
			shop.setCount(1);
			if (product.getProType() != null && (product.getProType().equals("1") || product.getProType().equals("5"))) {
				shop.setUnitPrice(product.getPromiseCost());
			} else {
				shop.setUnitPrice(product.getCost());
			}
			Double orderMoney = shop.getCount() * shop.getUnitPrice();
			shop.setOrderMoney(orderMoney);
			shop.setIntegral((int) (orderMoney / 10));
			service.saveOrUpdate(shop);
		}
		return "query_shop";
	}

	/*
	 * 保存健身计划到健身计划购物车
	 */
	public void saveShopPlan() {
		try {
			planRelease = (PlanRelease) service.load(PlanRelease.class, planRelease.getId());
			shopPlan = new ShopPlan();
			shopPlan.setMember(toMember());
			shopPlan.setPlanRelease(planRelease);
			shopPlan.setCount(1);
			shopPlan.setUnitPrice(planRelease.getUnitPrice());
			Double orderMoney = shopPlan.getCount() * shopPlan.getUnitPrice();
			shopPlan.setOrderMoney(orderMoney);
			shopPlan.setIntegral((int) (orderMoney / 10));
			service.saveOrUpdate(shopPlan);

			response("ok");
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	/*
	 * 保存产品到购物车
	 */

	public void saveShopProduct() {
		try {
			product = (Product) service.load(Product.class, product.getId());
			shop.setMember(toMember());
			shop.setProduct(product);
			shop.setCount(1);
			if (product.getProType() != null && (product.getProType().equals("1") || product.getProType().equals("5"))) {
				shop.setUnitPrice(product.getPromiseCost());
			} else {
				shop.setUnitPrice(product.getCost());
			}
			Double orderMoney = shop.getCount() * shop.getUnitPrice();
			shop.setOrderMoney(orderMoney);
			shop.setIntegral((int) (orderMoney / 10));
			service.saveOrUpdate(shop);

			response("ok");
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	public void saveShopGoods() {
		try {
			goods = (Goods) service.load(Goods.class, goods.getId());
			shopGoods = new ShopGoods();
			shopGoods.setMember(toMember());
			shopGoods.setGoods(goods);
			shopGoods.setCount(1);
			shopGoods.setUnitPrice(goods.getPrice());
			Double orderMoney = shopGoods.getCount() * shopGoods.getUnitPrice();
			shopGoods.setOrderMoney(orderMoney);
			shopGoods.setIntegral((int) (orderMoney / 10));
			service.saveOrUpdate(shopGoods);
			response("ok");
		} catch (Exception e) {
			e.printStackTrace();
			response(e);
		}
	}

	// 1.我的购物车
	public String queryShopping() {
		final String role = toMember().getRole();
		if (role == null || "".equals(role))
			return "selected";
		
		String memberId = toMember().getId().toString();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (" + BasicShop.getShopSql() + ") t where t.member_id =?");
		List<Map<String, Object>> list = service.queryForList(sb.toString(), memberId);
		request.setAttribute("shops", list);

		member = this.toMember();
		if (member.getMobilephone() != null && member.getMobilephone().equals("") == false)
			request.setAttribute("mobilephone", member.getMobilephone().replaceAll(member.getMobilephone().substring(3, 7), "*****"));

		return "shopping_list";
	}

	@SuppressWarnings("rawtypes")
	public String saveShopping() {
		if (shopList != null && shopList.size() > 0) {
			for (Iterator it = shopList.iterator(); it.hasNext();) {
				if (it.next() == null) {
					it.remove();
				}
			}
			service.saveOrUpdate(shopList);
		}
		return queryAudit();
	}

	// 2.填写并核对订单信息
	@SuppressWarnings("unchecked")
	public String queryAudit() {
		member = (Member) service.load(Member.class, this.toMember().getId());
		shopList = (List<Shop>) service.findObjectBySql(" from Shop s where s.member.id=? ", toMember().getId());
		return "shopping_audit";
	}

	public String deleteProduct() {
		String str[] = request.getParameter("idType").split(",");
		id = Long.valueOf(str[0]);
		String type = str[1];
		if ("1".equals(type)) {
			service.deleteProductShop((Shop) service.load(Shop.class, id));
		} else if ("3".equals(type)) {
			service.deletePlanShop((ShopPlan) service.load(ShopPlan.class, id));
		} else if ("6".equals(type)) {
			service.deleteGoodsShop((ShopGoods) service.load(ShopGoods.class, id));
		}
		return queryShopping();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String preShop() {
		shop = new Shop();
		shop.setMember(toMember());
		shop.setOrderStartTime(new Date());
		if (product != null && product.getId() != null) {
			product = (Product) service.load(Product.class, product.getId());
			List<Project> projectList = (List<Project>) service.findObjectBySql("from CourseInfo c where c.member.id = ? ", product.getMember().getId());
			Project p = new Project();
			p.setId(null);
			p.setName("其它：");
			projectList.add(p);
			request.setAttribute("projectList", projectList);
			List<Friend> friendList = (List<Friend>) service.findObjectBySql(
					"from Friend f where f.member.id = ? and f.member.role = ? and f.friend.role = ? and f.type = ? ", new Object[] {
							product.getMember().getId(), "E", "E", "1" });
			List<Member> clubList = new ArrayList();
			for (Friend f : friendList) {
				clubList.add(f.getFriend());
			}
			request.setAttribute("clubList", clubList);
		}
		session.setAttribute("toMember", product.getMember());
		session.setAttribute("wpath", 4);
		if (goType != null && goType.equals("1")) {
			return "shop_edit" + product.getProType();
		}
		return "shop_edit";
	}

	public String checkShop() {
		/*
		 * if (product != null && product.getId() != null) { product = (Product)
		 * service.load(Product.class, product.getId()); //
		 * 判断购物车中是否已添加该套装或课程收费，且购物车还未完成 List sList = service.findObjectBySql(
		 * "from Shop s where s.member.id = ? and s.product.member.id = ? and ( s.product.type = ? or s.product.type = ? ) and s.orderEndTime > ? "
		 * , new Object[] { this.toMember().getId(),
		 * product.getMember().getId(), "1", "3", shop.getOrderStartTime() });
		 * if (sList != null && sList.size() > 0) { response("isShop"); // Shop
		 * s= (Shop) sList.get(0); //
		 * if(shop.getOrderStartTime().before(s.getOrderEndTime())){ //
		 * response("isShop"); } // return null; } // 判断订单中是否已添加该套装或课程收费，且订单还未完成
		 * List oList = service .findObjectBySql(
		 * "from Order o where o.member.id = ? and o.product.member.id = ? and o.status <> ?  and ( o.product.type = ? or o.product.type = ? ) and o.orderEndTime > ? "
		 * , new Object[] { this.toMember().getId(),
		 * product.getMember().getId(), "2", "1", "3", shop.getOrderStartTime()
		 * }); if (oList != null && oList.size() > 0) { response("isOrder"); //
		 * Order o= (Order) oList.get(0); //
		 * if(shop.getOrderStartTime().before(o.getOrderEndTime())){ //
		 * response("isOrder"); } // return null; } }
		 */
		response("ok");
		return null;
	}
}
