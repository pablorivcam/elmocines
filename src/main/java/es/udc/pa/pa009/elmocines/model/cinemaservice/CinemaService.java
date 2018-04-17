package es.udc.pa.pa009.elmocines.model.cinemaservice;

import java.util.Calendar;
import java.util.List;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.util.MovieSessionsDto;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * The Interface CinemaService.
 */
public interface CinemaService {

	/**
	 * Gets the provinces.
	 *
	 * @return the provinces
	 */
	public List<Province> getProvinces();

	/**
	 * FIXME: esto debería ser un block o lo dejamos como list? Find cinemas by
	 * province id.
	 *
	 * @param provinceId
	 *            the province id
	 * @return the list
	 */
	public List<Cinema> findCinemasByProvinceId(Long provinceId)
			throws InstanceNotFoundException;;

	/**
	 * Find sessions by cinema id and a date range.
	 *
	 * @param cinemaId
	 *            the cinema id
	 * @param initDate
	 *            the init date
	 * @param finalDate
	 *            the final date
	 * @return the list of sessions
	 * @throws InputValidationException
	 *             the input validation exception
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 */
	public List<MovieSessionsDto> findSessionsByCinemaIdAndDate(Long cinemaId, Calendar date, Calendar finalDate)
			throws InputValidationException, InstanceNotFoundException;

	/**
	 * Find movie by id.
	 *
	 * @param movieId
	 *            the movie id
	 * @return the movie
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 */
	public Movie findMovieById(Long movieId) throws InstanceNotFoundException;

	/**
	 * Find session by session id.
	 *
	 * @param sessionId
	 *            the session id
	 * @return the session
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 */
	public Session findSessionBySessionId(Long sessionId) throws InstanceNotFoundException;
}
