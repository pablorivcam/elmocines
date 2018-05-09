package es.udc.pa.pa009.elmocines.model.purchase;

import java.util.List;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("purchaseDao")
public class PurchaseDaoHibernate extends GenericDaoHibernate<Purchase, Long> implements PurchaseDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Purchase> findPurchasesByUserId(Long userId, int startIndex, int count) {

		List<Purchase> purchases = null;

		purchases = getSession()
				.createQuery("SELECT p FROM Purchase p WHERE p.user.userProfileId = :userId ORDER BY p.purchaseId")
				.setParameter("userId", userId).setFirstResult(startIndex).setMaxResults(count).getResultList();

		return purchases;
	}
}
