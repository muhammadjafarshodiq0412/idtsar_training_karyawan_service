package com.trainingkaryawan.controller;

import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final ResponseService responseService;

    @Autowired
    public AuthenticationController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @GetMapping("/ping")
    public ResponseEntity<Object> ping(){
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateSuccessResponse(ResponseType.SUCCESS_PING, null);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }
}
