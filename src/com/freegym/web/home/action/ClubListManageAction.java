package com.freegym.web.home.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.config.CourseInfo;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.order.Product;
import com.freegym.web.order.ProductGrade;
import com.freegym.web.utils.DBConstant;
import com.sanmen.web.core.content.Banner;
import com.sanmen.web.core.system.Area;
import com.sanmen.web.core.system.Parameter;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/home/clublist.jsp"),
		@Result(name = "shop_go", location = "/home/shop_go.jsp"),
		@Result(name = "loadpic", location = "/home/loadpic.jsp"),
		@Result(name = "list", location = "/home/clublist_middle.jsp") })
public class ClubListManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3769475961884283745L;

	private String flag;
	private Product product;
	private Long productId;
	/**
	 * 查询类型
	 */
	private Character queryType;

	private String typeId;// 服务项目
	private String typeName;// 服务项目
	private List<Parameter> typeList;

	private String area;// 地区,主要指区县
	private List<Area> areaList;
	private String grade;// 评分'':全部2:20分以下3:20-40分4:40-60分5:60-80分6:80-100分
	private String orderType;// 排序方式''：默认2：发布时间3：销量4：评分
	private String keyword; // 关键字搜索

	// v3新增字段
	private List<Parameter> proTypeList; // 健身卡类型集
	private String proType;// 健身卡类型
	private String proTypeName;// 服务项目
	private String changguangyuding;// 场馆预订，名称暂时定为它，有需要就repalce这个名称

	private List<Banner> bannerList;

	private String day1;
	private String day2;
	private String day3;

	private String lproType; // 左上侧健身卡类型
	private String lproTypeName; // 左上侧健身卡类型名称
	private String ltypeId; // 左上侧场馆预订
	private String ltypeName; // 左上侧场馆预订名称

	private ProductGrade productGrade;

	private File picFile;
	private String fileName;

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String execute() throws UnsupportedEncodingException {
		session.setAttribute("position", 2);

		String currentCity = request.getParameter("currentCity") == null ? (String) session.getAttribute("currentCity")
				: URLDecoder.decode(request.getParameter("currentCity"), "UTF-8");
		currentCity = currentCity.trim();
		if("".equals(currentCity)){
			currentCity = "北京市";
		}
		
		session.setAttribute("currentCity", currentCity);
		// 健身场馆广告位
		request.setAttribute("factoryRecommends",
				service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_J, currentCity));
		typeList = service.findParametersByCodes("course_type_c");
		proTypeList = (List<Parameter>) service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
				"card_type_c");
		areaList = (List<Area>) service.findObjectBySql(
				"from Area a where a.parent in (select a1.id from Area a1 where a1.name = ? and  a1.parent != null )",
				currentCity);
		pageInfo.setPageSize(5);
		
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(keyword)) {
			currentCity = "";
		}
		StringBuffer sql = new StringBuffer(Member.Sql4US(null, null, null, null, null, null, currentCity));
		sql.append(" and m.role = 'E' ) ");
		// 关键字搜索
		if (keyword != null && !"".equals(keyword)) {
			final String where = "%" + keyword + "%";
			sql.append(" or (m.name like '").append(where).append("' or m.nick like '").append(where)
					.append("' or m.email like '").append(where).append("' or m.mobilephone like '").append(where)
					.append("')");
		}
		// 左上侧场馆预订
		if (typeId == null || "".equals(typeId)) {
			if (ltypeId != null && !"".equals(ltypeId)) {
				typeId = ltypeId;
				typeName = ltypeName;
			}
		}
		if (typeId != null && !"".equals(typeId)) {
			sql.append(" and (m.id in (select club from tb_member_factory where project =" + typeId
					+ " and applied = 1 ) or m.id in (select member from tb_course_info where type = " + typeId + "))");
		}
		// 点击左上侧健身卡
		if (proType == null || "".equals(proType)) {
			if (lproType != null && !"".equals(lproType)) {
				proType = lproType;
				proTypeName = lproTypeName;
			}
		}
		if (proType != null && !"".equals(proType)) {
			sql.append(" and m.id in (select distinct member from tb_product where proType =" + proType + ") ");
		}
		final List<Object> parms = new ArrayList<Object>();
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());
		Random random = new Random();
		double score = 0;
		List list = new ArrayList<>();
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			Long object = (Long) p.get("countEmp");
			if (null == object || object.equals(0l)) {
				int i = random.nextInt(10) + 1;
				p.put("countEmp", i);
				p.put("member_grade", score > 0 ? score - 5 : 50);
			} else {
				score = Double.valueOf(String.valueOf(p.get("member_grade")));
			}

			String role = String.valueOf(p.get("role"));
			if(role.equals("E")) {
				list.add(p);
			}
		}
		pageInfo.setItems(list);
		if (pageInfo.getItems().size() > 0) {
			request.setAttribute("products", service.loadProductsByMember(pageInfo.getItems()));
			request.setAttribute("courseInfos", service.loadCourseInfosByMember(pageInfo.getItems()));
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		day1 = sdf.format(new Date());
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdfshow = new SimpleDateFormat("MM月dd日");
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date date = calendar.getTime();
		day2 = sdf.format(date);
		String day2show = sdfshow.format(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		String day3show = sdfshow.format(date);
		day3 = sdf.format(date);
		request.setAttribute("courseList1",
				service.queryForList(
						"select a.*,b.image,b.name,m1.name clubName from tb_course a left join tb_course_info b on a.courseId = b.id left join tb_member m1 on a.member = m1.id where a.planDate =? and a.member in(select id from tb_member m where m.role='E' and m.city= ?) and a.startTime > ? order by a.hour_price asc limit 8",
						day1, currentCity, new SimpleDateFormat("HH:mm").format(new Date())));
		request.setAttribute("courseList2",
				service.queryForList(
						"select a.*,b.image,b.name,m1.name clubName from tb_course a left join tb_course_info b on a.courseId = b.id left join tb_member m1 on a.member = m1.id where a.planDate =? and a.member in(select id from tb_member m where m.role='E' and m.city= ?) order by a.hour_price asc limit 8",
						day2, currentCity));
		request.setAttribute("courseList3",
				service.queryForList(
						"select a.*,b.image,b.name,m1.name clubName from tb_course a left join tb_course_info b on a.courseId = b.id left join tb_member m1 on a.member = m1.id where a.planDate =? and a.member in(select id from tb_member m where m.role='E' and m.city= ?) order by a.hour_price asc limit 8",
						day3, currentCity));
		day1 = new SimpleDateFormat("MM月dd日").format(new Date());
		day2 = day2show;
		day3 = day3show;

		// 新增开通城市
		request.setAttribute("openCitys", service.findOpenCityForPinYin());
		request.setAttribute("currentCity", currentCity);
		Map map = service.findOpenCityForPinYin();

		return SUCCESS;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String onQuery() {
		String currentCity = (String) session.getAttribute("currentCity");
		typeList = service.findParametersByCodes("course_type_c");
		proTypeList = (List<Parameter>) service.findObjectBySql(
				" from Parameter p where p.parent = (select po.id from Parameter po where po.code = ?) order by p.id asc",
				"card_type_c");
		areaList = (List<Area>) service.findObjectBySql(
				"from Area a where a.parent in (select a1.id from Area a1 where a1.name = ? and a1.parent != null)",
				currentCity);
		pageInfo.setPageSize(5);
		request.setAttribute("factoryRecommends",
				service.findRecommendBySectorCode(DBConstant.RECOMM_SECTOR_SLIDES_J, currentCity));
		StringBuffer sql = new StringBuffer(Member.getMemberSql(null, null, null, null, null, null, currentCity));
		sql.append(" and m.role = 'E' ");
		// 关键字搜索
		if (keyword != null && !"".equals(keyword)) {
			final String where = "%" + keyword + "%";
			sql.append(" and (m.name like '").append(where).append("' or m.nick like '").append(where)
					.append("' or m.email like '").append(where).append("' or m.mobilephone like '").append(where)
					.append("')");
		}
		// 左上侧场馆预订
		if (typeId == null || "".equals(typeId)) {
			if (ltypeId != null && !"".equals(ltypeId)) {
				typeId = ltypeId;
				typeName = ltypeName;
			}
		}
		if (typeId != null && !"".equals(typeId)) {
			sql.append(" and (m.id in (select club from tb_member_factory where project =" + typeId
					+ " and applied = '1') or m.id in (select member from tb_course_info where type =" + typeId
					+ ")) ");
		}
		if (area != null && !"".equals(area)) {
			sql.append(" and m.county = '" + area + "'");
		}
		// 点击左上侧健身卡
		if (proType == null || "".equals(proType)) {
			if (lproType != null && !"".equals(lproType)) {
				proType = lproType;
				proTypeName = lproTypeName;
			}
		}
		if (proType != null && !"".equals(proType)) {
			sql.append(" and m.id in (select distinct member from tb_product where proType =" + proType + ") ");
		}
		if (changguangyuding != null && !"".equals(changguangyuding)) {
			if ("2".equals(changguangyuding)) {
				sql.append("and m.id not in (select distinct club from tb_member_factory)");
			} else {
				sql.append("and m.id in (select distinct club from tb_member_factory)");
			}
		}
		if (!"".equals(pageInfo.getOrder()) && !"".equals(pageInfo.getOrderFlag()) && pageInfo.getOrder() != null
				&& pageInfo.getOrderFlag() != null) {
			sql.append(" order by ");
			if ("endPublishTime".equals(pageInfo.getOrder())) {
				sql.append("endPublishTime ");
			} else if ("orderCount".equals(pageInfo.getOrder())) {
				sql.append("salesNum ");
			} else if ("countSocure".equals(pageInfo.getOrder())) {
				sql.append("member_grade ");
			} else {
				sql.append("birthday ");
			}
			if ("asc".equals(pageInfo.getOrderFlag())) {
				sql.append("asc");
			} else if ("desc".equals(pageInfo.getOrderFlag())) {
				sql.append("desc");
			} else {
				sql.append("desc ");
			}
		}
		final List<Object> parms = new ArrayList<Object>();
		pageInfo = service.findPageBySql(sql.toString(), pageInfo, parms.toArray());
		Random random = new Random();
		double score = 0;
		for (final Iterator<?> it = pageInfo.getItems().iterator(); it.hasNext();) {
			Map p = (Map) it.next();
			// if (p.get("id").equals(1493)) {
			// System.out.println(p.get("countEmp"));
			// }
			Long object = (Long) p.get("countEmp");
			if (null == object || object.equals(0l)) {
				int i = random.nextInt(10) + 1;
				p.put("countEmp", i);
				p.put("member_grade", score > 0 ? score - 5 : 50);
			} else {
				score = Double.valueOf(String.valueOf(p.get("member_grade")));
			}

		}
		if (pageInfo.getItems().size() > 0) {
			request.setAttribute("products", service.loadProductsByMember(pageInfo.getItems()));
			request.setAttribute("courseInfos", service.loadCourseInfosByMember(pageInfo.getItems()));
		}
		// bannerList = (List<Banner>)
		// service.findObjectBySql(" from Banner b where b.section.id = ? order
		// by b.id asc",
		// 80l);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		day1 = sdf.format(new Date());
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdfshow = new SimpleDateFormat("MM月dd日");
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date date = calendar.getTime();
		day2 = sdf.format(date);
		String day2show = sdfshow.format(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		date = calendar.getTime();
		String day3show = sdfshow.format(date);
		day3 = sdf.format(date);
		request.setAttribute("courseList1",
				service.queryForList(
						"select a.*,b.image,b.name,m1.name clubName from tb_course a left join tb_course_info b on a.courseId = b.id left join tb_member m1 on a.member = m1.id where a.planDate =? and a.member in(select id from tb_member m where m.role='E' and m.city= ?) and a.startTime > ? order by a.hour_price asc limit 8",
						day1, currentCity, new SimpleDateFormat("HH:mm").format(new Date())));
		request.setAttribute("courseList2",
				service.queryForList(
						"select a.*,b.image,b.name,m1.name clubName from tb_course a left join tb_course_info b on a.courseId = b.id left join tb_member m1 on a.member = m1.id where a.planDate =? and a.member in(select id from tb_member m where m.role='E' and m.city= ?) order by a.hour_price asc limit 8",
						day2, currentCity));
		request.setAttribute("courseList3",
				service.queryForList(
						"select a.*,b.image,b.name,m1.name clubName from tb_course a left join tb_course_info b on a.courseId = b.id left join tb_member m1 on a.member = m1.id where a.planDate =? and a.member in(select id from tb_member m where m.role='E' and m.city= ?) order by a.hour_price asc limit 8",
						day3, currentCity));
		day1 = new SimpleDateFormat("MM月dd日").format(new Date());
		day2 = day2show;
		day3 = day3show;
		request.setAttribute("area", area);
		request.setAttribute("typeName", typeName);
		request.setAttribute("proTypeName", proTypeName);
		request.setAttribute("changguangyuding", changguangyuding);
		return "list";
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String shoGo() {
		if (productId != null) {
			product = (Product) service.load(Product.class, productId);
			if (product != null) {
				// 健身卡简介，根据健身卡类型而定
				List<Map<String, Object>> parameterList = service.queryForList(
						"SELECT * FROM tb_parameter p WHERE p.parent = (SELECT id FROM tb_parameter WHERE CODE = 'card_type_c') and p.code = ?",
						product.getProType());
				if (parameterList != null && parameterList.size() > 0) {
					request.setAttribute("memo", parameterList.get(0).get("memo"));
				}
				// 获取评价信息
				StringBuffer appraiseSql = new StringBuffer("select * from (").append(BaseAppraise.getAppraiseInfo())
						.append(") t ");
				appraiseSql.append("where t.orderType = '1' AND t.productId = ").append(product.getId());
				List list = service.queryForList(appraiseSql.toString());
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					Map p = (Map) it.next();
					p.put("grade", p.get("grade") == null ? 0 : (int) (double) (p.get("grade")));
					p.put("avgGrade", p.get("avgGrade") == null ? 0 : (int) (double) (p.get("avgGrade")));
				}
				request.setAttribute("productAppraise", list);
				request.setAttribute("otherProducts",
						service.findObjectBySql(
								"from Product p where p.member.id =? and p.id!=? and p.isClose=? and p.audit=?",
								product.getMember().getId(), product.getId(), "2", '1'));
				// 作者综合评分以及服务人次
				StringBuffer memberSql = new StringBuffer("select * from ("
						+ Member.getMemberSql(null, null, null, null, null, null, product.getMember().getCity())
						+ ") t where t.id=?");
				List memberAppraises = service.queryForList(memberSql.toString(), product.getMember().getId());
				for (final Iterator<?> it = memberAppraises.iterator(); it.hasNext();) {
					Map p = (Map) it.next();
					p.put("member_grade", p.get("member_grade") == null ? 0 : (int) (double) (p.get("member_grade")));
				}
				request.setAttribute("memberAppraise", memberAppraises);
				if (null != product.getFreeProjects() && !"".equals(product.getFreeProjects())) {
					List<CourseInfo> cs = service.findCourseInfoByIds(product.getFreeProjects());
					request.setAttribute("freeProjects", StringUtils.listToString(cs, "Name"));
				}
				if (null != product.getCostProjects() && !"".equals(product.getCostProjects())) {
					List<CourseInfo> cs = service.findCourseInfoByIds(product.getCostProjects());
					request.setAttribute("costProjects", StringUtils.listToString(cs, "Name"));
				}
				if (null != product.getUseRange() && !"".equals(product.getUseRange())) {
					List<Member> ms = service.findMemberByIds(product.getUseRange());
					request.setAttribute("clubLists", StringUtils.listToString(ms, "Name"));
				}
				return "shop_go";
			} else {
				return null;
			}

		} else {
			return null;
		}
	}

	/**
	 * setter && getter
	 */
	public File getPicFile() {
		return picFile;
	}

	public void setPicFile(File picFile) {
		this.picFile = picFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public ProductGrade getProductGrade() {
		return productGrade;
	}

	public void setProductGrade(ProductGrade productGrade) {
		this.productGrade = productGrade;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getLtypeId() {
		return ltypeId;
	}

	public void setLtypeId(String ltypeId) {
		this.ltypeId = ltypeId;
	}

	public String getLproTypeName() {
		return lproTypeName;
	}

	public void setLproTypeName(String lproTypeName) {
		this.lproTypeName = lproTypeName;
	}

	public String getLtypeName() {
		return ltypeName;
	}

	public void setLtypeName(String ltypeName) {
		this.ltypeName = ltypeName;
	}

	public String getLproType() {
		return lproType;
	}

	public void setLproType(String lproType) {
		this.lproType = lproType;
	}

	public String getDay1() {
		return day1;
	}

	public void setDay1(String day1) {
		this.day1 = day1;
	}

	public String getDay2() {
		return day2;
	}

	public void setDay2(String day2) {
		this.day2 = day2;
	}

	public String getDay3() {
		return day3;
	}

	public void setDay3(String day3) {
		this.day3 = day3;
	}

	public List<Banner> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<Banner> bannerList) {
		this.bannerList = bannerList;
	}

	public String getProTypeName() {
		return proTypeName;
	}

	public void setProTypeName(String proTypeName) {
		this.proTypeName = proTypeName;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}

	public String getChangguangyuding() {
		return changguangyuding;
	}

	public void setChangguangyuding(String changguangyuding) {
		this.changguangyuding = changguangyuding;
	}

	public Character getQueryType() {
		return queryType;
	}

	public void setQueryType(Character queryType) {
		this.queryType = queryType;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	private List<Member> clubList;

	public List<Member> getClubList() {
		return clubList;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public List<Parameter> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Parameter> typeList) {
		this.typeList = typeList;
	}

	public List<Parameter> getProTypeList() {
		return proTypeList;
	}

	public void setProTypeList(List<Parameter> proTypeList) {
		this.proTypeList = proTypeList;
	}

	public void setClubList(List<Member> clubList) {
		this.clubList = clubList;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public List<Area> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<Area> areaList) {
		this.areaList = areaList;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
