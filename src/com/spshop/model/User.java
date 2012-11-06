package com.spshop.model;

public class User extends Component{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5322585792062205187L;
	private String email;
	private String telephone;
	private String firstName;
	private String lastName;
	private String password;
	private String country;
	private String address;
	private String zipcode;
	private String city;
	private String gender;
	private String state;
	
	private String fullNameP;
	private String fullNameB;
	
	private String address1P;
	private String address1B;
	
	
	private String address2P;
	private String address2B;
	
	private String cityP;
	private String cityB;
	
	private String stateProvinceP;
	private String stateProvinceB;
	
	private int countryP;
	private int countryB;
	
	private String postalCodeP;
	private String postalCodeB;
	
	private String phoneP;
	private String phoneB;
	
	private boolean billingSameAsPrimary;
	
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

	private boolean suitMeasurement;
	
	public User() {
	}

	public User(User user) {
	    super(user);
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    

	public String getFullNameP() {
		return fullNameP;
	}

	public void setFullNameP(String fullNameP) {
		this.fullNameP = fullNameP;
	}

	public String getFullNameB() {
		return fullNameB;
	}

	public void setFullNameB(String fullNameB) {
		this.fullNameB = fullNameB;
	}

	public String getAddress1P() {
		return address1P;
	}

	public void setAddress1P(String address1P) {
		this.address1P = address1P;
	}

	public String getAddress1B() {
		return address1B;
	}

	public void setAddress1B(String address1B) {
		this.address1B = address1B;
	}

	public String getAddress2P() {
		return address2P;
	}

	public void setAddress2P(String address2P) {
		this.address2P = address2P;
	}

	public String getAddress2B() {
		return address2B;
	}

	public void setAddress2B(String address2B) {
		this.address2B = address2B;
	}

	public String getCityP() {
		return cityP;
	}

	public void setCityP(String cityP) {
		this.cityP = cityP;
	}

	public String getCityB() {
		return cityB;
	}

	public void setCityB(String cityB) {
		this.cityB = cityB;
	}

	public String getStateProvinceP() {
		return stateProvinceP;
	}

	public void setStateProvinceP(String stateProvinceP) {
		this.stateProvinceP = stateProvinceP;
	}

	public String getStateProvinceB() {
		return stateProvinceB;
	}

	public void setStateProvinceB(String stateProvinceB) {
		this.stateProvinceB = stateProvinceB;
	}

	public int getCountryP() {
		return countryP;
	}

	public void setCountryP(int countryP) {
		this.countryP = countryP;
	}

	public int getCountryB() {
		return countryB;
	}

	public void setCountryB(int countryB) {
		this.countryB = countryB;
	}

	public String getPostalCodeP() {
		return postalCodeP;
	}

	public void setPostalCodeP(String postalCodeP) {
		this.postalCodeP = postalCodeP;
	}

	public String getPostalCodeB() {
		return postalCodeB;
	}

	public void setPostalCodeB(String postalCodeB) {
		this.postalCodeB = postalCodeB;
	}

	public String getPhoneP() {
		return phoneP;
	}

	public void setPhoneP(String phoneP) {
		this.phoneP = phoneP;
	}

	public String getPhoneB() {
		return phoneB;
	}

	public void setPhoneB(String phoneB) {
		this.phoneB = phoneB;
	}

	/**
	 * @autogenerated by CodeHaggis (http://sourceforge.net/projects/haggis)
	 * clone
	 * @return Object
	 */
	public User clone() {
		User obj = null;
		obj = new User(this);
		if (this.email != null) {
			/* Does not have a clone() method */
			obj.email = this.email;
		}
		if (this.telephone != null) {
			/* Does not have a clone() method */
			obj.telephone = this.telephone;
		}
		if (this.firstName != null) {
			/* Does not have a clone() method */
			obj.firstName = this.firstName;
		}
		if (this.lastName != null) {
			/* Does not have a clone() method */
			obj.lastName = this.lastName;
		}
		if (this.password != null) {
			/* Does not have a clone() method */
			obj.password = this.password;
		}
		if (this.country != null) {
			/* Does not have a clone() method */
			obj.country = this.country;
		}
		if (this.address != null) {
			/* Does not have a clone() method */
			obj.address = this.address;
		}
		if (this.zipcode != null) {
			/* Does not have a clone() method */
			obj.zipcode = this.zipcode;
		}
		if (this.city != null) {
			/* Does not have a clone() method */
			obj.city = this.city;
		}
		if (this.gender != null) {
			/* Does not have a clone() method */
			obj.gender = this.gender;
		}
		if (this.state != null) {
			/* Does not have a clone() method */
			obj.state = this.state;
		}
		if (this.fullNameP != null) {
			/* Does not have a clone() method */
			obj.fullNameP = this.fullNameP;
		}
		if (this.fullNameB != null) {
			/* Does not have a clone() method */
			obj.fullNameB = this.fullNameB;
		}
		if (this.address1P != null) {
			/* Does not have a clone() method */
			obj.address1P = this.address1P;
		}
		if (this.address1B != null) {
			/* Does not have a clone() method */
			obj.address1B = this.address1B;
		}
		if (this.address2P != null) {
			/* Does not have a clone() method */
			obj.address2P = this.address2P;
		}
		if (this.address2B != null) {
			/* Does not have a clone() method */
			obj.address2B = this.address2B;
		}
		if (this.cityP != null) {
			/* Does not have a clone() method */
			obj.cityP = this.cityP;
		}
		if (this.cityB != null) {
			/* Does not have a clone() method */
			obj.cityB = this.cityB;
		}
		if (this.stateProvinceP != null) {
			/* Does not have a clone() method */
			obj.stateProvinceP = this.stateProvinceP;
		}
		if (this.stateProvinceB != null) {
			/* Does not have a clone() method */
			obj.stateProvinceB = this.stateProvinceB;
		}
		obj.countryP = this.countryP;
		obj.countryB = this.countryB;
		obj.billingSameAsPrimary = this.billingSameAsPrimary;
		
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
		
		
		if (this.postalCodeP != null) {
			/* Does not have a clone() method */
			obj.postalCodeP = this.postalCodeP;
		}
		if (this.postalCodeB != null) {
			/* Does not have a clone() method */
			obj.postalCodeB = this.postalCodeB;
		}
		if (this.phoneP != null) {
			/* Does not have a clone() method */
			obj.phoneP = this.phoneP;
		}
		if (this.phoneB != null) {
			/* Does not have a clone() method */
			obj.phoneB = this.phoneB;
		}
		return obj;
	}

	public Address getPrimaryAddress() {
		return new Address(fullNameP, address1P, address2P, cityP, stateProvinceP, countryP, postalCodeP, phoneP);
	}

	public void setPrimaryAddress(Address primaryAddress) {
		this.fullNameP = primaryAddress.getFullName();
		this.address1P = primaryAddress.getAddress1();
		this.address2P = primaryAddress.getAddress2();
		this.cityP = primaryAddress.getCity();
		this.stateProvinceP = primaryAddress.getStateProvince();
		this.countryP = primaryAddress.getCountry();
		this.postalCodeP = primaryAddress.getPostalCode();
		this.phoneP = primaryAddress.getPhone();
	}

	public Address getBillingAddress() {
		return new Address(fullNameB, address1B, address2B, cityB, stateProvinceB, countryB, postalCodeB, phoneB);
	}

	public void setBillingAddress(Address billingAddress) {
		this.fullNameB = billingAddress.getFullName();
		this.address1B = billingAddress.getAddress1();
		this.address2B = billingAddress.getAddress2();
		this.cityB = billingAddress.getCity();
		this.stateProvinceB = billingAddress.getStateProvince();
		this.countryB = billingAddress.getCountry();
		this.postalCodeB = billingAddress.getPostalCode();
		this.phoneB = billingAddress.getPhone();
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

}
