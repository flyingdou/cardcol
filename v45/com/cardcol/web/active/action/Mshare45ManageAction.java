package com.cardcol.web.active.action;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.cardcol.web.basic.MemberEvaluate;
import com.cardcol.web.basic.Product45;
import com.cardcol.web.utils.DistanceUtil;
import com.freegym.web.active.Active;
import com.freegym.web.course.BaseAppraise;
import com.freegym.web.mobile.BasicJsonAction;
import com.freegym.web.order.Goods;
import com.freegym.web.plan.Workout;
import com.freegym.web.plan.WorkoutDetail;
import com.opensymphony.xwork2.ActionContext;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({@Result(name="planrelease",location="/share45/myPlanRelease.jsp"),
	@Result(name="courseshare",location="/share45/courseshare45.jsp"),
	@Result(name="productshare",location="/share45/productshare45.jsp"),
	@Result(name="signshare1",location="/share45/signshare1.jsp"),
	@Result(name="signshare",location="/share45/signshare.jsp"),
@Result(name="goodshare",location="/share45/goodshare45.jsp"),
@Result(name="evaluateshare",location="/share45/evaluateShare.jsp")})
public class Mshare45ManageAction extends BasicJsonAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3897486386552369705L;
	
	@SuppressWarnings("unused")
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@SuppressWarnings("unchecked")
	Map<String, Object> request1 = (Map<String, Object>) ActionContext.getContext().get("request");
	
	public void list() {
		/*
		 * 挑战分享
		 */
			Active a = (Active) service.load(Active.class, id);
		if(a==null){
			request1.put("a", a);
		}
		else{
		request1.put("a", a);
		request1.put("addornot", a.getCategory().equals('A') ? "体重增加" :a.getCategory().equals('B')?"体重减少":a.getCategory().equals('E')?"运动": a.getCategory().equals('D')?"健身次数":"");
		request1.put("sport", a.getCategory().equals('A') ? "公斤" :a.getCategory().equals('B')?"公斤":a.getCategory().equals('E')?"小时": a.getCategory().equals('D')?"次":"");
		}
		Long count = service.queryForLong("select count(*) from tb_active_order ci where ci.active = ? and ci.status != '0' ", id);
		request1.put("size", count);
		try {
			request.getRequestDispatcher("share45/activeshare.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
	
	/**
	 * 淘课分享
	 */
		public String courseShare(){
			String id = request.getParameter("id");
			Map<String, Object> map = service.queryCourseDetail(id);
			System.out.println(map);
			request1.put("map", map);
			return "courseshare";
		}
		
		/**
		 * 智能计划分享
		 */
		public String goodsShare() {
			Goods g = (Goods) service.load(Goods.class, id);
			if(null==g){
				request1.put("g", g);
			}
			else
			{
				StringBuffer appraiseSql = new StringBuffer("select distinct cnt,avgGrade from (").append(BaseAppraise.getAppraiseInfo()).append(") t ");
				appraiseSql.append("where t.orderType = ").append("6").append(" AND t.productId = ").append(id);
				List<Map<String, Object>> list=service.queryForList(appraiseSql.toString());
				for (final Map<String, Object> map : list) {
					request1.put("countperson", null == map.get("cnt") || map.size() == 0 ? 0 : map.get("cnt"));
					request1.put("avgGrade",
							null == map.get("avgGrade") || map.size() == 0 ? 0 : map.get("avgGrade"));
				}
			request.setAttribute("planAppraise", list);
			request1.put("p", g);
			String s1=g.getPlanType();
			String[] S1=s1.split(",");
			StringBuffer sb1=new StringBuffer();
			for(int i=0;i<S1.length;i++){
				if(S1[i].equals("A")){
					S1[i]="瘦身减重";
				}
				else if(S1[i].equals("B")){
					S1[i]="健美增肌";
				}
				else if(S1[i].equals("C")){
					S1[i]="运动康复";
				}
				else if(S1[i].equals("D")){
					S1[i]="提高运动表现";
				}
				sb1.append(S1[i]).append(",");
			}
			String SB1=sb1.toString().substring(0, sb1.length()-1);
			request1.put("gtype", SB1);
			String s2=g.getApplyObject();
			String[] S2=s2.split(",");
			StringBuffer sb2=new StringBuffer();
			for(int i=0;i<S2.length;i++){
				if(S2[i].equals("A")){
					S2[i]="初级";
				}
				else if(S2[i].equals("B")){
					S2[i]="中级";
				}
				else if(S2[i].equals("C")){
					S2[i]="高级";
				}
				sb2.append(S2[i]).append(",");
			}
			String SB2=sb2.toString().substring(0, sb2.length()-1);
			request1.put("gapply", SB2);
			String s3=g.getScene();
			String[] S3=s3.split(",");
			StringBuffer sb3=new StringBuffer();
			for(int i=0;i<S3.length;i++){
				if(S3[i].equals("A")){
					S3[i]="健身房";
				}
				else if(S3[i].equals("B")){
					S3[i]="办公室";
				}
				else if(S3[i].equals("C")){
					S3[i]="家庭";
				}
				else if(S3[i].equals("D")){
					S3[i]="户外";
				}
				sb3.append(S3[i]).append(",");
			}
			String SB3=sb3.toString().substring(0, sb3.length()-1);
			request1.put("gscene", SB3);
			request1.put("author",g.getType()=="1"?"卡库":"王严讲健身");
			}
			return "goodshare";
		}
		
		/**
		 * 我的计划分享
		 */
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public String  myPlanRelease() {
				final List<?> list = service.findObjectBySql("from Workout wo where wo.course.id = ? order by wo.sort", id);
				long sum = service.queryForLong("SELECT SUM(planDuration) from tb_workout_detail d where d.workout in (SELECT id FROM tb_workout w where w.course = ?)",id);
				final JSONObject ret = new JSONObject();
				final JSONArray jarr = new JSONArray();
				for (final Iterator<?> it = list.iterator(); it.hasNext();) {
					final Workout wo = (Workout) it.next();
					final JSONObject obj = new JSONObject(), actObj = new JSONObject();
					actObj.accumulate("name", wo.getAction().getName()).accumulate("image", getString(wo.getAction().getImage()));
					final List<?> detailList = service.findObjectBySql("from WorkoutDetail wo where wo.workout.id = ?", wo.getId());
					StringBuilder str = new StringBuilder();
					if (detailList.size() > 0) {
						List planTimesList = new ArrayList<>();
						List planWeightList = new ArrayList<>();
						boolean planTimesIsnumber = false;
						boolean planWeightIsnumber = false;
						for (final Iterator<?> it2 = detailList.iterator(); it2.hasNext();) {
							WorkoutDetail detail = (WorkoutDetail) it2.next();
							if (!isNumeric(detail.getPlanTimes() == null ? "0" : detail.getPlanTimes())) {
								planTimesIsnumber = true;
								planTimesList.add(detail.getPlanTimes());
							} else {
								planTimesList.add(toInt(detail.getPlanTimes()));
							}

							if (!isNumeric(detail.getPlanWeight() == null ? "0" : detail.getPlanWeight())) {
								planWeightIsnumber = true;
								planWeightList.add(detail.getPlanWeight());
							} else {
								planWeightList.add(toDouble(detail.getPlanWeight()));
							}
						}
						str.append(detailList.size() + "组,");
						if (planTimesIsnumber) {
							str.append("每组" + planTimesList.get(0) + ",");
						} else {
							Collections.sort(planTimesList);
							if (planTimesList.get(0) == planTimesList.get(planTimesList.size() - 1)) {
								str.append("每组" + planTimesList.get(0) + "次,");
							} else {
								str.append("每组" + planTimesList.get(0) + "-" + planTimesList.get(planTimesList.size() - 1) + "次,");
							}
						}
						if (planWeightIsnumber) {
							str.append("重量" + planWeightList.get(0));
						} else {
							Collections.sort(planWeightList);
							if (planWeightList.get(0) == planWeightList.get(planWeightList.size() - 1)) {
								str.append("重量" + planWeightList.get(0) + "公斤");
							} else {
								str.append("重量" + planWeightList.get(0) + "-" + planWeightList.get(planWeightList.size() - 1) + "公斤");
							}
						}
					}
					obj.accumulate("workoutId", wo.getId()).accumulate("mode", wo.getAction().getPart().getProject().getMode()).accumulate("action", actObj).accumulate("detail",
							str.toString());
					jarr.add(obj);
				}
				ret.accumulate("items", jarr).accumulate("sumTime", sum);
				request.setAttribute("map", ret);
			return "planrelease";
		}
		
		
		/**
		 * 签到分享
		 */
		public String signShare(){
			String sid = request.getParameter("id");
			Map<String, Object> map = service.signShare(sid);
			Long count = service.queryForLong("SELECT count(*) FROM tb_sign_in where memberSign=?",map.get("id"));
			request1.put("map", map);
			if(count == 1 ){
				return "signshare";
			}else{
				request1.put("count",count);
				return "signshare1";
			}
		}
		
		/**
		 * 健身卡分享
		 */
		public String product45Share(){
			Product45 p = (Product45) service.load(Product45.class,id);
			Long count = service.queryForLong("SELECT count(*) FROM tb_product_club_v45 WHERE PRODUCT=?",p.getId());
			Long payCount = service.queryForLong("SELECT count(*) FROM tb_product_order_v45 where `status`=1 AND ORDER_PRODUCT =?",p.getId());
			request1.put("product", p);
			request1.put("count",count);
			request1.put("payCount",payCount);
			return "productshare";
		}
		
	/**
	 * 评论分享
	 */
		public String evaluate(){
			MemberEvaluate me = (MemberEvaluate) service.load(MemberEvaluate.class, id);
			Map<String, Object> map = service.queryForMap("SELECT mc.name as cname, mc.address, mc.image as image1 ,m.`name` as name,m.image AS image2 ,m.id  from tb_sign_in s LEFT JOIN tb_member mc on mc.id = s.memberAudit LEFT JOIN tb_member m ON m.id= s.memberSign  where s.id = ?", me.getSignIn().getId());
			Long count = service.queryForLong("SELECT count(*) FROM tb_sign_in where memberSign=? ",map.get("id"));
			request1.put("map", map);
			request1.put("me",me);
			request.setAttribute("me", me);
			request1.put("count",count);
			request1.put("content",DistanceUtil.decodeFromNonLossyAscii(me.getContent()));
			return "evaluateshare";
	}
}