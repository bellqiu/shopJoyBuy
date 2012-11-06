package com.spshop.admin.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

public class AccordionMenu extends Composite {

	private Tree tree;

	@UiConstructor
	public AccordionMenu(String type) {
		tree = createTree(type);
		initWidget(tree);
	}

	private Tree createTree(String type) {
		Tree tree = new Tree();
		AccordionMenuType accordionMenuType = AccordionMenuType.valueOf(type);
		List<AccordianItem> rootItems = getItems(accordionMenuType);
		for (AccordianItem accordianItem : rootItems) {
			tree.addItem(accordianItem);
		}
		SelectionHandler<TreeItem> selectionHandler = getSeletionHandler(accordionMenuType);
		tree.addSelectionHandler(selectionHandler);
		return tree;
	}
	

	private SelectionHandler<TreeItem> getSeletionHandler(
			AccordionMenuType accordionMenuType) {
		SelectionHandler<TreeItem> selectionHandler = new SelectionHandler<TreeItem>() {
			
			public void onSelection(SelectionEvent<TreeItem> item) {
				AccordianItem  accordianItem = (AccordianItem)item.getSelectedItem();
				if(null!=accordianItem.getComand() && !accordianItem.getComand().isEmpty()){
					AdminWorkspace.contentPanel.setTitle(accordianItem.getTitle());
					CommandFactory.lock(accordianItem.getTitle()).execute();
					accordianItem.getComand().execute();
					CommandFactory.release().execute();
				}
			}
		};
		return selectionHandler;
	}

	private List<AccordianItem> getItems(AccordionMenuType accordionMenuType) {
		List<AccordianItem> items = new ArrayList<AccordianItem>();
		switch (accordionMenuType) {
		case RESOURCE_MANAGEMENT:
			AccordianItem root = new AccordianItem("Resource Management", CommandFactory.emptyCommand());
			root.addItem(new AccordianItem("Create Image", CommandFactory.createImage()));
			root.addItem(new AccordianItem("Query Image", CommandFactory.queryImage()));
			root.addItem(new AccordianItem("Create Batch Image", CommandFactory.createBatchImage()));
			root.addItem(new AccordianItem("Create HTML", CommandFactory.createHTML()));
			root.addItem(new AccordianItem("Query HTML", CommandFactory.queryHTML()));
			items.add(root);
			root.setState(true);
			break;
		case PRODUCT_MANAGEMENT:
			AccordianItem root2 = new AccordianItem("Product Management", CommandFactory.emptyCommand());
			root2.addItem(new AccordianItem("Create Product", CommandFactory.createProduct()));
			root2.addItem(new AccordianItem("Query/Edit Product", CommandFactory.queryProduct()));
			root2.addItem(new AccordianItem("Top Selling", CommandFactory.topSelingManager()));
			root2.addItem(new AccordianItem("Dashboard Selling", CommandFactory.dashboardSellingManager()));
			root2.addItem(new AccordianItem("Related Product", CommandFactory.createRelatedProduct()));
			root2.addItem(new AccordianItem("Query Related Product", CommandFactory.queryRelatedProduct()));
			items.add(root2);
			root2.setState(true);
			break;
			
		case SITE_MANAGEMENT:
			AccordianItem root3 = new AccordianItem("Site Management", CommandFactory.emptyCommand());
			root3.addItem(new AccordianItem("Category Management",  CommandFactory.categoryManager()));
			root3.addItem(new AccordianItem("Site Management",  CommandFactory.siteManager()));
			root3.addItem(new AccordianItem("Delivery&Country",  CommandFactory.deliveryManager()));
			root3.addItem(new AccordianItem("OrderList Management", CommandFactory.manageOrder()));
			root3.addItem(new AccordianItem("Reply Messages", CommandFactory.showMessages()));
			items.add(root3);
			root3.setState(true);
			break;
			
		case USER_MANAGEMENT:
            AccordianItem root4 = new AccordianItem("User Management", CommandFactory.emptyCommand());
            root4.addItem(new AccordianItem("Query User", CommandFactory.queryUser()));
            items.add(root4);
            root4.setState(true);
            break;

		default:
			break;
		}
		return items;
	}
}
