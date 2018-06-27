package com.freegym.web.basic.action;

import java.io.File;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Member;
import com.sanmen.web.core.utils.FileUtil;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/picture.jsp"),
		@Result(name = "save", location = "picture.asp", type = "redirect") })
public class PictureManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 8942409430710869379L;

	private Member member;
	
	private String banner;
	
	private File memberHead;

	private String memberHeadFileName;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}
	
	public File getMemberHead() {
		return memberHead;
	}

	public void setMemberHead(File memberHead) {
		this.memberHead = memberHead;
	}

	public String getMemberHeadFileName() {
		return memberHeadFileName;
	}

	public void setMemberHeadFileName(String memberHeadFileName) {
		this.memberHeadFileName = memberHeadFileName;
	}

	public String execute() {
		session.setAttribute("spath", 7);
		member = toMember();
		String querySql = "select banner_image from tb_club_wifi where club = ?";
		Map<String, Object> map = service.queryForMap(querySql, member.getId());
		if(map != null && map.containsKey("banner_image")) {
			this.banner = (String) map.get("banner_image");
		}
		return SUCCESS;
	}

	public String save() {
		member = toMember();
		try {
			if (file != null && file.getFile() != null) {
//				boolean isImage = FileUtil.isFile(file.getFile(),
//						"png,jpg,gif,");
//				if (!isImage)
//					throw new LogicException("非正常图片格式，请确认！");
				FileUtil.addWaterMark(
						file.getFile(),
						file.getFileFileName().substring(
								file.getFileFileName().lastIndexOf(".") + 1),
						webPath + "/images/watermark.png", -1, -1, 0.0f);
			}
			member.setImage(this.saveFile("picture", member.getImage()));
			member = (Member) service.saveOrUpdate(member);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
		}
		session.setAttribute(LOGIN_MEMBER, member);
		return "save";
	}
	
	public String save2() {
		member = toMember();
		try {
			if (file != null && file.getFile() != null) {
//				boolean isImage = FileUtil.isFile(file.getFile(),
//						"png,jpg,gif,");
//				if (!isImage)
//					throw new LogicException("非正常图片格式，请确认！");
				/*FileUtil.addWaterMark(
						file.getFile(),
						file.getFileFileName().substring(
								file.getFileFileName().lastIndexOf(".") + 1),
						webPath + "/images/watermark.png", -1, -1, 0.0f);*/
			}
			String fileName = saveFile("picture", memberHead, memberHeadFileName, null);
			if(fileName != null) {
				String querySql = "select * from tb_club_wifi where club = ?";
				Map<String, Object> map = service.queryForMap(querySql, member.getId());
				if(map != null && map.get("id") != null) {					
					String updateSql = "update tb_club_wifi set banner_image = '"+ fileName +"' where club = " + member.getId();				
					service.executeUpdate(updateSql);
				} else {
					String insertSql = "insert into tb_club_wifi(id,banner_image,club) values(null,'"+fileName+"',"+member.getId()+")";
					service.executeUpdate(insertSql);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error", e);
		}
		session.setAttribute(LOGIN_MEMBER, member);
		return "save";
	}
}
