package com.trainingkaryawan.controller;

import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.AuthRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.service.ResponseService;
import com.trainingkaryawan.service.impl.AuthenticationServiceImpl;
import com.trainingkaryawan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user-login")
public class AuthenticationController {

    private final ResponseService responseService;
    private final AuthenticationServiceImpl authenticationService;

    public AuthenticationController(ResponseService responseService, AuthenticationServiceImpl authenticationService) {
        this.responseService = responseService;
        this.authenticationService = authenticationService;
    }

    @GetMapping("/ping")
    public ResponseEntity<Object> ping(){
        Pair<HttpStatus, GeneralResponse<Object>> response = responseService.generateSuccessResponse(ResponseType.SUCCESS_PING, null);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthRequest request){
        log.info("incoming get token with request {}", JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = authenticationService.getToken(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }


    @PostMapping("/signin_google")
    public ResponseEntity<Object> signInGoogle(@RequestParam String accessToken){
        return null;
    }
}
