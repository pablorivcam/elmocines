package es.udc.pa.pa009.elmocines.model.util;

import java.util.List;

import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.session.Session;

public class MovieSessionsDto {

	private Movie movie;

	private List<Session> sessions;

	public MovieSessionsDto(Movie movie, List<Session> sessions) {
		this.movie = movie;
		this.sessions = sessions;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public List<Session> getSessions() {
		return sessions;
	}

	public void setSessions(List<Session> sessions) {
		this.sessions = sessions;
	}

}
