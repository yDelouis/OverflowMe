package fr.ydelouis.overflowme.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import fr.ydelouis.overflowme.api.entity.RepChange;

public class RepChangeDao extends BaseDaoImpl<RepChange, Long>
{
	private static final String FIELD_CREATIONDATE = "creationDate";
	
	public RepChangeDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, RepChange.class);
	}
	
	public int createAll(Collection<RepChange> repChanges) throws SQLException{
		int result = 0;
		for(RepChange repChange : repChanges) 
			result += create(repChange);
		return result;
	}

	public List<RepChange> queryByCreationDate(int page, int pageSize) throws SQLException {
		return queryBuilder()
				.orderBy(FIELD_CREATIONDATE, false)
				.offset((long) (page * pageSize))
				.limit((long) pageSize)
				.query();
	}
}
