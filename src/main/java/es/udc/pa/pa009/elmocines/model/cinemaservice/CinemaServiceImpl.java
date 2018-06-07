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
import es.udc.pa.pa009.elmocines.model.purchase.PurchaseDao;
import es.udc.pa.pa009.elmocines.model.room.RoomDao;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.session.SessionDao;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfileDao;
import es.udc.pa.pa009.elmocines.model.util.MovieSessionsDto;
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
	@Transactional(readOnly = true)
	public List<Cinema> findCinemasByProvinceId(Long provinceId) throws InstanceNotFoundException {
		if (provinceId != null) {
			provinceDao.find(provinceId);
		}
		return cinemaDao.findCinemasByProvinceId(provinceId);
	}

	@Override
	@Transactional(readOnly = true)
	public Movie findMovieById(Long movieId) throws InstanceNotFoundException {
		return movieDao.find(movieId);
	}

	@Override
	@Transactional(readOnly = true)
	public Session findSessionBySessionId(Long sessionId) throws InstanceNotFoundException {
		return sessionDao.find(sessionId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<MovieSessionsDto> findSessionsByCinemaIdAndDate(Long cinemaId, Calendar initDate)
			throws InputValidationException, InstanceNotFoundException {

		// Miramos si el cine existe
		cinemaDao.find(cinemaId);

		Calendar now = Calendar.getInstance();

		Calendar finalDate = Calendar.getInstance();
		finalDate.setTime(initDate.getTime());
		finalDate.set(Calendar.HOUR_OF_DAY, 23);
		finalDate.set(Calendar.MINUTE, 59);

		if (initDate.getTimeInMillis() < now.getTimeInMillis() - 30) {
			throw new InputValidationException("Init date cannot be before actual's date.");
		}

		List<Session> sessions = sessionDao.findSessionsByCinemaId(cinemaId, initDate, finalDate);
		List<MovieSessionsDto> movieSessions = new ArrayList<>();

		MovieSessionsDto movieSession = null;
		Movie movie = null;

		for (Session s : sessions) {
			if (s.getMovie() != movie) {
				movie = s.getMovie();
				movieSession = new MovieSessionsDto(movie, new ArrayList<>());
				movieSessions.add(movieSession);
			}
			movieSession.getSessions().add(s);
		}
		return movieSessions;
	}

	@Override
	public Cinema findCinemaByCinemaId(Long cinemaId) throws InstanceNotFoundException {
		return cinemaDao.find(cinemaId);
	}

}
