package in.akshat.branch_service.exception;

public class NoSuchBranchExistsException extends RuntimeException{
	public NoSuchBranchExistsException(String message) {
		super(message);
	}
}
