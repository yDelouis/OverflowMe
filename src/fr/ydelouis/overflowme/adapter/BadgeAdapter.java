package fr.ydelouis.overflowme.adapter;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import fr.ydelouis.overflowme.api.entity.Badge;
import fr.ydelouis.overflowme.common.EndlessExpListAdapter;
import fr.ydelouis.overflowme.view.BadgeDescListItem;
import fr.ydelouis.overflowme.view.BadgeDescListItem_;
import fr.ydelouis.overflowme.view.BadgeListItem;
import fr.ydelouis.overflowme.view.BadgeListItem_;

public class BadgeAdapter extends EndlessExpListAdapter<Badge>
{
	private List<Badge> badges = new ArrayList<Badge>();
	private List<Badge> myBadges;
	
	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		super.getGroupView(groupPosition, isExpanded, convertView, parent);
		BadgeListItem view;
		if(convertView != null)
			view = (BadgeListItem) convertView;
		else
			view = BadgeListItem_.build(parent.getContext());
		
		Badge badge = badges.get(groupPosition);
		boolean awarded = true;
		if(myBadges != null)
			awarded = myBadges.contains(badge);
		view.bind(badge, awarded);
		
		return view;
	}
	
	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		BadgeDescListItem view;
		if(convertView != null)
			view = (BadgeDescListItem) convertView;
		else
			view = BadgeDescListItem_.build(parent.getContext());
		
		Badge badge = badges.get(groupPosition);
		view.bind(badge);
		
		return view;
	}

	@Override
	public void addAll(List<Badge> newData) {
		badges.addAll(newData);
	}
	
	@Override
	public void clear() {
		badges.clear();
	}
	
	public void setMyBadges(List<Badge> myBadges) {
		this.myBadges = myBadges;
	}
	
	@Override
	public Object getGroup(int groupPosition) {
		return badges.get(groupPosition);
	}
	
	@Override
	public int getGroupCount() {
		return badges.size();
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return badges.get(groupPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}
	
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 1;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}
}
