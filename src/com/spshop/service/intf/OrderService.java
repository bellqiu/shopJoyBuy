package com.spshop.service.intf;

import java.util.List;

import com.spshop.dao.intf.OrderDAO;
import com.spshop.model.Order;

public interface OrderService extends BaseService<Order,OrderDAO, Long>{
	public Order saveOrder(Order order, String status);
	public Order getOrderById(String id);
	public List<Order> getOrdersByUserId(long userId);
	public List<Order> getOrdersByUserId(long userId,int start);
	public int countOrdersByUserId(long userId);
	public Order getCartById(String id);
	public Order getUserCart(long userId);
	public Order getCartOrPendingOrderById(String id, long userid);
	
	public Order updateOrderItem(String orderSN, long uid , long itemId, int amount);
	public Order applyCoupon(String orderSN,long uid , String couponCode);
	public Order applyShippingAddress(String orderSN,long uid , long addressId);
	public Order applyBillingAddress(String orderSN, long uid ,long addressId);
	public Order applyShippingMethod(String orderSN, long uid ,String method);
}
