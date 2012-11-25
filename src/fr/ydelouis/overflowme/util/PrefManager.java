package fr.ydelouis.overflowme.util;

import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefManager
{
	private static SharedPreferences mPrefs;
	private static Context mContext;
	
	public static boolean getBoolean(Context context, int keyResId, boolean defaultValue) {
		getPrefs(context);
		return mPrefs.getBoolean(mContext.getString(keyResId), defaultValue);
	}
	
	public static float getFloat(Context context, int keyResId, float defaultValue) {
		getPrefs(context);
		return mPrefs.getFloat(mContext.getString(keyResId), defaultValue);
	}
	
	public static int getInt(Context context, int keyResId, int defaultValue) {
		getPrefs(context);
		return mPrefs.getInt(mContext.getString(keyResId), defaultValue);
	}
	
	public static long getLong(Context context, int keyResId, long defaultValue) {
		getPrefs(context);
		return mPrefs.getLong(mContext.getString(keyResId), defaultValue);
	}
	
	public static String getString(Context context, int keyResId, String defaultValue) {
		getPrefs(context);
		return mPrefs.getString(mContext.getString(keyResId), defaultValue);
	}
	
	public static Set<String> getStringSet(Context context, int keyResId, Set<String> defaultValue) {
		getPrefs(context);
		return mPrefs.getStringSet(mContext.getString(keyResId), defaultValue);
	}
	
	public static SharedPreferences getPrefs(Context context) {
		if(mPrefs == null || !context.equals(mContext)) {
			mContext = context.getApplicationContext();
			mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		}
		return mPrefs;
	}
}
