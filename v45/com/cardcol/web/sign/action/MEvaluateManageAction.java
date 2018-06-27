package com.cardcol.web.sign.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.cardcol.web.basic.MemberEvaluate;
import com.cardcol.web.utils.DistanceUtil;
import com.freegym.web.basic.Member;
import com.freegym.web.course.SignIn;
import com.freegym.web.mobile.BasicJsonAction;

import net.sf.json.JSONObject;
@Namespace("")
@InterceptorRefs({ @InterceptorRef("mobileStack") })
public class MEvaluateManageAction extends BasicJsonAction{
	private File image1, image2, image3;

	private String image1FileName, image2FileName, image3FileName;
	
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

	/**
	 *
	 */
	private static final long serialVersionUID = 5005833803719101080L;
	/**
	 *一卡通评价{id:签到主表id,totailtyScore:总评分,evenScore:环境分,deviceScore:设备分,serviceScore:服务分,content:评价内容，evalTime:评价时间，image:图片1，image1FileName:图片1名字，image2:图片2，image2FileName:图片2名字，image3:图片3image3FileName:图片3名字，
	 *}";
	 */
	public void evaluate(){
		//jsons = "{\"id\":9,\"totailtyScore\":98,\"evenScore\":5,\"deviceScore\":5,\"serviceScore\":5,\"content\":'评价内容'}";
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm");
			JSONObject obj = JSONObject.fromObject(jsons);
			MemberEvaluate me = new MemberEvaluate();
			me.setDeviceScore(obj.getInt("deviceScore"));//设备分
			me.setEvalTime(new Date());//评价时间
			me.setEvenScore(obj.getInt("evenScore"));//环境分
			me.setMember(new Member(getMobileUser().getId()));//签到方(签到订单id)	
			me.setServiceScore(obj.getInt("serviceScore"));//服务分
			me.setSignIn(new SignIn(obj.getLong("id")));//被签到方
			me.setTotailtyScore(obj.getInt("totailtyScore"));//评价总分
			//String content= DistanceUtil.filterEmoji(obj.getString("content"));
			String content=DistanceUtil.encodeToNonLossyAscii(obj.getString("content"));
			me.setContent(content);
			String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
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
			me = (MemberEvaluate) service.saveOrUpdate(me);
			me.setContent(DistanceUtil.decodeFromNonLossyAscii(me.getContent()));
			long count = service.queryForLong("select count(*) from tb_sign_in s  where  s.memberSign= ?",getMobileUser().getId());
			final JSONObject ob = new JSONObject();
			ob.accumulate("success", true).accumulate("id", me.getId()).accumulate("evaltime", sdf.format(me.getEvalTime())).accumulate("count", count);
			response(ob);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}
	
	/**
	 * 删除点评(传入参数 id)
	 */
	public void deleteEvaluate() {
		try {
			service.delete(MemberEvaluate.class, id);
			final JSONObject ret = new JSONObject();
			ret.accumulate("success", true).accumulate("message", "OK");
			response(ret);
		} catch (Exception e) {
			log.error("error", e);
			response(e);
		}
	}

}
