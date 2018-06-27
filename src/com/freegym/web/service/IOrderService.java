package com.freegym.web.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.freegym.web.active.Active;
import com.freegym.web.active.Team;
import com.freegym.web.basic.Member;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.course.SignIn;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.Complaint;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.Goods;
import com.freegym.web.order.Order;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.Shop;
import com.freegym.web.order.ShopGoods;
import com.freegym.web.order.ShopPlan;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;

public interface IOrderService extends ICourseService {

	public void saveGoodsOrder(Goods goods, Date startDate, Member member, String payNo, String price);
		
	public CourseOrder saveCourseOrder(Course course, Date startDate, Member member, String payNo, String price);

	public void savePlanOrder(PlanRelease plan, Date startDate, Member member, String payNo, String price);

	public FactoryOrder saveFactoryOrder(FactoryCosts factoryCosts, Date startDate, Member member, String payNo,
			String price, Date factoryStartTime, Date factoryEndTime);

	public void saveProductOrder(Product product, Date startDate, Member member, String payNo, String price);

	public void saveActiveOrder(Active active, Date startDate, Member member, String payNo, String price,
			final Long teamId, final Character createMode, final String name, final String members, String judge);

	public void deletePlanShop(ShopPlan shopPlan);

	public void deleteGoodsShop(ShopGoods shopGoods);

	public void deleteProductShop(Shop Shop);

	public void updateOrderPayType(String payNo, String payType);

	/**
	 * 增加优惠券功能
	 * 
	 * @param payNo
	 * @param payType
	 * @param ticketId
	 */
	public void updateOrderPayType(String payNo, String payType, Double ticketPrice);

	public Long[] saveShopToOrder(final List<Shop> shopList, final List<ProductOrder> orderList);

	public Long[] saveShopPlanToOrder(final List<ShopPlan> shopPlanList, final List<PlanOrder> planOrderList);

	/**
	 * 查找订单，如果有订单ID，则根据订单ID查询订单，否则，根据当前用户查找订单
	 * 
	 * @param queryOrder
	 *            ，订单
	 * @param queryMember
	 *            ，会员
	 * @param member
	 *            ，登录用户
	 */
	public List<ProductOrder> findCurrentOrder(final ProductOrder queryOrder, final Member queryMember,
			final Member member);

	/**
	 * 保存订单和交易明细
	 * 
	 * @param orderNo
	 *            ，父订单编号
	 * @return 订单list、会员
	 */
	// public List<ProductOrder> saveOrderByOrderNo(final String orderNo);

	/**
	 * 查询会员可用提现金额
	 * 
	 * @param member
	 *            ，会员
	 * @return 返回金额数值
	 */
	public Double findPickMoneyCountByMember(final Member member);

	/**
	 * 保存投诉和修改订单状态为中止
	 * 
	 * @param complaint
	 *            ，投诉对象
	 */
	public void saveOrUpdateComplaint(final Complaint complaint);

	/**
	 * 订单结算
	 */
	public void saveBalanceOrder();

	/**
	 * 保存订单结算数据
	 * 
	 * @param order
	 *            ，订单对象
	 * @param orderDetailList
	 *            ，交易明细列表
	 */
	public void saveBalance(final Order order, final List<OrderDetail> orderDetailList);

	/**
	 * 保存签到记录
	 * 
	 * @param signIn
	 *            ，签到对象
	 */
	public void saveOrUpdateSignIn(final SignIn signIn);

	/**
	 * 查找可以签到的订单
	 * 
	 * @param memberSignInId
	 *            ，签到方ID
	 * @param memberAuditId
	 *            ，签到审核方ID
	 */
	public List<Map<String, Object>> findSignOrder(final Long memberSignInId, final String memberId);
	
	/**
	 * 
	 * @param memberSignInId
	 * @param memberId
	 * @param ss
	 * @return
	 */
	public List<ProductOrder> findSignOrder(final Long memberSignInId, final String memberId, int ss);

	/**
	 * 查找可以签到的订单
	 * 
	 * @param memberSignInId
	 *            ，签到方ID
	 * @param memberAuditId
	 *            ，签到审核方ID
	 */
	public List<Map<String, Object>> findSignOrder(final Long memberSignInId, final String memberId, String wx);

	/**
	 * 依据订单号查找挑战合同
	 * 
	 * @param orderno
	 * @return
	 */
	public ActiveOrder findActiveOrderByNo(String orderno);

	/**
	 * 依据付款的情况更新订单状态及参与者的状态
	 * 
	 * @param orderno
	 * @param payMoney
	 * @param paidMoney
	 * @param needPayMoney
	 */
	// public ActiveOrder saveActiveOrder(String orderno);

	public void loadOrderObject(Complaint c);

	/**
	 * 查找当前用户所在的团队
	 * 
	 * @param id
	 * @return
	 */
	public List<Team> findTeamByMember(Long id);

	/**
	 * 生成计划数据
	 * 
	 * @param order
	 * @param pr
	 */
	public void genPlanData(Order order, PlanRelease pr);

	/**
	 * 更新订单状态
	 * 
	 * @param orderno
	 * @param string
	 * @return
	 */
	public Order updateOrderStatus(String orderno, String tradeNo) throws Exception;

	
	/**
	 * 更新订单状态
	 * 
	 * @param orderno
	 * @param string
	 * @return
	 */
	public Order updateOrderStatus(String orderno, String tradeNo, String flag) throws Exception;
	
	
	/**
	 * 0元购买  更新订单状态
	 * 
	 * @param orderno
	 * @param string
	 * @return
	 */
	public Order updateOrderStatus4z(String orderno, String tradeNo, String flag) throws Exception;

	
	
	
	
	/**
	 * 自动订单更新订单状态
	 * 
	 * @param orderno
	 * @param string
	 * @return
	 */
	public Order updateOrderStatuse(String orderno) throws Exception;
	
	public void updateDou(Order order) throws Exception;

	/**
	 * 查询参加挑战的用户
	 * 
	 * @return
	 */
	public Object findActivePartakeMembers(Active active);

	public void ordersms(Order order, String orderType);

}
