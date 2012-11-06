package com.spshop.admin.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.Widget;

public class LoadingEvent extends GwtEvent<LoadingHandler> {
	public static Type<LoadingHandler> TYPE = new Type<LoadingHandler>();
	
	private Widget source;
	
	public LoadingEvent(Widget source) {
		this.setSource(source);
	}

	@Override
	protected void dispatch(LoadingHandler handler) {
		handler.onLoading(this);
	}

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoadingHandler> getAssociatedType() {
		return TYPE;
	}

	public Widget getSource() {
		return source;
	}

	public void setSource(Widget source) {
		this.source = source;
	}


}
