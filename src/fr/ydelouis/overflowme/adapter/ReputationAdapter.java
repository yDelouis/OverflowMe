package fr.ydelouis.overflowme.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import fr.ydelouis.overflowme.api.entity.RepChange;
import fr.ydelouis.overflowme.common.EndlessExpListAdapter;
import fr.ydelouis.overflowme.util.DateUtil;
import fr.ydelouis.overflowme.view.RepChangeGroupListItem;
import fr.ydelouis.overflowme.view.RepChangeGroupListItem_;
import fr.ydelouis.overflowme.view.RepChangeListItem;
import fr.ydelouis.overflowme.view.RepChangeListItem_;

public class ReputationAdapter extends EndlessExpListAdapter<RepChange>
{
	private List<List<RepChange>> repChanges = new ArrayList<List<RepChange>>();
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		super.getGroupView(groupPosition, isExpanded, convertView, parent);
		RepChangeGroupListItem view;
		if(convertView != null)
			view = (RepChangeGroupListItem) convertView;
		else 
			view = RepChangeGroupListItem_.build(parent.getContext());
		
		view.bind(repChanges.get(groupPosition), isExpanded);
		
		return view;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		RepChangeListItem view;
		if(convertView != null)
			view = (RepChangeListItem) convertView;
		else 
			view = RepChangeListItem_.build(parent.getContext());
		
		view.bind(repChanges.get(groupPosition).get(childPosition));
		
		return view;
	}
	
	@Override
	public void addAll(List<RepChange> newRepChanges) {
		boolean added;
		for(RepChange newRepChange : newRepChanges) {
			added = false;
			for(List<RepChange> repChangesGroup : repChanges) {
				if(DateUtil.getDay(newRepChange.getCreationDate()) == DateUtil.getDay(repChangesGroup.get(0).getCreationDate())) {
					repChangesGroup.add(newRepChange);
					added = true;
				}
			}
			if(!added) {
				List<RepChange> newRepChangesGroup = new ArrayList<RepChange>();
				newRepChangesGroup.add(newRepChange);
				repChanges.add(newRepChangesGroup);
			}
		}
	}
	
	@Override
	public void clear() {
		repChanges.clear();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return repChanges.get(groupPosition);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return repChanges.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return repChanges.get(groupPosition).get(childPosition).getId();
	}

	
	@Override
	public int getChildrenCount(int groupPosition) {
		return repChanges.get(groupPosition).size();
	}

	@Override
	public int getGroupCount() {
		return repChanges.size();
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
