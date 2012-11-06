package com.spshop.fe.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.cache.SCacheFacade;
import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.Order;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.OrderService;
import com.spshop.utils.Constants;

public class PayAction extends BaseAction {
	@Override
	public ActionForward processer(ActionMapping mapping, PageFormBean page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward(Constants.SUCCESS_VALUE);
	}

}