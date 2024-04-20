package com.trainingkaryawan.service;

import com.trainingkaryawan.entity.oauth.UserEntity;

public interface UserService {
    UserEntity findByUsername(String username);

//    Pair<HttpStatus, GeneraleResponse<Object>> findCurrentUser();
}
