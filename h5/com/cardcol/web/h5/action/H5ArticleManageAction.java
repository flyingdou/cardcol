package com.cardcol.web.h5.action;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.SystemBasicAction;
import com.sanmen.web.core.content.Article;

@Namespace("")
@InterceptorRef("customStack")
@Results({ @Result(name = "article", location = "/h5/article.jsp") })
public class H5ArticleManageAction extends SystemBasicAction {

	private static final long serialVersionUID = 2582804711846603446L;

	@Override
	public String execute() {
		final Article art = (Article) service.load(Article.class, id);
		request.setAttribute("article", art);
		return "article";
	}
}
