package fr.ydelouis.overflowme.common;

import java.util.List;

import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;

public abstract class EndlessExpListAdapter<T> extends BaseExpandableListAdapter
{
	private int pageNumber = 0;
	private int anticipation = 10;
	private boolean loading = false;
	private boolean hasReachEnd = false;
	private DataProvider<T> dataProvider;
	private View loadingView;
	private ListView listView;
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		listView = (ListView) parent;
		if(dataProvider != null &&!loading && !hasReachEnd
				&& groupPosition > getGroupCount() - anticipation)
			new LoadingTask().execute();
		return null;
	}
	
	public abstract void addAll(List<T> newData);
	public abstract void clear();
	
	public View getLoadingView() {
		return loadingView;
	}

	public void setLoadingView(View loadingView) {
		this.loadingView = loadingView;
	}

	public void setDataProvider(DataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
		if(getGroupCount() == 0)
			new LoadingTask().execute();
	}
	
	public void setAnticipation(int anticipation) {
		this.anticipation = anticipation;
	}
	
	public void reset() {
		pageNumber = 1;
		clear();
	}
	
	private class LoadingTask extends AsyncTask<Void, Void, List<T>>
	{
		@Override
		protected void onPreExecute() {
			loading = true;
			if(loadingView != null)
				listView.addFooterView(loadingView);
		}
		
		@Override
		protected List<T> doInBackground(Void... params) {
			if(dataProvider != null)
				return dataProvider.loadPage(pageNumber);
			return null;
		}
		
		@Override
		protected void onPostExecute(List<T> newData) {
			if(newData == null || newData.size() == 0)
				hasReachEnd = true;
			else {
				addAll(newData);
				notifyDataSetChanged();
				pageNumber++;
			}
			if(loadingView != null)
				listView.removeFooterView(loadingView);
			loading = false;
		}
	}
}
