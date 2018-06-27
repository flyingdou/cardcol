package com.freegym.web.content.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.ContentBasicAction;
import com.freegym.web.utils.DBConstant;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("customStack") })
@Results({ @Result(name = "success", location = "/content/index.jsp") })
public class ServiceManageAction extends ContentBasicAction {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = -546219074700831488L;

	protected Long channel;

	public Long getChannel() {
		return channel;
	}

	public void setChannel(Long channel) {
		this.channel = channel;
	}

	public String execute() {
		request.setAttribute("channels", service.findObjectBySql("from Sector s where s.parent is null and s.position = '2' order by s.sort"));
		request.setAttribute("mainBannel", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_MAIN));
		request.setAttribute("clubs", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_CLUB));
		request.setAttribute("coachs", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_COACH));
		request.setAttribute("members", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_MEMBER));
		request.setAttribute("sectorys", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_SECTORY));
		request.setAttribute("rules", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_RULE));
		request.setAttribute("focus", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_FOCUS));
		request.setAttribute("news", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_NEWS));
		//中间广告位(广告1)
		request.setAttribute("mBannel", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_BANNER_1));
//		//右边广告位(广告2)
		request.setAttribute("rBannel", service.findRecommendBySectorCode(DBConstant.RECOMM_SERVICE_BANNER_2));
		return SUCCESS;
	}
}
