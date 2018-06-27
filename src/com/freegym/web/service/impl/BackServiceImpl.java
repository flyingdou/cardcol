package com.freegym.web.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cardcol.web.basic.Product45;
import com.cardcol.web.basic.ProductClub45;
import com.freegym.web.jdbc.JdbcResultSetExtractor;
import com.freegym.web.service.IBackService;
import com.sanmen.web.core.common.PageInfo;
import com.sanmen.web.core.service.impl.SystemServiceImpl;

@Service("backService")
public class BackServiceImpl extends SystemServiceImpl implements IBackService {

	@Override
	public boolean sendMail(final List<String> emails, final String subject, final String content) {
		try {
			mailSender.setTos(emails);
			mailSender.setSubject(subject);
			mailSender.setContent(content);
			mailSender.sendHtmlMessages();
			return true;
		} catch (Exception e) {
			log.error("error", e);
			return false;
		}
	}

	@Override
	public PageInfo findPageBySql(String sql, PageInfo pageInfo, Class<?> cls) {
		final StringBuffer sbCount = new StringBuffer("select count(*) from (" + sql + ") temp");
		final StringBuffer sbQuery = new StringBuffer("select * from (" + sql + ") temp limit " + pageInfo.getStart() + " , " + pageInfo.getPageSize());
		final Integer count = jdbc.queryForObject(sbCount.toString(), Integer.class);
		final List<?> list = jdbc.queryForList(sbQuery.toString(), new JdbcResultSetExtractor(cls));
		final PageInfo pi = PageInfo.getInstance();
		pi.setCurrentPage(pageInfo.getCurrentPage());
		pi.setPageSize(pageInfo.getPageSize());
		pi.setOrder(pageInfo.getOrder());
		pi.setOrderFlag(pageInfo.getOrderFlag());
		pi.setTotalCount(count);
		pi.setItems(list);
		return pi;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public PageInfo findPageBySql(final String sql, final PageInfo pageInfo, final Class<?> cls, final Object... args) {
		final StringBuffer sbCount = new StringBuffer("select count(*) from (" + sql + ") temp");
		final StringBuffer sbQuery = new StringBuffer("select * from (" + sql + ") temp limit " + pageInfo.getStart() + " , " + pageInfo.getPageSize());
		final Integer count = jdbc.queryForObject(sbCount.toString(), args, Integer.class);
		final PageInfo pi = PageInfo.getInstance();
		pi.setCurrentPage(pageInfo.getCurrentPage());
		pi.setPageSize(pageInfo.getPageSize());
		pi.setOrder(pageInfo.getOrder());
		pi.setOrderFlag(pageInfo.getOrderFlag());
		pi.setTotalCount(count);
		final List<?> list = jdbc.query(sbQuery.toString(), args, new JdbcResultSetExtractor(cls));
		pi.setItems(list);
		return pi;
	}

	@Override
	public List<?> findProjectBySystem() {
		return getHibernateTemplate().find("from Project p where p.system = ?", '1');
	}

	@Override
	public List<Map<String, Object>> queryTradeDetail(Long id) {
		return queryForList("select * from v_order_trade_detail where tradeMember = ?", id);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Product45 saveOneCard(Product45 product, List<ProductClub45> clubs) {
		if (product.getId() != null) executeUpdate("delete from tb_product_club_v45 where product = " + product.getId());
		product = (Product45) saveOrUpdate(product);
		for (final ProductClub45 club : clubs) {
			club.setProduct(product);
			getHibernateTemplate().merge(club);
		}
		return product;
	}
}
