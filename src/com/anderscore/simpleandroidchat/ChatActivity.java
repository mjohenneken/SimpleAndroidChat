package com.anderscore.simpleandroidchat;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
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
		setContentView(R.layout.activity_contact_list);

		list = (ListView) findViewById(android.R.id.list);
		list.setEmptyView(findViewById(android.R.id.empty));
		eTextChatMessage = (EditText) findViewById(R.id.eTextChatMessage);
		btnSendMessage = (Button) findViewById(R.id.btnSendMessage);

		listAdapter = new ChatListAdapter(this);
		list.setAdapter(listAdapter);
		btnSendMessage.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = eTextChatMessage.getText().toString();
				if (msg != "") {
					getService().sendMessage(msg);
				}
			}
		});
		super.onCreate(savedInstanceState);
	}

	@Override
	void onServiceAvailable() {
		listAdapter.updateList(getService().getMessages(contact.getName()));
		
	}

	@Override
	void notifyContact(Contact contact) {
		//TODO
	}

	@Override
	void notifyMsg(ChatMsg msg) {
		if(msg.getContact().getName().equals(contact.getName())){
			listAdapter.updateList(msg);
		}

	}

}
