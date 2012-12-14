package fr.ydelouis.overflowme.api.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.ydelouis.overflowme.model.RepChangeDao;

@DatabaseTable(tableName = "reputationChange", daoClass = RepChangeDao.class)
public class RepChange
{
	public enum Type {
		asker_accepts_answer,
		asker_unaccept_answer,
		answer_accepted,
		answer_unaccepted,
		voter_downvotes,
		voter_undownvotes,
		post_downvoted,
		post_undownvoted,
		post_upvoted,
		post_unupvoted,
		suggested_edit_approval_received,
		post_flagged_as_spam,
		post_flagged_as_offensive,
		bounty_given,
		bounty_earned,
		bounty_cancelled,
		post_deleted,
		post_undeleted,
		association_bonus,
		arbitrary_reputation_change,
		vote_fraud_reversal,
		post_migrated,
		user_deleted
	}

	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	@SerializedName("creation_date")
	private long	creationDate;
	@DatabaseField
	@SerializedName("post_id")
	private int		postId;
	@DatabaseField
	@SerializedName("reputation_change")
	private int		change;
	@DatabaseField
	@SerializedName("reputation_history_type")
	private Type	type;
	@DatabaseField
	@SerializedName("user_id")
	private int		userId;
	@DatabaseField
	private String title;
	@DatabaseField
	private String link;
	
	public RepChange() {
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	
	public int getPostId() {
		return postId;
	}
	
	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	public int getChange() {
		return change;
	}
	
	public void setChange(int change) {
		this.change = change;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public int getUserId() {
		return userId;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getTitle() {
		if(title != null)
			return title;
		String typeStr = type.toString().replace("_", " ");
		typeStr = typeStr.substring(0, 1).toUpperCase()+typeStr.substring(1, typeStr.length());
		return typeStr;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public static class RepChangeRequest extends Request<RepChange>{}
}
