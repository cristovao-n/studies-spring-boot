package com.blog.payloads;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDTO {
    private Long id;
    @NotNull
    @Min(2)
    private String title;
    @NotNull
    @Min(4)
    private String description;
    @NotBlank()
    @Size(min = 4, max = 256, message = "Post title must have between {min} and {max} characters.")
    private String content;
}
