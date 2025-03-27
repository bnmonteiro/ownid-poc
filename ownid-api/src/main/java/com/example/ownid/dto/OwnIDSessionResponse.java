package com.example.ownid.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnIDSessionResponse {
    private String token;

    public OwnIDSessionResponse(String token) {
        this.token = token;
    }
}
