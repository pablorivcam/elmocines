package es.udc.pa.pa009.elmocines.model.cinemaservice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.cinema.CinemaDao;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.movie.MovieDao;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.province.ProvinceDao;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchase.PurchaseDao;
import es.udc.pa.pa009.elmocines.model.room.Room;
import es.udc.pa.pa009.elmocines.model.room.RoomDao;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.session.SessionDao;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfileDao;
import es.udc.pojo.modelutil.data.Block;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Service("cinemaService")
@Transactional
public class CinemaServiceImpl implements CinemaService {

	@Autowired
	UserProfileDao userDao;

	@Autowired
	SessionDao sessionDao;

	@Autowired
	CinemaDao cinemaDao;

	@Autowired
	RoomDao roomDao;

	@Autowired
	PurchaseDao purchaseDao;

	@Autowired
	ProvinceDao provinceDao;

	@Autowired
	MovieDao movieDao;

	@Override
	public List<Province> getProvinces() {
		return provinceDao.getProvinces();
	}

	@Override
	public List<Cinema> findCinemasByProvinceId(Long provinceId) {
		return cinemaDao.findCinemasByProvinceId(provinceId);
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
	public Movie findMovieById(Long movieId) throws InstanceNotFoundException {
		return movieDao.find(movieId);
	}

	@Override
	public Session findSessionBySessionId(Long sessionId) throws InstanceNotFoundException {
		return sessionDao.find(sessionId);
	}

	@Override
	public Purchase purchaseTickets(Long userId, String creditCardNumber, Calendar creditCardExpirationDate,
			Long sessionId, int locationsAmount)
			throws InstanceNotFoundException, InputValidationException, TooManyLocationsException {

		UserProfile user = new UserProfile();
		Session session = new Session();
		int sessionFreeLocations;

		if (locationsAmount > 10) {
			throw new InputValidationException();
		}

		if (sessionId != null) {
			session = sessionDao.find(sessionId);
		}

		sessionFreeLocations = session.getFreeLocationsCount();

		if (locationsAmount > sessionFreeLocations) {
			throw new TooManyLocationsException(sessionFreeLocations);
		}

		if (userId != null) {
			user = userDao.find(userId);
		}

		Purchase purchase = new Purchase(creditCardNumber, creditCardExpirationDate, locationsAmount,
				Purchase.PurchaseState.PENDING, Calendar.getInstance(), session, user);

		purchaseDao.save(purchase);

		session.setFreeLocationsCount(sessionFreeLocations - locationsAmount);

		return purchase;
	}

	@Override
	public Block<Purchase> getPurchases(Long userId, int startIndex, int count)
			throws InputValidationException, InstanceNotFoundException {

		if (startIndex < 0 || count < 0) {
			throw new InputValidationException();
		}

		if (userId != null) {
			userDao.find(userId);
		}

		List<Purchase> purchases = purchaseDao.findPurchasesByUserId(userId);
		boolean existMoreItems = purchases.size() > count;

		if (existMoreItems) {
			purchases.remove(purchases.size() - 1);
		}

		return new Block<>(purchases, existMoreItems);
	}

	@Override
	public Purchase getPurchase(Long purchaseId) throws InstanceNotFoundException {
		return purchaseDao.find(purchaseId);
	}

	@Override
	public Purchase collectTickets(Long purchaseId) throws InstanceNotFoundException, TicketsAlreadyCollectedException {
		Purchase purchase = purchaseDao.find(purchaseId);

		if (purchase.getPurchaseState().equals(Purchase.PurchaseState.DELIVERED))
			throw new TicketsAlreadyCollectedException();

		purchase.setPurchaseState(Purchase.PurchaseState.DELIVERED); // No hace falta llamar al save, hibernate lo hace
																		// automáticamente.
		return purchase;
	}

}
