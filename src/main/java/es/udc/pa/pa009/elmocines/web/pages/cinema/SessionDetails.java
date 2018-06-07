package es.udc.pa.pa009.elmocines.web.pages.cinema;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchaseservice.ExpiredDateException;
import es.udc.pa.pa009.elmocines.model.purchaseservice.PurchaseService;
import es.udc.pa.pa009.elmocines.model.purchaseservice.TooManyLocationsException;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile.Role;
import es.udc.pa.pa009.elmocines.model.userservice.UserService;
import es.udc.pa.pa009.elmocines.web.pages.purchase.SuccessfulPurchase;
import es.udc.pa.pa009.elmocines.web.pages.user.Login;
import es.udc.pa.pa009.elmocines.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class SessionDetails {

	private Long sessionId;
	private Purchase purchase;

	@Property
	private String creditCardNumber;

	@Property
	@Persist
	private int locationsAmount;

	@Property
	private String cardExpiredDate;

	@Inject
	private UserService userService;

	@Inject
	private PurchaseService purchaseService;

	@Component(id = "creditCardNumber")
	private TextField creditCardField;

	@Component(id = "cardExpiredDate")
	private TextField expiredDateField;

	@Component(id = "locationsAmount")
	private TextField locationsAmountField;

	@Inject
	private Messages messages;

	@Component
	private Form purchaseForm;

	@InjectPage
	private SuccessfulPurchase successfulPurchase;

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

	@Property
	@SessionState(create = false)
	private UserSession userSession;

	// Zona par actualizar la zona de las localizaciones
	@InjectComponent
	private Zone locationsZone;

	@Inject
	private AjaxResponseRenderer ajaxResponseRenderer;

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	void onActivate(Long sessionId) {
		this.sessionId = sessionId;
		try {
			session = cinemaService.findSessionBySessionId(sessionId);
		} catch (InstanceNotFoundException e) {
		}
	}

	Long onPassivate() {
		return sessionId;
	}

	void onValidateFromPurchaseForm() throws InstanceNotFoundException, InputValidationException, ExpiredDateException {

		if (!purchaseForm.isValid()) {
			return;
		}

		Calendar cal = Calendar.getInstance();
		DateFormat df = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);

		if ((locationsAmount > 10) || (locationsAmount == 0)) {
			purchaseForm.recordError(locationsAmountField, messages.get("error-locationsAmountNotAllowed"));
		} else {
			try {
				cal.setTime(df.parse(cardExpiredDate));
				if (cal.before(Calendar.getInstance())) {
					purchaseForm.recordError(expiredDateField, messages.get("error-expiredCard"));
				} else {
					purchase = purchaseService.purchaseTickets(userSession.getUserProfileId(), creditCardNumber, cal,
							sessionId, locationsAmount);
				}
			} catch (TooManyLocationsException e) {
				purchaseForm.recordError(locationsAmountField, messages.get("error-freeLocationsPurchased"));
			} catch (ParseException e) {
				purchaseForm.recordError(expiredDateField, messages.get("error-expiredDateFormat"));
			}
		}

	}

	public Format getFormat() {
		return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
	}

	public boolean isClient() {
		return userSession.getRole().equals(Role.CLIENT);
	}

	Object onSuccess() {
		successfulPurchase.setPurchaseId(purchase.getPurchaseId());
		return successfulPurchase;
	}

	void onPlusLocation() {
		if (locationsAmount < 10) {
			locationsAmount += 1;
		}
		formatAjax();

	}

	void onMinusLocation() {
		if (locationsAmount > 0) {
			locationsAmount -= 1;
		}
		formatAjax();
	}

	private void formatAjax() {
		if (request.isXHR()) {
			ajaxResponseRenderer.addRender("locationsZone", locationsZone);
		}
	}

}
