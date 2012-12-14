package fr.ydelouis.overflowme.activity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.j256.ormlite.misc.TransactionManager;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.entity.Badge;
import fr.ydelouis.overflowme.api.entity.Badge.BadgeRequest;
import fr.ydelouis.overflowme.api.entity.User;
import fr.ydelouis.overflowme.api.rest.UserRest;
import fr.ydelouis.overflowme.common.DataProvider;
import fr.ydelouis.overflowme.fragment.BadgeListFragment;
import fr.ydelouis.overflowme.fragment.BadgeListFragment_;
import fr.ydelouis.overflowme.model.BadgeDao;
import fr.ydelouis.overflowme.model.DatabaseHelper;
import fr.ydelouis.overflowme.model.MeStore;
import fr.ydelouis.overflowme.util.DateUtil;
import fr.ydelouis.overflowme.view.UserBadgesTitle;

@EActivity(R.layout.activity_userbadges)
public class UserBadgesActivity extends Activity implements DataProvider<Badge>
{
	private static final int PAGE_SIZE = 20;
	
	@Extra
	protected User user;
	@OrmLiteDao(helper = DatabaseHelper.class, model = Badge.class)
	protected BadgeDao badgeDao;
	@Bean
	protected MeStore meStore;
	@RestService
	protected UserRest userRest;
	
	@ViewById(R.id.userBadges_title)
	protected UserBadgesTitle title;
	private BadgeListFragment fragment;
	private boolean newInstance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle(user.getDisplayName());
		
		Api.prepare(this, userRest.getRestTemplate());
		newInstance = (savedInstanceState == null);
		if(newInstance) {
			fragment = BadgeListFragment_.builder().build();
			fragment.setDataProvider(this);
			getFragmentManager().beginTransaction().add(R.id.userBadges_fragment, fragment).commit();
		} else
			fragment.setDataProvider(this);
	}
	
	@AfterViews
	protected void initViews() {
		title.bind(user.getBadgeCount());
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				if(meStore.isMe(user) && newInstance)
					loadMyNewBadges();
			}
		});
	}
	
	@Background
	protected void loadMyNewBadges() {
		setProgressBarVisible(true);
		Boolean hasNew = false;
		try {
			hasNew = TransactionManager.callInTransaction(badgeDao.getConnectionSource(), updateLoader);
		} catch (SQLException e) {}
		System.out.println(hasNew);
		if(hasNew != null && hasNew)
			fragment.reset();
		setProgressBarVisible(false);
	}
	
	@UiThread
	protected void setProgressBarVisible(boolean isVisible) {
		setProgressBarIndeterminateVisibility(isVisible);
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

	@Override
	public List<Badge> loadPage(int pageNumber) {
		if(meStore.isMe(user)) {
			try {
				return badgeDao.queryByName(pageNumber, PAGE_SIZE);
			} catch (SQLException e) { return new ArrayList<Badge>(); }
		} else
			return userRest.getBadges(user.getId(), pageNumber+1, PAGE_SIZE).getItems();
	}
	
	private Callable<Boolean> updateLoader = new Callable<Boolean>() {
		public Boolean call() throws Exception {
			long fromDate = meStore.getLastBadgeDownload(); 
			BadgeRequest request;
			int page = 1;
			boolean hasNew = false;
			do {
				request = userRest.getBadgesSince(user.getId(), page, PAGE_SIZE, fromDate);
				if(request.getItems().size() > 0) {
					badgeDao.createOrUpdateAll(request.getItems());
					hasNew = true;
				}
				page++;
				if(isFinishing())
					throw new Exception();
			} while(request != null && request.hasMore());
			meStore.setLastBadgeDownload(DateUtil.now());
			return hasNew;
		};
	};
}
