package fr.ydelouis.overflowme.api.entity;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import fr.ydelouis.overflowme.model.BadgeDao;

@DatabaseTable(tableName = "badge", daoClass = BadgeDao.class)
public class Badge
{
	public enum Rank { bronze, silver, gold	}
	public enum Type { named, tag_based	}

	@DatabaseField(id = true)
	@SerializedName("badge_id")
	private Integer	id;
	@DatabaseField
	private Rank	rank;
	@DatabaseField
	private String	name;
	@DatabaseField
	private String	description;
	@DatabaseField
	@SerializedName("award_count")
	private int		awardCount;
	@DatabaseField
	@SerializedName("badge_type")
	private Type type;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Rank getRank() {
		return rank;
	}

	public void setRank(Rank rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAwardCount() {
		return awardCount;
	}

	public void setAwardCount(int awardCount) {
		this.awardCount = awardCount;
	}
	
	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null)
			return false;
		if(!(o instanceof Badge))
			return false;
		return ((Badge) o).getId() == getId();
	}
	
	public static class BadgeRequest extends Request<Badge> {}
}
