package com.spshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gwt.user.client.rpc.RemoteService;
import com.spshop.manager.client.service.HelloService;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/service/helloService")
public class HelloController extends BaseGWTController implements HelloService {
	
	@Override
	public String hello(String name) {
		return "Hello " + name;
	}

	@Override
	protected RemoteService getService() {
		return this;
	}
}
