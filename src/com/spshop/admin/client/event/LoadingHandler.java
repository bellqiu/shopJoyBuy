package com.spshop.admin.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoadingHandler extends EventHandler{
	void onLoading(LoadingEvent event);
}
