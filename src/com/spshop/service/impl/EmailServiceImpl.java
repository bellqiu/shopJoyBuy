package com.spshop.service.impl;

import com.spshop.dao.intf.EmailDAO;
import com.spshop.exception.ServiceValidateException;
import com.spshop.model.Email;
import com.spshop.service.AbstractService;
import com.spshop.service.intf.EmailService;

public class EmailServiceImpl extends AbstractService<Email, EmailDAO, Long> implements EmailService {

    @Override
    public Email saveEmail(Email email) throws ServiceValidateException {
        return save(email).clone();
    }

}
