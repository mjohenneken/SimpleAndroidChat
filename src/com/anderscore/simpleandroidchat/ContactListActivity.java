package com.anderscore.simpleandroidchat;

import com.anderscore.simpleandroidchat.MessengerService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;

public class ContactListActivity extends Activity {
	
	MessengerService messengerService;
	ServiceConnection serviceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			LocalBinder binder = (LocalBinder) service;
			messengerService = binder.getService();
			messengerService.connect();
			
		}
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			messengerService = null;			
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contact_list);		
		bind();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.contact_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}	
	
	@Override
	protected void onStop() {
		unbind();
		super.onStop();
	}
	
	private void bind() {
		Intent serviceIntent = new Intent(this, MessengerService.class);
		startService(serviceIntent);
		bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		
	}
	
	private void unbind() {
		unbindService(serviceConnection);
	}	
}
