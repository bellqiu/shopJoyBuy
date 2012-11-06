package com.spshop.service.impl;

import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.spshop.dao.intf.CouponDAO;

import com.spshop.model.Coupon;

import com.spshop.service.AbstractService;

import com.spshop.service.intf.CouponService;

public class CouponServiceImpl extends AbstractService<Coupon,CouponDAO, Long> implements CouponService{
	
	private static Logger logger = Logger.getLogger(CouponServiceImpl.class);
	
	@Override
	public void saveCoupon(Coupon coupon) {
		if(null == coupon.getCreateDate()){
			coupon.setCreateDate(new Date());
		}
		coupon.setUpdateDate(new Date());
		if(null == coupon.getCode()){
			coupon.setCode(generateCouponCode());
		}
		getDao().save(coupon);
	}

	@Override
	public Coupon getCouponByCode(String code) {
		
		Coupon coupon = null;
		
		try {
			coupon = getDao().getCouponByCode(code);
			
			if(coupon.isOnetime() && coupon.getUsedCount()>0){
				return null;
			}
			Date currentDate = new Date();
			if(coupon.getEndTime().after(currentDate) && coupon.getStartDate().before(currentDate)){
				return coupon;
			}
		} catch (Exception e) {
			logger.warn(e.getMessage(),e);
		}
		
		return coupon;
	}

	@Override
	public void batchCreateOnetimeCoupon(float value, Date start, Date end,
			String name, int count) {
		while(count>0){
			
			Coupon coupon = new Coupon();
			coupon.setValue(value);
			coupon.setStartDate(start);
			coupon.setEndTime(end);
			coupon.setName(name);
			coupon.setOnetime(true);
			coupon.setCode(generateCouponCode());
			saveCoupon(coupon);
			
			count--;
		}
	}
	
	
	private String generateCouponCode(){
		String id = "";
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (new Random().nextInt(99)+100);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (new Random().nextInt(9999999)+100);
		return id;
	}

}
