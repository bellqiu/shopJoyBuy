package com.spshop.admin.client.businessui;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.businessui.callback.SelectedCallBack;
import com.spshop.model.Component;
import com.spshop.model.Order;
import com.spshop.model.User;

public class ShowOrder extends ObservableComposite<List<com.spshop.model.Order>, ShowOrder> {

    private static ShowOrderUiBinder uiBinder = GWT.create(ShowOrderUiBinder.class);
    @UiField Button showOrder;
    
    private final User user;

    interface ShowOrderUiBinder extends UiBinder<Widget, ShowOrder> {
    }
    
    public ShowOrder(User user) {
        initWidget(uiBinder.createAndBindUi(this));
        this.user = user;
    }

    @UiHandler("showOrder")
    void onShowOrderClick(ClickEvent event) {
        CommandFactory.popUpShowOrder(true, user, new SelectedCallBack() {
            
            @Override
            public void callBack(List<Component> selectedItems) {
                Window.alert(selectedItems.toString());
            }
        }).execute();
    }

    @Override
    public void setComponent(List<Order> component) {
        // TODO Auto-generated method stub
        
    }

}
