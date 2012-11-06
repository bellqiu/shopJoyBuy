package com.spshop.admin.client.businessui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.SelectedCallBack;
import com.spshop.model.Component;
import com.spshop.model.Product;
import com.spshop.model.TabProduct;

public class TopSellingManager extends ObservableComposite<TabProduct, TopSellingManager> {

	private static TopSellingManagerUiBinder uiBinder = GWT
			.create(TopSellingManagerUiBinder.class);
	@UiField Button productPicker;
	@UiField FlowPanel host;
	@UiField ProductInline style;
	@UiField Button save;
	@UiField TextBox tabName;
	@UiField Grid namePanel;
	private boolean showName;
	private boolean showBTN;
	private boolean showPicker;

	interface TopSellingManagerUiBinder extends
			UiBinder<Widget, TopSellingManager> {
	}
	
	interface ProductInline extends CssResource{
		String productItem();
		String productDesc();
	}

	public TopSellingManager() {
		initWidget(uiBinder.createAndBindUi(this));
		if(!isShowName()){
			namePanel.setVisible(false);
		}
	}

	@UiHandler("productPicker")
	void onProductPickerClick(ClickEvent event) {
		
		final TopSellingManager self = this;
		CommandFactory.popUpQueryProduct(true, new SelectedCallBack() {
			@Override
			public void callBack(List<Component> selectedItems) {
				self.host.clear();
				
				for (Component component : selectedItems) {
					Product p = (Product) component;
					if(!self.getComponet().getProducts().contains(p)){
						self.getComponet().getProducts().add(p);
					}
				}
				
				for (Product p : self.getComponet().getProducts()) {
					addProduct(p);
				}
				
			}
		}).execute();
	}

	@Override
	public void setComponent(TabProduct component) {
		host.clear();
		tabName.setValue(null==component.getName()?"":component.getName());
		this.component = component;
		if(null==component.getProducts()){
			this.component.setProducts(new ArrayList<Product>());
		}
		for(Product product : this.component.getProducts()){
			addProduct(product);
		}
	}
	
	private void addProduct(final Product product){
		final TopSellingManager self = this;
		final VerticalPanel panel = new VerticalPanel();
		final SimplePanel sp = new SimplePanel();
		sp.add(panel);
		sp.addStyleName(style.productItem());
		panel.add(new com.google.gwt.user.client.ui.Image(product.getImages().get(0).getSmallUrl()));
		
		Button btn = new Button("Remove");
		btn.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				getComponet().getProducts().remove(product);
				self.host.remove(sp);
			}
		});
		HTML title = new HTML(product.getTitle());
		title.addStyleName(style.productDesc());
		panel.add(title);
		panel.add(btn);
		host.add(sp);
	}
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		final TopSellingManager self = this;
		CommandFactory.lock("Process..").execute();
		AdminWorkspace.ADMIN_SERVICE_ASYNC.saveTopSelling(getComponet(), new AsyncCallbackAdapter<TabProduct>(){
			@Override
			public void onSuccess(TabProduct rs) {
				self.setComponent(rs);
				CommandFactory.release().execute();
				notifyChange();
			}
		});
	}
	@UiHandler("tabName")
	void onTabNameKeyUp(KeyUpEvent event) {
		getComponet().setName(tabName.getValue());
		notifyChange();
	}
	
	public void setShowName(boolean showName) {
		this.showName = showName;
		namePanel.setVisible(showName);
	}
	
	public void setShowButton(boolean showBTN) {
		this.showBTN = showBTN;
		save.setVisible(showBTN);
	}
	
	public boolean isShowButton() {
		return showBTN;
	}

	public boolean isShowName() {
		return showName;
	}

	public void setShowPicker(boolean showPicker) {
		this.showPicker = showPicker;
		productPicker.setVisible(showPicker);
	}

	public boolean isShowPicker() {
		return showPicker;
	}
}
