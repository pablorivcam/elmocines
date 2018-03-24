package es.udc.pa.pa009.elmocines.model.room;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("roomDao")
public class RoomDaoHibernate extends GenericDaoHibernate<Room, Long> implements RoomDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Room> findRoomsByCinemaId(Long cinemaId) {

		List<Room> rooms = null;

		rooms = getSession().createQuery("SELECT r FROM Room r WHERE r.cinemaId = :cinemaId ORDER BY s.name")
				.setParameter("cinemaId", cinemaId).getResultList();

		return rooms;
	}

}
