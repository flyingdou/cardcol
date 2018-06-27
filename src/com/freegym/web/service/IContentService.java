package com.freegym.web.service;

import java.util.List;

public interface IContentService extends ICommunityService {

	List<?> findSectors(final Long id);

	void updateSector(final Long id, final Long parent);

}
