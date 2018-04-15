package es.udc.pa.pa009.elmocines.model.session;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.room.Room;

/**
 * La clase Session que modela la entidad Session.
 */
@Entity
@Table(name = "SessionMovies")
public class Session {

	/** The session id. */

	private long sessionId;

	/** The free locations count. */
	private int freeLocationsCount;

	/** The date. */
	private Calendar date;

	/** The price. */
	private BigDecimal price;

	/** The movie. */
	private Movie movie;

	/** The room. */
	private Room room;

	private Integer version;

	/**
	 * Constructor vacío necesario para hibernate.
	 */
	public Session() {

	}

	/**
	 * Instantiates a new session.
	 *
	 * @param date
	 *            the date
	 * @param price
	 *            the price
	 * @param movie
	 *            the movie
	 * @param room
	 *            the room
	 */
	public Session(Calendar date, BigDecimal price, Movie movie, Room room) {

		this.date = date;
		this.price = price;
		this.movie = movie;
		this.room = room;

		// FIXME: ?
		freeLocationsCount = room.getCapacity();

		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.SECOND, 0);

	}

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	@Id
	@SequenceGenerator(name = "sessionIdGenerator", sequenceName = "sessionSeq")
	@GeneratedValue(generator = "sessionIdGenerator", strategy = GenerationType.AUTO)
	public long getSessionId() {
		return sessionId;
	}

	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Gets the free locations count.
	 *
	 * @return the free locations count
	 */
	public int getFreeLocationsCount() {
		return freeLocationsCount;
	}

	/**
	 * Sets the free locations count.
	 *
	 * @param freeLocationsCount
	 *            the new free locations count
	 */
	public void setFreeLocationsCount(int freeLocationsCount) {
		this.freeLocationsCount = freeLocationsCount;
	}

	/**
	 * Gets the price.
	 *
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * Sets the price.
	 *
	 * @param price
	 *            the new price
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	/**
	 * Gets the movie.
	 *
	 * @return the movie
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "movieId")
	public Movie getMovie() {
		return movie;
	}

	/**
	 * Sets the movie.
	 *
	 * @param movie
	 *            the new movie
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	/**
	 * Gets the room.
	 *
	 * @return the room
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roomId")
	public Room getRoom() {
		return room;
	}

	/**
	 * Sets the room.
	 *
	 * @param room
	 *            the new room
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(Calendar date) {
		this.date = date;
		date.set(Calendar.MILLISECOND, 0);
		date.set(Calendar.SECOND, 0);
	}

}
