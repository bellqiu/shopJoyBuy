package com.spshop.admin.client.businessui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.EditorChangeAdapter;
import com.spshop.model.TabProduct;
import com.spshop.model.TabSelling;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class DashboardSellingManager extends ObservableComposite<TabSelling, DashboardSellingManager>{

	private static DashboardSellingManagerUiBinder uiBinder = GWT
			.create(DashboardSellingManagerUiBinder.class);
	@UiField Button addTab;
	@UiField TabLayoutPanel host;
	@UiField Button remove;
	@UiField Button save;

	interface DashboardSellingManagerUiBinder extends
			UiBinder<Widget, DashboardSellingManager> {
	}

	public DashboardSellingManager() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("addTab")
	void onAddTabClick(ClickEvent event) {
		TabProduct tproduct = new TabProduct();
		tproduct.setName("New Tab");
		if(!getComponet().getTabs().contains(tproduct)){
			addTab(tproduct);
			getComponet().getTabs().add(tproduct);
		}
	}
	
	private void addTab(TabProduct tproduct){
		final TopSellingManager tab = new TopSellingManager();
		final DashboardSellingManager self = this;
		tab.addChangeListener(new EditorChangeAdapter<TabProduct, TopSellingManager>(){
			@Override
			public void onChange(TabProduct component, TopSellingManager widget) {
				self.host.setTabHTML(self.host.getWidgetIndex(widget), component.getName());
			}
		});
		tab.setShowName(true);
		tab.setShowButton(false);
		tab.setComponent(tproduct);
		host.add(tab , tproduct.getName());
		//getComponet().getTabs().add(tproduct);
		host.selectTab(tab);
	}

	@Override
	public void setComponent(TabSelling component) {
		this.component = component;
		while(host.getWidgetCount()>0){
			host.remove(0);
		}
		if(null == component.getTabs()){
			this.component.setTabs(new ArrayList<TabProduct>());
		}
		for(TabProduct product : component.getTabs()){
			addTab(product);
		}
	}
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		CommandFactory.lock("Processing").execute();
		final DashboardSellingManager self = this;
		AdminWorkspace.ADMIN_SERVICE_ASYNC.saveTabSelling(getComponet(), new AsyncCallbackAdapter<TabSelling>(){
			@Override
			public void onSuccess(TabSelling rs) {
				self.setComponent(rs);
				CommandFactory.release().execute();
			}
		});
	}
	@UiHandler("remove")
	void onRemoveClick(ClickEvent event) {
		if(Window.confirm("Are you sure?")){
			getComponet().getTabs().remove(((TopSellingManager)host.getWidget(host.getSelectedIndex())).getComponet());
			host.remove(host.getSelectedIndex());
		}
	}
}
