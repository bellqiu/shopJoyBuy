package com.spshop.admin.client.businessui.callback;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.spshop.admin.client.CommandFactory;

public class 
AsyncCallbackAdapter<T> implements AsyncCallback<T>{
	@Override
	public void onFailure(Throwable throwable) {
		CommandFactory.onError("Errors",throwable.getMessage()).execute();
	}

	@Override
	public void onSuccess(T rs) {
		
	}

}
