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
import es.udc.pa.pa009.elmocines.model.room.RoomDao;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.session.SessionDao;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfileDao;
import es.udc.pa.pa009.elmocines.model.util.MovieSessionsDto;
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
	public List<Cinema> findCinemasByProvinceId(Long provinceId) throws InstanceNotFoundException{
		return cinemaDao.findCinemasByProvinceId(provinceId);
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
			throws InstanceNotFoundException, InputValidationException, TooManyLocationsException,ExpiredDateException {

		UserProfile user = new UserProfile();
		Session session = new Session();
		int sessionFreeLocations;
		Calendar current = Calendar.getInstance();

		if (locationsAmount > 10) {
			throw new InputValidationException();
		}

		if (sessionId != null) {
			session = sessionDao.find(sessionId);
		}
		
		Calendar sessionDate = session.getDate();
		
		if(sessionDate.compareTo(current)<0) {
			throw new ExpiredDateException();
		}

		sessionFreeLocations = session.getFreeLocationsCount();

		if (locationsAmount > sessionFreeLocations) {
			throw new TooManyLocationsException(sessionFreeLocations);
		}

		if (userId != null) {
			user = userDao.find(userId);
		}

		Purchase purchase = new Purchase(creditCardNumber, creditCardExpirationDate, locationsAmount,
				 current, session, user);

		purchaseDao.save(purchase);

		session.setFreeLocationsCount(sessionFreeLocations - locationsAmount);
		
		sessionDao.save(session);

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

		List<Purchase> purchases = purchaseDao.findPurchasesByUserId(userId, startIndex, count);
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
	public Purchase collectTickets(Long purchaseId) throws InstanceNotFoundException, TicketsAlreadyCollectedException, ExpiredDateException {		
		Purchase purchase = purchaseDao.find(purchaseId);
		
		Calendar current = Calendar.getInstance();
		Calendar ticketDate = purchase.getSession().getDate();

		if (purchase.getPurchaseState().equals(Purchase.PurchaseState.DELIVERED)) {
			throw new TicketsAlreadyCollectedException();
		}

		if(ticketDate.compareTo(current)<0) {
			throw new ExpiredDateException();
		}
		
		purchase.setPurchaseState(Purchase.PurchaseState.DELIVERED); // No hace falta llamar al save, hibernate lo hace
																		// automáticamente.
		purchaseDao.save(purchase);
		
		return purchase;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MovieSessionsDto> findSessionsByCinemaIdAndDate(Long cinemaId, Calendar initDate, Calendar finalDate)
			throws InputValidationException, InstanceNotFoundException {

		// Miramos si el cine existe
		cinemaDao.find(cinemaId);

		if (initDate.after(finalDate)) {
			throw new InputValidationException("Init date cannot be after final date.");
		}

		if (initDate.after(Calendar.getInstance())) {
			throw new InputValidationException("Init date cannot be after actual's date.");
		}

		List<Session> sessions = sessionDao.findSessionsByCinemaId(cinemaId, initDate, finalDate);
		List<Movie> movies = new ArrayList<>();
		List<MovieSessionsDto> movieSessions = new ArrayList<>();

		MovieSessionsDto movieSession = null;

		// FIXME: no me fio yo de esta implementación
		for (Session s : sessions) {
			if (!movies.contains(s.getMovie())) {
				movies.add(s.getMovie());
				movieSession = new MovieSessionsDto(s.getMovie(), new ArrayList<>());
				movieSessions.add(movieSession);
			} else {
				if (movieSession.getMovie() != s.getMovie()) {
					for (MovieSessionsDto m : movieSessions) {
						if (m.getMovie().equals(s.getMovie())) {
							movieSession = m;
						}
					}
				}
			}
			movieSession.getSessions().add(s);
		}

		return movieSessions;
	}

}
