package com.blog.payloads;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 2, max = 256)
    private String title;

    @NotNull
    @NotEmpty
    @NotBlank
    @Size(min = 4, max = 256)
    private String description;
    @NotBlank()
    @Size(min = 4, max = 256, message = "Post content must have between {min} and {max} characters.")
    private String content;
}
