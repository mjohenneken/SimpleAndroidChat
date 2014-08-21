package com.anderscore.simpleandroidchat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.anderscore.simpleandroidchat.Constants.Event;
import com.anderscore.simpleandroidchat.Constants.OnlineStatus;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessengerService extends Service implements ConnectionAdapterEventbus {
	
	ConnectionAdapter 	connection;
	DBModel				model;
	LocalBinder			mBinder	= new LocalBinder();
	LinkedList<Messenger> messengers = new LinkedList<Messenger>();
	OnlineStatus onlineStatus;
	static MessengerService service = null;;
	
	
/* ------- Service ------- */
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	
	@Override
	public void onCreate() {		
		super.onCreate();
		if(service == null)	service = this;
		ConnectivityManager cManager	= (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfo	= cManager.getAllNetworkInfo();
		setOnlineStatus(networkInfo);	
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
		service = null;
	}

	
/* ------- Service API ------- */
	
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
		
		public void registerMessenger(Messenger messenger) {
			messengers.add(messenger);
		}

		public void unregisterMessenger(Messenger messenger) {
			messengers.remove(messenger);
		}
	}

	
/* ------- ConnectionAdapterEventbus ------- */
	
	
	@Override
	public void contactEvent(Contact contact) {
		model.addOrEditContact(contact);
		Iterator<Messenger> iter = messengers.iterator();
		while(iter.hasNext()) {
			try {
				iter.next().send(Message.obtain(null,Event.CONTACT,contact));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	
	@Override
	public void incommingMsgEvent(ChatMsg chatMsg) {
		chatMsg = model.appendChatMsg(chatMsg);
		Iterator<Messenger> iter = messengers.iterator();
		while(iter.hasNext()) {
			try {
				iter.next().send(Message.obtain(null,Event.MSG,chatMsg));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		NotificationBuilder.createOutAppNotification(this, chatMsg);
	}


	public void setOnlineStatus(NetworkInfo[] networkInfo) {
		boolean wifi	= networkInfo[ConnectivityManager.TYPE_WIFI].isConnected();
		boolean mobile	= networkInfo[ConnectivityManager.TYPE_MOBILE].isConnected();
		if(wifi){
			onlineStatus = OnlineStatus.WIFI;
		}
		if(mobile&&!wifi){
			onlineStatus = OnlineStatus.MOBILE;
		}
		onlineStatus	= OnlineStatus.OFFLINE;
		System.out.println("InternetStatus: " + onlineStatus);
	}
	
	public static MessengerService getService(){
		return service;
	}
}