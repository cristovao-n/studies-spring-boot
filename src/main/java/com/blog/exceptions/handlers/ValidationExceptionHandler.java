package com.blog.exceptions.handlers;

import com.blog.payloads.ValidationErrorPayload;
import com.blog.payloads.ValidationErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest webRequest) {
        Map<String, String> fieldErrors = this.makeFieldErrorsMap(exception);
        ValidationErrorResponse validationErrorResponse = this.makeValidationErrorResponse(fieldErrors, webRequest, exception);

        return new ResponseEntity<>(validationErrorResponse, HttpStatus.BAD_REQUEST);
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

    private ValidationErrorResponse makeValidationErrorResponse(Map<String, String> fieldErrors, WebRequest webRequest, MethodArgumentNotValidException exception) {
        boolean hasMoreThanOneFieldError = fieldErrors.size() > 1;
        String title = "Invalid field" + (hasMoreThanOneFieldError ? "s" : "");
        String description = "Please review the field" + (hasMoreThanOneFieldError ? "s" : "") + " and try again";
        ValidationErrorPayload validationErrorPayload = new ValidationErrorPayload(title, description, fieldErrors);

        String endpoint = webRequest.getDescription(false);
        return new ValidationErrorResponse(validationErrorPayload, new Date(), endpoint, exception.getClass().getSimpleName());
    }
}
