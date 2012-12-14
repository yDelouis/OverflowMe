package fr.ydelouis.overflowme.fragment;

import android.app.Fragment;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.activity.*;
import fr.ydelouis.overflowme.api.entity.User;

@EFragment(R.layout.fragment_aboutme)
public class AboutMeFragment extends Fragment implements OnClickListener
{
	@FragmentArg
	protected User user;
	
	@ViewById(R.id.aboutMe_website)
	protected TextView websiteText;
	@ViewById(R.id.aboutMe_aboutMeTitle)
	protected TextView aboutMeTitle;
	@ViewById(R.id.aboutMe_aboutMe)
	protected TextView aboutMeText;
	
	@AfterViews
	protected void init() {
		if(user.getWebsite() == null || user.getWebsite().isEmpty())
			websiteText.setText(R.string.aboutMe_noWebsite);
		else {
			websiteText.setText(user.getWebsite());
			websiteText.setOnClickListener(this);
		}
		aboutMeTitle.setText(String.format(getString(R.string.aboutMe_aboutMeTitle), user.getDisplayName()));
		if(user.getAboutMe() == null || user.getAboutMe().isEmpty())
			aboutMeText.setText(R.string.aboutMe_noAboutMe);
		else
			aboutMeText.setText(Html.fromHtml(user.getAboutMe()));
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.aboutMe_website:
				WebViewActivity_.intent(getActivity())
					.url(user.getWebsite())
					.title(String.format(getString(R.string.aboutMe_websiteActivityTitle),
													user.getDisplayName()))
					.start();
				break;
		}
	}
}
