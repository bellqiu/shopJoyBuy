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
public class OrderItemSummaryJsonView {
	private String finalPrice;
	private String quantity;
	
	public OrderItemSummaryJsonView() {
	}


	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}


	public String getFinalPrice() {
		return finalPrice;
	}


	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
	
}
