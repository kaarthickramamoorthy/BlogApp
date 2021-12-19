package com.prathickya.blogApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO - Data Transfer Object
//Frequently used Design pattern to pass the data (multiple parameters) between Client Front-End/Server Back-End
//DTO helps to hide implementation details of JPA Entities to client
//Client sends the JSON to Server in RequestBody - Spring boot converts JSON(@RequestBody) to Java
//Server sends the JSON to Client in ResponseBody - Spring boot converts Java to JSON
//Server can send either Entity or DTO to the client
//Best practice is to send DTO instead of exposing entities to client. Need to send what is actually required to client

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;
    private String title;
    private String description;
    private String content;
}
