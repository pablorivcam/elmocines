package es.udc.pa.pa009.elmocines.web.pages.purchase;

import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;

import es.udc.pa.pa009.elmocines.model.cinema.Cinema;
import es.udc.pa.pa009.elmocines.model.movie.Movie;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchaseservice.ExpiredDateException;
import es.udc.pa.pa009.elmocines.model.purchaseservice.PurchaseService;
import es.udc.pa.pa009.elmocines.model.purchaseservice.TicketsAlreadyCollectedException;
import es.udc.pa.pa009.elmocines.model.room.Room;
import es.udc.pa.pa009.elmocines.model.session.Session;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;

public class PurchaseDetails {

		private Long purchaseId;
		
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
		private Locale locale;
		
		@InjectPage
		private PurchaseDetails purchaseDetails;
		
		void onActivate(Long purchaseId) {
			this.purchaseId = purchaseId;
		}

		Object onPassivate() {
			return purchaseId;
		}

		Object onSuccessFromPurchaseIdBox(){
			purchaseDetails.setPurchaseId(purchaseId);
			return purchaseDetails;
		}
		
		void onPrepareForRender() {
			try {
				purchase = purchaseService.getPurchase(purchaseId);
				session = purchase.getSession();
				movie = session.getMovie();
				room = session.getRoom();
				cinema = room.getCinema();
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
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
			return (purchase.getPurchaseState().toString().equals("PENDING"));
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
