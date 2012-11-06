package com.spshop.admin.client.rich;

import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.ToggleButton;

public class ColorButton extends ToggleButton {
	private String colorName;
	private String colorValue;

	@UiConstructor
	public ColorButton(String name, String value) {
		this.colorName = name;
		this.colorValue = value;
		setHTML("<DIV STYLE='border-color: "+value+";border-style: solid;border-width: 9px;height: 0;margin-right: 2px;width: 0;'></DIV>");
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorValue(String colorValue) {
		this.colorValue = colorValue;
	}

	public String getColorValue() {
		return colorValue;
	}
}
