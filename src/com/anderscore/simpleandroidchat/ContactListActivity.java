package com.anderscore.simpleandroidchat;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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
				//chat
				
			}
		});
		super.onCreate(savedInstanceState);
	}

	@Override
	void onServiceAvailable() {
		listAdpater.updateList(getService().getContacts());
		
	}

}
