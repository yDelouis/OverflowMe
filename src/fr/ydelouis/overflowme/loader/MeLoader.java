package fr.ydelouis.overflowme.loader;

import android.content.Context;

import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;

import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.entity.User;
import fr.ydelouis.overflowme.api.rest.MeRest;
import fr.ydelouis.overflowme.model.MeStore;

@EBean
public class MeLoader
{
	@RootContext
	protected Context context;
	@Bean
	protected MeStore meStore;
	@RestService
	protected MeRest meRest;
	
	public void load() {
		Api.prepare(context, meRest.getRestTemplate());
		User me = meRest.getMe().get().get(0);
		me.setTagsCount(meRest.getNbTags().get());
		meStore.saveMe(me);
	}
}
