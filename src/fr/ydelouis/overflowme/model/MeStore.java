package fr.ydelouis.overflowme.model;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.api.Scope;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import fr.ydelouis.overflowme.api.entity.BadgeCount;
import fr.ydelouis.overflowme.api.entity.User;

@EBean(scope = Scope.Singleton)
public class MeStore
{
	private static final String ME = "me_";
	private static final String LAST_SEEN_ME = "lastSeenMe_";
	
	private static final String ID = "id";
	private static final String DISPLAY_NAME = "displayName";
	private static final String AGE = "age";
	private static final String IMAGE_URL = "imageUrl";
	private static final String REPUTATION = "reputation";
	private static final String REPUTATION_CHANGE_WEEK = "reputationChangeWeek";
	private static final String LAST_ACCESS = "lastAccess";
	private static final String BADGE_GOLD = "badgeGold";
	private static final String BADGE_SILVER = "badgeSilver";
	private static final String BADGE_BRONZE = "badgeBronze";
	private static final String LOCATION = "location";
	private static final String CREATION_DATE = "creationDate";
	private static final String ABOUT_ME = "aboutMe";
	private static final String WEBSITE = "website";
	private static final String ANSWERS_COUNT = "answersCount";
	private static final String QUESTIONS_COUNT = "questionsCount";
	private static final String VOTE_UP_COUNT = "voteUpCount";
	private static final String VOTE_DOWN_COUNT = "voteDownCount";
	private static final String TAGS_COUNT = "tagsCount";
	
	private static final String ACCESS_TOKEN = "accessToken";
	private static final String LAST_SEEN_DATE = "lastSeenDate";
	
	private SharedPreferences pref;
	private User me;
	private User lastSeenMe;
	private String accessToken;
	private long lastSeenDate = 0;
	
	public MeStore(Context context) {
		pref = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public User getMe() {
		if(me != null)
			return me;
		me = getMe(ME);
		return me;
	}
	
	public User getLastSeenMe() {
		if(lastSeenMe != null)
			return lastSeenMe;
		lastSeenMe = getMe(LAST_SEEN_ME);
		return lastSeenMe;
	}
	
	private User getMe(String prefix) {
		int id = getInt(prefix+ID);
		if(id == 0)
			return null;
		User me = new User();
		me.setId(id);
		me.setDisplayName(getString(prefix+DISPLAY_NAME));
		me.setAge(getInt(prefix+AGE));
		me.setImageUrl(getString(prefix+IMAGE_URL));
		me.setReputation(getInt(prefix+REPUTATION));
		me.setReputationChangeWeek(getInt(prefix+REPUTATION_CHANGE_WEEK));
		me.setLastAccess(getLong(prefix+LAST_ACCESS));
		me.setBadgeCount(getBadgeCount(prefix));
		me.setLocation(getString(prefix+LOCATION));
		me.setCreationDate(getLong(prefix+CREATION_DATE));
		me.setAboutMe(getString(prefix+ABOUT_ME));
		me.setWebsite(getString(prefix+WEBSITE));
		me.setAnswersCount(getInt(prefix+ANSWERS_COUNT));
		me.setQuestionsCount(getInt(prefix+QUESTIONS_COUNT));
		me.setVoteUpCount(getInt(prefix+VOTE_UP_COUNT));
		me.setVoteDownCount(getInt(prefix+VOTE_DOWN_COUNT));
		me.setTagsCount(getInt(prefix+TAGS_COUNT));
		return me;
	}
	
	public void saveMe(User me) {
		this.me = me;
		saveMe(me, ME);
	}
	
	public void saveLastSeenMe(User lastSeenMe) {
		this.lastSeenMe = lastSeenMe;
		saveMe(lastSeenMe, LAST_SEEN_ME);
	}
	
	private void saveMe(User me, String prefix) {
		if(me != null) {
		pref.edit()
			.putInt(prefix+ID, me.getId())
			.putString(prefix+DISPLAY_NAME, me.getDisplayName())
			.putInt(prefix+AGE, me.getAge())
			.putString(prefix+IMAGE_URL, me.getImageUrl())
			.putInt(prefix+REPUTATION, me.getReputation())
			.putInt(prefix+REPUTATION_CHANGE_WEEK, me.getReputationChangeWeek())
			.putLong(prefix+LAST_ACCESS, me.getLastAccess())
			.putInt(prefix+BADGE_GOLD, me.getBadgeCount().getGold())
			.putInt(prefix+BADGE_SILVER, me.getBadgeCount().getSilver())
			.putInt(prefix+BADGE_BRONZE, me.getBadgeCount().getBronze())
			.putString(prefix+LOCATION, me.getLocation())
			.putLong(prefix+CREATION_DATE, me.getCreationDate())
			.putString(prefix+ABOUT_ME, me.getAboutMe())
			.putString(prefix+WEBSITE, me.getWebsite())
			.putInt(prefix+ANSWERS_COUNT, me.getAnswersCount())
			.putInt(prefix+QUESTIONS_COUNT, me.getQuestionsCount())
			.putInt(prefix+VOTE_UP_COUNT, me.getVoteUpCount())
			.putInt(prefix+VOTE_DOWN_COUNT, me.getVoteDownCount())
			.putInt(prefix+TAGS_COUNT, me.getTagsCount())
			.commit();
		} else {
			pref.edit().putInt(prefix+ID, 0).commit();
		}
	}
	
	public String getAccessToken() {
		if(accessToken == null)
			accessToken = getString(ACCESS_TOKEN);
		return accessToken;
	}
	
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
		pref.edit().putString(ACCESS_TOKEN, accessToken).commit();
	}
	
	public long getLastSeenDate() {
		if(lastSeenDate == 0)
			lastSeenDate = getLong(LAST_SEEN_DATE);
		return lastSeenDate;
	}
	
	public void setLastSeenDate(long lastSeenDate) {
		this.lastSeenDate = lastSeenDate;
		pref.edit().putLong(LAST_SEEN_DATE, lastSeenDate).commit();
	}
	
	private int getInt(String attrName) {
		return pref.getInt(attrName, 0);
	}
	
	private String getString(String attrName) {
		return pref.getString(attrName, null);
	}
	
	private long getLong(String attrName) {
		return pref.getLong(attrName, 0);
	}
	
	private BadgeCount getBadgeCount(String prefix) {
		BadgeCount bc = new BadgeCount();
		bc.setGold(getInt(prefix+BADGE_GOLD));
		bc.setSilver(getInt(prefix+BADGE_SILVER));
		bc.setBronze(getInt(prefix+BADGE_BRONZE));
		return bc;
	}
}
