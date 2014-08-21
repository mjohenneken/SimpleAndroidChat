package com.anderscore.simpleandroidchat;

import com.anderscore.simpleandroidchat.Constants.Extra;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

/**	Baut und pushed eine Notification
 * 	
 * 	@author paloka
 */
class NotificationBuilder{

	/** Baut und pushed die Notification
	 * 	new NotificationCompat.Builder(context)
	 * 	ContentTitle, ContentText, SmallIcon, AutoCancel, Ticker, ContentIntent
	 * 	
	 *  R.drawable.ic_launcher
	 *  intent.putExtra(Extra.USER_ID,chatMsg.getUserId())
	 *  TaskStackBuilder.create(context) .addNewIntent .getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT)
	 *  
	 *  NotificationManager context.getSystemService(Context.NOTIFICATION_SERVICE)
	 * 	mNotificationManager.notify(Constants.NOTIFICATION_INDEX, mBuilder.build())
	 */
	public static void createOutAppNotification(Context context, ChatMsg chatMsg) {
		NotificationCompat.Builder mBuilder	= new NotificationCompat.Builder(context)
			.setContentTitle(chatMsg.getUser())
			.setContentText(chatMsg.getMsg())
			.setSmallIcon(R.drawable.ic_launcher)
			.setAutoCancel(true)
			.setTicker(chatMsg.getUser() + ": " + chatMsg.getMsg());
		
		Intent intent	= new Intent(context, ChatActivity.class);
		intent.putExtra(Extra.USER_ID, chatMsg.getUserId());
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addNextIntent(new Intent(context,ContactListActivity.class));
		stackBuilder.addNextIntent(intent);
		PendingIntent pendingIntent	= stackBuilder.getPendingIntent(0, PendingIntent.FLAG_CANCEL_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		
		NotificationManager mNotificationManager	= (NotificationManager)	context.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(Constants.NOTIFICATION_INDEX, mBuilder.build());
	}
}