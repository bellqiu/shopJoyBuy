package com.spshop.admin.client.businessui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.CommandFactory;
import com.spshop.model.OrderItem;
import com.spshop.model.UserOption;

public class ShowOrderItemDetails extends ObservableComposite<List<com.spshop.model.UserOption>, ShowOrderItemDetails> {

    private static ShowOrderItemDetailsUiBinder uiBinder = GWT.create(ShowOrderItemDetailsUiBinder.class);
    
    private OrderItem item = new OrderItem();

    interface ShowOrderItemDetailsUiBinder extends UiBinder<Widget, ShowOrderItemDetails> {
    }

    public ShowOrderItemDetails() {
        initWidget(uiBinder.createAndBindUi(this));
    }

    @UiField
    Button showDetails;

    public ShowOrderItemDetails(OrderItem item) {
        initWidget(uiBinder.createAndBindUi(this));
        this.item = item;
    }

    @UiHandler("showDetails")
    void onClick(ClickEvent e) {
        CommandFactory.popUpShowDetails(false, item).execute();
    }

    public void setText(String text) {
        showDetails.setText(text);
    }

    public String getText() {
        return showDetails.getText();
    }

    @Override
    public void setComponent(List<UserOption> component) {
        // TODO Auto-generated method stub
        
    }

}
