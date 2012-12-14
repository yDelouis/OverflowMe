package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.Badge;

@EViewGroup(R.layout.listitem_badgedesc)
public class BadgeDescListItem extends LinearLayout
{
	@ViewById(R.id.badgeDescItem_desc)
	protected TextView description;
	
	public BadgeDescListItem(Context context) {
		super(context);
	}
	
	public void bind(Badge badge) {
		description.setText(Html.fromHtml(badge.getDescription()).toString());
	}
}
