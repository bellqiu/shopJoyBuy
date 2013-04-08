package com.spshop.web;

import static com.spshop.utils.Constants.CURRENT_ORDER;
import static com.spshop.utils.Constants.C_USER_FIRST_NAME;
import static com.spshop.utils.Constants.C_USER_LAST_NAME;
import static com.spshop.utils.Constants.DEFAULT_CURRENCY;
import static com.spshop.utils.Constants.FIRST_NAME_ERR;
import static com.spshop.utils.Constants.LAST_NAME_ERR;
import static com.spshop.utils.Constants.MEASUREMENT_MSG;
import static com.spshop.utils.Constants.PAGINATION;
import static com.spshop.utils.Constants.REG_PWD_RE_ERR;
import static com.spshop.utils.Constants.REG_USER_NAME_SUC;
import static com.spshop.utils.Constants.SHIPPING_EXPEDITED;
import static com.spshop.utils.Constants.SHIPPING_METHOD;
import static com.spshop.utils.Constants.SHIPPING_STANDARD;
import static com.spshop.utils.Constants.SITE_VIEW;
import static com.spshop.utils.Constants.SUIT_MEASUREMENT;
import static com.spshop.utils.Constants.TXT_NEW_PWD1;
import static com.spshop.utils.Constants.TXT_NEW_PWD2;
import static com.spshop.utils.Constants.TXT_PWD;
import static com.spshop.utils.Constants.UPDATE_ACC_SUC;
import static com.spshop.utils.Constants.USER_ORDERS;
import static com.spshop.utils.Constants.USER_ORDERS_COUNT;
import static com.spshop.utils.Constants.WRONG_PWD;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.NumberFormat;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.spshop.model.Country;
import com.spshop.model.Message;
import com.spshop.model.Order;
import com.spshop.model.SuitMeasurement;
import com.spshop.model.User;
import com.spshop.model.enums.OrderStatus;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CountryService;
import com.spshop.service.intf.MessageService;
import com.spshop.service.intf.OrderService;
import com.spshop.service.intf.UserService;
import com.spshop.utils.Constants;
import com.spshop.utils.EmailTools;
import com.spshop.utils.EncryptUtil;
import com.spshop.utils.Utils;
import com.spshop.web.view.SiteView;

@Controller
@SessionAttributes({ "continueShopping" })
@RequestMapping("/uc")
public class UserCenterController extends BaseController {

	Logger logger = Logger.getLogger(UserCenterController.class);
	
	//private static final String ORDER_ID = "orderId";

	@RequestMapping("/changePwd")
	public String changePwd(Model model) {
		return "changePwd";
	}

	@RequestMapping(value = "/changePwd", method = RequestMethod.POST)
	public String changePwd2(Model model, HttpServletRequest request) {
		String oldPWD = request.getParameter(TXT_PWD);
		String pwd1 = request.getParameter(TXT_NEW_PWD1);
		String pwd2 = request.getParameter(TXT_NEW_PWD2);
		User user = ServiceFactory.getService(UserService.class)
				.queryUserByEmail(getUserView().getLoginUser().getEmail());
		if (null != user && !user.getPassword().equals(oldPWD)) {
			getUserView().getErr().put(WRONG_PWD, "Original password is wrong");
		}
		
		if (pwd1 == null || pwd1.length() < 6) {
			getUserView().getErr().put(REG_PWD_RE_ERR, "Invalid password");
		} else if (!pwd1.equals(pwd2)) {
			getUserView().getErr().put(REG_PWD_RE_ERR,
					"Two new passwords are not same");
		}

		if (getUserView().getErr().isEmpty() && null != user) {
			user.setPassword(pwd1);
			user = ServiceFactory.getService(UserService.class).save(user);
			getUserView().getMsg()
					.put(REG_USER_NAME_SUC, "Update successfully");
		}

		return "changePwd";
	}

	@RequestMapping("/userProfile")
	public String userProfile(Model model) {
		return "userProfile";
	}
	
	/*@RequestMapping("/updateOrderAddress")
	public String updateOrderAddress(Model model,
			HttpServletRequest request, HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException{
		
		String orderId = request.getParameter(ORDER_ID);
		String addType = request.getParameter(Constants.ADD_TYPE);
		
		Order order = null;

		if(StringUtils.isNotBlank(orderId)){
			order = ServiceFactory.getService(OrderService.class).getOrderById(orderId);
		}else{
			order = getUserView().getCart().getOrder();
		}
		
		if(order != null){
			
			Address address = retrieveAddress(request);
			
			if(Constants.ADD_TYPE_P.equals(addType)){
				address.setCountry(order.getPrimaryAddress().getCountry());
				order.setPrimaryAddress(address);
				ServiceFactory.getService(OrderService.class).saveOrder(order, order.getStatus());
			}else{
				order.setBillingSameAsPrimary(false);
				address.setCountry(order.getPrimaryAddress().getCountry());
				order.setBillingAddress(address);
			}
			
			
			JsonFactory f = new JsonFactory();
			ObjectMapper mapper = new ObjectMapper(f);
			mapper.writeValue(response.getOutputStream(), address);
		
		}
		
		return null;
	}*/

	@RequestMapping("/retrieveShippingPrice")
	public String retrieveShippingPrice(Model model,
			HttpServletRequest request, HttpServletResponse response,
			@RequestParam("cc") int cc) throws IOException {

		Country country = ServiceFactory.getService(CountryService.class)
				.getCountryById(cc);

		Map<String, String> rs = new HashMap<String, String>();

		String shippingMethod = request.getParameter(SHIPPING_METHOD);

		if (null != country) {

			float dePrice = 0f;
			float adDePrice = 0f;

			if (getUserView().getCart().getOrder().getTotalPrice() > country
					.getFreeAdDePrice()) {
				adDePrice = 0;
			} else {
				adDePrice = country.getAdDePrice();
			}

			if (getUserView().getCart().getOrder().getTotalPrice() > country
					.getFreeDePrice()) {
				dePrice = 0;
			} else {
				dePrice = country.getDePrice();
			}

			if (SHIPPING_EXPEDITED.equals(shippingMethod)) {

				rs.put("grandTotal",
						Utils.toNumber((getUserView().getCart().getOrder()
								.getTotalPrice()
								+ adDePrice - getUserView().getCart()
								.getOrder().getCouponCutOff())
								* getUserView().getCurrencyRate()));
				rs.put("shippingCost",
						Utils.toNumber(adDePrice
								* getUserView().getCurrencyRate()));
			} else {

				rs.put("grandTotal",
						Utils.toNumber((getUserView().getCart().getOrder()
								.getTotalPrice()
								+ dePrice - getUserView().getCart().getOrder()
								.getCouponCutOff())
								* getUserView().getCurrencyRate()));
				rs.put("shippingCost",
						Utils.toNumber(dePrice
								* getUserView().getCurrencyRate()));
			}

			if (null != country) {
				rs.put(SHIPPING_STANDARD,
						Utils.toNumber(dePrice
								* getUserView().getCurrencyRate()));
				rs.put(SHIPPING_EXPEDITED,
						Utils.toNumber(adDePrice
								* getUserView().getCurrencyRate()));

			}
		}

		JSONObject jsonObject = JSONObject.fromObject(rs);

		response.getWriter().print(jsonObject);

		return null;
	}

	@RequestMapping(value = "/shoppingCart_address", method = RequestMethod.GET)
	public String shoppingCartAdress(Model model, @RequestParam(value="id", required=false) String id) {
		
		if(null != id){
			Order order = ServiceFactory.getService(OrderService.class).getCartOrPendingOrderById(id, getUserView().getLoginUser().getId());
			
			
			String stdShippingPrice  = "0";
			String extShippingPrice  = "0";
			
			model.addAttribute(Constants.PROCESSING_ORDER, order);
			if(null!=order && order.getItems().size()>0 ){
				if(null != order.getShippingAddress()){
					Country country = ServiceFactory.getService(CountryService.class).getCountryById(Long.valueOf(order.getShippingAddress().getCountry()));
					float rate = getSiteView().getCurrencies().get(order.getCurrency());
					if(null != country && order.getTotalPrice() < country.getFreeAdDePrice()){
						extShippingPrice = Utils.toNumber(country.getAdDePrice()*rate);
						if(order.getTotalPrice() < country.getFreeDePrice()){
							stdShippingPrice = Utils.toNumber(country.getDePrice()*rate);
						}
						
					}
				}
			}else{
				order = null;
			}
			
			model.addAttribute("stdShippingPrice", stdShippingPrice);
			model.addAttribute("extShippingPrice", extShippingPrice);
			model.addAttribute(Constants.PROCESSING_ORDER, order);
			
		}
		
		
		return "shoppingCart_address";
	}

	@RequestMapping("/shoppingCart_payment")
	public String shoppingCartPayment(HttpServletRequest request, Model model) {
		
		Order order = null;
		String orderId = request.getParameter("id");
		if(StringUtils.isNotBlank(orderId)){
			order = ServiceFactory.getService(OrderService.class).getOrderById(orderId);
			model.addAttribute(Constants.CURRENT_ORDER, order);
			model.addAttribute("id", orderId);
		}else{
			order = getUserView().getCart().getOrder();
			ServiceFactory.getService(OrderService.class).saveOrder(order, OrderStatus.ONSHOPPING.toString());
		}
		
		return "shoppingCart_payment";
	}

	@RequestMapping("/shoppingCart_payment2")
	public String shoppingCartPayment(Model model,
			@RequestParam("id") String orderId) {

		Order order = ServiceFactory.getService(OrderService.class)
				.getOrderById(orderId);
		if (null != order
				&& OrderStatus.PENDING.getValue().equals(order.getStatus())) {
			model.addAttribute(CURRENT_ORDER, order);
		} else {
			model.addAttribute(CURRENT_ORDER, order);
		}

		return "paypal";
	}

	@RequestMapping(value = "/shoppingCart_payment_2_pay", method = RequestMethod.POST)
	public String shoppingCartPayment2Pay(Model model,
			@RequestParam("payType") String paymentMehod, HttpServletRequest request) throws UnsupportedEncodingException {
		
		String payType = paymentMehod.split("\\^")[0];
		String cardType = null;
		
		if(paymentMehod.split("^").length > 1 ){
			cardType = paymentMehod.split("\\^")[1];
		}
		
		Order order = null;
		String orderId = request.getParameter("id");
		if(StringUtils.isNotBlank(orderId)){
			order = ServiceFactory.getService(OrderService.class).getOrderById(orderId);
		}
		
		boolean isRePay = true;
		
		if(null == order){
			isRePay = false;
			order = getUserView().getCart().getOrder();
			getUserView().getCart().setOrder(new Order());
		}
		
		if(null != cardType){
			order.setOrderType(payType+"^"+cardType);
		}else{
			order.setOrderType(payType);
		}

		order = ServiceFactory.getService(OrderService.class).saveOrder(order,
				OrderStatus.PENDING.toString());

		model.addAttribute(CURRENT_ORDER, order);

		if (null != order && !order.getItems().isEmpty()) {

			final Order o = order;

			final Map<String, Object> root = new HashMap<String, Object>();

			final SiteView siteView = getSiteView();

			root.put("order", order);

			float currencyRate = 1;

			if (!DEFAULT_CURRENCY.equals(o.getCurrency())) {
				currencyRate = getSiteView().getCurrencies().get(
						o.getCurrency());
			}

			root.put("currencyRate", currencyRate);
			root.put(SITE_VIEW, siteView);
			if(!isRePay){
				new Thread() {
					public void run() {
						try {
							EmailTools
									.sendMail(
											"paid",
											"Order Received, Awaiting Payment Confirmation",
											root, o.getUser().getEmail());
						} catch (Exception e) {
							logger.debug(e);
						}
					};
				}.start();
			}else if ("Globebill".equals(payType)){
				
				globebillPay(order,model,request);
				
				return "Globebill";
			}

			return "paypal";
		}

		return "shoppingCart_payment";
	}

	private void globebillPay(Order order, Model model, HttpServletRequest request) {
		String merNo = "10309";
		String gatewayNo = "10309001";
		String signKeyNo = "2066v826";
		String orderNumber = order.getName();
	    String amount = new NumberFormat("##0.##").getNumberFormat().format(getSiteView().getCurrencies().get(order.getCurrency())*(order.getTotalPrice() + order.getDePrice() - order.getCouponCutOff()));

	    String returnUrl = request.getScheme()+"://" + request.getServerName() + "/uc/globebillPayRs";
	    
	    
	    /**
	     *
	     * 	merNo + gatewayNo +
			orderNo + orderCurrency+ orderAmount +
			eturnUrl+ signkey

	     */
	    StringBuffer signInfo  = new StringBuffer(merNo).append(gatewayNo)
	    						.append(orderNumber).append(order.getCurrency()).append(amount).append(returnUrl)
	    						.append(signKeyNo);
	    
	   logger.info("signInfo:" + signInfo);
	  
	    String signString = new EncryptUtil().getSHA256Encrypt(signInfo.toString());
	    
	    String firstName = "";
	    String lastName = "N/A";
	    
	   // String username = order.getCustomerName();
	    
	    String cc = "";
	    
	   long ccId = Long.valueOf(order.getBillingAddress().getCountry());
	    
	   if(ccId >0){
		   cc = getSiteView().getCountryMap().get(String.valueOf(ccId)).getAbbrCode();
	   }

	   model.addAttribute("signInfo", signString);
	    model.addAttribute("amount", amount);
	    model.addAttribute("returnUrl", returnUrl);
	    model.addAttribute("firstName", order.getShippingAddress().getFirstName());
	    model.addAttribute("lastName", order.getShippingAddress().getLastName());
	    model.addAttribute("cc", cc);
	    
	}

	@RequestMapping(value = "/userProfile", params = { "action=updateAccount" }, method = RequestMethod.POST)
	public String updateAccount(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String firstName = request.getParameter(C_USER_FIRST_NAME);
		String lastName = request.getParameter(C_USER_LAST_NAME);

		if (StringUtils.isBlank(firstName) || firstName.length() > 50) {
			getUserView().getErr().put(FIRST_NAME_ERR, "invalid first name");
		}

		if (StringUtils.isBlank(lastName) || lastName.length() > 50) {
			getUserView().getErr().put(LAST_NAME_ERR, "invalid last name");
		}

		if (getUserView().getErr().isEmpty()) {
			User user = getUserView().getLoginUser();

			user.setFirstName(firstName);
			user.setLastName(lastName);

			ServiceFactory.getService(UserService.class).saveUser(user);

			getUserView().getMsg().put(UPDATE_ACC_SUC, "Update successfully");

		}

		return "userProfile";
	}

	@RequestMapping(value = "/userProfile", params = { "action=updateAddress1" }, method = RequestMethod.POST)
	public String updateAddress1(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		
		//model.addAttribute(PRIMARY_ADDRESS, address);

		return "userProfile";
	}

	@RequestMapping(value = "/userProfile", params = { "action=updateAddress2" }, method = RequestMethod.POST)
	public String updateAddress2(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		return "userProfile";
	}

	@RequestMapping("/myOrder")
	public String myOrder(Model model, HttpServletRequest request) {

		String strPage = request.getParameter(PAGINATION);

		int start = 1;

		try {
			start = Integer.parseInt(strPage);
		} catch (Exception e) {
		}

		if (start < 1) {
			start = 1;
		}

		List<Order> orders = ServiceFactory.getService(OrderService.class)
				.getOrdersByUserId(getUserView().getLoginUser().getId(), start);

		int count = ServiceFactory.getService(OrderService.class)
				.countOrdersByUserId(getUserView().getLoginUser().getId());

		model.addAttribute(USER_ORDERS, orders);
		model.addAttribute(USER_ORDERS_COUNT, count);
		model.addAttribute(PAGINATION, start);

		return "userOrder";
	}

	@RequestMapping("/orderDetails")
	public String orderDetails(Model model, HttpServletRequest request,
			@RequestParam("id") String orderId) throws UnsupportedEncodingException {

		Order order = ServiceFactory.getService(OrderService.class)
				.getOrderById(orderId);

		model.addAttribute(CURRENT_ORDER, order);
		if(request.getParameter("tradeInfo")!=null){
			model.addAttribute("tradeInfo", URLDecoder.decode(request.getParameter("tradeInfo"),"UTF-8"));
		}

		return "orderDetails";
	}

	@RequestMapping(value = "/my-measurements", method = RequestMethod.GET)
	public String measurements(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		// String cpid = (String)
		// request.getSession().getAttribute(CURRENT_PRODUCT_ID);

		String continueShopping = (String) request.getSession().getAttribute(
				"continueShopping");

		if (StringUtils.isNotBlank(continueShopping)) {
			model.addAttribute("continueShopping", "true");
		}

		if (StringUtils.isNotBlank(request.getParameter("editMode"))) {
			model.addAttribute("editMode", true);
		}

		SuitMeasurement measurement = getUserView().getLoginUser()
				.getSuitMeasurement();

		model.addAttribute(SUIT_MEASUREMENT, measurement);

		if (getUserView().getLoginUser().isSuitMeasurementComplete()) {
			model.addAttribute(MEASUREMENT_MSG,
					"You have filled the suit measurement");
		} else {
			model.addAttribute(MEASUREMENT_MSG,
					"You have not filled the suit measurement");
		}

		return "measurements";
	}

	@RequestMapping(value = "/my-measurements", method = RequestMethod.POST)
	public String measurementsPost(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		SuitMeasurement measurement = retrieveSuitMeasurement(request);

		model.addAttribute(SUIT_MEASUREMENT, measurement);

		getUserView().getLoginUser().setMySuitMeasurement(measurement);
		getUserView().getLoginUser().setSuitMeasurementComplete(true);

		ServiceFactory.getService(UserService.class).saveUser(
				getUserView().getLoginUser());

		model.addAttribute(MEASUREMENT_MSG, "Update measurement successfully");

		return "measurements";
	}

	@RequestMapping("/feedback")
	public String postMessage(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		String optType = request.getParameter("optType");
		if ("compose".equals(optType)) {
			return "composeMsg";
		} else if ("viewThread".equals(optType)) {
			retrieveMessageThread(model, request);
			model.addAttribute("currentUserId", getUserView().getLoginUser()
					.getId());
			return "feedback";
		} else if ("postMsg".equals(optType)) {
			Message message = populateMessage(request);
			String lastMsgId = request.getParameter("lastMsgId");
			if (lastMsgId != null) {
				long parentId = Long.valueOf(lastMsgId);
				Message parent = ServiceFactory
						.getService(MessageService.class).fetchById(
								Long.valueOf(parentId));
				ServiceFactory.getService(MessageService.class).replyMessage(
						parent, message);
			} else {
				ServiceFactory.getService(MessageService.class).save(message);
			}
			populateMessages(model);
			return "messageList";
		} else {
			populateMessages(model);
			return "messageList";
		}
	}

	private void retrieveMessageThread(Model model, HttpServletRequest request) {
		String msgId = request.getParameter("messageId");
		Message message = ServiceFactory.getService(MessageService.class)
				.fetchById(Long.valueOf(msgId));
		if (message != null) {
			List<Message> messages = new ArrayList<Message>();
			messages.add(message);
			Message msg = message;
			do {
				if (msg.getReplyBy() != null) {
					messages.add(msg.getReplyBy());
					msg = msg.getReplyBy();
				}
			} while (msg != null && msg.getReplyBy() != null);
			Collections.reverse(messages);
			model.addAttribute("messageThread", messages);
		}
	}

	private Message populateMessage(HttpServletRequest request) {
		String content = request.getParameter("newMessage");
		String title = request.getParameter("messageTitle");
		Message msg = new Message();
		Date date = new Date();
		String dateStr = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
				DateFormat.SHORT).format(date);
		msg.setContent(content);
		if (title != null) {
			msg.setName(title);
		} else {
			msg.setName(dateStr.trim());
		}
		msg.setCreateDate(date);
		msg.setReplied(false);
		msg.setUser(getUserView().getLoginUser());
		return msg;
	}

	private void populateMessages(Model model) {
		model.addAttribute("messages",
				ServiceFactory.getService(MessageService.class)
						.getMessagesByUser(getUserView().getLoginUser()));
	}
}
