package com.blog.exceptions.handlers;

import com.blog.exceptions.BaseException;
import com.blog.exceptions.BlogAPIException;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.ErrorPayload;
import com.blog.payloads.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = this.makeBaseExceptionErrorResponse(exception, webRequest);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BlogAPIException.class)
    public ResponseEntity<ErrorResponse> handleBlogAPIException(BlogAPIException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = this.makeBaseExceptionErrorResponse(exception, webRequest);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse makeBaseExceptionErrorResponse(BaseException exception, WebRequest webRequest) {
        ErrorPayload errorPayload = new ErrorPayload(exception.getTitle(), exception.getDescription());
        return new ErrorResponse(errorPayload, new Date(), webRequest.getDescription(false), exception.getClass().getSimpleName());
    }

}
