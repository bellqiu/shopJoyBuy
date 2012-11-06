package com.spshop.fe.actions;

import java.io.PrintWriter;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spshop.fe.formbeans.PageFormBean;
import com.spshop.model.Coupon;
import com.spshop.model.cart.ShoppingCart;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CouponService;

public class CouponAction extends BaseAction{
	private static Logger logger = Logger.getLogger(CouponAction.class);
	@Override
	public ActionForward processer(ActionMapping mapping, PageFormBean page,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PrintWriter out = response.getWriter();
		String code = request.getParameter("couponCode");
		
		Coupon coupon = null;
		try{		
			coupon = ServiceFactory.getService(CouponService.class).getCouponByCode(code);
		}catch(Exception e){
			logger.warn(e.getMessage(), e);
		}
		
		StringBuffer rs = new StringBuffer("d={");
		NumberFormat f = NumberFormat.getInstance();
		f.setMaximumFractionDigits(2);
		
		ShoppingCart cart = getCart(request, response);
		if(null==coupon){
			rs.append("errorString:'invalid coupon'");
		}if(coupon.getMinexpend() > cart.getOrder().getTotalPrice()){
			rs.append("errorString:'Cannot not apply in order less than USD " +  coupon.getMinexpend() +"'" );
		}else{
			float cutOff = 0f;
			cart.getOrder().setCouponCode(coupon.getCode());
			
			if(!coupon.isCutOff()){
				cutOff = coupon.getValue();
				cart.getOrder().setCouponCutOff(cutOff);
			}else{
				cutOff = coupon.getValue() * cart.getOrder().getTotalPrice();
				cart.getOrder().setCouponCutOff(cutOff);
			}
			
			String sCut =  f.format(cutOff * getCurrencies(request).get(cart.getOrder().getCurrency()));
			String sTotal = f.format((cart.getOrder().getTotalPrice() + cart.getOrder().getDePrice() - cutOff)*getCurrencies(request).get(cart.getOrder().getCurrency()));
			
			String sCurrency = cart.getOrder().getCurrency();
			rs.append("cut:'"+sCurrency + " " + sCut + "' ,");
			rs.append("total:'"+sCurrency + " " + sTotal + "'");
			
		}
		
		rs.append("}");
		
		out.print(rs.toString());
		return null;
	}

}
