package com.cardcol.web.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.freegym.web.basic.Member;
import com.freegym.web.service.IWorkoutService;
import com.sanmen.web.core.common.PageInfo;

public interface IClub45Service extends IWorkoutService {

	PageInfo queryOneCardList(String city, PageInfo pageInfo);

	// PageInfo queryByPriceType(String city, String priceType, PageInfo
	// pageInfo);

	PageInfo queryProduct45Member(String city, PageInfo pageInfo, Double longitude, Double latitude, String id);

	PageInfo queryStore4EWX(String city, PageInfo pageInfo, Double longitude, Double latitude, String id);

	PageInfo queryStore(String city, PageInfo pageInfo, Double longitude, Double latitude, String id);

	PageInfo queryProduct45Member(PageInfo pageInfo, Double longitude, Double latitude, Object[] args);

	PageInfo listPageBySql(String sql, PageInfo pageInfo, Object[] args) throws SQLException;

	PageInfo queryPickDetail(PageInfo pageInfo, String id);

	PageInfo queryOrder(PageInfo pageInfo, long id, String status);
	
	PageInfo queryOrderInStatus(PageInfo pageInfo, long id, String status);

	PageInfo queryOrderByType(PageInfo pageInfo, long id, String status, String type);

	PageInfo queryOrder(PageInfo pageInfo, long id, String status, String dou);

	PageInfo queryOrder(PageInfo pageInfo, long id, String status, String dou, String hua);

	// PageInfo queryMember(PageInfo pageInfo, String id, String str);

	// Double returnCardPrice(String id);

	PageInfo queryCourse(PageInfo pageInfo, String date, Double longitude, Double latitude);

	PageInfo queryCourseByTime(PageInfo pageInfo, String date, Double longitude, Double latitude);

	Map<String, Object> queryMemberDetail(String id);

	PageInfo queryProduct45MemberEvaluate(PageInfo pageInfo, String id, String type);
	
	PageInfo queryEvaluateByDou(PageInfo pageInfo, String id, String type);

	PageInfo queryStoreEvaluate(PageInfo pageInfo, String id, String type, String queryType);

	PageInfo queryProduct45MemberEvaluate(PageInfo pageInfo, String id, String type, String queryType);

	PageInfo queryProduct45MemberEvaluate(PageInfo pageInfo, String id, String type, String queryType, String member);

	List<Map<String, Object>> queryProduct45MemberCourse(String id);

	Map<String, Object> queryCourseDetail(String id);
	
	Map<String, Object> queryCourseDetail2(String id);

	PageInfo income(PageInfo pageInfo, long id);

	Map<String, Object> queryActiveDetail(String aid, String id);

	Map<String, Object> queryOneCardOrderDetail(String id);

	PageInfo queryMySignIn(PageInfo pageInfo, String id);

	Map<String, Object> queryMemberEvaluateDetail(String id);

	Map<String, Object> querySignInfo(long id, String type);

	void saveActiveResult(Double weight, long id);

	Map<String, Object> queryClubEvaluateNum(String id);

	Map<String, Object> queryClubEvaluateNum(String id, String s);

	public Member login(final String mobile, final String password, Double lng, Double lat);

	public Member thirdLoginCheck(String thirdType, String thirdId, Double lng, Double lat);

	public Member thirdLoginCheck(String thirdType, String thirdId, Double lng, Double lat, String wx);

	Map<String, Object> queryPickAccount(String id);

	Map<String, Object> signShare(String id);

	public PageInfo queryClubById(PageInfo pageInfo, String id);
}
