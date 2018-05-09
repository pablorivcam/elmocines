package es.udc.pa.pa009.elmocines.model.cinema;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.udc.pa.pa009.elmocines.model.province.Province;
import es.udc.pa.pa009.elmocines.model.room.Room;

/**
 * La clase Cinema para modelar un cine.
 */
@Entity
@Table(name = "Cinemas")
public class Cinema {

	/** The cinema id. */
	private Long cinemaId;

	/** The name. */
	private String name;

	/** The province. */
	private Province province;

	/** The room. */
	private List<Room> rooms;

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
	 * @param rooms
	 *            the rooms
	 */
	public Cinema(String name, Province province, List<Room> rooms) {
		this.name = name;
		this.province = province;
		this.rooms = rooms;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	/**
	 * Gets the cinema id.
	 *
	 * @return the cinema id
	 */
	@Id
	@SequenceGenerator(name = "cinemaSeqGenerator", sequenceName = "cinemaSeq")
	@GeneratedValue(generator = "cinemaSeqGenerator", strategy = GenerationType.AUTO)
	public Long getCinemaId() {
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
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "provinceId")
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
	 * Gets the rooms.
	 *
	 * @return the rooms
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cinema")
	public List<Room> getRooms() {
		return rooms;
	}

	/**
	 * Sets the rooms.
	 *
	 * @param rooms
	 *            the new rooms
	 */
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

}
