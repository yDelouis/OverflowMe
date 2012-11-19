package fr.ydelouis.overflowme.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingActivity;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.fragment.MenuFragment;
import fr.ydelouis.overflowme.fragment.MenuFragment_;

public abstract class MenuActivity extends SlidingActivity
{
	private final static int ID_MENUFRAME = 1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		customizeActionBar();
		customizeSlidingMenu();
		setMenu();
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
		getSlidingMenu().setOnClosedListener(menuFragment);
		ft.replace(ID_MENUFRAME, menuFragment);
		ft.commit();
	}
	
	private void customizeSlidingMenu() {
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingMenu_behindOffset);
		sm.setShadowWidthRes(R.dimen.slidingMenu_shadowWidth);
		sm.setShadowDrawable(R.drawable.slidingmenu_shadow);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				toggle();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
