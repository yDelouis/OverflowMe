package fr.ydelouis.overflowme.api;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import fr.ydelouis.overflowme.model.MeStore;
import fr.ydelouis.overflowme.model.MeStore_;

public class ApiInterceptor implements ClientHttpRequestInterceptor 
{
	private static final String TAG = "API Rest";
	private static final boolean LOG_REQUEST = true;
	private static final boolean LOG_FULL_REQUEST = false;
	private static final boolean LOG_RESPONSE = false;
	
	private static ApiInterceptor instanceWithSite;
	private static ApiInterceptor instanceWithoutSite;
	
	public static ApiInterceptor getInstance(Context context, boolean includeSite) {
		if(includeSite) {
			if(instanceWithSite == null)
				instanceWithSite = new ApiInterceptor(context, true);
			return instanceWithSite;
		} else {
			if(instanceWithoutSite == null)
				instanceWithoutSite = new ApiInterceptor(context, false);
			return instanceWithoutSite;
		}
	}
	
	private MeStore meStore;
	private boolean includeSite;
	
	public ApiInterceptor(Context context, boolean includeSite) {
		this.includeSite = includeSite;
		meStore = MeStore_.getInstance_(context);
	}
	
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
    	HttpRequest apiRequest = new ApiHttpRequest(request);
    	if(LOG_FULL_REQUEST) Log.i(TAG, request.getMethod()+" : "+apiRequest.getURI());
    	else if(LOG_REQUEST) Log.i(TAG, request.getMethod()+" : "+request.getURI().toString().replace(Api.ROOT_URL, ""));
    	ApiHttpResponse response = new ApiHttpResponse(execution.execute(apiRequest, body));
    	if(LOG_RESPONSE) Log.i(TAG, response.getStatusCode().value()+" : "+stringOf(response.getBody()));
    	return response;
    }
    
    private String stringOf(InputStream inputStream) {
    	inputStream.mark(Integer.MAX_VALUE);
    	BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
    	StringBuilder strBuilder = new StringBuilder();
    	String line;
    	try {
			while ((line = r.readLine()) != null)
			    strBuilder.append(line);
		} catch (IOException e) {}
    	try {
			inputStream.reset();
		} catch (IOException e) {}
    	return strBuilder.toString();
    }
    
    private class ApiHttpRequest implements HttpRequest
    {
    	private HttpRequest httpRequest;
    	
    	public ApiHttpRequest(HttpRequest httpRequest) {
    		this.httpRequest = httpRequest;
    	}
    	
		@Override
		public HttpHeaders getHeaders() {
			return httpRequest.getHeaders();
		}

		@Override
		public HttpMethod getMethod() {
			return httpRequest.getMethod();
		}

		@Override
		public URI getURI() {
			URI uri = httpRequest.getURI();
			Uri.Builder builder = new Uri.Builder();
			builder.scheme(uri.getScheme());
			builder.authority(uri.getAuthority());
			builder.path(uri.getPath());
			builder.encodedQuery(uri.getQuery());
			builder.appendQueryParameter(Api.KEY_KEY, Api.KEY_VALUE);
			if(includeSite)
				builder.appendQueryParameter(Api.SITE_KEY, Api.SITE_VALUE);
			builder.appendQueryParameter(Api.ACCESS_TOKEN, meStore.getAccessToken());
			return URI.create(builder.build().toString());
		}
    }
    
    private class ApiHttpResponse implements ClientHttpResponse
    {
    	private ClientHttpResponse response;
    	private byte[] byteResponse;
    	
    	public ApiHttpResponse(ClientHttpResponse response) {
    		this.response = response;
    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
    		try {
	    		InputStream inputStream = response.getBody();
	    	    byte[] buffer = new byte[1024];
	    	    int len;
	    	    while ((len = inputStream.read(buffer)) > -1 ) {
	    	        baos.write(buffer, 0, len);
	    	    }
	    	    baos.flush();
	    	    byteResponse = baos.toByteArray();
    		} catch (IOException e) {}
    	}
    	
		@Override
		public InputStream getBody() throws IOException {
			return new ByteArrayInputStream(byteResponse);
		}

		@Override
		public HttpHeaders getHeaders() {
			return response.getHeaders();
		}

		@Override
		public void close() {
			response.close();
		}

		@Override
		public int getRawStatusCode() throws IOException {
			return response.getRawStatusCode();
		}

		@Override
		public HttpStatus getStatusCode() throws IOException {
			return response.getStatusCode();
		}

		@Override
		public String getStatusText() throws IOException {
			return response.getStatusText();
		}
    }
}