package fr.ydelouis.overflowme.loader;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.OrmLiteDao;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;

import fr.ydelouis.overflowme.api.Api;
import fr.ydelouis.overflowme.api.entity.Answer;
import fr.ydelouis.overflowme.api.entity.Question;
import fr.ydelouis.overflowme.api.entity.RepChange;
import fr.ydelouis.overflowme.api.rest.AnswerRest;
import fr.ydelouis.overflowme.api.rest.MeRest;
import fr.ydelouis.overflowme.api.rest.QuestionRest;
import fr.ydelouis.overflowme.api.rest.UserRest;
import fr.ydelouis.overflowme.model.DatabaseHelper;
import fr.ydelouis.overflowme.model.RepChangeDao;
import fr.ydelouis.overflowme.util.DateUtil;

@EBean
public class RepChangeLoader
{
	@RootContext
	protected Context context;
	@RestService
	protected UserRest userRest;
	@RestService
	protected MeRest meRest;
	@RestService
	protected AnswerRest answerRest;
	@RestService
	protected QuestionRest questionRest;
	@OrmLiteDao(helper = DatabaseHelper.class, model = RepChange.class)
	protected RepChangeDao repChangeDao;
	
	@AfterInject
	protected void init() {
		Api.prepare(context, userRest.getRestTemplate());
		Api.prepare(context, meRest.getRestTemplate());
		Api.prepare(context, answerRest.getRestTemplate());
		Api.prepare(context, questionRest.getRestTemplate());
	}
	
	public boolean loadMyRepChangeSince(long since, Fragment fragment) {
		List<RepChange> newRepChanges = new ArrayList<RepChange>();
		try {
			boolean loadNext = true;
			List<RepChange> newRepChangesPage;
			int page = 1;
			while(loadNext) {
				newRepChangesPage = meRest.getReputationHistory(page).getItems();
				if(newRepChangesPage.size() != 0) {
					newRepChanges.addAll(newRepChangesPage);
					RepChange firstRepChange = newRepChangesPage.get(newRepChangesPage.size()-1);
					if(firstRepChange.getCreationDate() > since)
						page++;
					else
						loadNext = false;
				} else
					loadNext = false;
			}
			int i = 0;
			while(i < newRepChanges.size() && newRepChanges.get(i).getCreationDate() > since)
				i++;
			if(newRepChanges.size() > 0 && i > 0) {
				newRepChanges = newRepChanges.subList(0, i);
				if(!newRepChanges.isEmpty()) {
					clear(newRepChanges);
					setTitles(newRepChanges);
					if(fragment.getActivity() != null)
						repChangeDao.createAll(newRepChanges);
				}
				return true;
			}
		} catch(RuntimeException e) {
			e.printStackTrace();
		} catch(SQLException sqlE) {}
		return false;
	}
	
	public List<RepChange> getReputationHistory(int userId, int page, int pageSize) {
		try {
			List<RepChange> repChanges = userRest.getReputationHistory(userId, page, pageSize).getItems();
			if(!repChanges.isEmpty()) {
				clear(repChanges);
				setTitles(repChanges);
			}
			return repChanges;
		} catch(RuntimeException e) {
			return new ArrayList<RepChange>();
		}
	}
	
	private void setTitles(List<RepChange> repChanges) {
		String ids = "";
		for(RepChange repChange : repChanges) {
			if(repChange.getPostId() != 0 && !ids.contains(repChange.getPostId()+";")) {
				ids += repChange.getPostId()+";";
			}
		}
		if(!ids.isEmpty())
			ids = ids.substring(0, ids.length()-1);
		
		try {
			List<Answer> answers = answerRest.getAnswersTitles(ids).getItems();
			List<Question> questions = questionRest.getQuestionsTitles(ids).getItems();
			for(Answer answer : answers) {
				for(RepChange repChange : repChanges) {
					if(repChange.getPostId() == answer.getId()) {
						repChange.setLink(answer.getLink());
						repChange.setTitle(answer.getTitle());
					}
				}
			}
			for(Question question : questions) {
				for(RepChange repChange : repChanges) {
					if(repChange.getPostId() == question.getId()) {
						repChange.setLink(question.getLink());
						repChange.setTitle(question.getTitle());
					}
				}
			}
		} catch(RuntimeException e) { e.printStackTrace(); }
	}
	
	private void clear(List<RepChange> repChanges) {
		if(repChanges == null || repChanges.size() < 2)
			return;
		List<RepChange> repToRemove = new ArrayList<RepChange>();
		RepChange rc1, rc2;
		rc1 = repChanges.get(0);
		for(int i = 1; i < repChanges.size(); i++) {
			rc2 = repChanges.get(i);
			if(rc1.getPostId() == rc2.getPostId()
			&& rc1.getChange() + rc2.getChange() == 0
			&& Math.abs(rc1.getCreationDate() - rc2.getCreationDate()) < DateUtil.FIFTEEN_MINUTES) {
				repToRemove.add(rc1);
				repToRemove.add(rc2);
			}
			rc1 = rc2;
		}
		repChanges.removeAll(repToRemove);
	}
}
