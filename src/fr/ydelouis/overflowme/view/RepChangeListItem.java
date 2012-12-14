package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.RepChange;

@EViewGroup(R.layout.listitem_repchange)
public class RepChangeListItem extends LinearLayout
{
	@ViewById(R.id.reputationItem_change)
	protected TextView change;
	@ViewById(R.id.reputationItem_title)
	protected TextView title;
	
	public RepChangeListItem(Context context) {
		super(context);
	}
	
	public void bind(RepChange repChange) {
		change.setText(String.valueOf(repChange.getChange()));
		change.setTextColor(getResources().getColor(repChange.getChange() > 0 ? R.color.green : R.color.maroon));
		
		title.setText(repChange.getTitle());
		
		int iconId = 0;
		switch (repChange.getType()) {
			case answer_accepted: iconId = R.drawable.ic_rep_answeraccepted; break;
			case answer_unaccepted: iconId = R.drawable.ic_rep_answerunaccepted; break;
			case post_upvoted: iconId = R.drawable.ic_rep_postupvoted; break;
			case post_unupvoted: iconId = R.drawable.ic_rep_postunupvoted; break;
			case post_downvoted: iconId = R.drawable.ic_rep_postdownvoted; break;
			case post_undownvoted: iconId = R.drawable.ic_rep_postundownvoted; break;
		}
		title.setCompoundDrawablesWithIntrinsicBounds(iconId, 0, 0, 0);
	}
}
