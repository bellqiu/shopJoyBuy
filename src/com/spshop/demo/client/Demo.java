package com.spshop.demo.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.TreeViewModel;
import com.spshop.admin.client.businessui.ImageCreation;

public class Demo implements EntryPoint {

	public void onModuleLoad() {

		// StackLayoutPanel
		StackLayoutPanel stackLayoutPanel = new StackLayoutPanel(Unit.EM);
		stackLayoutPanel.setSize("20em", "40em");
		stackLayoutPanel.add(new HTML("<div><p>ABC123</p></div>"), new HTML(
				"<span>Header</span>"), 2);
		stackLayoutPanel.add(new HTML("<div><p>ABC123</p></div>"), new HTML(
				"<span>Header2</span>"), 2);
		stackLayoutPanel.add(new HTML("<div><p>ABC123</p></div>"), new HTML(
				"<span>Header3</span>"), 2);

		// TabLayoutPanel
		TabLayoutPanel tabLayoutPanel = new TabLayoutPanel(2, Unit.EM);
		tabLayoutPanel.setSize("80em", "40em");
		tabLayoutPanel.add(new HTML("this content1"), new HTML(
				"<span>Tab1</span>"));
		tabLayoutPanel.add(new HTML("this content2"), new HTML(
				"<span>Tab2</span>"));

		// Cell List
		List<String> DAYS = Arrays.asList("Sunday", "Monday", "Tuesday",
				"Wednesday", "Thursday", "Friday", "Saturday");
		TextCell textCell = new TextCell();
		// Create a CellList that uses the cell.
		CellList<String> cellList = new CellList<String>(textCell);
		// Set the total row count. This isn't strictly necessary, but it
		// affects
		// paging calculations, so its good habit to keep the row count up to
		// date.
		cellList.setRowCount(DAYS.size(), true);
		// Push the data into the widget.
		cellList.setRowData(0, DAYS);
		
		
		//Cell Table 
		// Create a CellTable.
	    CellTable<Contact> table = new CellTable<Contact>();
	    // Create name column.
	    TextColumn<Contact> nameColumn = new TextColumn<Contact>() {
	      @Override
	      public String getValue(Contact contact) {
	        return contact.name;
	      }
	    };
	    // Create address column.
	    TextColumn<Contact> addressColumn = new TextColumn<Contact>() {
	      @Override
	      public String getValue(Contact contact) {
	        return contact.address;
	      }
	    };
	    // Add the columns.
	    table.addColumn(nameColumn, "Name");
	    table.addColumn(addressColumn, "Address");
	    // Set the total row count. This isn't strictly necessary, but it affects
	    // paging calculations, so its good habit to keep the row count up to date.
	    table.setRowCount(CONTACTS.size(), true);
	    // Push the data into the widget.
	    table.setRowData(0, CONTACTS);
	    
	    
		
	    //Cell Tree
	    // Create a model for the tree.
	    TreeViewModel model = new CustomTreeModel();
	    // Create the tree using the model. We specify the default value of the
	    // hidden root node as "Item 1".
	    CellTree tree = new CellTree(model, "Item 1");
	    
	    
	    
	  //UI Binder
	    HelloWorldUI helloWorldUI = new HelloWorldUI();
	    List<String > list = new ArrayList<String>();
	    list.add("A");
	    list.add("B");
	    list.add("C");
	    //helloWorldUI.setName(list);
	    //RootPanel.get().add(helloWorldUI);
	    //Document.get().getBody().appendChild(helloWorldUI.getElement());
	    
	   // RootPanel.get().add(new Accordion());
	    
		//RootPanel.get().add(tree);
		
		RootPanel.get().add(tabLayoutPanel);
		
	}
	
	
	// The model that defines the nodes in the tree.
	  private static class CustomTreeModel implements TreeViewModel {

	    // Get the NodeInfo that provides the children of the specified value.
	    public <T> NodeInfo<?> getNodeInfo(T value) {

	      // Create some data in a data provider. Use the parent value as a prefix for the next level.
	      ListDataProvider<String> dataProvider = new ListDataProvider<String>();
	      for (int i = 0; i < 2; i++) {
	        dataProvider.getList().add(value + "." + String.valueOf(i));
	      }

	      // Return a node info that pairs the data with a cell.
	      return new DefaultNodeInfo<String>(dataProvider, new TextCell());
	    }

	    // Check if the specified value represents a leaf node. Leaf nodes cannot be opened.
	    public boolean isLeaf(Object value) {
	      // The maximum length of a value is ten characters.
	      return value.toString().length() > 10;
	    }
	  }
	
	
	  private static List<Contact> CONTACTS = Arrays.asList(
			    new Contact("John", "123 Fourth Road"),
			    new Contact("Mary", "222 Lancer Lane"));
	
	 private static class Contact {
		    private final String address;
		    private final String name;

		    public Contact(String name, String address) {
		      this.name = name;
		      this.address = address;
		    }
	  }
}
