package com.spshop.model;

import java.util.ArrayList;
import java.util.List;


public class OrderItem extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6303793659582817131L;

	private Product product;
	private int quantity;
	private float finalPrice;
	private List<UserOption> userOptions;
	private String lastArrivalDate;
	
	public OrderItem() {
	}
	
	public OrderItem(OrderItem item) {
		super(item);
	}
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(float finalPrice) {
		this.finalPrice = finalPrice;
	}
	public void setUserOptions(List<UserOption> userOptions) {
		this.userOptions = userOptions;
	}
	public List<UserOption> getUserOptions() {
		return userOptions;
	}
	
	public float getItemTotalPrice(){
		return getFinalPrice()*getQuantity();
	}

	public String getLastArrivalDate() {
        return lastArrivalDate;
    }

    public void setLastArrivalDate(String lastArrivalDate) {
        this.lastArrivalDate = lastArrivalDate;
    }

    public OrderItem clone() {
		OrderItem obj = null;
		obj = new OrderItem(this);
		if (this.product != null) {
			obj.product = (Product) this.product.clone();
		}
		obj.quantity = this.quantity;
		obj.finalPrice = this.finalPrice;
		obj.lastArrivalDate = this.lastArrivalDate;
		if (this.userOptions != null) {
			/* Does not have a clone() method */
			List<UserOption> options = new ArrayList<UserOption>();
			if(null!=this.userOptions){
				for (UserOption userOption : this.userOptions) {
					options.add(userOption.clone());
				}
			}
			obj.userOptions = options;
		}
		return obj;
	}
	
}
