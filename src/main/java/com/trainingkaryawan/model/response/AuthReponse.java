package com.trainingkaryawan.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class AuthReponse {
    private Date expired;
    private String token;

    public AuthReponse(Date expired, String token) {
        this.expired = expired;
        this.token = token;
    }
}
