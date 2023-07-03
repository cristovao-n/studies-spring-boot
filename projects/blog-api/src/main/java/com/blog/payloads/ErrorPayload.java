package com.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ErrorPayload {
    private String title;
    private String description;

}
