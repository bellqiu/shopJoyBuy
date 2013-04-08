package com.spshop.service.intf;

import com.spshop.dao.intf.AddressDAO;
import com.spshop.model.Address;

public interface AddressService extends BaseService<Address, AddressDAO, Long>{
	public Address saveOrUpdateAddress(Address address);
	public Address getAddressById(long id);
	public void deleteAddressById(long id);
}
