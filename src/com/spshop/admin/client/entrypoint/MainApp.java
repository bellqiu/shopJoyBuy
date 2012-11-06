package com.spshop.admin.client.entrypoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.terracotta.agent.repkg.de.schlichtherle.key.KeyProvider;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.spshop.admin.client.style.GlobalCss;
import com.spshop.model.Address;
import com.spshop.model.User;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent.AsyncHandler;
import com.google.gwt.user.cellview.client.ColumnSortList;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.Range;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

public class MainApp implements EntryPoint {

	private static MainAppUiBinder uiBinder = GWT.create(MainAppUiBinder.class);
	@UiField(provided=true) CellTable<Object> cellTable = new CellTable<Object>();

	interface MainAppUiBinder extends UiBinder<DockLayoutPanel, MainApp> {
	}

	@Override
	public void onModuleLoad() {
		GWT.<GlobalCss> create(GlobalCss.class).css().ensureInjected();
		Window.enableScrolling(false);
		Window.setMargin("0px");
		
		
		final List<User> USERS = new ArrayList<User>();
		
		for (int i = 0; i < 100; i++) {
			User user = new User();
			user.setId(i);
			user.setName("Name" + i);
			user.setPrimaryAddress(new Address("aaaa"+i,"add"+i,"add2"+i,"asdas","asdasd",i,"asdas","asdasdasd"));
			USERS.add(user);
		}
		
		ProvidesKey<User>  keyProvider = new ProvidesKey<User>() {

			@Override
			public Object getKey(User user) {
				 return (user == null) ? null : user.getId();
			}
		};
		
		final SingleSelectionModel<User> model = new SingleSelectionModel<User>(keyProvider);
		
		
		/*
		 * asdasdasdasd
		 */
		 // Create a CellTable.
	    final CellTable<User> table = new CellTable<User>();
	    
	    table.setSelectionModel(model);
	    
	    model.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent e) {
				User user = model.getSelectedObject();
				Window.alert(user.getName());
			}
		});

	    // Create name column.
	    TextColumn<User> nameColumn = new TextColumn<User>() {
	      @Override
	      public String getValue(User contact) {
	        return contact.getName();
	      }
	    };
	    
	    nameColumn.setSortable(true);
	   

	    // Create address column.
	    TextColumn<User> addressColumn = new TextColumn<User>() {
	      @Override
	      public String getValue(User contact) {
	        return contact.getPrimaryAddress().getAddress1();
	      }
	    };

	    addressColumn.setSortable(true);
	    
	    // Add the columns.
	    table.addColumn(nameColumn, "Name");
	    table.addColumn(addressColumn, "Address");
	    table.setRowCount(100, true);
	    //table.setRowData(0,USERS);
	    table.setVisibleRange(0,4);
	    
	    AsyncDataProvider<User> dataProvider = new AsyncDataProvider<User>() {
	        @Override
	        protected void onRangeChanged(HasData<User> display) {
	          final Range range = display.getVisibleRange();
	          // This timer is here to illustrate the asynchronous nature of this data
	          // provider. In practice, you would use an asynchronous RPC call to
	          // request data in the specified range.
	          new Timer() {
	            @Override
	            public void run() {
	              int start = range.getStart();
	              int end = start + range.getLength();
	              List<User> dataInRange = USERS.subList(start, end);
	              final ColumnSortList sortList = table.getColumnSortList();

	              Collections.sort(USERS, new Comparator<User>() {

					@Override
					public int compare(User o1, User o2) {
						
						int rs = (int)(o1.getId() - o2.getId());
						
						return sortList.get(0).isAscending()? rs : -rs ;
					}
	              });
	              // Push the data back into the list.
	              table.setRowData(start, dataInRange);
	            }
	          }.schedule(2000);
	        }
	      };

	      dataProvider.addDataDisplay(table);
	      AsyncHandler columnSortHandler = new AsyncHandler(table);
	      table.addColumnSortHandler(columnSortHandler);
	      table.getColumnSortList().push(nameColumn);

	      SimplePager pager = new SimplePager();
	      pager.setDisplay(table);


		/**************************************************************************************************/

		final DockLayoutPanel app = uiBinder.createAndBindUi(this);

		app.setVisible(true);
		
		
		VerticalPanel panel = new VerticalPanel();
		
		panel.add(table);
		panel.add(pager);

		RootLayoutPanel root = RootLayoutPanel.get();
		root.add(panel);
	}

}
