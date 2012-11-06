package com.spshop.admin.client.businessui;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.PopWindow;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.SelectedCallBack;
import com.spshop.admin.client.rich.RichText;
import com.spshop.model.Category;
import com.spshop.model.Component;
import com.spshop.model.Image;
import com.google.gwt.user.client.ui.IntegerBox;

public class CategoryCreation extends Composite {

	private Category category;
	private CategoryManager categoryManager;

	private static CategoryCreationUiBinder uiBinder = GWT
			.create(CategoryCreationUiBinder.class);
	@UiField
	TextBox name;
	@UiField
	TextBox displayName;
	@UiField
	Button button;
	@UiField
	TextBox url;
	@UiField
	TextArea relatedKeywords;
	@UiField
	CheckBox isSpecialOffer;
	@UiField
	RichText marketContent;
	@UiField
	Anchor specicalOfferImageAnchor;
	private boolean addChildCategory = false;
	private boolean addRootCategory = false;
	@UiField
	TextBox pageTitle;
	@UiField
	Anchor showImage;
	@UiField CheckBox cEnable;
	@UiField CheckBox marketOnly;
	@UiField TextArea description;
	@UiField IntegerBox index;
	@UiField Button delete;
	
	private com.google.gwt.user.client.ui.Image specialOfferImageUI;

	private Image specialOfferImage;

	interface CategoryCreationUiBinder extends
			UiBinder<Widget, CategoryCreation> {
	}

	public CategoryCreation(Category category, CategoryManager categoryManager) {
		initWidget(uiBinder.createAndBindUi(this));
		setCategory(category);
		this.categoryManager = categoryManager;
	}


	private void setCategory(Category category) {
		
		this.category = category;
		name.setValue(category.getName());
		cEnable.setValue(category.isEnable());
		description.setText(category.getDescription());
		displayName.setValue(category.getDisplayName());
		url.setValue(category.getUrl());
		relatedKeywords.setValue(category.getRelatedKeyword());
		isSpecialOffer.setValue(category.isSpecialOffer());
		marketOnly.setValue(category.isDisplayMarketOnly());
		pageTitle.setValue(category.getPageTitle());
		index.setValue(category.getIndex());
		marketContent.setHTML(null == category.getMarketContent() ? ""
				: category.getMarketContent());
		setSpecialOfferImage(category.getSpecialOfferImage());
		if (getSpecialOfferImage() != null) {
			setSpecialOfferImageUI(getSpecialOfferImage());
		} else {
			showImage.setVisible(false);
		}
		
		if(category.getId()>1){
			delete.setVisible(true);
			button.setText("Update");
		}else{
			delete.setVisible(false);
			button.setText("Save");
		}
	}

	public Category getCategory() {
		return category;
	}

	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
		save();
	}
	
	private void save(){
		category.setUpdateDate(new Date());
		category.setIndex(index.getValue());
		category.setName(name.getValue());
		category.setDisplayName(displayName.getValue());
		category.setMarketContent(marketContent.getHTML());
		category.setPageTitle(pageTitle.getValue());
		category.setRelatedKeyword(relatedKeywords.getValue());
		category.setSpecialOffer(isSpecialOffer.getValue());
		category.setSpecialOfferImage(specialOfferImage);
		category.setUrl(url.getValue());
		category.setEnable(cEnable.getValue());
		category.setDisplayMarketOnly(marketOnly.getValue());
		category.setDescription(description.getText());
		// categoryManager.tree.update(category);
		if (category.getId() < 1) {
			category.setCreateDate(new Date());
		}
		CommandFactory.lock("Save Category").execute();
		AdminWorkspace.ADMIN_SERVICE_ASYNC.saveCategory(category,
				new AsyncCallbackAdapter<Category>() {
					@Override
					public void onSuccess(Category rs) {
						CategoryTreeItem item = (CategoryTreeItem) categoryManager
								.getTree().getSelectedItem();
						
						if(isAddRootCategory()){
							categoryManager.getTree().addRoot(
									new CategoryTreeItem(rs));
						}else if (null != rs.getParent()&&isAddChildCategory()) {
								categoryManager.getTree().addCategory(item,
										new CategoryTreeItem(rs));
						} else {
							item.setCategory(rs);
						}
						button.setText("Update");
						CommandFactory.release().execute();
					}
				});

	}

	@UiHandler({ "specicalOfferImageAnchor" })
	void onMarketImageClick(ClickEvent event) {
		final CategoryCreation self = this;
		CommandFactory.popUpImageQuery(false, new SelectedCallBack() {
			@Override
			public void callBack(List<Component> selectedItems) {
				if (null != selectedItems && !selectedItems.isEmpty()) {
					self.setSpecialOfferImage((Image) selectedItems.get(0));
					self.setSpecialOfferImageUI((Image) selectedItems.get(0));
				}
			}
		}).execute();
	}

	public void setSpecialOfferImage(Image specialOfferImage) {
		this.specialOfferImage = specialOfferImage;
	}

	public Image getSpecialOfferImage() {
		return specialOfferImage;
	}

	@UiHandler("showImage")
	void onShowImageMouseMove(ClickEvent event) {
		
		PopWindow popWindow = new PopWindow("Special Offer Image",specialOfferImageUI,true,true);
		popWindow.center();
	}

	public com.google.gwt.user.client.ui.Image getSpecialOfferImageUI() {
		return specialOfferImageUI;
	}

	public void setSpecialOfferImageUI(Image specialOfferImage) {
		this.specialOfferImageUI = new com.google.gwt.user.client.ui.Image(specialOfferImage.getNoChangeUrl());
		this.showImage.setVisible(true);
		this.specicalOfferImageAnchor.setText("Update Image for SpecicalOffer");
	}


	public void setAddChildCategory(boolean addChildCategory) {
		this.addChildCategory = addChildCategory;
	}


	public boolean isAddChildCategory() {
		return addChildCategory;
	}


	public void setAddRootCategory(boolean addRootCategory) {
		this.addRootCategory = addRootCategory;
	}


	public boolean isAddRootCategory() {
		return addRootCategory;
	}
	
	@UiHandler("delete")
	void onDeleteClick(ClickEvent event) {
		if(Window.confirm("Are you sure?")){
			getCategory().setDeleted(true);
			save();
		}
	}
}
