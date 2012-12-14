package fr.ydelouis.overflowme.fragment;

import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.adapter.UserAnswerAdapter;
import fr.ydelouis.overflowme.adapter.UserAnswerExpAdapter;
import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.entity.Answer;
import fr.ydelouis.overflowme.api.rest.UserRest;
import fr.ydelouis.overflowme.common.DataProvider;
import fr.ydelouis.overflowme.model.MeStore;

@EFragment(R.layout.fragment_useranswerlist)
public class UserAnswerListFragment extends Fragment
{
	private static final int PAGE_SIZE = 30;

	@ViewById(R.id.userAnswerList_activityList)
	protected ExpandableListView activityList;
	@ViewById(R.id.userAnswerList_scoreList)
	protected ListView scoreList;
	@FragmentArg
	protected int userId;
	@Bean
	protected MeStore meStore;
	@RestService
	protected UserRest userRest;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
	}
	
	@AfterViews
	protected void init() {
		Api.prepare(getActivity(), userRest.getRestTemplate());
		
		if(userId == 0)
			userId = meStore.getMe().getId();
		
		initLists();
	}
	
	@UiThread
	protected void initLists() {
		UserAnswerAdapter answerAdapter = new UserAnswerAdapter(getActivity());
		answerAdapter.setDataProvider(scoreDataProvider);
		scoreList.setAdapter(answerAdapter);
		UserAnswerExpAdapter answerExpAdapter = new UserAnswerExpAdapter();
		answerExpAdapter.setDataProvider(activityDataProvider);
		activityList.setAdapter(answerExpAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_useranswerlist, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.userAnswerList_sortActivity:
				scoreList.setVisibility(View.GONE);
				activityList.setVisibility(View.VISIBLE);
				break;
			case R.id.userAnswerList_sortScore:
				scoreList.setVisibility(View.VISIBLE);
				activityList.setVisibility(View.GONE);
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	private DataProvider<Answer> scoreDataProvider = new DataProvider<Answer>() {
		@Override
		public List<Answer> loadPage(int pageNumber) {
			return userRest.getAnswersByScore(userId, pageNumber+1, PAGE_SIZE).getItems();
		}
	};
	
	private DataProvider<Answer> activityDataProvider = new DataProvider<Answer>() {
		@Override
		public List<Answer> loadPage(int pageNumber) {
			return userRest.getAnswersByActivity(userId, pageNumber+1, PAGE_SIZE).getItems();
		}
	};
}
