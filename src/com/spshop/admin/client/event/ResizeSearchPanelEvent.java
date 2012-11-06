package com.spshop.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class ResizeSearchPanelEvent extends GwtEvent<ResizeSearchPanelHandler>{
	public static Type<ResizeSearchPanelHandler> TYPE = new Type<ResizeSearchPanelHandler>();
	private double height;
	@Override
	protected void dispatch(ResizeSearchPanelHandler handler) {
		handler.onResize(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ResizeSearchPanelHandler> getAssociatedType() {
		return TYPE;
	}

	public ResizeSearchPanelEvent(double height) {
		this.height = height;
	}
	
	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

}
