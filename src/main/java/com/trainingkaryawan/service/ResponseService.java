package com.trainingkaryawan.service;

import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.response.GeneralResponse;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ResponseService {
    Pair<HttpStatus, GeneralResponse<Object>> generateErrorResponse(ResponseType type, Object data, String... messages);
    Pair<HttpStatus, GeneralResponse<Object>> generateSuccessResponse(ResponseType type, Object data);
    ResponseEntity<Object> toResponseEntity(Pair<HttpStatus, GeneralResponse<Object>> response);
    Map<String, Object> toMap(Pair<HttpStatus, GeneralResponse<Object>> response);
    Pair<HttpStatus, GeneralResponse<Object>> generateErrorDataNotFound(String... message);
}
