package es.udc.pa.pa009.elmocines.web.pages.user;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.PasswordField;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile.Role;
import es.udc.pa.pa009.elmocines.model.userservice.UserProfileDetails;
import es.udc.pa.pa009.elmocines.model.userservice.UserService;
import es.udc.pa.pa009.elmocines.web.pages.Index;
import es.udc.pa.pa009.elmocines.web.pages.cinema.SessionDetails;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicy;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicyType;
import es.udc.pa.pa009.elmocines.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.DuplicateInstanceException;

@AuthenticationPolicy(AuthenticationPolicyType.NON_AUTHENTICATED_USERS)
public class Register {
	
	private Long sessionId;
	@InjectPage
	private SessionDetails sessionDetails;

	@Property
	private String loginName;

	@Property
	private String password;

	@Property
	private String retypePassword;

	@Property
	private String firstName;

	@Property
	private String lastName;

	@Property
	private String email;

	@SessionState(create = false)
	private UserSession userSession;

	@Inject
	private UserService userService;

	@Component
	private Form registrationForm;

	@Component(id = "loginName")
	private TextField loginNameField;

	@Component(id = "password")
	private PasswordField passwordField;

	@Inject
	private Messages messages;

	private Long userProfileId;
	
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

	void onValidateFromRegistrationForm() {

		if (!registrationForm.isValid()) {
			return;
		}

		if (!password.equals(retypePassword)) {
			registrationForm.recordError(passwordField, messages.get("error-passwordsDontMatch"));
		} else {

			try {
				UserProfile userProfile = userService.registerUser(loginName, password,
						new UserProfileDetails(firstName, lastName, email), Role.CLIENT);
				userProfileId = userProfile.getUserProfileId();
			} catch (DuplicateInstanceException e) {
				registrationForm.recordError(loginNameField, messages.get("error-loginNameAlreadyExists"));
			}

		}

	}

	Object onSuccess() {

		userSession = new UserSession();
		userSession.setUserProfileId(userProfileId);
		userSession.setFirstName(firstName);
		userSession.setRole(Role.CLIENT);
		
		if (sessionId != null) {
			sessionDetails.setSessionId(sessionId);
			return sessionDetails;
		} else {
			return Index.class;
		}

	}

}
