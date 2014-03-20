package com.spshop.admin.client.businessui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.PopWindow;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.OperationListenerAdapter;
import com.spshop.model.Component;
import com.spshop.model.HTML;
import com.spshop.model.Image;
import com.spshop.model.Message;
import com.spshop.model.Order;
import com.spshop.model.Product;
import com.spshop.model.TabProduct;
import com.spshop.model.User;
import com.spshop.model.enums.ImageSizeType;
import com.spshop.model.query.QueryCriteria;
import com.spshop.model.query.QueryResult;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.OrderService;
@SuppressWarnings("rawtypes")
public class ComponentQuery extends ResizeComposite {

	public static interface Listener {
		
		void onItemSelected(Component item);
		void onItemUnSelected(Component item);
	}

	interface Binder extends UiBinder<Widget, ComponentQuery> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	private static final Binder binder = GWT.create(Binder.class);
	static final int VISIBLE_RECORD_COUNT = 40;

	@UiField
	FlexTable header;
	@UiField
	FlexTable table;
	@UiField
	SelectionStyle selectionStyle;

	@UiField
	QueryCondition queryCondition;
	@UiField SplitLayoutPanel container;

	private QueryCriteria queryCriteria;

	private QueryResult<Component> result;

	private Listener listener;
	private int startIndex;
	private List<Integer> selectedRows = new ArrayList<Integer>();
	private NavBar navBar;
	private boolean enableMultiSelect = false;

	public ComponentQuery(String title, Class type) {
		initWidget(binder.createAndBindUi(this));
		table.setCellPadding(0);
		table.setCellSpacing(0);
		header.setCellPadding(0);
		header.setCellSpacing(0);
		navBar = new NavBar(this);
		queryCondition.setCaption(title);
		queryCondition.setComponentQuery(this);
		queryCondition.setType(type);
		initTable();
	}
	

	/**
	 * Sets the listener that will be notified when an item is selected.
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	void newer() {
		// Move back a page.
		startIndex -= 1;
		search();
		update();
	}

	void older() {
		startIndex += 1;
		search();
		update();
	}

	@UiHandler("table")
	void onTableClicked(ClickEvent event) {
		// Select the row that was clicked (-1 to account for header row).
		Cell cell = table.getCellForEvent(event);
		if (cell != null) {
			int col = cell.getCellIndex();
			if (col < 2) {
				int row = cell.getRowIndex();
				selectRow(row);
			}
		}
	}

	private void initTable() {
		// Initialize the header.
		if (queryCondition.getType() == Image.class) {
			initImageHeader();
			
			ListBox listBox = new ListBox(false);
			
			for (ImageSizeType type : ImageSizeType.values()) {
				listBox.addItem(type.getTitle(), type.getValue());
			}
			
			queryCondition.addField("strSizeType","Type", listBox);
			
		}
		
		if (queryCondition.getType() == Product.class) {
			initProductHeader();
		}
		
		if (queryCondition.getType() == HTML.class) {
			initHTMLHeader();
		}
		
		if (queryCondition.getType() == User.class) {
		    initUserHeader();
        }
		
		if (queryCondition.getType() == TabProduct.class) {
		    initTabProductHeader();
        }
		
		if (queryCondition.getType() == Order.class) {
            initOrderHeader();
        }
		
		if (queryCondition.getType() == Message.class) {
            initMessageHeader();
        }
		// Initialize the table.

	}


	private void initTabProductHeader() {
		initHTMLHeader();
	}

	private void initHTMLHeader(){
		header.getColumnFormatter().setWidth(0, "80px");
		header.getColumnFormatter().setWidth(1, "150px");
		header.getColumnFormatter().setWidth(2, "120px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "250px");
		
		header.setText(0, 0, " ");
		header.setText(0, 1, "Name");
		header.setText(0, 2, "Create Date");
		header.setText(0, 3, " ");
		header.setWidget(0, 4, navBar);
		header.getCellFormatter().setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_RIGHT);
		
		table.getColumnFormatter().setWidth(0, "80px");
		table.getColumnFormatter().setWidth(1, "150px");
		table.getColumnFormatter().setWidth(2, "120px");
		table.getColumnFormatter().setWidth(3, "100px");
		table.getColumnFormatter().setWidth(4, "250px");
	}
	
    private void initUserHeader() {
        header.getColumnFormatter().setWidth(0, "30px");
        header.getColumnFormatter().setWidth(1, "100px");
        header.getColumnFormatter().setWidth(2, "100px");
        header.getColumnFormatter().setWidth(3, "130px");
        header.getColumnFormatter().setWidth(4, "110px");
        header.getColumnFormatter().setWidth(5, "100px");
        header.getColumnFormatter().setWidth(6, "130px");
        header.setText(0, 0, "Id");
        header.setText(0, 1, "First Name");
        header.setText(0, 2, "Last Name");
        header.setText(0, 3, "Email");
        header.setText(0, 4, "Tel");
        header.setText(0, 5, "Create Date");

        header.setWidget(0, 6, navBar);
        header.getCellFormatter().setHorizontalAlignment(0, 6, HasHorizontalAlignment.ALIGN_RIGHT);
        
        table.getColumnFormatter().setWidth(0, "30px");
        table.getColumnFormatter().setWidth(1, "100px");
        table.getColumnFormatter().setWidth(2, "100px");
        table.getColumnFormatter().setWidth(3, "130px");
        table.getColumnFormatter().setWidth(4, "110px");
        table.getColumnFormatter().setWidth(5, "100px");
        table.getColumnFormatter().setWidth(6, "130px");
    }

	private void initProductHeader() {
		header.getColumnFormatter().setWidth(0, "80px");
		header.getColumnFormatter().setWidth(1, "150px");
		header.getColumnFormatter().setWidth(2, "120px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "250px");
		// header.getColumnFormatter().setWidth(5, "200px");

		header.setText(0, 0, "Thumbnail");
		header.setText(0, 1, "Name");
		header.setText(0, 2, "Title");
		header.setText(0, 3, "Create Date");
		// header.setWidget(0, 4, new InlineLabel("Operation"));
		header.setWidget(0, 4, navBar);
		header.getCellFormatter().setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_RIGHT);
		table.getColumnFormatter().setWidth(0, "80px");
		table.getColumnFormatter().setWidth(1, "150px");
		table.getColumnFormatter().setWidth(2, "120px");
		table.getColumnFormatter().setWidth(3, "100px");
		table.getColumnFormatter().setWidth(4, "250px");
	}

	private void initImageHeader() {
		header.getColumnFormatter().setWidth(0, "80px");
		header.getColumnFormatter().setWidth(1, "250px");
		header.getColumnFormatter().setWidth(2, "120px");
		header.getColumnFormatter().setWidth(3, "100px");
		header.getColumnFormatter().setWidth(4, "150px");
		// header.getColumnFormatter().setWidth(5, "200px");

		header.setText(0, 0, "Thumbnail");
		header.setText(0, 1, "Name");
		header.setText(0, 2, "Size Type");
		header.setText(0, 3, "Create Date");
		// header.setWidget(0, 4, new InlineLabel("Operation"));
		header.setWidget(0, 4, navBar);
		header.getCellFormatter().setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_RIGHT);
		table.getColumnFormatter().setWidth(0, "80px");
		table.getColumnFormatter().setWidth(1, "250px");
		table.getColumnFormatter().setWidth(2, "120px");
		table.getColumnFormatter().setWidth(3, "100px");
		table.getColumnFormatter().setWidth(4, "150px");
	}
	
    private void initOrderHeader() {
        header.getColumnFormatter().setWidth(0, "100px");
        header.getColumnFormatter().setWidth(1, "100px");
        header.getColumnFormatter().setWidth(2, "100px");
        header.getColumnFormatter().setWidth(3, "130px");
        header.getColumnFormatter().setWidth(4, "110px");
        header.getColumnFormatter().setWidth(5, "130px");
        header.setText(0, 0, "Customer");
        header.setText(0, 1, "Email");
        header.setText(0, 2, "Create Date");
        header.setText(0, 3, "Total Price");
        header.setText(0, 4, "Status");

        header.setWidget(0, 5, navBar);
        header.getCellFormatter().setHorizontalAlignment(0, 5, HasHorizontalAlignment.ALIGN_RIGHT);
        
        table.getColumnFormatter().setWidth(0, "100px");
        table.getColumnFormatter().setWidth(1, "100px");
        table.getColumnFormatter().setWidth(2, "100px");
        table.getColumnFormatter().setWidth(3, "130px");
        table.getColumnFormatter().setWidth(4, "110px");
        table.getColumnFormatter().setWidth(5, "130px");
    }
    
    private void initMessageHeader() {
        header.getColumnFormatter().setWidth(0, "200px");
        header.getColumnFormatter().setWidth(1, "100px");
        header.getColumnFormatter().setWidth(2, "100px");
        header.getColumnFormatter().setWidth(3, "100px");
        header.getColumnFormatter().setWidth(4, "90px");
        header.getColumnFormatter().setWidth(5, "80px");
        header.setText(0, 0, "Title");
        header.setText(0, 1, "Email");
        header.setText(0, 2, "Create Date");
        header.setText(0, 3, "Customer");
        header.setText(0, 4, "Status");

        header.setWidget(0, 5, navBar);
        header.getCellFormatter().setHorizontalAlignment(0, 5, HasHorizontalAlignment.ALIGN_RIGHT);
        
        table.getColumnFormatter().setWidth(0, "200px");
        table.getColumnFormatter().setWidth(1, "100px");
        table.getColumnFormatter().setWidth(2, "100px");
        table.getColumnFormatter().setWidth(3, "100px");
        table.getColumnFormatter().setWidth(4, "90px");
        table.getColumnFormatter().setWidth(5, "80px");
    }

	/**
	 * Selects the given row (relative to the current page).
	 * 
	 * @param row
	 *            the row to be selected
	 */
	private void selectRow(int row) {
		
		if (row > result.getResult().size() - 1) {
			return;
		}

		Component component = result.getResult().get(row);
		if (component == null) {
			return;
		}
		
		if(!enableMultiSelect){
			for(Integer i : selectedRows){
				styleRow(i.intValue(), false);
				if(listener != null){
					listener.onItemUnSelected(result.getResult().get(i.intValue()));
				}
			}
			selectedRows = new ArrayList<Integer>();
		}

		if (selectedRows.contains(row)) {
			styleRow(row, false);
			selectedRows.remove(new Integer(row));
		} else {
			styleRow(row, true);
			selectedRows.add(row);
		}
		
		if (listener != null) {
			listener.onItemSelected(component);
		}
	}

	private void styleRow(int row, boolean selected) {
		if (row != -1) {
			String style = selectionStyle.selectedRow();

			if (selected) {
				table.getRowFormatter().addStyleName(row, style);
			} else {
				table.getRowFormatter().removeStyleName(row, style);
			}
		}
	}

	private void update() {
		int rowCount = table.getRowCount();
		for(int i=0 ; i<rowCount; i++ ){
			table.removeRow(0);
		}
		selectedRows = new ArrayList<Integer>();
		int count = result.getRecordCount();
		int max = result.getResult().size();
		int perPage = VISIBLE_RECORD_COUNT;

		// Update the nav bar.
		navBar.update(startIndex, count, max,perPage);

		if (max > 0) {
			if (result.getComponentType().equals(Image.class.getName())) {
				updateImage();
			}
			
			if (result.getComponentType().equals(Product.class.getName())) {
				updateProduct();
			}
			
			if (result.getComponentType().equals(HTML.class.getName())) {
				updateHTML();
			}
			
			if (result.getComponentType().equals(User.class.getName())) {
                updateUser();
            }
			
			if (result.getComponentType().equals(TabProduct.class.getName())) {
                updateTabProduct();
            }
			
			if (result.getComponentType().equals(Order.class.getName())) {
                updateOrder();
            }
			
			if (result.getComponentType().equals(Message.class.getName())) {
                updateMessage();
            }
		}

	}
	
	private void updateTabProduct() {
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
		for (int i = 0; i < result.getResult().size(); i++) {
			TabProduct tp = (TabProduct) result.getResult().get(i);
			table.setText(i, 1, tp.getName());
			table.setText(i, 2, dateTimeFormat.format(tp.getCreateDate()));
			Operation<TabProduct> operation = new Operation<TabProduct>(tp);
			operation.setListener(new OperationListenerAdapter<TabProduct>(){
				@Override
				public void onDelete(TabProduct content) {
					super.onDelete(content);
				}
				@Override
				public void onEdit(TabProduct content) {
					ScrollPanel scrollPanel = new ScrollPanel();
					scrollPanel.setSize("900px", "420px");
					TopSellingManager tabManager = new TopSellingManager();
					tabManager.setShowButton(true);
					if("DEFAUL_TOP_SELLING".equals(content.getName())){
						tabManager.setShowName(false);
					}else{
						tabManager.setShowName(true);
					}
					tabManager.setComponent(content);
					tabManager.setSize("900px", "350px");
					tabManager.setTitle("Edit Related Product");
					scrollPanel.add(tabManager);
					PopWindow pop = new PopWindow("Edit Related Product",scrollPanel, true, true);
					//pop.setSize("400px", "400px");
					pop.center();
				}
			});
			table.setWidget(i, 4, operation);
			table.getCellFormatter().setHorizontalAlignment(i, 4,
					HasHorizontalAlignment.ALIGN_RIGHT);
		}
	}

	private void updateHTML() {
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
		for (int i = 0; i < result.getResult().size(); i++) {
			HTML html = (HTML) result.getResult().get(i);
			table.setText(i, 1, html.getName());
			table.setText(i, 2, dateTimeFormat.format(html.getCreateDate()));
			Operation<HTML> operation = new Operation<HTML>(html);
			operation.setListener(new OperationListenerAdapter<HTML>(){
				@Override
				public void onDelete(HTML content) {
					super.onDelete(content);
				}
				@Override
				public void onEdit(HTML content) {
					ScrollPanel scrollPanel = new ScrollPanel();
					scrollPanel.setSize("900px", "420px");
					HTMLCreation htmlCreation = new HTMLCreation();
					htmlCreation.setComponent(content);
					htmlCreation.setSize("900px", "350px");
					htmlCreation.setTitle("Edit HTML");
					scrollPanel.add(htmlCreation);
					PopWindow pop = new PopWindow("Edit HTML",scrollPanel, true, true);
					//pop.setSize("400px", "400px");
					pop.center();
				}
			});
			table.setWidget(i, 4, operation);
			table.getCellFormatter().setHorizontalAlignment(i, 4,
					HasHorizontalAlignment.ALIGN_RIGHT);
		}
	}
	
	private void updateUser() {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
        for (int i = 0; i < result.getResult().size(); i++) {
            User user = (User) result.getResult().get(i);
            table.setText(i, 0, String.valueOf(user.getId()));
            table.setText(i, 1, user.getFirstName());
            table.setText(i, 2, user.getLastName());
            table.setText(i, 3, user.getEmail());
            table.setText(i, 4, user.getTelephone());
            table.setText(i, 5, dateTimeFormat.format(user.getCreateDate()));
            ShowOrder showOrder = new ShowOrder(user);
            table.setWidget(i, 6, showOrder);
            table.getCellFormatter().setHorizontalAlignment(i, 6,
                    HasHorizontalAlignment.ALIGN_RIGHT);
        }
    }

	private void updateProduct() {
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
		for (int i = 0; i < result.getResult().size(); i++) {
			Product product = (Product) result.getResult().get(i);
			if(null!=product.getImages()&&product.getImages().size()>0){
				table.setWidget(i, 0, new com.google.gwt.user.client.ui.Image(
					product.getImages().get(0).getIconUrl()));
			}
			table.setText(i, 1, product.getName());
			table.setText(i, 2, product.getTitle());
			table.setText(i, 3, dateTimeFormat.format(product.getCreateDate()));
			Operation<Product> operation = new Operation<Product>(product);
			operation.setListener(new OperationListenerAdapter<Product>(){
				@Override
				public void onDelete(Product content) {
					super.onDelete(content);
				}
				@Override
				public void onEdit(Product content) {
					ScrollPanel scrollPanel = new ScrollPanel();
					scrollPanel.setSize("900px", "720px");
					ProductCreation productCreation = new ProductCreation(content);
					productCreation.setSize("900px", "650px");
					productCreation.setTitle("Edit Product");
					scrollPanel.add(productCreation);
					PopWindow pop = new PopWindow("Edit Product",scrollPanel, true, true);
					//pop.setSize("400px", "400px");
					pop.center();
				}
			});
			table.setWidget(i, 4, operation);
			table.getCellFormatter().setHorizontalAlignment(i, 4,
					HasHorizontalAlignment.ALIGN_RIGHT);
		}
	}

	private void updateImage() {
		DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
		for (int i = 0; i < result.getResult().size(); i++) {
			Image image = (Image) result.getResult().get(i);
			table.setWidget(i, 0, new com.google.gwt.user.client.ui.Image(
					image.getIconUrl()));
			table.setText(i, 1, image.getName());
			table.setText(i, 2, image.getSizeType().getTitle());
			table.setText(i, 3, dateTimeFormat.format(image.getCreateDate()));
			Operation<Image> operation = new Operation<Image>(image);
			operation.setListener(new OperationListenerAdapter<Image>(){
				@Override
				public void onDelete(Image content) {
					super.onDelete(content);
				}
				@Override
				public void onEdit(Image content) {
					ImageCreation imageCreation = new ImageCreation(content);
					imageCreation.setSize("800px", "400px");
					imageCreation.setTitle("Edit Image");
					PopWindow pop = new PopWindow("Edit Image",imageCreation, true, true);
					//pop.setSize("400px", "400px");
					pop.center();
				}
			});
			table.setWidget(i, 4, operation);
			table.getCellFormatter().setHorizontalAlignment(i, 4,
					HasHorizontalAlignment.ALIGN_RIGHT);
		}
	}
	
	private void updateOrder() {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
        for (int i = 0; i < result.getResult().size(); i++) {
            Order order = (Order) result.getResult().get(i);
            if(null != order.getShippingAddress()){
            	table.setText(i, 0, order.getShippingAddress().getFirstName() + " " + order.getShippingAddress().getLastName());
            }
            table.setText(i, 1, order.getCustomerEmail()==null ? (order.getUser()!=null?order.getUser().getEmail() : "" ): order.getCustomerEmail());
            table.setText(i, 2, dateTimeFormat.format(order.getCreateDate()));
            table.setText(i, 3, NumberFormat.getFormat("0.00").format(order.getTotalPrice()));
            table.setText(i, 4, order.getStatus());
            Operation<Order> operation = new Operation<Order>(order);
            operation.setListener(new OperationListenerAdapter<Order>(){
                @Override
                public void onEdit(Order content) {
                    OrderInfo orderInfo = new OrderInfo(content);
                    orderInfo.setSize("1200px", "700px");
                    orderInfo.setTitle("Order Info");
                    PopWindow pop = new PopWindow("Order Info",orderInfo, true, true);
                    pop.center();
                }
                @Override
                public void onDelete(Order content) {
                    // TODO Auto-generated method stub
                    super.onDelete(content);
                }
            });
            table.setWidget(i, 5, operation);
            table.getCellFormatter().setHorizontalAlignment(i, 5,
                    HasHorizontalAlignment.ALIGN_RIGHT);
        }
    }
	
	private void updateMessage(){
	    DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
        for (int i = 0; i < result.getResult().size(); i++) {
            Message message = (Message) result.getResult().get(i);
            table.setText(i, 0, message.getName());
            table.setText(i, 1, message.getUser().getEmail());
            table.setText(i, 2, dateTimeFormat.format(message.getCreateDate()));
            String customerName = "";
            Long userId = 0l;
            if (message.getReplyTo()!=null && !message.getReplyTo().getUser().getName().equals(AdminWorkspace.loginInfo.getLoginUser().getName())) {
                customerName = message.getReplyTo().getUser().getName();
                userId = message.getReplyTo().getUser().getId();
            } else {
                customerName = message.getUser().getName();
                userId = message.getUser().getId();
            }
            table.setText(i, 3, customerName);
            table.setText(i, 4, message.isReplied()?"Replied":"Awaiting Replied");
            ReplyMessage reply = new ReplyMessage(message, userId);
            table.setWidget(i, 5, reply);
            table.getCellFormatter().setHorizontalAlignment(i, 5,
                    HasHorizontalAlignment.ALIGN_RIGHT);
        }
	}

	public void setQueryCriteria(QueryCriteria queryCriteria) {
		this.queryCriteria = queryCriteria;
	}

	public QueryCriteria getQueryCriteria() {
		return queryCriteria;
	}

	public void search() {
		CommandFactory.lock("Search").execute();
		String hql = this.queryCondition.getHql();
		List<Object> params = this.queryCondition.getParams();
		String className = this.queryCondition.getType().getName();
		if (hql != null && className != null && hql.trim() != "" && className.trim() != "") {
		    AdminWorkspace.ADMIN_SERVICE_ASYNC.queryByHQL(hql, params, className, startIndex * VISIBLE_RECORD_COUNT, VISIBLE_RECORD_COUNT, new AsyncCallbackAdapter<QueryResult<Component>>() {
		        @Override
		        public void onSuccess(QueryResult<Component> rs) {
		            setResult(rs);
                    update();
                    CommandFactory.release().execute();
		        }
		    });
        } else {
            queryCriteria.setStartIndex(startIndex * VISIBLE_RECORD_COUNT);
            queryCriteria.setMaxResult(VISIBLE_RECORD_COUNT);
            AdminWorkspace.ADMIN_SERVICE_ASYNC.query(queryCriteria,
                                                     new AsyncCallbackAdapter<QueryResult<Component>>() {
                public void onSuccess(QueryResult<Component> result) {
                    setResult(result);
                    update();
                    CommandFactory.release().execute();
                }
            });
        }
	}

	public QueryResult<Component> getResult() {
		return result;
	}

	public void setResult(QueryResult<Component> result) {
		this.result = result;
	}

	public QueryCondition getQueryCondition() {
		return queryCondition;
	}

	public void setEnableMultiSelect(boolean enableMultiSelect) {
		this.enableMultiSelect = enableMultiSelect;
	}

	public boolean isEnableMultiSelect() {
		return enableMultiSelect;
	}
	
	public List<Component> getSelected(){
		List<Component> rs = new ArrayList<Component>();
		for(int i : selectedRows){
			rs.add(result.getResult().get(i));
		}
		return rs;
	}
	
	public Component getSingle(){
		if(!selectedRows.isEmpty()){
			return result.getResult().get(selectedRows.get(0).intValue());
		}
		return null;
	}
	
	public SplitLayoutPanel getContainer(){
		return container;
	}

}
