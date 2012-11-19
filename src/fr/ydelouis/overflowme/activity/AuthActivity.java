package fr.ydelouis.overflowme.activity;

import org.springframework.util.StringUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EActivity;

import fr.ydelouis.overflowme.R;
import fr.ydelouis.overflowme.loader.MeLoader;
import fr.ydelouis.overflowme.loader.NotifLoader;
import fr.ydelouis.overflowme.model.MeStore;

@SuppressLint("SetJavaScriptEnabled")
@EActivity
public class AuthActivity extends Activity
{
	private static final String AUTH_URL = "https://stackexchange.com/oauth/dialog";
	private static final int CLIENT_ID = 846;
	private static final String[] SCOPE = {"read_inbox", "no_expiry", "write_access", "private_info"} ;
	private static final String SCOPE_DELIMITER = ",";
	private static final String REDIRECT_URI = "http://overflowme.ydelouis.fr";
	private static final String ACCESS_TOKEN = "access_token";
	
	protected WebView webView;

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
		requestWindowFeature(Window.FEATURE_PROGRESS);
		webView = new WebView(this);
		setContentView(webView);
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int progress) {
	            setProgress(progress*100);
			}
		});
		webView.setWebViewClient(new AuthWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.loadUrl(buildAuthUrl());
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
			setContentView(R.layout.activity_auth_progress);
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
		return url.substring(url.indexOf(ACCESS_TOKEN)+13);
	}
	
	private class AuthWebViewClient extends WebViewClient
	{
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if(url.startsWith(REDIRECT_URI))
				onAuthSuccess(url);
			else 
				super.onPageStarted(view, url, favicon);
		}
	}
}
