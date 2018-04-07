package es.udc.pa.pa009.elmocines.model.cinemaservice;

public class InputValidationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InputValidationException() {
		super("Some input values are not correct.");
	}

}
