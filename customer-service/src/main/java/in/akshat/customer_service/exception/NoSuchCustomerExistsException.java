package in.akshat.customer_service.exception;

public class NoSuchCustomerExistsException extends RuntimeException {
	public NoSuchCustomerExistsException(String message) {
		super(message);
	}
}
