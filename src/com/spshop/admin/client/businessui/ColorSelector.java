package com.spshop.admin.client.businessui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.businessui.callback.ChangeObservable;
import com.spshop.admin.client.businessui.callback.EditorChangeListener;
import com.spshop.admin.client.rich.ColorButton;
import com.spshop.model.ProductOptionItem;

public class ColorSelector extends PopupPanel implements ChangeObservable<Map<String, ColorButton>, ColorSelector>{
	
	private Set<EditorChangeListener<Map<String, ColorButton>, ColorSelector>> listeners= new TreeSet<EditorChangeListener<Map<String, ColorButton>, ColorSelector>>();

	private static ColorSelectorUiBinder uiBinder = GWT
			.create(ColorSelectorUiBinder.class);
	
	private Map<String, ColorButton> colors = new HashMap<String, ColorButton>();
	private List<ProductOptionItem> items ;
	 
	@UiField ColorButton black;
	@UiField ColorButton blue;
	@UiField ColorButton browncoffee;
	@UiField ColorButton burgundy;
	@UiField ColorButton champagne;
	@UiField ColorButton chocolate;
	@UiField ColorButton daffodil;
	@UiField ColorButton darkgreen;
	@UiField ColorButton darknavy;
	@UiField ColorButton fuchsia;
	@UiField ColorButton gold;
	@UiField ColorButton grape;
	@UiField ColorButton huntergreen;
	@UiField ColorButton ivory;
	@UiField ColorButton silver;
	@UiField ColorButton white;
	@UiField ColorButton sage;
	@UiField ColorButton royalblue;
	@UiField ColorButton pink;
	@UiField ColorButton red;
	@UiField ColorButton orange;
	@UiField ColorButton lilac;
	@UiField ColorButton lightskyblue;
	@UiField ColorButton lavender;
	@UiField Button seletor;
	@UiField Grid table;
	@UiField SelectionStyle selectionStyle;

	interface ColorSelectorUiBinder extends UiBinder<Widget, ColorSelector> {
	}
	
	interface SelectionStyle extends CssResource {
		String selected();
		String popup();
	}

	public ColorSelector() {
		setWidget(uiBinder.createAndBindUi(this));
		colors.put(black.getColorName(),black);
		colors.put(blue.getColorName(),blue);
		colors.put(browncoffee.getColorName(),browncoffee);
		colors.put(burgundy.getColorName(),burgundy);
		colors.put(champagne.getColorName(),champagne);
		colors.put(chocolate.getColorName(),chocolate);
		colors.put(daffodil.getColorName(),daffodil);
		colors.put(darkgreen.getColorName(),darkgreen);
		colors.put(darknavy.getColorName(),darknavy);
		colors.put(fuchsia.getColorName(),fuchsia);
		colors.put(gold.getColorName(),gold);
		colors.put(grape.getColorName(),grape);
		colors.put(huntergreen.getColorName(),huntergreen);
		colors.put(ivory.getColorName(),ivory);
		colors.put(silver.getColorName(),silver);
		colors.put(white.getColorName(),white);
		colors.put(sage.getColorName(),sage);
		colors.put(royalblue.getColorName(),royalblue);
		colors.put(pink.getColorName(),pink);
		colors.put(red.getColorName(),red);
		colors.put(orange.getColorName(),orange);
		colors.put(lilac.getColorName(),lilac);
		colors.put(lightskyblue.getColorName(),lightskyblue);
		colors.put(lavender.getColorName(),lavender);
		addStyleName(selectionStyle.popup());
	}

	@Override
	public void addChangeListener(
			EditorChangeListener<Map<String, ColorButton>, ColorSelector> listener) {
		listeners.add(listener);
	}

	@Override
	public void notifyChange() {
		for(EditorChangeListener<Map<String, ColorButton>, ColorSelector> ls:listeners){
			ls.onChange(colors, this);
		}
	}

	@Override
	public void notifyDelete() {
		for(EditorChangeListener<Map<String, ColorButton>, ColorSelector> ls:listeners){
			ls.onDelete(colors, this);
		}
	}
	@UiHandler("seletor")
	void onSeletorClick(ClickEvent event) {
		notifyChange();
		hide();
	}

	public void setItems(List<ProductOptionItem> items) {
		this.items = items;
		for (ColorButton btn : colors.values()) {
			btn.setDown(false);
			btn.setValue(false);
		}
		for (ProductOptionItem productOptionItem : items) {
			ColorButton button = colors.get(productOptionItem.getName());
			if(null!=button){
				button.setDown(true);
				button.setValue(true);
			}
		}
	}

	public List<ProductOptionItem> getItems() {
		return items;
	}
}
