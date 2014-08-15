package com.anderscore.simpleandroidchat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.anderscore.simpleandroidchat.MessengerService.LocalBinder;

public class AbstractActivity extends Activity {

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

	protected MessengerService getService() {
		return messengerService;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bind();
	}

	@Override
	protected void onStop() {
		super.onStop();
		unbind();
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
