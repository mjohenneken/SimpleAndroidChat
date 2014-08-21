package com.anderscore.simpleandroidchat;

import java.util.ArrayList;

import com.anderscore.simpleandroidchat.Constants.Extra;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
/**
 * 
 * @author mjohenneken
 * list, listAdapter
 *
 */
public class ContactListActivity extends AbstractActivity {
	
	ListView list;
	ContactListAdapter adapter;
	
	/**
	 * content View, listadpater, instanz listview holen, emtpy View, onitemClick
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_contact_list);
		
		adapter = new ContactListAdapter(this);
		list = (ListView) findViewById(android.R.id.list);
		list.setEmptyView(findViewById(android.R.id.empty));
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(ContactListActivity.this,ChatActivity.class);
				intent.putExtra(Extra.USER_ID, adapter.getItem(position).getId());
				startActivity(intent);				
			}
		});		
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onServiceAvailable() {
		ArrayList<Contact> contacts = mBinder.getContacts();
		adapter.updateList(contacts);	
	}


}
