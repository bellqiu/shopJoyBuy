package com.spshop.service.impl;

import java.util.ArrayList;

import com.spshop.dao.intf.UserDAO;
import com.spshop.model.Address;
import com.spshop.model.User;
import com.spshop.service.AbstractService;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.AddressService;
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
		
		user = getDao().queryUserByEmailAndPassword(user);
			
		return user!=null?user.clone():null;
	}

	@Override
	public User queryUserByEmail(String email){
		
		User user = getDao().queryUserByName(email);
		
		return user!=null?user.clone():null;
	}
	
	public User saveUser(User user){
		user = getDao().save(user);
		if(null!=user){
			return user.clone();
		}
		
		return null;
	}

	@Override
	public User saveOrUpdateUserShippingAddress(User user, Address add) {
		//add = ServiceFactory.getService(AddressService.class).saveOrUpdateAddress(add);
		User usr = getDao().fetchById(user.getId());
		
		if(null == usr.getShippingAddresses()){
			usr.setShippingAddresses(new ArrayList<Address>());
		}
		
		boolean existingAdd = false;
		for (Address usrAdd : usr.getShippingAddresses()) {
			if(usrAdd.getId() == add.getId()){
				existingAdd = true;
				usrAdd.setAddress1(add.getAddress1());
				usrAdd.setAddress2(add.getAddress2());
				usrAdd.setCity(add.getCity());
				usrAdd.setCountry(add.getCountry());
				usrAdd.setFirstName(add.getFirstName());
				usrAdd.setLastName(add.getLastName());
				usrAdd.setPhone(add.getPhone());
				usrAdd.setPostalCode(add.getPostalCode());
				usrAdd.setStateProvince(add.getStateProvince());
			}
		}
		
		if(!existingAdd){
			usr.getShippingAddresses().add(add);
		}
		
		getDao().evict(usr);
		return getDao().save(usr).clone();
	}

	@Override
	public User deleteUserShippingAddress(User user, long address) {
		Address add = ServiceFactory.getService(AddressService.class).getAddressById(address);
	
		User usr = getDao().fetchById(user.getId());
		
		if(null == add){
			return usr;
		}
		
		if(null == usr.getShippingAddresses()){
			usr.setShippingAddresses(new ArrayList<Address>());
		}
		
		int existingAdd = -1;
		for (int i=0 ; i < usr.getShippingAddresses().size(); i++) {
			Address usrAdd = usr.getShippingAddresses().get(i);
			if(usrAdd.getId() == add.getId()){
				existingAdd = i;
			}
		}
		
		if(existingAdd > -1){
			ServiceFactory.getService(AddressService.class).deleteAddressById(address);
		
			usr.getShippingAddresses().remove(existingAdd);
		}
		
		return getDao().save(usr).clone();
	}

	@Override
	public User saveOrUpdateUserBillingAddress(User user, Address add) {
		User usr = getDao().fetchById(user.getId());
		
		if(null == usr.getBillingAddress()){
			usr.setBillingAddress(new Address());
		}
		
		Address usrAdd = usr.getBillingAddress();
		
		usrAdd.setAddress1(add.getAddress1());
		usrAdd.setAddress2(add.getAddress2());
		usrAdd.setCity(add.getCity());
		usrAdd.setCountry(add.getCountry());
		usrAdd.setFirstName(add.getFirstName());
		usrAdd.setLastName(add.getLastName());
		usrAdd.setPhone(add.getPhone());
		usrAdd.setPostalCode(add.getPostalCode());
		usrAdd.setStateProvince(add.getStateProvince());
		
		usr.setBillingAddress(usrAdd);
		getDao().evict(usr);
		return getDao().save(usr).clone();
	}
}
