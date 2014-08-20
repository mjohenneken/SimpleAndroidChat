package com.anderscore.simpleandroidchat;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Messenger;

public class MessengerService extends Service implements ConnectionAdapterEventbus {
	
	ConnectionAdapter 	connection;
	DBModel				model;
	LocalBinder			mBinder	= new LocalBinder();
	
	public class LocalBinder extends Binder{
		public MessengerService getService(){
			return MessengerService.this;
		}
	}
	
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


	/**	registerMessenger
	 * 
	 * 	@param messenger
	 */
	public void registerMessenger(Messenger messenger) {
		
	}

	
	/**	unregisterMessenger
	 * 
	 * 	@param messenger
	 */
	public void unregisterMessenger(Messenger messenger) {
		
	}
	
	
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

	
/* ------- ConnectionAdapterEventbus ------- */
	
	
	@Override
	public void contactEvent(Contact contact) {

	}

	
	@Override
	public void incommingMsgEvent(ChatMsg chatMsg) {
		
	}
}
