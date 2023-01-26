package com.blog.payloads;

import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
public class ValidationErrorResponse {
    private Date timestamp;
    private String endpoint;
    private ValidationErrorPayload error;

    public ValidationErrorResponse(ValidationErrorPayload error, Date timestamp, String endpoint) {
        this.timestamp = timestamp;
        this.endpoint = endpoint;
        this.error = error;
    }

}
