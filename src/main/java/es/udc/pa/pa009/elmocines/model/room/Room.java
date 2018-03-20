package es.udc.pa.pa009.elmocines.model.room;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;

/**
 * La clase Room que modela la entidad Room.
 */
@Entity
public class Room {

	/** The room id. */
	@Id
	@SequenceGenerator(name = "roomIdGenerator", sequenceName = "roomSeq")
	@GeneratedValue(generator = "roomIdGenerator", strategy = GenerationType.AUTO)
	private long roomId;

	/** The name. */
	private String name;

	/** The capacity. */
	private int capacity;

	/** The cinema. */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cinemaId")
	private Cinema cinema;

	/**
	 * Constructor vacío necesario para hibernate.
	 */
	public Room() {

	}

	/**
	 * Instantiates a new room.
	 *
	 * @param name
	 *            the name
	 * @param capacity
	 *            the capacity
	 * @param cinema
	 *            the cinema
	 */
	public Room(String name, int capacity, Cinema cinema) {
		this.name = name;
		this.capacity = capacity;
		this.cinema = cinema;
	}

	/**
	 * Gets the room id.
	 *
	 * @return the room id
	 */
	public long getRoomId() {
		return roomId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the capacity.
	 *
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * Sets the capacity.
	 *
	 * @param capacity
	 *            the new capacity
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Gets the cinema.
	 *
	 * @return the cinema
	 */
	public Cinema getCinema() {
		return cinema;
	}

	/**
	 * Sets the cinema.
	 *
	 * @param cinema
	 *            the new cinema
	 */
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

}
