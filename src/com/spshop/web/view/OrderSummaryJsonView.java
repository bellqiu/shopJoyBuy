/*
 * Project: iSAPort
 * Copyright (c) 2012 HP. All Rights Reserved.
 */
package com.spshop.web.view;

import org.codehaus.jackson.annotate.JsonAutoDetect;

/**
 * 
 * @author <link href="wan-shan.zhu@hp.com">Spark Zhu</link>
 * @version 1.0
 */
@JsonAutoDetect
public class OrderSummaryJsonView {
	private String subTotal;
	private String coupon;
	private String grandTotal;
	private String dePrice;
	
	private OrderItemSummaryJsonView orderItemView;
	private OrderShippingAddressJsonView shippingAddressView;
	
	private boolean success;
	private String msg;
	
	public OrderSummaryJsonView() {
	}

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public OrderItemSummaryJsonView getOrderItemView() {
		return orderItemView;
	}

	public void setOrderItemView(OrderItemSummaryJsonView orderItemView) {
		this.orderItemView = orderItemView;
	}

	public String getDePrice() {
		return dePrice;
	}

	public void setDePrice(String dePrice) {
		this.dePrice = dePrice;
	}

	public OrderShippingAddressJsonView getShippingAddressView() {
		return shippingAddressView;
	}

	public void setShippingAddressView(OrderShippingAddressJsonView shippingAddressView) {
		this.shippingAddressView = shippingAddressView;
	}
	
}
