package com.spshop.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.spshop.model.Coupon;

public class AddNewCouponEvent extends GwtEvent<AddNewCouponHandler> {
	public static Type<AddNewCouponHandler> TYPE = new Type<AddNewCouponHandler>();
	
	private Coupon coupon;

	@Override
	protected void dispatch(AddNewCouponHandler handler) {
		handler.onAddCoupon(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<AddNewCouponHandler> getAssociatedType() {
		return TYPE;
	}

	public Coupon getCoupon() {
		return coupon;
	}

	public void setCoupon(Coupon coupon) {
		this.coupon = coupon;
	}

}
