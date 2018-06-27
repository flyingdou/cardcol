package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.FlashPictureDeleteResponse;

/**
 * TOP API: taobao.flash.picture.delete request
 * 
 * @author top auto create
 * @since 1.0, 2016.03.05
 */
public class FlashPictureDeleteRequest extends BaseTaobaoRequest<FlashPictureDeleteResponse> {
	
	

	/** 
	* 用户昵称
	 */
	private String nick;

	/** 
	* 图片ID字符串,可以一个也可以一组,用英文逗号间隔,如450,120,155
	 */
	private String pictureIds;

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNick() {
		return this.nick;
	}

	public void setPictureIds(String pictureIds) {
		this.pictureIds = pictureIds;
	}

	public String getPictureIds() {
		return this.pictureIds;
	}

	public String getApiMethodName() {
		return "taobao.flash.picture.delete";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("nick", this.nick);
		txtParams.put("picture_ids", this.pictureIds);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<FlashPictureDeleteResponse> getResponseClass() {
		return FlashPictureDeleteResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(nick, "nick");
		RequestCheckUtils.checkNotEmpty(pictureIds, "pictureIds");
	}
	

}