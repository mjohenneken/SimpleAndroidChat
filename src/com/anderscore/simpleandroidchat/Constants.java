package com.anderscore.simpleandroidchat;

public final class Constants {
	
	public static final String HOST 	= "max-johenneken.de";
	public static final String USER 	= "test1";
	public static final String PASSWORD = "test1";
	public static final Integer PORT 	= 5222;
	
	public static final class Event {
		public static final int CONTACT = 1;
		public static final int MSG 	= 2;
	}
	
	public static final class Extra {
		public static final String USER = "user";
		public static final String IS_ONLINE = "is_online";
	}
	
	public static final class DB{
		public static final String 	DB_NAME			= "simpleAndroidChatDB";
		public static final int 	DB_VERSION		= 1;
		
		public static final class Contacts{
			public static final String TABLE_NAME	= "contacts";
			public static final String ID			= "_ID";
			public static final String USER			= "user";
			public static final String ONLINE		= "isOnline";
		}
		
		public static final class Messages{
			public static final String TABLE_NAME	= "messages";
			public static final String ID			= "_ID";
			public static final String USER_ID		= "user_id";
			public static final String INCOMMING	= "is_incomming";
			public static final String TEXT_MSG		= "text_msg";
		}
	}
}
