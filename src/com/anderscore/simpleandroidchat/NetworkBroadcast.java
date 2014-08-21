package com.anderscore.simpleandroidchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkBroadcast extends BroadcastReceiver{

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		ConnectivityManager cManager	= (ConnectivityManager) arg0.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networkInfo	= cManager.getAllNetworkInfo();
	}
}
