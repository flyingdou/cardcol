package com.freegym.web.service;

import java.util.List;

import com.freegym.web.basic.Friend;
import com.freegym.web.basic.Member;
import com.freegym.web.course.Apply;
import com.freegym.web.course.Appraise;
import com.freegym.web.course.Message;
import com.freegym.web.factoryorder.FactoryApply;
import com.freegym.web.plan.Course;

public interface ICourseService extends IContentService {

	public Message saveOrUpdateMessage(Message msg);

	public void saveChangeIsCore(Friend friend);

	public void saveChangeMessageStatus(Message msg, Long[] ids);

	public void saveRelieve(String type, Member member, Friend friend);

	public Course updateCourse(Course cs, Integer saveType);

	public Course saveCourse(Course cs, Integer saveType);

	/**
	 * 保存申请的课程，需要将总人数进行记录。
	 * 
	 * @param course
	 *            ，当前被申请的课程
	 * @param member
	 *            ，当前申请会员
	 * @return 返回总的加入人数
	 */
	public Integer saveRequest(Course course, Member member);

	/**
	 * 删除课程，如果saveType (groupBy)大于0，则表示删除其批次生成的所有的课程
	 * 
	 * @param id
	 *            ，当前课程ID号
	 * @param saveType
	 *            ，批次号
	 */
	public void deleteCourse(final Long id, final Integer saveType);

	/**
	 * 修改预约状态，并更新课程表里面的数据
	 * 
	 * @param status
	 *            ，需要修改的状态
	 * @param applyList
	 *            ，预约列表
	 */
	public void saveApplyStatus(String status, List<Apply> applyList);

	/**
	 * 修改预约状态，并更新场地表里面的数据
	 * 
	 * @param status
	 *            ，需要修改的状态
	 * @param applyList
	 *            ，预约列表
	 */
	public void saveSiteApplyStatus(String status, List<FactoryApply> applyList, Member member);

	/**
	 * 撤销预约，并更新课程表里面的数据
	 * 
	 * @param ids
	 *            ，预约主键ID数组
	 * @param member
	 *            ，撤销预约的会员
	 * 
	 */
	public void deleteApplys(Long[] ids, Member member);

	/**
	 * 撤销预约，并更新场地表里面的数据
	 * 
	 * @param ids
	 *            ，预约主键ID数组
	 * @param member
	 *            ，撤销预约的会员
	 * 
	 */
	public void deleteSiteApplys(Long[] ids, Member member);

	/**
	 * 保存评价，并更新计算会员表中平均评分信息
	 * 
	 * @param appraise
	 *            评价对象
	 * @return Appraise对象
	 */
	public Appraise saveOrUpdateAppraise(Appraise appraise);

	/**
	 * 删除会员评分，同时将被评分的会员的评分相减
	 * 
	 * @param id
	 */
	public void deleteAppraise(Long id);

	/**
	 * 删除预约的课程数据。这里删除后必须将该团体课加入人数的数据进行减一 参数列表： courseId 为当前课程ID， memberId
	 * 为当前会员的ID
	 * 
	 * @param courseId
	 * @param memberId
	 */
	public void deleteApply(final Long courseId, final Long memberId);

}
