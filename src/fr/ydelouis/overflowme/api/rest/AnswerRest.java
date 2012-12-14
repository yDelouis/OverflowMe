package fr.ydelouis.overflowme.api.rest;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.Filter;
import fr.ydelouis.overflowme.api.entity.Answer.AnswerRequest;

@Rest(rootUrl = Api.ROOT_URL, converters = GsonHttpMessageConverter.class)
public interface AnswerRest
{
	public RestTemplate getRestTemplate();
	
	@Get("/answers/{ids}?filter="+Filter.ANSWER_TITLE)
	public AnswerRequest getAnswersTitles(String ids);
}
