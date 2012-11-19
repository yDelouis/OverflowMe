package fr.ydelouis.overflowme.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EReceiver;
import com.googlecode.androidannotations.annotations.SystemService;

import fr.ydelouis.overflowme.loader.MeLoader;
import fr.ydelouis.overflowme.loader.NotifLoader;

@EReceiver
public class MyStateUpdator extends BroadcastReceiver
{
	public static final String ACTION_UPDATE = "action_update";
	public static final String EVENT_MYSTATEUPDATED = "fr.ydelouis.overflowme.event.MYSTATE_UPDATED";

	private static PendingIntent scheduledIntent;
	private static boolean working = false;
	
	@SystemService
	protected ConnectivityManager connectivityManager;
	@SystemService
	protected AlarmManager alarmManager;
	@Bean
	protected MeLoader meLoader;
	@Bean
	protected NotifLoader notifLoader;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if(ACTION_UPDATE.equals(action))
			update(context);
		else {
			scheduleUpdate(context);
			update(context);
		}
	}
	
	@Background
	protected void update(Context context) {
		if(working)
			return;
		if(!connectivityManager.getActiveNetworkInfo().isConnected())
			return;
		working = true;
		meLoader.load();
		notifLoader.load();
		context.sendBroadcast(new Intent(EVENT_MYSTATEUPDATED));
		working = false;
	}
	
	private void scheduleUpdate(Context context) {
		if(scheduledIntent == null) {
			Intent intent = new Intent(context, MyStateUpdator_.class);
			intent.setAction(ACTION_UPDATE);
			scheduledIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		}
		cancelUpdate();

		alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, AlarmManager.INTERVAL_FIFTEEN_MINUTES, AlarmManager.INTERVAL_FIFTEEN_MINUTES, scheduledIntent);
	}
	
	private void cancelUpdate() {
		alarmManager.cancel(scheduledIntent);
	}
}
