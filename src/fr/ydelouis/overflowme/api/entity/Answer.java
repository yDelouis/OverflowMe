package fr.ydelouis.overflowme.api.entity;

import com.google.gson.annotations.SerializedName;

public class Answer
{
	@SerializedName("answer_id")
	private long	id;
	@SerializedName("creation_date")
	private long creationDate;
	@SerializedName("last_activity_date")
	private long lastActivityDate;
	private String	title;
	private String	link;
	private int score;
	@SerializedName("is_accepted")
	private boolean isAccepted;
	@SerializedName("questionId")
	private int questionId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
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
	
	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public long getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(long lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public static class AnswerRequest extends Request<Answer>{}
}
