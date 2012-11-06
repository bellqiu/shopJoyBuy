package com.spshop.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.spshop.dao.intf.MessageDAO;
import com.spshop.model.Message;
import com.spshop.model.User;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.MessageService;

public class MessageServiceImpl extends AbstractService<Message, MessageDAO, Long> implements MessageService {

    @Override
    public List<Message> getMessagesByUser(User user) {
        String hql = "From Message as m where m.user.id = ? and m.replyTo = null order by m.id desc";
        List<Message> orders = new ArrayList<Message>();
        List<Object> cs = (List<Object>)getDao().queryByHQL(hql, 0, 999, user.getId());

        if (null != cs) {
            for (Object object : cs) {
                orders.add(((Message)object).clone());
            }
        }
        return orders;
    }

    @Override
    public List<Message> retrieveNoRepliedMessage() {
        String hql = "From Message as m where m.replied = false and m.replyBy = null order by m.id asc";
        List<Message> messages = new ArrayList<Message>();
        List<Object> cs = (List<Object>)getDao().queryByHQL(hql);

        if (null != cs) {
            for (Object object : cs) {
                messages.add(((Message)object).clone());
            }
        }
        return messages;
    }

    @Override
    public Message replyMessage(Message parent, Message message) {
        message.setReplyTo(parent);
        message = getDao().save(message);
        parent.setReplied(true);
        parent.setReplyBy(message);
        Message ancestor = retrieveAncestorMessage(parent);
        getDao().merge(parent);
        if (ancestor != null) {
            if (ancestor.getUser().getName().equals(message.getUser().getName())) {
                ancestor.setReplied(false);
            } else {
                ancestor.setReplied(true);
            }
            getDao().merge(ancestor);
        }
        return message.clone();
    }
    
    private Message retrieveAncestorMessage(Message message){
        Message ancestor = message;
        while (ancestor.getReplyTo() != null) {
            ancestor = ancestor.getReplyTo();
        }
        return ancestor.clone();
    }
    
    @Override
    public Message fetchById(Long id) {
        return super.fetchById(id).clone();
    }

}
