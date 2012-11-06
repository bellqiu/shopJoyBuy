package com.spshop.model;

public class Address {
	private String fullName;

	private String address1;

	private String address2;

	private String city;

	private String stateProvince;

	private int country;

	private String postalCode;

	private String phone;

	public String getFullName() {
		return fullName;
	}
	
	public Address() {
	}
	
	public Address(String fullName, String address1, String address2,
			String city, String stateProvince, int country, String postalCode,
			String phone) {
		this.fullName = fullName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.stateProvince = stateProvince;
		this.country = country;
		this.postalCode = postalCode;
		this.phone = phone;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStateProvince() {
		return stateProvince;
	}

	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
