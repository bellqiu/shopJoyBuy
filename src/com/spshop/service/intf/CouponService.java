package com.spshop.service.intf;

import java.util.Date;

import com.spshop.dao.intf.CouponDAO;

import com.spshop.model.Coupon;

public interface CouponService extends BaseService<Coupon,CouponDAO, Long>{
	void saveCoupon(Coupon coupon);
	/**
	 * 
	 * @param code
	 * @return is not coupon or invalid coupon return null;
	 */
	Coupon getCouponByCode(String code);
	
	
	void batchCreateOnetimeCoupon(float value, Date start, Date end, String name, int count );
	
}
