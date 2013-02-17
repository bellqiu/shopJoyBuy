package com.spshop.admin.client.businessui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.model.Address;
import com.spshop.model.Country;
import com.spshop.model.Order;
import com.spshop.model.Site;
import com.spshop.model.UserOption;
import com.google.gwt.user.client.ui.TextArea;

public class OrderInfo extends Composite {

    private static OrderInfoUiBinder uiBinder = GWT.create(OrderInfoUiBinder.class);
    
    private Order order;
    private Site site;
    private Map<String, Country> countryMap = new HashMap<String, Country>();
    private static String ADDRESS_SEPARATOR = "  ";
    private static String ADDRESS_COMMA = ", ";
    
    @UiField Button button;
    @UiField OrderStatusSelection orderStatus;
    @UiField FlexTable orderTable;
    @UiField FlexTable orderHeader;
    @UiField Label orderId;
    @UiField Label totalPrice;
    @UiField Label currency;
    @UiField Label createDate;
    @UiField Label email;
    @UiField Label billingAddr;
    @UiField Label couponId;
    @UiField Label dePrice;
    @UiField Label primaryAddr;
    @UiField Label customerMsg;
    @UiField Label couponPrice;
    @UiField Label shippingType;
    @UiField TextArea txtTraceInfo;
    @UiField Button btnSaveTrace;
    @UiField Label paymentMethod;
    private void populateOrderInfo(){
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        this.orderId.setText(this.order.getName());
        this.totalPrice.setText(NumberFormat.getFormat("0.00").format(this.order.getTotalPrice()));
        this.dePrice.setText(NumberFormat.getFormat("0.00").format(this.order.getDePrice()));
        this.currency.setText(this.order.getCurrency());
        this.createDate.setText(dateTimeFormat.format(this.order.getCreateDate()));
        this.email.setText(this.order.getCustomerEmail());
        this.customerMsg.setText(this.order.getCustomerMsg());
        this.couponId.setText(order.getCouponCode());
        this.couponPrice.setText(String.valueOf(order.getCouponCutOff()));
        this.shippingType.setText(order.getShippingMethod());
        this.txtTraceInfo.setText(this.order.getTraceInfo());
        this.primaryAddr.setText(populateAddressString(this.order.getPrimaryAddress()));
        if (this.order.isBillingSameAsPrimary()) {
            this.billingAddr.setText(populateAddressString(this.order.getPrimaryAddress()));
        } else {
            this.billingAddr.setText(populateAddressString(this.order.getBillingAddress()));
        }
        if (this.order.getOrderType() == null || "".equals(this.order.getOrderType().trim())) {
            this.paymentMethod.setText("Paypal");
        } else {
            this.paymentMethod.setText(order.getOrderType());
        }
    }
    
    private String populateAddressString(Address addr){
        
        String fullName = addr.getFullName();
        String address1 = addr.getAddress1();
        String address2 = addr.getAddress2();
        String city = addr.getCity();
        String state = addr.getStateProvince();
        String country = this.countryMap.get(String.valueOf(addr.getCountry())) != null ? this.countryMap.get(String.valueOf(addr.getCountry())).getName() : "";
        String postalCode = addr.getPostalCode();
        String tel = addr.getPhone();
        
        StringBuilder address = new StringBuilder();
        address.append(fullName);
        address.append(ADDRESS_SEPARATOR);
        address.append("(");
        address.append(address1);
        if (address2 != null && !"".equals(address2.trim())) {
            address.append(ADDRESS_COMMA);
            address.append("<");
            address.append(address2);
            address.append(">");
        }
        address.append(ADDRESS_COMMA);
        address.append(city);
        address.append(ADDRESS_COMMA);
        address.append(state);
        address.append(ADDRESS_COMMA);
        address.append(postalCode);
        address.append(ADDRESS_COMMA);
        if (this.order.getCustomerCountry()!=null && !"".equals(this.order.getCustomerCountry().trim())) {
            address.append(this.order.getCustomerCountry());
        } else {
            address.append(country);
        }
        address.append(")");
        address.append(ADDRESS_SEPARATOR);
        address.append("Tel: ");
        if (tel!=null && !"".equals(tel.trim())) {
            address.append(tel);
        } else {
            address.append(this.order.getDeliverPhone());
        }
        
        return address.toString();
    }
    
    interface OrderInfoUiBinder extends UiBinder<Widget, OrderInfo> {
    }

    public OrderInfo(Order order) {
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yyyy-MM-dd HH:mm:ss");
        initWidget(uiBinder.createAndBindUi(this));
        this.site = AdminWorkspace.loginInfo.getSite();
        this.order = order;
        this.orderStatus.setSelectedValue(order.getStatus());
        this.countryMap.putAll(AdminWorkspace.loginInfo.getCountryMap());
        populateOrderInfo();
        initOrderInfoHeader();
        if (this.order != null && this.order.getItems() != null && this.order.getItems().size() != 0) {
            for (int i = 0; i < this.order.getItems().size(); i++) {
                this.orderTable.setHTML(i, 0, populateUserOptionString(this.order.getItems().get(i).getUserOptions()));
                this.orderTable.setText(i, 1, dateTimeFormat.format(this.order.getItems().get(i).getCreateDate()));
                this.orderTable.setHTML(i, 2, "<img src='"+"http://"+site.getDomain()+"/"+this.order.getItems().get(i).getProduct().getImages().get(0).getIconUrl()+"'/><a target='_blank' href='"+"http://"+site.getDomain()+"/"+this.order.getItems().get(i).getProduct().getName()+"'>"+this.order.getItems().get(i).getProduct().getTitle()+"</a>");
                this.orderTable.setText(i, 3, String.valueOf(this.order.getItems().get(i).getQuantity()));
                this.orderTable.setText(i, 4, NumberFormat.getFormat("0.00").format(this.order.getItems().get(i).getFinalPrice()));
            }
        }
    }
    
    private String populateUserOptionString(List<UserOption> userOptions){
        StringBuffer html = new StringBuffer();
        html.append("<ul>");
        for (UserOption userOption : userOptions) {
            html.append("<li>");
            html.append(userOption.getName());
            html.append(": ");
            if ("Color".equals(userOption.getName())) {
                html.append(processColor(userOption, userOptions));
            } else {
                html.append(userOption.getValue());
            }
            html.append("</li>");
        }
        
        html.append("</ul>");
        return html.toString();
    }
    
//    private String processSize(UserOption userOption, List<UserOption> userOptions){
//        if ("Customized".equals(userOption.getValue())) {
//            return getOptionData(userOptions, "Customized Size").getValue();
//        } else {
//            return userOption.getValue();
//        }
//    }
    private String processColor(UserOption userOption, List<UserOption> userOptions){
        if ("The Same As Picture".equals(userOption.getName()) || "ASP".equals(userOption.getName()) || "The Same As Picture##ASP".equals(userOption.getName())) {
            return "The Same As Picture";
        } else {
            String[] colorArr = userOption.getValue().split("##");
            if (colorArr != null && colorArr.length != 0) {
                return colorArr[0] + ": " + "<img alt='" +colorArr[0]+ "' src='" + "http://www.joybuy.co.uk" + colorArr[1] + "' title='" + colorArr[0] + "' width='18' height='18'>";
            } else {
                return "";
            }
        }
    }
    
    private void initOrderInfoHeader(){
        this.orderHeader.getColumnFormatter().setWidth(0, "150px");
        this.orderHeader.getColumnFormatter().setWidth(1, "90px");
        this.orderHeader.getColumnFormatter().setWidth(2, "250px");
        this.orderHeader.getColumnFormatter().setWidth(3, "30px");
        this.orderHeader.getColumnFormatter().setWidth(4, "40px");
        
        this.orderHeader.setText(0, 0, "Serial NO.");
        this.orderHeader.setText(0, 1, "Creation Date");
        this.orderHeader.setText(0, 2, "Product Name");
        this.orderHeader.setText(0, 3, "Qty.");
        this.orderHeader.setText(0, 4, "Price");
        
        this.orderTable.getColumnFormatter().setWidth(0, "150px");
        this.orderTable.getColumnFormatter().setWidth(1, "90px");
        this.orderTable.getColumnFormatter().setWidth(2, "250px");
        this.orderTable.getColumnFormatter().setWidth(3, "30px");
        this.orderTable.getColumnFormatter().setWidth(4, "40px");
    }
    
    public void setOrder(Order order) {
        this.order = order;
        this.orderStatus.setSelectedValue(order.getStatus());
    }

    public Order getOrder() {
        return order;
    }

    @UiHandler("button")
    void onButtonClick(ClickEvent event) {
        CommandFactory.lock("Save Order Status").execute();
        //this.order.setStatus(this.orderStatus.getSelectedValue());
        AdminWorkspace.ADMIN_SERVICE_ASYNC.updateOrderStatus(order ,this.orderStatus.getSelectedValue() , new AsyncCallbackAdapter<Order>() {
            @Override
            public void onSuccess(Order result) {
                setOrder(result);
                CommandFactory.release().execute();
            }
        });
    }
    @UiHandler("btnSaveTrace")
    void onBtnSaveTraceClick(ClickEvent event) {
        CommandFactory.lock("Save Order Status").execute();
        this.order.setTraceInfo(this.txtTraceInfo.getValue());
        AdminWorkspace.ADMIN_SERVICE_ASYNC.updateOrderStatus(order, this.orderStatus.getSelectedValue(),  new AsyncCallbackAdapter<Order>() {
            @Override
            public void onSuccess(Order result) {
                setOrder(result);
                CommandFactory.release().execute();
            }
        });
    }
}
