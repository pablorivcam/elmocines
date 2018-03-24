package es.udc.pa.pa009.elmocines.model.session;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

/**
 * The Interface SessionDao.
 */
public interface SessionDao extends GenericDao<Session, Long> {

	/**
	 * Find sessions by rooms id.
	 *
	 * @param rooms
	 *            the rooms
	 * @param startIndex
	 *            the start index
	 * @param count
	 *            the count
	 * @return the list
	 */
	public List<Session> findSessionsByRoomsId(List<Long> rooms, int startIndex, int count);

}
