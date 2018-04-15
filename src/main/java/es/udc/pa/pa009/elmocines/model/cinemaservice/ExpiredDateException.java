package es.udc.pa.pa009.elmocines.model.cinemaservice;

public class ExpiredDateException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ExpiredDateException() {
		super("This tickets have already expired.");
	}
}
