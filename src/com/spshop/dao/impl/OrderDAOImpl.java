package com.spshop.dao.impl;

import com.spshop.dao.AbstractBaseDAO;
import com.spshop.dao.intf.OrderDAO;
import com.spshop.model.Order;
import com.spshop.model.User;

public class OrderDAOImpl extends AbstractBaseDAO<Order, Long>  implements OrderDAO{
	public User getUserById(long id){
		return (User) getSession().get(User.class, id);
	}
}
