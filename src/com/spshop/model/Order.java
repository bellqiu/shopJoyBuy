package com.spshop.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Order extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8785205423133606769L;
	private String customerName;
	private String customerCompany;
	private String customerAddress;
	private String customerAddress2;
	private String customerTelephone;
	private String customerCountry;
	private String customerEmail;
	private String customerZipcode;
	private User user;
	private String deliverCode;
	private String deliverCompany;
	private String deliverPhone;
	private List<OrderItem> items;
	//private List<Coupon> coupons;
	private String orderType;
	private float totalPrice;
	private String status;
	private String addressType;
	private String city;
	private float dePrice;
	private String currency;
	private String customerMsg;
	private String state;
	private long country;
	
	
	private String bCustomerName;
	private String customGender;
	private String bCustomGender;
	private String bCustomerAddress;
	private String bCustomerAddress2;
	private String bCity;
	private String bCustomerZipcode;
	private String bstate;
	private String bPhone;
	private long bcountry;
	private String customerBCountry;
	
	private String shippingMethod;
	
	private float couponCutOff;
	
	private String couponCode;
	
	private boolean billingSameAsPrimary = true;
	
	private String traceInfo;
	
	private float height;
	private float weight;
	private float age;
	private String shoulder;
	private String chest;
	private String stomch;
	private String posture;
	private float shirtNeck;
	private float jacketShirtLenght;
	private float chestSize;
	private float stomachSize;
	private float jacketHips;
	private float shoulderSize;
	private float sleeveLength;
	private float bicepSize;
	private float wristSize;
	private float pantsLength;
	private float waist;
	private float crotch;
	private float thighSize;
	private float kneeSize;
	
	private boolean suitMeasurement;
	
	public Order() {
		if(null!=getName()){
			setName(getOrderId());
		}
	}
	
	public Order(Order order) {
		super(order);
		if(null==getName()){
			setName(getOrderId());
		}
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerCompany() {
		return customerCompany;
	}

	public void setCustomerCompany(String customerCompany) {
		this.customerCompany = customerCompany;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerTelephone() {
		return customerTelephone;
	}

	public void setCustomerTelephone(String customerTelephone) {
		this.customerTelephone = customerTelephone;
	}


	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerZipcode() {
		return customerZipcode;
	}

	public void setCustomerZipcode(String customerZipcode) {
		this.customerZipcode = customerZipcode;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDeliverCode() {
		return deliverCode;
	}

	public void setDeliverCode(String deliverCode) {
		this.deliverCode = deliverCode;
	}

	public String getDeliverCompany() {
		return deliverCompany;
	}

	public void setDeliverCompany(String deliverCompany) {
		this.deliverCompany = deliverCompany;
	}

	public String getDeliverPhone() {
		return deliverPhone;
	}

	public void setDeliverPhone(String deliverPhone) {
		this.deliverPhone = deliverPhone;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	/*public void setCoupons(List<Coupon> coupons) {
		this.coupons = coupons;
	}

	public List<Coupon> getCoupons() {
		return coupons;
	}*/

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setCustomerCountry(String customerCountry) {
		this.customerCountry = customerCountry;
	}

	public String getCustomerCountry() {
		return customerCountry;
	}

	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public float getTotalPrice() {
		return totalPrice;
	}
	
	public String getCfirstName(){
		if(null!=customerName&&customerName.split(",").length>1){
			return customerName.split(",")[0];
		}
		return "";
	}
	
	public String getClastName(){
		if(null!=customerName&&customerName.split(",").length>1){
			return customerName.split(",")[1];
		}
		return "";

	}
	
	public String getBfirstName(){
		if(null!=bCustomerName&&bCustomerName.split(",").length>1){
			return bCustomerName.split(",")[0];
		}
		return "";
	}
	
	public String getBlastName(){
		if(null!=bCustomerName&&bCustomerName.split(",").length>1){
			return bCustomerName.split(",")[1];
		}
		return "";

	}
	
	public Address getPrimaryAddress(){
		return new Address(customerName,customerAddress,customerAddress2,city,state,(int)country,customerZipcode,customerTelephone);
	}
	
	public void setPrimaryAddress(Address address){
		 setCustomerName(address.getFullName());
		 setCustomerAddress(address.getAddress1());
		 setCustomerAddress2(address.getAddress2());
		 setCity(address.getCity());
		 setState(address.getStateProvince());
		 setCountry(address.getCountry());
		 setCustomerZipcode(address.getPostalCode());
		 setCustomerTelephone(address.getPhone());
	}
	
	public void setBillingAddress(Address address){
		 setBcustomerName(address.getFullName());
		 setBcustomerAddress(address.getAddress1());
		 setBcustomerAddress2(address.getAddress2());
		 setBcity(address.getCity());
		 setBstate(address.getStateProvince());
		 setBcountry(address.getCountry());
		 setBcustomerZipcode(address.getPostalCode());
		 setBphone(address.getPhone());
	}
	
	public Address getBillingAddress(){
		return new Address(bCustomerName,bCustomerAddress,bCustomerAddress2,bCity,bstate,(int)bcountry,bCustomerZipcode,bPhone);
	}
	
	public Order clone() {
		Order obj = null;
		obj = new Order(this);
		if (this.customerName != null) {
			/* Does not have a clone() method */
			obj.customerName = this.customerName;
		}
		if (this.customerCompany != null) {
			/* Does not have a clone() method */
			obj.customerCompany = this.customerCompany;
		}
		if (this.customerAddress != null) {
			/* Does not have a clone() method */
			obj.customerAddress = this.customerAddress;
		}
		if (this.customerTelephone != null) {
			/* Does not have a clone() method */
			obj.customerTelephone = this.customerTelephone;
		}
		if (this.customerCountry != null) {
			/* Does not have a clone() method */
			obj.customerCountry = this.customerCountry;
		}
		if (this.customerEmail != null) {
			/* Does not have a clone() method */
			obj.customerEmail = this.customerEmail;
		}
		if (this.customerZipcode != null) {
			/* Does not have a clone() method */
			obj.customerZipcode = this.customerZipcode;
		}
		if (this.user != null) {
			obj.user = (User) this.user.clone();
		}
		if (this.deliverCode != null) {
			/* Does not have a clone() method */
			obj.deliverCode = this.deliverCode;
		}
		if (this.deliverCompany != null) {
			/* Does not have a clone() method */
			obj.deliverCompany = this.deliverCompany;
		}
		if (this.deliverPhone != null) {
			/* Does not have a clone() method */
			obj.deliverPhone = this.deliverPhone;
		}
		
		if (this.orderType != null) {
			/* Does not have a clone() method */
			obj.orderType = this.orderType;
		}
		
		if (this.addressType != null) {
			/* Does not have a clone() method */
			obj.addressType = this.addressType;
		}
		
		if (this.customerAddress2 != null) {
			/* Does not have a clone() method */
			obj.customerAddress2 = this.customerAddress2;
		}
		
		if (this.customerMsg != null) {
			/* Does not have a clone() method */
			obj.customerMsg = this.customerMsg;
		}
		
		if (this.traceInfo != null) {
            /* Does not have a clone() method */
            obj.traceInfo = this.traceInfo;
        }
		
		obj.dePrice = this.dePrice;
		obj.country = this.country;
		obj.bcountry = this.bcountry;
		obj.billingSameAsPrimary = this.billingSameAsPrimary;
		obj.couponCutOff = this.couponCutOff;
		
		obj.chest = this.chest;
		obj.chestSize = this.chestSize;
		obj.height = this.height;
		obj.weight = this.weight;
		obj.age = this.age;
		obj.shoulder = this.shoulder;
		obj.stomch = this.stomch;
		obj.posture = this.posture;
		obj.shirtNeck = this.shirtNeck;
		obj.jacketShirtLenght = this.jacketShirtLenght;
		obj.stomachSize = this.stomachSize;
		obj.jacketHips = this.jacketHips;
		obj.shoulderSize = this.shoulderSize;
		obj.sleeveLength = this.sleeveLength;
		obj.bicepSize = this.bicepSize;
		obj.wristSize = this.wristSize;
		obj.pantsLength = this.pantsLength;
		obj.waist = this.waist;
		obj.crotch = this.crotch;
		obj.thighSize = this.thighSize;
		obj.kneeSize = this.kneeSize;
		obj.suitMeasurement = this.suitMeasurement;
		
		
		if (this.city != null) {
			/* Does not have a clone() method */
			obj.city = this.city;
		}
		
		if (this.customerBCountry != null) {
			/* Does not have a clone() method */
			obj.customerBCountry = this.customerBCountry;
		}
		
		if (this.currency != null) {
			/* Does not have a clone() method */
			obj.currency = this.currency;
		}
		

		if (this.couponCode != null) {
			/* Does not have a clone() method */
			obj.couponCode = this.couponCode;
		}
		
		if (this.items != null) {
			List<OrderItem> items = new ArrayList<OrderItem>();
			
			if(null!=this.items){
				for (OrderItem orderItem : this.items) {
					items.add(orderItem.clone());
				}
			}
			
			obj.items = items;
		}
		obj.totalPrice = this.totalPrice;
		if (this.status != null) {
			obj.status = this.status;
		}
		
		
		if (this.bCustomerName != null) {
			obj.bCustomerName = this.bCustomerName;
		}
		
		if (this.customGender != null) {
			obj.customGender = this.customGender;
		}
		
		if (this.bCustomGender != null) {
			obj.bCustomGender = this.bCustomGender;
		}
		
		if (this.bCustomerAddress2 != null) {
			obj.bCustomerAddress2 = this.bCustomerAddress2;
		}
		
		if (this.bCustomerAddress != null) {
			obj.bCustomerAddress = this.bCustomerAddress;
		}
		
		if (this.bCity != null) {
			obj.bCity = this.bCity;
		}
		
		if (this.bCustomerZipcode != null) {
			obj.bCustomerZipcode = this.bCustomerZipcode;
		}
		
		if (this.bPhone != null) {
			obj.bPhone = this.bPhone;
		}
		
		if (this.state != null) {
			obj.state = this.state;
		}
		
		if (this.bstate != null) {
			obj.bstate = this.bstate;
		}
		
		
		if (this.shippingMethod != null) {
			obj.shippingMethod = this.shippingMethod;
		}
		
		return obj;
	}

	public String getbCustomerName() {
		return bCustomerName;
	}

	public void setbCustomerName(String bCustomerName) {
		this.bCustomerName = bCustomerName;
	}

	public String getbCustomGender() {
		return bCustomGender;
	}

	public void setbCustomGender(String bCustomGender) {
		this.bCustomGender = bCustomGender;
	}

	public String getbCustomerAddress() {
		return bCustomerAddress;
	}

	public void setbCustomerAddress(String bCustomerAddress) {
		this.bCustomerAddress = bCustomerAddress;
	}

	public String getbCustomerAddress2() {
		return bCustomerAddress2;
	}

	public void setbCustomerAddress2(String bCustomerAddress2) {
		this.bCustomerAddress2 = bCustomerAddress2;
	}

	public String getbCity() {
		return bCity;
	}

	public void setbCity(String bCity) {
		this.bCity = bCity;
	}

	public String getbCustomerZipcode() {
		return bCustomerZipcode;
	}

	public void setbCustomerZipcode(String bCustomerZipcode) {
		this.bCustomerZipcode = bCustomerZipcode;
	}

	public String getbPhone() {
		return bPhone;
	}

	public void setbPhone(String bPhone) {
		this.bPhone = bPhone;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public String getShoulder() {
		return shoulder;
	}

	public void setShoulder(String shoulder) {
		this.shoulder = shoulder;
	}

	public String getChest() {
		return chest;
	}

	public void setChest(String chest) {
		this.chest = chest;
	}

	public String getStomch() {
		return stomch;
	}

	public void setStomch(String stomch) {
		this.stomch = stomch;
	}

	public String getPosture() {
		return posture;
	}

	public void setPosture(String posture) {
		this.posture = posture;
	}

	public float getShirtNeck() {
		return shirtNeck;
	}

	public void setShirtNeck(float shirtNeck) {
		this.shirtNeck = shirtNeck;
	}

	public float getJacketShirtLenght() {
		return jacketShirtLenght;
	}

	public void setJacketShirtLenght(float jacketShirtLenght) {
		this.jacketShirtLenght = jacketShirtLenght;
	}

	public float getChestSize() {
		return chestSize;
	}

	public void setChestSize(float chestSize) {
		this.chestSize = chestSize;
	}

	public float getStomachSize() {
		return stomachSize;
	}

	public void setStomachSize(float stomachSize) {
		this.stomachSize = stomachSize;
	}

	public float getJacketHips() {
		return jacketHips;
	}

	public void setJacketHips(float jacketHips) {
		this.jacketHips = jacketHips;
	}

	public float getShoulderSize() {
		return shoulderSize;
	}

	public void setShoulderSize(float shoulderSize) {
		this.shoulderSize = shoulderSize;
	}

	public float getSleeveLength() {
		return sleeveLength;
	}

	public void setSleeveLength(float sleeveLength) {
		this.sleeveLength = sleeveLength;
	}

	public float getBicepSize() {
		return bicepSize;
	}

	public void setBicepSize(float bicepSize) {
		this.bicepSize = bicepSize;
	}

	public float getWristSize() {
		return wristSize;
	}

	public void setWristSize(float wristSize) {
		this.wristSize = wristSize;
	}

	public float getPantsLength() {
		return pantsLength;
	}

	public void setPantsLength(float pantsLength) {
		this.pantsLength = pantsLength;
	}

	public float getWaist() {
		return waist;
	}

	public void setWaist(float waist) {
		this.waist = waist;
	}

	public float getCrotch() {
		return crotch;
	}

	public void setCrotch(float crotch) {
		this.crotch = crotch;
	}

	public float getThighSize() {
		return thighSize;
	}

	public void setThighSize(float thighSize) {
		this.thighSize = thighSize;
	}

	public float getKneeSize() {
		return kneeSize;
	}

	public void setKneeSize(float kneeSize) {
		this.kneeSize = kneeSize;
	}

	public boolean isSuitMeasurementComplete() {
		return suitMeasurement;
	}

	public void setSuitMeasurementComplete(boolean suitMeasurement) {
		this.suitMeasurement = suitMeasurement;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setDePrice(float dePrice) {
		this.dePrice = dePrice;
	}

	public float getDePrice() {
		return dePrice;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCustomerMsg(String customerMsg) {
		this.customerMsg = customerMsg;
	}

	public String getCustomerMsg() {
		return customerMsg;
	}

	public void setCustomerAddress2(String customerAddress2) {
		this.customerAddress2 = customerAddress2;
	}

	public String getCustomerAddress2() {
		return customerAddress2;
	}

	public void setBcustomerName(String bCustomerName) {
		this.bCustomerName = bCustomerName;
	}

	public String getBcustomerName() {
		return bCustomerName;
	}

	public void setCustomGender(String customGender) {
		this.customGender = customGender;
	}

	public String getCustomGender() {
		return customGender;
	}

	public void setBcustomGender(String bCustomGender) {
		this.bCustomGender = bCustomGender;
	}

	public String getBcustomGender() {
		return bCustomGender;
	}

	public void setBcustomerAddress(String bCustomerAddress) {
		this.bCustomerAddress = bCustomerAddress;
	}

	public String getBcustomerAddress() {
		return bCustomerAddress;
	}

	public void setBcustomerAddress2(String bCustomerAddress2) {
		this.bCustomerAddress2 = bCustomerAddress2;
	}

	public String getBcustomerAddress2() {
		return bCustomerAddress2;
	}

	public void setBcity(String bCity) {
		this.bCity = bCity;
	}

	public String getBcity() {
		return bCity;
	}

	public void setBcustomerZipcode(String bCustomerZipcode) {
		this.bCustomerZipcode = bCustomerZipcode;
	}

	public String getBcustomerZipcode() {
		return bCustomerZipcode;
	}

	public void setBphone(String bPhone) {
		this.bPhone = bPhone;
	}

	public String getBphone() {
		return bPhone;
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

	public void setBstate(String bstate) {
		this.bstate = bstate;
	}

	public String getBstate() {
		return bstate;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getState() {
		return state;
	}

	public String getShippingMethod() {
		return shippingMethod;
	}

	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}

	public float getCouponCutOff() {
		return couponCutOff;
	}

	public void setCouponCutOff(float couponCutOff) {
		this.couponCutOff = couponCutOff;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public long getCountry() {
		return country;
	}

	public void setCountry(long country) {
		this.country = country;
	}

	public long getBcountry() {
		return bcountry;
	}

	public void setBcountry(long bcountry) {
		this.bcountry = bcountry;
	}

	public String getCustomerBCountry() {
		return customerBCountry;
	}

	public void setCustomerBCountry(String customerBCountry) {
		this.customerBCountry = customerBCountry;
	}

	public boolean isBillingSameAsPrimary() {
		return billingSameAsPrimary;
	}

	public void setBillingSameAsPrimary(boolean billingSameAsPrimary) {
		this.billingSameAsPrimary = billingSameAsPrimary;
	}
	
	public SuitMeasurement getSuitMeasurement(){
		return new SuitMeasurement(height, weight, age, shoulder, chest, stomch, posture, shirtNeck, jacketShirtLenght, chestSize, stomachSize, jacketHips, shoulderSize, sleeveLength, bicepSize, wristSize, pantsLength, waist, crotch, thighSize, kneeSize);
	}
	
	public void setMySuitMeasurement(SuitMeasurement measurement){
		this.chest = measurement.getChest();
		this.chestSize = measurement.getChestSize();
		this.height = measurement.getHeight();
		this.weight = measurement.getWeight();
		this.age = measurement.getAge();
		this.shoulder = measurement.getShoulder();
		this.stomch = measurement.getStomach();
		this.posture = measurement.getPosture();
		this.shirtNeck = measurement.getShirtNeck();
		this.jacketShirtLenght = measurement.getJacketShirtLenght();
		this.stomachSize = measurement.getStomachSize();
		this.jacketHips = measurement.getJacketHips();
		this.shoulderSize = measurement.getShoulderSize();
		this.sleeveLength = measurement.getSleeveLength();
		this.bicepSize = measurement.getBicepSize();
		this.wristSize = measurement.getWristSize();
		this.pantsLength = measurement.getPantsLength();
		this.waist = measurement.getWaist();
		this.crotch = measurement.getCrotch();
		this.thighSize = measurement.getThighSize();
		this.kneeSize = measurement.getKneeSize();
	}

    public void setTraceInfo(String traceInfo) {
        this.traceInfo = traceInfo;
    }

    public String getTraceInfo() {
        return traceInfo;
    }

}
