package com.anderscore.simpleandroidchat;

public class ChatMsg {
	private int id;
	private String user;
	private int userId;
	private boolean incomming;
	private String msg;
	
	
	public ChatMsg(int id, String user, int userId, boolean incomming, String msg) {
		setId(id);
		setUser(user);
		setIncomming(incomming);
		setMsg(msg);
		setUserId(userId);
	}
	
	
	public ChatMsg(String user, int userId, boolean incomming, String msg) {
		setUser(user);
		setIncomming(incomming);
		setMsg(msg);
		setUserId(userId);
	}
	
	
	public ChatMsg(String user, boolean incomming, String msg) {
		setUser(user);
		setIncomming(incomming);
		setMsg(msg);
	}
	
	
	private void setUser(String user) {
		this.user = user;
	}
	
	private void setIncomming(boolean incomming) {
		this.incomming = incomming;
	}
	
	private void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getUser() {
		return user;
	}
	
	public boolean isIncomming() {
		return incomming;
	}
	
	public String getMsg() {
		return msg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}	
}
