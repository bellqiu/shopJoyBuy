package com.spshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.spshop.dao.intf.CountryDAO;
import com.spshop.exception.ServiceValidateException;
import com.spshop.model.Component;
import com.spshop.model.Country;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.CountryService;

public class CountryServiceImpl extends AbstractService<Country,CountryDAO, Long> implements CountryService{
	public Country saveCountry(Country country) throws ServiceValidateException{
		return save(country).clone();
	}
	
	@SuppressWarnings("unchecked")
	public List<Country> getAllCountries() throws ServiceValidateException{
		List<Country> countries = new ArrayList<Country>();
		String hql = "FROM Country";
		List<Component> rs = getDao().queryByHQL(hql, 0, 1000);
		
		for(Component c : rs){
			Country co = (Country)c.clone();
			countries.add(co);
		}
		return countries;
	}

	@Override
	public Country getCountryById(long id) throws ServiceValidateException {
		Country country = getDao().fetchById(id);
		
		if(null == country){
			return null;
		}
		
		return country.clone();
	}

	@Override
	public Country getCountryByName(String name)
			throws ServiceValidateException {
		
		String hql = "FROM Country where name = ?";
		List<Country> rs = (List<Country>) getDao().queryByHQL(hql, 0, 200,name);
		
		if(null == rs || rs.isEmpty()){
			return null;
		}
		
		return rs.get(0);
	}
}
