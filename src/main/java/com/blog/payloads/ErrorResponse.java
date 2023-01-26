package com.blog.payloads;

import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
public class ErrorResponse {
    private Date timestamp;
    private String endpoint;
    private String exception;
    private ErrorPayload error;

    public ErrorResponse(ErrorPayload error, Date timestamp, String endpoint, String exception) {
        this.timestamp = timestamp;
        this.endpoint = endpoint;
        this.exception = exception;
        this.error = error;
    }

}
