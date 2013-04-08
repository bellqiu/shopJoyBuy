package com.spshop.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.spshop.dao.intf.OrderDAO;
import com.spshop.model.Address;
import com.spshop.model.Country;
import com.spshop.model.Coupon;
import com.spshop.model.Order;
import com.spshop.model.OrderItem;
import com.spshop.model.User;
import com.spshop.model.enums.OrderStatus;
import com.spshop.service.AbstractService;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.AddressService;
import com.spshop.service.intf.CountryService;
import com.spshop.service.intf.CouponService;
import com.spshop.service.intf.OrderService;
import com.spshop.utils.Constants;
import com.spshop.utils.EmailTools;
import com.spshop.utils.Utils;

public class OrderServiceImpl extends AbstractService<Order,OrderDAO, Long> implements OrderService{
	public Order saveOrder(Order order, String status){
		order  = merge(order);
		if(null!=order.getUser()){
			User usr = getDao().getUserById(order.getUser().getId());
			if(null!=usr){
				order.setUser(usr);
			}
		}
		
		applyShippingMethod(order, order.getShippingMethod());
		
		if(order.getName() == null){
			order.setName(getOrderId());
		}
		
		if(null==order.getStatus()){
			order.setStatus(OrderStatus.ONSHOPPING.toString());
		}
		
		order.setUpdateDate(new Date());
		if(null==order.getCreateDate()){
			order.setCreateDate(new Date());
		}
		
		String couponCode = order.getCouponCode();
		if(StringUtils.isNotBlank(couponCode)){
			Coupon coupon = ServiceFactory.getService(CouponService.class).getCouponByCode(couponCode);
			if(null!=coupon && ((coupon.isOnetime()&&coupon.getUsedCount()<1)||!coupon.isOnetime())&&coupon.getMinexpend() <= order.getTotalPrice()){
				float cutOff = 0f;
				order.setCouponCode(coupon.getCode());
				if(!coupon.isCutOff()){
					cutOff = coupon.getValue();
					order.setCouponCutOff(cutOff);
				}else{
					cutOff = coupon.getValue() * order.getTotalPrice();
					order.setCouponCutOff(cutOff);
				}
			}else{
				order.setCouponCutOff(0);
				order.setCouponCode(null);
			}
		}

		
			
			
			
			if(OrderStatus.PAID.toString().equals(status) && !status.equals(order.getStatus())){
				
				final Map<String,Object> root = new HashMap<String,Object>(); 
				final Order o = order;
				root.put("order", order);
				
				Map<String,Float> currencies = Utils.getCurrencies();
				
				float currencyRate = 1;
				
				if(!Constants.DEFAULT_CURRENCY.equalsIgnoreCase(order.getCurrency())){
					currencyRate = currencies.get(order.getCurrency());
				}
				root.put("currencyRate", currencyRate);
				
				new Thread(){
					public void run() {
						try{
							EmailTools.sendMail("paid2", "Order Received and Payment Confirmation", root,o.getUser().getEmail());
						}catch(Exception e){
							
						}
					};
				}.start();
			}else if(OrderStatus.SHIPPING.toString().equals(status) && !(status.equals(order.getStatus()))){
				
				final Map<String,Object> root = new HashMap<String,Object>(); 
				final Order o = order;
				root.put("order", order);
				
				Map<String,Float> currencies = Utils.getCurrencies();
				
				float currencyRate = 1;
				
				if(!Constants.DEFAULT_CURRENCY.equalsIgnoreCase(order.getCurrency())){
					currencyRate = currencies.get(order.getCurrency());
				}
				root.put("currencyRate", currencyRate);
				
				new Thread(){
					public void run() {
						try{
							EmailTools.sendMail("shipping", "Shipping notification - HoneyBuy", root,o.getUser().getEmail());
						}catch(Exception e){
							
						}
					};
				}.start();
			}
		
		order.setStatus(status);
		order = getDao().save(order);
		order = order.clone();
		
		return order;
	}

	@Override
	public Order getOrderById(String id) {
		String hql = "From Order as o where o.name = ? and o.status != 'ONSHOPPING'";
		@SuppressWarnings("unchecked")
		List<Object> cs = (List<Object>)getDao().queryByHQL(hql,0,999, id);
		if(null!=cs){
			for (Object object : cs) {
				return ((Order)object).clone();
			}
		}
		
		return null;
	}
	
	@Override
	public Order getCartById(String id) {
		
		long longId = 0;
		
		try {
			longId = Long.parseLong(id);
		} catch (NumberFormatException e) {
			return null;
		}
		
		String hql = "From Order as o where o.id = ? and o.status = 'ONSHOPPING' order by o.id desc";
		
		@SuppressWarnings("unchecked")
		List<Object> cs = (List<Object>)getDao().queryByHQL(hql,0,999, longId);
		if(null!=cs){
			for (Object object : cs) {
				return ((Order)object).clone();
			}
		}
		
		return null;
	}
	
	@Override
	public Order getCartOrPendingOrderById(String id, long userid) {
		
		
		String hql = "From Order as o where o.name = ? and o.user.id = ? and o.status in('ONSHOPPING','PENDING') order by o.id desc";
		
		@SuppressWarnings("unchecked")
		List<Object> cs = (List<Object>)getDao().queryByHQL(hql,0,999, id, userid);
		if(null!=cs){
			for (Object object : cs) {
				return ((Order)object).clone();
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Order> getOrdersByUserId(long userId) {
		String hql = "From Order as o where o.user.id = ? and o.status != 'ONSHOPPING' order by o.id desc";
		List<Order> orders= new ArrayList<Order>();
		List<Object> cs = (List<Object>)getDao().queryByHQL(hql,0,999, userId);
		
		if(null!=cs){
			for (Object object : cs) {
				orders.add(((Order)object).clone());
			}
		}
		return orders;
	}
	
	protected String getOrderId(){
		String id = "";
		Date today = new Date();
		int y= today.getYear()%100;
		int m = today.getMonth() + 1;
		int d = today.getDate();
		String sy = "";
		String sm = "";
		String sd = "";
		
		if(y<10){
			sy = "0"+y;
		}else{
			sy = ""+ y;
		}
		
		if(m<10){
			sm = "0"+m;
		}else{
			sm = ""+ m;
		}
		
		if(d<10){
			sd = "0"+d;
		}else{
			sd = ""+ d;
		}
		id = id + sy+ sm + sd +"-";
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (char)(new Random().nextInt(26)+65);
		id = id + (new Random().nextInt(99)+100);
		
		return id;
	}

	@Override
	public Order getUserCart(long userId) {
		
		String hql = "From Order as o where o.user.id = ? and o.status = 'ONSHOPPING' order by o.id desc";
		@SuppressWarnings("unchecked")
		List<Object> cs = (List<Object>)getDao().queryByHQL(hql, 0,999, userId);
		if(null!=cs){
			for (Object object : cs) {
				return ((Order)object).clone();
			}
		}
		
		return null;
	}

	@Override
	public List<Order> getOrdersByUserId(long userId, int start) {
		String hql = "From Order as o where o.user.id = ? and o.status != 'ONSHOPPING' order by o.id desc";
		
		int begin = (start -1)*Constants.PAGINATION_DEFAULT_20;
		
		if(begin < 0){
			begin = 0;
		}
		
		List<Order> orders= new ArrayList<Order>();
		List<Object> cs = (List<Object>)getDao().queryByHQL(hql,begin,Constants.PAGINATION_DEFAULT_20, new Long(userId));
		
		if(null!=cs){
			for (Object object : cs) {
				orders.add(((Order)object).clone());
			}
		}
		return orders;
	}

	@Override
	public int countOrdersByUserId(long userId) {
		
		String hql = "select count(o.id) From Order as o where o.user.id = ? and o.status != 'ONSHOPPING' order by o.id asc";
		
		List<Object> rs = (List<Object>)getDao().queryByHQL(hql,0,Constants.PAGINATION_DEFAULT_20, new Long(userId));
		
		int count = Integer.valueOf( rs.get(0).toString());
		
		return count%Constants.PAGINATION_DEFAULT_20==0 ? count/Constants.PAGINATION_DEFAULT_20 : count/Constants.PAGINATION_DEFAULT_20+1;
	}

	@Override
	public Order updateOrderItem(String orderSN,long uid , long itemId, int amount) {
		
		Order order = getCartOrPendingOrderById(orderSN, uid);
		order = merge(order);
		if(null != order){
			if(amount < 0 ){
				amount = 0;
			}
			if(null != order.getItems()){
				OrderItem mappedItem = null;
				for (OrderItem item : order.getItems()) {
					if(itemId == item.getId()){
						mappedItem = item;
					}
				}
				if(null != mappedItem){
					if(amount > 0){
						mappedItem.setQuantity(amount);
					}else if(order.getItems().size() > 1){
						order.getItems().remove(mappedItem);
					}
				}
			}
			
			if(null != order.getCouponCode()){
				
				order = applyCoupon(order, order.getCouponCode());
				
			}
			
			order = applyShippingMethod(order, order.getShippingMethod());
			
			order = save(order).clone();
		}
		
		return order;
	}
	
	private Order applyCoupon(Order order, String couponCode){
		if(null != order.getCouponCode()){
			Coupon coupon = ServiceFactory.getService(CouponService.class).getCouponByCode(order.getCouponCode());
			
			if(null!=coupon){
				if(coupon.getMinexpend() > order.getTotalPrice()){
					order.setCouponCode(null);
					order.setCouponCutOff(0f);
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
				}
			}
		}else{
			order.setCouponCode(null);
			order.setCouponCutOff(0f);
		}
		
		return order;
	}

	@Override
	public Order applyCoupon(String orderSN,long uid , String couponCode) {

		Order order = getCartOrPendingOrderById(orderSN, uid);
		
		if(null != order){
			order = merge(order);
			if(null != couponCode){
				order.setCouponCode(couponCode);
				applyCoupon(order, couponCode);
				
			}
		}
		
		order = save(order).clone();
		
		return order;
	}

	@Override
	public Order applyShippingAddress(String orderSN,long uid , long addressId) {
		Order order = getCartOrPendingOrderById(orderSN, uid);
		order = merge(order);
		Address address = ServiceFactory.getService(AddressService.class).getAddressById(addressId);
		if(null != order && null!= address){
			
			if(null != order.getCouponCode()){
				
				applyCoupon(order, order.getCouponCode());
				
			}
			
			if(null != address){
				
				//Country country = ServiceFactory.getService(CountryService.class).getCountryById(Long.valueOf(address.getCountry()));
				
				/*if(country.get){
				
					order.setDePrice(dePrice)
				
				}*/
				order.setShippingAddress(address);
				
				order = applyShippingMethod(order, order.getShippingMethod());
			}
			
			order = save(order).clone();
		}
		
		return order;
	}

	@Override
	public Order applyBillingAddress(String orderSN, long uid ,long addressId) {
		Order order = getCartOrPendingOrderById(orderSN, uid);
		order = merge(order);
		Address address = ServiceFactory.getService(AddressService.class).getAddressById(addressId);
		if(null != order && null!= address){
			order.setBillingAddress(address);
		}
		order = save(order).clone();
		return order;
	}

	@Override
	public Order applyShippingMethod(String orderSN, long uid, String method) {
		Order order = getCartOrPendingOrderById(orderSN, uid);
		order = merge(order);
		Address address = order.getShippingAddress();
		
		if(null != order && null!= address){
			
			if(null != order.getCouponCode()){
				
				applyCoupon(order, order.getCouponCode());
				
			}
			
			order = applyShippingMethod(order, method);
			
			order = save(order).clone();
		}
		
		return order;
	}
	
	private Order applyShippingMethod(Order order, String method){
		Address address = order.getShippingAddress();
		if(null != address && null!=method && order.getItems().size() > 0){
			Country country = ServiceFactory.getService(CountryService.class).getCountryById(Long.valueOf(order.getShippingAddress().getCountry()));
			if(Constants.SHIPPING_EXPEDITED.equals(method)){
				order.setShippingMethod(Constants.SHIPPING_EXPEDITED);
				order.setShippingMethod(Constants.SHIPPING_EXPEDITED);
			}else{
				order.setShippingMethod(Constants.SHIPPING_STANDARD);
				order.setShippingMethod(Constants.SHIPPING_STANDARD);
			}
			
			if(null != country && order.getTotalPrice() < country.getFreeAdDePrice()){
				if(order.getTotalPrice() < country.getFreeDePrice()){
					if(order.getShippingMethod() == Constants.SHIPPING_EXPEDITED){
						order.setDePrice(country.getAdDePrice());
					}else if(order.getShippingMethod() == Constants.SHIPPING_STANDARD){
						order.setDePrice(country.getDePrice());
					}
				}else if(order.getTotalPrice() < country.getFreeAdDePrice()){
					if(order.getShippingMethod() == Constants.SHIPPING_EXPEDITED){
						order.setDePrice(country.getAdDePrice());
					}else if(order.getShippingMethod() == Constants.SHIPPING_STANDARD){
						order.setDePrice(0f);
					}
				}
				
			}else{
				order.setDePrice(0f);
			}
		}else{
			order.setShippingMethod(null);
			order.setDePrice(0f);
		}
		
		return order;
	}
}
