package fr.ydelouis.overflowme.api.rest;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.Filter;
import fr.ydelouis.overflowme.api.entity.Badge.BadgeRequest;
import fr.ydelouis.overflowme.api.entity.RepChange.RepChangeRequest;
import fr.ydelouis.overflowme.api.entity.User.UserRequest;

@Rest(rootUrl = Api.ROOT_URL, converters = GsonHttpMessageConverter.class)
public interface MeRest
{
	public RestTemplate getRestTemplate();
	
	@Get("/me?filter="+Filter.USER_COMPLETE)
	public UserRequest getMe();
	
	@Get("/me/tags?filter="+Filter.TOTAL)
	public UserRequest getNbTags();

	@Get("/me/reputation-history?page={page}") 
	public RepChangeRequest getReputationHistory(int page);
	
	@Get("/me/reputation-history?page={page}&pagesize={pageSize}") 
	public RepChangeRequest getReputationHistory(int page, int pageSize);
	
	@Get("/me/badges?page={page}&pagesize={pageSize}&order=asc&sort=name&filter="+Filter.BADGE_COMPLETE)
	public BadgeRequest getBadges(int page, int pageSize);
}
