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
			}

			if ("YoursPay".equals(payType)) {
				/** 订单信息 **/

				String MerNo = "1624";
				/** <必填>--商户号. **/

				String BillNo = order.getName();
				/** <必填>--订单号. 一个网店只能产生唯一的订单号,不能出现重复,可以是字母和数字的组合. **/

				String MD5key = "YNWNUrlJ";
				/** <必填>--密钥. 可以在YourSpay商户后台查询和修改,为了支付安全,建议一段时间更换一次. **/

				String Amount = new NumberFormat("##0.##").getNumberFormat()
						.format((order.getTotalPrice() - order
								.getCouponCutOff()) * currencyRate);
				/** <必填>--订单总金额.包括运费在内. 必须只能为数字,并且大于0,最多为小数点两位. **/

				String Freight = new NumberFormat("##0.##").getNumberFormat()
						.format((order.getDePrice()) * currencyRate);
				/** <必填>--运费. 必须只能为数字,最多为小数点两位,如果无运费,可以为设为0. **/

				String CurrencyCode = order.getCurrency();
				/** <必填>--币种. 美元:USD,英镑:GBP,欧元:EUR,加元:CAD,澳元:AUD,日元:JPY. **/

				/*
				 * 以下为账单信息.为了让客户在支付页面无需重复填写账单信息,建议账单信息尽可能获取,如果网店系统无账单信息,建议用收货信息代替
				 * .
				 */

				String BFirstName = order.getUser().getFirstName() == null ? ""
						: order.getUser().getFirstName();
				/** <可选>--持卡人姓. 如果网店只有一个全名,建议把全名对姓和名各赋值一份. **/

				String BLastName = order.getUser().getLastName() == null ? ""
						: order.getUser().getLastName();
				/** <可选>--持卡人名. 如果网店只有一个全名,建议把全名对姓和名各赋值一份. **/

				String Email = order.getUser().getEmail();
				/** <必填>--持卡人邮箱. 用于支付成功或者失败,向客户发送支付成功/失败提示邮件. **/

				String Phone = order.getUser().getBillingAddress().getPhone();
				/** <可选>--持卡人电话. **/

				String BillAddress = order.getUser().getBillingAddress()
						.getAddress1() == null ? "" : order.getUser()
						.getBillingAddress().getAddress1();
				/** <可选>--详细地址. **/

				String BillCity = order.getUser().getBillingAddress().getCity() == null ? ""
						: order.getUser().getBillingAddress().getCity();
				/** <可选>--城市. **/

				String BillState = order.getUser().getBillingAddress()
						.getStateProvince() == null ? "" : order.getUser()
						.getBillingAddress().getStateProvince();
				/** <可选>--省份/州. **/

				String BillCountry = order.getUser().getBillingAddress()
						.getCountry() == 0 ? "" : ServiceFactory
						.getService(CountryService.class)
						.getCountryById(
								order.getUser().getBillingAddress()
										.getCountry()).getAbbrCode();
				/** <可选>--国家. 国家名称最好用大写,而且是国家简称. **/

				String BillZip = order.getUser().getBillingAddress()
						.getPostalCode() == null ? "" : order.getUser()
						.getBillingAddress().getPostalCode();
				/** <可选>--邮编. **/

				if (order.getUser().isBillingSameAsPrimary()) {
					Phone = order.getUser().getPrimaryAddress().getPhone();
					/** <可选>--持卡人电话. **/

					BillAddress = order.getUser().getPrimaryAddress()
							.getAddress1() == null ? "" : order.getUser()
							.getPrimaryAddress().getAddress1();
					/** <可选>--详细地址. **/

					BillCity = order.getUser().getPrimaryAddress().getCity() == null ? ""
							: order.getUser().getPrimaryAddress().getCity();
					/** <可选>--城市. **/

					BillState = order.getUser().getPrimaryAddress()
							.getStateProvince() == null ? "" : order.getUser()
							.getPrimaryAddress().getStateProvince();
					/** <可选>--省份/州. **/

					BillCountry = order.getUser().getPrimaryAddress()
							.getCountry() == 0 ? "" : ServiceFactory
							.getService(CountryService.class)
							.getCountryById(
									order.getUser().getPrimaryAddress()
											.getCountry()).getAbbrCode();
					/** <可选>--国家. 国家名称最好用大写,而且是国家简称. **/

					BillZip = order.getUser().getPrimaryAddress()
							.getPostalCode() == null ? "" : order.getUser()
							.getPrimaryAddress().getPostalCode();
				}

				/** 收货人信息 **/

				String SFirstName = BFirstName;
				/** <必填>--收货人姓. 如果网店只有一个全名,建议把全名对姓和名各赋值一份. **/

				String SLastName = BLastName;
				/** <必填>--收货人名. 如果网店只有一个全名,建议把全名对姓和名各赋值一份. **/

				String ShipAddress = "00000";
				/** <必填>--详细地址. **/

				String ShipCity = "00000";
				/** <必填>--城市. **/

				String ShipState = "00000";
				/** <可选>--省份/州. **/

				String ShipCountry = "00000";
				/** <必填>--国家. 国家名称最好用大写,而且是国家简称. **/

				String ShipZip = "00000";
				/** <必填>--邮编. **/

				String ShipEmail = order.getUser().getEmail();
				/** <可选>--邮箱. **/

				String ShipPhone = order.getUser().getPrimaryAddress()
						.getPhone();
				/** <可选>--电话. **/

				/** 通道信息 **/
				String Language = "2";
				/** <必填>--通道参数,非语言,只能为固定值:2. **/

				String LangCode = "en";
				/** <可选>--支付页面语言. 英文:en,法语:fr,意大利语:it,德语:de,日语:ja,默认为英文(en). **/

				String Currency = "15";
				/** <必填>--通道参数.非币种,只能为固定值:15. **/

				String ReturnURL = "http://www.honeybuy.com//uc/yoursPayResults";
				/** <必填>--返回页面. 支付完成后,将返回到此页面,提示支 付的结果(成功/失败). **/

				String Remark = "";
				/** <可选>--备注. 网店中客户填写的备注. **/

				/**
				 * <必填>--货物信息.如果购物多个,通过循环遍历,把所需的每个商品的名称(GoodsName),数量(Qty)单价(
				 * Price)进行连结
				 **/
				/**
				 * 比如：<Goods><GoodsName>商品名</GoodsName><Qty>数量</Qty><Price>单价</
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

				/** 参数组合, 顺序不能颠倒 **/
				StringBuilder md5src = new StringBuilder();
				md5src = md5src.append(MerNo).append(BillNo).append(Freight)
						.append(Amount).append(CurrencyCode).append(ReturnURL)
						.append(Email).append(MD5key);
				/** 对参数组合字符串进行md5加密,并且转换为大写 **/
				String MD5info = Encrypt.MD5(md5src.toString()).toUpperCase();

				/** 把参数用xml组合成字符串 **/
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

				// //////////////////////////////////请求参数//////////////////////////////////////

				// 服务器异步通知页面路径
				String notify_url = getSiteView().getHost()+"/uc/aliPayAsyncResults";
				// 需http://格式的完整路径，不允许加?id=123这类自定义参数

				// 页面跳转同步通知页面路径
				String return_url = getSiteView().getHost()+"/uc/aliPaySyncResults";
				// 需http://格式的完整路径，不允许加?id=123这类自定义参数

				// 商户订单号
				String out_trade_no = order.getName();
				// 必填

				// 订单名称
				String subject = order.getName();
				// 必填

				if(null == cardType){
					cardType = "boc-visa";
				}
				// 默认网银
				String default_bank = cardType/*request.getParameter("default_bank")*/;
				// 必填，如果要使用外卡支付功能，本参数需赋值为“12.5 银行列表”中的值
				
				// 公用业务扩展参数
				
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
				// 必填，用于商户的特定业务信息的传递

				// 卖家支付宝账号
				String seller_logon_id = "honeybuy@foxmail.com";
				// 必填

				// 付款金额
				String total_fee =  new NumberFormat("##0.##").getNumberFormat().format(order.getTotalPrice() + order.getDePrice());
				// 必填

				// 订单描述

				String body = "";
				
				// 商品展示地址
				String show_url = "www.honeybuy.com/uc/orderDetails?id="+order.getName();
				// 空值

				// 币种
				String currency = /*order.getCurrency()*/"USD";
				// 必填，default_bank为boc-visa或boc-master时，支持USD，为boc-jcb时，不支持currency参数，即默认支持RMB

				// ////////////////////////////////////////////////////////////////////////////////

				// 把请求参数打包成数组
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

				// 建立请求
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
		String orderNumber = order.getName();//订单号
		//订单金额, 两位小数
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
