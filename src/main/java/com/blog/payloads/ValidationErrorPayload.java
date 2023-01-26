package com.blog.payloads;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationErrorPayload extends ErrorPayload {
    private Map<String, String> fieldErrors;

    public ValidationErrorPayload(String title, String description, Map<String, String> fieldErrors) {
        super(title, description);
        this.fieldErrors = fieldErrors;
    }
}
