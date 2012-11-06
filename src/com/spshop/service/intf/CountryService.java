package com.spshop.service.intf;

import java.util.List;

import com.spshop.dao.intf.CountryDAO;
import com.spshop.exception.ServiceValidateException;

import com.spshop.model.Country;

public interface CountryService extends BaseService<Country,CountryDAO, Long>{
	public Country saveCountry(Country country) throws ServiceValidateException;
	public List<Country> getAllCountries() throws ServiceValidateException;
	public Country getCountryById(long id) throws ServiceValidateException;
	public Country getCountryByName(String name) throws ServiceValidateException;
}
