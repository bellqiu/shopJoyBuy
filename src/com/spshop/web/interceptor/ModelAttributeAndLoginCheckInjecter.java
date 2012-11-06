package com.spshop.web.interceptor;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.spshop.model.User;
import com.spshop.utils.Constants;
import com.spshop.web.BaseController;
import com.spshop.web.view.UserView;

@Component
@Aspect
public class ModelAttributeAndLoginCheckInjecter {
	
	private static Logger logger = Logger.getLogger(ModelAttributeAndLoginCheckInjecter.class);
	
	@Before("execution(public * com.spshop.web.*Controller.*(..) )")
	@Order(value=0)
	public void validateProductContext(JoinPoint joinPoint) {
		Object[] params = joinPoint.getArgs();
		Object target = joinPoint.getTarget();
		if(null != params){
			for(Object param : params){
				if(param instanceof Model && target instanceof BaseController){
					Model model = (Model) param;
					BaseController controller = (BaseController) target;
					model.addAttribute(Constants.SITE_VIEW, controller.getSiteView());
					model.addAttribute(Constants.USER_VIEW, controller.getUserView());
					logger.debug("set Site view and User View");
				}
			}
		}
	}
	
	@Around("execution(public * com.spshop.web.UserCenterController.*(..) )")
	@Order(value=1)
	public Object loginCheck(ProceedingJoinPoint joinPoint)
			throws Throwable {
		Object target = joinPoint.getTarget();
		if (target instanceof BaseController) {
			BaseController controller = (BaseController) target;
			UserView userView = controller.getUserView();
			User user = userView.getLoginUser();
			if (null != user) {
				logger.debug("User is login");
				return joinPoint.proceed();
			}

		}
		logger.debug("user is not login");
		return "/login";
	}
}
