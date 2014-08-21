package com.anderscore.simpleandroidchat;

import android.content.Context;

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
		
	}
}