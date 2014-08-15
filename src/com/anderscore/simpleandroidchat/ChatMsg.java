package com.anderscore.simpleandroidchat;

public class ChatMsg {
	private Contact contact;
	private boolean incomming;
	private String msg;
	
	public ChatMsg(Contact contact, boolean incomming, String msg) {
		setContact(contact);
		setIncomming(incomming);
		setMsg(msg);
	}
	
	
	private void setContact(Contact contact) {
		this.contact = contact;
	}
	
	private void setIncomming(boolean incomming) {
		this.incomming = incomming;
	}
	
	private void setMsg(String msg) {
		this.msg = msg;
	}
	
	public Contact getContact() {
		return contact;
	}
	
	public boolean isIncomming() {
		return incomming;
	}
	
	public String getMsg() {
		return msg;
	}	
}
