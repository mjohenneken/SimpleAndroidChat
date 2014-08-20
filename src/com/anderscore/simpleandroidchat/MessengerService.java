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
		return null;
	}
	
	
	/**	getContact
	 * 
	 * 	@param contactId
	 * 	@return
	 */
	public Contact getContact(int contactId){
		return null;
	}
	
	
	/**	sendMsg
	 * 
	 * 	@param msg
	 * 	@return
	 */
	public ChatMsg sendMsg(ChatMsg msg) {
		return null;
	}

	
/* ------- ConnectionAdapterEventbus ------- */
	
	
	@Override
	public void contactEvent(Contact contact) {

	}

	
	@Override
	public void incommingMsgEvent(ChatMsg chatMsg) {
		
	}
}
