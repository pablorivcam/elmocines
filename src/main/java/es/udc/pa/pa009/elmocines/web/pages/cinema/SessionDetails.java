package es.udc.pa.pa009.elmocines.web.pages.cinema;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

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

	@InjectComponent
	private Zone ticketsZone;

	@Inject
	private Request request;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

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

	public boolean getAmountValid() {
		return (amount > 0);
	}

	void onPlusOneUnit() {
		amount += 1;
		formatAjax();
	}

	void onMinusOneUnit() {
		amount -= 1;
		formatAjax();
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

	private void formatAjax() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("ticketsZone", ticketsZone);
		}
	}

}
