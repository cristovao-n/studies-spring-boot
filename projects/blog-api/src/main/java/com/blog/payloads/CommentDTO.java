package com.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDTO {
    private long id;

    @NotBlank
    @Size(min = 2, max = 256)
    private String name;

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 4, max = 256)
    private String body;
}
