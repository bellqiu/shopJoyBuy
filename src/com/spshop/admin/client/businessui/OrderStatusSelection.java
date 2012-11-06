package com.spshop.admin.client.businessui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.ListBox;
import com.spshop.model.enums.OrderStatus;

public class OrderStatusSelection extends Composite {

    private static OrderStatusSelectionUiBinder uiBinder = GWT.create(OrderStatusSelectionUiBinder.class);
    
    @UiField ListBox orderStatus;

    interface OrderStatusSelectionUiBinder extends UiBinder<Widget, OrderStatusSelection> {
    }
    
    public OrderStatusSelection() {
        initWidget(uiBinder.createAndBindUi(this));
        for (OrderStatus status : OrderStatus.values()) {
            orderStatus.addItem(status.getTitle(), status.getValue());
        }
    }

    public void setSelectedValue(String currentItem) {
        for (int i = 0; i < this.orderStatus.getItemCount(); i++) {
            if (orderStatus.getValue(i).equals(currentItem)) {
                orderStatus.setSelectedIndex(i);
            }
        }
    }

    public String getSelectedValue() {
        return orderStatus.getValue(orderStatus.getSelectedIndex());
    }

}
