package com.anderscore.simpleandroidchat;

public class Contact {
	private String name;
	private boolean isOnline;
	
	public Contact(String name, boolean isOnline) {
		setName(name);
		setOnline(isOnline);
	}
	
	public String getName() {
		return name;
	}
	private void setName(String name) {
		this.name = name;
	}
	public boolean isOnline() {
		return isOnline;
	}
	private void setOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}

}
