package com.cardcol.web.balance;

import java.util.List;

import com.freegym.web.order.Order;
import com.sanmen.web.core.bean.CommonId;
import com.sanmen.web.core.common.LogicException;

public interface IBalance {

	public List<CommonId> execute(Order order) throws LogicException;
}
