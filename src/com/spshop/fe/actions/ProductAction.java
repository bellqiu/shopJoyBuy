package com.spshop.fe.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.cache.SCacheFacade;
import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.Product;
import com.spshop.utils.Constants;

public class ProductAction extends BaseAction {

	@Override
	public ActionForward processer(ActionMapping mapping, PageFormBean page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String[] uris = request.getRequestURI().split("/");
		String productName = uris[uris.length - 1];
		Product product = SCacheFacade.getProduct(productName);
		/*Product displayProduct = product.clone();
		if (displayProduct != null) {

			String index = request.getParameter("itemIndex");
			if (null != index && !"".equals(index)) {
				int itemIndex = Integer.valueOf(request
						.getParameter("itemIndex"));
				HttpSession session = request.getSession();
				String email = (String) session.getAttribute("email");
				Order order = SCacheFacade.getOrder(email);
				if (order != null && order.getItems() != null) {
					OrderItem userOrderItem = order.getItems().get(itemIndex);
					if (userOrderItem != null) {
						for (UserOption uo : userOrderItem.getUserOptions()) {
							for (ProductOption po : displayProduct.getOptions()) {
								if (po.getName().equals(uo.getOptionName())) {
									po.setDefaultValue(uo.getValue());
								}
							}
						}
						page.addPageProperty("displayOrderItem", "displayOrderItem");
					}
				}
			}*/

			page.addPageProperty("productDetail", product);
			populatePathNodesForPage(product.getCategories().get(0),
					page.getPathNodes());
//		}

		return mapping.findForward(Constants.SUCCESS_VALUE);
	}
}
