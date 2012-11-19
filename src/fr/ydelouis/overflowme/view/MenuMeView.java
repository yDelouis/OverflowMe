package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.BadgeCount;
import fr.ydelouis.overflowme.api.entity.User;

@EViewGroup(R.layout.view_menume)
public class MenuMeView extends RelativeLayout
{
	@ViewById(R.id.menuMe_image)
	protected ImageView image;
	@ViewById(R.id.menuMe_displayName)
	protected TextView displayName;
	@ViewById(R.id.menuMe_reputation)
	protected TextView reputation;
	@ViewById(R.id.menuMe_reputationChange)
	protected TextView reputationChange;
	@ViewById(R.id.menuMe_goldPoint)
	protected View goldPoint;
	@ViewById(R.id.menuMe_goldText)
	protected TextView goldText;
	@ViewById(R.id.menuMe_goldChange)
	protected TextView goldChange;
	@ViewById(R.id.menuMe_silverPoint)
	protected View silverPoint;
	@ViewById(R.id.menuMe_silverText)
	protected TextView silverText;
	@ViewById(R.id.menuMe_silverChange)
	protected TextView silverChange;
	@ViewById(R.id.menuMe_bronzePoint)
	protected View bronzePoint;
	@ViewById(R.id.menuMe_bronzeText)
	protected TextView bronzeText;
	@ViewById(R.id.menuMe_bronzeChange)
	protected TextView bronzeChange;
	
	public MenuMeView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void bind(User me, User lastSeenMe) {
		if(me == null)
			return;
		new AQuery(image).image(me.getImageUrl());
		displayName.setText(me.getDisplayName());
		reputation.setText(String.format(getContext().getString(R.string.menuMe_reputation), me.getReputationString()));
		BadgeCount mbc = me.getBadgeCount();
		goldPoint.setVisibility(mbc.getGold() == 0 ? View.GONE : View.VISIBLE);
		goldText.setVisibility(mbc.getGold() == 0 ? View.GONE : View.VISIBLE);
		silverPoint.setVisibility(mbc.getSilver() == 0 ? View.GONE : View.VISIBLE);
		silverText.setVisibility(mbc.getSilver() == 0 ? View.GONE : View.VISIBLE);
		bronzePoint.setVisibility(mbc.getBronze() == 0 ? View.INVISIBLE : View.VISIBLE);
		bronzeText.setVisibility(mbc.getBronze() == 0 ? View.INVISIBLE : View.VISIBLE);
		goldText.setText(String.valueOf(mbc.getGold()));
		silverText.setText(String.valueOf(mbc.getSilver()));
		bronzeText.setText(String.valueOf(mbc.getBronze()));
		
		if(lastSeenMe != null) {
			BadgeCount lsmbc = lastSeenMe.getBadgeCount();
			setChange(reputationChange, me.getReputation(), lastSeenMe.getReputation());
			setChange(goldChange, mbc.getGold(), lsmbc.getGold());
			setChange(silverChange, mbc.getSilver(), lsmbc.getSilver());
			setChange(bronzeChange, mbc.getBronze(), lsmbc.getBronze());
		}
	}
	
	private void setChange(TextView textView, int value, int lastSeenValue) {
		if(value == lastSeenValue)
			textView.setVisibility(View.GONE);
		else {
			textView.setVisibility(View.VISIBLE);
			textView.setText("("+(value-lastSeenValue > 0 ? "+" : "")+(value-lastSeenValue)+")");
		}
	}
}
