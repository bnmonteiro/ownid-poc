package com.example.ownid.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnIDDataResponse {
    private String ownIdData;

    public OwnIDDataResponse(String ownIdData) {
        this.ownIdData = ownIdData;
    }
}
