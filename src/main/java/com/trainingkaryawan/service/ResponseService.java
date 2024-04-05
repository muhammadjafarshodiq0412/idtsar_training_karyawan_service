package com.trainingkaryawan.service;

import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.response.GeneraleResponse;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface ResponseService {
    Pair<HttpStatus, GeneraleResponse<Object>> generateErrorResponse(ResponseType type, Object data, String... messages);
    Pair<HttpStatus, GeneraleResponse<Object>> generateSuccessResponse(ResponseType type, Object data);
    ResponseEntity<Object> toResponseEntity(Pair<HttpStatus, GeneraleResponse<Object>> response);
    Map<String, Object> toMap(Pair<HttpStatus, GeneraleResponse<Object>> response);
    public Pair<HttpStatus, GeneraleResponse<Object>> generateErrorDataNotFound(String... message);
}
