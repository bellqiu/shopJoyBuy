package com.spshop.admin.client.businessui;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.PopWindow;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.model.Country;
import com.google.gwt.user.client.ui.DoubleBox;

public class CountryCreation extends ObservableComposite<Country, CountryCreation> {

	private static CountryCreationUiBinder uiBinder = GWT
			.create(CountryCreationUiBinder.class);
	@UiField TextBox countryName;
	@UiField TextBox countryAbbr;
	@UiField Button save;
	@UiField Button newCountry;
	@UiField DoubleBox dePrice;
	@UiField DoubleBox hDPrice;
	@UiField DoubleBox freeBasicD;
	@UiField DoubleBox freeHD;
	
	
	interface CountryCreationUiBinder extends UiBinder<Widget, CountryCreation> {
	}
	
	public CountryCreation() {
		initWidget(uiBinder.createAndBindUi(this));
		if(null==getComponet()){
			setComponent(new Country());
		}
	}

	@Override
	public void setComponent(Country component) {
		this.component = component;
		
		if(component.getId()<1){
			component.setCreateDate(new Date());
			component.setUpdateDate(new Date());
		}else{
			component.setUpdateDate(new Date());
		}
		
		countryName.setText(component.getName());
		countryAbbr.setText(component.getAbbrCode());
		dePrice.setValue((double)component.getDePrice());
		hDPrice.setValue((double)component.getAdDePrice());
		freeBasicD.setValue((double)component.getFreeDePrice());
		freeHD.setValue((double)component.getFreeAdDePrice());
	}

	@UiHandler("countryName")
	void onCountryNameKeyUp(KeyUpEvent event) {
		getComponet().setName(countryName.getText());
	}
	@UiHandler("countryAbbr")
	void onCountryAbbrKeyUp(KeyUpEvent event) {
		getComponet().setAbbrCode(countryAbbr.getText());
	}
	@UiHandler("save")
	void onButtonClick(ClickEvent event) {

		final PopWindow loading = PopWindow.createLoading("Waiting").lock();
		AdminWorkspace.ADMIN_SERVICE_ASYNC.saveCountry(getComponet(), new AsyncCallbackAdapter<Country>(){
			@Override
			public void onSuccess(Country rs) {
				setComponent(rs);
				notifyChange();
				loading.hide();
				RootPanel.get().remove(loading);
				save.setText("Update");
			}
		});
	
	}
	@UiHandler("newCountry")
	void onNewCountryClick(ClickEvent event) {
		setComponent(new Country());
		save.setText("Save");
	}
	@UiHandler("dePrice")
	void onDePriceKeyUp(KeyUpEvent event) {
		getComponet().setDePrice(dePrice.getValue().floatValue());
	}
	@UiHandler("hDPrice")
	void onHDPriceKeyUp(KeyUpEvent event) {
		getComponet().setAdDePrice(hDPrice.getValue().floatValue());
	}
	@UiHandler("freeHD")
	void onFreeHDKeyUp(KeyUpEvent event) {
		getComponet().setFreeAdDePrice(freeHD.getValue().floatValue());
	}
	@UiHandler("freeBasicD")
	void onFreeBasicDKeyUp(KeyUpEvent event) {
		getComponet().setFreeDePrice(freeBasicD.getValue().floatValue());
	}
}
