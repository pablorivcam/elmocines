package es.udc.pa.pa009.elmocines.model.purchase;

import java.util.List;

import es.udc.pojo.modelutil.dao.GenericDao;

public interface PurchaseDao extends GenericDao<Purchase, Long> {
	
	public List<Purchase> findPurchasesByUserId(Long userId);

}
