package com.anderscore.simpleandroidchat;

import com.anderscore.simpleandroidchat.MessengerService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

/**
 * 
 * @author max Attribut bound, Service
 */
public class AbstractActivity extends Activity {

	boolean bound	= false;
	MessengerService mService	= null;

	ServiceConnection serviceConnection = new ServiceConnection() {

		/**
		 * LocalBinder casten, Service holen, connect(testweise)
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			LocalBinder mBinder	= (LocalBinder) service;
			mService		= mBinder.getService();
		}

		/**
		 * service nullen
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mService	= null;
		}

	};

	/**
	 * bind
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		bind();
	}

	/**
	 * unbind
	 */
	@Override
	protected void onStop() {
		super.onStop();
		unbind();
	}

	/**
	 * bound checken und setzen, intent , startService, bindService mit flag
	 * bind auto create
	 */
	private void bind() {
		if(bound) return;
		bound	= true;
		Intent intent = new Intent(this, MessengerService.class);
		startService(intent);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	/**
	 * bound checken und setzen, unbindService
	 */
	private void unbind() {
		if(!bound) return;
		bound = false;
		unbindService(serviceConnection);
	}

}
