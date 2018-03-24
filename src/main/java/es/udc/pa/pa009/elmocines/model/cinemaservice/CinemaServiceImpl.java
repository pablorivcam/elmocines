package es.udc.pa.pa009.elmocines.model.cinemaservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.cinema.CinemaDao;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.room.Room;
import es.udc.pa.pa009.elmocines.model.room.RoomDao;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.session.SessionDao;
import es.udc.pojo.modelutil.data.Block;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Service("cinemaService")
@Transactional
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	SessionDao sessionDao;

	@Autowired
	CinemaDao cinemaDao;

	@Autowired
	RoomDao roomDao;

	@Override
	public List<Province> getProvinces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Cinema> findCinemasByProvinceId(Long provinceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Block<Session> getSessionsByCinemaId(Long cinemaId, int startIndex, int count)
			throws InputValidationException, InstanceNotFoundException

	{
		if (startIndex < 0 || count < 0) {
			throw new InputValidationException();
		}

		if (cinemaId != null) {
			cinemaDao.find(cinemaId);

		}

		List<Room> rooms = roomDao.findRoomsByCinemaId(cinemaId);

		List<Long> roomIds = new ArrayList<>();
		for (Room r : rooms) {
			roomIds.add(r.getRoomId());
		}

		// Buscamos las count+1 sessiones desde el start index
		List<Session> sessions = sessionDao.findSessionsByRoomsId(roomIds, startIndex, count);
		// Si las sessiones buscadas llenan toda la lista, es decir count+1>count, es
		// que quedan más items por buscar
		boolean existMoreItems = sessions.size() > count;

		if (existMoreItems) {
			sessions.remove(sessions.size() - 1);
		}

		return new Block<>(sessions, existMoreItems);
	}

	@Override
	public Movie findMovieById(Movie movie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session findSessionBySessionId(Long sessionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void purchaseTickets(Long sessionId, int locationsAmmount) {
		// TODO Auto-generated method stub

	}

	@Override
	public Block<Purchase> getPurchases(Long userId, int startIndex, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Purchase getPurchase(Long purchaseId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Purchase collectTickets(Long purchaseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
