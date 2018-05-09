package es.udc.pa.pa009.elmocines.web.util;

import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile.Role;

public class UserSession {

	private Long userProfileId;
	private String firstName;
	private Role role;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

}
