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
	public List<Cinema> findCinemasByProvinceId(Long provinceId) throws InstanceNotFoundException{
		if(provinceId!=null){
			provinceDao.find(provinceId);
		}
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
