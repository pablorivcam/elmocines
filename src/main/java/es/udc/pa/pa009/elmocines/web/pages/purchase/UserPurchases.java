/**
 * 
 */
package es.udc.pa.pa009.elmocines.web.pages.purchase;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;


import es.udc.pa.pa009.elmocines.model.userservice.UserService;
import es.udc.pa.pa009.elmocines.model.purchase.Purchase;
import es.udc.pa.pa009.elmocines.model.purchaseservice.PurchaseService;
import es.udc.pa.pa009.elmocines.web.pages.Index;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicy;
import es.udc.pa.pa009.elmocines.web.services.AuthenticationPolicyType;
import es.udc.pa.pa009.elmocines.web.util.UserSession;
import es.udc.pojo.modelutil.exceptions.InstanceNotFoundException;
import es.udc.pa.pa009.elmocines.model.cinemaservice.InputValidationException;
import es.udc.pojo.modelutil.data.Block;

@AuthenticationPolicy(AuthenticationPolicyType.AUTHENTICATED_USERS)
public class UserPurchases {
	
	private final static int PURCHASES_PER_PAGE = 20;
	private int startIndex = 0;
	
	@Property
	private Block<Purchase> purchases;
	
	@SessionState(create=false)
    private UserSession userSession;

    @Inject
    private UserService userService;
    
    @Inject
    private PurchaseService purchaseService;
    
    public Object[] getPreviousLinkContext() {
		
		if (startIndex-PURCHASES_PER_PAGE >= 0) {
			return new Object[] {userSession.getUserProfileId(), startIndex-PURCHASES_PER_PAGE};
		} else {
			return null;
		}
		
	}
	
	public Object[] getNextLinkContext() {
		
		if (purchases.getExistMoreItems()) {
			return new Object[] {userSession.getUserProfileId(), startIndex+PURCHASES_PER_PAGE};
		} else {
			return null;
		}
		
	}
    
    void onPrepareForRender(){
        
        try {
			purchases = purchaseService.getPurchases(userSession.getUserProfileId(),startIndex,PURCHASES_PER_PAGE);
		} catch (InstanceNotFoundException e) {
			e.printStackTrace();
		} catch (InputValidationException e) {
			e.printStackTrace();
		}

    }
    
    public Object[] onPassivate() {
		return new Object[] {userSession.getUserProfileId(), startIndex};
	}
	
	void onActivate(Long userId, int startIndex) {
		this.startIndex = startIndex;
		 try {
				purchases = purchaseService.getPurchases(userId,startIndex,PURCHASES_PER_PAGE);
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			} catch (InputValidationException e) {
				e.printStackTrace();
			}
	}
    

}