package service;

import java.util.List;

import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.service.IWorkoutService;
import com.sanmen.web.core.common.PageInfo;

public interface clubService extends IWorkoutService {
	public PageInfo findCardList(long id, PageInfo pageInfo);

	public Member saveOrUpdateRecord1(Member member, final String doneDate) throws Exception;

	/*
	 * 保存身体数据
	 */
	public Member saveOrUpdateRecord1(final List<TrainRecord> record, final Member member, final String doneDate)
			throws Exception;
}