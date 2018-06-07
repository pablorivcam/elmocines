package es.udc.pa.pa009.elmocines.model.session;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("sessionDao")
public class SessionDaoHibernate extends GenericDaoHibernate<Session, Long> implements SessionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Session> findSessionsByCinemaId(Long cinemaId, Calendar initDate, Calendar finalDate) {

		return getSession()
				.createQuery("SELECT s FROM Session s WHERE s.room.cinema.cinemaId = :cinemaId "
						+ "AND s.date BETWEEN :initDate AND :finalDate ORDER BY s.movie, s.date")
				.setParameter("cinemaId", cinemaId).setParameter("initDate", initDate)
				.setParameter("finalDate", finalDate).getResultList();

	}

}
