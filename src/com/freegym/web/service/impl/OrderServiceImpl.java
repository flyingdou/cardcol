package com.freegym.web.service.impl;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.aliyn.api.geteway.util.SingleSendSms;
import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.cardcol.web.balance.IBalance;
import com.cardcol.web.order.ProductOrder45;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.active.Active;
import com.freegym.web.active.Team;
import com.freegym.web.active.TeamMember;
import com.freegym.web.alipay.util.UtilDate;
import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.basic.MemberTicket;
import com.freegym.web.common.Constants;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.course.SignIn;
import com.freegym.web.order.ActiveOrder;
import com.freegym.web.order.ActiveOrderDetail;
import com.freegym.web.order.Complaint;
import com.freegym.web.order.CourseOrder;
import com.freegym.web.order.CourseOrderDetail;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.order.FactoryOrderDetail;
import com.freegym.web.order.Goods;
import com.freegym.web.order.GoodsOrder;
import com.freegym.web.order.Order;
import com.freegym.web.order.OrderDetail;
import com.freegym.web.order.PlanOrder;
import com.freegym.web.order.PlanOrderDetail;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductOrder;
import com.freegym.web.order.ProductOrderDetail;
import com.freegym.web.order.Shop;
import com.freegym.web.order.ShopGoods;
import com.freegym.web.order.ShopPlan;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.PlanRelease;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.freegym.web.plan.gen.IPlanGenerator;
import com.freegym.web.service.IBasicService;
import com.freegym.web.service.IOrderService;
import com.freegym.web.system.Ticket;
import com.freegym.web.utils.DateUtils;
import com.freegym.web.utils.EasyUtils;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.system.Parameter;
import com.sanmen.web.core.utils.StringUtils;

import net.sf.json.JSONObject;

@Service("orderService")
public class OrderServiceImpl extends CourseServiceImpl implements IOrderService, Constants, Constantsms {

	@Autowired
	@Qualifier("courseBalanceImpl")
	IBalance courseBalanceImpl;

	@Autowired
	@Qualifier("goodsBalanceImpl")
	IBalance goodsBalanceImpl;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveGoodsOrder(Goods goods, Date startDate, Member member, String payNo, String price) {
		GoodsOrder goodsOrder = new GoodsOrder();
		goodsOrder.setGoods(goods);
		goodsOrder.setOrderStartTime(startDate);
		goodsOrder.setMember(member);
		goodsOrder.setNo(getKeyNo(TB_GOODS_ORDER, "TB_GOODS_ORDER", 14));
		goodsOrder.setPayNo(payNo);
		goodsOrder.setOrderDate(new Date());
		goodsOrder.setStatus("0".charAt(0));
		goodsOrder.setOrderMoney((new BigDecimal(price)).doubleValue());
		getHibernateTemplate().merge(goodsOrder);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public CourseOrder saveCourseOrder(Course course, Date startDate, Member member, String payNo, String price) {
		CourseOrder courseOrder = new CourseOrder();
		courseOrder.setCourse(course);
		courseOrder.setOrderStartTime(startDate);
		courseOrder.setMember(member);
		courseOrder.setNo(getKeyNo(TB_COURSE_ORDER, "TB_COURSE_ORDER", 14));
		courseOrder.setPayNo(payNo);
		courseOrder.setOrderDate(new Date());
		courseOrder.setStatus("0".charAt(0));
		courseOrder.setOrderMoney((new BigDecimal(price)).doubleValue());
		return getHibernateTemplate().merge(courseOrder);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void savePlanOrder(PlanRelease plan, Date startDate, Member member, String payNo, String price) {
		PlanOrder planOrder = new PlanOrder();
		planOrder.setPlanRelease(plan);
		planOrder.setOrderStartTime(startDate);
		planOrder.setMember(member);
		planOrder.setNo(getKeyNo(TB_PLAN_ORDER, "TB_PLAN_ORDER", 14));
		planOrder.setPayNo(payNo);
		planOrder.setOrderDate(new Date());
		planOrder.setStatus("0".charAt(0));
		planOrder.setOrderMoney((new BigDecimal(price)).doubleValue());
		getHibernateTemplate().merge(planOrder);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public FactoryOrder saveFactoryOrder(FactoryCosts factoryCosts, Date startDate, Member member, String payNo,
			String price, Date factoryStartTime, Date factoryEndTime) {
		FactoryOrder factoryOrder = new FactoryOrder();
		factoryOrder.setFactoryCosts(factoryCosts);
		factoryOrder.setOrderStartTime(factoryStartTime);
		factoryOrder.setOrderEndTime(factoryEndTime);
		factoryOrder.setMember(member);
		factoryOrder.setNo(getKeyNo(TB_FACTORY_ORDER, "TB_FACTORY_ORDER", 14));
		factoryOrder.setPayNo(payNo);
		factoryOrder.setOrderDate(new Date());
		factoryOrder.setStatus("0".charAt(0));
		factoryOrder.setOrderMoney((new BigDecimal(price)).doubleValue());
		return getHibernateTemplate().merge(factoryOrder);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveProductOrder(Product product, Date startDate, Member member, String payNo, String price) {
		ProductOrder productOrder = new ProductOrder();
		productOrder.setProduct(product);
		productOrder.setOrderStartTime(startDate);
		productOrder.setMember(member);
		productOrder.setNo(getKeyNo(CARDCOL_ORDER_NO, "CARDCOL_ORDER_NO", 14));
		productOrder.setPayNo(payNo);
		productOrder.setOrderDate(new Date());
		productOrder.setStatus("0".charAt(0));
		productOrder.setOrderMoney((new BigDecimal(price)).doubleValue());
		getHibernateTemplate().merge(productOrder);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveActiveOrder(Active active, Date startDate, Member member, String payNo, String price,
			final Long teamId, final Character createMode, final String name, final String members,
			final String judge) {
		ActiveOrder activeOrder = new ActiveOrder();
		activeOrder.setActive(active);
		activeOrder.setOrderStartTime(startDate);
		activeOrder.setMember(member);
		activeOrder.setNo(getKeyNo(TB_ACTIVE_ORDER, "TB_ACTIVE_ORDER", 14));
		activeOrder.setPayNo(payNo);
		activeOrder.setOrderDate(new Date());
		activeOrder.setStatus("0".charAt(0));
		activeOrder.setJudge(judge);
		activeOrder.setOrderMoney((new BigDecimal(price)).doubleValue());
		activeOrder = getHibernateTemplate().merge(activeOrder);
		if (activeOrder.getActive().getMode() == 'B') {
			Team t = new Team();
			if (createMode == '0') { // 加入团队
				t.setId(teamId);
				// 当前用户加入到团队列表中
				final TeamMember tm = new TeamMember();
				tm.setMember(activeOrder.getMember());
				tm.setTeam(new Team(teamId));
				getHibernateTemplate().merge(tm);
			} else { // 新建团队
				t.setName(name);
				if (members != null && !"".equals(members)) {
					final String[] arrs = members.split(",");
					for (final String arr : arrs) {
						final String mName = arr.trim();
						final List<?> list1 = getHibernateTemplate().find(
								"from Member m where m.nick = ? or m.mobilephone = ? or m.email = ?", mName, mName,
								mName);
						if (list1.size() > 0) {
							final Member m1 = (Member) list1.get(0);
							t.addMember(new TeamMember(m1));
						} else {
							throw new LogicException("会员" + arr + "不存在本系统中，请确认！");
						}
					}
				}
				sendNotity(t.getMembers(), "会员【" + member.getName() + "】邀请您参加【" + active.getName() + "】团体挑战");
				t.addMember(new TeamMember(activeOrder.getMember()));
				t = getHibernateTemplate().merge(t);
			}
			activeOrder.setTeam(t);
			activeOrder = getHibernateTemplate().merge(activeOrder);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deletePlanShop(ShopPlan shopPlan) {
		getHibernateTemplate().delete(shopPlan);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteGoodsShop(ShopGoods shopGoods) {
		getHibernateTemplate().delete(shopGoods);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteProductShop(Shop Shop) {
		getHibernateTemplate().delete(Shop);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Long[] saveShopToOrder(List<Shop> shopList, List<ProductOrder> orderList) {
		final Long[] ids = new Long[orderList.size()];
		int i = 0;
		for (ProductOrder o : orderList) {
			o = getHibernateTemplate().merge(o);
			ids[i++] = o.getId();
		}
		getHibernateTemplate().deleteAll(shopList);

		return ids;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateOrderPayType(String payNo, String payType) {
		String[] sql = { "update tb_goods_Order set payType = " + payType + " where payNo=" + payNo,
				"update TB_PlanRelease_ORDER set payType = " + payType + " where payNo=" + payNo,
				"update tb_product_Order set payType = " + payType + " where payNo=" + payNo,
				"update tb_active_Order set payType = " + payType + " where payNo=" + payNo,
				"update TB_CourseRelease_ORDER set payType = " + payType + " where payNo=" + payNo,
				"update tb_factory_Order set payType = " + payType + " where payNo=" + payNo };
		jdbc.batchUpdate(sql);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Long[] saveShopPlanToOrder(List<ShopPlan> shopPlanList, final List<PlanOrder> planOrderList) {

		final Long[] planids = new Long[planOrderList.size()];
		int i = 0;
		for (PlanOrder o : planOrderList) {
			o = getHibernateTemplate().merge(o);
			planids[i++] = o.getId();
		}
		// getHibernateTemplate().delete(shopPlanList);
		return planids;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<ProductOrder> findCurrentOrder(ProductOrder queryOrder, Member queryMember, Member member) {
		List<ProductOrder> orderList = new ArrayList<ProductOrder>();
		if (queryOrder != null && queryOrder.getNo() != null && !"".equals(queryOrder.getNo())) {
			orderList = (List<ProductOrder>) getHibernateTemplate().find(" from ProductOrder o where o.no = ? ",
					queryOrder.getNo());
		} else {
			if (queryMember != null && queryMember.getNick() != null && !"".equals(queryMember.getNick())) {
				Date[] dateArr = DateUtils.getStartAndEndDate(new Date());
				if (member.getRole().equals("M")) {
					orderList = (List<ProductOrder>) getHibernateTemplate().find(
							" from ProductOrder o where o.product.member.nick like ? and o.member.id = ? ",
							new Object[] { "%" + queryMember.getNick() + "%", member.getId() });
				} else if (member.getRole().equals("E")) {
					orderList = (List<ProductOrder>) getHibernateTemplate().find(
							" from ProductOrder o where o.product.member.id = ? and o.member.nick like ? ",
							new Object[] { member.getId(), "%" + queryMember.getNick() + "%" });
				} else if (member.getRole().equals("S")) {
					orderList = (List<ProductOrder>) getHibernateTemplate().find(
							" from ProductOrder o where o.product.member.id = ? and o.member.nick like ? ",
							new Object[] { member.getId(), "%" + queryMember.getNick() + "%" });
				}
			}
		}
		return orderList;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Double findPickMoneyCountByMember(Member member) {
		Double pickMoneyCount = 0d;
		Double detailInMoney = 0d;
		Double detailOutMoney = 0d;
		/*
		 * List<ProductOrderDetail> orderDetailList = (List<ProductOrderDetail>)
		 * getHibernateTemplate() .find(
		 * "from ProductOrderDetail od where od.transUser.id = ?", new Object[] {
		 * member.getId() }); for (ProductOrderDetail orderDetail : orderDetailList) {
		 * if (orderDetail.getTransUser().getId().equals(member.getId())){
		 * detailOutMoney = detailOutMoney + orderDetail.getDetailMoney(); } else {
		 * detailInMoney = detailInMoney + orderDetail.getDetailMoney(); } }
		 */
		Map<String, Object> map1 = this.queryForMap(
				"SELECT SUM(BALANCE_MONEY) as money from tb_order_balance_v45 where BALANCE_TO =?", member.getId());
		Map<String, Object> map2 = this.queryForMap(
				"SELECT SUM(pickMoney) as money  from tb_pick_detail where member=? and status in (1,2) ",
				member.getId());
		System.out.println(map2.get("money"));
		if (map1.get("money") == null) {
			detailInMoney = 0.00;
		} else {
			detailInMoney = (Double) map1.get("money");
		}
		if (map2.get("money") == null) {
			detailOutMoney = 0.00;
		} else {
			detailOutMoney = (Double) map2.get("money");
		}
		pickMoneyCount = (detailInMoney - detailOutMoney) < 0.00 ? 0.00
				: BigDecimal.valueOf((detailInMoney - detailOutMoney)).setScale(2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
		// pickMoneyCount = detailInMoney - detailOutMoney;
		return pickMoneyCount;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveOrUpdateComplaint(Complaint complaint) {
		if (complaint.getType() == '1') {
			final ProductOrder order = getHibernateTemplate().load(ProductOrder.class, complaint.getOrderId());
			order.setStatus('3');
			getHibernateTemplate().merge(order);
		} else if (complaint.getType() == '2') {
			final ActiveOrder ao = getHibernateTemplate().load(ActiveOrder.class, complaint.getOrderId());
			ao.setStatus('3');
			getHibernateTemplate().merge(ao);
		} else if (complaint.getType() == '3') {
			final PlanOrder order = getHibernateTemplate().load(PlanOrder.class, complaint.getOrderId());
			order.setStatus('3');
			getHibernateTemplate().merge(order);
		} else if (complaint.getType() == '4') {
			final FactoryOrder order = getHibernateTemplate().load(FactoryOrder.class, complaint.getOrderId());
			order.setStatus('3');
			getHibernateTemplate().merge(order);
		} else if (complaint.getType() == '5') {
			final CourseOrder order = getHibernateTemplate().load(CourseOrder.class, complaint.getOrderId());
			order.setStatus('3');
			getHibernateTemplate().merge(order);
		} else if (complaint.getType() == '6') {
			final GoodsOrder order = getHibernateTemplate().load(GoodsOrder.class, complaint.getOrderId());
			order.setStatus('3');
			getHibernateTemplate().merge(order);
		}
		getHibernateTemplate().saveOrUpdate(complaint);
	}

	// 交易类型1：预付款2：保证金3：违约金4：缺勤费用5：训练费用6：交易服务费7:交易手续费8：超勤费用9:退款10:提现
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveBalanceOrder() {
		final List<ProductOrder> orderList = (List<ProductOrder>) getHibernateTemplate()
				.find(" from ProductOrder o where o.status = ? ", "1");
		for (ProductOrder order : orderList) {
			Product product = order.getProduct();
			if (product.getType().equals("1")) {
				// 套餐
				if (product.getProType().equals("1")) {
					// 对赌
				} else if (product.getProType().equals("2")) {
					// 按月（时效）付费
				} else if (product.getProType().equals("3")) {
					// 按月（计次）付费
				} else if (product.getProType().equals("4")) {
					// 预付费
				}
			} else if (product.getType().equals("2")) {
				// 实物商品

			} else if (product.getType().equals("3")) {
				// 定制收费
				if (product.getProType().equals("7")) {
					// 计次收费
				} else if (product.getProType().equals("8")) {
					// 计时收费
				}
			} else if (product.getType().equals("4")) {
				// 高级会员套餐

			}
		}
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void saveBalance(final Order order, final List<OrderDetail> orderDetailList) {
		getHibernateTemplate().saveOrUpdate(order);
		for (final OrderDetail pod : orderDetailList) {
			if (pod.getInOutType() == INOUT_TYPE_IN) {
				Member transUser = pod.getTransUser();
			}
			getHibernateTemplate().merge(pod);
		}
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveOrUpdateSignIn(SignIn signIn) {
		Member member = getHibernateTemplate().load(Member.class, signIn.getMemberSign().getId());
		member.setWorkoutTimes(member.getWorkoutTimes() == null ? 1 : member.getWorkoutTimes() + 1);
		getHibernateTemplate().saveOrUpdate(signIn);
		getHibernateTemplate().saveOrUpdate(member);
	}

	/**
	 * E卡通或课程签到
	 */

	@Override
	public List<Map<String, Object>> findSignOrder(final Long memberSignInId, final String memberId) {
		final StringBuffer sb = new StringBuffer();
		sb.append("select * from (");
		sb.append("SELECT a.id, a.member, c.CLUB AS store, b.PROD_NAME AS NAME, '" + Constants.ORDER_TYPE_ONECARD
				+ "'  type ,'' as planDate  FROM tb_product_order_v45 a, tb_product_v45 b, tb_product_club_v45 c"
				+ " WHERE a.ORDER_PRODUCT = b.id AND b.id = c.product AND a. STATUS = '1' And a.orderEndTime >= ?");
		sb.append(" UNION ALL ");
		sb.append("SELECT a.id, a.member, c.member AS store, c.`name` AS NAME, '" + Constants.ORDER_TYPE_COURSE
				+ "' type ,concat(b.planDate, ' ', b.startTime) as planDate  FROM tb_courserelease_order a,"
				+ " tb_course b, tb_course_info c, tb_member m WHERE a.course = b.id AND b.courseId = c.id"
				+ " AND a.`status` = '1' AND (b.planDate > ? OR (b.planDate = ? AND b.startTime >= ?))"
				+ " AND a.member = m.id AND a.id not in(select orderId from tb_sign_in where memberSign = ?)");
		sb.append(") d where d.member =? and d.store = ?");
		System.out.println(sb.toString());
		String dateTime = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String currentDay = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
		List<Map<String, Object>> list = queryForList(sb.toString(), dateTime, currentDay, currentDay, currentTime,
				memberSignInId, memberSignInId, memberId);
		System.out.println(list);
		return list;
	}

	@Override
	public List<ProductOrder> findSignOrder(final Long memberSignInId, final String memberId, int ss) {
		return null;
	}

	/**
	 * 私教套餐签到
	 */
	@Override
	public List<Map<String, Object>> findSignOrder(final Long memberSignInId, final String memberId, String wx) {
		String sql = "select * from (  "
				+ "select o.id as orderId,a.id as prodId, o.member as member,a.member as coach,a.name as NAME,'"
				+ Constants.ORDER_TYPE_PRODUCT + "' type   "
				+ "from tb_product a,tb_member m,tb_product_order o   where  m.id = a.member and m.role = 'S'    "
				+ "and o.product = a.id  and o.status = '1' and a.type='1' ) d where d.member = ? and d.coach = ?";
		System.out.println(sql);
		List<Map<String, Object>> list = queryForList(sql, memberSignInId, memberId);
		System.out.println(list);
		return list;
	}

	@Override
	public ActiveOrder findActiveOrderByNo(String orderno) {
		final List<?> list = getHibernateTemplate().find("from ActiveOrder ao where ao.payNo = ?", orderno);
		if (list.size() > 0)
			return (ActiveOrder) list.get(0);
		return null;
	}

	@Override
	public void loadOrderObject(Complaint c) {
		if (c != null) {
			if (c.getType() == '1') {
				c.setOrder(getHibernateTemplate().load(ProductOrder.class, c.getOrderId()));
			} else if (c.getType() == '2') {
				c.setOrder(getHibernateTemplate().load(ActiveOrder.class, c.getOrderId()));
			} else if (c.getType() == '3') {
				c.setOrder(getHibernateTemplate().load(PlanOrder.class, c.getOrderId()));
			} else if (c.getType() == '4') {
				c.setOrder(getHibernateTemplate().load(FactoryOrder.class, c.getOrderId()));
			} else if (c.getType() == '5') {
				c.setOrder(getHibernateTemplate().load(CourseOrder.class, c.getOrderId()));
			} else {
				c.setOrder(getHibernateTemplate().load(GoodsOrder.class, c.getOrderId()));
			}
		}
	}

	@Override
	public List<Team> findTeamByMember(final Long id) {
		return jdbc.query("select * from tb_active_team", new RowMapper<Team>() {
			@Override
			public Team mapRow(ResultSet rs, int arg1) throws SQLException {
				final Team t = new Team();
				t.setId(rs.getLong("id"));
				t.setName(rs.getString("name"));
				return t;
			}
		});
	}

	public void xdd(Order order, PlanRelease pr) {
		genPlanData(order, pr);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void genPlanData(Order order, PlanRelease pr) {
		if (EasyUtils.count == 0) {
			EasyUtils.count++;
		} else {
			EasyUtils.count = 0;
			return;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<Course> courseList = (List<Course>) findObjectBySql(
					"from Course c where c.member.id = ? and c.planDate between ? and ? order by planDate asc",
					pr.getPlan_participant().getId(), sdf.format(pr.getStartDate()), sdf.format(pr.getEndDate()));
			if (courseList != null && courseList.size() > 0) {
				int daysBetween = UtilDate.daysBetween(sdf.parse(courseList.iterator().next().getPlanDate()),
						order.getOrderStartTime());
				for (Course course : courseList) {
					Course c = (Course) course.clone();
					c.setId(null);
					c.setMember(order.getMember());
					c.setCoach(order.getMember());
					c.setPlanDate(
							sdf.format(UtilDate.addDate(sdf.parse(course.getPlanDate()), Long.valueOf(daysBetween))));
					c.setCreateTime(new Date());
					c = getHibernateTemplate().merge(c);
					for (final Workout workout : course.getWorks()) {
						Workout w;
						w = (Workout) workout.clone();
						w.setId(null);
						w.setCourse(c);
						w = getHibernateTemplate().merge(w);
						for (final WorkoutDetail wod : workout.getDetails()) {
							WorkoutDetail d;
							d = (WorkoutDetail) wod.clone();
							d.setId(null);
							d.setWorkout(w);
							getHibernateTemplate().merge(d);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Order updateOrderStatuse(String orderno) throws Exception {
		final String sql = "select * from (" + Order.getOrderSql() + ") t where t.no = ? or t.no = ?";
		final List<Map<String, Object>> list = jdbc.queryForList(sql, orderno, orderno + orderno.substring(0, 12));
		Order order = null;
		for (final Map<String, Object> map : list) {
			String no = (String) map.get("no");
			order = updateAutoOrders(no);
		}
		return order;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateDou(Order order) throws Exception {
		GoodsOrder ao = (GoodsOrder) order;
		balanceDou(ao);
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Order updateOrderStatus(String orderno, String tradeNo) throws Exception {
		// 支付宝支付传过来的payNo28位，而银联支付传过来的payNo16位，需进行处理，处理方式为在其末尾加上其前12位数字。
		String sql = "select * from (" + Order.getOrderSql() + ") t where t.no in(?,?) or t.payno in(?,?)";
		List<Map<String, Object>> list = jdbc.queryForList(sql, tradeNo, orderno, tradeNo, orderno);
		List<Map<String, Object>> productList = jdbc.queryForList(
				"select * from tb_product_order where payNo = ? or payNo = ?", orderno,
				orderno + orderno.substring(0, 12));
		if (list == null || list.size() == 0) {
			list = productList;
		}
		Order order = null;
		for (final Map<String, Object> map : list) {
			String no = (String) map.get("no");
			if (orderno.startsWith(PRICECUTDOWN_PRODUCT_ORDER)) {
				order = updatePriceCutdwonProductOrder(orderno);
			} else if (orderno.startsWith(CARDCOL_ORDER_NO)) { // 健身卡订单
				order = updateProductOrder(orderno);
			} else if (orderno.startsWith(TB_ACTIVE_ORDER)) { // 活动订单
				order = updateActiveOrder(orderno);
			} else if (orderno.startsWith(TB_PLAN_ORDER)) { // 计划订单
				Character st = map.get("status") == null ? '0' : ((String) map.get("status")).charAt(0);
				PlanOrder po = (PlanOrder) updatePlanOrder(orderno);
				order = po;
				if (st == ORDER_STATUS_BEPAID) {
					PlanRelease pr = po.getPlanRelease();
					genPlanData(po, pr);
				}
			} else if (orderno.startsWith(TB_FACTORY_ORDER)) { // 场地订单
				order = updateFactoryOrder(orderno);
			} else if (orderno.startsWith(TB_COURSE_ORDER)) { // 团体课订单
				order = updateCourseOrder(orderno);
			} else if (orderno.startsWith(TB_GOODS_ORDER)) { // 自动生成订单
				order = updateAutoOrder(orderno);
			} else if (orderno.startsWith(TB_PRODUCT_ORDER_V45)) {
				order = updateProductOrderV45(orderno, tradeNo);
			}
		}

		return order;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Order updateOrderStatus(String orderno, String tradeNo, String flag) throws Exception {
		// 支付宝支付传过来的payNo28位，而银联支付传过来的payNo16位，需进行处理，处理方式为在其末尾加上其前12位数字。
		String sql = "select * from (" + Order.getOrdersqlx() + ") t where t.no in(?,?) or t.payno in(?,?)";
		List<Map<String, Object>> list = jdbc.queryForList(sql, tradeNo, orderno, tradeNo, orderno);
		List<Map<String, Object>> productList = jdbc.queryForList(
				"select * from tb_product_order where payNo = ? or payNo = ?", orderno,
				orderno + orderno.substring(0, 12));
		if (list == null || list.size() == 0) {
			list = productList;
		}
		Order order = null;
		for (final Map<String, Object> map : list) {
			String no = (String) map.get("no");
			if (orderno.startsWith(PRICECUTDOWN_PRODUCT_ORDER)) {
				order = updatePriceCutdwonProductOrder(orderno);
			}
			if (orderno.startsWith(CARDCOL_ORDER_NO)) { // 健身卡订单
				order = updateProductOrderC(orderno);
			} else if (orderno.startsWith(TB_ACTIVE_ORDER)) { // 挑战订单
				order = updateActiveOrderC(orderno);
			} else if (orderno.startsWith(TB_PLAN_ORDER)) { // 计划订单
				Character st = map.get("status") == null ? '0' : ((String) map.get("status")).charAt(0);
				PlanOrder po = (PlanOrder) updatePlanOrderC(orderno);
				order = po;
				if (st == ORDER_STATUS_BEPAID) {
					PlanRelease pr = po.getPlanRelease();
					genPlanData(po, pr);
				}
			} else if (orderno.startsWith(TB_FACTORY_ORDER)) { // 场地订单
				order = updateFactoryOrderC(orderno);
			} else if (orderno.startsWith(TB_COURSE_ORDER)) { // 团体课订单
				order = updateCourseOrderC(orderno);
			} else if (orderno.startsWith(TB_GOODS_ORDER)) { // 自动生成订单
				order = updateAutoOrderC(orderno);
			} else if (orderno.startsWith(TB_PRODUCT_ORDER_V45)) {
				order = updateProductOrderV45C(orderno, tradeNo);
			}
		}

		return order;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Order updateOrderStatus4z(String orderno, String tradeNo, String flag) throws Exception {
		// 支付宝支付传过来的payNo28位，而银联支付传过来的payNo16位，需进行处理，处理方式为在其末尾加上其前12位数字。
		String sql = "select * from (" + Order.getOrdersqlx() + ") t where t.no = ? or t.payNo = ?";
		Object[] objd = { orderno, orderno + orderno.substring(0, 12) };
		List<Map<String, Object>> list = DataBaseConnection.getList(sql, objd);
		String sqldd = "select * from tb_product_order where no = ? or payNo = ?";
		Object[] objdd = { orderno, orderno + orderno.substring(0, 12) };
		List<Map<String, Object>> productList = DataBaseConnection.getList(sqldd, objdd);
		if (list == null || list.size() == 0) {
			list = productList;
		}
		Order order = null;
		for (final Map<String, Object> map : list) {
			String no = (String) map.get("no");
			if (no.startsWith(CARDCOL_ORDER_NO)) { // 健身卡订单
				order = updateProductOrderC(no);
			} else if (no.startsWith(TB_ACTIVE_ORDER)) { // 挑战订单
				order = updateActiveOrderC(no);
			} else if (no.startsWith(TB_PLAN_ORDER)) { // 计划订单
				Character st = map.get("status") == null ? '0' : ((String) map.get("status")).charAt(0);
				PlanOrder po = (PlanOrder) updatePlanOrderC(no);
				order = po;
				if (st == ORDER_STATUS_BEPAID) {
					PlanRelease pr = po.getPlanRelease();
					genPlanData(po, pr);
				}
			} else if (no.startsWith(TB_FACTORY_ORDER)) { // 场地订单
				order = updateFactoryOrderC(no);
			} else if (no.startsWith(TB_COURSE_ORDER)) { // 团体课订单
				order = updateCourseOrderC(no);
			} else if (no.startsWith(TB_GOODS_ORDER)) { // 自动生成订单
				order = updateAutoOrderC(no);
			} else if (no.startsWith(TB_PRODUCT_ORDER_V45)) {
				order = updateProductOrderV45C(no, tradeNo);
			}
		}

		return order;
	}

	@SuppressWarnings("unchecked")
	private Order updatePlanOrder(String orderno) {
		final List<PlanOrder> planOrderList = (List<PlanOrder>) getHibernateTemplate()
				.find("from PlanOrder o where o.no = ? ", orderno);
		if (planOrderList != null && planOrderList.size() > 0) {
			PlanOrder planOrder = planOrderList.get(0);
			if (planOrder.getStatus() == ORDER_STATUS_BEPAID) {
				/*
				 * if (planOrder.getTicketId() != null) { final MemberTicket mt = (MemberTicket)
				 * this.load(MemberTicket.class, planOrder.getTicketId());
				 * mt.setStatus(STATUS_TICKET_USED); this.saveOrUpdate(mt); }
				 */
				planOrder.setStatus(ORDER_STATUS_PAID);
				planOrder.setPayTime(new Date());
				planOrder = getHibernateTemplate().merge(planOrder);
				// 交易明细
				PlanOrderDetail od = new PlanOrderDetail();
				od.setOrder(planOrder);
				od.setTransUser(planOrder.getMember());
				od.setTransObject(new Member(1l));
				od.setInOutType(INOUT_TYPE_OUT);
				od.setDetailDate(new Date());
				od.setDetailMoney(planOrder.getOrderMoney());
				od.setDetailType('1');
				getHibernateTemplate().merge(od);
				Member member = (Member) load(Member.class, planOrder.getMember().getId());
				final Member productMember = (Member) load(Member.class,
						planOrder.getPlanRelease().getMember().getId());
				productMember
						.setOrderCount((productMember.getOrderCount() == null ? 0 : productMember.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);

				for (final PlanOrder po : planOrderList) {
					final PlanRelease product = po.getPlanRelease();
					// 会员购买健身计划后，系统给会员发送购买成功的提醒短信。内容：您成功购买了{0}的{1}，订单号：{2}。请到您的健身日历中查看。
					sendMessage(member.getMobilephone(), "mobile.validate.buy.plan",
							new Object[] { product.getMember().getName(), product.getPlanName(), po.getNo() });
					// 会员购买了商户产品后，系统向商户发出提醒消息。内容：{0}已购买了您的{1}，订单号：{2}。
					sendMessage(productMember.getMobilephone(), "mobile.validate.sale.plan",
							new Object[] { member.getName(), product.getPlanName(), po.getNo() });
				}
			}
			return planOrder;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Order updatePlanOrderC(String orderno) {
		final List<PlanOrder> planOrderList = (List<PlanOrder>) getHibernateTemplate()
				.find("from PlanOrder o where o.no = ? ", orderno);
		if (planOrderList != null && planOrderList.size() > 0) {
			PlanOrder planOrder = planOrderList.get(0);
			if (planOrder.getStatus() == ORDER_STATUS_BEPAID) {
				/*
				 * if (planOrder.getTicketId() != null) { final MemberTicket mt = (MemberTicket)
				 * this.load(MemberTicket.class, planOrder.getTicketId());
				 * mt.setStatus(STATUS_TICKET_USED); this.saveOrUpdate(mt); }
				 */
				planOrder.setStatus(ORDER_STATUS_PAID);
				planOrder.setPayTime(new Date());
				planOrder = getHibernateTemplate().merge(planOrder);
				// 交易明细
				PlanOrderDetail od = new PlanOrderDetail();
				od.setOrder(planOrder);
				od.setTransUser(planOrder.getMember());
				od.setTransObject(new Member(1l));
				od.setInOutType(INOUT_TYPE_OUT);
				od.setDetailDate(new Date());
				od.setDetailMoney(planOrder.getOrderMoney());
				od.setDetailType('1');
				getHibernateTemplate().merge(od);
				Member member = (Member) load(Member.class, planOrder.getMember().getId());
				final Member productMember = (Member) load(Member.class,
						planOrder.getPlanRelease().getMember().getId());
				productMember
						.setOrderCount((productMember.getOrderCount() == null ? 0 : productMember.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);

				for (final PlanOrder po : planOrderList) {
					final PlanRelease product = po.getPlanRelease();
					// //
					// 会员购买健身计划后，系统给会员发送购买成功的提醒短信。内容：您成功购买了{0}的{1}，订单号：{2}。请到您的健身日历中查看。
					// sendMessage(member.getMobilephone(),
					// "mobile.validate.buy.plan",
					// new Object[] { product.getMember().getName(),
					// product.getPlanName(), po.getNo() });
					// // 会员购买了商户产品后，系统向商户发出提醒消息。内容：{0}已购买了您的{1}，订单号：{2}。
					// sendMessage(productMember.getMobilephone(),
					// "mobile.validate.sale.plan",
					// new Object[] { member.getName(), product.getPlanName(),
					// po.getNo() });

					// ${member}购买了您在卡库健身上发布${plan}
					SingleSendSms sms = new SingleSendSms();
					JSONObject obj = new JSONObject();
					obj.accumulate("member", member.getName()).accumulate("plan", product.getPlanName());
					if (!StringUtils.isEmpty(product.getMember().getMobilephone())) {
						sms.sendMsgC(product.getMember().getMobilephone(), obj.toString(), TEMPLATE_PLAN_CODE_C);
					}
				}
			}
			return planOrder;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Order updateFactoryOrder(String orderno) {
		final List<FactoryOrder> factoryOrderList = (List<FactoryOrder>) findObjectBySql(
				" from FactoryOrder o where o.no = ? ", new Object[] { orderno });
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		if (factoryOrderList != null && factoryOrderList.size() > 0) {
			FactoryOrder factoryOrder = factoryOrderList.get(0);
			if (factoryOrder.getStatus() == null || factoryOrder.getStatus() == ORDER_STATUS_BEPAID) {
				factoryOrder.setStatus(ORDER_STATUS_PAID);
				factoryOrder.setPayTime(new Date());
				factoryOrder = getHibernateTemplate().merge(factoryOrder);
				// 生成交易明细
				FactoryOrderDetail aod = new FactoryOrderDetail();
				aod.setOrder(factoryOrder);
				aod.setDetailDate(new Date());
				aod.setDetailMoney(factoryOrder.getOrderMoney());
				aod.setTransUser(factoryOrder.getMember());
				aod.setTransObject(new Member(1l));
				aod.setInOutType(INOUT_TYPE_OUT);
				aod.setDetailType('1');
				getHibernateTemplate().merge(aod);

				final Member member = (Member) load(Member.class, factoryOrder.getMember().getId());

				final Member productMember = (Member) load(Member.class,
						factoryOrder.getFactoryCosts().getFactory().getClub());
				productMember
						.setOrderCount((productMember.getOrderCount() == null ? 0 : productMember.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);

				for (final FactoryOrder po : factoryOrderList) {
					final FactoryCosts product = po.getFactoryCosts();
					final Parameter project = (Parameter) load(Parameter.class,
							Long.parseLong(product.getFactory().getProject()));
					// 您成功购买了｛年/月/日/开始时间-结束时间｝{俱乐部名称}｛运动项目｝｛场地名称｝，请凭此短信到该商户消费。客服电话：12345678901
					sendMessage(member.getMobilephone(), "mobile.validate.buy.factory",
							new Object[] { product.getPlanDate().replaceAll("-", "/") + "/"
									+ sdf.format(po.getOrderStartTime()) + "-" + sdf.format(po.getOrderEndTime()),
									productMember.getName(), project.getName(), product.getFactory().getName(),
									productMember.getTell() });
					// 会员预约场地后，系统向商户发出提醒消息。内容：{0}已购买了您的{年/月/日/开始时间-结束时间}{运动项目}的{场地名称}，请您预留席位并提供服务。
					sendMessage(productMember.getMobilephone(), "mobile.validate.sale.factory",
							new Object[] { member.getName(), product.getPlanDate().replaceAll("-", "/") + "/"
									+ sdf.format(po.getOrderStartTime()) + "-" + sdf.format(po.getOrderEndTime()),
									project.getName(), product.getFactory().getName() });
				}
			}
			return factoryOrder;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Order updateFactoryOrderC(String orderno) {
		final List<FactoryOrder> factoryOrderList = (List<FactoryOrder>) findObjectBySql(
				" from FactoryOrder o where o.no = ? ", new Object[] { orderno });
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		if (factoryOrderList != null && factoryOrderList.size() > 0) {
			FactoryOrder factoryOrder = factoryOrderList.get(0);
			if (factoryOrder.getStatus() == null || factoryOrder.getStatus() == ORDER_STATUS_BEPAID) {
				factoryOrder.setStatus(ORDER_STATUS_PAID);
				factoryOrder.setPayTime(new Date());
				factoryOrder = getHibernateTemplate().merge(factoryOrder);
				// 生成交易明细
				FactoryOrderDetail aod = new FactoryOrderDetail();
				aod.setOrder(factoryOrder);
				aod.setDetailDate(new Date());
				aod.setDetailMoney(factoryOrder.getOrderMoney());
				aod.setTransUser(factoryOrder.getMember());
				aod.setTransObject(new Member(1l));
				aod.setInOutType(INOUT_TYPE_OUT);
				aod.setDetailType('1');
				getHibernateTemplate().merge(aod);

				final Member member = (Member) load(Member.class, factoryOrder.getMember().getId());

				final Member productMember = (Member) load(Member.class,
						factoryOrder.getFactoryCosts().getFactory().getClub());
				productMember
						.setOrderCount((productMember.getOrderCount() == null ? 0 : productMember.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);

				for (final FactoryOrder po : factoryOrderList) {
					final FactoryCosts product = po.getFactoryCosts();
					final Parameter project = (Parameter) load(Parameter.class,
							Long.parseLong(product.getFactory().getProject()));
					// 您成功购买了｛年/月/日/开始时间-结束时间｝{俱乐部名称}｛运动项目｝｛场地名称｝，请凭此短信到该商户消费。客服电话：12345678901
					sendMessage(member.getMobilephone(), "mobile.validate.buy.factory",
							new Object[] { product.getPlanDate().replaceAll("-", "/") + "/"
									+ sdf.format(po.getOrderStartTime()) + "-" + sdf.format(po.getOrderEndTime()),
									productMember.getName(), project.getName(), product.getFactory().getName(),
									productMember.getTell() });
					// 会员预约场地后，系统向商户发出提醒消息。内容：{0}已购买了您的{年/月/日/开始时间-结束时间}{运动项目}的{场地名称}，请您预留席位并提供服务。
					sendMessage(productMember.getMobilephone(), "mobile.validate.sale.factory",
							new Object[] { member.getName(), product.getPlanDate().replaceAll("-", "/") + "/"
									+ sdf.format(po.getOrderStartTime()) + "-" + sdf.format(po.getOrderEndTime()),
									project.getName(), product.getFactory().getName() });
				}
			}
			return factoryOrder;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Order updateCourseOrder(String orderno) {
		final List<CourseOrder> courseOrderList = (List<CourseOrder>) findObjectBySql(
				" from CourseOrder o where o.no = ? ", new Object[] { orderno });
		if (courseOrderList != null && courseOrderList.size() > 0) {
			CourseOrder courseOrder = courseOrderList.get(0);
			if (courseOrder.getStatus() == ORDER_STATUS_BEPAID) {
				/*
				 * if (courseOrder.getTicketId() != null) { final MemberTicket mt =
				 * (MemberTicket) this.load(MemberTicket.class, (long)
				 * courseOrder.getTicketId()); mt.setStatus(STATUS_TICKET_USED);
				 * this.saveOrUpdate(mt); }
				 */
				// 复制商品
				// 更新订单状态
				courseOrder.setStatus(ORDER_STATUS_PAID);
				courseOrder.setPayTime(new Date());
				// saveOrUpdate(courseBalanceImpl.execute(courseOrder));
				// courseOrder = getHibernateTemplate().merge(courseOrder);
				// 增加课程的人数
				Course course = courseOrder.getCourse();
				course.setJoinNum(course.getJoinNum() == null ? 0 : course.getJoinNum() + 1);
				getHibernateTemplate().merge(course);
				CourseOrderDetail od = new CourseOrderDetail();
				od.setOrder(courseOrder);
				od.setTransUser(courseOrder.getMember());
				od.setTransObject(new Member(1l));
				od.setInOutType(INOUT_TYPE_OUT);
				od.setDetailDate(new Date());
				od.setDetailMoney(courseOrder.getOrderMoney());
				od.setDetailType('1');
				getHibernateTemplate().merge(od);

				final Member member = (Member) load(Member.class, courseOrder.getMember().getId());

				Integer integralCount = member.getIntegralCount() == null ? 0 : member.getIntegralCount();
				if (courseOrder.getIntegral() != null)
					integralCount += courseOrder.getIntegral();
				member.setIntegralCount(integralCount);
				getHibernateTemplate().merge(member);

				final Member productMember = (Member) load(Member.class, courseOrder.getCourse().getMember().getId());
				productMember
						.setOrderCount((productMember.getOrderCount() == null ? 0 : productMember.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);

				for (final CourseOrder po : courseOrderList) {
					final Course product = po.getCourse();
					SingleSendSms sms = new SingleSendSms();
					// 您成功购买了${club}的${course}，开课时间:${time}。请使用健身E卡通APP去俱乐部签到上课。
					sms.sendMsg(member.getMobilephone(),
							"{\"club\":\'" + productMember.getName() + "\',\"course\":\'"
									+ product.getCourseInfo().getName() + "\',\"time\":\'"
									+ product.getPlanDate().replaceAll("-", "/") + "/" + product.getStartTime() + "\'}",
							TEMPLATE_COURSE_CODE);
					// ${member}购买了您的${course}，课程时间:${datetime}。请您预留席位并提供服务。
					sms.sendMsg(productMember.getMobilephone(),
							"{\"member\":\'" + member.getName() + "\',\"course\":\'" + product.getCourseInfo().getName()
									+ "\',\"datetime\":\'" + product.getPlanDate().replaceAll("-", "/") + "/"
									+ product.getStartTime() + "\'}",
							TEMPLATE_COURSES_CODE);
				}
			}
			return courseOrder;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private Order updateCourseOrderC(String orderno) {
		final List<CourseOrder> courseOrderList = (List<CourseOrder>) findObjectBySql(
				" from CourseOrder o where o.no = ? ", new Object[] { orderno });
		if (courseOrderList != null && courseOrderList.size() > 0) {
			CourseOrder courseOrder = courseOrderList.get(0);
			if (courseOrder.getStatus() == ORDER_STATUS_BEPAID) {
				/*
				 * if (courseOrder.getTicketId() != null) { final MemberTicket mt =
				 * (MemberTicket) this.load(MemberTicket.class, (long)
				 * courseOrder.getTicketId()); mt.setStatus(STATUS_TICKET_USED);
				 * this.saveOrUpdate(mt); }
				 */
				// 复制商品
				// 更新订单状态
				courseOrder.setStatus(ORDER_STATUS_PAID);
				courseOrder.setPayTime(new Date());
				// saveOrUpdate(courseBalanceImpl.execute(courseOrder));
				// courseOrder = getHibernateTemplate().merge(courseOrder);
				// 增加课程的人数
				Course course = courseOrder.getCourse();
				course.setJoinNum(course.getJoinNum() == null ? 0 : course.getJoinNum() + 1);
				getHibernateTemplate().merge(course);
				CourseOrderDetail od = new CourseOrderDetail();
				od.setOrder(courseOrder);
				od.setTransUser(courseOrder.getMember());
				od.setTransObject(new Member(1l));
				od.setInOutType(INOUT_TYPE_OUT);
				od.setDetailDate(new Date());
				od.setDetailMoney(courseOrder.getOrderMoney());
				od.setDetailType('1');
				getHibernateTemplate().merge(od);

				final Member member = (Member) load(Member.class, courseOrder.getMember().getId());

				Integer integralCount = member.getIntegralCount() == null ? 0 : member.getIntegralCount();
				if (courseOrder.getIntegral() != null)
					integralCount += courseOrder.getIntegral();
				member.setIntegralCount(integralCount);
				getHibernateTemplate().merge(member);

				final Member productMember = (Member) load(Member.class, courseOrder.getCourse().getMember().getId());
				productMember
						.setOrderCount((productMember.getOrderCount() == null ? 0 : productMember.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);

				for (final CourseOrder po : courseOrderList) {
					final Course product = po.getCourse();
					SingleSendSms sms = new SingleSendSms();

					sms.sendMsg(member.getMobilephone(),
							"{\"club\":\'" + productMember.getName() + "\',\"course\":\'"
									+ product.getCourseInfo().getName() + "\',\"time\":\'"
									+ product.getPlanDate().replaceAll("-", "/") + "/" + product.getStartTime() + "\'}",
							TEMPLATE_COURSE_CODE);

					sms.sendMsg(productMember.getMobilephone(),
							"{\"member\":\'" + member.getName() + "\',\"course\":\'" + product.getCourseInfo().getName()
									+ "\',\"datetime\":\'" + product.getPlanDate().replaceAll("-", "/") + "/"
									+ product.getStartTime() + "\'}",
							TEMPLATE_COURSES_CODE);
				}
			}
			return courseOrder;
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Order updateAutoOrder(String orderno) throws Exception {
		final List<GoodsOrder> list1 = (List<GoodsOrder>) getHibernateTemplate()
				.find("from GoodsOrder ao where ao.no = ?", orderno);
		GoodsOrder ao = new GoodsOrder();
		if (list1 != null && list1.size() > 0) {
			ao = list1.get(0);
			log.error("======订单号：" + ao.getNo() + "=======状态：" + ao.getStatus() + "========");
			if (ao.getStatus() == null || ao.getStatus() == ORDER_STATUS_BEPAID) {
				// if (ao.getTicketId() != null) {
				// final MemberTicket mt = (MemberTicket) this.load(MemberTicket.class, (long)
				// ao.getTicketId());
				// mt.setStatus(STATUS_TICKET_USED);
				// this.saveOrUpdate(mt);
				// }
				ao.setStatus(ORDER_STATUS_PAID);
				ao.setPayTime(new Date());
				String updateSql = "update tb_goods_order set status=?,pay_time=? where no=?";
				Object[] args = new Object[] { "3", EasyUtils.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"),
						ao.getNo() };
				DataBaseConnection.updateData(updateSql, args);
				goodsBalanceImpl.execute(ao);

				// GoodsOrderDetail aod = new GoodsOrderDetail();
				// aod.setOrder(ao);
				// aod.setDetailDate(new Date());
				// aod.setDetailMoney(ao.getOrderMoney());
				// aod.setTransUser(ao.getMember());
				// aod.setTransObject(new Member(1l));
				// aod.setInOutType(INOUT_TYPE_OUT);
				// aod.setDetailType('A');
				// getHibernateTemplate().merge(aod);

				Member member = ao.getMember();

				Member saleMember = (Member) load(Member.class, ao.getGoods().getMember());

				SingleSendSms sms = new SingleSendSms();
				// 您成功购买了${plan}，订单号:${orderNo}。请到“我的计划”中查看。
				sms.sendMsg(ao.getMember().getMobilephone(),
						"{\"plan\":\'" + ao.getGoods().getName() + "\',\"orderNo\":\'" + ao.getNo() + "\'}",
						TEMPLATE_GOOD_CODE);
				// ${member}已购买了您的${plan}，订单号:${orderNo}。
				if (saleMember.getMobilephone() != null) {
					sms.sendMsg(
							saleMember.getMobilephone(), "{\"member\":\'" + ao.getMember().getName() + "\',\"plan\":\'"
									+ ao.getGoods().getName() + "\',\"orderNo\":\'" + ao.getNo() + "\'}",
							TEMPLATE_GOODS_CODE);
				}

				if (ao.getGoods().getImplClass() != null) {
					try {
						String cls = ao.getGoods().getImplClass();
						Class<?> clasz = Class.forName(cls);
						Constructor<?> constructor = clasz.getConstructor(IBasicService.class, Long.class, Long.class,
								Date.class);
						IPlanGenerator plan = (IPlanGenerator) constructor.newInstance(this, 1l, ao.getMember().getId(),
								ao.getOrderStartTime());
						plan.generator();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return ao;
			}
		}
		return ao;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void balanceDou(GoodsOrder ao) throws Exception {
		if (ao.getGoods().getImplClass() != null) {
			String cls = ao.getGoods().getImplClass();
			Class<?> clasz = Class.forName(cls);
			Constructor<?> constructor = clasz.getConstructor(IBasicService.class, Long.class, Long.class, Date.class);
			IPlanGenerator plan = (IPlanGenerator) constructor.newInstance(this, 1l, ao.getMember().getId(),
					ao.getOrderStartTime());
			plan.generator();
		}

	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Order updateAutoOrderC(String orderno) throws Exception {
		final List<GoodsOrder> list1 = (List<GoodsOrder>) getHibernateTemplate()
				.find("from GoodsOrder ao where ao.no = ?", orderno);
		GoodsOrder ao = new GoodsOrder();
		if (list1 != null && list1.size() > 0) {
			ao = list1.get(0);
			log.error("======订单号：" + ao.getNo() + "=======状态：" + ao.getStatus() + "========");
			if (ao.getStatus() == null || ao.getStatus() == ORDER_STATUS_BEPAID) {
				/*
				 * if (ao.getTicketId() != null) { final MemberTicket mt = (MemberTicket)
				 * this.load(MemberTicket.class, (long) ao.getTicketId());
				 * mt.setStatus(STATUS_TICKET_USED); this.saveOrUpdate(mt); }
				 */
				ao.setStatus(ORDER_STATUS_PAID);
				ao.setPayTime(new Date());
				saveOrUpdate(goodsBalanceImpl.execute(ao));

				Member member = ao.getMember();

				Member saleMember = (Member) load(Member.class, ao.getGoods().getMember());

				SingleSendSms sms = new SingleSendSms();
				// 您成功购买了${plan}，订单号:${orderNo}。请到“我的计划”中查看。
				sms.sendMsgC(ao.getMember().getMobilephone(),
						"{\"plan\":\'" + ao.getGoods().getName() + "\',\"orderNo\":\'" + ao.getNo() + "\'}",
						TEMPLATE_EXPERT_USER_CODE_C);
				// ${member}已购买了您的${plan}，订单号:${orderNo}。
				sms.sendMsgC(
						saleMember.getMobilephone(), "{\"member\":\'" + ao.getMember().getName() + "\',\"plan\":\'"
								+ ao.getGoods().getName() + "\',\"orderNo\":\'" + ao.getNo() + "\'}",
						TEMPLATE_EXPERT_CODE_C);
				if (ao.getGoods().getImplClass() != null) {
					String cls = ao.getGoods().getImplClass();
					Class<?> clasz = Class.forName(cls);
					Constructor<?> constructor = clasz.getConstructor(IBasicService.class, Long.class, Long.class,
							Date.class);
					IPlanGenerator plan = (IPlanGenerator) constructor.newInstance(this, 1l, ao.getMember().getId(),
							ao.getOrderStartTime());
					plan.generator();
				}
				return ao;
			}
		}
		return ao;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	public Order updateAutoOrders(String orderno) throws Exception {
		final List<GoodsOrder> list1 = (List<GoodsOrder>) getHibernateTemplate().find("FROM GoodsOrder where no =?",
				orderno);
		GoodsOrder ao = new GoodsOrder();
		if (list1 != null && list1.size() > 0) {
			ao = list1.get(0);
			Member member = ao.getMember();
			Member saleMember = (Member) load(Member.class, ao.getGoods().getMember());
			SingleSendSms sms = new SingleSendSms();
			// 您成功购买了${plan}，订单号:${orderNo}。请到“我的计划”中查看。
			sms.sendMsg(ao.getMember().getMobilephone(),
					"{\"plan\":\'" + ao.getGoods().getName() + "\',\"orderNo\":\'" + ao.getNo() + "\'}",
					TEMPLATE_GOOD_CODE);
			// ${member}已购买了您的${plan}，订单号:${orderNo}。
			sms.sendMsg(saleMember.getMobilephone(), "{\"member\":\'" + ao.getMember().getName() + "\',\"plan\":\'"
					+ ao.getGoods().getName() + "\',\"orderNo\":\'" + ao.getNo() + "\'}", TEMPLATE_GOODS_CODE);
			if (ao.getGoods().getImplClass() != null) {
				String cls = ao.getGoods().getImplClass();
				Class<?> clasz = Class.forName(cls);
				Constructor<?> constructor = clasz.getConstructor(IBasicService.class, Long.class, Long.class,
						Date.class);
				IPlanGenerator plan = (IPlanGenerator) constructor.newInstance(this, 1l, ao.getMember().getId(),
						ao.getOrderStartTime());
				plan.generator();
			}
			return ao;
		}
		return ao;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Order updateActiveOrder(String orderno) {
		final List<ActiveOrder> list1 = (List<ActiveOrder>) getHibernateTemplate()
				.find("from ActiveOrder ao where ao.no = ?", orderno);
		ActiveOrder ao = new ActiveOrder();
		if (list1 != null && list1.size() > 0) {
			ao = list1.get(0);
			if (ao.getStatus() == null || ao.getStatus() == ORDER_STATUS_BEPAID) {
				ao.setStatus(ORDER_STATUS_PAID);
				ao.setPayTime(new Date());
				ao = getHibernateTemplate().merge(ao);
				/*
				 * if (ao.getTicketId() != null) { final MemberTicket mt = (MemberTicket)
				 * this.load(MemberTicket.class, ao.getTicketId());
				 * mt.setStatus(STATUS_TICKET_USED); this.saveOrUpdate(mt); }
				 */
				ActiveOrderDetail aod = new ActiveOrderDetail();
				aod.setOrder(ao);
				aod.setDetailDate(new Date());
				aod.setDetailMoney(ao.getOrderMoney());
				aod.setTransUser(ao.getMember());
				aod.setTransObject(new Member(1l));
				aod.setInOutType(INOUT_TYPE_OUT);
				aod.setDetailType('A');
				getHibernateTemplate().merge(aod);

				Member member = ao.getMember();

				// 会员参加挑战后，系统给会员发送购买成功的提醒短信。内容：您已报名参加${activeName}，请努力达到自己的健身目标。
				SingleSendSms sms = new SingleSendSms();
				sms.sendMsg(ao.getMember().getMobilephone(), "{\"activeName\":\'" + ao.getActive().getName() + "\'}",
						TEMPLATE_ACTIVE_CODE);
				// 会员参加挑战后，系统向商户发出提醒消息。${member}已报名参加您发起的${active}，请帮助TA实现健身目标。
				sms.sendMsg(ao.getActive().getCreator().getMobilephone(), "{\"member\":\'" + ao.getMember().getName()
						+ "\',\"active\":\'" + ao.getActive().getName() + "\'}", TEMPLATE_ACTIVES_CODE);

			}
		}
		return ao;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	private Order updateActiveOrderC(String orderno) {
		final List<ActiveOrder> list1 = (List<ActiveOrder>) getHibernateTemplate()
				.find("from ActiveOrder ao where ao.no = ?", orderno);
		ActiveOrder ao = new ActiveOrder();
		if (list1 != null && list1.size() > 0) {
			ao = list1.get(0);
			if (ao.getStatus() == null || ao.getStatus() == ORDER_STATUS_BEPAID) {
				ao.setStatus(ORDER_STATUS_PAID);
				ao.setPayTime(new Date());
				ao = getHibernateTemplate().merge(ao);
				/*
				 * if (ao.getTicketId() != null) { final MemberTicket mt = (MemberTicket)
				 * this.load(MemberTicket.class, ao.getTicketId());
				 * mt.setStatus(STATUS_TICKET_USED); this.saveOrUpdate(mt); }
				 */
				ActiveOrderDetail aod = new ActiveOrderDetail();
				aod.setOrder(ao);
				aod.setDetailDate(new Date());
				aod.setDetailMoney(ao.getOrderMoney());
				aod.setTransUser(ao.getMember());
				aod.setTransObject(new Member(1l));
				aod.setInOutType(INOUT_TYPE_OUT);
				aod.setDetailType('A');
				getHibernateTemplate().merge(aod);

				Member member = ao.getMember();

				// 会员参加挑战后，系统给会员发送购买成功的提醒短信。内容：您已报名参加${activeName}，请努力达到自己的健身目标。
				SingleSendSms sms = new SingleSendSms();
				sms.sendMsgC(ao.getMember().getMobilephone(), "{\"activeName\":\'" + ao.getActive().getName() + "\'}",
						TEMPLATE_ACTIVE_CODE_C);

				// //
				// 会员参加挑战后，系统向商户发出提醒消息。${member}已报名参加您发起的${active}，请帮助TA实现健身目标。
				// sms.sendMsgC(ao.getActive().getCreator().getMobilephone(),
				// "{\"member\":\'" + ao.getMember().getName()
				// + "\',\"active\":\'" + ao.getActive().getName() + "\'}",
				// TEMPLATE_ACTIVES_CODE);

			}
		}
		return ao;
	}

	@SuppressWarnings("unchecked")
	private Order updateProductOrder(String orderno) {
		final List<ProductOrder> orderList = (List<ProductOrder>) findObjectBySql(
				" from ProductOrder o where o.no = ? ", new Object[] { orderno });
		ProductOrder order = new ProductOrder();
		if (orderList != null && orderList.size() > 0) {
			order = orderList.get(0);
			// 判断该订单是否已经确认付款
			if (order.getStatus() == null || order.getStatus() == ORDER_STATUS_BEPAID) {
				order.setStatus(ORDER_STATUS_PAID);
				order.setPayTime(new Date());
				order = getHibernateTemplate().merge(order);
				Member member = (Member) load(Member.class, order.getMember().getId());
				Integer integralCount = member.getIntegralCount() == null ? 0 : member.getIntegralCount();
				ProductOrderDetail od = null;
				Date detailDate = new Date();
				Double detailMoney = 0d;
				// 复制商品
				for (ProductOrder o : orderList) {

					o.setStatus(ORDER_STATUS_PAID);
					o = getHibernateTemplate().merge(o);
					od = new ProductOrderDetail();
					od.setOrder(o);
					od.setTransUser(o.getMember());
					od.setTransObject(new Member(1l));
					od.setInOutType(INOUT_TYPE_OUT);
					od.setDetailDate(detailDate);
					od.setDetailMoney(o.getOrderMoney());
					od.setDetailType('1');
					getHibernateTemplate().merge(od);

					detailMoney += o.getOrderMoney();

					if (o.getIntegral() != null)
						integralCount += o.getIntegral();
					if (o.getProduct().getType().equals("4"))
						member.setGrade("1");
				}
				member.setIntegralCount(integralCount);

				getHibernateTemplate().merge(member);
				final Member productMember = (Member) load(Member.class, order.getProduct().getMember().getId());
				productMember.setOrderCount((member.getOrderCount() == null ? 0 : member.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);
				for (final ProductOrder po : orderList) {
					final Product product = po.getProduct();
					// 您成功购买了{俱乐部昵称}的{健身卡名称}，订单号：{2}。请凭此短信到该商户处签到消费。客服电话：{12345678901}
					sendMessage(member.getMobilephone(), "mobile.validate.buy.product", new Object[] {
							product.getMember().getName(), product.getName(), po.getNo(), productMember.getTell() });
					// 会员购买了商户产品后，系统向商户发出提醒消息。内容：[用户名]已经购买了您的[商品名]，订单号：[订单编号]，请您按合同约定为该会员提供服务。
					sendMessage(product.getMember().getMobilephone(), "mobile.validate.sale.product",
							new Object[] { member.getName(), product.getName(), po.getNo() });
				}
			}
		}
		return order;
	}

	@SuppressWarnings("unchecked")
	private Order updatePriceCutdwonProductOrder(String orderno) {
		final List<ProductOrder> orderList = (List<ProductOrder>) findObjectBySql(
				" from ProductOrder o where o.no = ? ", new Object[] { orderno });
		ProductOrder order = new ProductOrder();
		if (orderList != null && orderList.size() > 0) {
			order = orderList.get(0);
			// 处理砍价活动数据
			Long priceCutdwonId = Long.valueOf(
					order.getPayNo().substring(18 + PRICECUTDOWN_PRODUCT_ORDER.length(), order.getPayNo().length()));
			String sqlx = " update tb_price_cutdown set status = 1 where id = ? ";
			String sqly = " update tb_price_cutdown set status = 1 where parent = ? ";
			Object[] objx = { priceCutdwonId };
			DataBaseConnection.updateData(sqlx, objx);
			DataBaseConnection.updateData(sqly, objx);

			// 判断该订单是否已经确认付款
			if (order.getStatus() == null || order.getStatus() == ORDER_STATUS_BEPAID) {
				order.setStatus(ORDER_STATUS_PAID);
				order.setPayTime(new Date());
				order = getHibernateTemplate().merge(order);
				Member member = (Member) load(Member.class, order.getMember().getId());
				Integer integralCount = member.getIntegralCount() == null ? 0 : member.getIntegralCount();
				ProductOrderDetail od = null;
				Date detailDate = new Date();
				Double detailMoney = 0d;
				// 复制商品
				for (ProductOrder o : orderList) {

					o.setStatus(ORDER_STATUS_PAID);
					o = getHibernateTemplate().merge(o);
					od = new ProductOrderDetail();
					od.setOrder(o);
					od.setTransUser(o.getMember());
					od.setTransObject(new Member(1l));
					od.setInOutType(INOUT_TYPE_OUT);
					od.setDetailDate(detailDate);
					od.setDetailMoney(o.getOrderMoney());
					od.setDetailType('1');
					getHibernateTemplate().merge(od);

					detailMoney += o.getOrderMoney();

					if (o.getIntegral() != null)
						integralCount += o.getIntegral();
					if (o.getProduct().getType().equals("4"))
						member.setGrade("1");
				}
				member.setIntegralCount(integralCount);

				getHibernateTemplate().merge(member);
				final Member productMember = (Member) load(Member.class, order.getProduct().getMember().getId());
				productMember.setOrderCount((member.getOrderCount() == null ? 0 : member.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);
				for (final ProductOrder po : orderList) {
					final Product product = po.getProduct();
					// 您成功购买了{俱乐部昵称}的{健身卡名称}，订单号：{2}。请凭此短信到该商户处签到消费。客服电话：{12345678901}
					sendMessage(member.getMobilephone(), "mobile.validate.buy.product", new Object[] {
							product.getMember().getName(), product.getName(), po.getNo(), productMember.getTell() });
					// 会员购买了商户产品后，系统向商户发出提醒消息。内容：[用户名]已经购买了您的[商品名]，订单号：[订单编号]，请您按合同约定为该会员提供服务。
					sendMessage(product.getMember().getMobilephone(), "mobile.validate.sale.product",
							new Object[] { member.getName(), product.getName(), po.getNo() });
				}

			}
		}
		return order;
	}

	@SuppressWarnings({ "unchecked" })
	private Order updateProductOrderC(String orderno) {
		final List<ProductOrder> orderList = (List<ProductOrder>) findObjectBySql(
				" from ProductOrder o where o.no = ? ", new Object[] { orderno });
		ProductOrder order = new ProductOrder();
		if (orderList != null && orderList.size() > 0) {
			order = orderList.get(0);

			// 判断该订单是否已经确认付款
			if (order.getStatus() == null || order.getStatus() == ORDER_STATUS_BEPAID) {
				order.setStatus(ORDER_STATUS_PAID);
				order.setPayTime(new Date());
				order = getHibernateTemplate().merge(order);

				//
				try {
					Member club = order.getProduct().getMember();
					Friend friend = new Friend();
					friend.setFriend(club);
					friend.setMember(order.getMember());
					friend.setJoinTime(new Date());
					friend.setTopTime(new Date());
					friend.setType("1");
					friend.setIsCore("0");
					getHibernateTemplate().merge(friend);
				} catch (Exception e) {
					e.printStackTrace();
				}

				// 送人健康返佣金
				try {
					// 送人健康返佣金, 在结算数据表中添加一条数据
					if ("S".equals(order.getOrigin().toString())) {
						// 生成返现红包
						String updateSql = "insert into tb_order_balance_v45(id,BALANCE_TIME,ORDER_MONEY,BALANCE_MONEY,BALANCE_SERVICE,ORDER_NO,PROD_NAME,BALANCE_TYPE,BALANCE_FROM,BALANCE_TO,ORDER_ID)"
								+ " values(null,?,?,?,?,?,?,?,?,?,?)";

						Object[] args = { new Date(), order.getOrderMoney(), order.getOrderMoney() * 0.5, 0,
								order.getNo(), "返现佣金", 1, 1, order.getContractMoney(), order.getId() };
						DataBaseConnection.updateData(updateSql, args);

						// 添加返现提醒记录
						updateSql = "insert into tb_back_money(id,from_member,to_member,back_money,time,status) values(null,?,?,?,?,?)";
						args = new Object[] { order.getMember().getId(), order.getContractMoney(),
								order.getOrderMoney() * 0.5, new Date(), 0 };
						DataBaseConnection.updateData(updateSql, args);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				/*
				 * if (order.getTicketId() != null) { final MemberTicket mt = (MemberTicket)
				 * this.load(MemberTicket.class, order.getTicketId());
				 * mt.setStatus(STATUS_TICKET_USED); this.saveOrUpdate(mt); }
				 */
				Member member = (Member) load(Member.class, order.getMember().getId());
				Integer integralCount = member.getIntegralCount() == null ? 0 : member.getIntegralCount();
				ProductOrderDetail od = null;
				Date detailDate = new Date();
				Double detailMoney = 0d;
				// 复制商品
				for (ProductOrder o : orderList) {

					o.setStatus(ORDER_STATUS_PAID);
					o = getHibernateTemplate().merge(o);
					od = new ProductOrderDetail();
					od.setOrder(o);
					od.setTransUser(o.getMember());
					od.setTransObject(new Member(1l));
					od.setInOutType(INOUT_TYPE_OUT);
					od.setDetailDate(detailDate);
					od.setDetailMoney(o.getOrderMoney());
					od.setDetailType('1');
					getHibernateTemplate().merge(od);

					detailMoney += o.getOrderMoney();

					if (o.getIntegral() != null)
						integralCount += o.getIntegral();
					if (o.getProduct().getType().equals("4"))
						member.setGrade("1");
				}
				member.setIntegralCount(integralCount);

				getHibernateTemplate().merge(member);
				final Member productMember = (Member) load(Member.class, order.getProduct().getMember().getId());
				productMember.setOrderCount((member.getOrderCount() == null ? 0 : member.getOrderCount()) + 1);
				getHibernateTemplate().merge(productMember);
				if (!"5".equals(order.getOrderType())) {
					DataBaseConnection.updateData("update tb_member set coach = ? where id = ?",
							new Object[] { productMember.getId(), member.getId() });
				}
				for (final ProductOrder po : orderList) {
					final Product product = po.getProduct();
					// 您成功购买了{俱乐部昵称}的{健身卡名称}，订单号：{2}。请凭此短信到该商户处签到消费。客服电话：{12345678901}
					sendMessage(member.getMobilephone(), "mobile.validate.buy.product", new Object[] {
							product.getMember().getName(), product.getName(), po.getNo(), productMember.getTell() });
					// 会员购买了商户产品后，系统向商户发出提醒消息。内容：[用户名]已经购买了您的[商品名]，订单号：[订单编号]，请您按合同约定为该会员提供服务。
					sendMessage(product.getMember().getMobilephone(), "mobile.validate.sale.product",
							new Object[] { member.getName(), product.getName(), po.getNo() });
				}
			}
		}
		return order;
	}

	@Override
	public Object findActivePartakeMembers(Active active) {
		String sql = "select * from tb_member a where a.id in (select b.member from tb_active_order b where b.active = ? and b.status <> ? and b.status is not null)";
		return queryForList(sql, active.getId(), 0);
	}

	@Override
	public void updateOrderPayType(String payNo, String payType, Double ticketPrice) {
		String[] sql = {
				"update tb_goods_Order set payType = " + payType + ", ticket_amount = " + ticketPrice + " where payNo="
						+ payNo,
				"update TB_PlanRelease_ORDER set payType = " + payType + ", ticket_amount = " + ticketPrice
						+ " where payNo=" + payNo,
				"update tb_product_Order set payType = " + payType + ", ticket_amount = " + ticketPrice
						+ " where payNo=" + payNo,
				"update tb_active_Order set payType = " + payType + ", ticket_amount = " + ticketPrice + " where payNo="
						+ payNo,
				"update TB_CourseRelease_ORDER set payType = " + payType + ", ticket_amount = " + ticketPrice
						+ " where payNo=" + payNo,
				"update tb_factory_Order set payType = " + payType + ", ticket_amount = " + ticketPrice
						+ " where payNo=" + payNo };
		jdbc.batchUpdate(sql);
	}

	@SuppressWarnings("unchecked")
	public Order updateProductOrderV45(String orderno, String tradeNo) {
		Member member = new Member();
		final List<ProductOrder45> orderList = (List<ProductOrder45>) findObjectBySql(
				" from ProductOrder45 o where o.no = ? ", new Object[] { orderno });
		ProductOrder45 po45 = new ProductOrder45();
		if (orderList != null && orderList.size() > 0) {
			ProductOrder45 order = orderList.get(0);
			if (order.getStatus() == ORDER_STATUS_BEPAID) {
				// 修改订单状态和支付时间
				order.setStatus(ORDER_STATUS_PAID);
				order.setPayTime(new Date());
				getHibernateTemplate().merge(order);

				// 发送购买成功的短信提醒
				// 您成功购买了${productName}，订单号:${orderNo}。请使用健身E卡通APP到适用俱乐部签到健身。
				SingleSendSms sms = new SingleSendSms();
				sms.sendMsg(order.getMember().getMobilephone(),
						"{\"productName\":\'" + order.getProduct().getName() + "\',\"orderNo\":\'" + orderno + "\'}",
						TEMPLATE_PRODUCT_CODE);

				// 如果订单有优惠券就把优惠券的状态改为已使用
				/*
				 * if (order.getTicketId() != null) { final MemberTicket mt = (MemberTicket)
				 * this.load(MemberTicket.class, (long) order.getTicketId());
				 * mt.setStatus(STATUS_TICKET_USED); this.saveOrUpdate(mt); }
				 */

				// E卡通微信公众号请人流汗活动,如果是分享订单,需要返现,在结算数据表中添加一条数据
				if (EasyUtils.shareOrder != null) {
					String updateSql;

					// 生成返现红包
					updateSql = "insert into tb_order_balance_v45(id,BALANCE_TIME,ORDER_MONEY,BALANCE_MONEY,BALANCE_SERVICE,ORDER_NO,PROD_NAME,BALANCE_TYPE,BALANCE_FROM,BALANCE_TO,ORDER_ID)"
							+ " values(null,?,?,?,?,?,?,?,?,?,?)";

					String orderId = DataBaseConnection
							.getOne("select id from " + EasyUtils.orderType + " where no =" + EasyUtils.orderNo, null)
							.get("id").toString();

					Object[] args = { new Date(), EasyUtils.shareOrder.get("backMoney"),
							EasyUtils.shareOrder.get("backMoney"), 0, "55" + EasyUtils.getRandomName(4), "红包", 1, 1,
							EasyUtils.shareOrder.get("toMember"), orderId };
					DataBaseConnection.updateData(updateSql, args);

					// 添加返现提醒记录
					updateSql = "insert into tb_back_money(id,from_member,to_member,back_money,time,status) values(null,?,?,?,?,?)";
					args = new Object[] { EasyUtils.shareOrder.get("fromMember"), EasyUtils.shareOrder.get("toMember"),
							EasyUtils.shareOrder.get("backMoney"), new Date(), 0 };
					DataBaseConnection.updateData(updateSql, args);

					// 重置常量
					EasyUtils.shareOrder = null;
				}

				// E卡通H5活动,给购买者赠送优惠券
				Ticket ticket = null;
				if (order.getProduct().getId() == 1) {
					// 赠送70的13
					ticket = (Ticket) DataBaseConnection.load("tb_ticket", Ticket.class, Long.valueOf("13"));
				}

				if (order.getProduct().getId() == 2) {
					// 赠送80的14
					ticket = (Ticket) DataBaseConnection.load("tb_ticket", Ticket.class, Long.valueOf("14"));
				}

				if (order.getProduct().getId() == 3) {
					// 赠送90的15
					ticket = (Ticket) DataBaseConnection.load("tb_ticket", Ticket.class, Long.valueOf("15"));
				}
				member = order.getMember();
				MemberTicket mt = new MemberTicket();
				mt.setMember(member);
				mt.setTicket(ticket);
				mt.setActiveDate(new Date());
				mt.setStatus(STATUS_TICKET_USE);
				mt = (MemberTicket) getHibernateTemplate().merge(mt);
			}
		}
		return po45;
	}

	@SuppressWarnings("unchecked")
	public Order updateProductOrderV45C(String orderno, String tradeNo) {
		final List<ProductOrder45> orderList = (List<ProductOrder45>) findObjectBySql(
				" from ProductOrder45 o where o.no = ? ", new Object[] { orderno });
		ProductOrder45 po45 = new ProductOrder45();
		if (orderList != null && orderList.size() > 0) {
			ProductOrder45 order = orderList.get(0);
			/*
			 * if (order.getTicketId() != null) { final MemberTicket mt = (MemberTicket)
			 * this.load(MemberTicket.class, (long) order.getTicketId());
			 * mt.setStatus(STATUS_TICKET_USED); this.saveOrUpdate(mt); }
			 */
			if (order.getStatus() == ORDER_STATUS_BEPAID) {
				BeanUtils.copyProperties(order, po45);
				for (int i = 1; i <= order.getCount(); i++) {
					ProductOrder45 eachOrder = new ProductOrder45();
					BeanUtils.copyProperties(order, eachOrder);
					eachOrder.setStatus(ORDER_STATUS_PAID);
					eachOrder.setPayTime(new Date());
					eachOrder.setId(null);
					eachOrder.setNo(orderno /* + "-" + String.valueOf(i) */);
					eachOrder.setTradeNo(tradeNo);
					getHibernateTemplate().merge(eachOrder);
				}
				getHibernateTemplate().delete(order);
				// 您成功购买了${productName}，订单号:${orderNo}。请使用健身E卡通APP到适用俱乐部签到健身。
				SingleSendSms sms = new SingleSendSms();
				sms.sendMsg(order.getMember().getMobilephone(),
						"{\"productName\":\'" + order.getProduct().getName() + "\',\"orderNo\":\'" + orderno + "\'}",
						TEMPLATE_PRODUCT_CODE);
			}
		}
		return po45;
	}

	@Override
	public void ordersms(Order order, String orderType) {
		if ("2".equals(orderType)) {
			ActiveOrder ao = (ActiveOrder) this.load(ActiveOrder.class, order.getId());
			SingleSendSms sms = new SingleSendSms();
			sms.sendMsg(ao.getMember().getMobilephone(), "{\"activeName\":\'" + ao.getActive().getName() + "\'}",
					TEMPLATE_ACTIVE_CODE);
			// 会员参加挑战后，系统向商户发出提醒消息。${member}已报名参加您发起的${active}，请帮助TA实现健身目标。
			sms.sendMsg(ao.getActive().getCreator().getMobilephone(),
					"{\"member\":\'" + ao.getMember().getName() + "\',\"active\":\'" + ao.getActive().getName() + "\'}",
					TEMPLATE_ACTIVES_CODE);
		} else if ("5".equals(orderType)) {
			CourseOrder co = (CourseOrder) this.load(CourseOrder.class, order.getId());
			final Member member = (Member) load(Member.class, co.getMember().getId());
			final Member productMember = (Member) load(Member.class, co.getCourse().getMember().getId());
			final Course product = co.getCourse();
			SingleSendSms sms = new SingleSendSms();
			// 您成功购买了${club}的${course}，开课时间:${time}。请使用健身E卡通APP去俱乐部签到上课。
			sms.sendMsg(member.getMobilephone(),
					"{\"club\":\'" + productMember.getName() + "\',\"course\":\'" + product.getCourseInfo().getName()
							+ "\',\"time\":\'" + product.getPlanDate().replaceAll("-", "/") + "/"
							+ product.getStartTime() + "\'}",
					TEMPLATE_COURSE_CODE);
			// ${member}购买了您的${course}，课程时间:${datetime}。请您预留席位并提供服务。
			sms.sendMsg(productMember.getMobilephone(),
					"{\"member\":\'" + member.getName() + "\',\"course\":\'" + product.getCourseInfo().getName()
							+ "\',\"datetime\":\'" + product.getPlanDate().replaceAll("-", "/") + "/"
							+ product.getStartTime() + "\'}",
					TEMPLATE_COURSES_CODE);
		} else if ("8".equals(orderType)) {
			ProductOrder45 po = (ProductOrder45) this.load(ProductOrder45.class, order.getId());
			// 您成功购买了${productName}，订单号:${orderNo}。请使用健身E卡通APP到适用俱乐部签到健身。
			SingleSendSms sms = new SingleSendSms();
			sms.sendMsg(po.getMember().getMobilephone(),
					"{\"productName\":\'" + po.getProduct().getName() + "\',\"orderNo\":\'" + po.getNo() + "\'}",
					TEMPLATE_PRODUCT_CODE);
		}

	}
}
