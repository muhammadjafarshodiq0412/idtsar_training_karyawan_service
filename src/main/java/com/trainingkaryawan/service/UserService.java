package com.trainingkaryawan.service;

import com.trainingkaryawan.entity.oauth.UserEntity;
import com.trainingkaryawan.model.response.GeneraleResponse;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;

public interface UserService {
    UserEntity findByUsername(String username);

//    Pair<HttpStatus, GeneraleResponse<Object>> findCurrentUser();
}
