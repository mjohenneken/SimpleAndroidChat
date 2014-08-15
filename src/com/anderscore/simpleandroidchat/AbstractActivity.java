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

public abstract class AbstractActivity extends Activity{

	MessengerService messengerService;
	boolean bound;
	ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			LocalBinder binder = (LocalBinder) service;
			messengerService = binder.getService();
			messengerService.registerMessenger(messenger);

			onServiceAvailable();

		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			messengerService = null;
		}

	};
	
	Messenger messenger = new Messenger(new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Event.CONTACT:
				notifyContact((Contact) msg.obj);				
				break;
			case Event.MSG:
				notifyMsg((ChatMsg) msg.obj);
				break;
			default:
				super.handleMessage(msg);
				break;
			}
		}
	});

	abstract void onServiceAvailable();
	abstract void notifyContact(Contact contact);
	abstract void notifyMsg(ChatMsg msg);

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
		messengerService.unregisterMessenger(messenger);
		unbind();
	}

	private void bind() {
		if (!bound) {
			bound = true;
			Intent serviceIntent = new Intent(this, MessengerService.class);
			startService(serviceIntent);
			bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
		}
	}

	private void unbind() {
		if (bound) {
			unbindService(serviceConnection);
		}
	}

}
