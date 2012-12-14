package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.BadgeCount;

@EViewGroup(R.layout.view_user_badgestitle)
public class UserBadgesTitle extends UserSectionTitle
{
	@ViewById(R.id.user_badgesTitle_goldPoint)
	protected View goldPoint;
	@ViewById(R.id.user_badgesTitle_goldText)
	protected TextView goldText;
	@ViewById(R.id.user_badgesTitle_silverPoint)
	protected View silverPoint;
	@ViewById(R.id.user_badgesTitle_silverText)
	protected TextView silverText;
	@ViewById(R.id.user_badgesTitle_bronzePoint)
	protected View bronzePoint;
	@ViewById(R.id.user_badgesTitle_bronzeText)
	protected TextView bronzeText;
	
	public UserBadgesTitle(Context context) {
		super(context);
	}
	
	public UserBadgesTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public UserBadgesTitle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@AfterViews
	protected void init() {
		setTitle(R.string.badges);
	}
	
	public void bind(BadgeCount badgeCount) {
		setCount(badgeCount.getTotal());
		if(badgeCount.getGold() != 0) {
			goldPoint.setVisibility(View.VISIBLE);
			goldText.setVisibility(View.VISIBLE);
			goldText.setText(String.valueOf(badgeCount.getGold()));
		} else {
			goldPoint.setVisibility(View.GONE);
			goldText.setVisibility(View.GONE);
		}
		if(badgeCount.getSilver() != 0) {
			silverPoint.setVisibility(View.VISIBLE);
			silverText.setVisibility(View.VISIBLE);
			silverText.setText(String.valueOf(badgeCount.getSilver()));
		} else {
			silverPoint.setVisibility(View.GONE);
			silverText.setVisibility(View.GONE);
		}
		if(badgeCount.getBronze() != 0) {
			bronzePoint.setVisibility(View.VISIBLE);
			bronzeText.setVisibility(View.VISIBLE);
			bronzeText.setText(String.valueOf(badgeCount.getBronze()));
		} else {
			bronzePoint.setVisibility(View.GONE);
			bronzeText.setVisibility(View.GONE);
		}
	}
}
