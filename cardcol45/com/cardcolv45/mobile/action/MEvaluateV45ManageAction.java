package com.cardcolv45.mobile.action;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.aliyun.api.gateway.demo.enums.Constantsms;
import com.cardcol.web.basic.MemberEvaluate;
import com.cardcol.web.utils.DistanceUtil;
import com.databaseConnection.DataBaseConnection;
import com.freegym.web.basic.Member;
import com.freegym.web.course.SignIn;
import com.freegym.web.mobile.BasicJsonAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MEvaluateV45ManageAction extends BasicJsonAction implements Constantsms {

	private static final long serialVersionUID = 5005833803719101080L;

	private File image1, image2, image3;

	private String image1FileName, image2FileName, image3FileName;

	/**
	 * 会员评价教练{id:签到主表id,totailtyScore:总评分,evenScore:环境分,deviceScore:设备分,
	 * serviceScore:服务分,content:评价内容，evalTime:评价时间，image:图片1，image1FileName:
	 * 图片1名字，image2:图片2，image2FileName:图片2名字，image3:图片3image3FileName:图片3名字， }";
	 */
	public void evaluate() {
		// 模拟的测试数据
//		 jsons = new JSONObject().accumulate("id", 263)//教练 “张莉”
//		 .accumulate("totailtyScore", 99).accumulate("evenScore", 5)
//		 .accumulate("deviceScore", 5).accumulate("serviceScore", 5)
//		 .accumulate("content", "服务态度Nice，专业水平高！").toString();

		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			JSONArray arr = JSONArray.fromObject(URLDecoder.decode(jsons, "UTF-8"));
			JSONObject obj = (JSONObject) arr.get(0);
			
			MemberEvaluate me = new MemberEvaluate();
			me.setEvalTime(new Date());// 评价时间
			me.setDeviceScore(obj.getInt("deviceScore"));// 设备分------------专业能力
			me.setEvenScore(obj.getInt("evenScore"));// 环境分----------------服务态度
			me.setServiceScore(obj.getInt("serviceScore"));// 服务分----------教学方法
			me.setTotailtyScore(obj.getInt("totailtyScore"));// 评价总分------评价总分
			me.setMember(new Member(getMobileUser().getId()));// 签到方(签到订单id)--评价者
			SignIn signIn = new SignIn();
			signIn.setMemberSign((Member) getMobileUser());
			Member memberAudit = (Member) service.load(Member.class, obj.getLong("id"));
			signIn.setMemberAudit(memberAudit);
			signIn = (SignIn) service.saveOrUpdate(signIn);
			me.setSignIn(signIn);// 被签到方-----------------被评价者

			// String content=
			// DistanceUtil.filterEmoji(obj.getString("content"));
			String content = DistanceUtil.encodeToNonLossyAscii(obj.getString("content"));// 给评价内容加密
			me.setContent(content);// 评价内容
			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName , null) : null;
			String fileName2 = image2 != null ? saveFile("picture", image2, image2FileName, null) : null;
			String fileName3 = image3 != null ? saveFile("picture", image3, image3FileName, null) : null;
			if (fileName1 != null) {
				me.setImage1(fileName1);
			}
			if (fileName2 != null) {
				me.setImage2(fileName2);
			}
			if (fileName3 != null) {
				me.setImage3(fileName3);
			}
			me = (MemberEvaluate) service.saveOrUpdate(me);// 将评价持久化到数据库，并返回持久化后的数据实体（评价实体me）
			me.setContent(DistanceUtil.decodeFromNonLossyAscii(me.getContent()));
			long count = service.queryForLong("select count(*) from tb_sign_in s  where  s.memberSign= ?",
					getMobileUser().getId());
			final JSONObject ob = new JSONObject();
			ob.accumulate("success", true).accumulate("key", me.getId()).accumulate("signIn", obj.getLong("id"))
					.accumulate("evaltime", sdf.format(me.getEvalTime())).accumulate("count", count);
			response(ob);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * 删除点评(传入参数，需要删除的点评的id)
	 */
	public void deleteEvaluate() {
		try {
			String mid = request.getParameter("mid");
			String mmid = getMobileUser().getId().toString();
			final JSONObject ret = new JSONObject();
			if(mmid.equals(mid)){
				//删除回复
				String sql = "delete from TB_MEMBER_EVALUATE_REPLY where evaluate = " + id;
				DataBaseConnection.updateData(sql, null);
				
				//删除点赞
				String sqlx = "delete from tb_member_evaluate_praise where evaluate = " + id;
				DataBaseConnection.updateData(sqlx, null);
				
				//删除评论
				service.delete(MemberEvaluate.class, id);
				ret.accumulate("success", true).accumulate("message", "OK");
				response(ret);
			}else{
				ret.accumulate("success", false).accumulate("message", "您不是这条评论的创建者，无权限删除该评论");
				response(ret);
			}
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

	/**
	 * getter and setter
	 */
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

}
