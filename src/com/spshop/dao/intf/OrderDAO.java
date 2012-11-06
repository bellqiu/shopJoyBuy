package com.spshop.dao.intf;

import com.spshop.model.Order;
import com.spshop.model.User;

public interface OrderDAO extends BaseDAO<Order, Long>{
	public User getUserById(long id);
}
