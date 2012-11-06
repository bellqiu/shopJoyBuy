package com.spshop.admin.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.spshop.admin.client.businessui.service.AdminService;
import com.spshop.admin.client.businessui.service.AdminServiceAsync;
import com.spshop.admin.shared.LoginInfo;
import com.spshop.model.Site;

public class AdminWorkspace implements EntryPoint {

  interface Binder extends UiBinder<DockLayoutPanel, AdminWorkspace> { }

  interface GlobalResources extends ClientBundle {
    @NotStrict
    @Source("global.css")
    CssResource css();
  }
  
  public static LoginInfo loginInfo;
  
  public static final String LOGIN_URL = "Admin.jsp";

  private static final Binder binder = GWT.create(Binder.class);

  @UiField public static TopPanel topPanel;
  @UiField public static ContentPanel contentPanel;
  @UiField public static Shortcuts shortcuts;
  
  public static final AdminServiceAsync ADMIN_SERVICE_ASYNC = GWT.create(AdminService.class);

  public void onModuleLoad() {
	  
    // Inject global styles.
    GWT.<GlobalResources>create(GlobalResources.class).css().ensureInjected();

    // Create the UI defined in AdminWorkspace.ui.xml.
    DockLayoutPanel outer = binder.createAndBindUi(this);

    // Get rid of scrollbars, and clear out the window's built-in margin,
    // because we want to take advantage of the entire client area.
    Window.enableScrolling(false);
    Window.setMargin("0px");

    // Special-case stuff to make topPanel overhang a bit.
    Element topElem = outer.getWidgetContainerElement(topPanel);
    topElem.getStyle().setZIndex(2);
    topElem.getStyle().setOverflow(Overflow.VISIBLE);

    // Add the outer panel to the RootLayoutPanel, so that it will be
    // displayed.
    RootLayoutPanel root = RootLayoutPanel.get();
    root.add(outer);
    
    refreshLogin();
  }
  
  public static String getSilteUrl(){
	  String s= "";
	  Site site = loginInfo.getSite();
	  
	  s = "http://"+site.getDomain();
	  
	  return s;
  }
  
  public static void refreshLogin(){
	  ADMIN_SERVICE_ASYNC.getLoginInfo(new AsyncCallback<LoginInfo>() {
			
			public void onSuccess(LoginInfo loginInfo) {
				 if(null!=loginInfo){
					 AdminWorkspace.loginInfo = loginInfo;
					 topPanel.setUserID(loginInfo.getUserID());
				 }else{
					 Window.Location.assign(LOGIN_URL);
				 }
			}
			
			public void onFailure(Throwable throwable) {
				Window.Location.assign(LOGIN_URL);
			}
		});
  }
}
