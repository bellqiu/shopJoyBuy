package com.spshop.utils;

import static com.spshop.utils.Constants.COOKIE_ACCOUNT;
import static com.spshop.utils.Constants.CURRENCY;
import static com.spshop.utils.Constants.DEFAULT_CURRENCY;
import static com.spshop.utils.Constants.SHOPPINGCART;
import static com.spshop.utils.Constants.USER_INFO;
import static com.spshop.utils.Constants.USER_NAME_PWD_SPLIT;
import static com.spshop.utils.Constants.USER_PROFILE;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.spshop.admin.shared.ImageSize;
import com.spshop.cache.SCacheFacade;
import com.spshop.model.Category;
import com.spshop.model.Image;
import com.spshop.model.Order;
import com.spshop.model.Product;
import com.spshop.model.User;
import com.spshop.model.cart.ShoppingCart;
import com.spshop.model.enums.OrderStatus;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.OrderService;
import com.spshop.service.intf.UserService;
import com.spshop.web.interceptor.BootstrapDataFilterInterceptor;

public class Utils {

	private static Logger log = Logger.getLogger(Utils.class);
	private static final String ALGORITHM = "DES";
	private KeyGenerator keyGen;
	private SecretKey desKey;
	private Cipher cip;
	private byte[] cipherByte;
	
	public static Utils OBJ = new Utils();

	private Utils() {
		init("XAdadajkxsfdfskldfhsdfhhhasfasfaasdaqwerqweqwasasdazxczxczxcfvhdfghdf");
	}
	
	public static Map<String,Float> getCurrencies(){
		Map<String,Float> currencies = new HashMap<String, Float>();
		Properties cp = new Properties();
		try {
			
			cp.load(BootstrapDataFilterInterceptor.class.getResourceAsStream("/currency.properties"));
			for (Object currencyName : cp.keySet()) {
				try {
					float rate = Float.parseFloat(cp.get(currencyName).toString());
					currencies.put(currencyName.toString().trim(), rate);
				} catch (NumberFormatException e) {
					log.error(e.getMessage(), e);
				}
			}
		}catch(Exception e){
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		
		return currencies;
	}
	
	public void init(String str) {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			keyGen = KeyGenerator.getInstance(ALGORITHM);
			keyGen.init(new SecureRandom(str.getBytes()));
			desKey = keyGen.generateKey();
			cip = Cipher.getInstance(ALGORITHM);
		} catch (NoSuchAlgorithmException ex) {
			log.error(ex.getMessage());

		} catch (NoSuchPaddingException ex) {
			log.error(ex.getMessage());

		}
	}

	public String getEncryString(String expreStr) {
		byte[] cipByte = null;
		byte[] expreByte = null;
		String cipStr = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			expreByte = expreStr.getBytes("UTF8");
			cipByte = dataEncryptor(expreByte);
			cipStr = base64en.encode(cipByte);
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			throw new RuntimeException("des", e);
		} finally {
			cipByte = null;
			expreByte = null;
			base64en = null;
		}
		return cipStr;
	}

	public String getDecry(String cipStr) {
		byte[] cipByte = null;
		byte[] expreByte = null;
		String expreStr = "";
		BASE64Decoder base64De = new BASE64Decoder();
		try {
			cipByte = base64De.decodeBuffer(cipStr);
			expreByte = dataDecryptor(cipByte);
			expreStr = new String(expreByte, "UTF8");
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new RuntimeException("des!", e);
		} finally {
			cipByte = null;
			expreByte = null;
			base64De = null;
		}
		return expreStr;
	}

	public byte[] dataEncryptor(byte[] expreByte) {
		try {
			cip.init(Cipher.ENCRYPT_MODE, desKey);
			cipherByte = cip.doFinal(expreByte);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return cipherByte;
	}

	public byte[] dataDecryptor(byte[] buff) {
		try {
			cip.init(Cipher.DECRYPT_MODE, desKey);
			cipherByte = cip.doFinal(buff);
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return cipherByte;

	}
	
	public static ShoppingCart retrieveShoppingCart(HttpServletRequest request, User user) {
		
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute(SHOPPINGCART);
		String currency = (String) request.getSession().getAttribute(CURRENCY);
		if(StringUtils.isEmpty(currency)){
			currency = DEFAULT_CURRENCY;
		}
		
		if(null == cart){
			Order order =  retrieveUserCarOrder(user);
			
			cart = new ShoppingCart(order);
			
			request.getSession().setAttribute(SHOPPINGCART, cart);
			
		}
		
		if(null!=user && cart.getOrder().getUser()==null){
				if(cart.getItemCount() > 0){
					cart.getOrder().setUser(user);
					Order order = ServiceFactory.getService(OrderService.class).saveOrder(cart.getOrder(), OrderStatus.ONSHOPPING.toString());
					cart.setOrder(order);
				}else{
					cart = new ShoppingCart(retrieveUserCarOrder(user));
				}
		}else if(null!=user && cart.getOrder().getUser().getId()!=user.getId()){
			
			Order order =retrieveUserCarOrder(user);
			cart = new ShoppingCart(order);
			order = ServiceFactory.getService(OrderService.class).saveOrder(cart.getOrder(), OrderStatus.ONSHOPPING.toString());
		}
		
		if(StringUtils.isBlank(cart.getOrder().getCurrency())){
			cart.getOrder().setCurrency(currency);
		}else if(StringUtils.isEmpty(request.getParameter(CURRENCY)) || StringUtils.isNotBlank(currency)){
			request.getSession().setAttribute(CURRENCY, currency);
		}
		
		return cart;
	}
	
	private static Order retrieveUserCarOrder(User user){
		Order order = null;
		if(null != user){
			order = ServiceFactory.getService(OrderService.class).getUserCart(user.getId());
		}
		
		if(null == order){
			order = new Order();
		}
		
		return order;
	}
	
	
	public static User retrieveUser(HttpServletRequest request){
		User user = (User) request.getSession().getAttribute(USER_INFO);
		String userProfile = request.getParameter(USER_PROFILE);
		

		if(null==user && StringUtils.isNotBlank(userProfile)){
			user = retrieveUserFromProfileString(userProfile);
		}
		
		/*if(null==user){
			user = retrieveUserFromCookies(request.getCookies());
			 request.getSession().setAttribute(USER_INFO, user);
		}*/

		return user;
	}
	
	
	private static User retrieveUserFromProfileString(String userProfile) {
		String value = Utils.OBJ.getDecry(userProfile);
		String[] mixUser = value.split(USER_NAME_PWD_SPLIT);
		User user = new User();
		user.setEmail(mixUser[0]);
		user.setPassword(mixUser[1]);
		return ServiceFactory.getService(UserService.class).validateUser(user);
	}

	public static User retrieveUserFromCookies(Cookie cookies[]){
		try {
			if(null!=cookies){
				for (Cookie  cookie: cookies) {
					if(COOKIE_ACCOUNT.equals(cookie.getName())){
						String value = Utils.OBJ.getDecry(cookie.getValue());
						String[] mixUser = value.split(USER_NAME_PWD_SPLIT);
						User user = new User();
						user.setEmail(mixUser[0]);
						user.setPassword(mixUser[1]);
						return ServiceFactory.getService(UserService.class).validateUser(user);
					}
				}
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}
	
	public static String toNumber(double number){
		NumberFormat format = new DecimalFormat(",##0.##");
		return format.format(number);
	}

	 public static List<Float> figureOutProductsSize(List<Product> products){
	        List<Float> size = new ArrayList<Float>();
	        Float maxW = 0f;
	        Float maxH = 0f;
	        Float minH = 600f;
	        Float tempW = 0f;
	        Float tempH = 0f;
	        Image img;
	        for (Product p : products) {
	            img = p.getImages().get(0);
	            tempW = Float.valueOf(ImageTools.getXY(img.getSizeType(), ImageSize.valueOf("LARGE_SIZE"))[0]);
	            tempH = Float.valueOf(ImageTools.getXY(img.getSizeType(), ImageSize.valueOf("LARGE_SIZE"))[1]);
	            if(tempH > maxH){
	                maxH = tempH;
	                maxW = tempW;
	            }
	            if (tempH <= minH) {
	                minH = tempH;
	            }
	        }
	        size.add(minH);
	        size.add(maxH);
	        size.add(maxW);
	        return size;
	    }
	 
	    /**
	     * Find category from list in cache
	     * 
	     * @param categories
	     *            The target list for finding
	     * @param catName
	     *            The category name
	     * @return Searching result
	     */
	    public static Category searchCategory(List<Category> categories, String catName) {
	        Category result = null;
	        
	        for (Category category : categories) {
	            if (category.getName().equals(catName)) {
	                result = category;
	                break;
	            } else if (category.getSubCategories().size() != 0){
	                result = searchCategory(category.getSubCategories(), catName);
	                if (result != null)
	                break;
	            }
	        }
	        
	        return result;
	    }
	    
	    public static Category populateCategoryForCategoryPage(String categoryName) {
	        List<Category> categories = SCacheFacade.getTopCategories();
	        
	        return searchCategory(categories, categoryName);
	    }
	    
	    public static Category getSecondaryLayerCategory(String cName){
	    	List<Category> categories = SCacheFacade.getTopCategories();
	    	Category c = searchCategory(categories, cName);
	    	Category sc = c;
	    	while (c.getParent() != null) {
	    		sc = c;
				c = c.getParent();
			}
	    	return sc;
	    }
	    
	    public static Category getCategoryByName(String cName){
	    	List<Category> categories = SCacheFacade.getTopCategories();
	    	return searchCategory(categories, cName);
	    }
}
