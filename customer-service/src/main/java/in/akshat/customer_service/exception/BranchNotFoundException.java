package in.akshat.customer_service.exception;

public class BranchNotFoundException extends RuntimeException {
	public BranchNotFoundException(String message) {
		super(message);
	}
}
