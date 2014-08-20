package com.anderscore.simpleandroidchat;

public interface ConnectionAdapterEventbus {
	
	
	/**	notification about new Contact data
	 * 	
	 * 	@param contact
	 */
	public abstract void contactEvent(Contact contact);
	
	
	/**	notification about an incomming Msg
	 * 
	 * 	@param chatMsg
	 */
	public abstract void incommingMsgEvent(ChatMsg chatMsg);
}
