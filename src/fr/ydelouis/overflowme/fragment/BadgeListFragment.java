package fr.ydelouis.overflowme.fragment;

import java.util.List;

import android.app.Fragment;
import android.widget.ExpandableListView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.adapter.BadgeAdapter;
import fr.ydelouis.overflowme.api.entity.Badge;
import fr.ydelouis.overflowme.common.DataProvider;

@EFragment(R.layout.fragment_badgelist)
public class BadgeListFragment extends Fragment
{
	@ViewById(R.id.badgeList_list)
	protected ExpandableListView list;
	private DataProvider<Badge> dataProvider;
	private List<Badge> myBadges;
	
	@AfterViews
	public void init() {
		BadgeAdapter adapter = new BadgeAdapter();
		adapter.setDataProvider(dataProvider);
		adapter.setMyBadges(myBadges);
		list.setAdapter(adapter);
	}
	
	@UiThread
	public void reset() {
		init();
	}
	
	public void setDataProvider(DataProvider<Badge> dataProvider) {
		this.dataProvider = dataProvider;
	}
	
	public void setMyBadges(List<Badge> myBadges) {
		this.myBadges = myBadges;
	}
}
