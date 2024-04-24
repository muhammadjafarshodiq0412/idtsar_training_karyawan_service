package com.trainingkaryawan.controller;

import com.trainingkaryawan.model.request.RegisterUserRequest;
import com.trainingkaryawan.model.response.GeneralResponse;
import com.trainingkaryawan.service.impl.UserServiceImpl;
import com.trainingkaryawan.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.trainingkaryawan.constant.GeneralConstant.TRAINING;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterUserRequest request) {
        log.info("incoming request register with request {}", JsonUtil.getString(request));
        Pair<HttpStatus, GeneralResponse<Object>> response = userService.register(request);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @GetMapping("/activation")
    public ResponseEntity<Object> userActivation(@RequestParam(name = "email") String email, @RequestParam(name = "otp") String otp) {
        log.info("incoming request user activation for email {} using otp {}", email, otp);
        Pair<HttpStatus, GeneralResponse<Object>> response = userService.activation(email, otp);
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }
}
