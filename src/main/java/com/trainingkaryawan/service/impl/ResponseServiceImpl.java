package com.trainingkaryawan.service.impl;

import com.trainingkaryawan.enums.ResponseType;
import com.trainingkaryawan.model.response.GeneraleResponse;
import com.trainingkaryawan.service.ResponseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ResponseServiceImpl implements ResponseService {

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> generateErrorResponse(ResponseType type, @Nullable Object data, String... messages) {
        String messageEn;
        String messageId;

        List<String> ens = new ArrayList<>();
        List<String> ids = new ArrayList<>();

        List<String> enStrings = new ArrayList<>();
        List<String> idStrings = new ArrayList<>();

        if (messages.length > 0) {
            for (int i=0; i < messages.length; i++) {
                if (i % 2 == 0) {
                    ens.add(messages[i]);
                    enStrings.add(messages[i]);
                } else {
                    ids.add(messages[i]);
                    idStrings.add(messages[i]);
                }
            }

            if (type.getDescriptionEn().contains("%s")) {
                messageEn = String.format(type.getDescriptionEn(), ens.toArray());
                messageId = String.format(type.getDescriptionId(), ids.toArray());
            } else {
                String enString = String.join(" ", enStrings);
                String idString = String.join(" ", idStrings);

                messageEn = type.getDescriptionEn() + " " + enString;
                messageId = type.getDescriptionId() + " " + idString;
            }

            return processErrorResponse(type, data, messageEn, messageId);
        }

        return processDefaultErrorResponse(type, data);
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> generateSuccessResponse(ResponseType type, Object data) {
        return getHttpStatusGeneralResponsePair(type, data);
    }

    private Pair<HttpStatus, GeneraleResponse<Object>> getHttpStatusGeneralResponsePair(ResponseType type, Object data) {

        GeneraleResponse<Object> response = new GeneraleResponse<>(
                type.getMessageCode(),
                type.getResponseStatus().getMessage(),
                data
        );

        HttpStatus status = HttpStatus.valueOf(type.getResponseStatus().getCode());
        return Pair.of(status, response);
    }

    @Override
    public ResponseEntity<Object> toResponseEntity(Pair<HttpStatus, GeneraleResponse<Object>> response) {
        return new ResponseEntity<>(response.getSecond(), response.getFirst());
    }

    @Override
    public Map<String, Object> toMap(Pair<HttpStatus, GeneraleResponse<Object>> response) {
        GeneraleResponse<Object> boostResponse = response.getSecond();

        Map<String, Object> map = new HashMap<>();
        map.put("code", boostResponse.getCode());
        map.put("status", boostResponse.getStatus());
        map.put("data", boostResponse.getData());

        return map;
    }

    private Pair<HttpStatus, GeneraleResponse<Object>> processErrorResponse(ResponseType type, Object data, String messageEn, String messageId) {


        GeneraleResponse<Object> response = new GeneraleResponse<>(
               type.getMessageCode(),
                type.getResponseStatus().getMessage(),
                data
        );

        HttpStatus status = HttpStatus.valueOf(type.getResponseStatus().getCode());
        return Pair.of(status, response);
    }

    private Pair<HttpStatus, GeneraleResponse<Object>> processDefaultErrorResponse(ResponseType type, Object data) {
        return getHttpStatusGeneralResponsePair(type, data);
    }

    @Override
    public Pair<HttpStatus, GeneraleResponse<Object>> generateErrorDataNotFound(String... message){
        return generateErrorResponse(ResponseType.DATA_NOT_FOUND, message);
    }
}
