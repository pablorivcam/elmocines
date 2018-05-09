package es.udc.pa.pa009.elmocines.model.room;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;

/**
 * La clase Room que modela la entidad Room.
 */
@Entity
@Table(name = "Rooms")
public class Room {

	/** The room id. */
	private Long roomId;

	/** The name. */
	private String name;

	/** The capacity. */
	private Integer capacity;

	/** The cinema. */
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
	public Room(String name, Integer capacity, Cinema cinema) {
		this.name = name;
		this.capacity = capacity;
		this.cinema = cinema;
	}

	/**
	 * Gets the room id.
	 *
	 * @return the room id
	 */
	@Id
	@SequenceGenerator(name = "roomIdGenerator", sequenceName = "roomSeq")
	@GeneratedValue(generator = "roomIdGenerator", strategy = GenerationType.AUTO)
	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
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
	public Integer getCapacity() {
		return capacity;
	}

	/**
	 * Sets the capacity.
	 *
	 * @param capacity
	 *            the new capacity
	 */
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	/**
	 * Gets the cinema.
	 *
	 * @return the cinema
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cinemaId")
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
