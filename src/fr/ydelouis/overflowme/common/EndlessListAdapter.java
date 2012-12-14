package fr.ydelouis.overflowme.common;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class EndlessListAdapter<T> extends ArrayAdapter<T>
{
	private int pageNumber = 0;
	private int anticipation = 10;
	private boolean loading = false;
	private boolean hasReachEnd = false;
	private DataProvider<T> dataProvider;
	private View loadingView;
	private ListView listView;
	
	public EndlessListAdapter(Context context) {
		super(context, 0, new ArrayList<T>());
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		listView = (ListView) parent;
		if(dataProvider != null &&!loading && !hasReachEnd
				&& position > getCount() - anticipation)
			new LoadingTask().execute();
		return null;
	}
	
	public View getLoadingView() {
		return loadingView;
	}

	public void setLoadingView(View loadingView) {
		this.loadingView = loadingView;
	}
	
	public void setDataProvider(DataProvider<T> dataProvider) {
		this.dataProvider = dataProvider;
		if(getCount() == 0)
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
			return dataProvider.loadPage(pageNumber);
		}
		
		@Override
		protected void onPostExecute(List<T> newData) {
			if(newData.size() == 0)
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
