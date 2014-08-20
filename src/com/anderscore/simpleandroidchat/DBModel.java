package com.anderscore.simpleandroidchat;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/** Speichert und lädt Contact- und MessageData in und aus der Datenbank
 * 	
 * 	@author paloka
 */
class DBModel{

	private DBConnection db;


	/**	Initialisiert das ContactModel
	 * 
	 * 	@param context - Anwendungskontext
	 */
	DBModel(Context context){
		this.db			= DBConnection.getDBConnection(context);
	}
	
	
/* ------- Interface ------- */

	
	/**	Gibt alle in der DB stehenden Contacte zurück.
	 *  
	 *  @return ArrayList<Contact> - Alle Contacts einsortiert mit der userId.
	 */
	ArrayList<Contact> getContacts(){
		SQLiteDatabase db		= this.db.getReadableDatabase();
		
		String sql	= 	"SELECT * FROM " + 
						Constants.DB.Contacts.TABLE_NAME + " ORDER BY " + 
						Constants.DB.Contacts.USER + " ASC";
		Cursor cursor = db.rawQuery(sql, null);
		
		ArrayList<Contact> contacts = new ArrayList<Contact>();
		if(!cursor.moveToFirst())	return contacts;
		do{
			boolean	online			=	false;
			if(cursor.getInt(cursor.getColumnIndex(Constants.DB.Contacts.ONLINE)) == 1)	online = true;
			
			contacts.add(new Contact(	cursor.getInt(cursor.getColumnIndex(Constants.DB.Contacts.ID)), 
										cursor.getString(cursor.getColumnIndex(Constants.DB.Contacts.USER)), 
										online,
										null));
			cursor.moveToNext();
		}while(!cursor.isAfterLast());
		return contacts;
	}
	
	
	/**	Gibt einen contact zurück.
	 * 	
	 * 	@param contactId
	 * 	@return - Den gewünschten Contact oder null
	 */
	Contact getContact(int contactId){
		SQLiteDatabase db	= this.db.getReadableDatabase();
		String sql	=	"SELECT * " +
						"FROM " + Constants.DB.Contacts.TABLE_NAME + " AS C " +
						"LEFT JOIN " + Constants.DB.Messages.TABLE_NAME + " AS M " +
						"ON C." + Constants.DB.Contacts.ID + " = M." + Constants.DB.Messages.TABLE_NAME +
						" WHERE C." + Constants.DB.Contacts.ID + " = " + contactId +
						" ORDER BY M." + Constants.DB.Contacts.ID + " ASC";
		Cursor cursor = db.rawQuery(sql, null);
		
		if(!cursor.moveToFirst())	return null;
		String 	user			=	cursor.getString(cursor.getColumnIndex(Constants.DB.Contacts.USER));
		boolean	online			=	false;
		if(cursor.getInt(cursor.getColumnIndex(Constants.DB.Contacts.ONLINE)) == 1)	online = true;
		
		ArrayList<ChatMsg> chatHistory	= new ArrayList<ChatMsg>();
		if(!cursor.isNull(cursor.getColumnIndex(Constants.DB.Messages.TEXT_MSG))){
			do{
				int id				= cursor.getInt(cursor.getColumnIndex(Constants.DB.Messages.ID));
				boolean direction	= false;
				if(cursor.getInt(cursor.getColumnIndex(Constants.DB.Messages.INCOMMING))==1)	direction = true;;
				String	msg			= cursor.getString(cursor.getColumnIndex(Constants.DB.Messages.TEXT_MSG));
				ChatMsg chatMsg		= new ChatMsg(id,user,contactId,direction,msg);
				chatHistory.add(chatMsg);
				cursor.moveToNext();
			}while(!cursor.isAfterLast());
		}
		return new Contact(contactId, user, online, chatHistory);
	}
	
	
	/**	Versucht den Contact zu editieren. Gibt es keinen solchen Contact wird ein neuer Contact angelegt.
	 * 	
	 * 	@param contact - hinzuzufügender Contact.
	 *  @return contact
	 */
	Contact addOrEditContact(Contact contact){	
		SQLiteDatabase db	= this.db.getWritableDatabase();
		
		ContentValues values	= 	new ContentValues();
		int online	= 0;
		if(contact.isOnline())	online = 1;
		values.put(Constants.DB.Contacts.ONLINE, online);
		
		String selection		= 	Constants.DB.Contacts.USER + " = ?";
		String[] selectionArgs	= 	{contact.getUser()};
		
		if(db.update(Constants.DB.Contacts.TABLE_NAME,values,selection,selectionArgs) > 0){
			String sql	= 	"SELECT * " +
							"FROM " + Constants.DB.Contacts.TABLE_NAME + " AS C " +
							"LEFT JOIN " + Constants.DB.Messages.TABLE_NAME + " AS M " +
							"ON C." + Constants.DB.Contacts.ID + " = M." + Constants.DB.Messages.USER_ID +
							" WHERE C." + Constants.DB.Contacts.USER + " = ?";
			Cursor cursor	= db.rawQuery(sql, selectionArgs);
			cursor.moveToFirst();
			contact.setId(cursor.getInt(0));	//Da DB_CONTACT_ID = DB_MESSAGE_ID hier Zugriff �ber Index
			
			ArrayList<ChatMsg> chatHistory	= new ArrayList<ChatMsg>();
			if(!cursor.isNull(cursor.getColumnIndex(Constants.DB.Messages.TEXT_MSG))){
				do{
					int id				= cursor.getInt(cursor.getColumnIndex(Constants.DB.Messages.ID));
					boolean direction	= false;
					if(cursor.getInt(cursor.getColumnIndex(Constants.DB.Messages.INCOMMING))==1)	direction = true;;
					String	msg			= cursor.getString(cursor.getColumnIndex(Constants.DB.Messages.TEXT_MSG));
					ChatMsg chatMsg		= new ChatMsg(id,contact.getUser(),contact.getId(),direction,msg);
					chatHistory.add(chatMsg);
					cursor.moveToNext();
				}while(!cursor.isAfterLast());
			}
			contact.setChatHistory(chatHistory);
		} else {
			values.put(Constants.DB.Contacts.USER, contact.getUser());
			values.put(Constants.DB.Contacts.ONLINE, contact.isOnline());
			contact.setId((int)db.insert(Constants.DB.Contacts.TABLE_NAME, null, values));
			contact.setChatHistory(new ArrayList<ChatMsg>());
		}
		return contact;
	}
	
	
	/** Fügt einem Contact eine Nachricht im Chatverlauf hinzu.
	 *  
	 *  @param chatMsg - hinzuzufügende Nachricht.
	 */
	ChatMsg appendChatMsg(ChatMsg chatMsg){
		SQLiteDatabase db	= this.db.getWritableDatabase();
		if(chatMsg.isIncomming()==true){
			String sql		= 	"SELECT * " +
								"FROM " + Constants.DB.Contacts.TABLE_NAME +
								" WHERE " + Constants.DB.Contacts.USER + " = ?";
			String[] selectionArgs	= {chatMsg.getUser()};
			Cursor cursor	=	db.rawQuery(sql, selectionArgs);
			if(cursor.moveToFirst()){
				chatMsg.setUserId(cursor.getInt(cursor.getColumnIndex(Constants.DB.Contacts.ID)));
			}
		}
		ContentValues values	= new ContentValues();
		values.put(Constants.DB.Messages.USER_ID, chatMsg.getUserId());
		if(chatMsg.isIncomming())	values.put(Constants.DB.Messages.INCOMMING, 1);
		else						values.put(Constants.DB.Messages.INCOMMING, 0);
		values.put(Constants.DB.Messages.TEXT_MSG, chatMsg.getMsg());
		int messageId = (int) db.insert(Constants.DB.Messages.TABLE_NAME, null, values);
		chatMsg.setId(messageId);
		return chatMsg;
	}
}