package es.udc.pa.pa009.elmocines.model.province;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;

/**
 * La clase Province que modela una provincia.
 */
@Entity
@Table(name = "Provinces")
public class Province {

	/** The province id. */
	private Long provinceId;

	/** The name. */
	private String name;

	/** The cinema. */
	private List<Cinema> cinemas;

	/**
	 * Instantiates a new province.
	 *
	 * @param name
	 *            the name
	 * @param cinemas
	 *            the cinemas
	 */
	public Province(String name, List<Cinema> cinemas) {
		this.name = name;
		this.cinemas = cinemas;
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
	@Id
	@SequenceGenerator(name = "provinceIdGenerator", sequenceName = "provinceSeq")
	@GeneratedValue(generator = "provinceIdGenerator", strategy = GenerationType.AUTO)
	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
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
	 * Gets the cinemas.
	 *
	 * @return the cinemas
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "province")
	public List<Cinema> getCinemas() {
		return cinemas;
	}

	/**
	 * Sets the cinemas.
	 *
	 * @param cinemas
	 *            the new cinemas
	 */
	public void setCinemas(List<Cinema> cinemas) {
		this.cinemas = cinemas;
	}

}
