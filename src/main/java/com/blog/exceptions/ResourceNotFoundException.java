package com.blog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;
    private long id;

    public ResourceNotFoundException (String resourceName, long id) {
        super(String.format("%s not found with id : '%s'", resourceName, id));
        this.resourceName = resourceName;
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public long getId() {
        return id;
    }

}
