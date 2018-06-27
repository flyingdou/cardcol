package com.freegym.web.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.freegym.web.basic.Member;
import com.freegym.web.config.Action;
import com.freegym.web.config.Factory;
import com.freegym.web.config.FactoryCosts;
import com.freegym.web.config.Part;
import com.freegym.web.plan.Course;
import com.freegym.web.plan.Diet;
import com.freegym.web.plan.Workout;
import com.freegym.web.system.Ticket;
import com.sanmen.web.core.system.Parameter;

public interface IWorkoutService extends IOrderService {

	/**
	 * 查找planDate时间所在月的计划状态
	 * 
	 * @param member
	 * @param planDate
	 * @param isDiet是否包含计划
	 * @return
	 * @throws ParseException
	 * 2015/1/15将isDiet类型改成String wh0708006
	 */
	String findMonthPlanStatus(Long member, String planDate, String isDiet) throws ParseException;

	/**
	 * 依据项目主键查找所有部位(parts)
	 * 
	 * @param projectId
	 * @param l 
	 * @return
	 */
	List<Part> findPartsByProjects(final Long projectId, final Long coachId);

	/**
	 * 依据项目主键号和部位主鍵查找所有动作
	 * 
	 * @param projectId
	 * @return
	 */
	List<Action> findActionsByProjectAndPart(final Long projectId,final Long partId, Member member);

	/**
	 * 查找当前用户当前天的所有饮食计划
	 * 
	 * @param member
	 * @param planDate
	 * @return
	 */
	List<Diet> findDietByUserAndDate(final Long member, final String planDate);

	/**
	 * 删除饮食计划
	 * 
	 * @param id
	 * @param ids
	 */
	void deleteDiet(final Long id, final Long[] ids);

	/**
	 * 复制饮食计划到startDate, endDate之间的所有weeks中
	 * 
	 * @param course
	 * @param toMember
	 * @param startDate
	 * @param endDate
	 * @param weeks
	 * @throws ParseException
	 */
	void copy(Course course, Long toMember, String startDate, String endDate, Integer[] weeks) throws ParseException;

	/**
	 * 复制当前饮食计划到指定的会员的指定日期内
	 * 
	 * @param diet
	 * @param startDate
	 * @param endDate
	 * @param weeks
	 */
	void copy(final Diet diet, final Long toMember, final String sDate, final String eDate, final Integer[] weeks) throws ParseException;

	/**
	 * 保存健身计划
	 * 
	 * @param course
	 * @param workouts
	 */
	Course saveWorkout(final Course course, final List<Workout> workouts);

	/**
	 * 获取当前用户的运动量
	 * 
	 * @param id
	 * @return
	 */
	Double findWeightByMember(final Long id);

	/**
	 * 获取当前用户某天的运动量
	 * @param id
	 * @param doneDate
	 * @return
	 */
	Double findWeightByMember(Long id, Date doneDate);

	/**
	 * 查找当前活动的团队。已满员的不算
	 * @param id
	 * @return
	 */
	List<Map<String, Object>> findTeamByActive(Long id);

	/**
	 * 保存私教预约课程，需要判断该私教当前时间是否有其它课程
	 * @param course
	 * @return
	 */
	Course saveAppointment(Course course);

	/**
	 * 查找toMember的所有有效运动项目
	 * @param toMember
	 * @return
	 */
	List<Parameter> findProjectForValid(Long toMember);

	/**
	 * 查找toMember的所有场地预约数据
	 * @param toMember
	 * @param projectId 
	 * @param planDate
	 * @return
	 */
	List<FactoryCosts> findFactoryCostsByMember(Long toMember, Long projectId, String planDate);

	/**
	 * 查找未读消息总数
	 * @param toMember
	 * @return 数量
	 */
	Integer findMessageCount(Long toMember);

	/**
	 * 加载指定栏目的所有广告图片
	 * @param sectorId
	 * @return
	 */
	List<?> findBannerBySectorId(Long sectorId);

	/**
	 * 获取当前用户指定项目下所有有效运动场地
	 * @param member projectId
	 * @return
	 */
	List<Factory> findFactoryByMember(Long member,String projectId);

	/**
	 * 依据激活码查找对应的优惠券
	 * @param activeCode
	 * @return
	 */
	Ticket findTicketByActiveCode(String activeCode);

	List<?> findMemberTicketByCode(String activeCode, Long id);
	
	List<Map<String, Object>> findRecommendBySectorCode(String[] code);
}
