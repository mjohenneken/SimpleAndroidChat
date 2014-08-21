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

public class MessengerService extends Service implements ConnectionAdapterEventbus {
	
	ConnectionAdapter 	connection;
	DBModel				model;
	LocalBinder			mBinder	= new LocalBinder();
	LinkedList<Messenger> messengers = new LinkedList<Messenger>();
	
	
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

	
/* ------- Service API ------- */


	
	public class LocalBinder extends Binder{
		/**	getContacts
		 * 
		 * 	@return
		 */
		public ArrayList<Contact> getContacts() {
			return model.getContacts();
		}
		
		
		/**	getContact
		 * 
		 * 	@param contactId
		 * 	@return
		 */
		public Contact getContact(int contactId){
			return model.getContact(contactId);
		}
		
		
		/**	sendMsg
		 * 
		 * 	@param msg
		 * 	@return
		 */
		public ChatMsg sendMsg(ChatMsg msg) {
			msg	= model.appendChatMsg(msg);
			connection.sendMsg(msg);
			return msg;
		}
		
		/**	registerMessenger
		 * 
		 * 	@param messenger
		 */
		public void registerMessenger(Messenger messenger) {
			messengers.add(messenger);
		}

		
		/**	unregisterMessenger
		 * 
		 * 	@param messenger
		 */
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
	}
}
