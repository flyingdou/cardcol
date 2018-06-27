package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;
import java.util.HashMap;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.FileItem;
import com.taobao.api.TaobaoUploadRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.FlashPictureUploadResponse;

/**
 * TOP API: taobao.flash.picture.upload request
 * 
 * @author top auto create
 * @since 1.0, 2016.03.05
 */
public class FlashPictureUploadRequest extends BaseTaobaoRequest<FlashPictureUploadResponse> implements TaobaoUploadRequest<FlashPictureUploadResponse> {

	
	

	/** 
	* 包括后缀名的图片标题,不能为空，如Bule.jpg,有些卖家希望图片上传后取图片文件的默认名
	 */
	private String imageInputTitle;

	/** 
	* 图片二进制文件流,不能为空,允许png、jpg、gif图片格式
	 */
	private FileItem img;

	/** 
	* 用户昵称
	 */
	private String nick;

	/** 
	* 图片标题,如果为空,上传的图片标题就取去掉后缀名的image_input_title,超过50字符长度会截取50字符，重名会在标题末尾加"(1)"；标题末尾已经有"(数字)"了，则数字加1。
	 */
	private String title;

	public void setImageInputTitle(String imageInputTitle) {
		this.imageInputTitle = imageInputTitle;
	}

	public String getImageInputTitle() {
		return this.imageInputTitle;
	}

	public void setImg(FileItem img) {
		this.img = img;
	}

	public FileItem getImg() {
		return this.img;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return this.nick;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public String getApiMethodName() {
		return "taobao.flash.picture.upload";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("image_input_title", this.imageInputTitle);
		txtParams.put("nick", this.nick);
		txtParams.put("title", this.title);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<FlashPictureUploadResponse> getResponseClass() {
		return FlashPictureUploadResponse.class;
	}

	public void check() throws ApiRuleException {
		
		RequestCheckUtils.checkNotEmpty(imageInputTitle, "imageInputTitle");
	}

	public Map<String, FileItem> getFileParams() {
		Map<String, FileItem> params = new HashMap<String, FileItem>();
		params.put("img", this.img);
		return params;
	}
	
	

}