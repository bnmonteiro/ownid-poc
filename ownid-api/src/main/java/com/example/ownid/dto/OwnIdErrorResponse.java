package com.example.ownid.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class OwnIdErrorResponse {
    private HttpStatus status;
    private String message;

    public OwnIdErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
