package com.spshop.service.impl;

import com.spshop.dao.intf.UserDAO;
import com.spshop.model.User;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.UserService;

/**
 * @author Spark.Zhu
 * 
 */
public class UserServiceImpl extends AbstractService<User, UserDAO, Long>
		implements UserService {

	
	@Override
	public Boolean validateUserByEmail(String email) {
		int count = getDao().queryUserCountByName(email);
		if (0 != count) {
			return true;
		}
		return false;
	}

	@Override
	public User validateUser(User user) {
		return getDao().queryUserByEmailAndPassword(user);
	}

	@Override
	public User queryUserByEmail(String email){
		return getDao().queryUserByName(email);
	}
	
	public User saveUser(User user){
		user = getDao().save(user);
		if(null!=user){
			return user.clone();
		}
		
		return null;
	}
}
