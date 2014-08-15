package com.anderscore.simpleandroidchat;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MessengerService extends Service {
	
	IBinder mBinder = new LocalBinder();
	ConnectionAdapter connectionAdapter;
	
	public class LocalBinder extends Binder {
		public MessengerService getService(){
			return MessengerService.this;
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
	
	@Override
	public void onCreate() {		
		super.onCreate();
		connectionAdapter = new ConnectionAdapter();	
		connect();
	}
	
	@Override
	public void onDestroy() {
		connectionAdapter.disconnect();
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	public void connect() {
		connectionAdapter.connect();
	}
	
	public void disconnect() {
		connectionAdapter.disconnect();
	}

}
