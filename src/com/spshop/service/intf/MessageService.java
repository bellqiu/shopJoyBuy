package com.spshop.service.intf;

import java.util.List;

import com.spshop.dao.intf.MessageDAO;
import com.spshop.model.Message;
import com.spshop.model.User;

public interface MessageService extends BaseService<Message, MessageDAO, Long> {
    public List<Message> getMessagesByUser(User user);
    
    public List<Message> retrieveNoRepliedMessage();
    
    public Message replyMessage(Message parent, Message message);
}
