package com.cardcol.web.basic.system.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.hibernate.criterion.DetachedCriteria;

import com.cardcol.web.basic.Product45;
import com.cardcol.web.basic.ProductClub45;
import com.freegym.web.SystemBasicAction;
import com.sanmen.web.core.common.Upload;
import com.sanmen.web.core.content.ContentRecommend;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Namespace("")
@InterceptorRef(value = "backStack")
@Results({ @Result(name = "success", location = "/jsp/system/onecard.jsp") })
public class OneCardManageAction extends SystemBasicAction {

	private static final long serialVersionUID = -3318630561965646327L;

	private Product45 product, query;

	private ContentRecommend sa;

	private String type, applyClubs;

	private Upload file1;
	
	public Upload getFile1() {
		return file1;
	}

	public void setFile1(Upload file1) {
		this.file1 = file1;
	}

	public Product45 getProduct() {
		return product;
	}

	public void setProduct(Product45 product) {
		this.product = product;
	}

	public Product45 getQuery() {
		return query;
	}

	public void setQuery(Product45 query) {
		this.query = query;
	}

	public ContentRecommend getSa() {
		return sa;
	}

	public void setSa(ContentRecommend sa) {
		this.sa = sa;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getApplyClubs() {
		return applyClubs;
	}

	public void setApplyClubs(String applyClubs) {
		this.applyClubs = applyClubs;
	}

	@Override
	protected void executeQuery() {
		final DetachedCriteria dc = Product45.getCriteriaQuery(query);
		if (pageInfo.getOrder() == null) {
			pageInfo.setOrder("id");
			pageInfo.setOrderFlag("desc");
		}
		pageInfo = service.findPageByCriteria(dc, pageInfo);
	}

	@Override
	protected Long executeSave() {
		if (product != null) {
			if (file != null) {
				String fileName = this.saveFile("picture", product.getImage());
				product.setImage(fileName);
			}

			if (file1 != null) {
				String fileName1 = saveDetailImage("picture", product.getImage1(), file1);
				product.setImage1(fileName1);
			}

			List<ProductClub45> clubs = new ArrayList<ProductClub45>();
			if (!StringUtils.isBlank(applyClubs)) {
				JSONArray objs = JSONArray.fromObject(applyClubs);
				for (int i = 0; i < objs.size(); i++)
					clubs.add(new ProductClub45(((JSONObject) objs.get(i)).getLong("id")));
			}
			if (StringUtils.isBlank(product.getStatus()))
				product.setStatus(PRODUCT_STATUS_NOVALID);
			product = service.saveOneCard(product, clubs);
			return product.getId();
		}
		return 0l;
	}

	/*
	 * 2017-7-15
	 * 
	 * 黄文俊 上传第二张图片的方法 开始
	 */
	protected String saveDetailImage(String dir, String oldFile, Upload file1) {
		if ((file1 == null) || (dir == null))
			return oldFile;
		if ((oldFile != null) && (!("".equals(oldFile))))
			deleteFile(dir, oldFile);
		return saveFile2(dir, file1);
	}

	protected String saveFile2(String dir, Upload file1) {
		if ((file1 == null) || (file1.getFile() == null))
			return null;
		String fileName = null;
		String path = webPath + dir + System.getProperty("file.separator");
		File f = new File(path);
		if (!(f.exists()))
			f.mkdirs();
		fileName = getFileName(file1.getFileFileName());
		String paths = path + fileName;
		file1.getFile().renameTo(new File(paths));
		return fileName;
	}
	/*
	 * 2017-7-15
	 * 
	 * 上传第二张图片的方法 结束
	 */

	public void changeStatus() {
		try {
			product = (Product45) service.load(Product45.class, id);
			product.setStatus(type);
			service.saveOrUpdate(product);
			response();
		} catch (Exception e) {
			response(e);
		}
	}

	@Override
	public void recommend() {
		try {
			sa.setIcon(saveFile("picture", sa.getIcon()));
			sa.setRecommType(PRODUCT_TYPE_ONECARD);
			Date nowDate = new Date();
			sa.setRecommDate(nowDate);
			sa.setStickTime(nowDate);
			service.saveOrUpdate(sa);
			response(true, "message: 'ok'");
		} catch (Exception e) {
			response(e);
		}
	}

	@Override
	protected Class<?> getEntityClass() {
		return Product45.class;
	}

	@Override
	protected String getExclude() {
		return "clubs,orders";
	}
}
