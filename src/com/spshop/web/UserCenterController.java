package com.spshop.web;

import static com.spshop.utils.Constants.ADDRESS1;
import static com.spshop.utils.Constants.ADDRESS1_ERR;
import static com.spshop.utils.Constants.ADDRESS2;
import static com.spshop.utils.Constants.ADDRESS2_ERR;
import static com.spshop.utils.Constants.ADD_TYPE;
import static com.spshop.utils.Constants.ADD_TYPE_B;
import static com.spshop.utils.Constants.ADD_TYPE_P;
import static com.spshop.utils.Constants.BILLING_ADDRESS;
import static com.spshop.utils.Constants.BILLING_SAME_AS_PRIMARY;
import static com.spshop.utils.Constants.CITY;
import static com.spshop.utils.Constants.CITY_ERR;
import static com.spshop.utils.Constants.COUNTRY;
import static com.spshop.utils.Constants.COUNTRY_ERR;
import static com.spshop.utils.Constants.CURRENT_ORDER;
import static com.spshop.utils.Constants.CURRENT_PRODUCT;
import static com.spshop.utils.Constants.C_USER_FIRST_NAME;
import static com.spshop.utils.Constants.C_USER_LAST_NAME;
import static com.spshop.utils.Constants.DEFAULT_CURRENCY;
import static com.spshop.utils.Constants.EMPTY_ORDER;
import static com.spshop.utils.Constants.FIRST_NAME_ERR;
import static com.spshop.utils.Constants.LAST_NAME_ERR;
import static com.spshop.utils.Constants.MEASUREMENT_MSG;
import static com.spshop.utils.Constants.PAGINATION;
import static com.spshop.utils.Constants.POASTAL_CODE;
import static com.spshop.utils.Constants.POSTAL_CODE_ERR;
import static com.spshop.utils.Constants.PRIMARY_ADDRESS;
import static com.spshop.utils.Constants.REG_PWD_RE_ERR;
import static com.spshop.utils.Constants.REG_USER_NAME_SUC;
import static com.spshop.utils.Constants.SHIPPING_EXPEDITED;
import static com.spshop.utils.Constants.SHIPPING_METHOD;
import static com.spshop.utils.Constants.SHIPPING_STANDARD;
import static com.spshop.utils.Constants.SITE_VIEW;
import static com.spshop.utils.Constants.STATE_PROVINCE;
import static com.spshop.utils.Constants.STATE_PROVINCE_ERR;
import static com.spshop.utils.Constants.SUIT_MEASUREMENT;
import static com.spshop.utils.Constants.TEL_NUM;
import static com.spshop.utils.Constants.TEL_NUM_ERR;
import static com.spshop.utils.Constants.TXT_NEW_PWD1;
import static com.spshop.utils.Constants.TXT_NEW_PWD2;
import static com.spshop.utils.Constants.TXT_PWD;
import static com.spshop.utils.Constants.UPDATE_ACC_SUC;
import static com.spshop.utils.Constants.UPDATE_ADDRESS_1_SUC;
import static com.spshop.utils.Constants.UPDATE_ADDRESS_2_SUC;
import static com.spshop.utils.Constants.USERNAME;
import static com.spshop.utils.Constants.USERNAME_ERR;
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

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipaySubmit;
import com.spshop.model.Address;
import com.spshop.model.Country;
import com.spshop.model.Message;
import com.spshop.model.Order;
import com.spshop.model.OrderItem;
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
import com.spshop.utils.Encrypt;
import com.spshop.utils.EncryptUtil;
import com.spshop.utils.Utils;
import com.spshop.web.view.SiteView;

@Controller
@SessionAttributes({ "continueShopping" })
public class UserCenterController extends BaseController {

	Logger logger = Logger.getLogger(UserCenterController.class);

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
	public String shoppingCartAdress(Model model) {

		if (getUserView().getCart().getItemCount() < 1) {
			getUserView().getErr().put(EMPTY_ORDER, "Shopping cart is empty");
			return "shoppingCart";
		}

		for (OrderItem orderItem : getUserView().getCart().getOrder()
				.getItems()) {
			if (orderItem.getProduct().getOptType() == 1) {
				SuitMeasurement measurement = getUserView().getLoginUser()
						.getSuitMeasurement();
				if (!validateMeasurements()) {
					model.addAttribute(CURRENT_PRODUCT, orderItem.getProduct());
					getUserView()
							.getMsg()
							.put(MEASUREMENT_MSG,
									"You need fill the suit measurement then continue...");
					model.addAttribute("continueShopping", "true");
					return "redirect:/uc/my-measurements";
				} else {
					getUserView().getCart().getOrder()
							.setMySuitMeasurement(measurement);
					getUserView().getCart().getOrder()
							.setSuitMeasurementComplete(true);
					break;
				}
			}
		}

		Address primary = null;
		Address billing = null;

		getUserView().getCart().getOrder()
				.setCustomerEmail(getUserView().getLoginUser().getEmail());

		if (StringUtils.isNotBlank(getUserView().getCart().getOrder()
				.getCustomerName())
				&& StringUtils.isNotBlank(getUserView().getCart().getOrder()
						.getPrimaryAddress().getPhone())) {
			primary = getUserView().getCart().getOrder().getPrimaryAddress();
		} else {
			primary = getUserView().getLoginUser().getPrimaryAddress();
		}

		if (StringUtils.isNotBlank(getUserView().getCart().getOrder()
				.getBcustomerName())) {
			billing = getUserView().getCart().getOrder().getBillingAddress();
		} else {
			billing = getUserView().getLoginUser().getBillingAddress();
		}

		model.addAttribute(ADD_TYPE_P, primary);
		model.addAttribute(ADD_TYPE_B, billing);

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
		
		if(null == order){
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
			if(StringUtils.isBlank(null)){
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
			}

			if ("YoursPay".equals(payType)) {
				/** è®¢å�•ä¿¡æ�¯ **/

				String MerNo = "1624";
				/** <å¿…å¡«>--å•†æˆ·å�·. **/

				String BillNo = order.getName();
				/** <å¿…å¡«>--è®¢å�•å�·. ä¸€ä¸ªç½‘åº—å�ªèƒ½äº§ç”Ÿå”¯ä¸€çš„è®¢å�•å�·,ä¸�èƒ½å‡ºçŽ°é‡�å¤�,å�¯ä»¥æ˜¯å­—æ¯�å’Œæ•°å­—çš„ç»„å�ˆ. **/

				String MD5key = "YNWNUrlJ";
				/** <å¿…å¡«>--å¯†é’¥. å�¯ä»¥åœ¨YourSpayå•†æˆ·å�Žå�°æŸ¥è¯¢å’Œä¿®æ”¹,ä¸ºäº†æ”¯ä»˜å®‰å…¨,å»ºè®®ä¸€æ®µæ—¶é—´æ›´æ�¢ä¸€æ¬¡. **/

				String Amount = new NumberFormat("##0.##").getNumberFormat()
						.format((order.getTotalPrice() - order
								.getCouponCutOff()) * currencyRate);
				/** <å¿…å¡«>--è®¢å�•æ€»é‡‘é¢�.åŒ…æ‹¬è¿�è´¹åœ¨å†…. å¿…é¡»å�ªèƒ½ä¸ºæ•°å­—,å¹¶ä¸”å¤§äºŽ0,æœ€å¤šä¸ºå°�æ•°ç‚¹ä¸¤ä½�. **/

				String Freight = new NumberFormat("##0.##").getNumberFormat()
						.format((order.getDePrice()) * currencyRate);
				/** <å¿…å¡«>--è¿�è´¹. å¿…é¡»å�ªèƒ½ä¸ºæ•°å­—,æœ€å¤šä¸ºå°�æ•°ç‚¹ä¸¤ä½�,å¦‚æžœæ— è¿�è´¹,å�¯ä»¥ä¸ºè®¾ä¸º0. **/

				String CurrencyCode = order.getCurrency();
				/** <å¿…å¡«>--å¸�ç§�. ç¾Žå…ƒ:USD,è‹±é•‘:GBP,æ¬§å…ƒ:EUR,åŠ å…ƒ:CAD,æ¾³å…ƒ:AUD,æ—¥å…ƒ:JPY. **/

				/*
				 * ä»¥ä¸‹ä¸ºè´¦å�•ä¿¡æ�¯.ä¸ºäº†è®©å®¢æˆ·åœ¨æ”¯ä»˜é¡µé�¢æ— éœ€é‡�å¤�å¡«å†™è´¦å�•ä¿¡æ�¯,å»ºè®®è´¦å�•ä¿¡æ�¯å°½å�¯èƒ½èŽ·å�–,å¦‚æžœç½‘åº—ç³»ç»Ÿæ— è´¦å�•ä¿¡æ�¯,å»ºè®®ç”¨æ”¶è´§ä¿¡æ�¯ä»£æ›¿
				 * .
				 */

				String BFirstName = order.getUser().getFirstName() == null ? ""
						: order.getUser().getFirstName();
				/** <å�¯é€‰>--æŒ�å�¡äººå§“. å¦‚æžœç½‘åº—å�ªæœ‰ä¸€ä¸ªå…¨å��,å»ºè®®æŠŠå…¨å��å¯¹å§“å’Œå��å�„èµ‹å€¼ä¸€ä»½. **/

				String BLastName = order.getUser().getLastName() == null ? ""
						: order.getUser().getLastName();
				/** <å�¯é€‰>--æŒ�å�¡äººå��. å¦‚æžœç½‘åº—å�ªæœ‰ä¸€ä¸ªå…¨å��,å»ºè®®æŠŠå…¨å��å¯¹å§“å’Œå��å�„èµ‹å€¼ä¸€ä»½. **/

				String Email = order.getUser().getEmail();
				/** <å¿…å¡«>--æŒ�å�¡äººé‚®ç®±. ç”¨äºŽæ”¯ä»˜æˆ�åŠŸæˆ–è€…å¤±è´¥,å�‘å®¢æˆ·å�‘é€�æ”¯ä»˜æˆ�åŠŸ/å¤±è´¥æ��ç¤ºé‚®ä»¶. **/

				String Phone = order.getUser().getBillingAddress().getPhone();
				/** <å�¯é€‰>--æŒ�å�¡äººç”µè¯�. **/

				String BillAddress = order.getUser().getBillingAddress()
						.getAddress1() == null ? "" : order.getUser()
						.getBillingAddress().getAddress1();
				/** <å�¯é€‰>--è¯¦ç»†åœ°å�€. **/

				String BillCity = order.getUser().getBillingAddress().getCity() == null ? ""
						: order.getUser().getBillingAddress().getCity();
				/** <å�¯é€‰>--åŸŽå¸‚. **/

				String BillState = order.getUser().getBillingAddress()
						.getStateProvince() == null ? "" : order.getUser()
						.getBillingAddress().getStateProvince();
				/** <å�¯é€‰>--çœ�ä»½/å·ž. **/

				String BillCountry = order.getUser().getBillingAddress()
						.getCountry() == 0 ? "" : ServiceFactory
						.getService(CountryService.class)
						.getCountryById(
								order.getUser().getBillingAddress()
										.getCountry()).getAbbrCode();
				/** <å�¯é€‰>--å›½å®¶. å›½å®¶å��ç§°æœ€å¥½ç”¨å¤§å†™,è€Œä¸”æ˜¯å›½å®¶ç®€ç§°. **/

				String BillZip = order.getUser().getBillingAddress()
						.getPostalCode() == null ? "" : order.getUser()
						.getBillingAddress().getPostalCode();
				/** <å�¯é€‰>--é‚®ç¼–. **/

				if (order.getUser().isBillingSameAsPrimary()) {
					Phone = order.getUser().getPrimaryAddress().getPhone();
					/** <å�¯é€‰>--æŒ�å�¡äººç”µè¯�. **/

					BillAddress = order.getUser().getPrimaryAddress()
							.getAddress1() == null ? "" : order.getUser()
							.getPrimaryAddress().getAddress1();
					/** <å�¯é€‰>--è¯¦ç»†åœ°å�€. **/

					BillCity = order.getUser().getPrimaryAddress().getCity() == null ? ""
							: order.getUser().getPrimaryAddress().getCity();
					/** <å�¯é€‰>--åŸŽå¸‚. **/

					BillState = order.getUser().getPrimaryAddress()
							.getStateProvince() == null ? "" : order.getUser()
							.getPrimaryAddress().getStateProvince();
					/** <å�¯é€‰>--çœ�ä»½/å·ž. **/

					BillCountry = order.getUser().getPrimaryAddress()
							.getCountry() == 0 ? "" : ServiceFactory
							.getService(CountryService.class)
							.getCountryById(
									order.getUser().getPrimaryAddress()
											.getCountry()).getAbbrCode();
					/** <å�¯é€‰>--å›½å®¶. å›½å®¶å��ç§°æœ€å¥½ç”¨å¤§å†™,è€Œä¸”æ˜¯å›½å®¶ç®€ç§°. **/

					BillZip = order.getUser().getPrimaryAddress()
							.getPostalCode() == null ? "" : order.getUser()
							.getPrimaryAddress().getPostalCode();
				}

				/** æ”¶è´§äººä¿¡æ�¯ **/

				String SFirstName = BFirstName;
				/** <å¿…å¡«>--æ”¶è´§äººå§“. å¦‚æžœç½‘åº—å�ªæœ‰ä¸€ä¸ªå…¨å��,å»ºè®®æŠŠå…¨å��å¯¹å§“å’Œå��å�„èµ‹å€¼ä¸€ä»½. **/

				String SLastName = BLastName;
				/** <å¿…å¡«>--æ”¶è´§äººå��. å¦‚æžœç½‘åº—å�ªæœ‰ä¸€ä¸ªå…¨å��,å»ºè®®æŠŠå…¨å��å¯¹å§“å’Œå��å�„èµ‹å€¼ä¸€ä»½. **/

				String ShipAddress = "00000";
				/** <å¿…å¡«>--è¯¦ç»†åœ°å�€. **/

				String ShipCity = "00000";
				/** <å¿…å¡«>--åŸŽå¸‚. **/

				String ShipState = "00000";
				/** <å�¯é€‰>--çœ�ä»½/å·ž. **/

				String ShipCountry = "00000";
				/** <å¿…å¡«>--å›½å®¶. å›½å®¶å��ç§°æœ€å¥½ç”¨å¤§å†™,è€Œä¸”æ˜¯å›½å®¶ç®€ç§°. **/

				String ShipZip = "00000";
				/** <å¿…å¡«>--é‚®ç¼–. **/

				String ShipEmail = order.getUser().getEmail();
				/** <å�¯é€‰>--é‚®ç®±. **/

				String ShipPhone = order.getUser().getPrimaryAddress()
						.getPhone();
				/** <å�¯é€‰>--ç”µè¯�. **/

				/** é€šé�“ä¿¡æ�¯ **/
				String Language = "2";
				/** <å¿…å¡«>--é€šé�“å�‚æ•°,é�žè¯­è¨€,å�ªèƒ½ä¸ºå›ºå®šå€¼:2. **/

				String LangCode = "en";
				/** <å�¯é€‰>--æ”¯ä»˜é¡µé�¢è¯­è¨€. è‹±æ–‡:en,æ³•è¯­:fr,æ„�å¤§åˆ©è¯­:it,å¾·è¯­:de,æ—¥è¯­:ja,é»˜è®¤ä¸ºè‹±æ–‡(en). **/

				String Currency = "15";
				/** <å¿…å¡«>--é€šé�“å�‚æ•°.é�žå¸�ç§�,å�ªèƒ½ä¸ºå›ºå®šå€¼:15. **/

				String ReturnURL = "http://www.honeybuy.com//uc/yoursPayResults";
				/** <å¿…å¡«>--è¿”å›žé¡µé�¢. æ”¯ä»˜å®Œæˆ�å�Ž,å°†è¿”å›žåˆ°æ­¤é¡µé�¢,æ��ç¤ºæ”¯ ä»˜çš„ç»“æžœ(æˆ�åŠŸ/å¤±è´¥). **/

				String Remark = "";
				/** <å�¯é€‰>--å¤‡æ³¨. ç½‘åº—ä¸­å®¢æˆ·å¡«å†™çš„å¤‡æ³¨. **/

				/**
				 * <å¿…å¡«>--è´§ç‰©ä¿¡æ�¯.å¦‚æžœè´­ç‰©å¤šä¸ª,é€šè¿‡å¾ªçŽ¯é��åŽ†,æŠŠæ‰€éœ€çš„æ¯�ä¸ªå•†å“�çš„å��ç§°(GoodsName),æ•°é‡�(Qty)å�•ä»·(
				 * Price)è¿›è¡Œè¿žç»“
				 **/
				/**
				 * æ¯”å¦‚ï¼š<Goods><GoodsName>å•†å“�å��</GoodsName><Qty>æ•°é‡�</Qty><Price>å�•ä»·</
				 * Price></Goods>
				 **/
				StringBuilder GoodsListInfo = new StringBuilder();
				GoodsListInfo = GoodsListInfo.append("<Goods><GoodsName>")
						.append("Nike").append("</GoodsName><Qty>").append("2")
						.append("</Qty><Price>").append("10")
						.append("</Price></Goods>");
				GoodsListInfo = GoodsListInfo.append("<Goods><GoodsName>")
						.append("Addi").append("</GoodsName><Qty>").append("1")
						.append("</Qty><Price>").append("20")
						.append("</Price></Goods>");

				/** å�‚æ•°ç»„å�ˆ, é¡ºåº�ä¸�èƒ½é¢ å€’ **/
				StringBuilder md5src = new StringBuilder();
				md5src = md5src.append(MerNo).append(BillNo).append(Freight)
						.append(Amount).append(CurrencyCode).append(ReturnURL)
						.append(Email).append(MD5key);
				/** å¯¹å�‚æ•°ç»„å�ˆå­—ç¬¦ä¸²è¿›è¡Œmd5åŠ å¯†,å¹¶ä¸”è½¬æ�¢ä¸ºå¤§å†™ **/
				String MD5info = Encrypt.MD5(md5src.toString()).toUpperCase();

				/** æŠŠå�‚æ•°ç”¨xmlç»„å�ˆæˆ�å­—ç¬¦ä¸² **/
				StringBuilder basexml = new StringBuilder();
				basexml = basexml
						.append("<?xml version='1.0' encoding='UTF-8' ?><Order>");
				basexml = basexml.append("<MerNo>").append(MerNo)
						.append("</MerNo>");
				basexml = basexml.append("<BillNo>").append(BillNo)
						.append("</BillNo>");
				basexml = basexml.append("<GoodsList>").append(GoodsListInfo)
						.append("</GoodsList>");
				basexml = basexml.append("<Amount>").append(Amount)
						.append("</Amount>");
				basexml = basexml.append("<Freight>").append(Freight)
						.append("</Freight>");
				basexml = basexml.append("<CurrencyCode>").append(CurrencyCode)
						.append("</CurrencyCode>");
				basexml = basexml.append("<BFirstName>").append(BFirstName)
						.append("</BFirstName>");
				basexml = basexml.append("<BLastName>").append(BLastName)
						.append("</BLastName>");
				basexml = basexml.append("<Email>").append(Email)
						.append("</Email>");
				basexml = basexml.append("<Phone>").append(Phone)
						.append("</Phone>");
				basexml = basexml.append("<BillAddress>").append(BillAddress)
						.append("</BillAddress>");
				basexml = basexml.append("<BillCity>").append(BillCity)
						.append("</BillCity>");
				basexml = basexml.append("<BillState>").append(BillState)
						.append("</BillState>");
				basexml = basexml.append("<BillCountry>").append(BillCountry)
						.append("</BillCountry>");
				basexml = basexml.append("<BillZip>").append(BillZip)
						.append("</BillZip>");
				basexml = basexml.append("<SFirstName>").append(SFirstName)
						.append("</SFirstName>");
				basexml = basexml.append("<SLastName>").append(SLastName)
						.append("</SLastName>");
				basexml = basexml.append("<ShipAddress>").append(ShipAddress)
						.append("</ShipAddress>");
				basexml = basexml.append("<ShipCity>").append(ShipCity)
						.append("</ShipCity>");
				basexml = basexml.append("<ShipState>").append(ShipState)
						.append("</ShipState>");
				basexml = basexml.append("<ShipCountry>").append(ShipCountry)
						.append("</ShipCountry>");
				basexml = basexml.append("<ShipZip>").append(ShipZip)
						.append("</ShipZip>");
				basexml = basexml.append("<ShipEmail>").append(ShipEmail)
						.append("</ShipEmail>");
				basexml = basexml.append("<ShipPhone>").append(ShipPhone)
						.append("</ShipPhone>");
				basexml = basexml.append("<Language>").append(Language)
						.append("</Language>");
				basexml = basexml.append("<LangCode>").append(LangCode)
						.append("</LangCode>");
				basexml = basexml.append("<Currency>").append(Currency)
						.append("</Currency>");
				basexml = basexml.append("<ReturnURL>").append(ReturnURL)
						.append("</ReturnURL>");
				basexml = basexml.append("<Remark>").append(Remark)
						.append("</Remark>");
				basexml = basexml.append("<MD5info>").append(MD5info)
						.append("</MD5info></Order>");
				String TradeInfo = Encrypt.URLEncode_BASE64(basexml.toString());

				model.addAttribute("tradeInfo", TradeInfo);

				return "yoursPay";
			} else if ("alipay".equals(payType)) {

				// //////////////////////////////////è¯·æ±‚å�‚æ•°//////////////////////////////////////

				// æœ�åŠ¡å™¨å¼‚æ­¥é€šçŸ¥é¡µé�¢è·¯å¾„
				String notify_url = getSiteView().getHost()+"/uc/aliPayAsyncResults";
				// éœ€http://æ ¼å¼�çš„å®Œæ•´è·¯å¾„ï¼Œä¸�å…�è®¸åŠ ?id=123è¿™ç±»è‡ªå®šä¹‰å�‚æ•°

				// é¡µé�¢è·³è½¬å�Œæ­¥é€šçŸ¥é¡µé�¢è·¯å¾„
				String return_url = getSiteView().getHost()+"/uc/aliPaySyncResults";
				// éœ€http://æ ¼å¼�çš„å®Œæ•´è·¯å¾„ï¼Œä¸�å…�è®¸åŠ ?id=123è¿™ç±»è‡ªå®šä¹‰å�‚æ•°

				// å•†æˆ·è®¢å�•å�·
				String out_trade_no = order.getName();
				// å¿…å¡«

				// è®¢å�•å��ç§°
				String subject = order.getName();
				// å¿…å¡«

				if(null == cardType){
					cardType = "boc-visa";
				}
				// é»˜è®¤ç½‘é“¶
				String default_bank = cardType/*request.getParameter("default_bank")*/;
				// å¿…å¡«ï¼Œå¦‚æžœè¦�ä½¿ç”¨å¤–å�¡æ”¯ä»˜åŠŸèƒ½ï¼Œæœ¬å�‚æ•°éœ€èµ‹å€¼ä¸ºâ€œ12.5 é“¶è¡Œåˆ—è¡¨â€�ä¸­çš„å€¼
				
				// å…¬ç”¨ä¸šåŠ¡æ‰©å±•å�‚æ•°
				
				Country customerCountry = ServiceFactory.getService(CountryService.class).getCountryByName(order.getCustomerCountry());
				
				String countryCode = customerCountry == null ? "NA": customerCountry.getAbbrCode();
				
				String extend_param = "ship_to_country^"+countryCode
										+"|ship_to_state^"+order.getState()
										+"|ship_to_street1^"+order.getCustomerAddress()
										+"|ship_to_phonenumber^"+order.getCustomerTelephone()
										+"|ship_to_postalcode^"+order.getCustomerZipcode()
										+"|ship_to_shipmethod^"+order.getShippingMethod()
										+"|ship_to_firstname^"+(order.getUser().getFirstName()==null?"NA":order.getUser().getFirstName())
										+"|ship_to_lastname^"+(order.getUser().getLastName()==null?"NA":order.getUser().getLastName())
										+"|product_name^"+order.getName()
										+"|registration_name^"+order.getUser().getEmail()
										+"|registration_email^"+order.getUser().getEmail()
										+"|registration_phone^"+order.getCustomerTelephone();
				// å¿…å¡«ï¼Œç”¨äºŽå•†æˆ·çš„ç‰¹å®šä¸šåŠ¡ä¿¡æ�¯çš„ä¼ é€’

				// å�–å®¶æ”¯ä»˜å®�è´¦å�·
				String seller_logon_id = "honeybuy@foxmail.com";
				// å¿…å¡«

				// ä»˜æ¬¾é‡‘é¢�
				String total_fee =  new NumberFormat("##0.##").getNumberFormat().format(order.getTotalPrice() + order.getDePrice());
				// å¿…å¡«

				// è®¢å�•æ��è¿°

				String body = "";
				
				// å•†å“�å±•ç¤ºåœ°å�€
				String show_url = "www.honeybuy.com/uc/orderDetails?id="+order.getName();
				// ç©ºå€¼

				// å¸�ç§�
				String currency = /*order.getCurrency()*/"USD";
				// å¿…å¡«ï¼Œdefault_bankä¸ºboc-visaæˆ–boc-masteræ—¶ï¼Œæ”¯æŒ�USDï¼Œä¸ºboc-jcbæ—¶ï¼Œä¸�æ”¯æŒ�currencyå�‚æ•°ï¼Œå�³é»˜è®¤æ”¯æŒ�RMB

				// ////////////////////////////////////////////////////////////////////////////////

				// æŠŠè¯·æ±‚å�‚æ•°æ‰“åŒ…æˆ�æ•°ç»„
				Map<String, String> sParaTemp = new HashMap<String, String>();
				sParaTemp.put("service", "alipay.trade.direct.forcard.pay");
				sParaTemp.put("partner", AlipayConfig.partner);
				sParaTemp.put("_input_charset", AlipayConfig.input_charset);
				sParaTemp.put("notify_url", notify_url);
				sParaTemp.put("return_url", return_url);
				sParaTemp.put("out_trade_no", out_trade_no);
				sParaTemp.put("subject", subject);
				sParaTemp.put("default_bank", default_bank);
				sParaTemp.put("extend_param", extend_param);
				sParaTemp.put("body", body);
				sParaTemp.put("seller_logon_id", seller_logon_id);
//				sParaTemp.put("seller_logon_id", seller_logon_id);
				sParaTemp.put("total_fee", total_fee);
				//sParaTemp.put("body", body);
				sParaTemp.put("show_url", show_url);
				sParaTemp.put("currency", currency);

				// å»ºç«‹è¯·æ±‚
				String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "get",
						"Submit");
				
				model.addAttribute("alipayFormContent", sHtmlText);
				
				//out.println(sHtmlText);
				
				return "alipay";
			}else if ("Globebill".equals(payType)){
				
				globebillPay(order,model);
				
				return "Globebill";
			}

			return "paypal";
		}

		return "shoppingCart_payment";
	}

	private void globebillPay(Order order, Model model) {
		String merNo = "10246";
		String gatewayNo = "10246001";
		String signKeyNo = "04d6x2r8";
		String orderNumber = order.getName();//è®¢å�•å�·
		//è®¢å�•é‡‘é¢�, ä¸¤ä½�å°�æ•°
	    String amount = new NumberFormat("##0.##").getNumberFormat().format(getSiteView().getCurrencies().get(order.getCurrency())*(order.getTotalPrice() + order.getDePrice() - order.getCouponCutOff()));

	    String returnUrl = getSiteView().getHost() + "/uc/globebillPayRs";
	    
	    
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
	    
	    String username = order.getCustomerName();
	    
	    String cc = "";
	    
	   long ccId = order.getCountry();
	    
	   if(ccId >0){
		   cc = getSiteView().getCountryMap().get(String.valueOf(ccId)).getAbbrCode();
	   }
	   
	   if(username != null){
		   username = username.trim();
		   int last = username.lastIndexOf(' ');
		   if(last > 0){
			   firstName = username.substring(0, last);
			   lastName = username.substring(last);
		   }else{
			   firstName = username;
		   }
	   }
	    
	    model.addAttribute("signInfo", signString);
	    model.addAttribute("amount", amount);
	    model.addAttribute("returnUrl", returnUrl);
	    model.addAttribute("firstName", firstName);
	    model.addAttribute("lastName", lastName);
	    model.addAttribute("cc", cc);
	    
	}
	
	@RequestMapping(value = "/shoppingCart_address", method = RequestMethod.POST)
	public String submitAddressInfo(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		Address primary = retrieveAddress(request, ADD_TYPE_P);
		Address billing = retrieveAddress(request, ADD_TYPE_B);
		String billingAsPrimary = request.getParameter(BILLING_SAME_AS_PRIMARY);
		String shippingMethod = request.getParameter(SHIPPING_METHOD);

		getUserView().getCart().getOrder().setPrimaryAddress(primary);
		getUserView().getCart().getOrder().setBillingAddress(billing);

		Map<String, String> vs = validateAddress(getUserView().getCart()
				.getOrder().getPrimaryAddress(), ADD_TYPE_P);

		if (null == billingAsPrimary) {
			vs.putAll(validateAddress(getUserView().getCart().getOrder()
					.getBillingAddress(), ADD_TYPE_B));
			getUserView().getCart().getOrder().setBillingSameAsPrimary(false);
		} else {
			getUserView().getCart().getOrder().setBillingSameAsPrimary(true);
		}

		if (SHIPPING_STANDARD.equals(shippingMethod)) {
			getUserView().getCart().getOrder()
					.setShippingMethod(SHIPPING_STANDARD);
		} else {
			getUserView().getCart().getOrder()
					.setShippingMethod(SHIPPING_EXPEDITED);
		}

		getUserView().setErr(vs);

		if (MapUtils.isNotEmpty(getUserView().getErr())) {
			return "shoppingCart_address";
		}

		Country country = ServiceFactory.getService(CountryService.class)
				.getCountryById(
						getUserView().getCart().getOrder().getPrimaryAddress()
								.getCountry());

		getUserView().getCart().getOrder()
				.setCustomerCountry(country.getName());

		if (!getUserView().getCart().getOrder().isBillingSameAsPrimary()) {
			Country country1 = ServiceFactory.getService(CountryService.class)
					.getCountryById(
							getUserView().getCart().getOrder()
									.getBillingAddress().getCountry());
			getUserView().getCart().getOrder()
					.setCustomerBCountry(country1.getName());
		}

		if (SHIPPING_STANDARD.equals(getUserView().getCart().getOrder()
				.getShippingMethod())) {
			if (getUserView().getCart().getOrder().getTotalPrice() < country
					.getFreeDePrice()) {
				getUserView().getCart().getOrder()
						.setDePrice(country.getDePrice());
			} else {
				getUserView().getCart().getOrder().setDePrice(0f);
			}
		} else {
			if (getUserView().getCart().getOrder().getTotalPrice() < country
					.getFreeAdDePrice()) {
				getUserView().getCart().getOrder()
						.setDePrice(country.getAdDePrice());
			} else {
				getUserView().getCart().getOrder().setDePrice(0f);
			}
		}

		ServiceFactory.getService(OrderService.class).saveOrder(
				getUserView().getCart().getOrder(),
				OrderStatus.ONSHOPPING.toString());

		return "redirect:/uc/shoppingCart_payment";
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

		String type = request.getParameter(ADD_TYPE);

		Address address = retrieveAddress(request);

		getUserView().getErr().putAll(validateAddress(address, type));

		if (getUserView().getErr().isEmpty()) {
			User user = getUserView().getLoginUser();
			user.setPrimaryAddress(address);
			user = ServiceFactory.getService(UserService.class).saveUser(user);
			getUserView().getMsg().put(UPDATE_ADDRESS_1_SUC,
					"Update successfully");
		}

		model.addAttribute(PRIMARY_ADDRESS, address);

		return "userProfile";
	}

	@RequestMapping(value = "/userProfile", params = { "action=updateAddress2" }, method = RequestMethod.POST)
	public String updateAddress2(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		String type = request.getParameter(ADD_TYPE);
		String sameAsPrimary = request.getParameter(BILLING_SAME_AS_PRIMARY);
		Address address = retrieveAddress(request);
		if (!StringUtils.isNotBlank(sameAsPrimary)) {
			getUserView().getErr().putAll(validateAddress(address, type));

			if (getUserView().getErr().isEmpty()) {
				User user = getUserView().getLoginUser();
				user.setBillingAddress(address);
				user.setBillingSameAsPrimary(false);
				user = ServiceFactory.getService(UserService.class).saveUser(
						user);
				getUserView().setLoginUser(user);
				getUserView().getMsg().put(UPDATE_ADDRESS_2_SUC,
						"Update successfully");
			}
		} else {
			User user = getUserView().getLoginUser();
			user.setBillingSameAsPrimary(true);
			user = ServiceFactory.getService(UserService.class).saveUser(user);
			getUserView().setLoginUser(user);
			getUserView().getMsg().put(UPDATE_ADDRESS_2_SUC,
					"Update successfully");
		}

		model.addAttribute(BILLING_ADDRESS, address);

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

	private Address retrieveAddress(HttpServletRequest request) {
		String type = request.getParameter(ADD_TYPE);
		return retrieveAddress(request, type);
	}

	private Address retrieveAddress(HttpServletRequest request, String type) {
		String userName = request.getParameter(type + USERNAME);
		String add1 = request.getParameter(type + ADDRESS1);
		String add2 = request.getParameter(type + ADDRESS2);
		String city = request.getParameter(type + CITY);
		String stateP = request.getParameter(type + STATE_PROVINCE);
		String c = request.getParameter(type + COUNTRY);
		String postalCode = request.getParameter(type + POASTAL_CODE);
		String telNum = request.getParameter(type + TEL_NUM);

		int intC = 0;

		try {
			intC = Integer.parseInt(c);
		} catch (NumberFormatException e) {
		}

		return new Address(userName, add1, add2, city, stateP, intC,
				postalCode, telNum);
	}

	private Map<String, String> validateAddress(Address address, String type) {
		Map<String, String> err = new HashMap<String, String>();

		if (StringUtils.isBlank(address.getFullName())
				|| address.getFullName().length() > 100) {
			err.put(type + USERNAME_ERR, "Invalid username");
		}

		if (StringUtils.isBlank(address.getAddress1())
				|| address.getAddress1().length() > 200) {
			err.put(type + ADDRESS1_ERR, "Invalid address");
		}

		if (StringUtils.isNotBlank(address.getAddress2())
				&& address.getAddress2().length() > 200) {
			err.put(type + ADDRESS2_ERR, "Invalid address");
		}

		if (StringUtils.isBlank(address.getCity())
				|| address.getCity().length() > 100) {
			err.put(type + CITY_ERR, "Invalid city");
		}

		if (null == ServiceFactory.getService(CountryService.class)
				.getCountryById(address.getCountry())) {
			err.put(type + COUNTRY_ERR, "Invalid country");
		}

		if (StringUtils.isBlank(address.getStateProvince())
				|| address.getStateProvince().length() > 100) {
			err.put(type + STATE_PROVINCE_ERR, "Invalid state");
		}

		if (StringUtils.isBlank(address.getPostalCode())
				|| address.getPostalCode().length() > 100) {
			err.put(type + POSTAL_CODE_ERR, "Invalid postal code");
		}

		if (StringUtils.isBlank(address.getPhone())
				|| address.getPhone().length() > 100) {
			err.put(type + TEL_NUM_ERR, "Invalid phone number");
		}

		return err;
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
