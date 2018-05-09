/**
 * 
 */
package es.udc.pa.pa009.elmocines.web.pages.purchase;


import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.Messages;
import es.udc.pa.pa009.elmocines.model.userservice.UserService;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicy;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicyType;
import es.udc.pa.pa009.elmocines.web.util.UserSession;
import es.udc.pa.pa009.elmocines.model.cinemaservice.CinemaService;
import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pa.pa009.elmocines.model.purchaseservice.ExpiredDateException;
import es.udc.pa.pa009.elmocines.model.purchaseservice.PurchaseService;
import es.udc.pa.pa009.elmocines.model.purchaseservice.TooManyLocationsException;


@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class PurchaseSession {
	
	private Long sessionId;
	private Purchase purchase;
	
	@Property
	private String creditCardNumber;
	
	@Property
	private Session session;

	@Property
	private int locationsAmount;

	@Property
	private String cardExpiredDate;

	@SessionState(create = false)
	private UserSession userSession;
	
	@Inject
	private Locale locale;
	
	@Inject
	private CinemaService cinemaService;

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
	
	
	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

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
	
	void onValidateFromPurchaseForm() throws InstanceNotFoundException, InputValidationException, ExpiredDateException{

		if (!purchaseForm.isValid()) {
			return;
		}
		
		Calendar cal = Calendar.getInstance();
		DateFormat df =DateFormat.getDateInstance(DateFormat.DEFAULT,locale);
	
		if ((locationsAmount>10) || (locationsAmount==0)){
			purchaseForm.recordError(locationsAmountField, messages.get("error-locationsAmountNotAllowed"));
		} else {
			try {
				cal.setTime(df.parse(cardExpiredDate));
				if(cal.before(Calendar.getInstance())){
					purchaseForm.recordError(expiredDateField, messages.get("error-expiredCard"));
				}
				else{
				purchase=purchaseService.purchaseTickets(userSession.getUserProfileId(),
					creditCardNumber,cal,sessionId,locationsAmount);
				}
			} catch (TooManyLocationsException e) {
				purchaseForm.recordError(locationsAmountField, messages.get("error-freeLocationsPurchased"));
			} catch (ParseException e) {
				purchaseForm.recordError(expiredDateField, messages.get("error-expiredDateFormat"));
			}
		}

	}

	Object onSuccess() {
		successfulPurchase.setPurchaseId(purchase.getPurchaseId());
		return successfulPurchase;
	}
	
	public Format getFormat() {
		return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
	}

}