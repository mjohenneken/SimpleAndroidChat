package com.anderscore.simpleandroidchat;

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
	
	/**
	 * listadpater, instanz , onitemClick
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_contact_list);
		
		list = (ListView) findViewById(android.R.id.list);
		list.setEmptyView(findViewById(android.R.id.empty));
		list.setOnItemClickListener(new OnItemClickListener() {

			/**
			 * TODO  Chat Activity starten
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				
			}
		});
		
		super.onCreate(savedInstanceState);
	}
	
	/**
	 * onServiceAvailable update
	 */
//	@Override
//	void onServiceAvailable() {
//		
//	}

}
