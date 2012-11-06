package com.spshop.admin.client.businessui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.spshop.model.User;

public class UserProfile extends Composite {

    private static UserProfileUiBinder uiBinder = GWT.create(UserProfileUiBinder.class);
    
    @UiField Label name;
    @UiField Label createDate;
    @UiField Label address;
    @UiField Label country;
    @UiField Label email;
    @UiField Label firstName;
    @UiField Label lastName;
    @UiField Label telephone;
    @UiField Label zipcode;
    
    private User user;

    interface UserProfileUiBinder extends UiBinder<Widget, UserProfile> {
    }

    public void setUser(User user) {
        this.user = user;
        
        DateTimeFormat dateTimeFormat = DateTimeFormat.getFormat("yy/MM/dd");
        name.setText(user.getName());
        createDate.setText(dateTimeFormat.format(user.getCreateDate()));
        address.setText(user.getAddress());
        country.setText(user.getCountry());
        email.setText(user.getEmail());
        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        telephone.setText(user.getTelephone());
        zipcode.setText(user.getZipcode());
        
    }

    public User getUser() {
        return user;
    }

    public UserProfile() {
        initWidget(uiBinder.createAndBindUi(this));
    }
    
    public UserProfile(User userInfo) {
        initWidget(uiBinder.createAndBindUi(this));
        setUser(userInfo);
    }

}
