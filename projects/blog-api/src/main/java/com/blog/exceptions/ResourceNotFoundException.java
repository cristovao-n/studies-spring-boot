package com.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException (Class resourceClass, long id) {
        super("Resource not found", String.format("%s not found with id : '%s'", resourceClass.getSimpleName(), id));
    }

}
