package com.freegym.web.system.action;

import java.io.File;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;

import com.freegym.web.BaseBasicAction;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
public class ImageUploadManageAction extends BaseBasicAction {

	private static final long serialVersionUID = 9107610847716595429L;

	private File upload;

	private String uploadContentType, uploadFileName;

	public java.io.File getUpload() {
		return upload;
	}

	public void setUpload(java.io.File upload) {
		this.upload = upload;
	}

	public String getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String execute() {
		if (upload != null) {
			final String path = "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/picture/";
			final String saveFile = path + this.saveFile("picture", upload, uploadFileName, "");
			final String result = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(2, '" + saveFile + "');</script>"; 
			response(result);
		}
		return null;
	}
}
