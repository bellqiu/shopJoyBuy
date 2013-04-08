package com.spshop.service.impl;

import com.spshop.dao.intf.AddressDAO;
import com.spshop.model.Address;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.AddressService;

public class AddressServiceImpl extends AbstractService<Address,AddressDAO, Long> implements AddressService{

	@Override
	public Address saveOrUpdateAddress(Address address) {
		return getDao().save(address).clone();
	}

	@Override
	public Address getAddressById(long id) {
		Address address = fetchById(id);
		if(null != address){
			return address.clone();
		}
		return null;
	}

	@Override
	public void deleteAddressById(long id) {
		Address address = fetchById(id);
		if(null != address){
			getDao().remove(address);
		}
	}
	
}
