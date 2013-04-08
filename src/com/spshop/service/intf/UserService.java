package com.spshop.service.intf;

import com.spshop.dao.intf.UserDAO;
import com.spshop.model.Address;
import com.spshop.model.User;

public interface UserService extends BaseService<User, UserDAO, Long>{

	Boolean validateUserByEmail(String email);

	User validateUser(User user);

	User queryUserByEmail(String email);
	
	public User saveUser(User user);
	
	public User saveOrUpdateUserShippingAddress(User user, Address address);
	
	public User deleteUserShippingAddress(User user, long address);
	
	public User saveOrUpdateUserBillingAddress(User user, Address address);
}
