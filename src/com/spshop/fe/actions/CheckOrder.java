package com.spshop.fe.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.Coupon;
import com.spshop.model.Order;
import com.spshop.model.enums.OrderStatus;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CouponService;
import com.spshop.service.intf.OrderService;

public class CheckOrder extends BaseAction {
	
	private static final String ACCOUNT = "pay@honeybuy.com";
	
	private static Logger logger = Logger.getLogger(CheckOrder.class);
	
	@SuppressWarnings("rawtypes")
	@Override
	public ActionForward processer(ActionMapping mapping, PageFormBean page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			logger.info("Accept request");
			
			List<String> errorStrings = new ArrayList<String>();
			List<String> msgs = new ArrayList<String>();
			
			Enumeration en = request.getParameterNames();
			String str = "cmd=_notify-validate";
			logger.info("################Accept######################");
			while (en.hasMoreElements()) {
				String paramName = (String) en.nextElement();
				String paramValue = request.getParameter(paramName);
				str = str + "&" + paramName + "="
						+ URLEncoder.encode(paramValue, "iso-8859-1");
				logger.info(paramName+": " + request.getParameter(paramName));
			}
			logger.info("######################################");
			logger.info("str: " + str);
			logger.info("######################################");
//			URL u = new URL("http://www.sandbox.paypal.com/c2/cgi-bin/webscr");
			 URL u = new URL("http://www.paypal.com/cgi-bin/webscr");
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);

			uc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			pw.println(str);
			pw.close();
			// Ã¦Å½Â¥Ã¥ï¿½â€” PayPal Ã¥Â¯Â¹ IPN Ã¥â€ºÅ¾Ã¥ï¿½â€˜Ã§Å¡â€žÃ¥â€ºÅ¾Ã¥Â¤ï¿½Ã¤Â¿Â¡Ã¦ï¿½Â¯
			BufferedReader in = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));
			String res = in.readLine();
			in.close();
			// Ã¥Â°â€  POST Ã¤Â¿Â¡Ã¦ï¿½Â¯Ã¥Ë†â€ Ã©â€¦ï¿½Ã§Â»â„¢Ã¦Å“Â¬Ã¥Å“Â°Ã¥ï¿½ËœÃ©â€¡ï¿½Ã¯Â¼Å’Ã¥ï¿½Â¯Ã¤Â»Â¥Ã¦Â Â¹Ã¦ï¿½Â®Ã¦â€šÂ¨Ã§Å¡â€žÃ©Å“â‚¬Ã¨Â¦ï¿½Ã¦Â·Â»Ã¥Å Â 
			// Ã¨Â¯Â¥Ã¤Â»ËœÃ¦Â¬Â¾Ã¦ËœÅ½Ã§Â»â€ Ã¦â€°â‚¬Ã¦Å“â€°Ã¥ï¿½ËœÃ©â€¡ï¿½Ã¥ï¿½Â¯Ã¥ï¿½â€šÃ¨â‚¬Æ’Ã¯Â¼Å¡
			// https://www.paypal.com/IntegrationCenter/ic_ipn-pdt-variable-reference.html
			String itemName = request.getParameter("item_name");
			String quantity = request.getParameter("quantity");
			String paymentStatus = request.getParameter("payment_status");
			String paymentAmount = request.getParameter("mc_gross");
			String paymentCurrency = request.getParameter("mc_currency");
			String txnId = request.getParameter("txn_id");
			String receiverEmail = request.getParameter("receiver_email");
			
			String payerEmail = request.getParameter("payer_email");
			String address_city = request.getParameter("address_city");
			String contact_phone = request.getParameter("contact_phone");
			String address_country = request.getParameter("address_country");
			String address_street = request.getParameter("address_street");
			String address_zip = request.getParameter("address_zip");
			String first_name = request.getParameter("first_name");
			String last_name = request.getParameter("last_name");
			Enumeration els = request.getParameterNames();
			// Ã¢â‚¬Â¦
			// Ã¨Å½Â·Ã¥ï¿½â€“ PayPal Ã¥Â¯Â¹Ã¥â€ºÅ¾Ã¥ï¿½â€˜Ã¤Â¿Â¡Ã¦ï¿½Â¯Ã§Å¡â€žÃ¥â€ºÅ¾Ã¥Â¤ï¿½Ã¤Â¿Â¡Ã¦ï¿½Â¯Ã¯Â¼Å’Ã¥Ë†Â¤Ã¦â€“Â­Ã¥Ë†Å¡Ã¦â€°ï¿½Ã§Å¡â€žÃ©â‚¬Å¡Ã§Å¸Â¥Ã¦ËœÂ¯Ã¥ï¿½Â¦Ã¤Â¸Âº PayPal Ã¥ï¿½â€˜Ã¥â€¡ÂºÃ§Å¡â€ž
			if ("VERIFIED".equals(res)) {
				// Ã¦Â£â‚¬Ã¦Å¸Â¥Ã¤Â»ËœÃ¦Â¬Â¾Ã§Å Â¶Ã¦â‚¬ï¿½
				// Ã¦Â£â‚¬Ã¦Å¸Â¥ txn_id Ã¦ËœÂ¯Ã¥ï¿½Â¦Ã¥Â·Â²Ã§Â»ï¿½Ã¥Â¤â€žÃ§ï¿½â€ Ã¨Â¿â€¡
				// Ã¦Â£â‚¬Ã¦Å¸Â¥ receiver_email Ã¦ËœÂ¯Ã¥ï¿½Â¦Ã¦ËœÂ¯Ã¦â€šÂ¨Ã§Å¡â€ž PayPal Ã¨Â´Â¦Ã¦Ë†Â·Ã¤Â¸Â­Ã§Å¡â€ž EMAIL Ã¥Å“Â°Ã¥ï¿½â‚¬
				// Ã¦Â£â‚¬Ã¦Å¸Â¥Ã¤Â»ËœÃ¦Â¬Â¾Ã©â€¡â€˜Ã©Â¢ï¿½Ã¥â€™Å’Ã¨Â´Â§Ã¥Â¸ï¿½Ã¥ï¿½â€¢Ã¤Â½ï¿½Ã¦ËœÂ¯Ã¥ï¿½Â¦Ã¦Â­Â£Ã§Â¡Â®
				// Ã¥Â¤â€žÃ§ï¿½â€ Ã¥â€¦Â¶Ã¤Â»â€“Ã¦â€¢Â°Ã¦ï¿½Â®Ã¯Â¼Å’Ã¥Å’â€¦Ã¦â€¹Â¬Ã¥â€ â„¢Ã¦â€¢Â°Ã¦ï¿½Â®Ã¥Âºâ€œ
				Order order = ServiceFactory.getService(OrderService.class).getOrderById(itemName);
				logger.info(">>>>>>>>>>>>>>>>>>>VERIFIED>>>>>>>>>>>>>>>>>>>>>>");
				if(null!=order){
					order.setCustomerEmail(payerEmail);
					logger.info(">>>>>>>>>>>>>>>>>>>paymentAmount:"+paymentAmount+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>paymentCurrency:"+paymentCurrency+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>receiverEmail:"+receiverEmail+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>itemNumber:"+quantity+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>(order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff()):"+(order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff())+">>>>>>>>>>>>>>>>>>>>>>");
					
					logger.info(">>>>>>>>>>>>>>>>>>>receiverEmail.equalsIgnoreCase(ACCOUNT):"+receiverEmail.equalsIgnoreCase(ACCOUNT)+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>itemNumber.equals('1'):"+quantity.equals("1")+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>order.getCurrency().equals(paymentCurrency):"+order.getCurrency().equals(paymentCurrency)+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>receiverEmail.equalsIgnoreCase(ACCOUNT):"+receiverEmail.equalsIgnoreCase(ACCOUNT)+">>>>>>>>>>>>>>>>>>>>>>");
					
					
					float rate = getCurrencies(request).get(order.getCurrency());
					logger.info(">>>>>>>>>>>>>>>>>>>((order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff())*rate- 1) <= Float.parseFloat(paymentAmount):"+(((order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff())*rate- 1)  <= Float.parseFloat(paymentAmount))+">>>>>>>>>>>>>>>>>>>>>>");
					if(((order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff())*rate- 1) <= Float.parseFloat(paymentAmount)
							&&order.getCurrency().equals(paymentCurrency)
							&&receiverEmail.equalsIgnoreCase(ACCOUNT)
							&&quantity.equals("1")){
						//order.setStatus(OrderStatus.PAID.getValue());
						Map<String,Object> root = new HashMap<String,Object>(); 
						root.put("order", order);
						root.put("currencyRate", getCurrencies(request).get(order.getCurrency()));
						//EmailTools.sendMail("paid2", "Order Received and Payment Confirmation", root,order.getCustomerEmail());
						try{
							Coupon coupon = ServiceFactory.getService(CouponService.class).getCouponByCode(order.getCouponCode());
							coupon.setUsedCount(coupon.getUsedCount()+1);
							ServiceFactory.getService(CouponService.class).saveCoupon(coupon);
						}catch(Exception e){
							logger.info(e.getMessage(),e);
						}
					}else{
						logger.info(">>>>>>>>>>>>>>>>>>>NOT enough mony>>>>>>>>>>>>>>>>>>>>>>");
					}
					logger.info("order.getAddressType():"+order.getAddressType());
					/*if("PA".equals(order.getAddressType())){
						order.setCity(address_city);
						order.setCustomerCountry(address_country);
						order.setCustomerName(last_name+" " + first_name);
						order.setCustomerZipcode(address_zip);
						order.setCustomerTelephone(contact_phone);
						order.setCustomerAddress(address_street);
						logger.info("Pay>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					}
					*/
					ServiceFactory.getService(OrderService.class).saveOrder(order, OrderStatus.PAID.getValue());
					
				}
				
				
			} else if ("INVALID".equals(res)) {
				// Ã©ï¿½Å¾Ã¦Â³â€¢Ã¤Â¿Â¡Ã¦ï¿½Â¯Ã¯Â¼Å’Ã¥ï¿½Â¯Ã¤Â»Â¥Ã¥Â°â€ Ã¦Â­Â¤Ã¨Â®Â°Ã¥Â½â€¢Ã¥Ë†Â°Ã¦â€šÂ¨Ã§Å¡â€žÃ¦â€”Â¥Ã¥Â¿â€”Ã¦â€“â€¡Ã¤Â»Â¶Ã¤Â¸Â­Ã¤Â»Â¥Ã¥Â¤â€¡Ã¨Â°Æ’Ã¦Å¸Â¥
				logger.info("##############INVALID########################");
			} else {
				// Ã¥Â¤â€žÃ§ï¿½â€ Ã¥â€¦Â¶Ã¤Â»â€“Ã©â€�â„¢Ã¨Â¯Â¯
				logger.info("##############ORTHER########################");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			//e.printStackTrace();
		}

		 return null;
	}
	
	public static void main(String[] args) {
		String cmd = "cmd=_notify-validate&last_name=User&test_ipn=1&address_name=Test+User&txn_type=web_accept&receiver_email=S1%40HP.COM&residence_country=US&address_city=San+Jose&payment_gross=184.00&payment_date=21%3A58%3A16+Dec+19%2C+2011+PST&address_zip=95131&payment_status=Pending&address_street=1+Main+St&first_name=Test&payer_email=buyer1_1317304411_per%40hp.com&protection_eligibility=Ineligible&payer_id=ANJ33V369TW8U&verify_sign=AFcWxV21C7fd0v3bYYYRCpSSRl31AALeaE7Cd4suR39f7Jb1gzRUXKsK&payment_type=instant&business=S1%40HP.COM&address_country_code=US&mc_fee=7.48&address_status=confirmed&transaction_subject=Order268549SCOBE141072&quantity=1&notify_version=3.4&mc_currency=USD&custom=&address_state=CA&payment_fee=7.48&handling_amount=0.00&payer_status=verified&shipping=0.00&item_name=Order268549SCOBE141072&tax=0.00&charset=gb2312&pending_reason=paymentreview&item_number=&ipn_track_id=DGhRld19cLHZisXmIg2UDQ&mc_gross=184.00&txn_id=0UB78234N8562484T&receiver_id=3WJ2MJ36TT9T4&address_country=United+States";
		
		try {
			URL u = new URL("https://www.sandbox.paypal.com/c2/cgi-bin/webscr");
			// URL u = new URL("http://www.paypal.com/cgi-bin/webscr");
			URLConnection uc = u.openConnection();
			uc.setDoOutput(true);

			uc.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			PrintWriter pw = new PrintWriter(uc.getOutputStream());
			pw.println(cmd);
			pw.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getMessage(), e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.error(e.getMessage(), e);
		}
	}
	
}