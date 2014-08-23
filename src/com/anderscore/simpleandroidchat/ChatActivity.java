package com.anderscore.simpleandroidchat;

import com.anderscore.simpleandroidchat.Constants.Extra;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
	
	ListView list;
	ChatListAdapter listAdapter;
	EditText eTextChatMessage;
	Button btnSendMessage;

	Contact contact;
	

	/**
	 * display up true, liste finden und setzen, andere views holen, listadapter instanz
	 * 
	 * click listeer: clear, new chatmsg, sendMsg, updateList, InputMethodManager, Intent mit Contact Extra
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_chat_list);
		getActionBar().setDisplayHomeAsUpEnabled(true);

		list = (ListView) findViewById(android.R.id.list);
		list.setEmptyView(findViewById(android.R.id.empty));
		eTextChatMessage = (EditText) findViewById(R.id.eTextChatMessage);
		btnSendMessage = (Button) findViewById(R.id.btnSendMessage);

		listAdapter = new ChatListAdapter(this);
		list.setAdapter(listAdapter);
		btnSendMessage.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String msgText = eTextChatMessage.getText().toString();
				if (!msgText.equals("")) {
					eTextChatMessage.getText().clear();
					ChatMsg msg = new ChatMsg(contact.getUser(),contact.getId(), false, msgText);
										
					//TODO ChatMsg showMsg = ChatActivity.this.mBinder.sendMsg(msg);
					//listAdapter.updateList(showMsg);

					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(eTextChatMessage.getWindowToken(), 0);
				}
			}
		});	
		super.onCreate(savedInstanceState);
	}

	/**
	 * navigate up
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	
//	/**
//	 * get messages
//	 */
//	@Override
//	void onServiceAvailable() {
//		int userID = getIntent().getIntExtra(Extra.USER_ID, 0);
//		//TODO contact = mBinder.getContact(userID);
//
//		
//		listAdapter.updateList(contact.getChatHistory());
//
//	}
//
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
//		if (msg.getUserId() ==contact.getId()) {
//		listAdapter.updateList(msg);
//}
//	}

}
