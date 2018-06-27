package com.freegym.web.service;

import com.freegym.web.config.FactoryCosts;
import com.freegym.web.order.FactoryOrder;


public interface IFactoryOrderService  extends IContentService {
	/**
	 * 
	 **/
	public FactoryCosts saveFactoryCosts(FactoryCosts factoryCosts);
	
	public Boolean deleteFactoryCosts(FactoryCosts factoryCosts);
	
	public FactoryCosts updateFactoryCosts(FactoryCosts factoryCosts);
	
	public FactoryOrder saveFactoryOrder(FactoryOrder factoryOrder);
	
	public Boolean  beforeSaveFactoryCosts(FactoryCosts factoryCosts);
	
}
