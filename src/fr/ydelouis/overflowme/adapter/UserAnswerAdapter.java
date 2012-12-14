package fr.ydelouis.overflowme.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import fr.ydelouis.overflowme.api.entity.Answer;
import fr.ydelouis.overflowme.common.EndlessListAdapter;
import fr.ydelouis.overflowme.view.UserAnswerListItem;
import fr.ydelouis.overflowme.view.UserAnswerListItem_;

public class UserAnswerAdapter extends EndlessListAdapter<Answer>
{
	public UserAnswerAdapter(Context context) {
		super(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		super.getView(position, convertView, parent);
		UserAnswerListItem view;
		if(convertView != null)
			view = (UserAnswerListItem) convertView;
		else 
			view = UserAnswerListItem_.build(getContext());
		
		view.bind(getItem(position));
		
		return view;
	}
}
