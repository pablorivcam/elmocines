/**
 * 
 */
package es.udc.pa.pa009.elmocines.web.pages.purchase;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchaseservice.PurchaseService;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicy;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicyType;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.CLIENT_USERS)
public class SuccessfulPurchase {
	
	private Long purchaseId;
	
	@Property
	private Purchase purchase;

	@Inject
	private PurchaseService purchaseService;
	
	public Long getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(Long purchaseId) {
		this.purchaseId = purchaseId;
	}
	
	void onActivate(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	Object onPassivate() {
		return purchaseId;
	}
	
	void onPrepareForRender() {
		try {
			purchase = purchaseService.getPurchase(purchaseId);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}