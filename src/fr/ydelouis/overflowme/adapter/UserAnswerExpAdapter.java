package fr.ydelouis.overflowme.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.Answer;
import fr.ydelouis.overflowme.common.EndlessExpListAdapter;
import fr.ydelouis.overflowme.util.DateUtil;
import fr.ydelouis.overflowme.view.UserAnswerGroupListItem;
import fr.ydelouis.overflowme.view.UserAnswerGroupListItem_;
import fr.ydelouis.overflowme.view.UserAnswerListItem;
import fr.ydelouis.overflowme.view.UserAnswerListItem_;

public class UserAnswerExpAdapter extends EndlessExpListAdapter<Answer>
{
	private List<List<Answer>> answers = new ArrayList<List<Answer>>();
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		super.getGroupView(groupPosition, isExpanded, convertView, parent);
		UserAnswerGroupListItem view;
		if(convertView != null)
			view = (UserAnswerGroupListItem) convertView;
		else 
			view = UserAnswerGroupListItem_.build(parent.getContext());
		
		view.bind(answers.get(groupPosition).get(0).getLastActivityDate(), isExpanded);
		
		return view;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		UserAnswerListItem view;
		if(convertView != null)
			view = (UserAnswerListItem) convertView;
		else 
			view = UserAnswerListItem_.build(parent.getContext());
		
		view.bind(answers.get(groupPosition).get(childPosition));
		view.setBackgroundColor(parent.getContext().getResources().getColor(R.color.grayLight));
		
		return view;
	}
	
	@Override
	public void addAll(List<Answer> newAnswers) {
		boolean added;
		for(Answer newAnswer : newAnswers) {
			added = false;
			for(List<Answer> answersGroup : answers) {
				if(DateUtil.getDay(newAnswer.getLastActivityDate()) == DateUtil.getDay(answersGroup.get(0).getLastActivityDate())) {
					answersGroup.add(newAnswer);
					added = true;
				}
			}
			if(!added) {
				List<Answer> newAnswersGroup = new ArrayList<Answer>();
				newAnswersGroup.add(newAnswer);
				answers.add(newAnswersGroup);
			}
		}
	}
	
	@Override
	public void clear() {
		answers.clear();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return answers.get(groupPosition);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return answers.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return answers.get(groupPosition).get(childPosition).getId();
	}

	
	@Override
	public int getChildrenCount(int groupPosition) {
		return answers.get(groupPosition).size();
	}

	@Override
	public int getGroupCount() {
		return answers.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}
