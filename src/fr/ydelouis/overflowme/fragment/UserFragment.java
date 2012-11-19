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
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;
import com.googlecode.androidannotations.annotations.rest.RestService;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.entity.BadgeCount;
import fr.ydelouis.overflowme.api.entity.User;
import fr.ydelouis.overflowme.api.rest.UserRest;
import fr.ydelouis.overflowme.model.MeStore;
import fr.ydelouis.overflowme.receiver.MyStateUpdator;
import fr.ydelouis.overflowme.util.DateUtil;

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
	@ViewById(R.id.user_aboutMeContainer)
	protected View aboutMeContainer;
	@ViewById(R.id.user_aboutMe)
	protected TextView aboutMe;
	@ViewById(R.id.user_reputationCount)
	protected TextView reputationCount;
	@ViewById(R.id.user_reputationChange)
	protected TextView reputationChange;
	@ViewById(R.id.user_badgesCount)
	protected TextView badgesCount;
	@ViewById(R.id.user_goldPoint)
	protected View goldPoint;
	@ViewById(R.id.user_goldText)
	protected TextView goldText;
	@ViewById(R.id.user_silverPoint)
	protected View silverPoint;
	@ViewById(R.id.user_silverText)
	protected TextView silverText;
	@ViewById(R.id.user_bronzePoint)
	protected View bronzePoint;
	@ViewById(R.id.user_bronzeText)
	protected TextView bronzeText;
	@ViewById(R.id.user_answersCount)
	protected TextView answersCount;
	@ViewById(R.id.user_questionsCount)
	protected TextView questionsCount;
	@ViewById(R.id.user_tagsCount)
	protected TextView tagsCount;
	@ViewById(R.id.user_votesCount)
	protected TextView votesCount;
	@ViewById(R.id.user_voteUpText)
	protected TextView voteUpCount;
	@ViewById(R.id.user_voteDownText)
	protected TextView voteDownCount;
	private ProgressDialog progressDialog;
	
	@Bean
	protected MeStore meStore;
	@RestService
	protected UserRest userRest;
	@FragmentArg
	protected int userId = 0;
	private User user;
	
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
	protected void init() {
		if(userId == 0) {
			user = meStore.getMe();
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
		if(user.getAboutMe().isEmpty())
			aboutMeContainer.setEnabled(false);
		aboutMe.setText(userId == 0 ? R.string.user_aboutMe : R.string.user_aboutHim);
		reputationCount.setText(user.getReputationString());
		reputationChange.setText((user.getReputationChangeWeek() >  0 ? "+" : "")+user.getReputationChangeWeek());
		BadgeCount bc = user.getBadgeCount();
		badgesCount.setText(String.valueOf(bc.getTotal()));
		if(bc.getGold() != 0) {
			goldPoint.setVisibility(View.VISIBLE);
			goldText.setVisibility(View.VISIBLE);
			goldText.setText(String.valueOf(bc.getGold()));
		} else {
			goldPoint.setVisibility(View.GONE);
			goldText.setVisibility(View.GONE);
		}
		if(bc.getSilver() != 0) {
			silverPoint.setVisibility(View.VISIBLE);
			silverText.setVisibility(View.VISIBLE);
			silverText.setText(String.valueOf(bc.getSilver()));
		} else {
			silverPoint.setVisibility(View.GONE);
			silverText.setVisibility(View.GONE);
		}
		if(bc.getBronze() != 0) {
			bronzePoint.setVisibility(View.VISIBLE);
			bronzeText.setVisibility(View.VISIBLE);
			bronzeText.setText(String.valueOf(bc.getBronze()));
		} else {
			bronzePoint.setVisibility(View.GONE);
			bronzeText.setVisibility(View.GONE);
		}
		answersCount.setText(String.valueOf(user.getAnswersCount()));
		questionsCount.setText(String.valueOf(user.getQuestionsCount()));
		tagsCount.setText(String.valueOf(user.getTagsCount()));
		votesCount.setText(String.valueOf(user.getVoteCount()));
		voteUpCount.setText(String.valueOf(user.getVoteUpCount()));
		voteDownCount.setText(String.valueOf(user.getVoteDownCount()));
	}
	
	@Background
	protected void getUser() {
		showProgressDialog();
		Api.prepare(getActivity(), userRest.getRestTemplate());
		try {
			user = userRest.getUser(userId).get().get(0);
			user.setTagsCount(userRest.getNbTags(userId).get());
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
	
	private BroadcastReceiver updateReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			init();
		}
	};
}
