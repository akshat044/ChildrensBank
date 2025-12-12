package in.akshat.branch_service.exception;

public class BranchAlreadyExistsException extends RuntimeException{
	
	public BranchAlreadyExistsException(String message) {
		super(message);
	}

}

