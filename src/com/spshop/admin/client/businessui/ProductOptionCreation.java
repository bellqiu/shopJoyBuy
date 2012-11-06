package com.spshop.admin.client.businessui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.businessui.callback.ChangeObservable;
import com.spshop.admin.client.businessui.callback.EditorChangeAdapter;
import com.spshop.admin.client.businessui.callback.EditorChangeListener;
import com.spshop.admin.client.businessui.callback.SelectedCallBack;
import com.spshop.admin.client.rich.ColorButton;
import com.spshop.model.Component;
import com.spshop.model.Image;
import com.spshop.model.ProductOption;
import com.spshop.model.ProductOptionItem;
import com.spshop.model.enums.ImageSizeType;
import com.spshop.model.enums.SelectType;

public class ProductOptionCreation extends Composite implements ChangeObservable<ProductOption, ProductOptionCreation>{

	private static ProductOptionCreateUiBinder uiBinder = GWT
			.create(ProductOptionCreateUiBinder.class);
	
	private ProductOption option;
	
	@UiField HTMLPanel itemManagerPanel;
	@UiField ProdOptionItemManager itemManager;
	@UiField Button button;
	@UiField TextBox optionName;
	@UiField TextArea opDesc;
	@UiField TextBox OpDefaultValue;
	@UiField ListBox OpTypes;
	@UiField Button colorPick;
//	@UiField CheckBox customizedSize;
	ColorSelector selector = new ColorSelector();
	
	
	public HTMLPanel getItemManagerPanel() {
		return itemManagerPanel;
	}
	public TextBox getOptionName() {
		return optionName;
	}
	public TextArea getOpDesc() {
		return opDesc;
	}
	public TextBox getOpDefaultValue() {
		return OpDefaultValue;
	}
	public ListBox getOpTypes() {
		return OpTypes;
	}
	
	
	private Set<EditorChangeListener<ProductOption, ProductOptionCreation>> changeListeners = new TreeSet<EditorChangeListener<ProductOption, ProductOptionCreation>>(); 

	interface ProductOptionCreateUiBinder extends
			UiBinder<Widget, ProductOptionCreation> {
	}
	public ProductOptionCreation() {
		this(ProductOption.createWithItem());
	}
	public ProductOptionCreation(final ProductOption option) {
		initWidget(uiBinder.createAndBindUi(this));
		SelectType outputTypes[] = SelectType.values();
		for (SelectType selectType : outputTypes) {
			OpTypes.addItem(selectType.getTitle(), selectType.getValue());
			OpTypes.setSelectedIndex(0);
		}
		List<ProductOptionItem> items = option.getItems();
		if(null==items){
			items = new ArrayList<ProductOptionItem>();
			option.setItems(items);
		}
		itemManager.setOptionType(option.getSelectType());
		itemManager.setOptionItems(items);
		this.setOption(option);
		final ProductOptionCreation self = this;
		
		
		
		
		selector.addChangeListener(new EditorChangeAdapter<Map<String,ColorButton>, ColorSelector>(){
			@Override
			public void onChange(Map<String, ColorButton> colors,
					ColorSelector widget) {
				option.setItems(new ArrayList<ProductOptionItem>());
				self.itemManager.setOptionItems(option.getItems());
				for (ColorButton btn : colors.values()) {
					if(btn.getValue()){
						ProductOptionItem optionItem = new ProductOptionItem();
						optionItem.setName(btn.getColorName());
						optionItem.setValue(btn.getColorValue());
						optionItem.setOption(option);
						self.itemManager.addOptionItem(optionItem,false);
					}
				}
			}
			
			@Override
			public void onDelete(Map<String, ColorButton> component,
					ColorSelector widget) {
			}
		});
		
	}
	
	private boolean haveOption(ProductOptionItem item, List<ProductOptionItem> items){
		for (ProductOptionItem it : items) {
			if(it.getName().equals(item.getName())){
				return true;
			}
		}
		return false;
	}
	
	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
		ProductOptionItem item = new ProductOptionItem();
		item.setOption(option);
		itemManager.addOptionItem(item,false);
	}


	public void setOption(ProductOption option) {
		this.option = option;
		itemManager.setOptionType(option.getSelectType());
		List<ProductOptionItem> emptyItem = option.getItems();
		if(null==emptyItem){
			emptyItem= new ArrayList<ProductOptionItem>();
		}
		this.option.setItems(emptyItem);
		if(this.option.getSelectType()==SelectType.INPUT_TEXT){
			this.itemManager.setOptionItems(this.option.getItems());
			this.button.setVisible(false);
			itemManager.setVisible(false);
			colorPick.setVisible(false);
			selector.hide();
		}else if(this.option.getSelectType()==SelectType.COLOR_SINGLE){
			this.itemManager.setOptionItems(this.option.getItems());
			this.button.setVisible(false);
			itemManager.setVisible(true);
			colorPick.setVisible(true);
		}else{
			this.itemManager.setOptionItems(this.option.getItems());
			this.button.setVisible(true);
			this.button.setEnabled(true);
			itemManager.setVisible(true);
			colorPick.setVisible(false);
			selector.hide();
		}
		optionName.setValue(option.getName());
		opDesc.setValue(option.getDescription());
		OpDefaultValue.setValue(option.getDefaultValue());
		for(int i=0; i < OpTypes.getItemCount();i++){
			if(OpTypes.getValue(i).endsWith(option.getStrSelectType())){
				OpTypes.setSelectedIndex(i);
			}
		}
//		
//		if("Size".equals(option.getName())){
//			customizedSize.setEnabled(true);
//		}else{
//			customizedSize.setEnabled(false);
//		}
		
		notifyChange();
	}

	public ProductOption getOption() {
		return option;
	}
	
	@Override
	public void addChangeListener(EditorChangeListener<ProductOption, ProductOptionCreation> changeListener) {
		this.changeListeners.add(changeListener);
	}
	public void clearChangeListener() {
		changeListeners.clear();
	}
	@Override
	public void notifyChange(){
		for (EditorChangeListener<ProductOption, ProductOptionCreation> listener : changeListeners) {
			listener.onChange(option, this);
		}
	}

	@UiHandler("optionName")
	void onOptionNameChange(KeyUpEvent event) {
		this.option.setName(optionName.getValue());
		notifyChange();
	}
	@UiHandler("opDesc")
	void onOpDescChange(ChangeEvent event) {
		this.option.setDescription(opDesc.getValue());
		notifyChange();
	}
	@UiHandler("OpDefaultValue")
	void onOpDefaultValueChange(ChangeEvent event) {
		this.option.setDefaultValue(OpDefaultValue.getValue());
		notifyChange();
	}
	@UiHandler("OpTypes")
	void onOpTypesChange(ChangeEvent event) {
		this.option.setSelectType(SelectType.valueOf(OpTypes.getValue(OpTypes.getSelectedIndex())));
		setOption(option);
		notifyChange();
	}
	@Override
	public void notifyDelete() {
		//Do nothing
	}
	@UiHandler("colorPick")
	void onColorPickClick(ClickEvent event) {
		CommandFactory.popUpImageQuery(true, new SelectedCallBack() {
			@Override
			public void callBack(List<Component> selectedItems) {
				List<ProductOptionItem> items = new ArrayList<ProductOptionItem>();
				for (Component component : selectedItems) {
					Image image = (Image) component;
					if(image.getSizeType() == ImageSizeType.PRODUCT_COLOR){
						ProductOptionItem item = new ProductOptionItem();
						item.setOption(option);
						item.setName(image.getName());
						item.setValue(image.getSmallUrl());
						items.add(item);
					}
				}
				
				if(null != option.getItems()){
					for (ProductOptionItem productOptionItem : option.getItems()) {
						boolean hasOpt = false;
						if(null != productOptionItem.getValue() && productOptionItem.getValue().endsWith(".jpg")){
							for (ProductOptionItem productOptionItem2 : items) {
								if(productOptionItem.getValue().equalsIgnoreCase(productOptionItem2.getValue())){
									hasOpt = true;
								}
							}
						}
						if(!hasOpt){
							items.add(productOptionItem);
						}
							
					}
				}
				
				option.setItems(items);
				itemManager.setOptionItems(items);
			}
		}).execute();
		//selector.setPopupPosition(event.getClientX(), event.getClientY());
		//selector.setItems(option.getItems());
		//selector.setAnimationEnabled(true);
		//selector.setGlassEnabled(true);
		//selector.setAutoHideEnabled(false);
		//selector.setModal(true);
		//selector.show();
	}
	
	/*@UiHandler("customizedSize")
	void onCustomizedSizeClick(ClickEvent event) {
		toggleCumtomizedSize(customizedSize.getValue());
	}
	
	private void toggleCumtomizedSize(boolean customized){
		if(customized){
			ProductOptionItem item = new ProductOptionItem();
			item.setName(BUConsts.CUSTOMIZED_SIZE);
			item.setValue(BUConsts.CUSTOMIZED_SIZE);
			itemManager.addOptionItem(item,false);
		}else{
			itemManager.removeCustomizedItem();
		}
	}*/
}
