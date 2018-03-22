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
	@Id
	@SequenceGenerator(name = "movieIdGenerator", sequenceName = "movieSeq")
	@GeneratedValue(generator = "movieIdGenerator", strategy = GenerationType.AUTO)
	private long movieId;

	/** The title. */
	private String title;

	/** The review. */
	private String review;

	/** The lenght. */
	@Temporal(TemporalType.TIME)
	private Calendar lenght;

	/** The init date. */
	// Nota: usamos date porque solo queremos almacenar la fecha.
	@Temporal(TemporalType.DATE)
	private Calendar initDate;

	/** The final date. */
	@Temporal(TemporalType.DATE)
	private Calendar finalDate;

	/**
	 * Constructor vacío necesario para hibernate.
	 */
	public Movie() {

	}

	public Movie(String title, String review, Calendar lenght, Calendar initDate, Calendar finalDate) {
		this.title = title;
		this.review = review;
		this.lenght = lenght;
		this.initDate = initDate;
		this.finalDate = finalDate;
	}

	public long getMovieId() {
		return movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public Calendar getLenght() {
		return lenght;
	}

	public void setLenght(Calendar lenght) {
		this.lenght = lenght;
	}

	public Calendar getInitDate() {
		return initDate;
	}

	public void setInitDate(Calendar initDate) {
		this.initDate = initDate;
	}

	public Calendar getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Calendar finalDate) {
		this.finalDate = finalDate;
	}

}
