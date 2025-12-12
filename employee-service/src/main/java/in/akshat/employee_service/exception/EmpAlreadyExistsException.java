package in.akshat.employee_service.exception;

public class EmpAlreadyExistsException  extends RuntimeException{
	public EmpAlreadyExistsException(String message) {
		super(message);
	}
}
