package com.anderscore.simpleandroidchat;


import com.anderscore.simpleandroidchat.Constants.Extra;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ContactListActivity extends AbstractActivity {	
	ListView list;	
	ContactListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_contact_list);
		adapter = new ContactListAdapter(this);
		list = (ListView) findViewById(android.R.id.list);
		list.setEmptyView(findViewById(android.R.id.empty));
		list.setOnItemClickListener(new OnItemClickListener() {			
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ContactListActivity.this,ChatActivity.class);
				intent.putExtra(Extra.USER_ID, adapter.getItem(position).getId());
				startActivity(intent);
			}
		});		
		adapter = new ContactListAdapter(this);
		list.setAdapter(adapter);
		super.onCreate(savedInstanceState);
	}
	@Override
	void onServiceAvailable() {
		adapter.updateList(mBinder.getContacts());
	}

	@Override
	void notifyContact(Contact contact) {
		adapter.updateList(mBinder.getContacts());		
	}

	@Override
	void notifyMsg(ChatMsg msg) {
		// TODO Auto-generated method stub
		
	}
}
