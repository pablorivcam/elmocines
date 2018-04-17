package es.udc.pa.pa009.elmocines.model.purchaseservice;

import java.util.Calendar;

import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pojo.modelutil.data.Block;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

/**
 * 
 * The Interface PurchaseService
 *
 */
public interface PurchaseService {

	/**
	 * Purchase tickets.
	 *
	 * @param userId
	 *            the user id
	 * @param creditCardNumber
	 *            the credit card number
	 * @param creditCardExpirationDate
	 *            the credit card expiration date
	 * @param sessionId
	 *            the session id
	 * @param locationsAmount
	 *            the locations amount
	 * @return the purchase
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @throws InputValidationException
	 *             the input validation exception
	 * @throws TooManyLocationsException
	 *             the too many locations exception
	 */
	public Purchase purchaseTickets(Long userId, String creditCardNumber, Calendar creditCardExpirationDate,
			Long sessionId, int locationsAmount)
			throws InstanceNotFoundException, InputValidationException, TooManyLocationsException,ExpiredDateException;

	/**
	 * Gets the purchases.
	 *
	 * @param userId
	 *            the user id
	 * @param startIndex
	 *            the start index of the purchases that we want to get.
	 * @param count
	 *            the number of purchases that we want to get.
	 * @throws InputValidationException
	 *             the input validation exception
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @return the purchases
	 */
	public Block<Purchase> getPurchases(Long userId, int startIndex, int count)
			throws InputValidationException, InstanceNotFoundException;

	/**
	 * Gets the purchase.
	 *
	 * @param purchaseId
	 *            the purchase id
	 * @return the purchase
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 */
	public Purchase getPurchase(Long purchaseId) throws InstanceNotFoundException;

	/**
	 * Collect tickets.
	 *
	 * @param purchaseId
	 *            the purchase id
	 * @return the purchase
	 * @throws InstanceNotFoundException
	 *             the instance not found exception
	 * @throws TicketsAlreadyCollectedException
	 *             the tickets already collected exception
	 * @throws ExpiredDateException 
	 */
	public Purchase collectTickets(Long purchaseId) throws InstanceNotFoundException, TicketsAlreadyCollectedException, ExpiredDateException;	
}
