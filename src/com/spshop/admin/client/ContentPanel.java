package com.spshop.admin.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;

public class ContentPanel extends ResizeComposite {

  interface Binder extends UiBinder<Widget, ContentPanel> { }
  private static final Binder binder = GWT.create(Binder.class);

  @UiField Element subject;
  @UiField HTMLPanel body;

  public ContentPanel() {
    initWidget(binder.createAndBindUi(this));
    subject.setInnerText("Broadcast");
    
    body.add(new HTML("This application is belong to Spark and his team, Thanks for all team memebers' hard work!!!"));
  }

  
  public void setTitle(String title){
	  subject.setInnerText(title);
  }
}
