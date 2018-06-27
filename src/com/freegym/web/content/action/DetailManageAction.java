package com.freegym.web.content.action;

import java.util.Iterator;
import java.util.List;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.sanmen.web.core.content.Article;
import com.sanmen.web.core.content.Sector;


@Namespace("")
@Results({ @Result(name = "success", location = "/content/detail.jsp"), @Result(name = "nr", location = "/content/nr.jsp") })
public class DetailManageAction extends ServiceManageAction {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */

	private static final long serialVersionUID = 3244519072165861991L;

	private Article article;

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public String execute() {
		request.setAttribute("channels", service.findObjectBySql("from Sector s where s.parent is null and s.position = '2' order by s.sort"));
		request.setAttribute("sector", service.load(Sector.class, channel));
		final List<?> list = service.findObjectBySql("from Article a where a.sector.id = ? and a.audit = '1' order by a.stickTime desc, a.issueTime desc", channel);
		final StringBuffer sb = new StringBuffer("www.cardcol.com/detail.asp?channel=" + channel);
		if (list.size() > 0) {
			if (id != null) {
				sb.append("&id=" + id);
				boolean isExist = false;
				for (Iterator<?> it = list.iterator(); it.hasNext();) {
					article = (Article) it.next();
					if (article.getId().equals(id)) {
						isExist = true;
						break;
					}
				}
				if (!isExist){
					article = (Article) list.get(0);
				}
			} else {
				article = (Article) list.get(0);
			}
		}
		request.setAttribute("link", sb.toString());
		request.setAttribute("articles", list);
		return SUCCESS;
	}
	
	public String loadArticle(){
		article = (Article) service.load(Article.class, id);
		final StringBuffer sb = new StringBuffer("www.cardcol.com/detail.asp?channel=" + channel + "&id=" + id);
		request.setAttribute("link", sb.toString());
		return "nr";
	}
}
