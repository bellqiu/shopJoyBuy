package com.spshop.web.view;

import java.util.HashMap;
import java.util.Map;

import com.spshop.model.User;
import com.spshop.model.cart.ShoppingCart;

public class UserView {

	private float currencyRate;
	private String currencyCode;
	private ShoppingCart shoppingCart;
	private User loginUser;
	private String requestPage;
	private Map<String, String> msg = new HashMap<String, String>();
	private Map<String, String> err = new HashMap<String, String>();
	private ShoppingCart cart = null;
	private Map<String,Float> currencyRateMap = new HashMap<String,Float>();

	public float getCurrencyRate() {
		return currencyRate;
	}

	public void setCurrencyRate(float currencyRate) {
		this.currencyRate = currencyRate;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	public String getRequestPage() {
		return requestPage;
	}

	public void setRequestPage(String requestPage) {
		this.requestPage = requestPage;
	}

	public Map<String, String> getMsg() {
		return msg;
	}

	public void setMsg(Map<String, String> msg) {
		this.msg = msg;
	}

	public Map<String, String> getErr() {
		return err;
	}

	public void setErr(Map<String, String> err) {
		this.err = err;
	}

	public ShoppingCart getCart() {
		return cart;
	}

	public void setCart(ShoppingCart cart) {
		this.cart = cart;
	}

	public Map<String,Float> getCurrencyRateMap() {
		return currencyRateMap;
	}

	public void setCurrencyRateMap(Map<String,Float> currencyRateMap) {
		this.currencyRateMap = currencyRateMap;
	}
}
