package fr.ydelouis.overflowme.activity;

import android.content.Intent;
import android.os.Bundle;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OnActivityResult;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.receiver.MyStateUpdator_;
import fr.ydelouis.overflowme.model.MeStore;

@EActivity(R.layout.activity_main)
public class MainActivity extends MenuActivity 
{
	private static final int REQUESTCODE_AUTH = 34943;
	
	@Bean
	protected MeStore meStore;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(meStore.getMe() == null)
			AuthActivity_.intent(this).startForResult(REQUESTCODE_AUTH);
		sendBroadcast(new Intent(this, MyStateUpdator_.class));
	}
	
	@OnActivityResult(REQUESTCODE_AUTH)
	protected void onAuthResult(int resultCode) {
		if(resultCode != RESULT_OK)
			finish();
	}
}
