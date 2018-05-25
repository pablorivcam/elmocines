package es.udc.pa.pa009.elmocines.web.pages.cinema;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.web.pages.purchase.PurchaseSession;
import es.udc.pa.pa009.elmocines.web.pages.user.Login;
import es.udc.pa.pa009.elmocines.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class SessionDetails {

	private Long sessionId;
	
	@InjectPage
	private PurchaseSession purchaseSession;
	
	@InjectPage
	private Login login;

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
	
	@SessionState(create=false)
    private UserSession userSession;

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
	
	Object onNextPage(long sessionId){
		
		if(userSession==null){
			login.setSessionId(sessionId);
			return login;
		}
		else{
			purchaseSession.setSessionId(sessionId);
			return purchaseSession;
		}
		
	}

}
