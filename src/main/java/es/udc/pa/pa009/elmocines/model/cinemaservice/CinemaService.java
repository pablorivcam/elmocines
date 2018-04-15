package es.udc.pa.pa009.elmocines.model.cinemaservice;

import java.util.Calendar;
import java.util.List;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.util.MovieSessionsDto;
import es.udc.pojo.modelutil.data.Block;
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
	public List<Cinema> findCinemasByProvinceId(Long provinceId);

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

	/**
	 * Purchase tickets.
	 *
	 * @param userId
	 *            the user id
	 * @param creditCardNumber
	 *            the credit card number
	 * @param creditCardExpirationDate
	 *            the credit card expiration date
	 * @param sessionId
	 *            the session id
	 * @param locationsAmount
	 *            the locations amount
	 * @return the purchase
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @throws InputValidationException
	 *             the input validation exception
	 * @throws TooManyLocationsException
	 *             the too many locations exception
	 */
	public Purchase purchaseTickets(Long userId, String creditCardNumber, Calendar creditCardExpirationDate,
			Long sessionId, int locationsAmount)
			throws InstanceNotFoundException, InputValidationException, TooManyLocationsException;

	/**
	 * Gets the purchases.
	 *
	 * @param userId
	 *            the user id
	 * @param startIndex
	 *            the start index of the purchases that we want to get.
	 * @param count
	 *            the number of purchases that we want to get.
	 * @throws InputValidationException
	 *             the input validation exception
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @return the purchases
	 */
	public Block<Purchase> getPurchases(Long userId, int startIndex, int count)
			throws InputValidationException, InstanceNotFoundException;

	/**
	 * Gets the purchase.
	 *
	 * @param purchaseId
	 *            the purchase id
	 * @return the purchase
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 */
	public Purchase getPurchase(Long purchaseId) throws InstanceNotFoundException;

	/**
	 * Collect tickets.
	 *
	 * @param purchaseId
	 *            the purchase id
	 * @return the purchase
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @throws TicketsAlreadyCollectedException
	 *             the tickets already collected exception
	 */
	public Purchase collectTickets(Long purchaseId) throws InstanceNotFoundException, TicketsAlreadyCollectedException;

}
