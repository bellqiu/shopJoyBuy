package com.spshop.demo.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.StackLayoutPanel;

public class Accordion extends ResizeComposite {

	private static AccordionUiBinder uiBinder = GWT
			.create(AccordionUiBinder.class);

	interface AccordionUiBinder extends UiBinder<StackLayoutPanel, Accordion> {
	}

	public Accordion() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
