package com.spshop.dao.impl;

import com.spshop.dao.AbstractBaseDAO;

import com.spshop.dao.intf.CouponDAO;

import com.spshop.model.Coupon;

public class CouponDAOImpl extends AbstractBaseDAO<Coupon, Long>  implements CouponDAO{

	@Override
	public Coupon getCouponByCode(String code) {
		return (Coupon) getSession().createQuery("from Coupon where code = ? ").setParameter(0, code).uniqueResult();
	}

}
