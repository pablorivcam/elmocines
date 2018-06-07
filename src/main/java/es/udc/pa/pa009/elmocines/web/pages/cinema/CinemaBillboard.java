package es.udc.pa.pa009.elmocines.web.pages.cinema;

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

public class CinemaBillboard {

	private static final String FAVOURITE_CINEMA_COOKIE = "favouriteCinema";
	private static final int FAVOURITE_CINEMA_COOKIE_AGE = 30 * 24 * 3600; // 30 days in seconds

	private Long cinemaId;

	@Property
	private Calendar date;

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

	// Zona par actualizar la zona del botón de cine favorito
	@InjectComponent
	private Zone favoriteCinemaZone;

	// AÑADIDO PARA QUE AJAX PUEDA RE-RENDERIZAR LAS SESIONES SEGÚN LAS FECHAS
	@InjectComponent
	private Zone sessionsZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	void onActivate(Long cinemaId) {
		this.cinemaId = cinemaId;
	}

	Long onPassivate() {
		return cinemaId;
	}

	void onPrepareForRender() {

		// Obtenemos el id del cine favorito de las cookies
		String favouriteCinemaIdAsString = cookies.readCookieValue(FAVOURITE_CINEMA_COOKIE);

		if (favouriteCinemaIdAsString != null)
			favouriteCinemaId = Long.parseLong(favouriteCinemaIdAsString);
		else
			favouriteCinemaId = null;

		// Creamos el modelo del selector
		date = Calendar.getInstance();
		dateSelectorModel = new ArrayList<>();

		Calendar today = Calendar.getInstance();
		for (int i = 0; i < 7; i++) {
			dateSelectorModel.add(getFormat().format(today.getTime()));
			today.add(Calendar.DAY_OF_YEAR, 1);
		}

		// Buscamos el cine por el id
		try {
			cinema = cinemaService.findCinemaByCinemaId(cinemaId);
		} catch (InstanceNotFoundException e1) {
			e1.printStackTrace();
		}

		// Obtnemos la cartelera del cine

		Calendar finalDate = Calendar.getInstance();
		finalDate.set(Calendar.HOUR_OF_DAY, 23);
		finalDate.set(Calendar.MINUTE, 59);

		try {
			sessions = cinemaService.findSessionsByCinemaIdAndDate(cinemaId, date, finalDate);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}
	}

	public Boolean getFavouriteCinemaId() {
		return (favouriteCinemaId != null && favouriteCinemaId == cinemaId);
	}

	void onValueChangedFromDateSelected(String dateSelected) {

		Calendar now = Calendar.getInstance();
		Calendar finalDate = Calendar.getInstance();

		date = calendarFromString(dateSelected);

		// Comprobamos que el día seleccionado no sea el actual y de lo contrario le
		// cambiamos la hora
		if (date.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
			date.setTimeInMillis(now.getTimeInMillis());
		}

		finalDate = calendarFromString(dateSelected);
		finalDate.set(Calendar.HOUR_OF_DAY, 23);
		finalDate.set(Calendar.MINUTE, 59);

		try {
			sessions = cinemaService.findSessionsByCinemaIdAndDate(cinemaId, date, finalDate);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

		ajaxResponseRenderer.addRender(sessionsZone);

	}

	// Método al clikar el establecer el cine favorito
	void onSetFavouriteCinema() {

		if (favouriteCinemaId == null || favouriteCinemaId != cinemaId) {
			cookies.getBuilder(FAVOURITE_CINEMA_COOKIE, cinemaId.toString()).setMaxAge(FAVOURITE_CINEMA_COOKIE_AGE)
					.write();
			favouriteCinemaId = cinemaId;
		} else {
			cookies.getBuilder(FAVOURITE_CINEMA_COOKIE, cinemaId.toString()).delete();
			favouriteCinemaId = null;
		}
		ajaxResponseRenderer.addRender(favoriteCinemaZone);

	}

	public Long getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Long cinemaId) {
		this.cinemaId = cinemaId;
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
