package fr.ydelouis.overflowme.api.entity;

import com.google.gson.annotations.SerializedName;

import fr.ydelouis.overflowme.entity.Notif.Notifable;

public class InboxItem
	implements
		Notifable
{
	public enum Type {
		comment("New comment"),
		chat_message("New chat message"),
		new_answer("New answer");
		
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

	@SerializedName("item_type")
	private Type	type;
	private String	title;
	@SerializedName("creation_date")
	private long	creationDate;
	@SerializedName("is_unread")
	private boolean	unread;
	private String	link;
	private Site	site;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	@Override
	public String getText() {
		return title;
	}

	@Override
	public String getNotifType() {
		if(type == null)
			return "";
		return type.toString();
	}
	
	public static class InboxItemRequest extends Request<InboxItem>{}
}
