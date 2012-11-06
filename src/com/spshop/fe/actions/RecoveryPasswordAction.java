package com.spshop.fe.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.User;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.UserService;
import com.spshop.utils.Constants;
import com.spshop.utils.EmailTools;

public class RecoveryPasswordAction extends BaseAction {
	@Override
	public ActionForward processer(ActionMapping mapping, PageFormBean page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String email = request.getParameter("recoveryEmail");
		
		if (email != null) {
		    User user = ServiceFactory.getService(UserService.class).queryUserByEmail(email);
		    if (user != null) {
		        final Map<String,Object> root = new HashMap<String,Object>(); 
	            final User u = user;
	            root.put("user", u);
	            new Thread(){
	                public void run() {
	                    try{
	                        EmailTools.sendMail("recovery", "Congratulations! Your password is found", root, u.getName());
	                    }catch(Exception e){
	                        
	                    }
	                };
	            }.start();
		    }
        } else {
            return mapping.findForward(Constants.fAILURE_VALUE);
        }
		return mapping.findForward(Constants.SUCCESS_VALUE);
	}

}