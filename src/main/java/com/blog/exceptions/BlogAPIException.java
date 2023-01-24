package com.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
 We throw this exception whenever we write some business logic or validate request parameters
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BlogAPIException extends RuntimeException {
    private HttpStatus httpStatus;

    public BlogAPIException (String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus () {
        return this.httpStatus;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
