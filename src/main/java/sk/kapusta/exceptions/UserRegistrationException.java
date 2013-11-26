package sk.kapusta.exceptions;

public class UserRegistrationException extends Exception {

	private static final long serialVersionUID = 7545425699109293617L;

	public UserRegistrationException() {
		 
	 }
	
	 public UserRegistrationException(String message) {
	    super(message);
	 }
	
}
