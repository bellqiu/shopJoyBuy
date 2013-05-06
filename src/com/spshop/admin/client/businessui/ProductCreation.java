package com.spshop.admin.client.businessui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DoubleBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.PopWindow;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.EditorChangeAdapter;
import com.spshop.admin.client.businessui.callback.SelectedCallBack;
import com.spshop.admin.client.rich.RichText;
import com.spshop.model.Category;
import com.spshop.model.Component;
import com.spshop.model.HTML;
import com.spshop.model.Image;
import com.spshop.model.Product;
import com.spshop.model.ProductOption;
import com.spshop.model.TabProduct;
import com.spshop.model.enums.ImageSizeType;
import com.spshop.model.enums.SelectType;

public class ProductCreation extends Composite{

	private static ImageCreationUiBinder uiBinder = GWT
			.create(ImageCreationUiBinder.class);
	@UiField Button addOption;
	@UiField Button pickRelatedProduct;
	@UiField ProdOptionManager optionManager;
	@UiField Button removeOption;
	@UiField Button Save;
	@UiField Button copy;
	@UiField ProdAttributeManager attributeManager;
	@UiField TextBox name;
	@UiField TextBox title;
	@UiField TextArea keywords;
	@UiField TextArea tags;
	@UiField RichText detail;
	@UiField CategoryPicker categoryPicker;
	@UiField ProdImageManager imageManager;
	@UiField DoubleBox prodPrice;
	@UiField DoubleBox prodActualPrice;
	@UiField CheckBox showComments;
	@UiField TopSellingManager relatedProduct;
	@UiField TabLayoutPanel host;
	@UiField CheckBox showLikeButton;
	@UiField Button manualPicker;
	@UiField Button removeManual;
	@UiField TabLayoutPanel manual;
	@UiField ListBox optionTypes;
	@UiField CheckBox enable;
	
	private Product product;

	interface ImageCreationUiBinder extends UiBinder<Widget, ProductCreation> {
	}

	public ProductCreation(Product product) {
		initWidget(uiBinder.createAndBindUi(this));
		setProduct(product);
		TabProduct tp= new TabProduct();
		relatedProduct.setComponent(tp);
		relatedProduct.setShowName(true);
		relatedProduct.setShowButton(false);
		relatedProduct.setShowPicker(false);
		
		
		//optionTypes.setValue(0, "Native");
		//optionTypes.setValue(1, "Suit");
		//optionTypes.setValue(2, "Other");
		
		optionTypes.addItem("Native", "0");
		optionTypes.addItem("Suit Opt", "1");
		optionTypes.addItem("Suit pants", "2");
		optionTypes.addItem("Vest Opt", "3");
		optionTypes.addItem("Woman suit Opt", "4");
		
		optionTypes.setSelectedIndex(product.getOptType());
		
		final ProductCreation self = this;
		host.addBeforeSelectionHandler(new BeforeSelectionHandler<Integer>() {
			@Override
			public void onBeforeSelection(BeforeSelectionEvent<Integer> event) {
				if(event.getItem().intValue()==3&&self.getProduct().getTabProductKey()>0){
					final PopWindow load = PopWindow.createLoading("Update recommend").lock();
					AdminWorkspace.ADMIN_SERVICE_ASYNC.getTopSelling(self.getProduct().getTabProductKey(), 
					new AsyncCallbackAdapter<TabProduct>(){
						@Override
						public void onSuccess(TabProduct rs) {
							self.relatedProduct.setComponent(rs);
							load.hide();
							RootPanel.get().remove(load);
						}
					});
				}
				
				if(event.getItem().intValue()==4&&self.getProduct().getManualKey()!=null&&self.getProduct().getManualKey().length()>0){
					final PopWindow load = PopWindow.createLoading("Update recommend").lock();
					AdminWorkspace.ADMIN_SERVICE_ASYNC.getHTMLs(self.getProduct().getManualKey(), 
					new AsyncCallbackAdapter<List<HTML>>(){
						@Override
						public void onSuccess(List<HTML> htmls) {
							getManual().clear();
							/*
							getManual().add(new com.google.gwt.user.client.ui.HTML(rs.getContent()));
							load.hide();*/
							for (HTML html : htmls) {
								getManual().add(createManualPanel(html.getContent()), html.getName());
							}
							RootPanel.get().remove(load);
						}
					});
				}
			}
		});
		
		relatedProduct.addChangeListener(new EditorChangeAdapter<TabProduct, TopSellingManager>(){
			@Override
			public void onChange(TabProduct component, TopSellingManager widget) {
				setTabProductKey(component.getId());
			}
		});
	}
	
	private void setTabProductKey(long key){
		product.setTabProductKey(key);
	}
	
	@UiHandler("addOption")
	void onAddOptionClick(ClickEvent event) {
		ProductOption productOption = ProductOption.createWithItem();
		productOption.setSelectType(SelectType.INPUT_TEXT);
		productOption.setProduct(product);
		optionManager.add(productOption,false);
	}
	@UiHandler("removeOption")
	void onRemoveOptionClick(ClickEvent event) {
		if(Window.confirm("Are your sure!")){
			optionManager.removeCurrentOption();
		}
	}
	
	private ScrollPanel createManualPanel(String html){
		ScrollPanel sp = new ScrollPanel();
		sp.setHeight("400px");
		sp.add(new com.google.gwt.user.client.ui.HTML(html));
		return sp;
	}
	
	public void setProduct(final Product product) {
		this.product = product;
		product.setUpdateDate(new Date());
		if(product.getId()>0){
			Save.setText("Update");
			copy.setVisible(true);
		}else{
			product.setCreateDate(new Date());
			this.product.setSite(AdminWorkspace.loginInfo.getSite());
			copy.setVisible(false);
		}
		List<ProductOption> options = product.getOptions();
		if(null==options){
			options = new ArrayList<ProductOption>();
			product.setOptions(options);
		}
		
		if(null==product.getCategories()){
			product.setCategories(new ArrayList<Category>());
		}
		
		if(null==product.getImages()){
			product.setImages(new ArrayList<Image>());
		}
		
		optionTypes.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent e) {
				getProduct().setOptType(getOptType().getSelectedIndex());
			}
		});
		
		name.setValue(product.getName());
		title.setValue(product.getTitle());
		keywords.setValue(product.getKeywords());
		tags.setValue(product.getTags());
		detail.setHTML(product.getDetail());
		optionManager.setOptions(options);
		attributeManager.setProduct(product);
		categoryPicker.setComponent(product.getCategories());
		imageManager.setComponent(product.getImages());
		prodPrice.setValue(product.getPrice());
		prodActualPrice.setValue(product.getActualPrice());
		showComments.setValue(product.isShowComments());
		showLikeButton.setValue(product.isShowlikeButton());
		enable.setValue(product.isDeleted());
	}
	public Product getProduct() {
		return product;
	}
	@UiHandler("Save")
	void onSaveClick(ClickEvent event) {
		if(isHaveDiffrientTypeImage()){
			PopWindow window = new PopWindow("Error", new com.google.gwt.user.client.ui.HTML("Cannot add two type images to one product! Please Remove."), true, true);
			window.center();
			return;
		}
		CommandFactory.lock("Save product").execute();
		AdminWorkspace.ADMIN_SERVICE_ASYNC.saveProduct(product, new AsyncCallbackAdapter<Product>() {
			@Override
			public void onSuccess(Product result) {
				setProduct(result);
				CommandFactory.release().execute();
			}
		});
	}
	@UiHandler("copy")
	void onCopyClick(ClickEvent event) {
		CommandFactory.lock("Save product").execute();
		Product product = getProduct().deepCopy();
		setProduct(product);
		Save.setText("Save");
		CommandFactory.release().execute();
	}
	@UiHandler("keywords")
	void onKeywordsKeyUp(KeyUpEvent event) {
		product.setKeywords(keywords.getValue());
	}
	@UiHandler("tags")
	void onTagsKeyUp(KeyUpEvent event) {
		product.setTags(tags.getValue());
	}
	@UiHandler("title")
	void onTitleKeyUp(KeyUpEvent event) {
		product.setTitle(title.getValue());
	}
	@UiHandler("detail")
	void onDetailBlur(BlurEvent event) {
		product.setDetail(detail.getHTML());
	}
	@UiHandler("name")
	void onNameKeyUp(KeyUpEvent event) {
		product.setName(name.getValue());
	}
	@UiHandler("showComments")
	void onShowCommentsClick(ClickEvent event) {
		product.setShowComments(showComments.getValue());
	}
	@UiHandler("showLikeButton")
	void onShowLikeButtonClick(ClickEvent event) {
		product.setShowlikeButton(showLikeButton.getValue());
	}
	@UiHandler("prodActualPrice")
	void onProdActualPriceKeyUp(KeyUpEvent event) {
		product.setActualPrice(prodActualPrice.getValue());
	}
	@UiHandler("prodPrice")
	void onProdPriceKeyUp(KeyUpEvent event) {
		product.setPrice(prodPrice.getValue());
	}
	@UiHandler("pickRelatedProduct")
	void onPickRelatedProductClick(ClickEvent event) {
		CommandFactory.popUpTabProductQuery(false, new SelectedCallBack() {
			
			@Override
			public void callBack(List<Component> selectedItems) {
				TabProduct tabProduct =(TabProduct)selectedItems.get(0);
				getRelatedProduct().setComponent(tabProduct);
				getProduct().setTabProductKey(tabProduct.getId());
			}
		}).execute();
	}
	
	@UiHandler("removeManual")
	void onRemoveManualClick(ClickEvent event) {
		String mannualKey = getProduct().getManualKey() == null?"" : getProduct().getManualKey();
		List<String> ks = Arrays.asList(mannualKey.split(","));
		List<String> keys = new ArrayList<String>();
		keys.addAll(ks);
		int index = getManual().getSelectedIndex();
		
		keys.remove(index);
		mannualKey = "";
		for (String key : keys) {
			mannualKey = mannualKey+key+",";
		}
		if(mannualKey.indexOf(',')!=-1){
			mannualKey=mannualKey.substring(0, mannualKey.lastIndexOf(','));
		}
		getProduct().setManualKey(mannualKey);
		getManual().remove(index);
	}
	
	@UiHandler("manualPicker")
	void onManualPickerClick(ClickEvent event) {
		
		CommandFactory.popUpHTMLQuery(true, new SelectedCallBack() {
			
			@Override
			public void callBack(List<Component> selectedItems) {
			//	HTML html = (HTML)selectedItems.get(0);
				String mannualKey = getProduct().getManualKey() == null?"" : getProduct().getManualKey()+",";
				if(mannualKey!=null){
					mannualKey = mannualKey.trim();
					if(mannualKey.startsWith(",")){
						mannualKey = mannualKey.substring(1);
					}
				}
				List<String> keys = Arrays.asList(mannualKey.split(","));
				if(null!=selectedItems){
					for (int i = 0; i < selectedItems.size(); i++) {
						if(!keys.contains(selectedItems.get(i).getId()+"")){
							mannualKey = mannualKey + selectedItems.get(i).getId()+",";
						}
					}
				}
				
				mannualKey=mannualKey.substring(0, mannualKey.lastIndexOf(','));
				
				getProduct().setManualKey(mannualKey);
				final PopWindow load = PopWindow.createLoading("Update recommend").lock();
				
				final String key = mannualKey;
				
				AdminWorkspace.ADMIN_SERVICE_ASYNC.getHTMLs(mannualKey, 
				new AsyncCallbackAdapter<List<HTML>>(){
					@Override
					public void onSuccess(List<HTML> htmls) {
						getManual().clear();
						/*
						getManual().add(new com.google.gwt.user.client.ui.HTML(rs.getContent()));
						load.hide();*/
						for(String id : Arrays.asList(key.split(","))){
							for (HTML html : htmls) {
								if(id.equalsIgnoreCase(html.getId()+"")){
									getManual().add(createManualPanel(html.getContent()), html.getName());
								}
							}
						}
						RootPanel.get().remove(load);
					}
				});
			}
		}).execute();
	}
	
	public TopSellingManager getRelatedProduct() {
		return relatedProduct;
	}

	public TabLayoutPanel getManual() {
		return manual;
	}
	
	public ListBox getOptType(){
		
		return optionTypes;
		
	}
	private boolean isHaveDiffrientTypeImage(){
		ImageSizeType sizeType = null;
		if(null != product.getImages()){
			for (Image image : product.getImages()) {
				if(sizeType==null){
					sizeType = image.getSizeType();
				}else if(sizeType != image.getSizeType()){
					return true;
				}
			}
		}
		
		return false;
	}
}
