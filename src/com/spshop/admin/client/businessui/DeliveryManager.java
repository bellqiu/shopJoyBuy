package com.spshop.admin.client.businessui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.PopWindow;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.EditorChangeAdapter;
import com.spshop.model.Country;

public class DeliveryManager extends Composite {

	private static DeliveryManagerUiBinder uiBinder = GWT
			.create(DeliveryManagerUiBinder.class);
	@UiField ListBox countryList;
	@UiField CountryCreation countryCreation;

	interface DeliveryManagerUiBinder extends UiBinder<Widget, DeliveryManager> {
	}

	public DeliveryManager() {
		initWidget(uiBinder.createAndBindUi(this));
		countryCreation.addChangeListener(new EditorChangeAdapter<Country, CountryCreation>(){
			@Override
			public void onChange(Country co, CountryCreation widget) {
				boolean hasCo = false;
				for(int i = 0; i < countryList.getItemCount(); i++){
					if((co.getId()+"").equals(countryList.getValue(i))){
						hasCo = true;
						countryList.setValue(i, co.getId()+"");
						countryList.setItemText(i, co.getAbbrCode()+"("+co.getName()+")");
						break;
					}
				}
				if(!hasCo){
					countryList.addItem(co.getAbbrCode()+"("+co.getName()+")",co.getId()+"");
				}
			}
		});
		
		countryList.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				ListBox lb = (ListBox)event.getSource();
				final String id = lb.getValue(lb.getSelectedIndex());
				
				final PopWindow loading = PopWindow.createLoading("Waiting").lock();
				AdminWorkspace.ADMIN_SERVICE_ASYNC.getCountryById(Long.valueOf(id), new AsyncCallbackAdapter<Country>(){
					@Override
					public void onSuccess(Country rs) {
						countryCreation.setComponent(rs);
						loading.hide();
						RootPanel.get().remove(loading);
					}
				});
			}
		});
	}
	
	public void setCountryList(List<Country> countries){
		if(null!=countries){
			for(Country co : countries){
				countryList.addItem(co.getAbbrCode()+"("+co.getName()+")",co.getId()+"");
			}
		}
	}

}
