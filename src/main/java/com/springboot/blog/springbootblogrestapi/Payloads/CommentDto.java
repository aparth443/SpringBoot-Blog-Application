package com.springboot.blog.springbootblogrestapi.Payloads;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;

    @NotEmpty(message = "Name should not be null")
    private String name;

    @NotEmpty(message = "Email should not be null")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10,message = "Body should be at least 10 characters")
    private String body;
}
