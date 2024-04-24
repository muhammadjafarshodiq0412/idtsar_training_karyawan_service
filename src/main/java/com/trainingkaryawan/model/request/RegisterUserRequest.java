package com.trainingkaryawan.model.request;

import lombok.Data;

import java.util.List;

@Data
public class RegisterUserRequest {
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String domicile;
    private String gender;
    private String password;
    private List<Long> roles;
}
