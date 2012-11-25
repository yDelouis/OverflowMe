package fr.ydelouis.overflowme.activity;

import java.util.List;

import android.preference.PreferenceActivity;
import fr.ydelouis.overflowme.R;

public class SettingsActivity extends PreferenceActivity
{
	@Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.settings_headers, target);
    }
}
