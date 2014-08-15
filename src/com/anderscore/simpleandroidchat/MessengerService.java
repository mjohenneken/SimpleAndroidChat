package com.anderscore.simpleandroidchat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.anderscore.simpleandroidchat.Constants.Event;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessengerService extends Service implements ConnectionAdapterCallback {
	
	IBinder mBinder = new LocalBinder();
	ConnectionAdapter connectionAdapter;
	LinkedList<Messenger> observers = new LinkedList<Messenger>();
	
	public class LocalBinder extends Binder {
		public MessengerService getService(){
			return MessengerService.this;
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public void onCreate() {		
		super.onCreate();
		connectionAdapter = new ConnectionAdapter(this);	
		connect();
	}
	
	@Override
	public void onDestroy() {
		disconnect();
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public void connect() {
		connectionAdapter.connect();
	}
	
	public void disconnect() {
		connectionAdapter.disconnect();
	}

	public ArrayList<Contact> getContacts() {
		return connectionAdapter.getContacts();
	}

	public void registerMessenger(Messenger messenger) {
		observers.add(messenger);		
	}

	public void unregisterMessenger(Messenger messenger) {
		observers.remove(messenger);		
	}

	@Override
	public void notifyContact(Contact contact) {
		Iterator<Messenger> iter = observers.iterator();
		while(iter.hasNext()) {
			try {
				iter.next().send(Message.obtain(null, Event.CONTACT, contact));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void notifyMsg(ChatMsg chatMsg) {
		Iterator<Messenger> iter = observers.iterator();
		while(iter.hasNext()) {
			try {
				iter.next().send(Message.obtain(null, Event.CONTACT, chatMsg));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

	public void sendMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

	public ChatMsg getMessages(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
