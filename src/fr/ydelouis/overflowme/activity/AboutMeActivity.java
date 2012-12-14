package fr.ydelouis.overflowme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.User;
import fr.ydelouis.overflowme.fragment.*;

@EActivity(R.layout.activity_fullscreenfragment)
public class AboutMeActivity extends Activity
{
	@Extra
	protected User user;
	
	protected AboutMeFragment fragment;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(user.getDisplayName());
		
		if(savedInstanceState == null) {
			fragment = AboutMeFragment_.builder().user(user).build();
			getFragmentManager().beginTransaction().add(android.R.id.content, fragment).commit();
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
