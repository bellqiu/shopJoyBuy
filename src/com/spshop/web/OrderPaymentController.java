package com.spshop.web;

import static com.spshop.utils.Constants.DEFAULT_CURRENCY;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.NumberFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spshop.model.Country;
import com.spshop.model.Coupon;
import com.spshop.model.Order;
import com.spshop.model.OrderItem;
import com.spshop.model.cart.ShoppingCart;
import com.spshop.model.enums.OrderStatus;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CountryService;
import com.spshop.service.intf.CouponService;
import com.spshop.service.intf.OrderService;
import com.spshop.utils.Constants;
import com.spshop.utils.EmailTools;
import com.spshop.utils.EncryptUtil;
import com.spshop.utils.Utils;
import com.spshop.web.view.OrderItemSummaryJsonView;
import com.spshop.web.view.OrderShippingAddressJsonView;
import com.spshop.web.view.OrderSummaryJsonView;

@Controller
@RequestMapping("/uc")
public class OrderPaymentController extends BaseController{
	
	Logger logger = Logger.getLogger(OrderPaymentController.class);
	
	@RequestMapping("/applyCoupon")
	@ResponseBody
	public OrderSummaryJsonView applyCoupon(Model model, @RequestParam("orderSN")String orderSN, @RequestParam("couponcode")String couponCode) {
		
		OrderSummaryJsonView summaryView = new OrderSummaryJsonView();
		OrderItemSummaryJsonView itemSummaryView = new OrderItemSummaryJsonView();
		summaryView.setOrderItemView(itemSummaryView);
		
		Order order = ServiceFactory.getService(OrderService.class).applyCoupon(orderSN, getUserView().getLoginUser().getId(), couponCode);
		
		Coupon coupon = ServiceFactory.getService(CouponService.class).getCouponByCode(couponCode);
		
		if(null != order){
			float rate = getSiteView().getCurrencies().get(order.getCurrency());
			summaryView.setSuccess(true);
			summaryView.setCoupon(Utils.toNumber( rate * order.getCouponCutOff()));
			summaryView.setGrandTotal(Utils.toNumber((order.getTotalPrice() + order.getDePrice() -order.getCouponCutOff())*rate));
			summaryView.setDePrice(Utils.toNumber( rate * order.getDePrice()));
			summaryView.setSubTotal(Utils.toNumber((order.getTotalPrice())*rate));
			if(null == coupon){
				summaryView.setSuccess(false);
				summaryView.setMsg("Invalid Coupon");
			}else{
				if(coupon.getMinexpend() > order.getTotalPrice()){
					summaryView.setSuccess(false);
					summaryView.setMsg("This Coupon need order total price more than " + order.getCurrency() + " " + Utils.toNumber(coupon.getMinexpend()*rate));
				}
			}
		}
		syncShoppingCartAndProcessingOrder(order);
		return summaryView;
		
	}
	
	

	@ResponseBody
	@RequestMapping("/updateOrder")
	public OrderSummaryJsonView updateOrder(@RequestParam("orderSN")String orderSN, @RequestParam("itemId")long itemId, @RequestParam("amount")int amount){
		OrderSummaryJsonView summaryView = new OrderSummaryJsonView();
		OrderItemSummaryJsonView itemSummaryView = new OrderItemSummaryJsonView();
		summaryView.setOrderItemView(itemSummaryView);
		
		OrderShippingAddressJsonView shippingAddressJsonView = new OrderShippingAddressJsonView();
		summaryView.setShippingAddressView(shippingAddressJsonView);
		
		Order order = ServiceFactory.getService(OrderService.class).updateOrderItem(orderSN, getUserView().getLoginUser().getId(), itemId, amount);
		
		if(null != order){
			float rate = getSiteView().getCurrencies().get(order.getCurrency());
			summaryView.setSuccess(true);
			summaryView.setCoupon(Utils.toNumber( rate * order.getCouponCutOff()));
			summaryView.setGrandTotal(Utils.toNumber((order.getTotalPrice() + order.getDePrice() -order.getCouponCutOff())*rate));
			summaryView.setDePrice(Utils.toNumber( rate * order.getDePrice()));
			summaryView.setSubTotal(Utils.toNumber((order.getTotalPrice())*rate));
			if(null != order.getShippingAddress()){
				Country country = ServiceFactory.getService(CountryService.class).getCountryById(Long.valueOf(order.getShippingAddress().getCountry()));
				
				if(null != country && order.getTotalPrice() < country.getFreeAdDePrice()){
					shippingAddressJsonView.setExpeditedPrice(Utils.toNumber(country.getAdDePrice()*rate));
					if(order.getTotalPrice() < country.getFreeDePrice()){
						shippingAddressJsonView.setStandardPrice(Utils.toNumber(country.getDePrice()*rate));
					}
					
					if(Constants.SHIPPING_EXPEDITED.equals(order.getShippingMethod())){
						shippingAddressJsonView.setExpeditedChecked(true);
					}else if(Constants.SHIPPING_STANDARD.equals(Constants.SHIPPING_STANDARD)){
						shippingAddressJsonView.setStandardChecked(true);
					}
				}
			}
			if(null!=order.getItems()){
				for (OrderItem oItem : order.getItems()) {
					if(oItem.getId() == itemId){
						itemSummaryView.setFinalPrice(Utils.toNumber(oItem.getFinalPrice()*oItem.getQuantity()*rate));
						itemSummaryView.setQuantity(String.valueOf(oItem.getQuantity()));
					}
				}
			}
		}
		syncShoppingCartAndProcessingOrder(order);
		return summaryView;
	}
	
	@ResponseBody
	@RequestMapping("/applyShippingAddress")
	public OrderSummaryJsonView applyShippingAddress(@RequestParam("orderSN")String orderSN, @RequestParam("addressId")long addressId){
		
		OrderSummaryJsonView summaryView = new OrderSummaryJsonView();
		OrderShippingAddressJsonView shippingAddressJsonView = new OrderShippingAddressJsonView();
		summaryView.setShippingAddressView(shippingAddressJsonView);
		
		Order order = ServiceFactory.getService(OrderService.class).applyShippingAddress(orderSN, getUserView().getLoginUser().getId(), addressId);
		
		if(null != order){
			float rate = getSiteView().getCurrencies().get(order.getCurrency());
			Country country = ServiceFactory.getService(CountryService.class).getCountryById(Long.valueOf(order.getShippingAddress().getCountry()));
			
			summaryView.setSuccess(true);
			summaryView.setCoupon(Utils.toNumber( rate * order.getCouponCutOff()));
			summaryView.setGrandTotal(Utils.toNumber((order.getTotalPrice() + order.getDePrice() -order.getCouponCutOff())*rate));
			summaryView.setDePrice(Utils.toNumber( rate * order.getDePrice()));
			summaryView.setSubTotal(Utils.toNumber((order.getTotalPrice())*rate));
			
			if(null != country && order.getTotalPrice() < country.getFreeAdDePrice()){
				shippingAddressJsonView.setExpeditedPrice(Utils.toNumber(country.getAdDePrice()*rate));
				if(order.getTotalPrice() < country.getFreeDePrice()){
					shippingAddressJsonView.setStandardPrice(Utils.toNumber(country.getDePrice()*rate));
				}
				
				if(Constants.SHIPPING_EXPEDITED.equals(order.getShippingMethod())){
					shippingAddressJsonView.setExpeditedChecked(true);
				}else if(Constants.SHIPPING_STANDARD.equals(Constants.SHIPPING_STANDARD)){
					shippingAddressJsonView.setStandardChecked(true);
				}
			}
			
		}
		syncShoppingCartAndProcessingOrder(order);
		return summaryView;
	}
	
	
	@RequestMapping("/applyShippingMethod")
	@ResponseBody
	public OrderSummaryJsonView applyShippingMethod(Model model, @RequestParam("orderSN")String orderSN, @RequestParam("shippingMethod")String shippingMethod) {
		
		OrderSummaryJsonView summaryView = new OrderSummaryJsonView();
		OrderItemSummaryJsonView itemSummaryView = new OrderItemSummaryJsonView();
		summaryView.setOrderItemView(itemSummaryView);
		
		Order order = ServiceFactory.getService(OrderService.class).applyShippingMethod(orderSN, getUserView().getLoginUser().getId(), shippingMethod);
		
		
		if(null != order){
			float rate = getSiteView().getCurrencies().get(order.getCurrency());
			summaryView.setSuccess(true);
			summaryView.setCoupon(Utils.toNumber( rate * order.getCouponCutOff()));
			summaryView.setGrandTotal(Utils.toNumber((order.getTotalPrice() + order.getDePrice() -order.getCouponCutOff())*rate));
			summaryView.setDePrice(Utils.toNumber( rate * order.getDePrice()));
			summaryView.setSubTotal(Utils.toNumber((order.getTotalPrice())*rate));
		}
		
		syncShoppingCartAndProcessingOrder(order);
		return summaryView;
		
	}
	
	@RequestMapping("/checkout")
	public String checkout( Model model, HttpServletRequest request, @RequestParam("orderSN")String orderSN,  @RequestParam("payment")String payment){
		
		Order order = ServiceFactory.getService(OrderService.class).getCartOrPendingOrderById(orderSN, getUserView().getLoginUser().getId());
		model.addAttribute(Constants.PROCESSING_ORDER, order);
		
		if(null != order){
			if(null == order.getShippingAddress()){
				model.addAttribute("errorMsg", "Please fill your shipping address");
			}else if(StringUtils.isBlank(order.getShippingMethod())){
				model.addAttribute("errorMsg", "Please select a shipping method");
			}else if("Globebill".equals(payment)) {
				order.setOrderType("Globebill");
				
				globebillPay(order, model, request);
				return "billingAddress";
				
			}else if("paypal".equals(payment)) {
				order.setOrderType("Paypal");
				checkOutOrder(model, order);
				return "paypal";
			}else if("WesternUnion".equals(payment)) {
				order.setOrderType("WesternUnion");
				checkOutOrder(model, order);
				return "WesternUnion";
			}else{
				model.addAttribute("errorMsg", "Please select a payment method");
			}
			
		}
		
		return "forward:/uc/shoppingCart_address?id="+orderSN;
	}
	
	@RequestMapping("/checkout_credit_card")
	public String checkoutCreditCard( Model model, HttpServletRequest request, @RequestParam("orderSN")String orderSN,  @RequestParam("add")long addressId){
		
		Order order = ServiceFactory.getService(OrderService.class).applyBillingAddress(orderSN, getUserView().getLoginUser().getId(), addressId);
		
		if(null != order){
			if(null == order.getBillingAddress()){
				model.addAttribute("errorMsg", "Please fill you billing address");
			}else {
				
				order = checkOutOrder(model, order);
			
				globebillPay(order, model, request);
				
				return "Globebill";
			}
			
		}
		
		return "forward:/uc/checkout?orderSN="+orderSN + "&payment="+order.getOrderType();
	}



	private Order checkOutOrder(Model model, Order order) {
		final Map<String, Object> root = new HashMap<String, Object>();
		model.addAttribute(Constants.PROCESSING_ORDER, order);
		order = ServiceFactory.getService(OrderService.class).saveOrder(order, OrderStatus.PENDING.toString());
		getUserView().setCart(new ShoppingCart(new Order()));
		
		final Order o = order;
		root.put("order", order);
		float currencyRate = 1;
		if (!DEFAULT_CURRENCY.equals(order.getCurrency())) {
			currencyRate = getSiteView().getCurrencies().get(
					order.getCurrency());
		}
		root.put("currencyRate", currencyRate);
		
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
		return order;
	}
	
	



	private void globebillPay(Order order, Model model, HttpServletRequest request) {
		
		String merNo = "10309";
		String gatewayNo = "10309001";
		String signKeyNo = "2066v826";
		String orderNumber = order.getName();//订单号
		//订单金额, 两位小数
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
	    
	    String firstName = order.getShippingAddress().getFirstName();
	    String lastName = order.getShippingAddress().getLastName();
	    
	   // String username = order.getCustomerName();
	    
	    String cc = "";
	    
	   long ccId = Long.valueOf(order.getShippingAddress().getCountry());
	    
	   if(ccId >0){
		   cc = getSiteView().getCountryMap().get(String.valueOf(ccId)).getAbbrCode();
	   }
	   
/*	   if(username != null){
		   username = username.trim();
		   int last = username.lastIndexOf(' ');
		   if(last > 0){
			   firstName = username.substring(0, last);
			   lastName = username.substring(last);
		   }else{
			   firstName = username;
		   }
	   }*/
	    
	    model.addAttribute("signInfo", signString);
	    model.addAttribute("amount", amount);
	    model.addAttribute("returnUrl", returnUrl);
	    model.addAttribute("firstName", firstName);
	    model.addAttribute("lastName", lastName);
	    model.addAttribute("cc", cc);
	    
	}
	
	@RequestMapping("/applyMsg")
	@ResponseBody
	public String applyMsg(Model model, @RequestParam("orderSN")String orderSN, @RequestParam("msg")String msg) {
		
		
		Order order = ServiceFactory.getService(OrderService.class).getCartOrPendingOrderById(orderSN, getUserView().getLoginUser().getId());
		if(null != order){
			order.setCustomerMsg(msg);
			ServiceFactory.getService(OrderService.class).save(order);
		}
		
		return null;
		
	}
}
