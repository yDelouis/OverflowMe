package fr.ydelouis.overflowme.activity;

import android.content.Intent;
import android.os.Bundle;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.FragmentById;
import com.googlecode.androidannotations.annotations.OnActivityResult;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.User;
import fr.ydelouis.overflowme.fragment.UserFragment;
import fr.ydelouis.overflowme.fragment.UserFragment.UserDetailOpener;
import fr.ydelouis.overflowme.model.MeStore;
import fr.ydelouis.overflowme.receiver.*;

@EActivity(R.layout.activity_main)
public class MainActivity extends MenuActivity implements UserDetailOpener
{
	private static final int REQUESTCODE_AUTH = 34943;
	
	@Bean
	protected MeStore meStore;
	@FragmentById(R.id.main_meFragment)
	protected UserFragment userFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(meStore.getMe() == null)
			AuthActivity_.intent(this).startForResult(REQUESTCODE_AUTH);
		if(savedInstanceState == null)
			sendBroadcast(new Intent(this, MyStateUpdator_.class));
	}
	
	@AfterViews
	protected void init() {
		userFragment.setUserDetailOpener(this);
	}
	
	@OnActivityResult(REQUESTCODE_AUTH)
	protected void onAuthResult(int resultCode) {
		if(resultCode == RESULT_OK)
			userFragment.init();
		else
			finish();
	}

	@Override
	protected int getCurrentMenuItemId() {
		return R.id.menu_me;
	}

	@Override
	public void openDetail(User user, int itemId) {
		switch (itemId) {
			case R.id.user_reputation:
				ReputationActivity_.intent(this).user(user).start();
				break;
			case R.id.user_aboutMe:
				AboutMeActivity_.intent(this).user(user).start();
				break;
			case R.id.user_badges:
				UserBadgesActivity_.intent(this).user(user).start();
				break;
			case R.id.user_answers:
				UserAnswersActivity_.intent(this).user(user).start();
				break;
		}
	}
}
