package fr.ydelouis.overflowme.activity;

import org.springframework.util.StringUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.loader.MeLoader;
import fr.ydelouis.overflowme.loader.NotifLoader;
import fr.ydelouis.overflowme.model.MeStore;

@SuppressLint("SetJavaScriptEnabled")
@EActivity(R.layout.activity_auth)
public class AuthActivity extends Activity
{
	private static final String AUTH_URL = "https://stackexchange.com/oauth/dialog";
	private static final int CLIENT_ID = 846;
	private static final String[] SCOPE = {"read_inbox", "no_expiry", "write_access", "private_info"} ;
	private static final String SCOPE_DELIMITER = ",";
	private static final String REDIRECT_URI = "https://stackexchange.com/oauth/login_success";
	private static final String ACCESS_TOKEN = "access_token";
	
	@ViewById(R.id.auth_webView)
	protected WebView webView;
	@ViewById(R.id.auth_loading)
	protected View loading;
	@ViewById(R.id.auth_loadingImage)
	protected ImageView loadingImage;
	@ViewById(R.id.auth_loadingText)
	protected View loadingText;

	@Bean
	protected MeStore meStore;
	@Bean
	protected MeLoader meLoader;
	@Bean
	protected NotifLoader notifLoader;
	
	private boolean authSucceed = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	}
	
	@AfterViews
	protected void init() {
		webView.setWebViewClient(new AuthWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setSavePassword(false);
		webView.loadUrl(buildAuthUrl());
		
		CookieSyncManager.createInstance(webView.getContext()).sync();
		CookieManager.getInstance().acceptCookie();
	}
	
	private String buildAuthUrl() {
		String url = AUTH_URL;
		url += "?client_id="+CLIENT_ID;
		url += "&scope="+StringUtils.arrayToDelimitedString(SCOPE, SCOPE_DELIMITER);
		url += "&redirect_uri="+REDIRECT_URI;
		return url;
	}
	
	private void onAuthSuccess(String url) {
		if(!authSucceed) {
			authSucceed = true;
			webView.setVisibility(View.INVISIBLE);
			loading.setVisibility(View.VISIBLE);
			loadingText.setVisibility(View.VISIBLE);
			getMeAndFinish(url);
		}
	}
	
	@Background
	protected void getMeAndFinish(String url) {
		meStore.setAccessToken(getAccessToken(url));
		meLoader.load();
		notifLoader.load();
		setResult(RESULT_OK);
		finish();
	}
	
	private String getAccessToken(String url) {
		return url.substring(url.indexOf(ACCESS_TOKEN)+ACCESS_TOKEN.length()+1);
	}
	
	private class AuthWebViewClient extends WebViewClient
	{
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if(url.startsWith(REDIRECT_URI))
				onAuthSuccess(url);
			else {
				webView.setVisibility(View.INVISIBLE);
				loading.setVisibility(View.VISIBLE);
				((AnimationDrawable) loadingImage.getDrawable()).start();
				setProgressBarIndeterminateVisibility(true);
				super.onPageStarted(view, url, favicon);
			}
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			if(!authSucceed) {
				webView.setVisibility(View.VISIBLE);
				loading.setVisibility(View.INVISIBLE);
				setProgressBarIndeterminateVisibility(false);
			}
		}
	}
}
