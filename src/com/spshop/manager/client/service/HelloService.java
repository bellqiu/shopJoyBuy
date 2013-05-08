package com.spshop.manager.client.service;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
@RemoteServiceRelativePath("../service/helloService")
public interface HelloService extends RemoteService{
	String hello(String name);
}
