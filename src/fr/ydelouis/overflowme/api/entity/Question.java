package fr.ydelouis.overflowme.api.entity;

import com.google.gson.annotations.SerializedName;

public class Question
{
	@SerializedName("question_id")
	private int		id;
	private String	title;
	private String	link;

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public static class QuestionRequest extends Request<Question>{}
}
