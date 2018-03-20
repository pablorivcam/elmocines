package es.udc.pa.pa009.elmocines.model.province;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;

/**
 * La clase Province que modela una provincia.
 */
@Entity
public class Province {

	/** The province id. */
	@Id
	@SequenceGenerator(name = "provinceIdGenerator", sequenceName = "provinceSeq")
	@GeneratedValue(generator = "provinceIdGenerator", strategy = GenerationType.AUTO)
	private long provinceId;

	/** The name. */
	private String name;

	/** The cinema. */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
	private Cinema cinema;

	/**
	 * Instantiates a new province.
	 *
	 * @param name
	 *            the name
	 * @param cinema
	 *            the cinema
	 */
	public Province(String name, Cinema cinema) {
		this.name = name;
		this.cinema = cinema;
	}

	/**
	 * Constructor vacío por defecto necesario para hibernate.
	 */
	public Province() {

	}

	/**
	 * Gets the province id.
	 *
	 * @return the province id
	 */
	public long getProvinceId() {
		return provinceId;
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
