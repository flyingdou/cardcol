package com.freegym.web.basic.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.freegym.web.BaseBasicAction;
import com.freegym.web.basic.Certificate;

@Namespace("")
@InterceptorRefs({ @InterceptorRef("foreStack") })
@Results({ @Result(name = "success", location = "/basic/cert.jsp") })
public class CertManageAction extends BaseBasicAction {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -3790627698727521253L;

	private List<Certificate> certs;

	public List<Certificate> getCerts() {
		return certs;
	}

	public void setCerts(List<Certificate> certs) {
		this.certs = certs;
	}

	@SuppressWarnings("unchecked")
	public String execute() {
		session.setAttribute("spath", 7);
		certs = (List<Certificate>) service.findObjectBySql("from Certificate c where c.member = ?", toMember().getId());
		return SUCCESS;
	}

	public void save() {
		try {
			final List<Certificate> cs = new ArrayList<Certificate>();
			for (Certificate c : certs) {
				c.setMember(toMember().getId());
				if (c.getFileName() == null || "".equals(c.getFileName())) cs.add(c);
			}
			if (this.file != null && this.file.getFiles() != null) {
				for (int i = 0; i < this.file.getFiles().length; i++) {
					cs.get(i).setFileName(saveFile("picture", file.getFiles()[i], file.getFilesFileName()[i], cs.get(i).getFileName()));
				}
			}
			service.saveOrUpdate(certs);
			response("OK");
		} catch (Exception e) {
			response(e.getMessage());
			log.error("error", e);
		}
	}

	public void delete() {
		try {
			service.delete(Certificate.class, id);
			response("OK");
		} catch (Exception e) {
			response(e.getMessage());
		}
	}
}
