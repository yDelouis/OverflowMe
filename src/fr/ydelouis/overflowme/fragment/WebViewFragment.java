package fr.ydelouis.overflowme.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.FragmentArg;
import com.googlecode.androidannotations.annotations.ViewById;

import fr.ydelouis.overflowme.R;

@SuppressLint("SetJavaScriptEnabled")
@EFragment(R.layout.fragment_webview)
public class WebViewFragment extends Fragment
{
	@FragmentArg
	protected String url;
	@ViewById(R.id.webView_webView)
	protected WebView webView;
	@ViewById(R.id.webView_loading)
	protected ImageView loadingImage;
	
	@AfterViews
	protected void init() {
		webView.setWebViewClient(new SoWebViewClient());
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.loadUrl(url);
		
		CookieSyncManager.createInstance(webView.getContext()).sync();
		CookieManager.getInstance().acceptCookie();
	}
	
	private static final String[] DIVTOHIDE = 
		{"header", "site-menu-wrap", "nav", "footer", "dno",
		"error-notification overlay-header"};
	
	private class SoWebViewClient extends WebViewClient
	{
		private String hideAllButWrapper() {
			String js = "(function() {";
			for(String className : DIVTOHIDE)
				js += "document.querySelectorAll('."+className+"')[0].style.display = 'none';";
			js += "})()";
			return js;
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			webView.loadUrl("javascript:"+hideAllButWrapper());
			webView.postDelayed(new Runnable() {
				public void run() {
					webView.setVisibility(View.VISIBLE);
					loadingImage.setVisibility(View.INVISIBLE);
					if(getActivity() != null)
						getActivity().setProgressBarIndeterminateVisibility(false);					
				}
			}, 100);
			
		}
		
		@Override
		public void onPageStarted(WebView webView, String url, Bitmap favicon) {
			webView.setVisibility(View.INVISIBLE);
			loadingImage.setVisibility(View.VISIBLE);
			((AnimationDrawable) loadingImage.getDrawable()).start();
			if(getActivity() != null)
				getActivity().setProgressBarIndeterminateVisibility(true);
		}
	}
}
