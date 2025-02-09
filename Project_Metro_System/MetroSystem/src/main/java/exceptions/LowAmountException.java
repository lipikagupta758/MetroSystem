package exceptions;

public class LowAmountException extends Exception{

	public LowAmountException() {
		super();
	}
	public LowAmountException(String message) {
		super(message);
	}
}
