package com.anderscore.simpleandroidchat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import android.util.Log;

public class ConnectionAdapter {

	XMPPTCPConnection connectionXMPP;
	ConnectionAdapterCallback callback;

	public ConnectionAdapter(ConnectionAdapterCallback callback) {
		this.callback = callback;
	}

	void connect() {

		ConnectionConfiguration config = new ConnectionConfiguration(Constants.HOST, Constants.PORT);
		config.setSecurityMode(SecurityMode.disabled);
		connectionXMPP = new XMPPTCPConnection(config);

		addConnectionListener();
		addMessageListener();

		new Thread(new Runnable() {

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
		connectionXMPP.addConnectionListener(new ConnectionListener() {

			@Override
			public void connected(XMPPConnection connection) {
				Log.i("connected", "connected: true");
				try {
					connectionXMPP.login(Constants.USER, Constants.PASSWORD);
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

	private void addMessageListener() {
		connectionXMPP.addPacketListener(new PacketListener() {

			@Override
			public void processPacket(Packet packet) throws NotConnectedException {
				org.jivesoftware.smack.packet.Message message = (org.jivesoftware.smack.packet.Message) packet;
				Contact contact = new Contact(message.getFrom().split("/")[0], connectionXMPP.getRoster().getPresence(packet.getFrom()).isAvailable());
				ChatMsg msg = new ChatMsg(contact, true, message.getBody());
				callback.notifyMsg(msg);
			}
		}, new PacketTypeFilter(org.jivesoftware.smack.packet.Message.class));
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
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		Roster roster = connectionXMPP.getRoster();
		Collection<RosterEntry> entries = roster.getEntries();
		for (RosterEntry entry : entries) {
			contacts.add(new Contact(entry.getUser(), roster.getPresence(entry.getUser()).isAvailable()));
		}
		return contacts;
	}

	public ChatMsg sendMessage(ChatMsg msg) {
		ChatMsg showMsg = null;
		try {
			org.jivesoftware.smack.packet.Message message = new org.jivesoftware.smack.packet.Message();
			message.setBody(msg.getMsg());
			message.setTo(msg.getContact().getUser());

			connectionXMPP.sendPacket(message);
			showMsg = new ChatMsg(new Contact(connectionXMPP.getUser().split("/")[0], true), false, msg.getMsg());			
		} catch (NotConnectedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return showMsg;
	}
}
