package com.anderscore.simpleandroidchat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

/**
 * 
 * @author max Attribut bound, Service
 */
public abstract class AbstractActivity extends Activity {

	// TODO

	ServiceConnection serviceConnection = new ServiceConnection() {

		/**
		 * LocalBinder casten, Service holen, connect(testweise)
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO
		}

		/**
		 * service nullen
		 */
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO
		}

	};

	/**
	 * bind
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO
	}

	/**
	 * unbind
	 */
	@Override
	protected void onStop() {
		super.onStop();
		// TODO
	}

	/**
	 * bound checken und setzen, intent , startService, bindService mit flag
	 * bind auto create
	 */
	private void bind() {
		// TODO
	}

	/**
	 * bound checken und setzen, unbindService
	 */
	private void unbind() {
		// TODO
	}

}
