package es.udc.pa.pa009.elmocines.model.cinemaservice;

public class TooManyLocationsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TooManyLocationsException(int freeLocations) {
		super("The amount of free locations of this session is " + freeLocations + "");
	}

}
