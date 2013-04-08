package com.spshop.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.spshop.model.Address;
import com.spshop.model.User;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.AddressService;
import com.spshop.service.intf.UserService;
import com.spshop.utils.Constants;

@Controller
@RequestMapping("/uc")
@SessionAttributes("userInfo")
public class AddressController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(AddressController.class);
	
	@RequestMapping("/listAddress")
	public String listAddress(@RequestParam(value="id", required=false, defaultValue="0") int id, Model model){
		
		Address address = ServiceFactory.getService(AddressService.class).getAddressById(id);
		
		model.addAttribute("address", address);
		
		return "Address";
	}
	
	@RequestMapping("/deleteUserShippingAddress")
	@ResponseBody
	public String deleteUserShippingAddress(@RequestParam(value="id", required=false, defaultValue="0") int id, Model model){
		
		User user = getUserView().getLoginUser();

		user = ServiceFactory.getService(UserService.class).deleteUserShippingAddress(user, id);
		
		getUserView().setLoginUser(user);
		
		model.addAttribute(Constants.USER_INFO, user);
		
		return "";
	}
	
	@RequestMapping("/addOrUpdateShippingAddress")
	public String addOrUpdateShippingAddress(Model model, HttpServletRequest request){
		
		Address address = ServiceFactory.getService(AddressService.class).saveOrUpdateAddress(retrieveAddressFromFormdata(request));
		
		User user = getUserView().getLoginUser();
		
		user = ServiceFactory.getService(UserService.class).saveOrUpdateUserShippingAddress(user, address);
		
		model.addAttribute("address", address);
		
		model.addAttribute(Constants.USER_INFO, user);
		
		return "body/uc-inc/fragments/address";
	}
	
	@RequestMapping("/addOrUpdateBillingAddress")
	public String addOrUpdateBillingAddress(Model model, HttpServletRequest request){
		
		Address address = ServiceFactory.getService(AddressService.class).saveOrUpdateAddress(retrieveAddressFromFormdata(request));
		
		User user = getUserView().getLoginUser();
		
		user = ServiceFactory.getService(UserService.class).saveOrUpdateUserBillingAddress(user, address);
		
		model.addAttribute("billingAddress", address);
		
		model.addAttribute(Constants.USER_INFO, user);
		
		return "body/uc-inc/fragments/address_no_delete";
	}
	
	private Address retrieveAddressFromFormdata(HttpServletRequest request){
		Address address = new Address();
		
		String id = request.getParameter("id");
		
		if(null == id || !id.trim().matches("\\d+")){
			logger.warn(id + " is not a valid id, assign to 0");
			id = "0";
		}
		
		id = id.trim();
		
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String add = request.getParameter("address");
		String addExt = request.getParameter("address_ext");
		String country = request.getParameter("country");
		String stateProvince = request.getParameter("state_province");
		String postcode = request.getParameter("postcode");
		String phone = request.getParameter("phone_number");
		String city = request.getParameter("city");
		
		address.setId(Long.valueOf(id));
		address.setCity(city);
		address.setFirstName(firstName);
		address.setLastName(lastName);
		address.setAddress1(add);
		address.setAddress2(addExt);
		address.setCountry(country);
		address.setStateProvince(stateProvince);
		address.setPostalCode(postcode);
		address.setPhone(phone);
		if(null == getSiteView().getCountryMap().get(country)){
			throw new RuntimeException("Country is invalid");
		}
		
		return address;
	}
}
