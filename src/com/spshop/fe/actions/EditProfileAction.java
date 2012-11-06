package com.spshop.fe.actions;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.User;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.UserService;
import com.spshop.utils.Constants;

public class EditProfileAction extends BaseAction {

    @Override
    public ActionForward processer(ActionMapping mapping,
                                   PageFormBean page,
                                   HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        User user = (User)request.getSession().getAttribute(Constants.USER_INFO);
        Boolean isModify = Boolean.valueOf(request.getParameter("isModify"));
        if (user != null) {
            if (isModify) {
                user = ServiceFactory.getService(UserService.class).queryUserByEmail(user.getEmail());
                populateUserModel(user, request);
                ServiceFactory.getService(UserService.class).merge(user);
                user.setPassword("");
            }
            request.getSession().setAttribute(Constants.USER_INFO, user);
            return mapping.findForward(Constants.SUCCESS_VALUE);
        } else {
            throw new IllegalStateException("This session timed out!!");
        }
    }

    private void populateUserModel(User user, HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String gender = request.getParameter("gender");
        String telephone = request.getParameter("telephone");
        String address = request.getParameter("address");
        String zipcode = request.getParameter("zipcode");
        String country = request.getParameter("country");
        String city = request.getParameter("city");
        String state = request.getParameter("state");

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGender(gender);
        user.setTelephone(telephone);
        user.setAddress(address);
        user.setZipcode(zipcode);
        user.setCountry(country);
        user.setCity(city);
        user.setUpdateDate(new Date());
        user.setState(state);
    }

}
