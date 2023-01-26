package com.blog.payloads;

import lombok.Getter;

import java.util.Date;
import java.util.Map;

@Getter
public class ErrorResponse {
    private Date timestamp;
    private String endpoint;
    private ErrorPayload error;

    public ErrorResponse(ErrorPayload error, Date timestamp, String endpoint) {
        this.timestamp = timestamp;
        this.endpoint = endpoint;
        this.error = error;
    }

}
