package es.udc.pa.pa009.elmocines.model.userprofile;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@Repository("userProfileDao")
public class UserProfileDaoHibernate extends
		GenericDaoHibernate<UserProfile, Long> implements UserProfileDao {

	public UserProfile findByLoginName(String loginName) throws InstanceNotFoundException {

    	UserProfile userProfile = null;
		try {
            userProfile = (UserProfile) getSession().createQuery(
    			"SELECT u FROM UserProfile u WHERE u.loginName = :loginName")
    			.setParameter("loginName", loginName)
    			.getSingleResult();
		} catch (NoResultException nre) {
			throw new InstanceNotFoundException(loginName, UserProfile.class.getName());
		}
   		return userProfile;
	}

}