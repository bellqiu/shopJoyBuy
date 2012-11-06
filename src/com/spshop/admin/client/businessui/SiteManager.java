package com.spshop.admin.client.businessui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.SelectedCallBack;
import com.spshop.admin.client.rich.RichText;
import com.spshop.admin.shared.LoginInfo;
import com.spshop.model.Component;
import com.spshop.model.Site;
import com.google.gwt.user.client.ui.DoubleBox;

public class SiteManager extends ObservableComposite<Site, SiteManager>{

	private static SiteManagerUiBinder uiBinder = GWT
			.create(SiteManagerUiBinder.class);
	@UiField Button updateCategoryCache;
	@UiField Label cacheStatus;
	@UiField FormPanel updateCatlogForm;
	@UiField Button logoPicker;
	@UiField SimplePanel logoImage;
	@UiField Button featuredPicker;
	@UiField SimplePanel featuredImage;
	@UiField Button deliverPicker;
	@UiField SimplePanel deliverImage;
	@UiField TextBox featuredUrl;
	@UiField TextBox deliverUrl;
	@UiField RichText siderBar;
	@UiField RichText siteFooter;
	@UiField Button save;
	@UiField DoubleBox freeDeliveryPrice;

	interface SiteManagerUiBinder extends UiBinder<Widget, SiteManager> {
	}

	public SiteManager() {
		initWidget(uiBinder.createAndBindUi(this));
		updateCatlogForm.setAction(AdminWorkspace.getSilteUrl()+"/"+"initServlet");
		setComponent(AdminWorkspace.loginInfo.getSite());
	}

	@UiHandler("updateCategoryCache")
	void onUpdateCategoryCacheClick(ClickEvent event) {
		updateCatlogForm.submit();
	}
	@UiHandler("updateCatlogForm")
	void onUpdateCatlogFormSubmitComplete(SubmitCompleteEvent event) {
		cacheStatus.setText("Updated");
	}

	@Override
	public void setComponent(Site component) {
		this.component = component;
		if(component.getLogo()!=null){
			logoImage.clear();
			logoImage.add(new Image(component.getLogo().getNoChangeUrl(),0,0,210,75));
		}
		if(component.getDelivery()!=null){
			deliverImage.clear();
			deliverImage.add(new Image(component.getDelivery().getNoChangeUrl(),0,0,165,50));
		}
		if(component.getFeaturedCat()!=null){
			featuredImage.clear();
			featuredImage.add(new Image(component.getFeaturedCat().getNoChangeUrl(),0,0,165,50));
		}
		deliverUrl.setValue(component.getDeliveryURL());
		featuredUrl.setValue(component.getFeaturedCatURL());
		siderBar.setHTML(component.getSideBar());
		siteFooter.setHTML(component.getSiteFooter());
		freeDeliveryPrice.setValue(Double.valueOf(component.getFreeDeliveryPrice()));
	}
	@UiHandler("logoPicker")
	void onLogoPickerClick(ClickEvent event) {
		CommandFactory.popUpImageQuery(false, new SelectedCallBack() {
			@Override
			public void callBack(List<Component> selectedItems) {
				logoImage.clear();
				com.spshop.model.Image image = (com.spshop.model.Image) selectedItems.get(0);
				logoImage.add(new Image(image.getNoChangeUrl(),0,0,210,75));
				getComponet().setLogo(image);
			}
		}).execute();
	}
	@UiHandler("featuredPicker")
	void onFeaturedPickerClick(ClickEvent event) {
		CommandFactory.popUpImageQuery(false, new SelectedCallBack() {
			@Override
			public void callBack(List<Component> selectedItems) {
				featuredImage.clear();
				com.spshop.model.Image image = (com.spshop.model.Image) selectedItems.get(0);
				featuredImage.add(new Image(image.getNoChangeUrl(),0,0,165,50));
				getComponet().setFeaturedCat(image);
			}
		}).execute();
	}
	@UiHandler("deliverPicker")
	void onDeliverPickerClick(ClickEvent event) {
		CommandFactory.popUpImageQuery(false, new SelectedCallBack() {
			@Override
			public void callBack(List<Component> selectedItems) {
				deliverImage.clear();
				com.spshop.model.Image image = (com.spshop.model.Image) selectedItems.get(0);
				deliverImage.add(new Image(image.getNoChangeUrl(),0,0,165,50));
				getComponet().setDelivery(image);
			}
		}).execute();
	}
	
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		CommandFactory.lock("save....").execute();
		AdminWorkspace.ADMIN_SERVICE_ASYNC.saveSite(getComponet(), new AsyncCallbackAdapter<Site>(){
			@Override
			public void onSuccess(Site rs) {
				AdminWorkspace.ADMIN_SERVICE_ASYNC.getLoginInfo(new AsyncCallback<LoginInfo>() {
						public void onSuccess(LoginInfo loginInfo) {
							setComponent(loginInfo.getSite());
							CommandFactory.release().execute();
						}
						public void onFailure(Throwable throwable) {
							Window.Location.assign(AdminWorkspace.LOGIN_URL);
						}
					});
			  
			}
		});
	}
	
	@UiHandler("featuredUrl")
	void onFeaturedUrlKeyUp(KeyUpEvent event) {
		component.setFeaturedCatURL(featuredUrl.getValue());
	}
	@UiHandler("deliverUrl")
	void onDeliverUrlKeyUp(KeyUpEvent event) {
		component.setDeliveryURL(deliverUrl.getValue());
	}
	@UiHandler("siderBar")
	void onSiderBarBlur(BlurEvent event) {
		component.setSideBar(siderBar.getHTML());
	}
	@UiHandler("siteFooter")
	void onRichTextBlur(BlurEvent event) {
		component.setSiteFooter(siteFooter.getHTML());
	}
	@UiHandler("freeDeliveryPrice")
	void onFreeDeliveryPriceKeyUp(KeyUpEvent event) {
		component.setFreeDeliveryPrice(freeDeliveryPrice.getValue().floatValue());
	}
}
