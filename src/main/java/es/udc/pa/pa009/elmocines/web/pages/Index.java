package es.udc.pa.pa009.elmocines.web.pages;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Cookies;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.util.MovieSessionsDto;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class Index {

	private static final String FAVOURITE_CINEMA_COOKIE = "favouriteCinema";

	@Property
	private Calendar date;

	@Property
	private Long favouriteCinemaId;

	@Property
	private List<MovieSessionsDto> sessions;

	@Property
	private MovieSessionsDto movieSession;

	@Property
	private Session session;

	@Property
	private Cinema cinema;

	@Property
	private List<String> dateSelectorModel;

	@Property
	private String dateSelected;

	@Inject
	private CinemaService cinemaService;

	@Inject
	private Locale locale;

	@Inject
	private Cookies cookies;

	// AÑADIDO PARA QUE AJAX PUEDA RE-RENDERIZAR LAS SESIONES SEGÚN LAS FECHAS
	@InjectComponent
	private Zone sessionsZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	void onPrepareForRender() {

		// Creamos el modelo del selector
		date = Calendar.getInstance();
		dateSelectorModel = new ArrayList<>();

		Calendar today = Calendar.getInstance();
		for (int i = 0; i < 7; i++) {
			dateSelectorModel.add(getFormat().format(today.getTime()));
			today.add(Calendar.DAY_OF_YEAR, 1);
		}

		// Obtenemos el id del cine favorito de las cookies
		String favouriteCinemaIdAsString = cookies.readCookieValue(FAVOURITE_CINEMA_COOKIE);

		if (favouriteCinemaIdAsString != null) {
			favouriteCinemaId = Long.parseLong(favouriteCinemaIdAsString);

			// Buscamos el cine por el id
			try {
				cinema = cinemaService.findCinemaByCinemaId(favouriteCinemaId);
			} catch (InstanceNotFoundException e1) {
				e1.printStackTrace();
			}

			// Obtnemos la cartelera del cine

			try {
				sessions = cinemaService.findSessionsByCinemaIdAndDate(favouriteCinemaId, date);
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (InputValidationException e) {
				e.printStackTrace();
			}

		} else {
			favouriteCinemaId = null;
		}

	}

	public Long getFavouriteCinemaIdForLinks() {
		return (favouriteCinemaId == null) ? 1L : favouriteCinemaId;
	}

	void onValueChangedFromDateSelected(String dateSelected) {

		String favouriteCinemaIdAsString = cookies.readCookieValue(FAVOURITE_CINEMA_COOKIE);

		if (favouriteCinemaIdAsString != null) {
			favouriteCinemaId = Long.parseLong(favouriteCinemaIdAsString);

			System.out.println("\n\n\nCambio de fecha a " + dateSelected + ". Recordamos que el cine favorito es "
					+ favouriteCinemaId + "\n\n\n");

			Calendar now = Calendar.getInstance();

			date = calendarFromString(dateSelected);

			// Comprobamos que el día seleccionado no sea el actual y de lo contrario le
			// cambiamos la hora
			if (date.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
				date.setTimeInMillis(now.getTimeInMillis());
			}

			try {
				sessions = cinemaService.findSessionsByCinemaIdAndDate(favouriteCinemaId, date);
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (InputValidationException e) {
				e.printStackTrace();
			}

			ajaxResponseRenderer.addRender(sessionsZone);
		}
	}

	// Añadido para el formateo de fechas
	public Format getFormat() {
		return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
	}

	public Calendar calendarFromString(String dateAsString) {
		Calendar cal = null;
		try {
			Date d = (Date) getFormat().parseObject(dateAsString);
			cal = Calendar.getInstance();
			cal.setTime(d);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return cal;

	}

}
