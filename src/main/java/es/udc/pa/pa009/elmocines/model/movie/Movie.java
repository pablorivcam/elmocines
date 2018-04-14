package es.udc.pa.pa009.elmocines.model.movie;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The Class Movie.
 */
@Entity
@Table(name = "Movies")
public class Movie {

	/** The movie id. */
	private Long movieId;

	/** The title. */
	private String title;

	/** The review. */
	private String review;

	/** The lenght. */
	private Integer lenght;

	/** The init date. */
	private Calendar initDate;

	/** The final date. */
	private Calendar finalDate;

	/**
	 * Constructor vacío necesario para hibernate.
	 */
	public Movie() {

	}

	/**
	 * Instantiates a new movie.
	 *
	 * @param title
	 *            the title
	 * @param review
	 *            the review
	 * @param lenght
	 *            the lenght
	 * @param initDate
	 *            the init date
	 * @param finalDate
	 *            the final date
	 */
	public Movie(String title, String review, Integer lenght, Calendar initDate, Calendar finalDate) {
		this.title = title;
		this.review = review;
		this.lenght = lenght;
		this.initDate = initDate;
		this.finalDate = finalDate;

		// FIXME: es necesario resetear las fechas si no tienen tiempo?
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	/**
	 * Gets the movie id.
	 *
	 * @return the movie id
	 */
	@Id
	@SequenceGenerator(name = "movieIdGenerator", sequenceName = "movieSeq")
	@GeneratedValue(generator = "movieIdGenerator", strategy = GenerationType.AUTO)
	public Long getMovieId() {
		return movieId;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the review.
	 *
	 * @return the review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Sets the review.
	 *
	 * @param review
	 *            the new review
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Gets the lenght.
	 *
	 * @return the lenght
	 */
	public Integer getLenght() {
		return lenght;
	}

	/**
	 * Sets the lenght.
	 *
	 * @param lenght
	 *            the new lenght
	 */
	public void setLenght(Integer lenght) {
		this.lenght = lenght;
	}

	/**
	 * Gets the inits the date.
	 *
	 * @return the inits the date
	 */
	@Temporal(TemporalType.DATE)
	public Calendar getInitDate() {
		return initDate;
	}

	/**
	 * Sets the inits the date.
	 *
	 * @param initDate
	 *            the new inits the date
	 */
	public void setInitDate(Calendar initDate) {
		this.initDate = initDate;
	}

	/**
	 * Gets the final date.
	 *
	 * @return the final date
	 */
	@Temporal(TemporalType.DATE)
	public Calendar getFinalDate() {
		return finalDate;
	}

	/**
	 * Sets the final date.
	 *
	 * @param finalDate
	 *            the new final date
	 */
	public void setFinalDate(Calendar finalDate) {
		this.finalDate = finalDate;
	}

}
