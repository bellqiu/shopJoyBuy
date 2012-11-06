package com.spshop.service.intf;

import com.spshop.dao.intf.EmailDAO;
import com.spshop.exception.ServiceValidateException;
import com.spshop.model.Email;

public interface EmailService extends BaseService<Email, EmailDAO, Long> {
    public Email saveEmail(Email email) throws ServiceValidateException;
}
