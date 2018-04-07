package es.udc.pa.pa009.elmocines.model.purchase;

import java.math.BigDecimal;
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
	@SequenceGenerator(name = "PurchaseIdGenerator", sequenceName = "purchaseSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "PurchaseIdGenerator")
	private Long purchaseId;

	/** The credit card number. */
	private BigDecimal creditCardNumber;

	/** The location count. */
	private Integer locationCount;

	/** The purchase state. */
	@Enumerated(EnumType.STRING)
	private PurchaseState purchaseState;

	/** The credit card expiration date. */
	@Temporal(TemporalType.DATE)
	@Column(name = "creditCardExpiration")
	private Calendar creditCardExpirationDate;

	/** The date. */
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;

	/** The session. */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "sessionId")
	private Session session;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "usrId")
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
	 * @param purchaseState
	 *            the purchase state
	 * @param date
	 *            the date
	 * @param session
	 *            the session
	 */
	public Purchase(BigDecimal creditCardNumber, Calendar creditCardExpirationDate, Integer locationCount,
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
	public Long getPurchaseId() {
		return purchaseId;
	}

	/**
	 * Gets the credit card number.
	 *
	 * @return the credit card number
	 */
	public BigDecimal getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * Sets the credit card number.
	 *
	 * @param creditCardNumber
	 *            the new credit card number
	 */
	public void setCreditCardNumber(BigDecimal creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * Gets the credit card expiration date.
	 *
	 * @return the credit card expiration date
	 */
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
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
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

	public PurchaseState getPurchaseState() {
		return purchaseState;
	}

	public void setPurchaseState(PurchaseState purchaseState) {
		this.purchaseState = purchaseState;
	}

	public UserProfile getUser() {
		return user;
	}

	public void setUser(UserProfile user) {
		this.user = user;
	}

}
