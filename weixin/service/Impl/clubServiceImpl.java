package service.Impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.freegym.web.active.TrainRecord;
import com.freegym.web.basic.Member;
import com.freegym.web.config.Setting;
import com.freegym.web.service.impl.WorkoutServiceImpl;
import com.sanmen.web.core.common.PageInfo;

import service.clubService;

@Service("clubService")
public class clubServiceImpl extends WorkoutServiceImpl implements clubService {
	@Autowired
	@Override
	public PageInfo findCardList(long id, PageInfo pageInfo) {
		String sql = "select P.NAME,P.PROD_IMAGE,P.PROD_SUMMARY,m.name,M.ID from tb_product_v45 p join tb_product_club_v45 c on p.id=c.product join tb_member m on c.club=m.id where m.role='E' and m.id=?";
		pageInfo = this.findPageBySql(sql, pageInfo);
		return pageInfo;
	}

	@Override
	public Member saveOrUpdateRecord1(Member member, String doneDate) throws Exception {
		return null;
	}

	@Override
	public Member saveOrUpdateRecord1(List<TrainRecord> record, Member member, String doneDate) throws Exception {
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for (final TrainRecord tr : record) {
			if (tr.getActiveOrder() != null) {
				List<?> recordList = getHibernateTemplate().find(
						"from TrainRecord cr where cr.partake.id = ? and cr.doneDate = ? and cr.activeOrder.id = ?",
						new Object[] { member.getId(), sdf.parse(doneDate), tr.getActiveOrder().getId() });
				if (recordList.size() > 0) {
					TrainRecord recordNew = (TrainRecord) recordList.get(0);
					recordNew.setWeight(tr.getWeight());
					recordNew.setWaist(tr.getWaist());
					recordNew.setHip(tr.getHip());
					recordNew.setHeight(tr.getHeight());
					recordNew = getHibernateTemplate().merge(recordNew);
					if (tr.getWeight() != null) {
						final Setting set = loadSetting(member.getId());
						set.setWeight(tr.getWeight());
						getHibernateTemplate().merge(set);
					}
					tr.setId(recordNew.getId());
				}
			} else {
				List<?> recordList = getHibernateTemplate().find(
						"from TrainRecord cr where cr.partake.id = ? and cr.doneDate = ?",
						new Object[] { member.getId(), sdf.parse(doneDate) });
				if (recordList.size() > 0) {
					TrainRecord recordNew = (TrainRecord) recordList.get(0);
					recordNew.setWeight(tr.getWeight());
					recordNew.setWaist(tr.getWaist());
					recordNew.setHip(tr.getHip());
					recordNew.setHeight(tr.getHeight());
					recordNew = getHibernateTemplate().merge(recordNew);
					tr.setId(recordNew.getId());
					if (tr.getWeight() != null) {
						final Setting set = loadSetting(member.getId());
						set.setWeight(tr.getWeight());
						getHibernateTemplate().merge(set);
					}
				}
			}
		}
		return member;

	}
}
