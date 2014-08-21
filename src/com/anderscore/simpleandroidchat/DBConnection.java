package com.anderscore.simpleandroidchat;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBConnection extends SQLiteOpenHelper{

	private static DBConnection db	= null;
	
	/**	Das initiieren dieses Contructors erzeugt ein Object, dass eine Schnittstelle zur DB bereithält.
	 * 	
	 * 	@param context - der Context, indem die Datenbank gebraucht wird.
	 */
	private DBConnection(Context context) {
		super(context, Constants.DB.DB_NAME, null, Constants.DB.DB_VERSION);
	}
	
	static DBConnection getDBConnection(Context context){
		if(db==null) db = new DBConnection(context);
		return db;
	}
	

/* ------- SQLiteOpenHelper ------- */
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " +
				Constants.DB.Contacts.TABLE_NAME + " (" +
				Constants.DB.Contacts.ID +" INTEGER PRIMARY KEY, " +
				Constants.DB.Contacts.USER + " TEXT, " +
				Constants.DB.Contacts.ONLINE + " INTEGER " +
				")";
		db.execSQL(sql);
		
		sql = "CREATE TABLE " +
				Constants.DB.Messages.TABLE_NAME + " (" +
				Constants.DB.Messages.ID +" INTEGER PRIMARY KEY, " +
				Constants.DB.Messages.USER_ID + " INTEGER, " +
				Constants.DB.Messages.INCOMMING + " INTEGER, " +
				Constants.DB.Messages.TEXT_MSG + " TEXT " +
				")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Constants.DB.Contacts.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Constants.DB.Messages.TABLE_NAME);
		onCreate(db);	
	}
}