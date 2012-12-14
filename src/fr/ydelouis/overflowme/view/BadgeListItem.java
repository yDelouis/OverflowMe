package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.Badge;
import fr.ydelouis.overflowme.api.entity.Badge.Type;

@EViewGroup(R.layout.listitem_badge)
public class BadgeListItem extends LinearLayout
{
	@ViewById(R.id.badgeItem_awarded)
	protected ImageView awardedImage;
	@ViewById(R.id.badgeItem_bkg)
	protected View badgeBkg;
	@ViewById(R.id.badgeItem_rank)
	protected ImageView rankImage;
	@ViewById(R.id.badgeItem_name)
	protected TextView nameText;
	@ViewById(R.id.badgeItem_awardCount)
	protected TextView awardCountText;
	
	public BadgeListItem(Context context) {
		super(context);
	}
	
	public void bind(Badge badge, boolean awarded) {
		awardedImage.setVisibility(awarded ? View.VISIBLE : View.INVISIBLE);
		if(badge.getType() == Type.named) {
			badgeBkg.setBackgroundResource(R.drawable.bkg_badge_named);
			nameText.setTextColor(getResources().getColor(R.color.white));
		} else {
			badgeBkg.setBackgroundResource(R.drawable.bkg_badge_tag);
			nameText.setTextColor(getResources().getColor(R.color.grayDark));
		}
		int rankResId = 0;
		switch (badge.getRank()) {
			case gold:
				rankResId = R.drawable.ic_badge_gold;
				break;
			case silver:
				rankResId = R.drawable.ic_badge_silver;
				break;
			case bronze:
				rankResId = R.drawable.ic_badge_bronze;
				break;
		}
		rankImage.setImageResource(rankResId);
		nameText.setText(badge.getName());
		awardCountText.setText("x"+badge.getAwardCount());
	}
}
