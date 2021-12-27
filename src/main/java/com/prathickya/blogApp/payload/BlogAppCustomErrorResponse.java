package com.prathickya.blogApp.payload;

import java.time.LocalDateTime;

public class BlogAppCustomErrorResponse {

    private final LocalDateTime time;
    private final String message;
    private final String details;

    public BlogAppCustomErrorResponse(LocalDateTime time, String message, String details) {
        this.time = time;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
