package com.freegym.web.course;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.freegym.web.plan.Course;

@Entity
@Table(name = "TB_COURSE_APPLY")
public class Apply extends BaseApply {

	private static final long serialVersionUID = 7563792954778825178L;

	@ManyToOne(targetEntity = Course.class)
	@JoinColumn(name = "course")
	private Course course;// 申请课程

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

//	public static String getApplySql(Apply query) {
//		final StringBuffer sql = new StringBuffer();
//		sql.append("SELECT a.id AS applyid,'1' AS applyType, a.applyDate,a.approveDate,a.isReadBuy,a.isReadSell,a.status,a.course AS projectid,a.member AS memberid,b.place, b.planDate, b.startTime ,b.endTime, c.name AS projectname, d.name AS membername,c.member AS clubid FROM TB_COURSE_APPLY a  LEFT JOIN tb_course b ON a.course = b.id LEFT JOIN TB_COURSE_INFO c ON b.courseId = c.id LEFT JOIN tb_member d ON a.member = d.id");
//		if(query!=null){
//			if (query.getMember() != null) {
//				if (query.getMember().getRole().equals("S")) {
//					if(query.getStatus()!=null){
//						if(!query.getStatus().equals("1")){
//							sql.append(" UNION ALL ");
//							sql.append("SELECT a.id AS applyid,'2' AS applyType, a.applyDate,a.approveDate,a.isReadBuy,a.isReadSell,a.status,a.factorycosts AS projectid,a.member AS memberid,c.name AS place,a.place_date AS planDate , a.start_time AS startTime, a.end_time AS endTime, d.name AS projectname, e.name AS membername,c.club AS clubid FROM TB_FACTORY_APPLY a LEFT JOIN TB_MEMBER_FACTORY_COSTS b ON a.factorycosts = b.id LEFT JOIN TB_MEMBER_FACTORY c ON b.factory = c.id LEFT JOIN tb_parameter d ON c.project = d.id LEFT JOIN tb_member e ON a.member = e.id");
//						}
//					}
//				}else{
//					sql.append(" UNION ALL ");
//					sql.append("SELECT a.id AS applyid,'2' AS applyType, a.applyDate,a.approveDate,a.isReadBuy,a.isReadSell,a.status,a.factorycosts AS projectid,a.member AS memberid,c.name AS place,a.place_date AS planDate , a.start_time AS startTime, a.end_time AS endTime, d.name AS projectname, e.name AS membername,c.club AS clubid FROM TB_FACTORY_APPLY a LEFT JOIN TB_MEMBER_FACTORY_COSTS b ON a.factorycosts = b.id LEFT JOIN TB_MEMBER_FACTORY c ON b.factory = c.id LEFT JOIN tb_parameter d ON c.project = d.id LEFT JOIN tb_member e ON a.member = e.id");
//				}
//			}
//		}
//		return sql.toString();
//	} 
	
	@Override
	public String getTableName() {
		return "";
	}

}
