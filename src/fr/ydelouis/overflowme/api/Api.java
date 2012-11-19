package fr.ydelouis.overflowme.api;

import java.util.ArrayList;

import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import android.content.Context;

public class Api
{
	public static final String ROOT_URL = "https://api.stackexchange.com";
	public static final String SITE_KEY = "site";
	public static final String SITE_VALUE = "stackoverflow.com";
	public static final String SITE_URL = "http://stackoverflow.com";
	public static final String KEY_KEY = "key";
	public static final String KEY_VALUE = "C8P2TfqDp5bY)DI*8RdpsQ((";
	public static final String ACCESS_TOKEN = "access_token";
	
	public static void prepare(Context context, RestTemplate restTemplate, boolean includeSite) {
		if(restTemplate.getInterceptors() == null) {
			ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			interceptors.add(ApiInterceptor.getInstance(context, includeSite));
			restTemplate.setInterceptors(interceptors);
		}
	}
	
	public static void prepare(Context context, RestTemplate restTemplate) {
		prepare(context, restTemplate, true);
	}
}
