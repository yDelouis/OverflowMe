package fr.ydelouis.overflowme.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.ItemClick;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.slidingmenu.lib.SlidingMenu.OnOpenedListener;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.adapter.NotifAdapter;
import fr.ydelouis.overflowme.entity.Notif;
import fr.ydelouis.overflowme.model.DatabaseHelper;
import fr.ydelouis.overflowme.model.MeStore;
import fr.ydelouis.overflowme.model.NotifDao;
import fr.ydelouis.overflowme.receiver.MyStateUpdator;
import fr.ydelouis.overflowme.util.DateUtil;
import fr.ydelouis.overflowme.util.NotifManager;
import fr.ydelouis.overflowme.view.MenuMeView;

@EFragment(R.layout.fragment_menu)
public class MenuFragment extends Fragment implements OnClosedListener, OnOpenedListener
{
	@Bean
	protected MeStore meStore;
	@OrmLiteDao(helper = DatabaseHelper.class, model = Notif.class)
	protected NotifDao notifDao;
	private List<Notif> notifs = new ArrayList<Notif>();
	private boolean hasBeenOpened = false;
	private MenuListener menuListener;
	
	@ViewById(R.id.menu_me)
	protected MenuMeView meView;
	@ViewById(R.id.menu_notifsList)
	protected ListView notifsList;
	@ViewById(R.id.menu_notifsCounter)
	protected TextView notifsCounter;
	
	@AfterViews
	protected void init() {
		notifsList.setAdapter(new NotifAdapter(getActivity(), notifs));
		update();
	}
	
	@Override
	public void onResume() {
		super.onResume();
		update();
		getActivity().registerReceiver(updateReceiver, new IntentFilter(MyStateUpdator.EVENT_MYSTATEUPDATED));
	}
	
	@Override
	public void onPause() {
		getActivity().unregisterReceiver(updateReceiver);
		super.onPause();
	}
	
	@UiThread
	public void update() {
		meView.bind(meStore.getMe(), meStore.getLastSeenMe());
		try {
			List<Notif> newNotifs = notifDao.queryByCreationDate();
			notifs.clear();
			notifs.addAll(newNotifs);
			((BaseAdapter) notifsList.getAdapter()).notifyDataSetChanged();
			int nbUnread = Notif.getNbUnread(notifs, meStore.getLastSeenDate());
			notifsCounter.setText(String.valueOf(nbUnread));
			if(nbUnread == 0)
				notifsCounter.setVisibility(View.INVISIBLE);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Click({R.id.menu_me, R.id.menu_settings, R.id.menu_logout, R.id.menu_questions,
			R.id.menu_badges, R.id.menu_users, R.id.menu_tags})
	public void onMenuItemClicked(View v) {
		if(menuListener != null)
			menuListener.onMenuItemClicked(v.getId());
	}
	
	@ItemClick(R.id.menu_notifsList)
	public void onNotifItemClicked(Notif notif) {
		if(menuListener != null)
			menuListener.onNotifClicked(notif);
	}
	
	public void setSelectedItem(int selectedItem) {
		switch (selectedItem) {
			case R.id.menu_questions:
			case R.id.menu_badges:
			case R.id.menu_users:
			case R.id.menu_tags:
				getView().findViewById(selectedItem).setBackgroundResource(R.color.orange);
				break;
		}
	}
	
	public void setMenuListener(MenuListener menuListener) {
		this.menuListener = menuListener;
	}
	
	@Override
	public void onOpened() {
		hasBeenOpened = true;
		NotifManager.cancel(getActivity());
	}
	
	@Override
	public void onClosed() {
		if(hasBeenOpened) {
			hasBeenOpened = false;
			meStore.saveLastSeenMe(meStore.getMe());
			meStore.setLastSeenDate(DateUtil.toSoTime(new Date()));
		}
	}
	
	private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			update();
		}
	};
	
	public interface MenuListener {
		public void onMenuItemClicked(int menuItemId);
		public void onNotifClicked(Notif notif);
	}
}
