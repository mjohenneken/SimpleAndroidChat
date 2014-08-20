package com.anderscore.simpleandroidchat;

import java.util.Collection;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

public class ConnectionAdapter {

	// XMPP Stuff
	private XMPPTCPConnection connection;	
	private ConnectionConfiguration config;
	private Roster roster;
	
	private ConnectionAdapterEventbus eventbus;

	
	/**	ConnectionAdaper
	 * 
	 * 	@param eventbus
	 */
	ConnectionAdapter(ConnectionAdapterEventbus eventbus) {
		this.eventbus	= eventbus;	
		
		config = new ConnectionConfiguration(Constants.HOST, Constants.PORT);
		
		connect();
	}


/* ------- ConnectionAdapter ------- */
	
	
	/**	connect
	 * 
	 */
	void connect() {
		if(connection!=null)	return;
		
		config.setSecurityMode(SecurityMode.disabled);
		config.setReconnectionAllowed(false);
		connection	= new XMPPTCPConnection(config);
		
		addConnectionListener();
		addMessageListener();

		new Thread(new Runnable() {
						
			@Override
			public void run() {
				try {	
					connection.connect();
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	

	/**	disconnect
	 * 
	 */
	void disconnect() {
		if(connection==null) return;
		try{
			connection.disconnect();
		}catch (NotConnectedException e) {
			e.printStackTrace();
		}
		connection 	= null;
		roster		= null;
	}

	
	/**	sendMsg
	 * 
	 * 	@param chatMsg
	 */
	void sendMsg(ChatMsg chatMsg) {
		if(connection==null)	return;		
		try {
			org.jivesoftware.smack.packet.Message message = new org.jivesoftware.smack.packet.Message();
			message.setBody(chatMsg.getMsg());
			message.setTo(chatMsg.getUser());
			connection.sendPacket(message);
		}catch 	(NotConnectedException e) {
			e.printStackTrace();
		}
	}
	
	
/* ------- protected Methods ------ */

	
	/**	fetchRosterEntries
	 * 
	 */
	private void fetchRosterEntries() {
		Collection<RosterEntry> rosterEntries = roster.getEntries();
		for (RosterEntry entry : rosterEntries) {
			Presence presence = roster.getPresence(entry.getUser());
			Contact contact;
			contact = new Contact(entry.getUser(), presence.isAvailable());
			eventbus.contactEvent(contact);
		}
	}
	
	
/* ------- Listener ------- */
	
	
	/**	Add Connection Listener
	 * 
	 */
	private void addConnectionListener(){		
		connection.addConnectionListener(new ConnectionListener() {
			@Override public void reconnectionSuccessful() {}			
			@Override public void reconnectionFailed(Exception arg0) {}			
			@Override public void reconnectingIn(int arg0) {}

			@Override
			public void connectionClosedOnError(Exception arg0) {
				System.out.println("connectionClosedOnError");			
			}

			@Override
			public void connectionClosed() {
				System.out.println("connectionClosed");
			}

			@Override
			public void connected(XMPPConnection arg0) {
				try {			
					connection.login(Constants.USER, Constants.PASSWORD);
				} catch (Exception e) {
					e.printStackTrace();
					disconnect();
				}
			}

			@Override
			public void authenticated(XMPPConnection arg0) {				
				roster	= connection.getRoster();
				addRosterListener();
				fetchRosterEntries();
			}
		});
	}
	
	
	/**	Add Roster Listener
	 * 
	 */
	private void addRosterListener(){
		roster.addRosterListener(new RosterListener() {
			
			@Override
			public void presenceChanged(Presence presence) {
				if(presence==null)	return;
				String user	= presence.getFrom().split("/")[0];
				RosterEntry entry = roster.getEntry(user);				
				eventbus.contactEvent(new Contact(entry.getUser(), presence.isAvailable()));
			}
			
			@Override
			public void entriesUpdated(Collection<String> arg0) {
				for(String entry:arg0)	presenceChanged(roster.getPresence(entry));
			}
			
			@Override
			public void entriesDeleted(Collection<String> arg0) {
				System.out.println("entriesDeleted");
			}
			
			@Override
			public void entriesAdded(Collection<String> arg0) {
				System.out.println("entriesAdded");
			}
		});
	}
	
	
	/**	Add Message Listener
	 * 
	 */
	private void addMessageListener(){
		connection.addPacketListener(new PacketListener() {
			
			@Override
			public void processPacket(Packet arg0) throws NotConnectedException {
				org.jivesoftware.smack.packet.Message message = (org.jivesoftware.smack.packet.Message) arg0;
				if(message.getBody()!=null && !message.getBody().equals(""))
					eventbus.incommingMsgEvent(new ChatMsg(message.getFrom().split("/")[0], true, message.getBody()));
			}
		}, new PacketTypeFilter(org.jivesoftware.smack.packet.Message.class));
	}
}
