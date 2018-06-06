package es.udc.pa.pa009.elmocines.model.purchase;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.model.userprofile.UserProfile;

// TODO: Auto-generated Javadoc
/**
 * La clase Purchase para modelar una compra.
 */
@Entity
@Table(name = "Purchases")
public class Purchase {

	/**
	 * The Enum PurchaseState.
	 */
	public static enum PurchaseState {

		/** The pending. */
		PENDING,
		/** The delivered. */
		DELIVERED
	};

	/** The purchase id. */
	private Long purchaseId;

	/** The credit card number. */
	private String creditCardNumber;

	/** The location count. */
	private Integer locationCount;

	/** The purchase state. */
	private PurchaseState purchaseState;

	/** The credit card expiration date. */
	private Calendar creditCardExpirationDate;

	/** The date. */
	private Calendar date;

	/** The session. */
	private Session session;

	private UserProfile user;

	/**
	 * Constructor sin parámetros necesario para hibernate.
	 */
	public Purchase() {

	}

	/**
	 * Instantiates a new purchase.
	 *
	 * @param creditCardNumber
	 *            the credit card number
	 * @param creditCardExpirationDate
	 *            the credit card expiration date
	 * @param locationCount
	 *            the location count
	 * @param date
	 *            the date
	 * @param session
	 *            the session
	 */
	public Purchase(String creditCardNumber, Calendar creditCardExpirationDate, Integer locationCount, Calendar date,
			Session session, UserProfile user) {
		this.creditCardNumber = creditCardNumber;
		this.creditCardExpirationDate = creditCardExpirationDate;
		this.locationCount = locationCount;
		this.purchaseState = PurchaseState.PENDING;
		this.date = date;
		this.session = session;
		this.user = user;

	}

	/**
	 * Instantiates a new purchase.
	 *
	 * @param creditCardNumber
	 *            the credit card number
	 * @param creditCardExpirationDate
	 *            the credit card expiration date
	 * @param locationCount
	 *            the location count
	 * @param purchaseState
	 *            the purchase state
	 * @param date
	 *            the date
	 * @param session
	 *            the session
	 */
	public Purchase(String creditCardNumber, Calendar creditCardExpirationDate, Integer locationCount,
			PurchaseState purchaseState, Calendar date, Session session, UserProfile user) {
		this.creditCardNumber = creditCardNumber;
		this.creditCardExpirationDate = creditCardExpirationDate;
		this.locationCount = locationCount;
		this.purchaseState = purchaseState;
		this.date = date;
		this.session = session;
		this.user = user;
	}

	/**
	 * Gets the purchase id.
	 *
	 * @return the purchase id
	 */
	@SequenceGenerator(name = "PurchaseIdGenerator", sequenceName = "purchaseSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PurchaseIdGenerator")
	public Long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	/**
	 * Gets the credit card number.
	 *
	 * @return the credit card number
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * Sets the credit card number.
	 *
	 * @param creditCardNumber
	 *            the new credit card number
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * Gets the credit card expiration date.
	 *
	 * @return the credit card expiration date
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "creditCardExpiration")
	public Calendar getCreditCardExpirationDate() {
		return creditCardExpirationDate;
	}

	/**
	 * Sets the credit card expiration date.
	 *
	 * @param creditCardExpirationDate
	 *            the new credit card expiration date
	 */
	public void setCreditCardExpirationDate(Calendar creditCardExpirationDate) {
		this.creditCardExpirationDate = creditCardExpirationDate;
	}

	/**
	 * Gets the location count.
	 *
	 * @return the location count
	 */
	public Integer getLocationCount() {
		return locationCount;
	}

	/**
	 * Sets the location count.
	 *
	 * @param locationCount
	 *            the new location count
	 */
	public void setLocationCount(Integer locationCount) {
		this.locationCount = locationCount;
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date
	 *            the new date
	 */
	public void setDate(Calendar date) {
		this.date = date;
		date.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "sessionId")
	public Session getSession() {
		return session;
	}

	/**
	 * Sets the session.
	 *
	 * @param session
	 *            the new session
	 */
	public void setSession(Session session) {
		this.session = session;
	}

	@Enumerated(EnumType.STRING)
	public PurchaseState getPurchaseState() {
		return purchaseState;
	}

	public void setPurchaseState(PurchaseState purchaseState) {
		this.purchaseState = purchaseState;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "usrId")
	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

}
