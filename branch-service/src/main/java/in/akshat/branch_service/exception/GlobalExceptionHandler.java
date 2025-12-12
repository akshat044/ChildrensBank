package in.akshat.branch_service.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

 

 

@ControllerAdvice
public class GlobalExceptionHandler {
	
	  @ExceptionHandler(value =NoSuchBranchExistsException.class)
	    public ResponseEntity<ErrorResponse> handleNoSuchBranchExistsException(NoSuchBranchExistsException ex) {
	        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	    }

    @ExceptionHandler(value =BranchAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleBranchAlreadyExistsException(BranchAlreadyExistsException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value =MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidExcetion(MethodArgumentNotValidException ex){
    	Map<String,String> map = new HashMap<>();
    	BindingResult bindingResult = ex.getBindingResult();
    	List<FieldError> errorList = bindingResult.getFieldErrors();
    	
    	for(FieldError error : errorList) {
    		map.put(error.getField(), error.getDefaultMessage());
    	}
    	return new ResponseEntity<>(map,HttpStatus.BAD_GATEWAY);
    }
    @ExceptionHandler(value =Exception.class)
    public ResponseEntity<ErrorResponse> handleLeftException(Exception ex){
    	ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage());
    	return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
