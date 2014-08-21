package com.anderscore.simpleandroidchat;

import android.os.Bundle;
import android.view.MenuItem;

/**
 * 
 * @author mjohenneken
 *	Attribut list, listadapter, etextChatMessage, btnSendMessage, Contact
 */
/**
 * @author paloka
 *
 */
public class ChatActivity extends AbstractActivity {
	

	/**
	 * display up true, liste finden und setzen, andere views holen, listadapter instanz
	 * 
	 * click listeer: clear, new chatmsg, sendMsg, updateList, InputMethodManager, Intent mit Contact Extra
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//TODO		
		super.onCreate(savedInstanceState);
	}

	/**
	 * navigate up
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//TODO
		return super.onOptionsItemSelected(item);
	}

	
	/**
	 * get messages
	 */
	@Override	
	protected void onServiceAvailable() {
		// TODO

	}

//	@Override
//	void notifyContact(Contact contact) {
//		//do nothing
//	}
//
//	/**
//	 *  listadapter update bei gleicher contact Id
//	 * 
//	 */
//	@Override
//	void notifyMsg(ChatMsg msg) {	
//
//	}

}
