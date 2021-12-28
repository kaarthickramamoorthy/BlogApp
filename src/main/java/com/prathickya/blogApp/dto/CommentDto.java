package com.prathickya.blogApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;

    @NotEmpty(message = "Name should not be empty or null")
    private String name;

    @NotEmpty(message = "Email should not be empty or null")
    @Email
    private String email;

    @NotEmpty
    private String body;
}
