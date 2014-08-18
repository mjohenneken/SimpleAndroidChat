package com.anderscore.simpleandroidchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.anderscore.simpleandroidchat.Constants.Extra;

public class ContactListActivity extends AbstractActivity {
	ListView listView;
	ContactListAdapter listAdpater;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_contact_list);	
		listAdpater = new ContactListAdapter(this);
		
		listView = (ListView) findViewById(android.R.id.list);
		listView.setAdapter(listAdpater);
		listView.setEmptyView(findViewById(android.R.id.empty));
		listView.setOnItemClickListener( new OnItemClickListener() {
		
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getBaseContext(), ChatActivity.class);
				intent.putExtra(Extra.USER, listAdpater.getItem(position).getUser());
				intent.putExtra(Extra.IS_ONLINE, listAdpater.getItem(position).isOnline());
				startActivity(intent);
			}
		});
		super.onCreate(savedInstanceState);
	}

	@Override
	void onServiceAvailable() {
		listAdpater.updateList(getService().getContacts());
		
	}

	@Override
	void notifyContact(Contact contact) {
		listAdpater.updateList(getService().getContacts());
		
	}

	@Override
	void notifyMsg(ChatMsg msg) {
		// TODO Auto-generated method stub
		
	}

}
