package es.udc.pa.pa009.elmocines.model.room;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface RoomDao extends GenericDao<Room, Long> {

	public List<Room> findRoomsByCinemaId(Long cinemaId);

}
