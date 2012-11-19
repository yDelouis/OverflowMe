package fr.ydelouis.overflowme.api.rest;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.Filter;
import fr.ydelouis.overflowme.api.entity.Total;
import fr.ydelouis.overflowme.api.entity.User;

@Rest(rootUrl = Api.ROOT_URL, converters = GsonHttpMessageConverter.class)
public interface MeRest
{
	public RestTemplate getRestTemplate();
	
	@Get("/me?filter="+Filter.USER_COMPLETE)
	public User.List getMe();
	
	@Get("/me/tags?filter="+Filter.TOTAL)
	public Total getNbTags();
}
