package com.trainingkaryawan.model.request.otp;

import lombok.Data;

@Data
public class ValidateOtpRequest {
    private String email;
    private String otp;
}
