package com.spshop.admin.client.businessui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.CommandFactory;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.model.Message;

public class PostMessage extends Composite {

    private static PostMessageUiBinder uiBinder = GWT.create(PostMessageUiBinder.class);

    private List<Message> messageList = new ArrayList<Message>();
    
    interface PostMessageUiBinder extends UiBinder<Widget, PostMessage> {
    }

    public PostMessage() {
        initWidget(uiBinder.createAndBindUi(this));
        initTable();
    }
    

    @UiField
    Button postMsg;
    @UiField FlexTable msgThread;
    @UiField FlexTable msgHeader;
    @UiField TextArea msgContent;

    private void initTable(){
        msgHeader.getColumnFormatter().setWidth(0, "140px");
        msgHeader.getColumnFormatter().setWidth(1, "600px");
        
        msgHeader.setText(0, 0, "Customer");
        msgHeader.setText(0, 1, "Message");
        
        msgThread.getColumnFormatter().setWidth(0, "140px");
        msgThread.getColumnFormatter().setWidth(1, "600px");
    }

    @UiHandler("postMsg")
    void onClick(ClickEvent e) {
        CommandFactory.lock("Replying Message").execute();
        Message parent = getMessageList().get(getMessageList().size()-1);
        Message message = populateMessage(getMessageList().get(getMessageList().size()-1), this.msgContent.getText());
        AdminWorkspace.ADMIN_SERVICE_ASYNC.replyMessage(parent, message, new AsyncCallbackAdapter<Message>() {
            @Override
            public void onSuccess(Message result) {
                getMessageList().add(result);
                setMessageList(getMessageList());
                msgContent.setText("");
                CommandFactory.release().execute();
            }
        });
    }
    
    private Message populateMessage(Message parentMessage, String content) {
        Message msg = new Message();
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
        msg.setContent(content);
        msg.setName(dateTimeFormat.format(new Date()).trim());
        msg.setCreateDate(new Date());
        msg.setReplied(true);
        msg.setUser(AdminWorkspace.loginInfo.getLoginUser());
        return msg;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
        int i = 0; 
        for(Message msg : messageList){
            msgThread.setText(i, 0, msg.getUser().getName());
            msgThread.setText(i, 1, msg.getContent());
            i++;
        }
    }

    public List<Message> getMessageList() {
        return messageList;
    }

}
