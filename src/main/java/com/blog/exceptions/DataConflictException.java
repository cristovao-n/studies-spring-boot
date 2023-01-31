package com.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

public class DataConflictException extends BaseException {

    private Map<String, String> fieldErrors;

    public DataConflictException (Map<String, String> fieldErrors) {
        super(
                "Invalid field" + (fieldErrors.size() > 1 ? "s" : ""),
                "Please review the field" + (fieldErrors.size() > 1 ? "s" : "") + " and try again");
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}
