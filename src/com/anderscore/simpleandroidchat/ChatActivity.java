package com.anderscore.simpleandroidchat;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.anderscore.simpleandroidchat.Constants.Extra;

public class ChatActivity extends AbstractActivity {
	ListView list;
	ChatListAdapter listAdapter;
	EditText eTextChatMessage;
	Button btnSendMessage;
	Contact contact;

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
					ChatMsg msg = new ChatMsg(contact.getUser(), contact.getId(), false, msgText);
					ChatMsg showMsg = ChatActivity.this.mBinder.sendMsg(msg);
					listAdapter.updateList(showMsg);
					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(eTextChatMessage.getWindowToken(), 0);
				}
			}
		});
		super.onCreate(savedInstanceState);
	}

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

	@Override
	void onServiceAvailable() {
		int userID = getIntent().getIntExtra(Extra.USER_ID, 0);
		contact = mBinder.getContact(userID);
		listAdapter.updateList(contact.getChatHistory());
	}

	@Override
	void notifyContact(Contact contact) {
		// do nothing
	}	
	@Override
	void notifyMsg(ChatMsg msg) {
		if (msg.getUserId() == contact.getId()) {
			listAdapter.updateList(msg);
		}
	}
}
