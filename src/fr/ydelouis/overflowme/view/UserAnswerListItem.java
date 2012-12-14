package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.Answer;

@EViewGroup(R.layout.listitem_answer)
public class UserAnswerListItem extends LinearLayout
{
	@ViewById(R.id.answerItem_score)
	protected TextView score;
	@ViewById(R.id.answerItem_title)
	protected TextView title;
	
	public UserAnswerListItem(Context context) {
		super(context);
	}
	
	public void bind(Answer answer) {
		title.setText(answer.getTitle());
		
		score.setText(String.valueOf(answer.getScore()));
		if(answer.isAccepted()) {
			score.setTextColor(getResources().getColor(R.color.yellow));
			score.setBackgroundColor(getResources().getColor(R.color.greenDark));
		} else {
			if(answer.getScore() >= 0)
				score.setTextColor(getResources().getColor(R.color.grayDark));
			else
				score.setTextColor(getResources().getColor(R.color.maroon));
			score.setBackgroundColor(getResources().getColor(R.color.grayLight));
		}
	}
	
	@Override
	public void setBackgroundColor(int color) {
		super.setBackgroundColor(color);
		if(((ColorDrawable) score.getBackground()).getColor() != getResources().getColor(R.color.greenDark)
				&& color == getResources().getColor(R.color.grayLight))
			score.setBackgroundColor(getResources().getColor(R.color.grayMiddleLight));
	}
}
