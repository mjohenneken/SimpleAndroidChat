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
				if (msgText != "") {
					eTextChatMessage.getText().clear();
					ChatMsg msg = new ChatMsg(contact, false, msgText);
					ChatMsg showMsg = getService().sendMessage(msg);
					listAdapter.updateList(showMsg);

					InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(eTextChatMessage.getWindowToken(), 0);
				}
			}
		});

		Intent startIntent = getIntent();
		contact = new Contact(startIntent.getStringExtra(Extra.USER), startIntent.getBooleanExtra(Extra.IS_ONLINE, false));

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
		// TODO get Messages

	}

	@Override
	void notifyContact(Contact contact) {
		// TODO
	}

	@Override
	void notifyMsg(ChatMsg msg) {
		if (msg.getContact().getUser().equals(contact.getUser())) {
			listAdapter.updateList(msg);
		}

	}

}
