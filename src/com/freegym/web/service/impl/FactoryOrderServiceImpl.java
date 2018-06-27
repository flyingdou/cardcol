package com.freegym.web.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.freegym.web.config.FactoryCosts;
import com.freegym.web.order.FactoryOrder;
import com.freegym.web.service.IFactoryOrderService;
@Service("factoryOrderService")
public class FactoryOrderServiceImpl extends ContentServiceImpl implements IFactoryOrderService {

	@Override
	public FactoryCosts saveFactoryCosts(FactoryCosts factoryCosts) {
		getHibernateTemplate().save(factoryCosts);
		return factoryCosts;
	}

	@Override
	public Boolean deleteFactoryCosts(FactoryCosts factoryCosts) {
		try {
			getHibernateTemplate().delete( factoryCosts);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}

	@Override
	public FactoryCosts updateFactoryCosts(FactoryCosts factoryCosts) {
		getHibernateTemplate().saveOrUpdate(factoryCosts);	
		return factoryCosts;
	}

	@Override
	public FactoryOrder saveFactoryOrder(FactoryOrder factoryOrder) {
		getHibernateTemplate().saveOrUpdate(factoryOrder);	
		return factoryOrder;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Boolean beforeSaveFactoryCosts(FactoryCosts factoryCosts) {
		
		FactoryCosts f=new FactoryCosts();
		
		List<FactoryCosts> conflictFactoryCosts=(List<FactoryCosts>)this.findObjectBySql(
				"from FactoryCosts f where f.plandate=? and f.starttime<fac."+factoryCosts.getStarttime()+" and "+factoryCosts.getStarttime()+" f.endtime or  f.starttime<fac."+factoryCosts.getStarttime()+" and "+factoryCosts.getStarttime()+" f.endtime " , factoryCosts.getPlanDate());
		if(conflictFactoryCosts.size()>0)
			return true;
		else 
			return false;
	}

}
