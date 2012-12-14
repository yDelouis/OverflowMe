package fr.ydelouis.overflowme.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.entity.User;
import fr.ydelouis.overflowme.api.rest.UserRest;
import fr.ydelouis.overflowme.model.MeStore;
import fr.ydelouis.overflowme.receiver.MyStateUpdator;
import fr.ydelouis.overflowme.util.DateUtil;
import fr.ydelouis.overflowme.view.UserBadgesTitle;
import fr.ydelouis.overflowme.view.UserReputationTitle;
import fr.ydelouis.overflowme.view.UserSectionTitle;
import fr.ydelouis.overflowme.view.UserVotesTitle;

@EFragment(R.layout.fragment_user)
public class UserFragment extends Fragment
{
	@ViewById(R.id.user_image)
	protected ImageView image;
	@ViewById(R.id.user_displayName)
	protected TextView displayName;
	@ViewById(R.id.user_age)
	protected TextView age;
	@ViewById(R.id.user_cityLabel)
	protected TextView cityLabel;
	@ViewById(R.id.user_city)
	protected TextView city;
	@ViewById(R.id.user_countryLabel)
	protected TextView countryLabel;
	@ViewById(R.id.user_country)
	protected TextView country;
	@ViewById(R.id.user_seniority)
	protected TextView seniority;
	@ViewById(R.id.user_aboutMe)
	protected UserSectionTitle aboutMeSectionTitle;
	@ViewById(R.id.user_reputation)
	protected UserReputationTitle reputationSectionTitle;
	@ViewById(R.id.user_badges)
	protected UserBadgesTitle badgesSectionTitle;
	@ViewById(R.id.user_answers)
	protected UserSectionTitle answersSectionTitle;
	@ViewById(R.id.user_questions)
	protected UserSectionTitle questionsSectionTitle;
	@ViewById(R.id.user_tags)
	protected UserSectionTitle tagsSectionTitle;
	@ViewById(R.id.user_votes)
	protected UserVotesTitle votesSectionTitle;
	private ProgressDialog progressDialog;
	
	@Bean
	protected MeStore meStore;
	@RestService
	protected UserRest userRest;
	@FragmentArg
	protected int userId;
	private User user;
	private  UserDetailOpener userDetailOpener;
	private UserSectionTitle[] sectionTitles;
	
	@Override
	public void onResume() {
		super.onResume();
		if(userId == 0)
			getActivity().registerReceiver(updateReceiver, new IntentFilter(MyStateUpdator.EVENT_MYSTATEUPDATED));
	}
	
	@Override
	public void onPause() {
		getActivity().unregisterReceiver(updateReceiver);
		super.onPause();
	}
	
	@AfterViews
	public void init() {
		sectionTitles = new UserSectionTitle[]
				{ reputationSectionTitle, badgesSectionTitle, answersSectionTitle,
				questionsSectionTitle, tagsSectionTitle, votesSectionTitle };
		
		user = meStore.getMe();
		if(userId == 0) {
			fillViews();
		} else {
			getUser();
		}
	}
	
	@UiThread
	protected void fillViews() {
		displayName.setText(user.getDisplayName());
		new AQuery(image).image(user.getImageUrl());
		age.setText(String.valueOf(user.getAge()));
		if(user.getCountry() != null) {
			city.setText(user.getCity());
			country.setText(user.getCountry());
			country.setVisibility(View.VISIBLE);
			countryLabel.setVisibility(View.VISIBLE);
		} else {
			cityLabel.setText(R.string.location);
			city.setText(user.getLocation());
			countryLabel.setVisibility(View.GONE);
			country.setVisibility(View.GONE);
		}
		seniority.setText(DateUtil.toDurationString(user.getCreationDate()));
		
		reputationSectionTitle.bind(user);
		badgesSectionTitle.bind(user.getBadgeCount());
		answersSectionTitle.setCount(user.getAnswersCount());
		questionsSectionTitle.setCount(user.getQuestionsCount());
		tagsSectionTitle.setCount(user.getTagsCount());
		votesSectionTitle.bind(user);
		
		int maxCountWidth = 0;
		for(UserSectionTitle sectionTitle : sectionTitles)
			maxCountWidth = Math.max(maxCountWidth, sectionTitle.getCountWidth());
		for(UserSectionTitle sectionTitle : sectionTitles)
			sectionTitle.setCountWidth(maxCountWidth);
	}
	
	@Background
	protected void getUser() {
		showProgressDialog();
		Api.prepare(getActivity(), userRest.getRestTemplate());
		try {
			user = userRest.getUser(userId).getItems().get(0);
			user.setTagsCount(userRest.getNbTags(userId).getTotal());
			fillViews();
			dismissProgressDialog();
		} catch (RuntimeException e) {
			// TODO
		}
	}
	
	@UiThread
	protected void showProgressDialog() {
		progressDialog = new ProgressDialog(getActivity());
		progressDialog.setMessage(getString(R.string.user_loadingUser));
		progressDialog.show();
	}
	
	@UiThread
	protected void dismissProgressDialog() {
		if(progressDialog != null)
			progressDialog.dismiss();
	}
	
	@Click({R.id.user_aboutMe, R.id.user_reputation, R.id.user_badges,
			R.id.user_answers, R.id.user_questions, R.id.user_tags,
			R.id.user_votes})
	protected void openDetail(View v) {
		if(userDetailOpener != null)
			userDetailOpener.openDetail(user, v.getId());
	}
	
	public void setUserDetailOpener(UserDetailOpener userDetailOpener) {
		this.userDetailOpener = userDetailOpener;
	}
	
	private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			init();
		}
	};
	
	public interface UserDetailOpener {
		public void openDetail(User user, int itemId);
	}
}
