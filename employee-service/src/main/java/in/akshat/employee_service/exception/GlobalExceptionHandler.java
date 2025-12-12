package in.akshat.employee_service.exception;

 
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------- Custom domain exceptions ----------
    @ExceptionHandler(NoSuchEmpExistsException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchEmpExists(NoSuchEmpExistsException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(EmpAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmpAlreadyExists(EmpAlreadyExistsException ex) {
        return build(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ---------- Bean Validation: @Valid on request body ----------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        BindingResult br = ex.getBindingResult();

        if (br.hasFieldErrors()) {
            // Build a simple "field: message; field2: message2" line
            StringBuilder sb = new StringBuilder("Validation failed -> ");
            boolean first = true;
            for (FieldError fe : br.getFieldErrors()) {
                if (!first) sb.append("; ");
                sb.append(fe.getField()).append(": ").append(fe.getDefaultMessage());
                first = false;
            }
            return build(status, sb.toString());
        }
        return build(status, "Validation failed. Fix the field errors and try again.");
    }

    // ---------- Bean Validation: @Validated on params/path vars ----------
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
            if (!first) sb.append("; ");
            String field = v.getPropertyPath() == null ? "parameter" : v.getPropertyPath().toString();
            sb.append(field).append(": ").append(v.getMessage());
            first = false;
        }
        String msg = sb.length() == 0 ? "Constraint violation." : sb.toString();
        return build(HttpStatus.BAD_REQUEST, msg);
    }

    // ---------- JSON / type / format issues ----------
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST,
                "Malformed JSON or invalid value. Check types, enum values, and date format (yyyy-MM-dd).");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String required = ex.getRequiredType() == null ? "required type" : ex.getRequiredType().getSimpleName();
        return build(HttpStatus.BAD_REQUEST, "Parameter '" + ex.getName() + "' must be of type " + required + ".");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex) {
        return build(HttpStatus.BAD_REQUEST, "Missing required request parameter: " + ex.getParameterName());
    }

    // ---------- HTTP protocol level ----------
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return build(HttpStatus.METHOD_NOT_ALLOWED, ex.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
    }

    // ---------- DB integrity (unique constraints, etc.) ----------
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex) {
        String msg = "Data integrity violation.";

        // Try to give a friendly message for common unique keys
        String root = deepestMessage(ex);
        if (root != null) {
            String lower = root.toLowerCase();
            if (lower.contains("uk_employees_email") || (lower.contains("unique") && lower.contains("email"))) {
                msg = "Email already exists.";
            } else if (lower.contains("uk_employees_phone_no") || (lower.contains("unique") && lower.contains("phone"))) {
                msg = "Phone number already exists.";
            }
        }
        return build(HttpStatus.CONFLICT, msg); // 409
    }

    // ---------- Fallback ----------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAny(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error. Please try again or contact support.");
    }

    // ---------- Helpers ----------
    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ErrorResponse(status.value(), message));
    }

    private String deepestMessage(Throwable t) {
        String last = null;
        Throwable cur = t;
        while (cur != null) {
            if (cur.getMessage() != null) last = cur.getMessage();
            cur = cur.getCause();
        }
        return last;
    }
}
