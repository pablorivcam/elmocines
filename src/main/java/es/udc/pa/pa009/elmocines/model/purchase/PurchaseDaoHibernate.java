package es.udc.pa.pa009.elmocines.model.purchase;

import org.springframework.stereotype.Repository;

import es.udc.pojo.modelutil.dao.GenericDaoHibernate;

@Repository("purchaseDao")
public class PurchaseDaoHibernate extends GenericDaoHibernate<Purchase, Long> implements PurchaseDao {

}
