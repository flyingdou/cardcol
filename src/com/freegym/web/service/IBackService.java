package com.freegym.web.service;

import java.util.List;
import java.util.Map;

import com.cardcol.web.basic.Product45;
import com.cardcol.web.basic.ProductClub45;
import com.sanmen.web.core.common.PageInfo;
import com.sanmen.web.core.service.ISystemService;

public interface IBackService extends ISystemService {
	@Override
	public PageInfo findPageBySql(String sql, PageInfo pageInfo, Class<?> cls);

	@Override
	public PageInfo findPageBySql(final String sql, final PageInfo pageInfo, final Class<?> cls, final Object... args);

	public List<?> findProjectBySystem();

	public List<Map<String, Object>> queryTradeDetail(Long id);

	public Product45 saveOneCard(Product45 product, List<ProductClub45> clubs);

}
