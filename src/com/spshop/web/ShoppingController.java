package com.spshop.web;

import static com.spshop.utils.Constants.ACCEPT_LICENSE;
import static com.spshop.utils.Constants.ACCEPT_LICENSE_ERR;
import static com.spshop.utils.Constants.COLOR_PARAM_PRE;
import static com.spshop.utils.Constants.COOKIE_ACCOUNT;
import static com.spshop.utils.Constants.CURRENT_PRODUCT;
import static com.spshop.utils.Constants.EMPTY_ORDER;
import static com.spshop.utils.Constants.EMPTY_STR;
import static com.spshop.utils.Constants.LOGIN_LANDING_PAGE_PARAM;
import static com.spshop.utils.Constants.LOGIN_PWD;
import static com.spshop.utils.Constants.LOGIN_USER_NAME;
import static com.spshop.utils.Constants.LOGOUT_ACTION;
import static com.spshop.utils.Constants.PRODUCT_ID_PARAM;
import static com.spshop.utils.Constants.QTY_PARAM;
import static com.spshop.utils.Constants.RECOVER_SUCCESS;
import static com.spshop.utils.Constants.REG_PWD;
import static com.spshop.utils.Constants.REG_PWD_ERR;
import static com.spshop.utils.Constants.REG_PWD_RE;
import static com.spshop.utils.Constants.REG_PWD_RE_ERR;
import static com.spshop.utils.Constants.REG_USER;
import static com.spshop.utils.Constants.REG_USER_NAME;
import static com.spshop.utils.Constants.REG_USER_NAME_ERR;
import static com.spshop.utils.Constants.REG_USER_NAME_SUC;
import static com.spshop.utils.Constants.REMEMBER_ID;
import static com.spshop.utils.Constants.SPLITER_AT;
import static com.spshop.utils.Constants.TEXTS_PARAM_PRE;
import static com.spshop.utils.Constants.TEXT_PARAM_PRE;
import static com.spshop.utils.Constants.TRUE;
import static com.spshop.utils.Constants.USER_ACCOUNT_ERROR;
import static com.spshop.utils.Constants.USER_INFO;
import static com.spshop.utils.Constants.USER_NAME_PWD_SPLIT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.NumberFormat;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.alipay.util.AlipayNotify;
import com.spshop.cache.SCacheFacade;
import com.spshop.model.Coupon;
import com.spshop.model.Order;
import com.spshop.model.OrderItem;
import com.spshop.model.Product;
import com.spshop.model.User;
import com.spshop.model.UserOption;
import com.spshop.model.cart.ShoppingCart;
import com.spshop.model.enums.OrderStatus;
import com.spshop.model.enums.SelectType;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CouponService;
import com.spshop.service.intf.OrderService;
import com.spshop.service.intf.UserService;
import com.spshop.utils.Constants;
import com.spshop.utils.EmailTools;
import com.spshop.utils.Encrypt;
import com.spshop.utils.FeedTools;
import com.spshop.utils.Utils;

@Controller
@RequestMapping("/uc")
@SessionAttributes({CURRENT_PRODUCT,"continueShopping"})
public class ShoppingController extends BaseController{
	
	private static final String COLOR = COLOR_PARAM_PRE;
	private static final String QTY = QTY_PARAM;
	private static final String TEXT = TEXT_PARAM_PRE;
	private static final String TEXTS = TEXTS_PARAM_PRE;
	private static final String PRODUCT_ID = PRODUCT_ID_PARAM;
	
	private Logger logger = Logger.getLogger(ShoppingController.class);
	
	@RequestMapping("/shoppingCart")
	public String shoppingCart(Model model) {
		if(getUserView().getCart().getItemCount() < 1){
			getUserView().getErr().put(EMPTY_ORDER, "Shopping cart is empty");
		}
		
		return "shoppingCart";
	}
	
	@RequestMapping(value="/shoppingCart", method = RequestMethod.POST,params="operation=addItem")
	public String shoppingCart2(Model model,HttpServletRequest request,HttpServletResponse response) {
		int qty = retriveQty(request);
		Product product = SCacheFacade.getProduct(retriveProductId(request));
		
		String[] relatedProducts = request.getParameterValues("relatedProduct");
		
		List<UserOption> options = retriveUserOptions(request);
		if(null!=product){
			
			getUserView().getCart().addItem(product, options, qty);
			
			if(product.getOptType() > 0){
				model.addAttribute("continueShopping", "true");
			}
			
			if(null!=relatedProducts){
				for (String pid : relatedProducts) {
					Product p = SCacheFacade.getProduct(pid);
					if(null!=p){
						getUserView().getCart().addItem(p, new ArrayList<UserOption>(), 1);
					}
				}
			}
			
			persistantCart();
		}
		
		return "shoppingCart";
	}
	
	private void persistantCart(){
		Order order = getUserView().getCart().getOrder();
		if(OrderStatus.ONSHOPPING.toString().equals(order.getStatus())){
			order = ServiceFactory.getService(OrderService.class).saveOrder(getUserView().getCart().getOrder(), OrderStatus.ONSHOPPING.toString());
		}
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
	
	private String retriveProductId(ServletRequest request){
		String productId = null;
		
		try {
			productId = request.getParameter(PRODUCT_ID);
		} catch (NumberFormatException e) {
			//e.printStackTrace();
		}
		
		return productId;
	}
	
	@SuppressWarnings("unchecked")
	private List<UserOption> retriveUserOptions(ServletRequest request){
		List<UserOption> options = new ArrayList<UserOption>();
		Enumeration<String> params = request.getParameterNames();
		while(params.hasMoreElements()){
			String param = params.nextElement();
			String[] ps = param.split(SPLITER_AT);
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
	
	@RequestMapping("/ProductFeed")
    public String ProductFeed(Model model, HttpServletResponse response) {
	    FeedTools.generateProductsExcel();
        return null;
    }
	

	@RequestMapping(value="/recoverPwd", method = RequestMethod.GET)
    public String recoverPwd2(Model model, HttpServletResponse response) {
        return "recoverPassword";
    }
	
	@RequestMapping(value="/createAccount", method = RequestMethod.GET)
    public String createAccount2(Model model, HttpServletResponse response) {
        return "login";
    }
	
	@RequestMapping(value="/createAccount", method = RequestMethod.POST)
    public String createAccount(Model model,HttpServletRequest request,HttpServletResponse response) {
		boolean noError = true;
		String email = request.getParameter(REG_USER_NAME);
		String pwd1 = request.getParameter(REG_PWD);
		String pwd2 = request.getParameter(REG_PWD_RE);
		
		if(null!=email){
			email = email.trim();
		}
		
		String acceptLicense = request.getParameter(ACCEPT_LICENSE);
		
		User user = new User();
		user.setName(email);
		user.setEmail(email);
		user.setPassword(pwd1);
		user.setCreateDate(new Date());
		user.setUpdateDate(new Date());
		
		if(!TRUE.equals(acceptLicense)){
			model.addAttribute(ACCEPT_LICENSE_ERR, "Please accept license");
			noError = false;
		}
		
		String landingpage = null;
		try {
			landingpage = URLDecoder.decode(request.getParameter(LOGIN_LANDING_PAGE_PARAM),"utf-8");
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
			
		if(null==email || !(email.contains("@"))){
				model.addAttribute(REG_USER_NAME_ERR, "Invalid user account");
				noError = false;
		}else{
			User u = ServiceFactory.getService(UserService.class).queryUserByEmail(email);
			if(u != null){
				model.addAttribute(REG_USER_NAME_ERR, "account already exist");
				noError = false;
			}
		}
		
		if(pwd1==null || pwd1.length()<5){
				model.addAttribute(REG_PWD_ERR, "Invalid password");
				noError = false;
		}else{
			if(pwd2==null || !pwd1.equals(pwd2)){
				model.addAttribute(REG_PWD_RE_ERR, "Two passwords are not same");
				noError = false;
			}
		}
		
		
		if(noError){
			final User u = ServiceFactory.getService(UserService.class).saveUser(user);
			if(null!=u){
					model.addAttribute(REG_USER_NAME_SUC, "Create Account successfully");
					model.addAttribute("createdUser", user);
				   final Map<String,Object> root = new HashMap<String,Object>(); 
		            root.put("user", u);
		            u.setPassword(u.getPassword());
		            
		            model.addAttribute(USER_INFO, u);
					request.getSession().setAttribute(USER_INFO, u);
					getUserView().setLoginUser(u);
					
		            new Thread(){
		                public void run() {
		                    try{
		                        EmailTools.sendMail("register", "Welcome to Honeybuy.com, New Member Registration", root, u.getName());
		                    }catch(Exception e){
		                        
		                    }
		                };
		            }.start();
				
			}
		}
		
		if(StringUtils.isNotBlank(landingpage)){
			getUserView().setRequestPage(landingpage);
		}
		model.addAttribute(REG_USER, user);
		
        return "login";
    }
	
	@RequestMapping(value="/checkUserEmail")
	public String checkUserEmail(@RequestParam("RegEmail") String email, HttpServletResponse response) throws IOException{
		
		User user = ServiceFactory.getService(UserService.class).queryUserByEmail(email);
		
		if(null == user){
			response.getWriter().print("0");
		}else{
			response.getWriter().print("1");
		}
		
		return null;
	}
	
	@RequestMapping(value="/recoverPwd", method = RequestMethod.POST)
    public String recoverPwd(Model model,HttpServletRequest request, HttpServletResponse response) {
		
		String userID = request.getParameter(LOGIN_USER_NAME);
		
		User user = ServiceFactory.getService(UserService.class).queryUserByEmail(userID);
		
		if(null == user){
			getUserView().getErr().put(USER_ACCOUNT_ERROR, "Account is not exist!");
		}else{
			getUserView().getMsg().put(RECOVER_SUCCESS, "recover success, send password to your email");
			
			final Map<String, Object> root = new HashMap<String, Object>();
			root.put("user", user);
			
			final String username = user.getName();
			
			if(null!=user.getPassword() && user.getPassword().length()>2){
				user.setPassword(user.getPassword());
			}
			new Thread(){
				@Override
				public void run() {
					EmailTools.sendMail("recovery", "Congratulations! Your password is found", root, username);
				}
			}.start();
		}
		
        return "recoverPassword";
    }
	
	@RequestMapping(value="/logout")
	public String logout(Model model,HttpServletRequest request,HttpServletResponse response){
		request.getSession().invalidate();
		model.addAttribute(LOGOUT_ACTION,Boolean.TRUE.toString());
		Cookie[] cookies = request.getCookies();
		if(null!=cookies){
			for (Cookie  cookie: cookies) {
				if(COOKIE_ACCOUNT.equals(cookie.getName())){
					cookie = new Cookie(COOKIE_ACCOUNT, EMPTY_STR);
					cookie.setPath("/");
					cookie.setMaxAge(30*24*60*60);
					response.addCookie(cookie);
				}
			}
		}
		return "redirect:"+getSiteView().getHost(); 
	}
	
	
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login2(Model model,HttpServletRequest request,HttpServletResponse response){
		
		/*User user = retrieveUserNameAndPWDFromCookie(request.getCookies());
		
		if(user!=null){
			model.addAttribute(LOGIN_USER_NAME,user.getEmail());
			model.addAttribute(LOGIN_PWD,user.getPassword());
		}*/
		
		return "login";
		
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(Model model,HttpServletRequest request,HttpServletResponse response){
		
		String landingpage = null;
			try {
				landingpage = URLDecoder.decode(request.getParameter(LOGIN_LANDING_PAGE_PARAM),"utf-8");
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
			
			String userID = request.getParameter(LOGIN_USER_NAME);
			String pwd = request.getParameter(LOGIN_PWD);
			String rememberAccount = request.getParameter(REMEMBER_ID);
			
			User loginUser = null;
			
			if(StringUtils.isBlank(landingpage)){
				landingpage = getSiteView().getHost();
			}
			
			try {
				if(loginUser == null){
					User user = new User();
					user.setEmail(userID.trim());
					user.setPassword(pwd);
					loginUser = ServiceFactory.getService(UserService.class).validateUser(user);
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			
			if(null!=loginUser){
				model.addAttribute(USER_INFO, loginUser);
				request.getSession().setAttribute(USER_INFO,loginUser);
				
				if(TRUE.equals(rememberAccount)){
					Cookie cookie = new Cookie(COOKIE_ACCOUNT, Utils.OBJ.getEncryString(loginUser.getEmail()+USER_NAME_PWD_SPLIT+loginUser.getPassword()));
					cookie.setMaxAge(30*24*60*60);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
				
				return "redirect:"+landingpage;
			}else{
				getUserView().getErr().put(USER_ACCOUNT_ERROR, "User account/password invalid!");
			}
			
			try {
				landingpage = URLEncoder.encode(landingpage,"utf-8");
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
			
			if(StringUtils.isNotBlank(landingpage)){
				getUserView().setRequestPage(landingpage);
			}
			
			model.addAttribute(LOGIN_USER_NAME, userID);
		
		return "login";
	}
	
	@RequestMapping(value="/updateShoppingCart", params="action=increaseItemToCart")
	public String updateShoppingCart1(HttpServletResponse response,@RequestParam("item") String itemID) throws IOException{
		
		Map<String, String> rs = updateCart(itemID,1,false);
		
		JSONObject jsonObject = JSONObject.fromObject(rs);
		
		response.getWriter().print(jsonObject);
		return null;
	}
	
	@RequestMapping(value="/updateShoppingCart", params="action=decreaseItemToCart")
	public String updateShoppingCart2(HttpServletResponse response,@RequestParam("item") String itemID) throws IOException{
		
		Map<String, String> rs = updateCart(itemID,-1,false);
		
		JSONObject jsonObject = JSONObject.fromObject(rs);
		
		response.getWriter().print(jsonObject);
		
		return null;
	}
	
	@RequestMapping(value="/updateShoppingCart", params="action=removeItemToCart")
	public String updateShoppingCart3(HttpServletResponse response,@RequestParam("item") String itemID) throws IOException{
		
		Map<String, String> rs = updateCart(itemID,0,true);

		JSONObject jsonObject = JSONObject.fromObject(rs);
		
		response.getWriter().print(jsonObject);
		
		return null;
	}
	
	@RequestMapping(value="/updateShoppingCart", params="action=applyCoupon")
	public String updateShoppingCart4(HttpServletResponse response,@RequestParam("couponID") String couponID) throws IOException{
		
		couponID = couponID.trim();
		getUserView().getCart().getOrder().setCouponCode(couponID);
		
		Map<String, String> rs = updateCart(null,0,false);
		
		JSONObject jsonObject = JSONObject.fromObject(rs);
		
		response.getWriter().print(jsonObject);
		
		return null;
	}
	
	
	@RequestMapping(value="/updateShoppingCart", params="action=updateCustomerMsg")
	public String updateShoppingCart5(HttpServletResponse response,@RequestParam("order_msg") String msg) throws IOException{
		
		msg = msg.trim();
		
		Map<String, String> rs = new HashMap<String, String>();
		
		if(msg.length() > 500){
			rs.put("orderMsg", "No more than 500 charactors");
		}else{
			getUserView().getCart().getOrder().setCustomerMsg(msg);
			persistantCart();
		}
		
		JSONObject jsonObject = JSONObject.fromObject(rs);
		
		response.getWriter().print(jsonObject);
		
		return null;
	}
	
	@RequestMapping(value="/aliPaySyncResults")
	public String aliPaySyncResults(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException{
		try {
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<String,String>();
			Map requestParams = request.getParameterMap();
			for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参�?//
			//商户订单�?

			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			
			logger.info("out_trade_no:" + out_trade_no);
			
			//付款总金�?

			String total_fee = request.getParameter("total_fee");

			//币种

			String currency = request.getParameter("currency");

			//外币金额
			String forex_total_fee = request.getParameter("forex_total_fee");
			
			float fee = 0f;
			
			try {
				fee = Float.valueOf(total_fee);
			} catch (Exception e) {
				logger.warn("total_fee:" + total_fee);
			}
			
			if(fee < 1){
				try {
					fee = Float.valueOf(forex_total_fee);
				} catch (Exception e) {
					logger.warn("forex_total_fee:" + forex_total_fee);
				}
			}
			
			Float currencyRate = getSiteView().getCurrencies().get(currency);
			
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参�?//
			
			//计算得出通知验证结果
			boolean verify_result = AlipayNotify.verify(params);
			
			if(verify_result){//验证成功
				logger.info("out_trade_no="+out_trade_no+",currency="+currency+",fee="+fee+", currencyRate="+currencyRate+"###########");
				Order order = ServiceFactory.getService(OrderService.class).getOrderById(out_trade_no);
				if(order != null 
						&& order.getCurrency().equals(currency)
						&& null != currencyRate
						&& currencyRate*fee + 1 > order.getTotalPrice() + order.getDePrice() - order.getCouponCutOff()
						){
					ServiceFactory.getService(OrderService.class).saveOrder(order, OrderStatus.PAID.toString());
					
					logger.info("out_trade_no:"+ out_trade_no + "is paid");
				}
				//////////////////////////////////////////////////////////////////////////////////////////
				//请在这里加上商户的业务逻辑程序代码
				//——请根据您的业务逻辑来编写程序（以下代码仅作参考）—�?
				
				//判断是否在商户网站中已经做过了这次通知返回的处�?
					//如果没有做过处理，那么执行商户的业务程序
					//如果有做过处理，那么不执行商户的业务程序
				
				//该页面可做页面美工编�?
				//out.println("验证成功");

				//——请根据您的业务逻辑来编写程序（以上代码仅作参考）—�?

				//////////////////////////////////////////////////////////////////////////////////////////
				
				return "redirect:/uc/orderDetails?id="+order.getName();
				
			}else{
				//该页面可做页面美工编�?
				//out.println("验证失败");
			}
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return null;
		}
		
		response.getWriter().print("failed");
		
		return null;
	}
	
	

	@RequestMapping("globebillPayRs")
	public String globebillPayRs(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException{

		
		String orderAmount = request.getParameter("orderAmount");
		String orderStatus = request.getParameter("orderStatus");
		String orderNo = request.getParameter("orderNo");
		String currency = request.getParameter("orderCurrency");
		
		String orderInfo = request.getParameter("orderInfo");
		
		Order order = ServiceFactory.getService(OrderService.class).getOrderById(orderNo);
		
		
		
		logger.info(String.format(">>>>>>>>>>>>>>>>Recive Money orderNo=%1$2s, orderStatus = %2$2s, orderAmount=%3$2s, currency=%4$2s, orderInfo=currency=%5$2s", orderNo, orderStatus, orderAmount, currency, orderInfo));
		
		float orderRealAmount = Float.parseFloat(new NumberFormat("##0.##").getNumberFormat().format(
									getSiteView().getCurrencies().get(order.getCurrency())*
									(order.getTotalPrice() + order.getDePrice() - order.getCouponCutOff())))
									- 1;

		if(orderRealAmount <= Float.parseFloat(orderAmount)
				&&order.getCurrency().equals(currency)
				&&orderStatus.equalsIgnoreCase("1")){
			try{
				Coupon coupon = ServiceFactory.getService(CouponService.class).getCouponByCode(order.getCouponCode());
				coupon.setUsedCount(coupon.getUsedCount()+1);
				ServiceFactory.getService(CouponService.class).saveCoupon(coupon);
				orderInfo = "Successful";
			}catch(Exception e){
				logger.info(e.getMessage(),e);
			}
			
			ServiceFactory.getService(OrderService.class).saveOrder(order, OrderStatus.PAID.getValue());
		}else{
			orderInfo = "Failed";	
			logger.info(">>>>>>>>>>>>>>>>>>>NOT enough mony>>>>>>>>>>>>>>>>>>>>>>");
		}
		logger.info("order.getAddressType():"+order.getAddressType());
		
		model.addAttribute("tradeInfo", orderInfo);
		model.addAttribute(Constants.PROCESSING_ORDER, order);
		
		return "Credit-card-Rs";
	}
	
	@RequestMapping(value="/yoursPayResults")
	public String yoursPayResults(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		String MD5key = "YNWNUrlJ";                                     /** <非空>--密钥. 从网店系统中获取�?*/
		
		String BillNo = request.getParameter("BillNo");                 /** <非空>--订单�? 从yourspay服务器返�?**/
		
		String Currency = request.getParameter("Currency");             /** <非空>--通道参数. 从yourspay服务器返�?**/
		
		String Amount = request.getParameter("Amount");                 /** <非空>--金额. 从yourspay服务器返�?*/

		String CurrencyCode = request.getParameter("CurrencyCode");     /** <非空>--币种. 从yourspay服务器返�?*/
		
		String Succeed = request.getParameter("Succeed");               /** <非空>--支付状�?从yourspay服务器返�?"0"表示支付失败�?1"表示支付成功,"2"表示待处�? **/
		
		String Result = request.getParameter("Result");                 /** <非空>--支付结果. 从yourspay服务器返�?*/
		
		String MD5info = request.getParameter("MD5info");               /** <非空>--取得的MD5校验信息. 从yourspay服务器返�?*/
		
		String Remark = request.getParameter("Remark");                 /** <非空>--备注. 从yourspay服务器返�?*/
		
		StringBuilder md5src = new StringBuilder();
		md5src = md5src.append(BillNo).append(Currency).append(Amount).append(Succeed).append(MD5key);   /** <非空>--参数组合.组合只能以这个顺�?位置不能颠�?**/
		
		String md5sign = Encrypt.MD5(md5src.toString()).toUpperCase();  /** <非空>--对组合参数进行md5加密**/
		
		OrderService orderService = ServiceFactory.getService(OrderService.class);
		
		Order order = orderService.getOrderById(BillNo);

		/** 对返回信息进行判�?然后把支付结果显示在返回页面中，并更新网店后台的订单状�? **/
		if(MD5info == md5sign && Succeed == "1"){
			 // 支付结果�?支付成功(Payment Result: Success)�?
			 // 更新网店后台的订单状态为:支付成功(Success)�?
			orderService.saveOrder(order, OrderStatus.PAID.toString());
			
		}else if(MD5info == md5sign && Succeed == "2"){  
			 // 支付结果�?支付待处�?Payment Result:Processing)
			 // 更新网店后台的订单状态为:待处�?Processing)�?
			orderService.saveOrder(order, OrderStatus.PENDING.toString());
		}else if(MD5info == md5sign && Succeed == "0"){ 
			// 支付结果�?支付失败(Payment Result: Fail)�?
			// 更新网店后台的订单状态为:支付失败(Fail)�?
			orderService.saveOrder(order, OrderStatus.PENDING.toString());
		}else{
			// 支付结果�?数据校验失败(Payment Result: Data Authentication Failed)�?
			// 更新网店后台的订单状态为：支付失�?Fail)�?
			orderService.saveOrder(order, OrderStatus.FAILD.toString());
		}
		
		return "forward:/uc/orderDetails?id="+order.getName();
	}
	
	private Map<String,String> updateCart(String itemID,int amount,boolean isRemove){
		ShoppingCart cart = getUserView().getCart();
		Map<String, String> rs = new HashMap<String, String>();
		
		/*Order order = cart.getOrder();
		
		Coupon coupon = ServiceFactory.getService(CouponService.class).getCouponByCode(order.getCouponCode());
		
		if(null!=coupon){
			if(coupon.getMinexpend() > order.getTotalPrice()){
				rs.put("couponFeedbackErr", "Cannot not apply in order less than USD " +  coupon.getMinexpend() +"'" );
			}else if((coupon.isOnetime()&&coupon.getUsedCount()<1)||!coupon.isOnetime()){
				float cutOff = 0f;
				order.setCouponCode(coupon.getCode());
				
				if(!coupon.isCutOff()){
					cutOff = coupon.getValue();
					order.setCouponCutOff(cutOff);
				}else{
					cutOff = coupon.getValue() * order.getTotalPrice();
					order.setCouponCutOff(cutOff);
				}
				rs.put("couponFeedbackSuc", "Apply successfully");
			}
		}else{
			rs.put("couponFeedbackErr", "Invalid coupon");
		}*/
		
		
		if(null!=itemID){
			if(isRemove){
				cart.remove(itemID);
				if(CollectionUtils.isEmpty(cart.getOrder().getItems())){
					cart.getOrder().setCustomerMsg("");
				}
				persistantCart();
				rs.put("itemID", itemID);
			}else{
				cart.updateCart(itemID, amount);
				persistantCart();
				for(OrderItem item : cart.getOrder().getItems()){
					if(item.getName().equals(itemID)){
						rs.put("itemQTY", Utils.toNumber(item.getQuantity()));
						rs.put("itemAmount", Utils.toNumber(item.getFinalPrice() * item.getQuantity()*getUserView().getCurrencyRate()));
					}
				}
			}
		}else{
			persistantCart();
		}
		
		
		rs.put("subTotal", Utils.toNumber(cart.getOrder().getTotalPrice()*getUserView().getCurrencyRate()));
		
		rs.put("coupon", "-"+Utils.toNumber(cart.getOrder().getCouponCutOff()*getUserView().getCurrencyRate()));
		
		rs.put("grandTotal", Utils.toNumber((cart.getOrder().getTotalPrice()-cart.getOrder().getCouponCutOff())*getUserView().getCurrencyRate()));
		
		rs.put("itemCount", cart.getItemCount()+"");
		
		return rs;
	}
	
	
	@RequestMapping("/checkorder")
	public String PaypalRS(HttpServletRequest request, HttpServletResponse response)
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
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));
			String res = in.readLine();
			in.close();
			
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
			
			if ("VERIFIED".equals(res)) {
				Order order = ServiceFactory.getService(OrderService.class).getOrderById(itemName);
				logger.info(">>>>>>>>>>>>>>>>>>>VERIFIED>>>>>>>>>>>>>>>>>>>>>>");
				if(null!=order){
					order.setCustomerEmail(payerEmail);
					logger.info(">>>>>>>>>>>>>>>>>>>paymentAmount:"+paymentAmount+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>paymentCurrency:"+paymentCurrency+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>receiverEmail:"+receiverEmail+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>itemNumber:"+quantity+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>(order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff()):"+(order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff())+">>>>>>>>>>>>>>>>>>>>>>");
					
					logger.info(">>>>>>>>>>>>>>>>>>>receiverEmail.equalsIgnoreCase(ACCOUNT):"+receiverEmail.equalsIgnoreCase(Constants.PAYPAL_ACCOUNT)+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>itemNumber.equals('1'):"+quantity.equals("1")+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>order.getCurrency().equals(paymentCurrency):"+order.getCurrency().equals(paymentCurrency)+">>>>>>>>>>>>>>>>>>>>>>");
					logger.info(">>>>>>>>>>>>>>>>>>>receiverEmail.equalsIgnoreCase(ACCOUNT):"+receiverEmail.equalsIgnoreCase(Constants.PAYPAL_ACCOUNT)+">>>>>>>>>>>>>>>>>>>>>>");
					
					
					float rate = getSiteView().getCurrencies().get(order.getCurrency());
					
					logger.info(">>>>>>>>>>>>>>>>>>>((order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff())*rate- 1) <= Float.parseFloat(paymentAmount):"+(((order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff())*rate- 1)  <= Float.parseFloat(paymentAmount))+">>>>>>>>>>>>>>>>>>>>>>");
					if(((order.getTotalPrice()+order.getDePrice() - order.getCouponCutOff())*rate- 1) <= Float.parseFloat(paymentAmount)
							&&order.getCurrency().equals(paymentCurrency)
							&&receiverEmail.equalsIgnoreCase(Constants.PAYPAL_ACCOUNT)
							&&quantity.equals("1")){
						//order.setStatus(OrderStatus.PAID.getValue());
						Map<String,Object> root = new HashMap<String,Object>(); 
						root.put("order", order);
						root.put("currencyRate", rate);
						//EmailTools.sendMail("paid2", "Order Received and Payment Confirmation", root,order.getCustomerEmail());
						try{
							Coupon coupon = ServiceFactory.getService(CouponService.class).getCouponByCode(order.getCouponCode());
							if(null != coupon){
								coupon.setUsedCount(coupon.getUsedCount()+1);
								ServiceFactory.getService(CouponService.class).saveCoupon(coupon);
							}
						}catch(Exception e){
							logger.info(e.getMessage(),e);
						}
					}else{
						logger.info(">>>>>>>>>>>>>>>>>>>NOT enough mony>>>>>>>>>>>>>>>>>>>>>>");
					}
					logger.info("order.getAddressType():"+order.getAddressType());
					ServiceFactory.getService(OrderService.class).saveOrder(order, OrderStatus.PAID.getValue());
					
				}
				
				
			} else if ("INVALID".equals(res)) {
				logger.info("##############INVALID########################");
			} else {
				logger.info("##############ORTHER########################");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		 return null;
	}
}
