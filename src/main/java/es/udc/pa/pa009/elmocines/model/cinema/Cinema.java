package es.udc.pa.pa009.elmocines.model.cinema;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.room.Room;

/**
 * La clase Cinema para modelar un cine.
 */
@Entity
public class Cinema {

	/** The cinema id. */
	@Id
	@SequenceGenerator(name = "cinemaSeqGenerator", sequenceName = "cinemaSeq")
	@GeneratedValue(generator = "cinemaSeqGenerator", strategy = GenerationType.AUTO)
	private long cinemaId;

	/** The name. */
	private String name;

	/** The province. */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "provinceId")
	private Province province;

	/** The room. */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
	private Room room;

	/**
	 * Constructor vacío necesario para hibernate.
	 */
	public Cinema() {

	}

	/**
	 * Instantiates a new cinema.
	 *
	 * @param name
	 *            the name
	 * @param province
	 *            the province
	 * @param room
	 *            the room
	 */
	public Cinema(String name, Province province, Room room) {
		this.name = name;
		this.province = province;
		this.room = room;
	}

	/**
	 * Gets the cinema id.
	 *
	 * @return the cinema id
	 */
	public long getCinemaId() {
		return cinemaId;
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
	 * Gets the province.
	 *
	 * @return the province
	 */
	public Province getProvince() {
		return province;
	}

	/**
	 * Sets the province.
	 *
	 * @param province
	 *            the new province
	 */
	public void setProvince(Province province) {
		this.province = province;
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
