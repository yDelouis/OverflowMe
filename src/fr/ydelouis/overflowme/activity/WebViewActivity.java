package fr.ydelouis.overflowme.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;

import fr.ydelouis.overflowme.fragment.WebViewFragment;
import fr.ydelouis.overflowme.fragment.WebViewFragment_;

@EActivity
public class WebViewActivity extends Activity
{
	private static final int ID_FRAGMENT = 1;
	
	@Extra
	protected String title;
	@Extra
	protected String url;
	protected WebViewFragment fragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		if(title != null)
			setTitle(title);
		
		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setId(ID_FRAGMENT);
		setContentView(frameLayout);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		fragment = WebViewFragment_.builder().url(url).build();
		ft.replace(ID_FRAGMENT, fragment);
		ft.commit();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
