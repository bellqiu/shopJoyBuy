package com.spshop.fe.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.cache.SCacheFacade;
import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.Country;
import com.spshop.model.Order;
import com.spshop.model.Product;
import com.spshop.model.User;
import com.spshop.model.UserOption;
import com.spshop.model.cart.ShoppingCart;
import com.spshop.model.enums.OrderStatus;
import com.spshop.model.enums.SelectType;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CountryService;
import com.spshop.service.intf.OrderService;
import com.spshop.utils.Constants;
import com.spshop.utils.EmailTools;

public class ShoppingCartAction extends BaseAction {
	
	private static final String PRODUCT_ID = "ProductId";
	private static final String COLOR = "color";
	private static final String QTY = "qty";
	private static final String TEXT = "text";
	private static final String TEXTS = "texts";
	private static final String SPLITER = "@";
	private static final String ADDITEM = "addItem";
	private static final String UPDATEITEM = "updateItem";
	private static final String REMOVEITEM = "removeItem";
	private static final String ITEMNAME = "itemName";
	private static final String OPERATION = "operation";
	private static final String CHECKOUT = "checkout";
	private static final String PAYMENT = "payment";
	private static final String ADDRESS_TYPE = "addressType";
	
	
	private static final String PAYMEMT_PAYPAL="paypal";
	
	private static final String ADDRESS_MA="MA";
	private static final String ADDRESS_PA="PA";
	private static final String ADDRESS_LA="LA";
	
	public static final String CMD_CHECK = "check";
	
	private static Logger logger = Logger.getLogger(ShoppingCartAction.class);
	
	@SuppressWarnings("unchecked")
	private List<UserOption> retriveUserOptions(ServletRequest request){
		List<UserOption> options = new ArrayList<UserOption>();
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String param = params.nextElement();
			String[] ps = param.split(SPLITER);
			if(ps.length>1){
				UserOption option = new UserOption();
				option.setCreateDate(new Date());
				option.setOptionName(ps[1]);
				if(COLOR.equals(ps[0])){
					option.setValue(request.getParameter(param));
					option.setOptionType(SelectType.COLOR_SINGLE);
					options.add(option);
				}
				
				if(TEXT.equals(ps[0])){
					option.setValue(request.getParameter(param));
					option.setOptionType(SelectType.SINGLE_LIST);
					options.add(option);
				}
				
				if(TEXTS.equals(ps[0])){
					
				}
			}
		}
		return options;
	}
	
	
	
	private String retriveProductId(ServletRequest request){
		String productId = null;
		
		try {
			productId = request.getParameter(PRODUCT_ID);
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		}
		
		return productId;
	}
	
	private int retriveQty(ServletRequest request){
		int quantity = 1;

		try {
			quantity = Integer.parseInt(request.getParameter(QTY));
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		}
		
		if(quantity<1){
			quantity = 1;
		}
		
		return quantity;
	}
	
	private String retriveItemName(ServletRequest request){
		String name = "";

		try {
			name = request.getParameter(ITEMNAME);
		} catch (NumberFormatException e) {
			
			//e.printStackTrace();
		}
		return name;
	}
	
	private String retriveOperation(ServletRequest request){
		return request.getParameter(OPERATION);
	}
	
	private String retrivePaymentType(ServletRequest request){
		return request.getParameter(PAYMENT);
	}
	
	private String retriveAddressType(ServletRequest request){
		return request.getParameter(ADDRESS_TYPE);
	}
	
	
	private String retriveAddress(ServletRequest request){
		return request.getParameter("MemberContactAddr[0]");
	}
	private String retriveFirstName(ServletRequest request){
		return request.getParameter("MemberContact[0]");
	}
	private String retriveLastName(ServletRequest request){
		return request.getParameter("MemberContact[1]");
	}
	private String retriveCountry(ServletRequest request){
		return request.getParameter("MemberState");
	}
	private String retriveZipCode(ServletRequest request){
		return request.getParameter("MemberZip");
	}
	private String retrivePhone(ServletRequest request){
		return request.getParameter("MemberContactPhone");
	}
	private String retriveEmail(ServletRequest request){
		return request.getParameter("MemberEmail");
	}
	
	private String retrieveDeType(ServletRequest request){
		if("Standard".equals(request.getParameter("shippingMethod"))){
			return "Standard";
		}
		return "Expedited";
	}
	
	private String retrieveBillingAddress(ServletRequest request){
		if("false".equals(request.getParameter("billing_address"))){
			return "false";
		}
		return "true";
	}
	
	private void retriveShippingInfo(HttpServletRequest request,HttpServletResponse response,boolean check){
		
		Order order = getCart(request, response).getOrder();
		order.setCity(request.getParameter("MemberCtiy"));
		order.setCustomerName(request.getParameter("MemberContact[0]")+","+request.getParameter("MemberContact[1]"));
		order.setCustomerAddress(request.getParameter("MemberContactAddr[0]"));
		order.setCustomerAddress2(request.getParameter("MemberContactAddr[1]"));
		order.setCustomerZipcode(request.getParameter("MemberZip"));
		order.setDeliverPhone(request.getParameter("MemberContactPhone"));
		order.setCustomerEmail(request.getParameter("MemberEmail"));
		order.setCustomGender(request.getParameter("gender"));
		order.setCustomerMsg(request.getParameter("Remarks"));
		order.setState(request.getParameter("MemberProvince"));
		
		if(check){
			if(null == order.getCity() || !order.getCity().matches(".{1,50}")){
				addError("city", "Not a valid city", request);
			}
			
			if(null == order.getCfirstName() || !order.getCfirstName() .matches(".{1,255}")){
				addError("firstName", "Not a valid Name", request);
			}
			
			if(null == order.getClastName() || !order.getClastName().matches(".{1,255}")){
				addError("lastName", "Not a valid Name", request);
			}
			
			if(null == order.getCustomerAddress() || !order.getCustomerAddress().matches(".{1,400}")){
				addError("address", "Not a valid Address", request);
			}
			
			if(null == order.getState() || !order.getState().matches(".{1,200}")){
				addError("state", "Not a valid State", request);
			}
			
			if(null == order.getCustomerCountry() || !order.getCustomerCountry().matches(".{1,255}")){
				addError("country", "Not a valid Country", request);
			}
			
			if(null == order.getCustomerZipcode() || !order.getCustomerZipcode().matches(".{1,255}")){
				addError("zipCode", "Not a valid ZipCode", request);
			}
			
			if(null == order.getDeliverPhone() || !order.getDeliverPhone().matches(".{1,255}")){
				addError("phone", "Not a valid Phone", request);
			}
			
			if(null == order.getCustomerEmail() || !order.getCustomerEmail().matches(".{1,255}")){
				addError("email", "Not a valid Email", request);
			}
		}
		
	}
	
	private void retriveBShippingInfo(HttpServletRequest request,HttpServletResponse response,boolean check){
		Order order = getCart(request, response).getOrder();
		order.setBcity(request.getParameter("MemberCtiy_b"));
		order.setBcustomerName(request.getParameter("MemberContact_b[0]")+","+request.getParameter("MemberContact_b[1]"));
		order.setBcustomerAddress(request.getParameter("MemberContactAddr_b[0]"));
		order.setBcustomerAddress2(request.getParameter("MemberContactAddr_b[1]"));
		order.setBcustomerZipcode(request.getParameter("MemberZip_b"));
		order.setBcustomGender(request.getParameter("ConsigneeGender_b"));
		order.setBphone(request.getParameter("MemberContactPhone_b"));
		order.setBstate(request.getParameter("MemberProvince"));
		//bMemberProvince
		
		if(check){
			if(null == order.getBcity() || !order.getBcity().matches(".{1,50}")){
				addError("bcity", "Not a valid city", request);
			}
			
			if(null == order.getBfirstName() || !order.getBfirstName().matches(".{1,255}")){
				addError("bfirstName", "Not a valid Name", request);
			}
			
			if(null == order.getBlastName() || !order.getBlastName().matches(".{1,255}")){
				addError("blastName", "Not a valid Name", request);
			}
			
			if(null == order.getBcustomerAddress() || !order.getBcustomerAddress().matches(".{1,400}")){
				addError("baddress", "Not a valid Address", request);
			}
			
			if(null == order.getBstate() || !order.getBstate().matches(".{1,200}")){
				addError("bstate", "Not a valid State", request);
			}
			
			if(null == order.getBcustomerZipcode() || !order.getBcustomerZipcode().matches(".{1,255}")){
				addError("bzipCode", "Not a valid ZipCode", request);
			}
			
			if(null == order.getBphone() || !order.getBphone() .matches(".{1,255}")){
				addError("bphone", "Not a valid Phone", request);
			}
		}
	}
	
	@Override
	public ActionForward processer(ActionMapping mapping, PageFormBean page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<String> errorStrings = new ArrayList<String>();
		List<String> msgs = new ArrayList<String>();
		if(CMD_CHECK.equals(retrieveCMDURL(request))){
			
			
			 User user = (User)request.getSession().getAttribute(Constants.USER_INFO);
			 Order order2 = getCart(request, response).getOrder();
			 if(null != user && null!=order2.getUser()){
				 order2.setUser(user);
				 order2.setCity(user.getCity());
				 order2.setCustomerName(user.getFirstName()+","+user.getLastName());
				 order2.setCustomerAddress(user.getAddress());
				 order2.setCustomerZipcode(user.getZipcode());
				 order2.setDeliverPhone(user.getTelephone());
				 order2.setCustomerEmail(user.getEmail());
				 order2.setBcustomGender(user.getGender());
			 }
			
			request.setAttribute("billing_address", "true");
			request.setAttribute("showCheckOption", true);
			String country  = retriveCountry(request);
			try {
				int countryID = -1;
				
				try {
					countryID = Integer.parseInt(country);
				} catch (Exception e) {
				}
				
				Country c = null;
				
				if(countryID < 0){
					List<Country> cd = SCacheFacade.getCounties();
					c = cd.get(0);
				}else{
					c = ServiceFactory.getService(CountryService.class).getCountryById(countryID);
				}
				
				if("Standard".equals(retrieveDeType(request))){
					getCart(request, response).getOrder().setDePrice(c.getDePrice());
					getCart(request, response).getOrder().setShippingMethod("Standard");
					request.setAttribute("shippingMethod", "Standard");
				}else{
					getCart(request, response).getOrder().setDePrice(c.getAdDePrice());
					request.setAttribute("shippingMethod", "Expedited");
					getCart(request, response).getOrder().setShippingMethod("Expedited");
				}
				getCart(request, response).getOrder().setCustomerCountry(c.getName());
				if(!"true".equals(retrieveBillingAddress(request))){
					request.setAttribute("billing_address", "false");
				}else{
					request.setAttribute("billing_address", "true");
				}
				request.setAttribute("defaultCountry", c);
				retriveShippingInfo(request,response,false);
				retriveBShippingInfo(request,response,false);
			} catch (Exception e) {
			}
			
			
			if("CONTINUE".equals(retriveOperation(request))){
				retriveShippingInfo(request,response,true);
				if(!"true".equals(retrieveBillingAddress(request))){
					retriveBShippingInfo(request,response,true);
				}
				if(getError(request).isEmpty()){
					if(PAYMEMT_PAYPAL.equals(retrivePaymentType(request))){
						paypalPay(request, response, errorStrings, msgs);
						return null;
					}
				}
			}
			updateCart(request, response, OrderStatus.ONSHOPPING);
		}
		
		
		
		Order order = getCart(request,response).getOrder();
		
		if(ADDITEM.equals(retriveOperation(request))){
			int qty = retriveQty(request);
			Product product = SCacheFacade.getProduct(retriveProductId(request));
			List<UserOption> options = retriveUserOptions(request);
			
			getCart(request,response).addItem(product,options,qty);
			updateCart(request, response,OrderStatus.ONSHOPPING);
		}
	
		if(UPDATEITEM.equals(retriveOperation(request))){
			int qty = retriveQty(request);
			String itemName = retriveItemName(request);
			getCart(request,response).update(itemName,qty);
			updateCart(request, response,OrderStatus.ONSHOPPING);
		}
	
		if(REMOVEITEM.equals(retriveOperation(request))){
			String itemName = retriveItemName(request);
			getCart(request,response).remove(itemName);
			updateCart(request, response,OrderStatus.ONSHOPPING);
		}
		
		if("pay".equals(retriveOperation(request))){
			Map<String,Object> root = new HashMap<String,Object>(); 
			root.put("order", order);
			root.put("currencyRate", getCurrencies(request).get(order.getCurrency()));
			//EmailTools.sendMail("paid", "Order Received, Awaiting Payment Confirmation", root,order.getCustomerEmail());
			order = ServiceFactory.getService(OrderService.class).saveOrder(order, OrderStatus.PENDING.getValue());
			clearCart(request);
		}
		if("goToCheck".equals(retriveOperation(request))){
			if("repay".equals(request.getParameter("operation_Type"))){
				try{
					Order order2 = ServiceFactory.getService(OrderService.class).getOrderById(request.getParameter("orderId"));
					//order2.setStatus(OrderStatus.ONSHOPPING.getValue());
					getCart(request, response).setOrder(order2);
				}catch(Exception exception){
					logger.warn(exception.getMessage(), exception);
				}
			}
			if(null == request.getSession().getAttribute(Constants.USER_INFO)){
				response.sendRedirect("/login/cmd/goto_check");
				return null;
			}else{
				 response.sendRedirect("/shopping/cmd/check");
				 return null;
			}
			//request.setAttribute("showCheckOption", true);
		}
		
		request.setAttribute(Constants.REQUEST_ERROR, errorStrings);
		request.setAttribute(Constants.REQUEST_MSG, msgs);
		return mapping.findForward(Constants.SUCCESS_VALUE);

	}
	
	private User retriveUser(HttpServletRequest request){
		return (User) request.getSession().getAttribute(Constants.USER_INFO);
	}
	
	
	private void clearCart(HttpServletRequest request){
		Order order = new Order();
		order.setCreateDate(new Date());
		ShoppingCart shoppingCart= new ShoppingCart(order);
		order.setStatus(OrderStatus.ONSHOPPING.getValue());
		order.setName(getOrderId());
		order.setCurrency(getCurrencyName(request));
		request.getSession().setAttribute(SHOPPINGCART, shoppingCart);
		request.getSession().setAttribute(Constants.DEFAULT_ORDER, null);
	}
	
	private void updateCart(HttpServletRequest request,  HttpServletResponse response, OrderStatus status){
		if(null == request.getSession().getAttribute(Constants.USER_INFO)){
			getCart(request,response).getOrder().setUser(null);
		}
		if(null!=getCart(request,response).getOrder().getItems()&&getCart(request,response).getOrder().getItems().size()>0){
			getCart(request,response).setOrder(ServiceFactory.getService(OrderService.class).saveOrder(getCart(request,response).getOrder(), status.getValue()));
		}else{
			Order order = new Order();
			order.setCreateDate(new Date());
			//order.setStatus(OrderStatus.ONSHOPPING.getValue());
			order.setName(getOrderId());
			order.setCurrency(getCurrencyName(request));
			getCart(request,response).setOrder(order);
		}
	}
	
	private void paypalPay(HttpServletRequest request,  HttpServletResponse response, List<String> errorStrings, List<String> msg) throws ServletException, IOException{

		
		String payment = retrivePaymentType(request);
		if(PAYMEMT_PAYPAL.equals(payment)){
			Order order = getCart(request, response).getOrder();
			if(null==order.getItems()||order.getItems().size()<1){
				errorStrings.add("Shopping cart is empty!");
			}else{
				order.setOrderType(payment);
				/*if(null!=retriveUser(request)){
					order.setUser(retriveUser(request));
					order.setAddressType(ADDRESS_LA);
					User user = retriveUser(request);
					
					order.setCustomerAddress(user.getAddress());
					order.setCustomerCountry(user.getCountry());
					order.setCustomerEmail(user.getEmail());
					order.setCustomerName(user.getLastName()+" " + user.getFirstName());
					order.setCustomerZipcode(user.getZipcode());
					order.setDeliverPhone(user.getTelephone());
					order.setCity(user.getCity());
					
				}*//*else if(ADDRESS_MA.equals(retriveAddressType(request))){
					
					order.setCustomerAddress(retriveAddress(request));
					order.setCustomerCountry(retriveCountry(request));
					order.setCustomerEmail(retriveEmail(request));
					order.setCustomerName(retriveLastName(request) +" " + retriveFirstName(request));
					order.setCustomerZipcode(retriveZipCode(request));
					order.setDeliverPhone(retrivePhone(request));
					
				}else{*/
					order.setAddressType(ADDRESS_PA);
//				}
				order.setName(getOrderId());
				order = ServiceFactory.getService(OrderService.class).saveOrder(order, OrderStatus.PENDING.getValue());
				request.setAttribute(Constants.DEFAULT_ORDER, order);
				updateCart(request, response,OrderStatus.PENDING);
				final Map<String,Object> root = new HashMap<String,Object>(); 
				final Order o = order;
				root.put("order", order);
				root.put("currencyRate", getCurrencies(request).get(order.getCurrency()));
				new Thread(){
					public void run() {
						try{
							EmailTools.sendMail("paid", "Order Received, Awaiting Payment Confirmation", root,o.getCustomerEmail());
						}catch(Exception e){
							
						}
					};
				}.start();
				clearCart(request);
				request.getRequestDispatcher("/jsp/pay_payPal.jsp").forward(request, response);
			}
		}
		
	}
	
}