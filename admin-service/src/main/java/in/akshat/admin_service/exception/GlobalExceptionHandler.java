package in.akshat.admin_service.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

 
@ControllerAdvice
public class GlobalExceptionHandler {

    // --- Custom domain exceptions ---
    @ExceptionHandler(NoSuchEmpExistsException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchEmpExists(NoSuchEmpExistsException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(EmpAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleEmpAlreadyExists(EmpAlreadyExistsException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.CONFLICT;
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), ex.getMessage(), req.getRequestURI()));
    }

    // --- @Valid body validation ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.BAD_REQUEST; // ✅ 400 (not 502)
        BindingResult br = ex.getBindingResult();
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        List<FieldError> errors = br.getFieldErrors();
        for (FieldError e : errors) {
            fieldErrors.putIfAbsent(e.getField(), e.getDefaultMessage());
        }
        String msg = "Validation failed. Fix the field errors and try again.";
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), msg, req.getRequestURI(), fieldErrors));
    }

    // --- @Validated on path params / query params ---
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.BAD_REQUEST;
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
            String field = v.getPropertyPath() != null ? v.getPropertyPath().toString() : "parameter";
            fieldErrors.put(field, v.getMessage());
        }
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), "Constraint violation.", req.getRequestURI(), fieldErrors));
    }

    // --- JSON/type/format issues ---
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleNotReadable(HttpMessageNotReadableException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.BAD_REQUEST;
        String msg = "Malformed JSON or invalid value. Check types, enum values, and date format (yyyy-MM-dd).";
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), msg, req.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.BAD_REQUEST;
        String required = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "required type";
        String msg = "Parameter '" + ex.getName() + "' must be of type " + required + ".";
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), msg, req.getRequestURI()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParam(MissingServletRequestParameterException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.BAD_REQUEST;
        String msg = "Missing required request parameter: " + ex.getParameterName();
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), msg, req.getRequestURI()));
    }

    // --- HTTP protocol level ---
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.METHOD_NOT_ALLOWED;
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), ex.getMessage(), req.getRequestURI()));
    }

    // --- DB integrity (unique constraints, etc.) ---
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.CONFLICT;
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        String message = "Data integrity violation.";

        String rootMsg = deepestMessage(ex);
        if (rootMsg != null) {
            String lower = rootMsg.toLowerCase();
            if (lower.contains("uk_employees_email")) {
                message = "Email already exists.";
                fieldErrors.put("email", "Email must be unique");
            } else if (lower.contains("uk_employees_phone_no")) {
                message = "Phone number already exists.";
                fieldErrors.put("phoneNo", "Phone number must be unique");
            }
        }

        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), message, req.getRequestURI(),
                fieldErrors.isEmpty() ? null : fieldErrors));
    }

    // --- Fallback ---
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAny(Exception ex, HttpServletRequest req) {
        HttpStatus s = HttpStatus.INTERNAL_SERVER_ERROR;
        String msg = "Unexpected error. Please try again or contact support.";
        return ResponseEntity.status(s).body(ErrorResponse.of(s.value(), s.getReasonPhrase(), msg, req.getRequestURI()));
    }

    // --- Utils ---
    private String deepestMessage(Throwable t) {
        Throwable cur = t;
        Throwable next = cur.getCause();
        while (next != null && next != cur) {
            cur = next;
            next = cur.getCause();
        }
        return cur != null ? cur.getMessage() : null;
    }
}
