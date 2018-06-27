package com.taobao.api.request;

import com.taobao.api.internal.util.RequestCheckUtils;
import java.util.Map;

import com.taobao.api.ApiRuleException;
import com.taobao.api.BaseTaobaoRequest;
import com.taobao.api.internal.util.TaobaoHashMap;

import com.taobao.api.response.OpenimIoscertSandboxSetResponse;

/**
 * TOP API: taobao.openim.ioscert.sandbox.set request
 * 
 * @author top auto create
 * @since 1.0, 2015.07.01
 */
public class OpenimIoscertSandboxSetRequest extends BaseTaobaoRequest<OpenimIoscertSandboxSetResponse> {
	
	

	/** 
	* 证书内容,base64编码
	 */
	private String cert;

	/** 
	* 系统自动生成
	 */
	private String password;

	public void setCert(String cert) {
		this.cert = cert;
	}

	public String getCert() {
		return this.cert;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public String getApiMethodName() {
		return "taobao.openim.ioscert.sandbox.set";
	}

	public Map<String, String> getTextParams() {		
		TaobaoHashMap txtParams = new TaobaoHashMap();
		txtParams.put("cert", this.cert);
		txtParams.put("password", this.password);
		if(this.udfParams != null) {
			txtParams.putAll(this.udfParams);
		}
		return txtParams;
	}

	public Class<OpenimIoscertSandboxSetResponse> getResponseClass() {
		return OpenimIoscertSandboxSetResponse.class;
	}

	public void check() throws ApiRuleException {
		RequestCheckUtils.checkNotEmpty(cert, "cert");
	}
	

}