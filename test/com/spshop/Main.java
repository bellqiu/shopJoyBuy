package com.spshop;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.web.servlet.DispatcherServlet;

import magick.MagickException;

import com.spshop.model.Coupon;
import com.spshop.model.Order;
import com.spshop.model.User;
import com.spshop.service.factory.ServiceFactory;
import com.spshop.service.intf.CouponService;
import com.spshop.service.intf.OrderService;
import com.spshop.utils.EmailTools;

import freemarker.template.TemplateException;

public class Main {
	public static void main(String[] args) throws MagickException, IOException, TemplateException {
		
		//System.out.println(ServiceFactory.getService(OrderService.class));
		
		/*Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File("/home/project/tpl"));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setOutputEncoding("UTF-8");
		
		Template tp = cfg.getTemplate("order.tpl");
		
		Map<String, Object> root = new HashMap<String, Object>();
		
		root.put("title", "Welcome to HoneyBuy");
		
		Writer out = new OutputStreamWriter(System.out);
		
		tp.process(root, out);
		
		
		out.flush();*/
		
		/*
		
		root.put("title", "Welcome to HoneyBuy");
		
		String content = TempleteParser.parseMailContent("order.tpl", root);
		
		System.out.println(content);*/
		//for(int i=0; i< 500 ; i++){
		/*	Map<String, Object> root = new HashMap<String, Object>();
			
			Order order = ServiceFactory.getService(OrderService.class).getOrderById("Order251677QEJPN928522");
			
			order.setCustomerName("asdasdasd");
			order.setCreateDate(new Date());
			root.put("order", order);
			root.put("currencyRate", .8);
			EmailTools.sendMail("paid2", "test paid", root, "kabist@picassotown.com");
			EmailTools.sendMail("paid2", "test paid", root, "wan-shan.zhu@hp.com");*/
			
		//	System.out.println("================================" + i);
			
		//}
		
	/*	Coupon coupon = new Coupon();
		coupon.setCode("sadas");
		coupon.setName("test");
		coupon.setComponentStatus(0);
		coupon.setCreateDate(new Date());
		coupon.setCutOff(true);
		coupon.setEndTime(new Date());
		coupon.setOnetime(true);
		coupon.setStartDate(new Date());
		coupon.setUpdateDate(new Date());
		coupon.setUsedCount(11);
		coupon.setValue(.90f);*/
		//ServiceFactory.getService(CouponService.class).save(coupon);
		
		String s = "";
		
		for (char i = 'A'; i < 'Z'; i++) {
			for(int j=0; j < 20 ; j++){
			s = s+ ","+ (char)i+ new Random().nextInt(1000);
			}
		}
		
		System.out.println(s);
		
	}
}
