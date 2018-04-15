package es.udc.pa.pa009.elmocines.model.purchase;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface PurchaseDao extends GenericDao<Purchase, Long> {

	/**
	 * Find purchases by user id.
	 *
	 * @param userId
	 *            the user id
	 * @param startIndex
	 *            the start index
	 * @param count
	 *            the count
	 * @return the list
	 */
	public List<Purchase> findPurchasesByUserId(Long userId, int startIndex, int count);

}
