package com.blog.exceptions;

import lombok.Getter;
import lombok.ToString;

import java.util.Date;

@Getter
@ToString
public class BaseException extends RuntimeException {
    private String title;
    private String description;

    public BaseException(String title, String description) {
        super(description);
        this.title = title;
        this.description = description;
    }

}
