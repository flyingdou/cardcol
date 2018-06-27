package com.freegym.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.freegym.web.service.IContentService;
import com.sanmen.web.core.content.Sector;

@Service("contentService")
public class ContentServiceImpl extends CommunityServiceImpl implements IContentService {

	@Override
	@Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
	public List<?> findSectors(final Long id) {
		if (id == null || id <= 0)
			return getHibernateTemplate().find("from Sector s where s.parent is null or s.parent <= 0 order by s.sort");
		return getHibernateTemplate().find("from Sector s where s.parent = ? order by s.sort", id);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSector(Long id, Long parent) {
		Sector s = (Sector) load(Sector.class, id);
		s.setParent(new Sector(parent));
		saveOrUpdate(s);
	}

}
