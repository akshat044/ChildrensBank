package in.akshat.employee_service.exception;

public class NoSuchEmpExistsException extends RuntimeException {
	public NoSuchEmpExistsException(String message) {
		super(message);
	}
}
