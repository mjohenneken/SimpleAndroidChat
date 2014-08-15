package com.anderscore.simpleandroidchat;

import java.io.IOException;
import java.util.ArrayList;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import android.util.Log;

public class ConnectionAdapter {
	
	XMPPTCPConnection connectionXMPP;

	void connect() {
		
		ConnectionConfiguration config = new ConnectionConfiguration(Constants.HOST,Constants.PORT);
		config.setSecurityMode(SecurityMode.disabled);
		connectionXMPP = new XMPPTCPConnection(config);
		
		addConnectionListener();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					connectionXMPP.connect();
				} catch (SmackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void addConnectionListener() {
		connectionXMPP.addConnectionListener(new ConnectionListener(){

			@Override
			public void connected(XMPPConnection connection) {
				Log.i("connected", "connected: true");
				try {
					connectionXMPP.login(Constants.USER,Constants.PASSWORD);
				} catch (XMPPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SmackException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void authenticated(XMPPConnection connection) {
				Log.i("authenticated", "authenticated: true");
			}

			@Override
			public void connectionClosed() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void connectionClosedOnError(Exception e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reconnectingIn(int seconds) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reconnectionSuccessful() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reconnectionFailed(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	public void disconnect() {
		try {
			connectionXMPP.disconnect();
			connectionXMPP = null;
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public ArrayList<Contact> getContacts() {
		
		return null;
	}
}
