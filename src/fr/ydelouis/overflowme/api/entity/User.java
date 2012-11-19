package fr.ydelouis.overflowme.api.entity;

import com.google.gson.annotations.SerializedName;

public class User
{
	@SerializedName("user_id")
	private int			id;
	@SerializedName("display_name")
	private String		displayName;
	private int			age;
	@SerializedName("profile_image")
	private String		imageUrl;
	private int			reputation;
	@SerializedName("reputation_change_week")
	private int reputationChangeWeek;
	@SerializedName("last_access_date")
	private long		lastAccess;
	@SerializedName("badge_counts")
	private BadgeCount	badgeCount;
	private String		location = "";
	@SerializedName("creation_date")
	private long		creationDate;
	@SerializedName("about_me")
	private String		aboutMe = "";
	@SerializedName("website_url")
	private String		website;
	@SerializedName("answer_count")
	private int			answersCount;
	@SerializedName("question_count")
	private int			questionsCount;
	@SerializedName("up_vote_count")
	private int			voteUpCount;
	@SerializedName("down_vote_count")
	private int			voteDownCount;
	private int tagsCount;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getReputation() {
		return reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public String getReputationString() {
		return (reputation > 1000 ? (reputation/1000)+"," : "")+(reputation%1000);
	}
	
	public String getReputationShortString() {
		if (reputation < 1000)
			return String.valueOf(reputation);
		if (reputation < 10000)
			return (reputation / 1000) + "," + (reputation % 1000);
		else
			return (reputation < 1000) + "." + ((reputation % 1000) / 100) + "k";
	}

	public int getReputationChangeWeek() {
		return reputationChangeWeek;
	}

	public void setReputationChangeWeek(int reputationChangeWeek) {
		this.reputationChangeWeek = reputationChangeWeek;
	}

	public long getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(long lastAccess) {
		this.lastAccess = lastAccess;
	}

	public BadgeCount getBadgeCount() {
		return badgeCount;
	}

	public void setBadgeCount(BadgeCount badgeCount) {
		this.badgeCount = badgeCount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getCity() {
		String[] data = location.split(",");
		return data[0].trim();
	}
	
	public String getCountry() {
		String[] data = location.split(",");
		if(data.length > 1)
			return data[1].trim();
		return null;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getAnswersCount() {
		return answersCount;
	}

	public int getTagsCount() {
		return tagsCount;
	}

	public void setTagsCount(int tagsCount) {
		this.tagsCount = tagsCount;
	}

	public void setAnswersCount(int answersCount) {
		this.answersCount = answersCount;
	}

	public int getQuestionsCount() {
		return questionsCount;
	}

	public void setQuestionsCount(int questionsCount) {
		this.questionsCount = questionsCount;
	}

	public int getVoteUpCount() {
		return voteUpCount;
	}

	public void setVoteUpCount(int voteUpCount) {
		this.voteUpCount = voteUpCount;
	}

	public int getVoteDownCount() {
		return voteDownCount;
	}

	public void setVoteDownCount(int voteDownCount) {
		this.voteDownCount = voteDownCount;
	}

	public int getVoteCount() {
		return voteDownCount + voteUpCount;
	}

	public static class List
	{
		private java.util.List<User>	items;

		public java.util.List<User> get() {
			return items;
		}
	}
}
