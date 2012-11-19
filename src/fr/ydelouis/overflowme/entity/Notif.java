package fr.ydelouis.overflowme.entity;

import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.ydelouis.overflowme.model.NotifDao;

@DatabaseTable(tableName = "notif", daoClass = NotifDao.class)
public class Notif implements Comparable<Notif>
{
	public static int getNbUnread(List<Notif> notifs, long lastSeenDate) {
		int i = 0;
		for(Notif notif : notifs) {
			if(notif.isUnread(lastSeenDate))
				i++;
		}
		return i;
	}
	
	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField
	private String type;
	@DatabaseField
	private String	text;
	@DatabaseField
	private String	link;
	@DatabaseField
	private long	creationDate;
	@DatabaseField
	private boolean	unread;

	public Notif() {}
	
	public Notif(Notifable item) {
		type = item.getNotifType();
		text = item.getText();
		link = item.getLink();
		creationDate = item.getCreationDate();
		unread = item.isUnread();
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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

	public boolean isUnread() {
		return unread;
	}
	
	public boolean isUnread(long lastSeenDate) {
		if(lastSeenDate == 0)
			return unread;
		return creationDate > lastSeenDate && unread;
	}

	public void setUnread(boolean unread) {
		this.unread = unread;
	}
	
	public interface Notifable {
		public String getNotifType();
		public String getText();
		public String getLink();
		public long getCreationDate();
		public boolean isUnread();
	}

	@Override
	public int compareTo(Notif another) {
		return -Long.valueOf(creationDate).compareTo(Long.valueOf(another.getCreationDate()));
	}
}
