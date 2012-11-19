package fr.ydelouis.overflowme.api.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.text.Html;

import com.google.gson.annotations.SerializedName;

import fr.ydelouis.overflowme.entity.Notif.Notifable;

public class Notification
	implements
		Notifable
{
	public enum Type {
		new_privilege("New privilege"),
		badge_earned("Badge earned"),
		edit_suggested("Edit suggested"),
		post_migrated("Post migrated");
		
		private String notifType;
		private Type(String notifType) {
			this.notifType = notifType;
		}
		public String toString() {
			if(notifType != null)
				return notifType;
			return super.toString();
		}
	}
	
	private Site	site;
	@SerializedName("notification_type")
	private Type type;
	@SerializedName("creation_date")
	private long	creationDate;
	@SerializedName("is_unread")
	private boolean	unread;
	private String	body;

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public long getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isUnread() {
		return unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public static class List
	{
		private java.util.List<Notification>	items;

		public java.util.List<Notification> get() {
			return items;
		}
	}

	@Override
	public String getText() {
		return Html.fromHtml(body).toString().replace("learn more", "");
	}

	@Override
	public String getLink() {
		Pattern p = Pattern.compile("<a href='(.*?)'>");
		Matcher m = p.matcher(body);
		while(m.find()) {
			return m.group(1);
		}
		return null;
	}

	@Override
	public String getNotifType() {
		if(type == null)
			return "";
		return type.toString();
	}
}
