package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.User;

@EViewGroup(R.layout.view_user_reputationtitle)
public class UserReputationTitle extends UserSectionTitle
{
	@ViewById(R.id.user_reputationTitle_change)
	protected TextView repChangeText;
	
	public UserReputationTitle(Context context) {
		super(context);
	}
	
	public UserReputationTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public UserReputationTitle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@AfterViews
	protected void init() {
		setTitle(R.string.reputation);
	}
	
	public void bind(User user) {
		setCount(user.getReputationString());
		String repChangeStr = (user.getReputationChangeWeek() > 0 ? "+" : "")+user.getReputationChangeWeek();
		repChangeText.setText(repChangeStr);
	}
}
