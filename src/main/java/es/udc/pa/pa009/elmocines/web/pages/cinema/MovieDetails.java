package es.udc.pa.pa009.elmocines.web.pages.cinema;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class MovieDetails {

	private Long movieId;

	@Property
	private Movie movie;

	@Inject
	private CinemaService cinemaService;

	void onActivate(Long movieId) {
		this.movieId = movieId;
		try {
			movie = cinemaService.findMovieById(movieId);
		} catch (InstanceNotFoundException e) {
		}
	}

	Object onPassivate() {
		return movieId;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

}
