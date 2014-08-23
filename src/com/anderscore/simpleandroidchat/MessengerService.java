package com.anderscore.simpleandroidchat;

import java.util.ArrayList;
import java.util.LinkedList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;

public class MessengerService extends Service implements ConnectionAdapterEventbus {
	
	ConnectionAdapter 	connection;
	DBModel				model;
	LocalBinder			mBinder	= new LocalBinder();
	
/* ------- Service ------- */
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	
	@Override
	public void onCreate() {		
		super.onCreate();
		connection	= new ConnectionAdapter(this);
		model		= new DBModel(this);
	}

	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		connection.disconnect();
	}

	
/* ------- Service API ------- 


	public void registerMessenger(Messenger messenger) {
		
	}

	
	public void unregisterMessenger(Messenger messenger) {
		
	}*/
	
	public class LocalBinder extends Binder{

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
		}
	}

	
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
