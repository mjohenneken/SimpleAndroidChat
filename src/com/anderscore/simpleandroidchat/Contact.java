package com.anderscore.simpleandroidchat;

import java.util.ArrayList;

public class Contact {
	private int id;
	private String name;
	private boolean isOnline;
	private ArrayList<ChatMsg> chatHistory;
	
	
	Contact(int id, String name, boolean isOnline, ArrayList<ChatMsg> chatHistory){
		setId(id);
		setName(name);
		setOnline(isOnline);
		setChatHistory(chatHistory);
	}
	
	
	Contact(String name, boolean isOnline) {
		setName(name);
		setOnline(isOnline);
	}
	

	public String getUser() {
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<ChatMsg> getChatHistory() {
		return chatHistory;
	}

	public void setChatHistory(ArrayList<ChatMsg> chatHistory) {
		this.chatHistory = chatHistory;
	}
}
