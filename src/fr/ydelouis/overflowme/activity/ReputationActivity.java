package fr.ydelouis.overflowme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.User;
import fr.ydelouis.overflowme.fragment.*;
import fr.ydelouis.overflowme.view.UserReputationTitle;

@EActivity(R.layout.activity_reputation)
public class ReputationActivity extends Activity
{
	@Extra
	protected User user;
	
	@ViewById(R.id.reputation_title)
	protected UserReputationTitle title;
	protected ReputationFragment fragment;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(user.getDisplayName());
		
		if(savedInstanceState == null) {
			fragment = ReputationFragment_.builder().userId(user.getId()).build();
			getFragmentManager().beginTransaction().add(R.id.reputation_fragment, fragment).commit();
		}
	}
	
	@AfterViews
	protected void initViews() {
		title.bind(user);
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
