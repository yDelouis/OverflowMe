package fr.ydelouis.overflowme.api.rest;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.Filter;
import fr.ydelouis.overflowme.api.entity.Answer.AnswerRequest;
import fr.ydelouis.overflowme.api.entity.Badge.BadgeRequest;
import fr.ydelouis.overflowme.api.entity.RepChange.RepChangeRequest;
import fr.ydelouis.overflowme.api.entity.User.UserRequest;

@Rest(rootUrl = Api.ROOT_URL, converters = GsonHttpMessageConverter.class)
public interface UserRest
{
	public RestTemplate getRestTemplate();
	
	@Get("/users/{userId}?filter="+Filter.USER_COMPLETE)
	public UserRequest getUser(int userId);
	
	@Get("/users/{userId}/tags?filter="+Filter.TOTAL)
	public UserRequest getNbTags(int userId);

	@Get("/users/{userId}/reputation-history?page={page}")
	public RepChangeRequest getReputationHistory(int userId, int page);
	@Get("/users/{userId}/reputation-history?page={page}&pagesize={pageSize}")
	public RepChangeRequest getReputationHistory(int userId, int page, int pageSize);
	
	@Get("/users/{userId}/badges?page={page}&pagesize={pageSize}&order=asc&sort=name&filter="+Filter.BADGE_COMPLETE)
	public BadgeRequest getBadges(int userId, int page, int pageSize);
	@Get("/users/{userId}/badges?page={page}&pagesize={pageSize}&fromdate={fromDate}&filter="+Filter.BADGE_COMPLETE)
	public BadgeRequest getBadgesSince(int userId, int page, int pageSize, long fromDate);

	@Get("/users/{userId}/answers?page={page}&pagesize={pageSize}&order=desc&sort=votes&filter="+Filter.ANSWER_USERLIST)
	public AnswerRequest getAnswersByScore(int userId, int page, int pageSize);
	@Get("/users/{userId}/answers?page={page}&pagesize={pageSize}&order=desc&sort=activity&filter="+Filter.ANSWER_USERLIST)
	public AnswerRequest getAnswersByActivity(int userId, int page, int pageSize);
	@Get("/users/{userId}/answers?page={page}&pagesize={pageSize}&fromdate={fromDate}&filter="+Filter.ANSWER_USERLIST)
	public AnswerRequest getAnswersSince(int userId, int page, int pageSize, long fromDate);
}
