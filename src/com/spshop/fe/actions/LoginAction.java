package com.spshop.fe.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.User;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.UserService;
import com.spshop.utils.Constants;

public class LoginAction extends BaseAction {
	public static final String CMD_GOTO_CHECK_WITHOUT_LOGIN ="goto_check";
    @Override
    public ActionForward processer(ActionMapping mapping,
                                   PageFormBean page,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
    	
    	if(CMD_GOTO_CHECK_WITHOUT_LOGIN.equals(retrieveCMDURL(request))){
    		request.setAttribute("processCheckWithoutLogin", true);
    	}
    	
    	if("As Guset".equals(request.getParameter("asGeust"))){
    		return mapping.findForward(Constants.SUCCESS_VALUE);
    	}
    	
        String action = request.getParameter(Constants.ACTION);
        HttpSession session = request.getSession();
        if (Constants.LOGIN_ACTION.equals(action)) {
            User user = new User();
            populateLoginInfo(request, user);

            if (StringUtils.isNotEmpty(user.getEmail()) && StringUtils.isNotEmpty(user.getPassword())) {
                user = ServiceFactory.getService(UserService.class).validateUser(user);
                if (user != null) {
                    user.setPassword(null);
                    session.setAttribute(Constants.USER_INFO, user);
                } else {
                    page.addPageProperty("loginError", Constants.LOGIN_FAILURE);
                    return mapping.findForward(Constants.fAILURE_VALUE);
                }

                return mapping.findForward(Constants.SUCCESS_VALUE);
            } else {
                return mapping.findForward(Constants.fAILURE_VALUE);
            }
        } else if (Constants.LOGOUT_ACTION.equals(action)) {
            session.removeAttribute(Constants.USER_INFO);
            return mapping.findForward(Constants.SUCCESS_VALUE);
        } else {
            return mapping.findForward(Constants.fAILURE_VALUE);
        }
    }
    
    private void populateLoginInfo(HttpServletRequest request, User user) {
        String email = request.getParameter("loginEmail");
        String password = request.getParameter("loginPassword");
        user.setEmail(email);
        user.setPassword(password);
    }
    
}