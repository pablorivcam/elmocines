package es.udc.pa.pa009.elmocines.web.pages.purchase;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.TextField;
import org.apache.tapestry5.ioc.Messages;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase.PurchaseState;
import es.udc.pa.pa009.elmocines.model.purchaseservice.ExpiredDateException;
import es.udc.pa.pa009.elmocines.model.purchaseservice.PurchaseService;
import es.udc.pa.pa009.elmocines.model.purchaseservice.TicketsAlreadyCollectedException;
import es.udc.pa.pa009.elmocines.model.room.Room;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicy;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicyType;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

@AuthenticationPolicy(AuthenticationPolicyType.WORKER_USERS)
public class PurchaseDetails {

		private Long purchaseId;
		
		private Purchase purchaseSearch;

		@Property
		private Long searchPurchaseId;
		
		@Property
		private Purchase purchase;
		
		@Property
		private Session session;

		@Property
		private Movie movie;
		
		@Property
		private Room room;
		
		@Property
		private Cinema cinema;
		
		@Inject
		private PurchaseService purchaseService;

		@Inject
		private Messages messages;
		
		@Component(id="searchPurchaseId")
		private TextField purchaseIdField;
		
		@Component
		private Form searchForm;
		
		@Inject
		private Locale locale;
		
		@InjectPage
		private PurchaseDetails purchaseDetails;
		
		void onActivate(Long purchaseId) {
			this.purchaseId = purchaseId;
			try {
				purchase = purchaseService.getPurchase(purchaseId);
				session = purchase.getSession();
				movie = session.getMovie();
				room = session.getRoom();
				cinema = room.getCinema();
			} catch (InstanceNotFoundException e) {
			}
		}

		Object onPassivate() {
			return purchaseId;
		}

		Object onSuccess(){
			purchaseDetails.setPurchaseId(purchaseSearch.getPurchaseId());
			return purchaseDetails;
		}
		
		void onValidateFromSearchForm(){
			if (!searchForm.isValid()){
				return;
			}
			
			try {
				purchaseSearch = purchaseService.getPurchase(searchPurchaseId);
			} catch (InstanceNotFoundException e){
				searchForm.recordError(purchaseIdField, messages.get("error-invalidPurchaseId"));				
			}
			
		}
		
		public void onDeliverTickets(Long purchaseId){
			try {
				purchaseService.collectTickets(purchaseId);
			} catch (InstanceNotFoundException | TicketsAlreadyCollectedException | ExpiredDateException e) {
				e.printStackTrace();
			}
		}
		
		public boolean isPurchaseDelivered(){
			return (purchase.getPurchaseState().equals(PurchaseState.PENDING));
		}
		
		public Long getPurchaseId() {
			return purchaseId;
		}

		public void setPurchaseId(Long purchaseId) {
			this.purchaseId = purchaseId;
		}
		
		public Format getFormat() {
			return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
		}

}
