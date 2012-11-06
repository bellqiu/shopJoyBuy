package com.spshop.dao.intf;

import com.spshop.model.Coupon;

public interface CouponDAO extends BaseDAO<Coupon, Long>{
	Coupon getCouponByCode(String code);
}
