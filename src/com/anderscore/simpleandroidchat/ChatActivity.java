package com.anderscore.simpleandroidchat;

import android.os.Bundle;

public class ChatActivity extends AbstractActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setContentView(R.layout.activity_list);
		
		super.onCreate(savedInstanceState);
	}
	
	@Override
	void onServiceAvailable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void notifyContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	void notifyMsg(ChatMsg msg) {
		// TODO Auto-generated method stub
		
	}

}
