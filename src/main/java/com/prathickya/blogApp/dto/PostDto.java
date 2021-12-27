package com.prathickya.blogApp.dto;

import com.prathickya.blogApp.entity.Comment;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

//DTO - Data Transfer Object
//Frequently used Design pattern to pass the data (multiple parameters) between Client Front-End/Server Back-End
//DTO helps to hide implementation details of JPA Entities to client
//Client sends the JSON to Server in RequestBody - Spring boot converts JSON(@RequestBody) to Java
//Server sends the JSON to Client in ResponseBody - Spring boot converts Java to JSON
//Server can send either Entity or DTO to the client
//Best practice is to send DTO instead of exposing entities to client. Need to send what is actually required to client

//It uses Bean Validation framework - Hibernate Validator
//Title should not be null or empty - Not null/Empty is @NotEmpty

@Data
@Getter
@Setter
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 2, message = "Post title should be minimum of 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should be minimum of 10 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
}
