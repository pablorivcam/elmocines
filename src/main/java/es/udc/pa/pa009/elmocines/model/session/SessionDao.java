package es.udc.pa.pa009.elmocines.model.session;

import java.util.Calendar;
import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

/**
 * The Interface SessionDao.
 */
public interface SessionDao extends GenericDao<Session, Long> {

	/**
	 * Find sessions by cinema id.
	 *
	 * @param cinemaId
	 *            the cinema id
	 * @param initDate
	 *            the init date
	 * @param finalDate
	 *            the final date
	 * @return the list
	 */
	public List<Session> findSessionsByCinemaId(Long cinemaId, Calendar initDate, Calendar finalDate);

}
