package es.udc.pa.pa009.elmocines.web.pages.cinema;

import java.util.Calendar;
import java.util.List;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.util.MovieSessionsDto;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class CinemaBillboard {

	private Long cinemaId;

	@Property
	private List<MovieSessionsDto> sessions;

	@Property
	private MovieSessionsDto movieSession;

	@Property
	private Session session;

	@Property
	private Cinema cinema;

	private Calendar initDate;
	private Calendar finalDate;

	@Inject
	private CinemaService cinemaService;

	void onActivate(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	Long onPassivate() {
		return cinemaId;
	}

	void onPrepareForRender() {

		try {
			cinema = cinemaService.findCinemaByCinemaId(cinemaId);
		} catch (InstanceNotFoundException e1) {
			e1.printStackTrace();
		}

		initDate = Calendar.getInstance();
		finalDate = Calendar.getInstance();
		finalDate.add(Calendar.DAY_OF_YEAR, 100);

		try {
			sessions = cinemaService.findSessionsByCinemaIdAndDate(cinemaId, initDate, finalDate);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}
	}

	public Long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

}
