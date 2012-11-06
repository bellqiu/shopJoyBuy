package com.spshop.admin.client.businessui;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.businessui.callback.EditorChangeAdapter;
import com.spshop.model.Product;
import com.spshop.model.ProductProperty;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;

public class ProdAttributeManager extends Composite {

	private static ProdAttributeManagerUiBinder uiBinder = GWT
			.create(ProdAttributeManagerUiBinder.class);
	@UiField VerticalPanel host;
	@UiField Button addButton;
	
	private Product product;

	interface ProdAttributeManagerUiBinder extends
			UiBinder<Widget, ProdAttributeManager> {
	}

	public ProdAttributeManager() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void setProduct(Product product) {
		this.product = product;
		host.clear();
		if(null==product.getProperties()){
			product.setProperties(new ArrayList<ProductProperty>());
		}
		for (ProductProperty productProperty : product.getProperties()) {
			host.add(createCreation(productProperty));
		}
	}

	@UiHandler("addButton")
	void onAddButtonClick(ClickEvent event) {
		ProductProperty property = new ProductProperty();
		property.setProduct(product);
		product.getProperties().add(property);
		host.add(createCreation(property));
	}
	
	private ProdAttributeCreation createCreation(ProductProperty productProperty){
		ProdAttributeCreation attributeCreation = new ProdAttributeCreation(productProperty);
		attributeCreation.addChangeListener(new EditorChangeAdapter<ProductProperty, ProdAttributeCreation>(){
			@Override
			public void onChange(ProductProperty component,
					ProdAttributeCreation widget) {
			}
			@Override
			public void onDelete(ProductProperty component,
					ProdAttributeCreation widget) {
				host.remove(widget);
				product.getProperties().remove(component);
			}
		});
		return attributeCreation;
	}
	
	
}
