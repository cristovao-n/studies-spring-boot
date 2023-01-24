package com.blog.payloads;

import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorDetails {
    private Date timestamp;
    private String title;
    private String description;
    private String endpoint;

    public ErrorDetails(Date timestamp, String title, String description, String endpoint) {
        this.timestamp = timestamp;
        this.title = title;
        this.description = description;
        this.endpoint = endpoint;
    }


}
