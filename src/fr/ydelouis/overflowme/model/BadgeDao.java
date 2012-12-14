package fr.ydelouis.overflowme.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import fr.ydelouis.overflowme.api.entity.Badge;

public class BadgeDao extends BaseDaoImpl<Badge, Integer>
{
	private static final String FIELD_NAME = "name";
	
	public BadgeDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Badge.class);
	}
	
	public int createAll(Collection<Badge> badges) throws SQLException{
		int result = 0;
		for(Badge badge : badges) 
			result += create(badge);
		return result;
	}
	
	public int createOrUpdateAll(Collection<Badge> badges) throws SQLException{
		int result = 0;
		for(Badge badge : badges) 
			result += createOrUpdate(badge).getNumLinesChanged();
		return result;
	}

	public List<Badge> queryByName(int page, int pageSize) throws SQLException {
		return queryBuilder()
				.orderBy(FIELD_NAME, true)
				.offset((long) (page * pageSize))
				.limit((long) pageSize)
				.query();
	}
}
