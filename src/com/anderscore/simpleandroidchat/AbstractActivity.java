package com.anderscore.simpleandroidchat;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;

import com.anderscore.simpleandroidchat.Constants.Event;
import com.anderscore.simpleandroidchat.MessengerService.LocalBinder;

/**
 * 
 * @author max Attribut bound, Service
 */
public abstract class AbstractActivity extends Activity {

	boolean mBound	= false;
	LocalBinder mBinder	= null;			
		
	ServiceConnection serviceConnection = new ServiceConnection() {		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mBinder	= (LocalBinder) service;
			mBinder.registerMessenger(messenger);
			onServiceAvailable();
		}
		@Override
		public void onServiceDisconnected(ComponentName name) {
			mBinder.unregisterMessenger(messenger);
			mBinder	= null;
		}

	};
	
	private Messenger messenger = new Messenger(new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case Event.CONTACT:
				notifyContact((Contact)msg.obj);
				return true;
				
			case Event.MSG:
				notifyMsg((ChatMsg)msg.obj);
				return true;

			default:
				return false;
			}
		}
	}));	
	abstract void onServiceAvailable();
	abstract void notifyMsg(ChatMsg chatMsg);
	abstract void notifyContact(Contact contact);

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
		if(mBound) return;
		mBound	= true;
		Intent intent = new Intent(this, MessengerService.class);
		startService(intent);
		bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
	}	
	private void unbind() {
		if(!mBound) return;
		mBound = false;
		unbindService(serviceConnection);
	}
}
