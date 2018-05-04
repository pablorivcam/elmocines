package es.udc.pa.pa009.elmocines.web.pages.cinema;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class SessionDetails {

	private Long sessionId;

	@Property
	private int amount;

	@Property
	private Session session;

	@Inject
	private CinemaService cinemaService;

	@Inject
	private Locale locale;

	@Inject
	private Request request;

	void onActivate(Long sessionId) {
		this.sessionId = sessionId;
	}

	Long onPassivate() {
		return sessionId;
	}

	void onPrepareForRender() {

		try {
			session = cinemaService.findSessionBySessionId(sessionId);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public Format getFormat() {
		return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
	}

}
