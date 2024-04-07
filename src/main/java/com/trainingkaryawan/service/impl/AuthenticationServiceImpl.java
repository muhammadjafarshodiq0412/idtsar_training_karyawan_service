package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.constant.GeneralConstant;
import com.trainingkaryawan.entity.oauth.UserEntity;
import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.request.AuthRequest;
import com.trainingkaryawan.model.response.AuthReponse;
import com.trainingkaryawan.model.response.ClaimUserDetailsModel;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.repository.UserRepository;
import com.trainingkaryawan.service.ResponseService;
import com.trainingkaryawan.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
public class AuthenticationServiceImpl {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ResponseService responseService;

    @Autowired
    public AuthenticationServiceImpl(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepository, ResponseService responseService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.responseService = responseService;
    }

    public Pair<HttpStatus, GeneraleResponse<Object>> getToken(AuthRequest request) {
        log.info("start get token");
        Pair<HttpStatus, GeneraleResponse<Object>> response = responseService.generateErrorResponse(ResponseType.ERROR_FIND_DATA, null);
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            UserEntity userEntity = userRepository.findByUsername(request.getUsername()).orElse(null);
            if (ObjectUtils.isEmpty(userEntity)) {
                return responseService.generateErrorDataNotFound();
            }
            if (userEntity.getIsActive() == Boolean.FALSE) {
                return responseService.generateErrorResponse(ResponseType.USER_NOT_ACTIVATED, null);
            }

            String token = jwtUtil.generateToken(request.getUsername(), new ClaimUserDetailsModel(userEntity));
            response = responseService.generateSuccessResponse(ResponseType.SUCCESS_GENERATE_TOKEN, token);
        } catch (Exception e) {
            log.error("error get token with error {}", e.getMessage());
            if (e instanceof BadCredentialsException) {
                return responseService.generateErrorResponse(ResponseType.INCORRECT_CREDENTIAL, null);
            }

            response = responseService.generateErrorDataNotFound();
        }

        log.info("end get token");
        return response;
    }
}
