package es.udc.pa.pa009.elmocines.web.pages.purchase;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchaseservice.PurchaseService;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class PurchaseSearch {

		private Purchase purchase;
	
		@Property
		private Long purchaseId;
		
		@Inject
		private PurchaseService purchaseService;
		
		@Inject
		private Messages messages;
		
		@Component(id="purchaseId")
		private TextField purchaseIdField;
		
		@Component
		private Form searchForm;
		
		@InjectPage
		private PurchaseDetails purchaseDetails;

		Object onSuccess(){
			purchaseDetails.setPurchaseId(purchase.getPurchaseId());
			return purchaseDetails;
		}
		
		void onValidateFromSearchForm(){
			if (!searchForm.isValid()){
				return;
			}
			
			try {
				purchase = purchaseService.getPurchase(purchaseId);
			} catch (InstanceNotFoundException e){
				searchForm.recordError(purchaseIdField, messages.get("error-invalidPurchaseId"));				
			}
			
		}
}
