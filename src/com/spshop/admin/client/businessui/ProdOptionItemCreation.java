package com.spshop.admin.client.businessui;

import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.businessui.callback.ChangeObservable;
import com.spshop.admin.client.businessui.callback.EditorChangeListener;
import com.spshop.model.ProductOptionItem;
import com.spshop.model.enums.SelectType;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class ProdOptionItemCreation extends Composite implements ChangeObservable<ProductOptionItem, ProdOptionItemCreation>{

	private static ProdOptionItemCreationUiBinder uiBinder = GWT
			.create(ProdOptionItemCreationUiBinder.class);
	@UiField TextBox name;
	public TextBox getName() {
		return name;
	}

	@UiField Button delete;
	@UiField HorizontalPanel value;
	@UiField DoubleBox priceChange;

	private ProductOptionItem optionItem;
	
	private Set<EditorChangeListener<ProductOptionItem, ProdOptionItemCreation>> listeners= new TreeSet<EditorChangeListener<ProductOptionItem,ProdOptionItemCreation>>();
	
	interface ProdOptionItemCreationUiBinder extends
			UiBinder<Widget, ProdOptionItemCreation> {
	}

	public ProdOptionItemCreation(ProductOptionItem item, SelectType type) {
		initWidget(uiBinder.createAndBindUi(this));
		setOptionItem(item, type);
	}

	public void setOptionItem(ProductOptionItem optionItem, SelectType type) {
		this.optionItem = optionItem;
		if(type == SelectType.COLOR_SINGLE){
			if(null != optionItem.getValue() && optionItem.getValue().endsWith(".jpg")){
				Image img = new Image(optionItem.getValue());
				img.setSize("20px", "20px");
				this.value.add(img); 
			}
		}else{
			final TextBox tb = new TextBox();
			tb.setValue(optionItem.getValue());
			tb.addKeyUpHandler(new KeyUpHandler() {
				@Override
				public void onKeyUp(KeyUpEvent event) {
					getOptionItem().setValue(tb.getValue());
					notifyChange();
				}
			});
			this.value.add(tb);
		}
		this.name.setValue(optionItem.getName());
		this.priceChange.setValue(Double.valueOf(optionItem.getPriceChange()));
	}

	public ProductOptionItem getOptionItem() {
		return optionItem;
	}

	@UiHandler("name")
	void onNameKeyUp(KeyUpEvent event) {
		this.optionItem.setName(name.getValue());
		notifyChange();
	}

	@Override
	public void addChangeListener(
			EditorChangeListener<ProductOptionItem, ProdOptionItemCreation> listener) {
		listeners.add(listener);
	}

	@Override
	public void notifyChange() {
		for (EditorChangeListener<ProductOptionItem, ProdOptionItemCreation> listener : listeners) {
			listener.onChange(optionItem, this);
		}
	}

	@Override
	public void notifyDelete() {
		for (EditorChangeListener<ProductOptionItem, ProdOptionItemCreation> listener : listeners) {
			listener.onDelete(optionItem, this);
		}
	}
	
	@UiHandler("delete")
	void onDeleteClick(ClickEvent event) {
		notifyDelete();
	}
}
