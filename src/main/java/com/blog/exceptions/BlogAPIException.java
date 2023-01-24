package com.blog.exceptions;

import org.springframework.http.HttpStatus;

/*
 We throw this exception whenever we write some business logic or validate request parameters
 */
public class BlogAPIException extends BaseException {

    public BlogAPIException (String title, String description) {
        super(title, description);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
