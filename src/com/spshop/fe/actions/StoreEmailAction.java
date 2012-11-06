package com.spshop.fe.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.Email;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.EmailService;
import com.spshop.utils.Constants;

public class StoreEmailAction extends BaseAction {

    @Override
    public ActionForward processer(ActionMapping mapping,
                                   PageFormBean page,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        
        String email = request.getParameter(Constants.SUBSCRIBE_EMAL);
        
        if (StringUtils.isNotBlank(email)) {
            Email e = new Email();
            e.setName(email);
            e.setEmailAddress(email);
            e.setCreateDate(new Date());
            ServiceFactory.getService(EmailService.class).saveEmail(e);
        }
        
        return mapping.findForward(Constants.SUCCESS_VALUE);
    }

}
