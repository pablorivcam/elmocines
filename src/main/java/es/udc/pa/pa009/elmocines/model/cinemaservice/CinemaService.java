package es.udc.pa.pa009.elmocines.model.cinemaservice;

import java.util.List;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pojo.modelutil.data.Block;

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
	public List<Cinema> findCinemasByProvinceId(long provinceId);

	/**
	 * Gets the sessions on a cinema.
	 *
	 * @param cinemaId
	 *            the cinema id
	 * @param startIndex
	 *            the start index of the sessions that we want to get.
	 * @param count
	 *            the number of sessions that we want to get.
	 * @return the sessions
	 */
	public Block<Session> getSessions(long cinemaId, int startIndex, int count);

	/**
	 * Find movie by id.
	 *
	 * @param movie
	 *            the movie
	 * @return the movie
	 */
	public Movie findMovieById(Movie movie);

	/**
	 * Find session by session id.
	 *
	 * @param sessionId
	 *            the session id
	 * @return the session
	 */
	public Session findSessionBySessionId(long sessionId);

	/**
	 * Purchase tickets.
	 *
	 * @param sessionId
	 *            the session id
	 * @param locationsAmmount
	 *            the locations ammount
	 */
	public void purchaseTickets(long sessionId, int locationsAmmount);

	/**
	 * Gets the purchases.
	 *
	 * @param userId
	 *            the user id
	 * @param startIndex
	 *            the start index of the purchases that we want to get.
	 * @param count
	 *            the number of purchases that we want to get.
	 * @return the purchases
	 */
	public Block<Purchase> getPurchases(long userId, int startIndex, int count);

	/**
	 * Gets the purchase.
	 *
	 * @param purchaseId
	 *            the purchase id
	 * @return the purchase
	 */
	public Purchase getPurchase(long purchaseId);

	/**
	 * Collect tickets.
	 *
	 * @param purchaseId
	 *            the purchase id
	 * @return the purchase
	 */
	public Purchase collectTickets(long purchaseId);

}
