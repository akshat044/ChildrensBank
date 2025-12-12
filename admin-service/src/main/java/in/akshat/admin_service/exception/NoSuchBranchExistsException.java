package in.akshat.admin_service.exception;

public class NoSuchBranchExistsException extends RuntimeException{
	public NoSuchBranchExistsException(String message) {
		super(message);
	}
}
