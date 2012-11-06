package com.spshop.admin.client.businessui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.businessui.callback.EditorChangeAdapter;
import com.spshop.model.ProductOption;

public class ProdOptionManager extends ResizeComposite{
	
	//private TreeMap<String, ProductOption> options = new TreeMap<String, ProductOption>();
	private List<ProductOption> options = new ArrayList<ProductOption>();
	private static ProdOptionManagerUiBinder uiBinder = GWT
			.create(ProdOptionManagerUiBinder.class);

	interface ProdOptionManagerUiBinder extends
			UiBinder<Widget, ProdOptionManager> {
	}
	
	@UiField
	TabLayoutPanel host;
	
	public ProdOptionManager() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public void setOptions(List<ProductOption> options){
		this.host.clear();
		this.options = options;
		
		Iterator<Widget> ite = host.iterator();
		
		while(ite.hasNext()) {
			host.remove(ite.next());
		}
		
		if(null!=options){
			for (ProductOption productOption : options) {
				add(productOption,true);
			}
		}
	}
	
	
	public void removeCurrentOption(){
		int index = host.getSelectedIndex();
		options.remove(index);
		host.remove(index);
		if(index>0){
			host.selectTab(index-1);
		}
	}
	
	public void add(ProductOption option, boolean isRefresh) {
		if(!containsOption(option)||isRefresh){
			ProductOptionCreation creation =  new ProductOptionCreation(option);
			creation.addChangeListener(new EditorChangeAdapter<ProductOption, ProductOptionCreation>() {
				@Override
				public void onChange(ProductOption component, ProductOptionCreation widget) {
					if(haveSameOption(component)){
						String opValue = widget.getOptionName().getValue();
						widget.getOptionName().setValue(opValue.substring(0,opValue.length()-1));
						Window.alert("Option Exit!");
					}else{
						host.setTabHTML(options.indexOf(component), component.getName());
					}
				}
			});
			host.add(creation,option.getName());
			if(!isRefresh){
				options.add(option);
			}
			selectOption(option);
		}
	}
	
	private void selectOption(ProductOption option){
		this.host.selectTab(options.indexOf(option));
	}
	
	public boolean containsOption(ProductOption option){
		if(null!=getOption(option.getName())){
			return true;
		}
		return false;
	}
	
	public boolean haveSameOption(ProductOption option){
		for(ProductOption op : options){
			if(op!=option&&op.getName().equals(option.getName())){
				return true;
			}
		}
		return false;
	}
	
	public ProductOption getOption(String option){
		for(ProductOption op : options){
			if(op.getName().equals(option)){
				return op;
			}
		}
		return null;
	}
}
