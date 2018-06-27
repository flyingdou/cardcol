package com.freegym.web.plan.action;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.WorkoutBasicAction;
import com.freegym.web.basic.Member;
import com.freegym.web.plan.PlanRelease;
import com.sanmen.web.core.common.LogicException;
import com.sanmen.web.core.utils.FileUtil;
import com.sanmen.web.core.utils.StringUtils;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/plan/release.jsp"), @Result(name = "workout", type = "redirect", location = "workout.asp") })
public class ReleaseManageAction extends WorkoutBasicAction {

	private static final long serialVersionUID = 5439537428568798578L;

	private PlanRelease release;

	private File image1, image2, image3;

	private String image1FileName, image2FileName, image3FileName;

	private String image1ContentType, image2ContentType, image3ContentType;

	private int imageIndex;

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

	public String getImage1ContentType() {
		return image1ContentType;
	}

	public void setImage1ContentType(String image1ContentType) {
		this.image1ContentType = image1ContentType;
	}

	public String getImage2ContentType() {
		return image2ContentType;
	}

	public void setImage2ContentType(String image2ContentType) {
		this.image2ContentType = image2ContentType;
	}

	public String getImage3ContentType() {
		return image3ContentType;
	}

	public void setImage3ContentType(String image3ContentType) {
		this.image3ContentType = image3ContentType;
	}

	public int getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	public PlanRelease getRelease() {
		return release;
	}

	public void setRelease(PlanRelease release) {
		this.release = release;
	}

	public String execute() {
		Member m = toMember();
		Long coachId = null;
		List<?> members = null;
		if (m.getRole().equals("S")) {// 存教练信息
			coachId = m.getId();
			members = service.findObjectBySql("from Member m where m.coach.id = ? or m.id = ?", new Object[] { coachId, coachId });
			request.setAttribute("members", members);
		} 
		if (release != null && release.getId() != null) {
			release = (PlanRelease) service.load(PlanRelease.class, release.getId());
			request.setAttribute("scenes", StringUtils.stringToList(release.getScene()));
		}
		return SUCCESS;
	}

	public String executeSave() {
		// 上传图片信息
		if (image1 != null && image1 != null) {
			boolean isImage = FileUtil.isFile(image1,
					"png,jpg,gif,");
			if (!isImage)
				throw new LogicException("非正常图片格式，请确认！");
			try {
				FileUtil.addWaterMark(
						image1,
						image1FileName.substring(
								image1FileName.lastIndexOf(".") + 1),
						webPath + "/images/watermark.png", -1, -1, 0.5f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		String fileName1 = image1 != null ? saveFile("picture", image1, image1FileName, null) : null;
		//String fileName2 = image2 != null ? saveFile("picture", image2, image2FileName, null) : null;
		//String fileName3 = image3 != null ? saveFile("picture", image3, image3FileName, null) : null;
		if (fileName1 != null) {
			release.setImage1(fileName1);
		}
		/*if (fileName2 != null) {
			release.setImage2(fileName2);
		}
		if (fileName3 != null) {
			release.setImage3(fileName3);
		}*/
		release.setScene(release.getScene().replaceAll(" ", ""));
		release.setAudit("1");
		release.setIsClose("2");
		release.setMember(toMember());
		release.setPublishTime(new Date());
		release.setPlanDay((int) ((release.getEndDate().getTime() - release.getStartDate().getTime()) / 1000 / 60 / 60 / 24) + 1);
		release = (PlanRelease) service.saveOrUpdate(release);
		JSONObject obj = new JSONObject();
		obj.accumulate("success", true).accumulate("key", release.getId());
		response(obj);
		return null;
	}
	
}
