package com.spshop.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spshop.model.Order;
import com.spshop.model.OrderItem;
import com.spshop.model.SuitMeasurement;
import com.spshop.utils.Constants;
import com.spshop.web.view.SiteView;
import com.spshop.web.view.UserView;

public class BaseController {
	public SiteView getSiteView() {
		HttpSession  session = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
		return (SiteView) session.getServletContext().getAttribute(Constants.SITE_VIEW);
	}

	public UserView getUserView(){
		HttpSession  session = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getSession();
		return (UserView) session.getAttribute(Constants.USER_VIEW);
	}
	
	protected SuitMeasurement retrieveSuitMeasurement(HttpServletRequest request){
		
		SuitMeasurement measurement = null;
		
		String height = request.getParameter("measure_height");
		String weight = request.getParameter("measure_weight");
		String age = request.getParameter("measure_age");
		String shoulder = request.getParameter("measure_shoulders");
		String chest = request.getParameter("measure_chest");
		String stomch = request.getParameter("measure_stomach");
		String posture = request.getParameter("measure_posture");
		String shirtNeck = request.getParameter("measure_shirt_neck");
		String jacketShirtLenght = request.getParameter("jacket_shirt_length");
		String chestSize = request.getParameter("measure_chest_size");
		String stomachSize = request.getParameter("measure_stomach_size");
		String jacketHips = request.getParameter("measure_jacket_hips");
		String shoulderSize = request.getParameter("measure_shoulder_size");
		String sleeveLength = request.getParameter("measure_sleeve_length");
		String bicepSize = request.getParameter("measure_bicep_size");
		String wristSize = request.getParameter("measure_wrist_size");
		String pantsLength = request.getParameter("measure_pants_length");
		String waist = request.getParameter("measure_waist");
		String crotch = request.getParameter("measure_crotch");
		String thighSize = request.getParameter("measure_thigh_size");
		String kneeSize = request.getParameter("measure_knee_size");
		
		try {
			measurement = new SuitMeasurement(Float.valueOf(height), Float.valueOf(weight), Float.valueOf(age),
					shoulder, chest, stomch, posture,
					Float.valueOf(shirtNeck), Float.valueOf(jacketShirtLenght), Float.valueOf(chestSize),
					Float.valueOf(stomachSize),Float.valueOf(jacketHips),Float.valueOf(shoulderSize),
					Float.valueOf(sleeveLength), Float.valueOf(bicepSize), Float.valueOf(wristSize),
					Float.valueOf(pantsLength), Float.valueOf(waist), Float.valueOf(crotch), Float.valueOf(thighSize),
					Float.valueOf(kneeSize));
		} catch (NumberFormatException e) {
		}
		
		return measurement;
	}
	
	protected boolean validateMeasurements(){
		
		for(OrderItem orderItem : getUserView().getCart().getOrder().getItems()){
			if(orderItem.getProduct().getOptType() > 0){
				if(!getUserView().getLoginUser().isSuitMeasurementComplete()){
					return false;
				}
			}
		}
		
		return true;
	}
	
	protected void syncShoppingCartAndProcessingOrder(Order order){
		
		if(order.getId() == getUserView().getCart().getOrder().getId()){
			getUserView().getCart().setOrder(order);
		}
		
	}
}
