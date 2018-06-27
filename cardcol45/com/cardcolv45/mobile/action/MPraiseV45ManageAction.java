package com.cardcolv45.mobile.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.cardcol.web.basic.EvaluatePraise;
import com.cardcol.web.basic.MemberEvaluate;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.mobile.BasicJsonAction;
import com.sanmen.web.core.common.LogicException;

import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MPraiseV45ManageAction extends BasicJsonAction implements Constantsms {

	/**
	 * 
	 */
	private static final long serialVersionUID = -818206225532612480L;
	
	//创建全局变量
	private MemberEvaluate evaluate;
	private Member member;
	
	
	/**
	 * 点赞
	 */
	public void addPraise(){
		try {
			long evaluateId = Long.parseLong(request.getParameter("evaluateId"));
			evaluate = (MemberEvaluate) service.load(MemberEvaluate.class, evaluateId);//加载被点赞的评论实体
			member = (Member) getMobileUser();//加载当前用户，作为点赞人
			
			EvaluatePraise praise = new EvaluatePraise();//创建点赞实体
			praise.setEvaluate(evaluate);//被点赞评论
			praise.setMember(member);//点赞人
			praise.setTime(new Date());//点赞时间
			praise = (EvaluatePraise) service.saveOrUpdate(praise);//持久化点赞实体，并返回持久化后的数据（点赞实体）
			
			JSONObject ret = new JSONObject();
			if(praise!=null){//点赞成功
				ret.accumulate("success", true)
				.accumulate("message", "OK")
				.accumulate("key", praise.getId())//返回当前业务的id
				;
				response(ret);
			}else{//点赞异常
				ret.accumulate("success", false)
				.accumulate("message", "点赞异常")
				;
				response(ret);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (LogicException e) {
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 取消点赞（通过传入点赞ID，实现删除点赞记录）by dou 2017-08-24
	 */
	public void deletePraise(){
	   try {
		   long evaluate = Long.parseLong(request.getParameter("evaluateId"));
		   String sql = "delete from TB_MEMBER_EVALUATE_PRAISE where member = ? and evaluate = ? ";
		   Object [] obj = {getMobileUser().getId(), evaluate};
		   int i = DataBaseConnection.updateData(sql, obj);
		   
		   JSONObject ret = new JSONObject();
		   if(i > 0){
			   ret.accumulate("success", true)
			   .accumulate("message", "OK")
			   ;
			   response(ret);
		   }else{
			   ret.accumulate("success", false)
			   .accumulate("message", "取消点赞异常")
			   ;
			   response(ret);
		   }
	} catch (NumberFormatException e) {
		e.printStackTrace();
	}
		
	}
	
	
	/**
	 * 获取（某一评论被）点赞的次数
	 */
	public void getPraiseCount(){
		try {
			long evaluateId = Long.parseLong(request.getParameter("evaluateId"));
			String sql = "select count(*) count from TB_MEMBER_EVALUATE_PRAISE where evaluate = ?";
			Object[] obj = {evaluateId};
			List<Map<String, Object>> list = DataBaseConnection.getList(sql, obj);
			int count = 0;
			JSONObject ret = new JSONObject();
			//查到了数据
			if(list.size()>0){
				count = Integer.valueOf(String.valueOf(Long.parseLong(list.get(0).get("count").toString())));
				ret.accumulate("success", true)
				.accumulate("message", "OK")
				.accumulate("praiseCount", count)
				;
				response(ret);
			}else{//没有查到数据
				ret.accumulate("success", false)
				.accumulate("message", "获取点赞数异常")
				;
				response(ret);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	 * getter and setter
	 */
	public MemberEvaluate getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(MemberEvaluate evaluate) {
		this.evaluate = evaluate;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	
	

}
