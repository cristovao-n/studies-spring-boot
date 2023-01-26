package com.blog.payloads;

import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
public class ValidationErrorResponse {
    private Date timestamp;
    private String endpoint;
    private String exception;
    private ValidationErrorPayload error;

    public ValidationErrorResponse(ValidationErrorPayload error, Date timestamp, String endpoint, String exception) {
        this.timestamp = timestamp;
        this.endpoint = endpoint;
        this.exception = exception;
        this.error = error;
    }

}
