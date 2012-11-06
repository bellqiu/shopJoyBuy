package com.spshop.admin.client.businessui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.PopWindow;
import com.spshop.model.Category;

public class CategoryPicker extends ObservableComposite<List<Category>, CategoryPicker>{

	private static CategoryPickerUiBinder uiBinder = GWT
			.create(CategoryPickerUiBinder.class);
	@UiField HorizontalPanel host;
	@UiField Button pickBTN;
	CategoryTree tree;

	interface CategoryPickerUiBinder extends UiBinder<Widget, CategoryPicker> {
	}

	public CategoryPicker() {
		initWidget(uiBinder.createAndBindUi(this));
		tree = new CategoryTree();
		tree.init(false,false);
	}

	@UiHandler("pickBTN")
	void onPickBTNClick(ClickEvent event) {
		ScrollPanel tp = new ScrollPanel();
		tp.setSize("400px", "400px");
		tp.add(tree);
//		tree.setSize("400px", "400px");
		final CategoryPicker self = this;
		final CategoryTree t = tree;
		PopWindow popWindow = new PopWindow("Select category",tp,true,true);
		Button add = new Button("Add");
		popWindow.addButton(add);
		add.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Category category =((CategoryTreeItem)t.getSelectedItem()).getCategory();
				if(null!=category&&!self.getComponet().contains(category)){
					self.getComponet().add(category);
					self.host.add(createButton(category));
					self.notifyChange();
				}
			}
		});
		popWindow.center();
	}

	@Override
	public void setComponent(List<Category> componet) {
		host.clear();
		this.component = componet;
		for (Category category : componet) {
			host.add(createButton(category));
		}
	}
	
	private Button createButton(final Category category){
		final Button btn = new Button(category.getDisplayName());
		final CategoryPicker self = this;
		btn.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				self.getComponet().remove(category);
				self.host.remove(btn);
				self.notifyChange();
			}
		});
		btn.addMouseOverHandler(new MouseOverHandler() {
			
			@Override
			public void onMouseOver(MouseOverEvent event) {
				btn.setText(category.getDisplayName()+" X");
			}
		});
		btn.addMouseOutHandler(new MouseOutHandler() {
			
			@Override
			public void onMouseOut(MouseOutEvent event) {
				btn.setText(category.getDisplayName());
			}
		});
		return btn;
	}
}
