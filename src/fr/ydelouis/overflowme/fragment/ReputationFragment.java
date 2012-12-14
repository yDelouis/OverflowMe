package fr.ydelouis.overflowme.fragment;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.adapter.ReputationAdapter;
import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.entity.RepChange;
import fr.ydelouis.overflowme.api.rest.UserRest;
import fr.ydelouis.overflowme.common.DataProvider;
import fr.ydelouis.overflowme.loader.RepChangeLoader;
import fr.ydelouis.overflowme.model.DatabaseHelper;
import fr.ydelouis.overflowme.model.MeStore;
import fr.ydelouis.overflowme.model.RepChangeDao;

@EFragment(R.layout.fragment_reputation)
public class ReputationFragment extends Fragment implements DataProvider<RepChange>
{
	private static final int PAGE_SIZE = 30;
	
	@ViewById(R.id.reputation_list)
	protected ExpandableListView list;
	@FragmentArg
	protected int userId;
	@OrmLiteDao(helper = DatabaseHelper.class, model = RepChange.class)
	protected RepChangeDao repChangeDao;
	@Bean
	protected RepChangeLoader repChangeLoader;
	@Bean
	protected MeStore meStore;
	@RestService
	protected UserRest userRest;
	private boolean newInstance;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		newInstance = (savedInstanceState == null);
	}
	
	@AfterViews
	protected void init() {
		Api.prepare(getActivity(), userRest.getRestTemplate());
		
		if(userId == 0)
			userId = meStore.getMe().getId();

		initList();
			
		if(meStore.isMe(userId) && newInstance)
			loadMyNewReputationChanges();
	}
		
	@UiThread
	protected void initList() {
		ReputationAdapter repAdapter = new ReputationAdapter();
		repAdapter.setDataProvider(this);
		list.setAdapter(repAdapter);
	}
	
	@Background
	protected void loadMyNewReputationChanges() {
		setProgressBarVisibility(true);
		long lastRepChangeDate = 0;
		try {
			List<RepChange> repChanges = repChangeDao.queryByCreationDate(0, 1);
			if(repChanges.size() > 0)
				lastRepChangeDate = repChanges.get(0).getCreationDate();
		} catch(SQLException e) {}
		if(repChangeLoader.loadMyRepChangeSince(lastRepChangeDate, this))
			initList();
		setProgressBarVisibility(false);
	}
	
	@UiThread
	protected void setProgressBarVisibility(boolean isVisible) {
		if(getActivity() != null)
			getActivity().setProgressBarIndeterminateVisibility(isVisible);
	}
	
	@Override
	public List<RepChange> loadPage(int pageNumber) {
		if(meStore.isMe(userId)) {
			try {
				return repChangeDao.queryByCreationDate(pageNumber, PAGE_SIZE);
			} catch (SQLException e) { return new ArrayList<RepChange>(); }
		} else
			return repChangeLoader.getReputationHistory(userId, pageNumber+1, PAGE_SIZE);
	}
}
