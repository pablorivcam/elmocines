package es.udc.pa.pa009.elmocines.model.session;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("sessionDao")
public class SessionDaoHibernate extends GenericDaoHibernate<Session, Long> implements SessionDao {

	// FIXME: ESTO ESTÁ BIEN ASI?
	@SuppressWarnings("unchecked")
	@Override
	public List<Session> findSessionsByRoomsId(List<Long> rooms, int startIndex, int count) {
		List<Session> sessions = null;

		String queryString = "SELECT s FROM Session s ";
		if (rooms.size() > 0) {
			queryString += "WHERE ";
			for (int i = 0; i < rooms.size(); i++) {
				if (i != 0) {
					queryString += "OR ";
				}
				queryString += "s.room.roomId= :roomId" + i + " ";
			}
		}
		queryString += "ORDER BY s.hour DESC";

		Query query = getSession().createQuery(queryString);

		for (int i = 0; i < rooms.size(); i++) {
			query.setParameter("roomId" + i, rooms.get(i));
		}

		sessions = query.setFirstResult(startIndex).setMaxResults(count).getResultList();

		return sessions;
	}

}
