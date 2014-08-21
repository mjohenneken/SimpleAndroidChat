package com.anderscore.simpleandroidchat;

import java.util.ArrayList;

import android.os.Messenger;

public class MessengerService implements ConnectionAdapterEventbus {
	
	ConnectionAdapter 	connection;
	DBModel				model;
	
	
/* ------- Service ------- 
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return 0;
	}
	
	
	@Override
	public void onCreate() {		
		super.onCreate();
	}

	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}*/

	
/* ------- Service API ------- 


	public void registerMessenger(Messenger messenger) {
		
	}

	
	public void unregisterMessenger(Messenger messenger) {
		
	}
	
	
	public ArrayList<Contact> getContacts() {
		return model.getContacts();
	}
	
	
	public Contact getContact(int contactId){
		return model.getContact(contactId);
	}
	

	public ChatMsg sendMsg(ChatMsg msg) {
		msg	= model.appendChatMsg(msg);
		connection.sendMsg(msg);
		return msg;
	}*/

	
/* ------- ConnectionAdapterEventbus ------- */
	
	
	@Override
	public void contactEvent(Contact contact) {
		model.addOrEditContact(contact);
	}

	
	@Override
	public void incommingMsgEvent(ChatMsg chatMsg) {
		model.appendChatMsg(chatMsg);
	}
}
