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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.room.Room;

/**
 * La clase Session que modela la entidad Session.
 */
@Entity
public class Session {

	/** The session id. */
	@Id
	@SequenceGenerator(name = "sessionIdGenerator", sequenceName = "sessionSeq")
	@GeneratedValue(generator = "sessionIdGenerator", strategy = GenerationType.AUTO)
	private long sessionId;

	/** The free locations count. */
	private int freeLocationsCount;

	/** The hour. */
	// Nota: usamos time porque solo queremos almacenar la hora.
	@Temporal(TemporalType.TIME)
	private Calendar hour;

	/** The price. */
	private BigDecimal price;

	/** The movie. */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "movieId")
	private Movie movie;

	/** The room. */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roomId")
	private Room room;

	/**
	 * Constructor vacío necesario para hibernate.
	 */
	public Session() {

	}

	/**
	 * Instantiates a new session.
	 *
	 * @param freeLocationsCount
	 *            the free locations count
	 * @param hour
	 *            the hour
	 * @param price
	 *            the price
	 * @param movie
	 *            the movie
	 * @param room
	 *            the room
	 */
	public Session(int freeLocationsCount, Calendar hour, BigDecimal price, Movie movie, Room room) {
		this.freeLocationsCount = freeLocationsCount;
		this.hour = hour;
		this.price = price;
		this.movie = movie;
		this.room = room;
	}

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public long getSessionId() {
		return sessionId;
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
	 * Gets the hour.
	 *
	 * @return the hour
	 */
	public Calendar getHour() {
		return hour;
	}

	/**
	 * Sets the hour.
	 *
	 * @param hour
	 *            the new hour
	 */
	public void setHour(Calendar hour) {
		this.hour = hour;
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

}
