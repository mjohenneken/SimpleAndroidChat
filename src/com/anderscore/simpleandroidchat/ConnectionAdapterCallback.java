package com.anderscore.simpleandroidchat;

public interface ConnectionAdapterCallback {
	public abstract void notifyContact(Contact contact);
	public abstract void notifyMsg(ChatMsg chatMsg);
}
