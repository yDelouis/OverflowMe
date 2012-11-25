package fr.ydelouis.overflowme.util;

import java.util.List;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.activity.MainActivity_;
import fr.ydelouis.overflowme.entity.Notif;

public class NotifManager
{
	private static final int NOTIF_ID = 12480918;
	private static final String PREF_EX_REPUTATIONCHANGE = "prefExReputationChange";
	private static final String PREF_EX_UNREADNOTIFSCOUNT = "prefExUnreadNotifsCount";
	
	public static void notify(Context context, int reputationChange, List<Notif> unreadNotifs) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		int exReputationChange = prefs.getInt(PREF_EX_REPUTATIONCHANGE, 0);
		int exUnreadNotifsCount = prefs.getInt(PREF_EX_UNREADNOTIFSCOUNT, 0);
		if(exReputationChange == reputationChange && exUnreadNotifsCount == unreadNotifs.size())
			return;

		Notification notification = buildNotification(context, reputationChange, unreadNotifs);
		getNotificationManager(context).notify(NOTIF_ID, notification);
		
		prefs.edit()
			.putInt(PREF_EX_REPUTATIONCHANGE, reputationChange)
			.putInt(PREF_EX_UNREADNOTIFSCOUNT, unreadNotifs.size())
			.commit();
	}
	
	public static void cancel(Context context) {
		getNotificationManager(context).cancel(NOTIF_ID);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.edit()
			.remove(PREF_EX_REPUTATIONCHANGE)
			.remove(PREF_EX_UNREADNOTIFSCOUNT)
			.commit();
	}
	
	private static NotificationManager getNotificationManager(Context context) {
		return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	private static Notification buildNotification(Context context, int reputationChange, List<Notif> unreadNotifs) {
		int nbUnreadNotifs = unreadNotifs.size();
		
		Notification.Builder builder = new Notification.Builder(context);
		builder.setSmallIcon(R.drawable.ic_notif);
		builder.setAutoCancel(true);
		builder.setLights(Color.rgb(255, 153, 0), 300, 2000);
		if(PrefManager.getBoolean(context, R.string.pref_notifs_vibrate, false))
			builder.setDefaults(Notification.DEFAULT_VIBRATE);
		String ringtone = PrefManager.getString(context, R.string.ringtone, null);
		if(ringtone != null)
			builder.setSound(Uri.parse(ringtone));
		
		String title, text;
		if(nbUnreadNotifs == 0) {
			if(reputationChange > 0)
				title = context.getString(R.string.notif_reputationGain);
			else 
				title = context.getString(R.string.notif_reputationLoss);
			int strResId = reputationChange > 0 ? R.string.notif_reputationGained : R.string.notif_reputationLost;
			text = String.format(context.getString(strResId), reputationChange);
		} else {
			if(reputationChange == 0) {
				title = context.getString(R.string.notif_unreadNotifs);
				if(nbUnreadNotifs == 1) {
					Notif notif = unreadNotifs.get(0);
					text = notif.getType()+" : "+notif.getText();
				} else {
					text = String.format(context.getString(R.string.notif_unreadNotifsCount), unreadNotifs.size());
				}
			} else {
				title = context.getString(R.string.notif_reputationAndNotifs);
				String reputationChangeStr = (reputationChange >= 0 ? "+" : "")+reputationChange;
				text = String.format(context.getString(R.string.notif_reputationAndNotifsText), reputationChangeStr, unreadNotifs.size());
			}
		}
		builder.setContentTitle(title);
		builder.setTicker(title);
		builder.setContentText(text);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
			&& nbUnreadNotifs > 1 || (reputationChange != 0 && nbUnreadNotifs != 0)) {
			Notification.InboxStyle inbox = new Notification.InboxStyle(builder);
			if(reputationChange != 0) {
				int strResId = reputationChange > 0 ? R.string.notif_reputationGained : R.string.notif_reputationLost;
				inbox.addLine(String.format(context.getString(strResId), reputationChange));
			}
			for(Notif notif : unreadNotifs)
				inbox.addLine(notif.getType()+" : "+notif.getText());
			builder.setStyle(inbox);
		}
		
		Intent intent = MainActivity_.intent(context).get();
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		builder.setContentIntent(pendingIntent);
		
		return builder.build();
	}
}
