package fr.ydelouis.overflowme.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.entity.Notif;
import fr.ydelouis.overflowme.fragment.MenuFragment;
import fr.ydelouis.overflowme.fragment.MenuFragment.MenuListener;
import fr.ydelouis.overflowme.fragment.MenuFragment_;

@EActivity
public abstract class MenuActivity extends SlidingActivity implements MenuListener
{
	private static final String EXTRA_STARTMENUOPENED = "extra_startMenuOpened";
	private static final long TIME_BEFORE_CLOSE_MENU = 100;
	private static final int ID_MENUFRAME = 1;
	
	@Extra(EXTRA_STARTMENUOPENED)
	protected boolean startMenuOpened = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		customizeActionBar();
		customizeSlidingMenu();
		setMenu();
	}
	
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		if(startMenuOpened && savedInstanceState == null)
			startMenuOpened();
	}
	
	private void customizeActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		findViewById(android.R.id.home).setPadding(10, 0, 0, 0);
	}
	
	private void setMenu() {
		FrameLayout frameLayout = new FrameLayout(this);
		frameLayout.setId(ID_MENUFRAME);
		setBehindContentView(frameLayout);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		MenuFragment menuFragment = new MenuFragment_();
		menuFragment.setMenuListener(this);
		getSlidingMenu().setOnClosedListener(menuFragment);
		getSlidingMenu().setOnOpenedListener(menuFragment);
		ft.replace(ID_MENUFRAME, menuFragment);
		ft.commit();
	}
	
	private void customizeSlidingMenu() {
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingMenu_behindOffset);
		sm.setShadowWidthRes(R.dimen.slidingMenu_shadowWidth);
		sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
	}
	
	private void startMenuOpened() {
		getSlidingMenu().showBehind(false);
		getSlidingMenu().postDelayed(new Runnable() {
			public void run() {
				getSlidingMenu().showAbove();
			}
		}, TIME_BEFORE_CLOSE_MENU);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				getSlidingMenu().toggle();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onMenuItemClicked(int menuItemId) {
		if(menuItemId == getCurrentMenuItemId())
			getSlidingMenu().toggle();
		else {
			switch (menuItemId) {
				case R.id.menu_me:
					startActivityDiscretly(MainActivity_.class);
					break;
				case R.id.menu_settings:
					startActivity(new Intent(this, SettingsActivity.class));
					getSlidingMenu().showAbove(false);
					break;
			}
		}
	}
	
	@Override
	public void onNotifClicked(Notif notif) {
		
	}
	
	private void startActivityDiscretly(Class<?> activityClass) {
		Intent intent = new Intent(this, activityClass);
		intent.putExtra(EXTRA_STARTMENUOPENED, true);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		overridePendingTransition(0, 0);
		finish();
	}
	
	protected abstract int getCurrentMenuItemId();
}
