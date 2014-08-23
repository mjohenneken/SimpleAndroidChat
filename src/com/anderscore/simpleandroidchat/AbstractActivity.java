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
public abstract class AbstractActivity extends Activity {

	boolean mBound	= false;
	LocalBinder mBinder	= null;

	ServiceConnection serviceConnection = new ServiceConnection() {

		/**
		 * LocalBinder casten, Service holen, connect(testweise)
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBinder	= (LocalBinder) service;
			onServiceAvailable();
		}

		/**
		 * service nullen
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBinder	= null;
		}

	};
	abstract void onServiceAvailable();

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
		if(mBound) return;
		mBound	= true;
		Intent intent = new Intent(this, MessengerService.class);
		startService(intent);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}

	/**
	 * bound checken und setzen, unbindService
	 */
	private void unbind() {
		if(!mBound) return;
		mBound = false;
		unbindService(serviceConnection);
	}

}
