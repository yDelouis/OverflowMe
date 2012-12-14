package fr.ydelouis.overflowme.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EViewGroup;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.entity.User;

@EViewGroup(R.layout.view_user_votestitle)
public class UserVotesTitle extends UserSectionTitle
{
	@ViewById(R.id.user_votesTitle_UpText)
	protected TextView voteUpCount;
	@ViewById(R.id.user_votesTitle_DownText)
	protected TextView voteDownCount;
	
	public UserVotesTitle(Context context) {
		super(context);
	}
	
	public UserVotesTitle(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public UserVotesTitle(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@AfterViews
	protected void init() {
		setTitle(R.string.votes);
	}

	public void bind(User user) {
		setCount(user.getVoteCount());
		voteUpCount.setText(String.valueOf(user.getVoteUpCount()));
		voteDownCount.setText(String.valueOf(user.getVoteDownCount()));
	}
}
