package fr.ydelouis.overflowme.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import fr.ydelouis.overflowme.entity.Notif;

public class NotifDao extends BaseDaoImpl<Notif, Long>
{
	private static final String FIELD_CREATIONDATE = "creationDate";
	
	public NotifDao(ConnectionSource connectionSource) throws SQLException {
		super(connectionSource, Notif.class);
	}
	
	public void clear() throws SQLException {
		TableUtils.clearTable(connectionSource, Notif.class);
	}

	public int create(Collection<Notif> notifs) throws SQLException{
		int result = 0;
		for(Notif notif : notifs) 
			result += create(notif);
		return result;
	}
	
	public List<Notif> queryByCreationDate() throws SQLException {
		return queryBuilder().orderBy(FIELD_CREATIONDATE, false).query();
	}
}
