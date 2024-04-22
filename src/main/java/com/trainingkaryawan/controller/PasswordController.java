package com.trainingkaryawan.controller;

import com.trainingkaryawan.model.request.otp.ChangePasswordRequest;
import com.trainingkaryawan.model.request.otp.SendOtpRequest;
import com.trainingkaryawan.model.request.otp.ValidateOtpRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.service.impl.OtpServiceImpl;
import com.trainingkaryawan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/forget-password")
public class PasswordController {

    private OtpServiceImpl otpService;

    public PasswordController(OtpServiceImpl otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/request-otp")
    public ResponseEntity<Object> requestOtp(@RequestBody SendOtpRequest request){
        log.info("incoming request update request otp with request {}", JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = otpService.requestOtp(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Object> validateOtp(@RequestBody ValidateOtpRequest request){
        log.info("incoming request update request otp with request {}", JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = otpService.validateOtp(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @PostMapping("/change-password")
    public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordRequest request){
        log.info("incoming request update request otp with request {}", JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = otpService.changePassword(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }
}
