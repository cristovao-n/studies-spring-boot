package com.blog.exceptions.handlers;

import com.blog.payloads.ErrorPayload;
import com.blog.payloads.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class UnexpectedExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedException(Exception exception, WebRequest webRequest) {
        ErrorResponse errorResponse = this.makeExceptionErrorResponse(exception, webRequest);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponse makeExceptionErrorResponse(Exception exception, WebRequest webRequest) {
        ErrorPayload errorPayload = new ErrorPayload("An unexpected error occurred", exception.getMessage());
        return new ErrorResponse(errorPayload, new Date(), webRequest.getDescription(false), exception.getClass().getSimpleName());
    }
}
