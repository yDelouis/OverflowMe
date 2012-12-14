package fr.ydelouis.overflowme.view;

import java.util.List;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.RepChange;
import fr.ydelouis.overflowme.util.DateUtil;

@EViewGroup(R.layout.listitem_repchangegroup)
public class RepChangeGroupListItem extends LinearLayout
{
	@ViewById(R.id.repChangeGroupItem_expanded)
	protected ImageView expanded;
	@ViewById(R.id.repChangeGroupItem_total)
	protected TextView totalText;
	@ViewById(R.id.repChangeGroupItep_date)
	protected TextView dateText;
	
	public RepChangeGroupListItem(Context context) {
		super(context);
	}
	
	public void bind(List<RepChange> repChanges, boolean isExpanded) {
		expanded.setImageResource(isExpanded ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_right);
		
		int total = 0;
		for(RepChange repChange : repChanges)
			total += repChange.getChange();
		totalText.setText((total > 0 ? "+" : "") + total);
		totalText.setTextColor(getResources().getColor(total > 0 ? R.color.green : R.color.maroon));
		
		dateText.setText(DateUtil.toDateString(repChanges.get(0).getCreationDate()));
	}
}
