package com.trainingkaryawan.model.request.otp;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String otp;
    private String newPassword;
    private String confirmNewPassword;
}
