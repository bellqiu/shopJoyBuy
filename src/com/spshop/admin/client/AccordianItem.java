package com.spshop.admin.client;

import com.google.gwt.user.client.ui.TreeItem;

public class AccordianItem extends TreeItem{
	private Command comand;
	public AccordianItem(String title, Command comand) {
		setTitle(title);
		setComand(comand);
		setHTML(title);
	}
	public void setComand(Command comand) {
		this.comand = comand;
	}
	public Command getComand() {
		return comand;
	}
}
