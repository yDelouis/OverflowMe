package fr.ydelouis.overflowme.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.receiver.MyStateUpdator_;

public class NotificationSettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings_notifs);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		setRingtoneSummary(getPreferenceScreen().getSharedPreferences(), getString(R.string.pref_notifs_ringtone));
		getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		getActivity().sendBroadcast(new Intent(getActivity(), MyStateUpdator_.class));
	}
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		if(key.equals(getString(R.string.pref_notifs_onOff))) {
			boolean enabled = sharedPreferences.getBoolean(key, true);
			setEnabled(R.string.pref_notifs_frequencyUpdate, enabled);
			setEnabled(R.string.pref_notifs_notifs, enabled);
			setEnabled(R.string.pref_notifs_reputationChange, enabled);
			setEnabled(R.string.pref_notifs_vibrate, enabled);
			setEnabled(R.string.pref_notifs_ringtone, enabled);
			return;
		}
		if(key.equals(getString(R.string.pref_notifs_ringtone))) {
			setRingtoneSummary(sharedPreferences, key);
			return;
		}
	}

	private void setEnabled(int prefKeyId, boolean enabled) {
		findPreference(getString(prefKeyId)).setEnabled(enabled);
	}
	
	private void setRingtoneSummary(SharedPreferences sharedPreferences, String key) {
		String ringtoneStr = sharedPreferences.getString(key, null);
		if(ringtoneStr != null) {
			Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), Uri.parse(ringtoneStr));
			findPreference(key).setSummary(ringtone.getTitle(getActivity()));
		}
	}
}
