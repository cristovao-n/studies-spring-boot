package com.blog.exceptions;

import com.blog.payloads.ErrorPayload;
import com.blog.payloads.ErrorResponse;
import com.blog.payloads.ValidationErrorPayload;
import com.blog.payloads.ValidationErrorResponse;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

@ControllerAdvice
public class GlobalExceptionHandler {
    // HANDLED EXCEPTIONS

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = this.makeErrorResponse(exception, webRequest);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorResponse> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = this.makeErrorResponse(exception, webRequest);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException exception, WebRequest webRequest) {
        ErrorPayload errorPayload = new ErrorPayload("Authentication error", exception.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(errorPayload, new Date(), webRequest.getDescription(false), exception.getClass().getSimpleName());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    // HANDLED VALIDATION EXCEPTIONS

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        Map<String, String> fieldErrors = this.makeFieldErrorsMap(exception);
        ValidationErrorResponse validationErrorResponse = this.makeSpringValidationErrorResponse(fieldErrors, webRequest, exception);

        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataConflictException.class)
    public ResponseEntity<ValidationErrorResponse> handleDataConflictException(DataConflictException exception, WebRequest webRequest) {
        ValidationErrorResponse validationErrorResponse = this.makeValidationErrorResponse(exception.getFieldErrors(), webRequest, exception);

        return new ResponseEntity<>(validationErrorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    // UNHANDLED EXCEPTIONS
    // COMMENTED TO PRESERVE STACK TRACE
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Exception> handleUnexpectedException(Exception exception, WebRequest webRequest) {
//        ErrorPayload errorPayload = new ErrorPayload("An unexpected error occurred", exception.getMessage());
//        ErrorResponse errorResponse = new ErrorResponse(errorPayload, new Date(), webRequest.getDescription(false), exception.getClass().getSimpleName());
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    private ErrorResponse makeErrorResponse(BaseException exception, WebRequest webRequest) {
        ErrorPayload errorPayload = new ErrorPayload(exception.getTitle(), exception.getDescription());
        return new ErrorResponse(errorPayload, new Date(), webRequest.getDescription(false), exception.getClass().getSimpleName());
    }

    private Map<String, String> makeFieldErrorsMap(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach((fieldError) -> {
            String fieldName = ((FieldError) fieldError).getField();
            String fieldErrorMessage = fieldError.getDefaultMessage();
            fieldErrors.put(fieldName, fieldErrorMessage);
        });
        return fieldErrors;
    }

    private ValidationErrorResponse makeValidationErrorResponse(Map<String, String> fieldErrors, WebRequest webRequest, BaseException exception) {
        ValidationErrorPayload validationErrorPayload = new ValidationErrorPayload(exception.getTitle(), exception.getDescription(), fieldErrors);
        String endpoint = webRequest.getDescription(false);
        return new ValidationErrorResponse(validationErrorPayload, new Date(), endpoint, exception.getClass().getSimpleName());
    }

    private ValidationErrorResponse makeSpringValidationErrorResponse(Map<String, String> fieldErrors, WebRequest webRequest, Exception exception) {
        boolean hasMoreThanOneFieldError = fieldErrors.size() > 1;
        String title = "Invalid field" + (hasMoreThanOneFieldError ? "s" : "");
        String description = "Please review the field" + (hasMoreThanOneFieldError ? "s" : "") + " and try again";
        ValidationErrorPayload validationErrorPayload = new ValidationErrorPayload(title, description, fieldErrors);

        String endpoint = webRequest.getDescription(false);
        return new ValidationErrorResponse(validationErrorPayload, new Date(), endpoint, exception.getClass().getSimpleName());
    }

}
