package com.spshop.web;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public abstract class BaseGWTController extends RemoteServiceServlet{
	private RemoteService remoteService;
	private Class<?> remoteServiceClass;

	public String processCall(String payload) throws SerializationException {
		try {
			RPCRequest rpcRequest = RPC.decodeRequest(payload,
					this.remoteServiceClass);

			// delegate work to the spring injected service
			return RPC.invokeAndEncodeResponse(this.remoteService,
					rpcRequest.getMethod(), rpcRequest.getParameters());
		} catch (IncompatibleRemoteServiceException e) {
			return RPC.encodeResponseForFailure(null, e);
		}
	}

	/**
	 * Setter for Spring injection of the GWT RemoteService object.
	 * 
	 * @param RemoteService
	 *            the GWT RemoteService implementation that will be delegated to
	 *            by the {@code GWTController}.
	 */
	public void setRemoteService(RemoteService remoteService) {
		this.remoteService = remoteService;
		this.remoteServiceClass = this.remoteService.getClass();
	}
	
	protected abstract RemoteService getService();
	
	@RequestMapping
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//response.getWriter().print("<H1></H1>");
		setRemoteService(getService());
		super.doPost(request, response);
		return null;
	}
	
	@Override
	public ServletContext getServletContext() {
		return getThreadLocalRequest().getSession().getServletContext();
	}
}
