package com.spshop.admin.client.businessui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.spshop.model.HTML;
import com.google.gwt.user.client.ui.Button;
import com.spshop.admin.client.AdminWorkspace;
import com.spshop.admin.client.PopWindow;
import com.spshop.admin.client.businessui.callback.AsyncCallbackAdapter;
import com.spshop.admin.client.rich.RichText;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.ClickEvent;

public class HTMLCreation extends ObservableComposite<HTML, HTMLCreation> {

	private static HTMLCreationUiBinder uiBinder = GWT
			.create(HTMLCreationUiBinder.class);
	@UiField TextBox name;
	@UiField Button save;
	@UiField RichText html;

	interface HTMLCreationUiBinder extends UiBinder<Widget, HTMLCreation> {
	}

	public HTMLCreation() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setComponent(HTML component) {
		this.component = component;
		this.name.setValue(component.getName());
		this.html.setHTML(component.getContent());
	}

	@UiHandler("html")
	void onHtmlBlur(BlurEvent event) {
		component.setContent(html.getHTML());
	}
	@UiHandler("name")
	void onNameKeyUp(KeyUpEvent event) {
		component.setName(name.getValue());
	}
	@UiHandler("save")
	void onSaveClick(ClickEvent event) {
		final PopWindow loading = PopWindow.createLoading("Waiting").lock();
		AdminWorkspace.ADMIN_SERVICE_ASYNC.saveHTML(getComponet(), new AsyncCallbackAdapter<HTML>(){
			@Override
			public void onSuccess(HTML rs) {
				setComponent(rs);
				loading.hide();
				RootPanel.get().remove(loading);
				save.setText("Update");
			}
		});
	}
}
