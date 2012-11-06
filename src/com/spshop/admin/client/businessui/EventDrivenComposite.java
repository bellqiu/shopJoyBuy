package com.spshop.admin.client.businessui;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.Composite;
import com.spshop.admin.client.businessui.service.AdminServiceAsync;
public abstract class EventDrivenComposite<C> extends Composite {
	protected C component;
	protected HandlerManager eventBus;
	protected AdminServiceAsync serviceAsync;
	
	public EventDrivenComposite() {
	}
	
	public C getComponet() {
		return component;
	}

	public abstract void setComponent(C component);
	
	public void setService(AdminServiceAsync serviceAsync){
		this.serviceAsync = serviceAsync;
	}
	
	public void setEventBus(HandlerManager eventBus){
		this.eventBus = eventBus;
	}
	
	public HandlerManager getEventBus() {
		return this.eventBus;
	}

}
