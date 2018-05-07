/**
 * 
 */
package es.udc.pa.pa009.elmocines.web.pages.purchase;


import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import java.util.List;
import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;
import es.udc.pa.pa009.elmocines.model.userservice.UserService;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchaseservice.PurchaseService;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicy;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicyType;
import es.udc.pa.pa009.elmocines.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pojo.modelutil.data.Block;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class UserPurchases {
	
	private final static int PURCHASES_PER_PAGE = 10;
	private Long userId;
	private int startIndex = 0;
	private Purchase purchase;
	private Block<Purchase> purchaseBlock;
	
	@Inject
	private Locale locale;
	
	@SessionState(create=false)
    private UserSession userSession;

    @Inject
    private UserService userService;
    
    @Inject
    private PurchaseService purchaseService;
    
    public List<Purchase> getPurchases() {
		return purchaseBlock.getItems();
	}
	
	public Purchase getPurchase() {
		return purchase;
	}

	public void setPurchase(Purchase purchase) {
		this.purchase = purchase;
	}
	
	public Format getFormat() {
		return DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
	}
    
    public Object[] getPreviousLinkContext() {
		
		if (startIndex-PURCHASES_PER_PAGE >= 0) {
			return new Object[] {userId, startIndex-PURCHASES_PER_PAGE};
		} else {
			return null;
		}
		
	}
	
	public Object[] getNextLinkContext() {
		
		if (purchaseBlock.getExistMoreItems()) {
			return new Object[] {userId, startIndex+PURCHASES_PER_PAGE};
		} else {
			return null;
		}
		
	}
    
    public Object[] onPassivate() {
		return new Object[] {userSession.getUserProfileId(), startIndex};
	}
	
	void onActivate(Long userId, int startIndex) {
		this.userId = userId;
		this.startIndex = startIndex;
		 try {
				purchaseBlock = purchaseService.getPurchases(userId,startIndex,PURCHASES_PER_PAGE);
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (InputValidationException e) {
				e.printStackTrace();
			}
	}
    

}