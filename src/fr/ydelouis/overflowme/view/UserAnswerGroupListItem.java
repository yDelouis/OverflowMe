package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.util.DateUtil;

@EViewGroup(R.layout.listitem_answergroup)
public class UserAnswerGroupListItem extends LinearLayout
{
	@ViewById(R.id.answerGroupItem_expanded)
	protected ImageView expanded;
	@ViewById(R.id.answerGroupItep_date)
	protected TextView dateText;
	
	public UserAnswerGroupListItem(Context context) {
		super(context);
	}
	
	public void bind(long date, boolean isExpanded) {
		expanded.setImageResource(isExpanded ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_right);
		dateText.setText(DateUtil.toDateString(date));
	}
}
