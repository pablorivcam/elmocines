package es.udc.pa.pa009.elmocines.model.purchaseservice;

public class TicketsAlreadyCollectedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TicketsAlreadyCollectedException() {
		super("This tickets are already collected.");
	}

}
