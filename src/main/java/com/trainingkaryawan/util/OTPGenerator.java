package com.trainingkaryawan.util;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OTPGenerator {

    private static final String OTP_CHARS = "0123456789";
    private static final int OTP_LENGTH = 6;

    public String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < OTP_LENGTH; i++) {
            int randomIndex = random.nextInt(OTP_CHARS.length());
            otp.append(OTP_CHARS.charAt(randomIndex));
        }

        return otp.toString();
    }
}