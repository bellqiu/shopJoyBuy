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
public class OrderShippingAddressJsonView {
	private String standardPrice="0";
	private String expeditedPrice="0";
	private boolean standardChecked;
	private boolean expeditedChecked;
	
	public OrderShippingAddressJsonView() {
	}

	public String getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(String standardPrice) {
		this.standardPrice = standardPrice;
	}

	public String getExpeditedPrice() {
		return expeditedPrice;
	}

	public void setExpeditedPrice(String expeditedPrice) {
		this.expeditedPrice = expeditedPrice;
	}

	public boolean isStandardChecked() {
		return standardChecked;
	}

	public void setStandardChecked(boolean standardChecked) {
		this.standardChecked = standardChecked;
	}

	public boolean isExpeditedChecked() {
		return expeditedChecked;
	}

	public void setExpeditedChecked(boolean expeditedChecked) {
		this.expeditedChecked = expeditedChecked;
	}
}
