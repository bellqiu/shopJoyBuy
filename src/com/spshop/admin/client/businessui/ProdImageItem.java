package com.spshop.admin.client.businessui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class ProdImageItem extends Composite {

	private static ProdImageItemUiBinder uiBinder = GWT
			.create(ProdImageItemUiBinder.class);

	interface ProdImageItemUiBinder extends UiBinder<Widget, ProdImageItem> {
	}

	public ProdImageItem() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
