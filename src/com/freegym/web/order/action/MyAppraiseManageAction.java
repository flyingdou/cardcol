package com.freegym.web.order.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Appraise;
import com.freegym.web.course.BaseAppraise;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "indexpage", location = "/order/index.jsp"), @Result(name = "myappraise", location = "/order/my_appraise.jsp"),
		@Result(name = "loadpicture", location = "/home/loadpic.jsp"), @Result(name = "loadpic", location = "/home/loadpic.jsp") })
public class MyAppraiseManageAction extends BaseBasicAction {

	private static final long serialVersionUID = -3429475961884283745L;
	private File image1, image2, image3;
	private String image1FileName, image2FileName, image3FileName;
	private String title;
	private String content;
	private Double grade;
	private String flag;
	private String index;
	private String gradewidth;
	private String appraiseId;
	private String orderId;
	private String orderType;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getGradewidth() {
		return gradewidth;
	}

	public void setGradewidth(String gradewidth) {
		this.gradewidth = gradewidth;
	}

	public String getAppraiseId() {
		return appraiseId;
	}

	public void setAppraiseId(String appraiseId) {
		this.appraiseId = appraiseId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public File getImage1() {
		return image1;
	}

	public void setImage1(File image1) {
		this.image1 = image1;
	}

	public File getImage2() {
		return image2;
	}

	public void setImage2(File image2) {
		this.image2 = image2;
	}

	public File getImage3() {
		return image3;
	}

	public void setImage3(File image3) {
		this.image3 = image3;
	}

	public String getImage1FileName() {
		return image1FileName;
	}

	public void setImage1FileName(String image1FileName) {
		this.image1FileName = image1FileName;
	}

	public String getImage2FileName() {
		return image2FileName;
	}

	public void setImage2FileName(String image2FileName) {
		this.image2FileName = image2FileName;
	}

	public String getImage3FileName() {
		return image3FileName;
	}

	public void setImage3FileName(String image3FileName) {
		this.image3FileName = image3FileName;
	}

	public String queryAppraise() {
		Member member = (Member) session.getAttribute(LOGIN_MEMBER);
		Long memberId = member.getId();

		final List<Object> params = new ArrayList<Object>();
		StringBuilder appraiseSql = new StringBuilder("select * from (").append(BaseAppraise.getMyAppraiseSql());
		// 查询已付款的订单
		appraiseSql.append(" ) t where t.status = '1' and t.fromId = ");
		appraiseSql.append(memberId);
		// 待评价
		if (flag != null) {
			if (flag.equals("1")) {
				appraiseSql.append(" and t.isappraise = '0' ");
			}
			// 已评价
			if (flag.equals("2")) {
				appraiseSql.append(" and t.isappraise = '1' ");
			}
		}
		appraiseSql.append(" order by t.orderdate desc ");
		pageInfo.setPageSize(5);
		pageInfo = service.findPageBySql(appraiseSql.toString(), pageInfo, params.toArray());

		return "myappraise";
	}

	/**
	 * 保存评价，六种订单都写到Appraise表中，以orderId和orderType区分
	 * 
	 * @return
	 */
	public void saveAppraise() {
		Appraise appraise = new Appraise();
		// 上传图片信息
		String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
		String fileName2 = image2 != null ? saveFile("picture", image2, image2FileName, null) : null;
		String fileName3 = image3 != null ? saveFile("picture", image3, image3FileName, null) : null;
		if (fileName1 != null) {
			appraise.setImage1(fileName1);
		}
		if (fileName2 != null) {
			appraise.setImage2(fileName2);
		}
		if (fileName3 != null) {
			appraise.setImage3(fileName3);
		}
		appraise.setOrderId(Long.valueOf(orderId));
		appraise.setOrderType(orderType);
		appraise.setGrade(grade);
		appraise.setTitle(title);
		appraise.setContent(content);
		appraise.setAppDate(new Date());
		appraise.setMember(this.toMember());
		service.saveOrUpdate(appraise);
		response("OK");
	}

	// 删除评价
	public void deleteAppraise() {
		service.delete(Appraise.class, Long.valueOf(appraiseId));
		response("OK");
	}

}
