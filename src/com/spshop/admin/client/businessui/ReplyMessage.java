package com.spshop.admin.client.businessui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.businessui.callback.SelectedCallBack;
import com.spshop.model.Component;
import com.spshop.model.Message;
import com.spshop.model.Order;

public class ReplyMessage extends ObservableComposite<List<com.spshop.model.Message>, ReplyMessage> {

    private static ReplyMessageUiBinder uiBinder = GWT.create(ReplyMessageUiBinder.class);

    interface ReplyMessageUiBinder extends UiBinder<Widget, ReplyMessage> {
    }

    public ReplyMessage(Message message, Long userId) {
        initWidget(uiBinder.createAndBindUi(this));
        this.message = message;
        AdminWorkspace.ADMIN_SERVICE_ASYNC.queryOrdersByUserId(userId, new AsyncCallbackAdapter<List<Order>>(){
            @Override
            public void onSuccess(List<Order> rs) {
                recentOrders.addAll(rs);
                CommandFactory.release().execute();
            }
        });
    }

    @UiField
    Button replyMsg;
    
    private final Message message;
    private final List<Order> recentOrders = new ArrayList<Order>();;

    @UiHandler("replyMsg")
    void onReplyMsgClick(ClickEvent e) {
        CommandFactory.popUpReplyPanel(true, message, recentOrders, new SelectedCallBack() {
            
            @Override
            public void callBack(List<Component> selectedItems) {
                Window.alert(selectedItems.toString());
            }
        }).execute();
    }

    @Override
    public void setComponent(List<Message> component) {
        // TODO Auto-generated method stub

    }

}
