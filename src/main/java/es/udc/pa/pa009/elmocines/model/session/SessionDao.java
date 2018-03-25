package es.udc.pa.pa009.elmocines.model.session;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;


/**
 * The Interface SessionDao.
 */
public interface SessionDao extends GenericDao<Session, Long> {

	
	/**
	 * Gets the sessions on a rooms.
	 *
	 * @param rooms
	 *            room
	 * @param startIndex
	 *            the start index of the sessions that we want to get.
	 * @param count
	 *            the number of sessions that we want to get.
	 */
	public List<Session> findSessionsByRoomsId(List<Long> rooms, int startIndex, int count);

}
