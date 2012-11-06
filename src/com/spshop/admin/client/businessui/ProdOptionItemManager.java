package com.spshop.admin.client.businessui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.spshop.admin.client.businessui.callback.EditorChangeAdapter;
import com.spshop.model.ProductOptionItem;
import com.spshop.model.enums.BUConsts;
import com.spshop.model.enums.SelectType;

public class ProdOptionItemManager extends Composite{

	private static ProdOptionItemManagerUiBinder uiBinder = GWT
			.create(ProdOptionItemManagerUiBinder.class);
	@UiField VerticalPanel host;
	@UiField
	ItemManagerStyle style;
	private List<ProductOptionItem> optionItems;
	private SelectType optionType;
	interface ProdOptionItemManagerUiBinder extends
			UiBinder<VerticalPanel, ProdOptionItemManager> {
	}
	public ProdOptionItemManager() {
		host = uiBinder.createAndBindUi(this);
		initWidget(host);
	}
	
	interface ItemManagerStyle extends CssResource{
		
	}
	
	public void setOptionItems(List<ProductOptionItem> items){
		if(null==optionItems){
			optionItems = new ArrayList<ProductOptionItem>();
		}else{
			optionItems = items;
		}
		while(host.getWidgetCount()>1){
			host.remove(1);
		}
		
		for(ProductOptionItem item:optionItems){
			addOptionItem(item,true);
		}
	}
	
	public void addOptionItem(ProductOptionItem item,boolean isRefresh){
		if(null==item.getName()){
			item.setName("New Item");
		}
		final ProdOptionItemManager manager = this;
		if(!haveSameOption(item)){
			ProdOptionItemCreation itemCreation = new ProdOptionItemCreation(item, null==optionType?SelectType.INPUT_TEXT:optionType);
			itemCreation.addChangeListener(new EditorChangeAdapter<ProductOptionItem, ProdOptionItemCreation>(){
				@Override
				public void onChange(ProductOptionItem component,
						ProdOptionItemCreation widget) {
					if(haveSameOption(component)){
						String value = widget.getName().getValue();
						widget.getName().setValue(value.substring(0,value.length()-1));
						Window.alert("Already have this item!");
					}
				}
				@Override
				public void onDelete(ProductOptionItem component,
						ProdOptionItemCreation widget) {
					manager.getOptionItems().remove(component);
					manager.getHost().remove(widget);
				}
			});
			host.add(itemCreation);
			if(!isRefresh){
				optionItems.add(item);
			}
		}
	}

	public List<ProductOptionItem> getOptionItems() {
		return optionItems;
	}
	
	public boolean haveSameOption(ProductOptionItem item){
		for(ProductOptionItem it:optionItems){
			if(it!=item&&it.getName().equals(item.getName())){
				return true;
			}
		}
		return false;
	}

	public VerticalPanel getHost() {
		return host;
	}

	public void removeCustomizedItem() {
		ProductOptionItem re = null;
		for(ProductOptionItem it:optionItems){
			if(BUConsts.CUSTOMIZED_SIZE.equals(it.getName())){
				re = it;
			}
		}
		if(null!=re){
			optionItems.remove(re);
		}
		setOptionItems(optionItems);
	}

	public void setOptionType(SelectType optionType) {
		this.optionType = optionType;
	}

	public SelectType getOptionType() {
		return optionType;
	}
	
	
}
